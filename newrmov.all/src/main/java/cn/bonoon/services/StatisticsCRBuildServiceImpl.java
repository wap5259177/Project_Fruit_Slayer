package cn.bonoon.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.core.StatisticsCRBuildService;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.ModelAreaQuarterBatch;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.entities.UnitEntity;
import cn.bonoon.entities.ruralBuild.ModelAreaCRBuildQuarterBatch;
import cn.bonoon.entities.ruralBuild.ModelAreaCRBuildQuarterEntity;
import cn.bonoon.entities.ruralBuild.ModelAreaCRBuildQuarterItem;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class StatisticsCRBuildServiceImpl extends AbstractService<ModelAreaCRBuildQuarterEntity> implements StatisticsCRBuildService {
	
	@Override
	public void refreshItem(LogonUser user, Long id) {
		//TODO:结束
	}
	
	private String unitPl = "select x from UnitEntity x where x.place.id=?";
	@Override
	@Transactional
	protected ModelAreaCRBuildQuarterEntity __save(OperateEvent event, ModelAreaCRBuildQuarterEntity entity) {
		/*
		 * 1.先判断这个年度的记录是否存在，如果存在，则不能再提交了
		 * 2.创建所有市级的上报记录
		 */
		int annual = entity.getAnnual();
		int period = entity.getPeriod();
		String ql = "select count(x) from ModelAreaCRBuildQuarterEntity x where x.annual=? and x.period=?";
		if(__exsit(ql, annual,period)){
			throw new RuntimeException("已经存在" + annual + "年的该季度的汇总记录！");
		}
		ql = "select x from ModelAreaEntity x where x.deleted=false";
		List<ModelAreaEntity> mas = __list(ModelAreaEntity.class, ql);
		int count = 0;
		super.__save(event, entity);
		ModelAreaCRBuildQuarterBatch[] quarterBatch = new ModelAreaCRBuildQuarterBatch[BatchHelper.length()];
		count = __parseMA(entity, mas, quarterBatch);
		int batchCount = 0; //批次
		for(ModelAreaCRBuildQuarterBatch qb : quarterBatch){
			if(null != qb){
				batchCount++;
				entityManager.merge(qb);
			}
		}
		entity.setMaCount(count);
		entity.setBatchCount(batchCount);
		entityManager.merge(entity);
		return entity;
	}

	private int __parseMA(ModelAreaCRBuildQuarterEntity entity, List<ModelAreaEntity> mas, ModelAreaCRBuildQuarterBatch[] quarterBatch) {
		int count = 0;
		for(ModelAreaEntity ma : mas){
			String batchName = ma.getBatch();
			int ordinal = BatchHelper.indexOf(batchName);
			if(ordinal != -1){
				ModelAreaCRBuildQuarterBatch ssc = __getBatch(entity, batchName, quarterBatch, ordinal);
				
				ModelAreaCRBuildQuarterItem item = new ModelAreaCRBuildQuarterItem();
				item.setBatch(ssc);
				item.setModelArea(ma);
				item.setOrdinal(ma.getOrdinalModel());
				
				//city
				item.setCityId(ma.getCityId());
				item.setCityName(ma.getCityName());
				
				UnitEntity cityUntiy = __first(UnitEntity.class, unitPl, ma.getCityId());
				item.setCityUnit(cityUntiy);
				
				//统计
				count++;
				ssc.setMaCount(ssc.getMaCount() + 1);
				entityManager.persist(item);
			}
		}
		return count;
	}

	private ModelAreaCRBuildQuarterBatch __getBatch(ModelAreaCRBuildQuarterEntity entity, String batchName, ModelAreaCRBuildQuarterBatch[] quarterBatch, int ordinal) {
		ModelAreaCRBuildQuarterBatch ssc = quarterBatch[ordinal];
		if(null == ssc){
			ssc = new ModelAreaCRBuildQuarterBatch();
			ssc.setQuarter(entity);
			ssc.setBatchName(batchName);
			ssc.setOrdinal(ordinal);
			quarterBatch[ordinal] = ssc;
			entityManager.persist(ssc);
		}
		return ssc;
	}

	
	
	@Override
	public List<ModelAreaCRBuildQuarterItem> itemSurveies(Long pid, Long id) {
		String ql = "select x from ModelAreaCRBuildQuarterItem x where x.batch.id=? and x.status !=0 order by x.modelArea.ordinalModel asc";
		return __list(ModelAreaCRBuildQuarterItem.class, ql, id);
	}

	@Override
	public List<ModelAreaCRBuildQuarterBatch> batchSurveies(Long pid) {
		String ql = "select x from ModelAreaCRBuildQuarterBatch x where x.quarter.id=?";
		return __list(ModelAreaCRBuildQuarterBatch.class, ql, pid);
	}

	
	public List<ModelAreaCRBuildQuarterItem> getAllItemFromProvince(Long id,
			String batch) {
		
		String ql = "select x from ModelAreaCRBuildQuarterItem x where x.batch.quarter.id=? and x.batch.batchName=?";
		return __list(ModelAreaCRBuildQuarterItem.class,ql,id,batch);
	}
	public List<ModelAreaCRBuildQuarterItem> getAllItemFromProvince(Long id
			) {
		
		String ql = "select x from ModelAreaCRBuildQuarterItem x where x.batch.quarter.id=? order by x.batch.batchName asc ";
		return __list(ModelAreaCRBuildQuarterItem.class,ql,id);
	}

	@Override
	public void upadateBatch(Long itemId, int batch, Long quarterId) {
		
			String hql="from ModelAreaCRBuildQuarterBatch as m where m.batchName='"+BatchHelper.getValue(batch)+"' and m.quarter.id="+quarterId;
			ModelAreaCRBuildQuarterBatch maqb=__first(ModelAreaCRBuildQuarterBatch.class,hql);
			
			 __exec("update from ModelAreaCRBuildQuarterItem as m set m.batch=? where m.id=?",maqb,itemId);
				

	}

	
	
		
	
	
}
