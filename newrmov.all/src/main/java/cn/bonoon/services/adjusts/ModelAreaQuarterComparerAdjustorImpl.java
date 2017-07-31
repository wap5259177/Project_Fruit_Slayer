package cn.bonoon.services.adjusts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.adjusts.ModelAreaQuarterComparerAdjustor;
import cn.bonoon.entities.ModelAreaQuarterAdministrationRural;
import cn.bonoon.entities.ModelAreaQuarterBatch;
import cn.bonoon.entities.ModelAreaQuarterEntity;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.entities.ModelAreaQuarterNaturalRural;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.RuralNeedFinishInfo;
import cn.bonoon.entities.logs.HandleLogEntity;
import cn.bonoon.entities.logs.ModelAreaQuarterComparer;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.AbstractSearchService;

@Service
@Transactional(readOnly = true)
public class ModelAreaQuarterComparerAdjustorImpl extends AbstractSearchService<ModelAreaQuarterComparer> implements ModelAreaQuarterComparerAdjustor{

	@Override
	@Transactional
	public void adjustAll(IOperator opt) {
		/*
		 * 处理的对象：ModelAreaQuarterEntity
		 */
		
		//处理所有的ModelAreaQuarterEntity对象
		String ql = "select x from ModelAreaQuarterEntity x order by x.ordinal";
		
		List<ModelAreaQuarterEntity> items = __list(ModelAreaQuarterEntity.class, ql);
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(opt);
		
		hlog.setTargetType(ModelAreaQuarterEntity.class.getName());
		hlog.setContent("调整季度报表中9个指标项的数据，共有季度(个)：" + items.size());
		entityManager.persist(hlog);
		
		parseEvent event = new parseEvent(hlog);
		
		for(ModelAreaQuarterEntity it : items){
			
			parse(it.getOrdinal(), it, event);
			
			entityManager.merge(it);
		}
		
		//这里处理完了，就对所有自然村进行处理
		String nql = "select x from NewRuralEntity x ";
		List<NewRuralEntity> nrs = __list(NewRuralEntity.class, nql);
		for(NewRuralEntity rural : nrs){
			
			RuralNeedFinishInfo rnfAdjust = event.rnfNatural.get(rural.getId());
			ModelAreaQuarterComparer ruralCom = new ModelAreaQuarterComparer();
			
			//调整前的原数据
			rural.getNeedFinish().copyTo(ruralCom.getRnfCompare().getSource());
			//处理后的
			ruralCom.getRnfCompare().getTarget().parseFinish(rnfAdjust);
			
			if(ruralCom.getRnfCompare().needAdjust()){
				ruralCom.setLog(event.hlog);
				
				ruralCom.setMaid(rural.getModelArea().getId());
				ruralCom.setName("[自然村]" + rural.getName());
				ruralCom.setStatus(1);
				ruralCom.setTargetType(NewRuralEntity.class.getName());
				ruralCom.setTargetId(rural.getId());
				
				ruralCom.getRnfCompare().getTarget().copyTo(rural.getNeedFinish());
				entityManager.merge(rural);
				entityManager.persist(ruralCom);
			}
		}
	}
	
	private void parse(Object ordinal, ModelAreaQuarterEntity maq, parseEvent event){
		for(ModelAreaQuarterBatch batch : maq.getBatchs()){
			parse(ordinal, batch, event);
		}
	}

	private void parse(Object ordinal, ModelAreaQuarterBatch batch, parseEvent event){
		for(ModelAreaQuarterItem it : batch.getItems()){
			parse(ordinal, it.getModelArea().getId(), it, event);
		}
	}

	private void parse(Object ordinal, Long maid, ModelAreaQuarterItem maItem, parseEvent event){
		/*
		 * 这里是处理一个片区的情况
		 */
		
		
		RuralNeedFinishInfo malSum = new RuralNeedFinishInfo();
		List<Long> hasParsed = new ArrayList<>();
		//该季报下示范片区所有行政村的数值累加
		for(ModelAreaQuarterAdministrationRural rural : maItem.getAdminRurals()){
			if(rural.getAdminRural().isDeleted()) continue;
			if(hasParsed.contains(rural.getAdminRural().getId())){
				//throw new RuntimeException("存在同一个村有两条记录的情况");
				continue;
			}
			hasParsed.add(rural.getAdminRural().getId());
			
			parse(ordinal, maid, rural, event);
			//累加每个行政村的need1-9
			malSum.sum(rural.getNeedFinish());
		}
		

		ModelAreaQuarterComparer ruralCom = new ModelAreaQuarterComparer();
		
		//调整前的原数据
		//把市县区的季报NeedFinish放进来?getSource()源 原来？
		
		maItem.getNeedFinish().copyTo(ruralCom.getRnfCompare().getSource());
		//把市县区的getAdminRurals所有的示范片区的行政村的NeedFinish()累加=malSum放进来 Target()目标的意思那么应该是示范片区的行政村改变了needFinish某个字段值就得和市县区的needFinish比较
		malSum.copyTo(ruralCom.getRnfCompare().getTarget());
		//比较俩个need1-9是否对应相等不等就是改变过了
		if(ruralCom.getRnfCompare().needAdjust()){
		//记录下谁操作的
			ruralCom.setLog(event.hlog);
			
			ruralCom.setMaid(maid);
			ruralCom.setName(ordinal + " - [片区]" + maItem.getModelArea().getName());
			ruralCom.setStatus(1);
			ruralCom.setTargetType(ModelAreaQuarterAdministrationRural.class.getName());
			ruralCom.setTargetId(maItem.getId());
			
//将市县区的季报拥有的NeedFinish改为Target()的值就是市县区的行政村的needFinish1-9
			maItem.setNeedFinish(malSum);
			entityManager.merge(maItem);
			entityManager.persist(ruralCom);
			
			//return ruralSum;
		}
	}
	
	private void parse(Object ordinal, Long maid, ModelAreaQuarterAdministrationRural rural, parseEvent event){
		/*
		 * 这里是处理一个行政村的情况
		 */
		
		RuralNeedFinishInfo ruralStat = new RuralNeedFinishInfo();
		
		List<Long> hasParsed = new ArrayList<>();
		//所有自然村的情况
		for(ModelAreaQuarterNaturalRural natural : rural.getNaturaRurals()){
			//需要被处理的对象
			NewRuralEntity nRural = natural.getNewRural();
			if(nRural.isDeleted()) continue;
			if(hasParsed.contains(nRural.getId())){
				//throw new RuntimeException("存在同一个村有两条记录的情况");
				continue;
			}
			hasParsed.add(nRural.getId());
			//赵以下有没有存到了
			RuralNeedFinishInfo rnfAdjust = event.rnfNatural.get(nRural.getId());
			if(null == rnfAdjust){
		
				rnfAdjust = new RuralNeedFinishInfo();
				
		
				natural.getNeedFinish().copyTo(rnfAdjust);

				//统计自然村有多少个needFinish rnfAdjust!=null就不加了
				ruralStat.stat(natural.getNeedFinish());
				
				event.rnfNatural.put(nRural.getId(), rnfAdjust);
				
				continue;
			}
			
			//需要处理的，这个是第二个季度的，
			//用来记录一个村不一样的地方的类
			ModelAreaQuarterComparer maqCom = new ModelAreaQuarterComparer();
			//调整前的原数据
			natural.getNeedFinish().copyTo(maqCom.getRnfCompare().getSource());
			
			//用前一个季度的数据把当前季度调整好
			natural.getNeedFinish().parseFinish2(rnfAdjust);
			//再调整缓存的记录，等待下一个季度的检查处理
			rnfAdjust.parseFinish(natural.getNeedFinish());
			
			//调整后的数据
			natural.getNeedFinish().copyTo(maqCom.getRnfCompare().getTarget());
			
			if(maqCom.getRnfCompare().needAdjust()){
				//数据是有调整过的
				
				maqCom.setLog(event.hlog);
				maqCom.setMaid(maid);
				maqCom.setName(ordinal + " - [自然村]" + nRural.getNaturalVillage());
				maqCom.setStatus(1);
				maqCom.setTargetType(ModelAreaQuarterNaturalRural.class.getName());
				maqCom.setTargetId(natural.getId());
			
				entityManager.merge(natural);
				entityManager.persist(maqCom);
			}
			
			//对数据进行处理完后再统计
			ruralStat.stat(natural.getNeedFinish());
		}
		
		ModelAreaQuarterComparer ruralCom = new ModelAreaQuarterComparer();
		//调整前的原数据
		rural.getNeedFinish().copyTo(ruralCom.getRnfCompare().getSource());
		//处理后的
		ruralStat.copyTo(ruralCom.getRnfCompare().getTarget());
		
		if(ruralCom.getRnfCompare().needAdjust()){
			ruralCom.setLog(event.hlog);
			
			ruralCom.setMaid(maid);
			ruralCom.setName(ordinal + " - [行政村]" + rural.getAdminRural().getName());
			ruralCom.setStatus(1);
			ruralCom.setTargetType(ModelAreaQuarterAdministrationRural.class.getName());
			ruralCom.setTargetId(rural.getId());
			
			rural.setNeedFinish(ruralStat);
			entityManager.merge(rural);
			entityManager.persist(ruralCom);
		}
		
		//都不需要调整的，返回null
		//return null;
	}
	
	
	private class parseEvent{
		
		private final HandleLogEntity hlog;
		
		/*
		 * 每一个自然村一个项，记录下所有自然村季度选择的情况
		 */
		private Map<Long, RuralNeedFinishInfo> rnfNatural = new HashMap<>();
		
		parseEvent(HandleLogEntity hlog){
			this.hlog = hlog;
		}
		
	}
	
}
