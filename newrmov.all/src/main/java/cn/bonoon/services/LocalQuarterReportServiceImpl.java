package cn.bonoon.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.LocalQuarterReportService;
import cn.bonoon.core.QuarterReportStatus;
import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.entities.InvestmentInfo;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.ModelAreaQuarterAdministrationRural;
import cn.bonoon.entities.ModelAreaQuarterEntity;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.entities.ModelAreaQuarterNaturalRural;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.ProjectEntity;
import cn.bonoon.entities.ProjectReportItem;
import cn.bonoon.entities.RuralNeedFinishInfo;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.AbstractSearchService;
import cn.bonoon.util.MonthAndQuarterUtil;

@Service
@Transactional(readOnly = true)
public class LocalQuarterReportServiceImpl extends
		AbstractSearchService<ModelAreaQuarterItem> implements
		LocalQuarterReportService {
	private static int[] MONTH = { 3, 6, 9, 12 };

	/*
	 * 开始上报
	 */
	@Override
	@Transactional
	public void toReport(IOperator opt, Long id) {
		ModelAreaQuarterItem item = __get(id);
		// 删掉季报下所有季度行政村，季度自然村
		__removeQrs(item);
		// 加过滤and x.deleted=false,x.status=1
		String ql2 = "select x from NewRuralEntity x where x.modelArea.id=? and x.deleted=false and x.status=1 and x.modelArea.deleted=false";
		ModelAreaEntity ma = item.getModelArea();
		// 本片区自然村
		List<NewRuralEntity> nres = __list(NewRuralEntity.class, ql2,
				ma.getId());
		Map<Long, ModelAreaQuarterAdministrationRural> map = new HashMap<>();
		for (NewRuralEntity nre : nres) {
			// 这行判断可以在hql查询时作为查询条件的
			if (null == nre || nre.getAdminRural() == null
					|| nre.getAdminRural().isDeleted()
					|| nre.getAdminRural().getStatus() != 1)
				continue;

			__add(item, map, nre);
		}
		item.setStatus(QuarterReportStatus.EDIT);
		item.getNeedFinish().clear();
		item.setArCount(0);
		item.setNrCount(0);
		item.setHouseholdCount(0);
		item.setPopulationCount(0);
		item.setArFinishPlan(0);

		for (Entry<Long, ModelAreaQuarterAdministrationRural> mapVar : map
				.entrySet()) {
			ModelAreaQuarterAdministrationRural maqar = mapVar.getValue();
			item.setArCount(item.getArCount() + maqar.getArCount());
			item.setNrCount(item.getNrCount() + maqar.getNrCount());// 主体村的自然村数
			item.setHouseholdCount(item.getHouseholdCount()
					+ maqar.getHouseholdCount());// 主体村的户数
			item.setPopulationCount(item.getPopulationCount()
					+ maqar.getPopulationCount());// 主体村的人数
			if (maqar.getArFinishPlan() == 1)
				item.setArFinishPlan(item.getArFinishPlan() + 1);
			item.getNeedFinish().sum(maqar.getNeedFinish());
		}

		__synchronousMoney(item);
		entityManager.merge(item);

	}

	public void set9Param(ModelAreaQuarterItem item) {
		item.getAdminRurals();
		int[] date = MonthAndQuarterUtil.getLastQuarter(item.getBatch()
				.getQuarter().getAnnual(), item.getBatch().getQuarter()
				.getPeriod());
		ModelAreaQuarterItem maqi = this.getOneMaqitem(item.getModelArea()
				.getId(), date[0], date[1]);
		List<ModelAreaQuarterAdministrationRural> lis = maqi.getAdminRurals();
		int i = 0;
		for (ModelAreaQuarterAdministrationRural lvar : lis) {
			System.out.println(lvar.getArFinishPlan() == lvar.getAdminRural()
					.getArFinishPlan());
			i += lvar.getAdminRural().getArFinishPlan();

			for (ModelAreaQuarterNaturalRural maqnr : lvar.getNaturaRurals()) {
				RuralNeedFinishInfo r1 = maqnr.getNeedFinish();
				RuralNeedFinishInfo r2 = maqnr.getNewRural().getNeedFinish();
				System.out.println(r1.getNeedFinish1() == r2.getNeedFinish1());
				System.out.println(r1.getNeedFinish2() == r2.getNeedFinish2());
				System.out.println(r1.getNeedFinish3() == r2.getNeedFinish3());
				System.out.println(r1.getNeedFinish4() == r2.getNeedFinish4());
				System.out.println(r1.getNeedFinish5() == r2.getNeedFinish5());
				System.out.println(r1.getNeedFinish6() == r2.getNeedFinish6());
				System.out.println(r1.getNeedFinish7() == r2.getNeedFinish7());
				System.out.println(r1.getNeedFinish8() == r2.getNeedFinish8());
				System.out.println(r1.getNeedFinish9() == r2.getNeedFinish9());

			}
		}
		System.out.println("上季度" + maqi.getBatch().getQuarter().getAnnual()
				+ " " + maqi.getBatch().getQuarter().getPeriod());
		System.out.println("ArFinishPlan: " + (i == maqi.getArFinishPlan()));
		System.out.println("上季度ArFinishPlan: " + (maqi.getArFinishPlan()));

		if (item.getAdminRurals() != null && item.getAdminRurals().size() > 0) {
			item.setArFinishPlan(0);
			for (ModelAreaQuarterAdministrationRural maqarVar : item
					.getAdminRurals()) {
				maqarVar.setArFinishPlan(maqarVar.getAdminRural()
						.getArFinishPlan());
				item.setArFinishPlan(item.getArFinishPlan()
						+ maqarVar.getAdminRural().getArFinishPlan());
				if (maqarVar.getNaturaRurals() != null
						&& maqarVar.getNaturaRurals().size() > 0) {
					for (ModelAreaQuarterNaturalRural maqnrVar : maqarVar
							.getNaturaRurals()) {
						if (maqnrVar != null) {
							System.out.println("name: "
									+ maqnrVar.getNewRural().getName()
									+ " modelareaRuralNeedFinish: "
									+ maqnrVar.getNeedFinish().getNeedFinish1()
									+ "ruralNeedFinish: "
									+ maqnrVar.getNewRural().getNeedFinish()
											.getNeedFinish1());
							RuralNeedFinishInfo rnfi2 = maqnrVar.getNewRural()
									.getNeedFinish();
							if (rnfi2.getNeedFinish1() == 2)
								rnfi2.setNeedFinish1(1);
							if (rnfi2.getNeedFinish2() == 2)
								rnfi2.setNeedFinish2(1);
							if (rnfi2.getNeedFinish3() == 2)
								rnfi2.setNeedFinish3(1);
							if (rnfi2.getNeedFinish4() == 2)
								rnfi2.setNeedFinish4(1);
							if (rnfi2.getNeedFinish5() == 2)
								rnfi2.setNeedFinish5(1);
							if (rnfi2.getNeedFinish6() == 2)
								rnfi2.setNeedFinish6(1);
							if (rnfi2.getNeedFinish7() == 2)
								rnfi2.setNeedFinish7(1);
							if (rnfi2.getNeedFinish8() == 2)
								rnfi2.setNeedFinish8(1);
							if (rnfi2.getNeedFinish9() == 2)
								rnfi2.setNeedFinish9(1);
							item.getNeedFinish().sum(rnfi2);
						}
					}
				}
			}
		}

	}

	private void __removeQrs(ModelAreaQuarterItem item) {
		List<ModelAreaQuarterAdministrationRural> qars = item.getAdminRurals();
		for (ModelAreaQuarterAdministrationRural qar : qars) {
			List<ModelAreaQuarterNaturalRural> qnrs = qar.getNaturaRurals();
			for (ModelAreaQuarterNaturalRural qnr : qnrs) {
				entityManager.remove(qnr);
			}
			qnrs.clear();
			entityManager.remove(qar);
		}
		qars.clear();
	}

	/**
	 * 对工程进展季报进行自然村属性,行政村属性的赋值添加
	 * 
	 * @param item
	 * @param map
	 * @param nre
	 * @return
	 */
	private ModelAreaQuarterNaturalRural __add(ModelAreaQuarterItem item,
			Map<Long, ModelAreaQuarterAdministrationRural> map,
			NewRuralEntity nre) {
//, ModelAreaQuarterNaturalRural oldMNRural
		/*
		 * 创建自然村季度:
		 * 1.如果能从map<id,ModelAreaQuarterAdministrationRural>拿到行政村季度则直接使用行行政村季度
		 * ,不能则创建它 2.注意要把自然村需要完成的指标项copy给你这里创建的自然村季度的需要完成的指标项, 以便判断填报时是否需要让它填报
		 * 如果里面的指标项的值为 0:可勾选 1:历史已经勾选 2:暂存就是勾选好的
		 */

		AdministrationCoreRuralEntity ar = nre.getAdminRural();

		ModelAreaQuarterAdministrationRural qar = map.get(ar.getId());

		// 自然村_季度数据赋值
		ModelAreaQuarterNaturalRural qnr = new ModelAreaQuarterNaturalRural();
		qnr.setNewRural(nre);
		qnr.setNrCount(1);
		qnr.setPopulationCount(nre.getPopulation());
		qnr.setHouseholdCount(nre.getHouseHold());

		ModelAreaQuarterEntity quarter = item.getBatch().getQuarter();
		int annual = quarter.getAnnual();
		int period = quarter.getPeriod();
		// 月份的下标是从0开始，所以第一季度为：0,1,2即<3
		int da = annual * 100 + MONTH[period];
		// 获取已经在上报的项目
		String ql = "select distinct x.project from ProjectReportItem x where x.project.rural.id=? and x.report.annual*100+x.report.period<? and x.report.status=1 and x.project.deleted=false";
		List<ProjectEntity> pes = __list(ProjectEntity.class, ql, nre.getId(),
				da);
		Integer fp = 0;
		Integer stp = 0;

		for (ProjectEntity pe : pes) {
			if (pe.getStatus() == 2) {
				fp++;
			}
			stp++;
			// 累计当前季度工程进展自然村资金
			qnr.getInvestment().sum(pe.getInvestment());
		}

		qnr.setStartProject(stp);
		qnr.setFinishProject(fp);

		RuralNeedFinishInfo nrnf = nre.getNeedFinish();
		RuralNeedFinishInfo qnrnf = qnr.getNeedFinish();
		nrnf.copyTo(qnrnf);

		// 行政村_季度数据赋值
		if (null == qar) {
			qar = new ModelAreaQuarterAdministrationRural();
			qar.setItem(item);
			qar.setAdminRural(ar);
			qar.setArCount(1);
			if (qar.getStartProject() == null)
				qar.setStartProject(new Integer(0));
			if (qar.getFinishProject() == null)
				qar.setFinishProject(new Integer(0));

			qar.setArFinishPlan(ar.getArFinishPlan());

			map.put(ar.getId(), qar);
		}
		// 条件: 指定不是null就是刷新功能 需要对map集合的qar行政村减掉不存在自然村的季度自然村的对应字段值
		// if (oldMNRural != null && qar != null) {
		// qar.setNrCount(qar.getNrCount() - oldMNRural.getNrCount());
		// qar.setHouseholdCount(qar.getHouseholdCount()
		// - oldMNRural.getHouseholdCount());
		// qar.setPopulationCount(qar.getPopulationCount()
		// - oldMNRural.getPopulationCount());
		// qar.getNeedFinish().subtract(oldMNRural.getNeedFinish());
		// qar.setStartProject(qar.getStartProject()
		// - oldMNRural.getStartProject());
		// qar.setFinishProject(qar.getFinishProject()
		// - oldMNRural.getFinishProject());
		// qar.getInvestment().subtractTo(qnr.getInvestment());
		// }

		qar.setNrCount(qar.getNrCount() + qnr.getNrCount());
		qar.setHouseholdCount(qar.getHouseholdCount() + qnr.getHouseholdCount());
		qar.setPopulationCount(qar.getPopulationCount()
				+ qnr.getPopulationCount());
		// 拿到自然村的needFinish不怕有2的情况
		qar.getNeedFinish().stat(qnr.getNeedFinish());
		qar.setStartProject(qar.getStartProject() + stp);
		qar.setFinishProject(qar.getFinishProject() + fp);
		// 工程进展自然村资金累计给对应所属行政村
		qar.getInvestment().sum(qnr.getInvestment());
		// 如果用方法传参数对象设置本对象的属性对象的方式，传入的参数对象引用名先为null后来被赋值了对象是不会对对象的属性对象赋值成功的，原因是对象的属性对象名引用了方法的参数引用名，而了方法的参数引用名刚开始引用了null的引用名，所以了方法的参数引用名为null,所以对象的属性对象引用了
		qnr.setArQuarter(qar);

		entityManager.persist(qar);
		entityManager.persist(qnr);
		return qnr;

	}

	/**
	 * 刷新
	 */
	@Override
	@Transactional
	public void refreshRural(LogonUser user, Long id) {

		/*
		 * 算法: 两个需要被删除的集合(自然村季度,行政村季度) 通过判断,移除不需要删除的项 最后集合剩下的项就是需要从数据库中删除的
		 */
		ModelAreaQuarterItem item = __get(id);
		ModelAreaEntity ma = item.getModelArea();
		// 刷新后重新设置这些行政村、自然村、户数、人数
		item.setArCount(ma.getSumMAdmin());// 主体村的行政村数
		item.setNrCount(ma.getSumMRural());// 主体村的自然村数
		item.setHouseholdCount(ma.getMainSumHouse());// 主体村的户数
		item.setPopulationCount(ma.getMainSumP());// 主体村的人数
		// 当前片区所有自然村
		// 加过滤and x.deleted=false
		String ql2 = "select x from NewRuralEntity x where x.modelArea.id=? and x.deleted=false";
		List<NewRuralEntity> nres = __list(NewRuralEntity.class, ql2,
				ma.getId());
		// 已经解析的行政村
		List<ModelAreaQuarterAdministrationRural> qars = item.getAdminRurals();
		// 所有已经解析的自然村
		List<ModelAreaQuarterNaturalRural> qnrs = new ArrayList<>();
		// 正常情况下的行政村
		Map<Long, ModelAreaQuarterAdministrationRural> ars = new HashMap<>();

		// 需要删除的行政村季度 id (有写可能删除有些不删除)
		List<Long> needDeletes = new ArrayList<>();

		for (ModelAreaQuarterAdministrationRural ar : qars) {
			qnrs.addAll(ar.getNaturaRurals());
			ars.put(ar.getAdminRural().getId(), ar);

			needDeletes.add(ar.getId());
		}
		// nres 台账所有自然村
		for (NewRuralEntity nr : nres) {
			// tm是自然村对应的季度里的自然村
			ModelAreaQuarterNaturalRural tm = null;
			for (ModelAreaQuarterNaturalRural qa : qnrs) {
				if (qa.getNewRural().getId().equals(nr.getId())) {
					tm = qa;
					break;
				}
			}
			// 如果存在 (台账的自然村在季度里面存在,则直接用季度里的自然村. 如果在季度里面找不着则创建一个自然村季度)
			if (null != tm) {
				needDeletes.remove(tm.getArQuarter().getId());// 把他移除后,剩下的就是需要删除的行政村季度
				qnrs.remove(tm);// 把他移除后,剩下就是需要删除的自然村季度
			} else {
				// news.add(nr);
				ModelAreaQuarterNaturalRural qnr = __add(item, ars, nr);// qnr==null??
				if (qnr != null) {
					needDeletes.remove(qnr.getArQuarter().getId());// 新添加上来的这个行政村季度是不需要删除的,所以这里移除掉,有可能这个行政村id不在这个needdelete的map里,但不影响,方法返回false则不执行
				}
			}
		}
		// qnrs需要从数据库删除的自然村季度
		for (ModelAreaQuarterNaturalRural qa : qnrs) {
			entityManager.remove(qa);
		}
		// needDeletes需要从数据库删除的行政村季度
		for (Long nd : needDeletes) {
			for (ModelAreaQuarterAdministrationRural ar : qars) {
				if (nd.equals(ar.getId())) {
					entityManager.remove(ar);
				}
			}
		}

		/*
		 * 1.已经解析的自然村和最新查询出的自然村做对比
		 * 减少:如果解析的村子多了记录则删除这些记录,然后判断这些自然村对应的行政村是否还有自然村.如果也没有了自然村,那么则行政村也删除
		 * 增加:判断该自然村是否有行政村,没有则创建
		 * 
		 * 2.行政村对比 3. 4.
		 */
		// Map<Long ,ModelAreaQuarterNaturalRural> nmap = new
		// HashMap<>();//刷新后存的自然村季度
		// Map<Long ,ModelAreaQuarterNaturalRural> nmap2 = new HashMap<>();
		//
		// Map<Long ,ModelAreaQuarterAdministrationRural> amap = new
		// HashMap<>();//刷新后存的行政村季度
		// Map<Long ,ModelAreaQuarterAdministrationRural> amap2 = new
		// HashMap<>();
		//
		//
		// for(ModelAreaQuarterNaturalRural qnr:qnrs){
		// nmap2.put(qnr.getNewRural().getId(), qnr);
		// }
		// for(ModelAreaQuarterAdministrationRural qar:qars){
		// amap2.put(qar.getAdminRural().getId(), qar);
		// }

		// for(NewRuralEntity nre:nres){
		// if(nmap2.containsKey(nre.getId())){
		// //1.如果 查出来的id在解析的里面 就把自然村季度放到一个map中,查出来的有多少个就放多少个
		// nmap.put(nre.getId(), nmap2.get(nre.getId()));
		// amap.put(nre.getAdminRural().getId(),amap2.get(nre.getAdminRural().getId())
		// );
		// }else{
		// //2.如果查出来的id不在解析的里面,那么创建一个自然村季度
		// ModelAreaQuarterNaturalRural qnr = new
		// ModelAreaQuarterNaturalRural();
		// qnr.setNewRural(nre);
		// AdministrationCoreRuralEntity ar = nre.getAdminRural();
		// if(amap2.containsKey(ar.getId())){//行政村季度已经有
		// qnr.setArQuarter(amap2.get(ar.getId()));
		// }else{
		// ModelAreaQuarterAdministrationRural arQuarter = new
		// ModelAreaQuarterAdministrationRural();
		// qnr.setArQuarter(arQuarter);
		// }
		// nmap.put(nre.getId(), qnr);
		// }
		// }
		// //3.判断 nmap amap2 如果行政村下面已经没有村子了,那么删除这个行政村季度
		//
		// Set<Long> ks = nmap2.keySet();
		// for(Long nid:ks){
		// if(!nmap.containsKey(nid)){//不在刷新后的季度里面,说明这个自然村id是被删除了的,判断这个自然村对应的行政村下面是否还有自然村,如果没有了则删除这个行政村
		// ModelAreaQuarterNaturalRural qnr = nmap2.get(nid);
		// if(qnr.getArQuarter().getNaturaRurals().size()<=0){
		//
		// }
		// }
		// }

	}

	@Override
	public ModelAreaQuarterNaturalRural getQnr(Long id) {
		String ql = "select x from 	ModelAreaQuarterNaturalRural x where x.id=?";
		return __first(ModelAreaQuarterNaturalRural.class, ql, id);
	}

	@Override
	@Transactional
	public void update(ModelAreaQuarterNaturalRural qnr) {
		entityManager.merge(qnr);
	}

	@Override
	@Transactional
	public void updateItem(ModelAreaQuarterItem item) {
		RuralNeedFinishInfo manf = item.getNeedFinish();
		manf.clear();// 9项需完成的指标清0
		item.setArFinishPlan(0);// 行政村规划设计数也要清0
		for (ModelAreaQuarterAdministrationRural ar : item.getAdminRurals()) {
			// AdministrationCoreRuralEntity are = ar.getAdminRural();
			RuralNeedFinishInfo arnf = ar.getNeedFinish();
			arnf.clear();
			for (ModelAreaQuarterNaturalRural qnr : ar.getNaturaRurals()) {
				// NewRuralEntity nre = qnr.getNewRural();
				RuralNeedFinishInfo nrnf = qnr.getNeedFinish();
				if (nrnf.getNeedFinish1() == 1) {
					arnf.setNeedFinish1(arnf.getNeedFinish1() + 1);
				}
				if (nrnf.getNeedFinish2() == 1) {
					arnf.setNeedFinish2(arnf.getNeedFinish2() + 1);
				}
				if (nrnf.getNeedFinish3() == 1) {
					arnf.setNeedFinish3(arnf.getNeedFinish3() + 1);
				}
				if (nrnf.getNeedFinish4() == 1) {
					arnf.setNeedFinish4(arnf.getNeedFinish4() + 1);
				}
				if (nrnf.getNeedFinish5() == 1) {
					arnf.setNeedFinish5(arnf.getNeedFinish5() + 1);
				}
				if (nrnf.getNeedFinish6() == 1) {
					arnf.setNeedFinish6(arnf.getNeedFinish6() + 1);
				}
				if (nrnf.getNeedFinish7() == 1) {
					arnf.setNeedFinish7(arnf.getNeedFinish7() + 1);
				}
				if (nrnf.getNeedFinish8() == 1) {
					arnf.setNeedFinish8(arnf.getNeedFinish8() + 1);
				}
				if (nrnf.getNeedFinish9() == 1) {
					arnf.setNeedFinish9(arnf.getNeedFinish9() + 1);
				}
				entityManager.merge(qnr);
				// entityManager.merge(nre);
			}
			manf.sum(arnf);// 统计9项需完成指标
			// 统计行政村规划设计数
			if (ar.getArFinishPlan() != 0) {
				item.setArFinishPlan(item.getArFinishPlan() + 1);
			}
			entityManager.merge(ar);
			// entityManager.merge(are);
		}
		entityManager.merge(item);
	}

	@Override
	@Transactional
	public void toFinish(Long id) {
		ModelAreaQuarterItem item = __get(id);
		item.setStatus(QuarterReportStatus.AUDIT);
		__setRuralNf(item);
		entityManager.merge(item);
	}

	/*
	 * 原始村子的needfish 指标有两个值： 0：未完成（默认）
	 * 1：已完成（勾选）：那么已经勾选的村子，在下一个季度的时候就不应该再让用户勾选，应该灰掉那个input
	 * 提交并完成上报的时候就应该，处理好原始村子的那些指标。
	 */
	private void __setRuralNf(ModelAreaQuarterItem item) {
		for (ModelAreaQuarterAdministrationRural qar : item.getAdminRurals()) {
			AdministrationCoreRuralEntity are = qar.getAdminRural();// 同时要保存are的needfinish
			are.setArFinishPlan(NewRuralEntity.__checkSetNf(
					qar.getArFinishPlan(), are.getArFinishPlan()));
			for (ModelAreaQuarterNaturalRural qnr : qar.getNaturaRurals()) {
				NewRuralEntity nre = qnr.getNewRural();// 同时要保存nre的needfinish

				nre.parseNeedFinish(qnr.getNeedFinish());
				//
				// RuralNeedFinishInfo nreNf = nre.getNeedFinish();
				// RuralNeedFinishInfo qnrNf = qnr.getNeedFinish();
				// if(nreNf==null){
				// nreNf = new RuralNeedFinishInfo();
				// }
				// nreNf.setNeedFinish1(__checkSetNf(qnrNf.getNeedFinish1()));
				// nreNf.setNeedFinish2(__checkSetNf(qnrNf.getNeedFinish2()));
				// nreNf.setNeedFinish3(__checkSetNf(qnrNf.getNeedFinish3()));
				// nreNf.setNeedFinish4(__checkSetNf(qnrNf.getNeedFinish4()));
				// nreNf.setNeedFinish5(__checkSetNf(qnrNf.getNeedFinish5()));
				// nreNf.setNeedFinish6(__checkSetNf(qnrNf.getNeedFinish6()));
				// nreNf.setNeedFinish7(__checkSetNf(qnrNf.getNeedFinish7()));
				// nreNf.setNeedFinish8(__checkSetNf(qnrNf.getNeedFinish8()));
				// nreNf.setNeedFinish9(__checkSetNf(qnrNf.getNeedFinish9()));
				// nre.setNeedFinish(nreNf);//少了这个会出问题的
				//

				entityManager.merge(nre);
				RuralNeedFinishInfo rnfiVar = qnr.getNeedFinish();
				if (rnfiVar.getNeedFinish1() == 2) {
					rnfiVar.setNeedFinish1(1);
				}
				if (rnfiVar.getNeedFinish2() == 2) {
					rnfiVar.setNeedFinish2(1);
				}
				if (rnfiVar.getNeedFinish3() == 2) {
					rnfiVar.setNeedFinish3(1);
				}
				if (rnfiVar.getNeedFinish4() == 2) {
					rnfiVar.setNeedFinish4(1);
				}
				if (rnfiVar.getNeedFinish5() == 2) {
					rnfiVar.setNeedFinish5(1);
				}
				if (rnfiVar.getNeedFinish6() == 2) {
					rnfiVar.setNeedFinish6(1);
				}
				if (rnfiVar.getNeedFinish7() == 2) {
					rnfiVar.setNeedFinish7(1);
				}
				if (rnfiVar.getNeedFinish8() == 2) {
					rnfiVar.setNeedFinish8(1);
				}
				if (rnfiVar.getNeedFinish9() == 2) {
					rnfiVar.setNeedFinish9(1);
				}
			}
			entityManager.merge(are);
		}
		reFlish(item);
	}

	// public int __checkSetNf(int value){
	// int result = 0;
	// if(value==0)result = 0;
	// if(value==1)result = 1;
	// if(value==2)result = 1;
	// return result;
	// }

	// @Override
	// public List<ModelAreaQuarterItem> getMaqitems(int count, Long id) {
	// String ql =
	// "select x from ModelAreaQuarterItem x where x.status=1 and x.modelArea.ownerId=? order by x.batch.quarter.annual desc";
	// return __top(count, ModelAreaQuarterItem.class, ql, id);
	// }

	// 通过ownerId annual period 拿到一个 item
	@Override
	public ModelAreaQuarterItem getMaqitem(Long ownerId, int annual, int period) {
		String ql = "select x from ModelAreaQuarterItem x where x.status=1 and x.modelArea.ownerId=? and x.batch.quarter.annual=? and x.batch.quarter.period=?";
		return __first(ModelAreaQuarterItem.class, ql, ownerId, annual, period);
	}

	/*
	 * 同步数据，只处理一个片区的数据
	 */
	@Override
	@Transactional
	public void synchronousMoney(Long id) {
		ModelAreaQuarterItem item = __get(id);

		__synchronousMoney(item);

		ModelAreaEntity ma = item.getModelArea();
		item.setArCount(ma.getSumMAdmin());// 主体村的行政村数
		item.setNrCount(ma.getSumMRural());// 主体村的自然村数
		item.setHouseholdCount(ma.getMainSumHouse());// 主体村的户数
		item.setPopulationCount(ma.getMainSumP());// 主体村的人数

		// -----重新统计那9个指标---------------------------------------------------------
		RuralNeedFinishInfo manf = item.getNeedFinish();
		manf.clear();// 9项需完成的指标清0
		item.setArFinishPlan(0);// 行政村规划设计数也要清0
		for (ModelAreaQuarterAdministrationRural ar : item.getAdminRurals()) {
			RuralNeedFinishInfo arnf = ar.getNeedFinish();
			arnf.clear();
			List<ModelAreaQuarterNaturalRural> qnrs = ar.getNaturaRurals();
			if (qnrs != null) {
				for (ModelAreaQuarterNaturalRural qnr : qnrs) {
					RuralNeedFinishInfo nrnf = qnr.getNeedFinish();
					arnf.stat(nrnf);
					// entityManager.merge(qnr);
				}
			}
			manf.sum(arnf);// 统计9项需完成指标
			// 统计行政村规划设计数
			if (ar.getArFinishPlan() != 0) {
				item.setArFinishPlan(item.getArFinishPlan() + 1);
			}
			entityManager.merge(ar);
		}
		// ------------------------------------------------------------------------------
		entityManager.flush();
		entityManager.merge(item);
	}

	public void __synchronousMoney(ModelAreaQuarterItem item) {
		ModelAreaQuarterEntity quarter = item.getBatch().getQuarter();
		int annual = quarter.getAnnual();
		int period = quarter.getPeriod();
		// arr[0]:第一季度对应月份 arr[1]第二季度...
		// int arr[] = {3,6,9,12};
		// 月份的下标是从0开始，所以第一季度为：0,1,2即<3
		int da = annual * 100 + MONTH[period];
		Long maId = item.getModelArea().getId();

		// 查出月报
		// String ql =
		// "select x from ProjectReportEntity x where x.annual*100+x.period<?  and x.modelArea.id=? and x.status=1";
		// List<ProjectReportEntity> prs = __list(ProjectReportEntity.class, ql,
		// da, maId);

		// 按照每个项目每条月报item来累计季度报表资金
		String ql = "select x from ProjectReportItem x where x.report.annual*100+x.report.period<?  and x.project.modelArea.id=? and x.report.status=1 and x.project.deleted=false";
		List<ProjectReportItem> prs = __list(ProjectReportItem.class, ql, da,
				maId);

		InvestmentInfo invs = item.getInvestment();
		if (invs == null) {
			invs = new InvestmentInfo();
		} else {
			invs.clear();
		}

		for (ProjectReportItem pr : prs) {
			if (pr.getProject().getRural() instanceof NewRuralEntity) {
				invs.sum(pr.getInvestment());
			}

		}

		// 累计初始资金
		ql = "select distinct x.project from ProjectReportItem x where x.report.annual*100+x.report.period<? and x.report.modelArea.id=? and x.report.status=1 and x.project.deleted=false";
		List<ProjectEntity> pes = __list(ProjectEntity.class, ql, da, maId);
		int fp = 0;
		int stp = 0;
		for (ProjectEntity p : pes) {
			if (p.getRural() instanceof NewRuralEntity) {
				invs.sum(p.getSourceInvestment());
				if (p.getStatus() == 2) {
					fp++;
				}
				stp++;
			}

		}

		// 下面是同步资金需要修改的项
		item.setInvestment(invs);
		item.setFinishProject(fp);
		item.setStartProject(stp);

		// ---------------------------------------------------------
		/*
		 * String ql = "select sum(x.investment.totalFunds), " +
		 * "sum(x.investment.stateFunds)," + "sum(x.investment.specialFunds)," +
		 * "sum(x.investment.provinceFunds)," + "sum(x.investment.cityFunds)," +
		 * "sum(x.investment.countyFunds)," + "sum(x.investment.socialFunds)," +
		 * "sum(x.investment.massFunds)," +//增加otherFunds
		 * "sum(x.investment.otherFunds)" +
		 * "from ProjectReportEntity x where x.annual*100+x.period<?  and x.modelArea.id=? and x.status=1 "
		 * ; //and x.modelArea.deleted=false x.type=1
		 * 
		 * Object[]funds = __first(Object[].class, ql, da,maId);
		 * 
		 * InvestmentInfo invs = new InvestmentInfo();
		 * 
		 * if(funds[0]!=null){ //重新统计下总资金 double totalFunds = 0.0; for(int i=1;
		 * i<funds.length; i++){
		 * //统计totalFunds应该从下标1开始，因为下标为0是数据库保存的totalFUnds，这个字段有问题 totalFunds =
		 * add(totalFunds, funds[i]); }
		 * 
		 * String ql2 =
		 * "select distinct x.project from ProjectReportItem x where x.report.annual*100+x.report.period<? and x.report.modelArea.id=? and x.report.status=1 and x.project.deleted=false"
		 * ; List<ProjectEntity> pes = __list(ProjectEntity.class, ql2,
		 * da,maId); // invs.setTotalFunds((double) funds[0]);
		 * 
		 * 
		 * //找出被退回项目的月报item // String
		 * ql3="select sum(x.investment.totalFunds), " + //
		 * "sum(x.investment.stateFunds)," + //
		 * "sum(x.investment.specialFunds)," + //
		 * "sum(x.investment.provinceFunds)," + //
		 * "sum(x.investment.cityFunds)," + // "sum(x.investment.countyFunds),"
		 * + // "sum(x.investment.socialFunds)," + //
		 * "sum(x.investment.massFunds)," +//增加otherFunds //
		 * "sum(x.investment.otherFunds)" + //
		 * "from ProjectReportItem x where x.project.status=0 and x.report.status=1 and x.project.modelArea.id=?"
		 * ; // Object[] itemFunds = __first(Object[].class,ql3,maId); // double
		 * itemTotolFunds = 0.0; // if(itemFunds[0] != null){ // // for(int
		 * i=1;i<itemFunds.length;i++){ // itemTotolFunds =
		 * add(itemTotolFunds,(double)itemFunds[i]); // } // }else{ // for(int
		 * i=1;i<itemFunds.length;i++){ // itemFunds[i] = 0.0; // itemTotolFunds
		 * = add(itemTotolFunds,(double)itemFunds[i]); // } // } //
		 * 
		 * //
		 * invs.setStateFunds(subtract((double)funds[1],(double)itemFunds[1]));
		 * //
		 * invs.setSpecialFunds(subtract((double)funds[2],(double)itemFunds[2]
		 * )); //
		 * invs.setProvinceFunds(subtract((double)funds[3],(double)itemFunds
		 * [3])); //
		 * invs.setCityFunds(subtract((double)funds[4],(double)itemFunds[4]));
		 * //
		 * invs.setCountyFunds(subtract((double)funds[5],(double)itemFunds[5]));
		 * //
		 * invs.setSocialFunds(subtract((double)funds[6],(double)itemFunds[6]));
		 * //
		 * invs.setMassFunds(subtract((double)funds[7],(double)itemFunds[7]));
		 * //
		 * invs.setOtherFunds(subtract((double)funds[8],(double)itemFunds[8]));
		 * 
		 * //月报的总资金减去被退回项目上报过月报的资金总和 //double subtractFunds
		 * =subtract(totalFunds,itemTotolFunds);
		 * 
		 * 
		 * invs.setStateFunds((double)funds[1]);
		 * invs.setSpecialFunds((double)funds[2]);
		 * invs.setProvinceFunds((double)funds[3]);
		 * invs.setCityFunds((double)funds[4]);
		 * invs.setCountyFunds((double)funds[5]);
		 * invs.setSocialFunds((double)funds[6]);
		 * invs.setMassFunds((double)funds[7]);
		 * invs.setOtherFunds((double)funds[8]);
		 * 
		 * invs.setTotalFunds(totalFunds);
		 * 
		 * int fp = 0; for(ProjectEntity p : pes){
		 * invs.sum(p.getSourceInvestment()); if(p.getStatus() == 2){ fp++; } }
		 * item.setInvestment(invs);
		 * 
		 * item.setFinishProject(fp); item.setStartProject(pes.size()); }
		 */
	}

	@Override
	public List<ModelAreaQuarterItem> getUrges(IOperator user) {
		// 片区未上报备案 (x.modelArea.status:-1),季报已经完成(x.status=1),季报在审核(x.status=4)
		String ql = "select x from ModelAreaQuarterItem x where x.modelArea.ownerId=? and  x.urge>0 and x.status not in(1,4) and x.modelArea.status<>-1 and x.modelArea.deleted=false";
		return __list(ModelAreaQuarterItem.class, ql, user.getOwnerId());
	}

	@Override
	public List<ModelAreaQuarterItem> getNeedReport(IOperator user) {
		String ql = "select x from ModelAreaQuarterItem x where x.modelArea.ownerId=? and x.batch.quarter.startAt<=? and x.modelArea.status<>-1 and x.modelArea.deleted=false";
		return __list(ModelAreaQuarterItem.class, ql, user.getOwnerId(),
				new Date());
	}

	@Override
	public ModelAreaQuarterItem getOneMaqitem(Long modelAreaID, int annual,
			int period) {
		String ql = "select x from ModelAreaQuarterItem x where x.modelArea.ownerId=? and x.batch.quarter.annual=? and x.batch.quarter.period=?";
		ql = "select x from ModelAreaQuarterItem x where x.modelArea.id=? and x.batch.quarter.annual=? and x.batch.quarter.period=?";
		return __first(ModelAreaQuarterItem.class, ql, modelAreaID, annual,
				period);

	}

	@Override
	public String updateQuarter9Parm(Long id) throws Exception {
		// 校正前刷一遍季报自然村主要是去掉暂存的
		ModelAreaQuarterItem item = this.get(id);
		reFlish(item);
		// 检测9项指标有无比之前某个季度少的情况
		// for(ModelAreaQuarterItem
		// maqi:beforeQuarter(item.getBatch().getQuarter().getAnnual(),item.getBatch().getQuarter().getPeriod(),item.getModelArea().getId())){
		// reFlushNewRuralNeedFinish(maqi,item);
		// }
		ModelAreaQuarterItem lastMAQI = null;
		for (ModelAreaQuarterItem lvar : beforeQuarter(item.getBatch()
				.getQuarter().getAnnual(), item.getBatch().getQuarter()
				.getPeriod(), item.getModelArea().getId())) {
			if ((item.getBatch().getQuarter().getAnnual() * 10 + item
					.getBatch().getQuarter().getPeriod()) > (lvar.getBatch()
					.getQuarter().getAnnual() * 10 + lvar.getBatch()
					.getQuarter().getPeriod())) {
				System.out.println(lvar.getBatch().getQuarter().getAnnual()
						+ "" + lvar.getBatch().getQuarter().getPeriod());
				lastMAQI = lvar;
				break;
			}

		}
		reFlushNewRuralNeedFinish(lastMAQI, item);
		item.getNeedFinish().clear();
		if (item.getAdminRurals() != null && item.getAdminRurals().size() > 0) {

			item.setArFinishPlan(0);
			int ruralSize = 0;
			for (ModelAreaQuarterAdministrationRural maqarVar : item
					.getAdminRurals()) {
				maqarVar.setArFinishPlan(maqarVar.getAdminRural()
						.getArFinishPlan());
				item.setArFinishPlan(item.getArFinishPlan()
						+ maqarVar.getAdminRural().getArFinishPlan());
				if (maqarVar.getNaturaRurals() != null
						&& maqarVar.getNaturaRurals().size() > 0) {
					System.out.println(maqarVar.getNaturaRurals().size());

					for (ModelAreaQuarterNaturalRural maqnrVar : maqarVar
							.getNaturaRurals()) {
						if (maqnrVar != null) {
							System.out.println("自然村数：" + (++ruralSize));
							// System.out.println("name: "
							// + maqnrVar.getNewRural().getName()
							// + " modelareaRuralNeedFinish: "
							// + maqnrVar.getNeedFinish().getNeedFinish1()
							// + "ruralNeedFinish: "
							// + maqnrVar.getNewRural().getNeedFinish()
							// .getNeedFinish1());
							RuralNeedFinishInfo rnfi = maqnrVar.getNeedFinish();
							RuralNeedFinishInfo rnfi2 = maqnrVar.getNewRural()
									.getNeedFinish();

							// 如果当前最新季度季度自然村有一个needFinish值小于对应自然村needFinish则重新矫正

							if (rnfi.getNeedFinish1() < rnfi2.getNeedFinish1()) {
								rnfi.setNeedFinish1(rnfi2.getNeedFinish1());
							}
							if (rnfi.getNeedFinish2() < rnfi2.getNeedFinish2()) {
								rnfi.setNeedFinish2(rnfi2.getNeedFinish2());
							}

							if (rnfi.getNeedFinish3() < rnfi2.getNeedFinish3()) {
								rnfi.setNeedFinish3(rnfi2.getNeedFinish3());
							}

							if (rnfi.getNeedFinish4() < rnfi2.getNeedFinish4()) {
								rnfi.setNeedFinish4(rnfi2.getNeedFinish4());
							}

							if (rnfi.getNeedFinish5() < rnfi2.getNeedFinish5()) {
								rnfi.setNeedFinish5(rnfi2.getNeedFinish5());
							}

							if (rnfi.getNeedFinish6() < rnfi2.getNeedFinish6()) {
								rnfi.setNeedFinish6(rnfi2.getNeedFinish6());
							}

							if (rnfi.getNeedFinish7() < rnfi2.getNeedFinish7()) {
								rnfi.setNeedFinish7(rnfi2.getNeedFinish7());
							}

							if (rnfi.getNeedFinish8() < rnfi2.getNeedFinish8()) {
								rnfi.setNeedFinish8(rnfi2.getNeedFinish8());
							}
							if (rnfi.getNeedFinish9() < rnfi2.getNeedFinish9()) {
								rnfi.setNeedFinish9(rnfi2.getNeedFinish9());
							}
							item.getNeedFinish().sum(rnfi2);
							System.out.println("item nF8:"
									+ item.getNeedFinish().getNeedFinish8());
						}
					}
				}
			}
		}
		// 赋值完后最新该片区所有自然村的9项指标排在本季度之前的所有季度比较若有比前季度数值小的则本季度数据有异常需赋值为前季度的最大值

		entityManager.flush();
		entityManager.merge(item);

		return "本季度9项指标以及村有编制规划设计村数（个）已更新至最新数据!";

	}

	/**
	 * 比较俩个季度报表的9项指标比较规则：前季度的某个指标若大于后季度的指标则
	 * 前季度拥有的每个自然村（注意这里拿到的是前季度拥有的季度自然村对应的自然村）的指标赋值为前季度的对应指标,
	 * 后季度报表出错指标进行重新统计为前季度的对应指标
	 */
	private void reFlushNewRuralNeedFinish(ModelAreaQuarterItem maiVar,
			ModelAreaQuarterItem item) throws Exception {
		RuralNeedFinishInfo rnfiBef = maiVar.getNeedFinish();
		RuralNeedFinishInfo rnfItem = item.getNeedFinish();

		if (rnfItem.getNeedFinish1() < rnfiBef.getNeedFinish1()) {
			item.getNeedFinish().setNeedFinish1(0);
			if (maiVar.getAdminRurals() != null
					&& maiVar.getAdminRurals().size() > 0) {
				for (ModelAreaQuarterAdministrationRural mararBefVar : maiVar
						.getAdminRurals()) {
					if (mararBefVar.getNaturaRurals() != null
							&& mararBefVar.getNaturaRurals().size() > 0) {
						for (ModelAreaQuarterNaturalRural mqnrBefVar : mararBefVar
								.getNaturaRurals()) {
							RuralNeedFinishInfo nRBefNeedFinish = mqnrBefVar
									.getNewRural().getNeedFinish();
							RuralNeedFinishInfo mqNRBefNeedFinish = mqnrBefVar
									.getNeedFinish();
							if (mqNRBefNeedFinish.getNeedFinish1() == 2) {
								if (maiVar.getStatus() == QuarterReportStatus.AUDIT
										|| maiVar.getStatus() == QuarterReportStatus.FINISH) {
									nRBefNeedFinish.setNeedFinish1(1);
								} else {
									nRBefNeedFinish.setNeedFinish1(0);
								}
							} else {
								nRBefNeedFinish
										.setNeedFinish1(mqNRBefNeedFinish
												.getNeedFinish1());
							}

						}
					}

				}
			} else {
				throw new Exception(item.getModelArea().getName()
						+ item.getBatch().getQuarter().getAnnual() + "年第"
						+ (item.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表" + "出现9项指标小于"
						+ maiVar.getBatch().getQuarter().getAnnual() + "年"
						+ (maiVar.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表的情况但"
						+ maiVar.getBatch().getQuarter().getAnnual() + "年"
						+ (maiVar.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表却没有季度行政村错误！");
			}
		}

		if (rnfItem.getNeedFinish2() < rnfiBef.getNeedFinish2()) {
			item.getNeedFinish().setNeedFinish2(0);
			if (maiVar.getAdminRurals() != null
					&& maiVar.getAdminRurals().size() > 0) {
				for (ModelAreaQuarterAdministrationRural mararBefVar : maiVar
						.getAdminRurals()) {
					if (mararBefVar.getNaturaRurals() != null
							&& mararBefVar.getNaturaRurals().size() > 0) {
						for (ModelAreaQuarterNaturalRural mqnrBefVar : mararBefVar
								.getNaturaRurals()) {
							RuralNeedFinishInfo nRBefNeedFinish = mqnrBefVar
									.getNewRural().getNeedFinish();
							RuralNeedFinishInfo mqNRBefNeedFinish = mqnrBefVar
									.getNeedFinish();
							if (mqNRBefNeedFinish.getNeedFinish2() == 2) {

								if (maiVar.getStatus() == QuarterReportStatus.AUDIT
										|| maiVar.getStatus() == QuarterReportStatus.FINISH) {
									nRBefNeedFinish.setNeedFinish2(1);
								} else {
									nRBefNeedFinish.setNeedFinish2(0);
								}
							} else {
								nRBefNeedFinish
										.setNeedFinish2(mqNRBefNeedFinish
												.getNeedFinish2());
							}

						}
					}

				}
			} else {
				throw new Exception(item.getModelArea().getName()
						+ item.getBatch().getQuarter().getAnnual() + "年第"
						+ (item.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表" + "出现9项指标小于"
						+ maiVar.getBatch().getQuarter().getAnnual() + "年"
						+ (maiVar.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表的情况但"
						+ maiVar.getBatch().getQuarter().getAnnual() + "年"
						+ (maiVar.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表却没有季度行政村错误！");
			}
		}

		if (rnfItem.getNeedFinish3() < rnfiBef.getNeedFinish3()) {
			item.getNeedFinish().setNeedFinish3(0);
			if (maiVar.getAdminRurals() != null
					&& maiVar.getAdminRurals().size() > 0) {
				for (ModelAreaQuarterAdministrationRural mararBefVar : maiVar
						.getAdminRurals()) {
					if (mararBefVar.getNaturaRurals() != null
							&& mararBefVar.getNaturaRurals().size() > 0) {
						for (ModelAreaQuarterNaturalRural mqnrBefVar : mararBefVar
								.getNaturaRurals()) {
							RuralNeedFinishInfo nRBefNeedFinish = mqnrBefVar
									.getNewRural().getNeedFinish();
							RuralNeedFinishInfo mqNRBefNeedFinish = mqnrBefVar
									.getNeedFinish();
							if (mqNRBefNeedFinish.getNeedFinish3() == 2) {
								if (maiVar.getStatus() == QuarterReportStatus.AUDIT
										|| maiVar.getStatus() == QuarterReportStatus.FINISH) {
									nRBefNeedFinish.setNeedFinish3(1);
								} else {
									nRBefNeedFinish.setNeedFinish3(0);
								}

							} else {
								nRBefNeedFinish
										.setNeedFinish3(mqNRBefNeedFinish
												.getNeedFinish3());
							}

						}
					}

				}
			} else {
				throw new Exception(item.getModelArea().getName()
						+ item.getBatch().getQuarter().getAnnual() + "年第"
						+ (item.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表" + "出现9项指标小于"
						+ maiVar.getBatch().getQuarter().getAnnual() + "年"
						+ (maiVar.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表的情况但"
						+ maiVar.getBatch().getQuarter().getAnnual() + "年"
						+ (maiVar.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表却没有季度行政村错误！");
			}
		}

		if (rnfItem.getNeedFinish4() < rnfiBef.getNeedFinish4()) {
			item.getNeedFinish().setNeedFinish4(0);
			if (maiVar.getAdminRurals() != null
					&& maiVar.getAdminRurals().size() > 0) {
				for (ModelAreaQuarterAdministrationRural mararBefVar : maiVar
						.getAdminRurals()) {
					if (mararBefVar.getNaturaRurals() != null
							&& mararBefVar.getNaturaRurals().size() > 0) {
						for (ModelAreaQuarterNaturalRural mqnrBefVar : mararBefVar
								.getNaturaRurals()) {
							RuralNeedFinishInfo nRBefNeedFinish = mqnrBefVar
									.getNewRural().getNeedFinish();
							RuralNeedFinishInfo mqNRBefNeedFinish = mqnrBefVar
									.getNeedFinish();
							if (mqNRBefNeedFinish.getNeedFinish4() == 2) {
								if (maiVar.getStatus() == QuarterReportStatus.AUDIT
										|| maiVar.getStatus() == QuarterReportStatus.FINISH) {
									nRBefNeedFinish.setNeedFinish4(1);
								} else {
									nRBefNeedFinish.setNeedFinish4(0);
								}
							} else {
								nRBefNeedFinish
										.setNeedFinish4(mqNRBefNeedFinish
												.getNeedFinish4());
							}

						}
					}

				}
			} else {
				throw new Exception(item.getModelArea().getName()
						+ item.getBatch().getQuarter().getAnnual() + "年第"
						+ (item.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表" + "出现9项指标小于"
						+ maiVar.getBatch().getQuarter().getAnnual() + "年"
						+ (maiVar.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表的情况但"
						+ maiVar.getBatch().getQuarter().getAnnual() + "年"
						+ (maiVar.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表却没有季度行政村错误！");
			}
		}

		if (rnfItem.getNeedFinish5() < rnfiBef.getNeedFinish5()) {
			item.getNeedFinish().setNeedFinish5(0);
			if (maiVar.getAdminRurals() != null
					&& maiVar.getAdminRurals().size() > 0) {
				for (ModelAreaQuarterAdministrationRural mararBefVar : maiVar
						.getAdminRurals()) {
					if (mararBefVar.getNaturaRurals() != null
							&& mararBefVar.getNaturaRurals().size() > 0) {
						for (ModelAreaQuarterNaturalRural mqnrBefVar : mararBefVar
								.getNaturaRurals()) {
							RuralNeedFinishInfo nRBefNeedFinish = mqnrBefVar
									.getNewRural().getNeedFinish();
							RuralNeedFinishInfo mqNRBefNeedFinish = mqnrBefVar
									.getNeedFinish();
							// NeedFinish=2暂存状态的季报那本季度的设为0吧
							if (mqNRBefNeedFinish.getNeedFinish5() == 2) {
								if (maiVar.getStatus() == QuarterReportStatus.AUDIT
										|| maiVar.getStatus() == QuarterReportStatus.FINISH) {
									nRBefNeedFinish.setNeedFinish5(1);
								} else {
									nRBefNeedFinish.setNeedFinish5(0);
								}
							} else {
								nRBefNeedFinish
										.setNeedFinish5(mqNRBefNeedFinish
												.getNeedFinish5());
							}

						}
					}

				}
			} else {
				throw new Exception(item.getModelArea().getName()
						+ item.getBatch().getQuarter().getAnnual() + "年第"
						+ (item.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表" + "出现9项指标小于"
						+ maiVar.getBatch().getQuarter().getAnnual() + "年"
						+ (maiVar.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表的情况但"
						+ maiVar.getBatch().getQuarter().getAnnual() + "年"
						+ (maiVar.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表却没有季度行政村错误！");
			}
		}

		if (rnfItem.getNeedFinish6() < rnfiBef.getNeedFinish6()) {
			item.getNeedFinish().setNeedFinish6(0);
			if (maiVar.getAdminRurals() != null
					&& maiVar.getAdminRurals().size() > 0) {
				for (ModelAreaQuarterAdministrationRural mararBefVar : maiVar
						.getAdminRurals()) {
					if (mararBefVar.getNaturaRurals() != null
							&& mararBefVar.getNaturaRurals().size() > 0) {
						for (ModelAreaQuarterNaturalRural mqnrBefVar : mararBefVar
								.getNaturaRurals()) {
							RuralNeedFinishInfo nRBefNeedFinish = mqnrBefVar
									.getNewRural().getNeedFinish();
							RuralNeedFinishInfo mqNRBefNeedFinish = mqnrBefVar
									.getNeedFinish();
							if (mqNRBefNeedFinish.getNeedFinish6() == 2) {
								if (maiVar.getStatus() == QuarterReportStatus.AUDIT
										|| maiVar.getStatus() == QuarterReportStatus.FINISH) {
									nRBefNeedFinish.setNeedFinish6(1);
								} else {
									nRBefNeedFinish.setNeedFinish6(0);
								}
							} else {
								nRBefNeedFinish
										.setNeedFinish6(mqNRBefNeedFinish
												.getNeedFinish6());
							}

						}
					}

				}
			} else {
				throw new Exception(item.getModelArea().getName()
						+ item.getBatch().getQuarter().getAnnual() + "年第"
						+ (item.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表" + "出现9项指标小于"
						+ maiVar.getBatch().getQuarter().getAnnual() + "年"
						+ (maiVar.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表的情况但"
						+ maiVar.getBatch().getQuarter().getAnnual() + "年"
						+ (maiVar.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表却没有季度行政村错误！");
			}
		}

		if (rnfItem.getNeedFinish7() < rnfiBef.getNeedFinish7()) {
			item.getNeedFinish().setNeedFinish7(0);
			if (maiVar.getAdminRurals() != null
					&& maiVar.getAdminRurals().size() > 0) {
				for (ModelAreaQuarterAdministrationRural mararBefVar : maiVar
						.getAdminRurals()) {
					if (mararBefVar.getNaturaRurals() != null
							&& mararBefVar.getNaturaRurals().size() > 0) {
						for (ModelAreaQuarterNaturalRural mqnrBefVar : mararBefVar
								.getNaturaRurals()) {
							RuralNeedFinishInfo nRBefNeedFinish = mqnrBefVar
									.getNewRural().getNeedFinish();
							RuralNeedFinishInfo mqNRBefNeedFinish = mqnrBefVar
									.getNeedFinish();
							if (mqNRBefNeedFinish.getNeedFinish7() == 2) {
								if (maiVar.getStatus() == QuarterReportStatus.AUDIT
										|| maiVar.getStatus() == QuarterReportStatus.FINISH) {
									nRBefNeedFinish.setNeedFinish7(1);
								} else {
									nRBefNeedFinish.setNeedFinish7(0);
								}
							} else {
								nRBefNeedFinish
										.setNeedFinish7(mqNRBefNeedFinish
												.getNeedFinish7());
							}

						}
					}

				}
			} else {
				throw new Exception(item.getModelArea().getName()
						+ item.getBatch().getQuarter().getAnnual() + "年第"
						+ (item.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表" + "出现9项指标小于"
						+ maiVar.getBatch().getQuarter().getAnnual() + "年"
						+ (maiVar.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表的情况但"
						+ maiVar.getBatch().getQuarter().getAnnual() + "年"
						+ (maiVar.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表却没有季度行政村错误！");
			}
		}

		if (rnfItem.getNeedFinish8() < rnfiBef.getNeedFinish8()) {
			item.getNeedFinish().setNeedFinish8(0);
			if (maiVar.getAdminRurals() != null
					&& maiVar.getAdminRurals().size() > 0) {
				for (ModelAreaQuarterAdministrationRural mararBefVar : maiVar
						.getAdminRurals()) {
					if (mararBefVar.getNaturaRurals() != null
							&& mararBefVar.getNaturaRurals().size() > 0) {
						for (ModelAreaQuarterNaturalRural mqnrBefVar : mararBefVar
								.getNaturaRurals()) {
							RuralNeedFinishInfo nRBefNeedFinish = mqnrBefVar
									.getNewRural().getNeedFinish();
							RuralNeedFinishInfo mqNRBefNeedFinish = mqnrBefVar
									.getNeedFinish();

							if (mqNRBefNeedFinish.getNeedFinish8() == 2) {
								if (maiVar.getStatus() == QuarterReportStatus.AUDIT
										|| maiVar.getStatus() == QuarterReportStatus.FINISH) {
									nRBefNeedFinish.setNeedFinish8(1);
								} else {
									nRBefNeedFinish.setNeedFinish8(0);
								}
							} else {
								nRBefNeedFinish
										.setNeedFinish8(mqNRBefNeedFinish
												.getNeedFinish8());
							}
							// item.getNeedFinish().setNeedFinish8(
							// item.getNeedFinish().getNeedFinish8()
							// + nRBefNeedFinish.getNeedFinish8());
							// System.out.println("itemnF8最新值:"
							// + item.getNeedFinish().getNeedFinish8());
						}
					}

				}
			} else {
				throw new Exception(item.getModelArea().getName()
						+ item.getBatch().getQuarter().getAnnual() + "年第"
						+ (item.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表" + "出现9项指标小于"
						+ maiVar.getBatch().getQuarter().getAnnual() + "年"
						+ (maiVar.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表的情况但"
						+ maiVar.getBatch().getQuarter().getAnnual() + "年"
						+ (maiVar.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表却没有季度行政村错误！");
			}
		}

		if (rnfItem.getNeedFinish9() < rnfiBef.getNeedFinish9()) {
			item.getNeedFinish().setNeedFinish9(0);
			if (maiVar.getAdminRurals() != null
					&& maiVar.getAdminRurals().size() > 0) {
				for (ModelAreaQuarterAdministrationRural mararBefVar : maiVar
						.getAdminRurals()) {
					if (mararBefVar.getNaturaRurals() != null
							&& mararBefVar.getNaturaRurals().size() > 0) {
						for (ModelAreaQuarterNaturalRural mqnrBefVar : mararBefVar
								.getNaturaRurals()) {
							RuralNeedFinishInfo nRBefNeedFinish = mqnrBefVar
									.getNewRural().getNeedFinish();
							RuralNeedFinishInfo mqNRBefNeedFinish = mqnrBefVar
									.getNeedFinish();
							if (mqNRBefNeedFinish.getNeedFinish9() == 2) {
								if (maiVar.getStatus() == QuarterReportStatus.AUDIT
										|| maiVar.getStatus() == QuarterReportStatus.FINISH) {
									nRBefNeedFinish.setNeedFinish9(1);
								} else {
									nRBefNeedFinish.setNeedFinish9(0);
								}
							} else {
								nRBefNeedFinish
										.setNeedFinish9(mqNRBefNeedFinish
												.getNeedFinish9());
							}

						}
					}

				}
			} else {
				throw new Exception(item.getModelArea().getName()
						+ item.getBatch().getQuarter().getAnnual() + "年第"
						+ (item.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表" + "出现9项指标小于"
						+ maiVar.getBatch().getQuarter().getAnnual() + "年"
						+ (maiVar.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表的情况但"
						+ maiVar.getBatch().getQuarter().getAnnual() + "年"
						+ (maiVar.getBatch().getQuarter().getPeriod() + 1)
						+ "季度报表却没有季度行政村错误！");
			}
		}

	}

	@Override
	public List<ModelAreaQuarterItem> beforeQuarter(int nowAnnual,
			int nowPeriod, Long maId) {
		List<ModelAreaQuarterItem> l = __list(
				ModelAreaQuarterItem.class,
				"select x from ModelAreaQuarterItem as x where x.modelArea.id=? and x.batch.quarter.annual*10+x.batch.quarter.period<"
						+ nowAnnual
						* 10
						+ nowPeriod
						+ " order by x.batch.quarter.annual*10+x.batch.quarter.period desc",
				maId);
		for (ModelAreaQuarterItem lvar : l) {

			System.out.println(lvar.getBatch().getQuarter().getAnnual() + ""
					+ lvar.getBatch().getQuarter().getPeriod());
		}

		return l;
	}

	/**
	 * 刷一下本季报去掉上报存在季报自然村nf状态=2的情况
	 * 
	 * @param item
	 */
	public void reFlish(ModelAreaQuarterItem item) {

		RuralNeedFinishInfo manf = item.getNeedFinish();
		manf.clear();
		if (item.getAdminRurals() != null && item.getAdminRurals().size() > 0) {
			for (ModelAreaQuarterAdministrationRural ar : item.getAdminRurals()) {
				// AdministrationCoreRuralEntity are = ar.getAdminRural();
				RuralNeedFinishInfo arnf = ar.getNeedFinish();
				arnf.clear();
				for (ModelAreaQuarterNaturalRural qnr : ar.getNaturaRurals()) {
					// NewRuralEntity nre = qnr.getNewRural();
					RuralNeedFinishInfo nrnf = qnr.getNeedFinish();
					// 已经上报待审核和已经完成的进行检查下needFinish有无为2的情况
					if (item.getStatus() == QuarterReportStatus.AUDIT
							|| item.getStatus() == QuarterReportStatus.FINISH) {
						if (nrnf.getNeedFinish1() == 2) {
							nrnf.setNeedFinish1(1);
						}
						if (nrnf.getNeedFinish2() == 2) {
							nrnf.setNeedFinish2(1);
						}
						if (nrnf.getNeedFinish3() == 2) {
							nrnf.setNeedFinish3(1);
						}
						if (nrnf.getNeedFinish4() == 2) {
							nrnf.setNeedFinish4(1);
						}
						if (nrnf.getNeedFinish5() == 2) {
							nrnf.setNeedFinish5(1);
						}
						if (nrnf.getNeedFinish6() == 2) {
							nrnf.setNeedFinish6(1);
						}
						if (nrnf.getNeedFinish7() == 2) {
							nrnf.setNeedFinish7(1);
						}
						if (nrnf.getNeedFinish8() == 2) {
							nrnf.setNeedFinish8(1);
						}
						if (nrnf.getNeedFinish9() == 2) {
							nrnf.setNeedFinish9(1);
						}
					}
					if (nrnf.getNeedFinish1() == 1) {
						arnf.setNeedFinish1(arnf.getNeedFinish1() + 1);
					}
					if (nrnf.getNeedFinish2() == 1) {
						arnf.setNeedFinish2(arnf.getNeedFinish2() + 1);
					}
					if (nrnf.getNeedFinish3() == 1) {
						arnf.setNeedFinish3(arnf.getNeedFinish3() + 1);
					}
					if (nrnf.getNeedFinish4() == 1) {
						arnf.setNeedFinish4(arnf.getNeedFinish4() + 1);
					}
					if (nrnf.getNeedFinish5() == 1) {
						arnf.setNeedFinish5(arnf.getNeedFinish5() + 1);
					}
					if (nrnf.getNeedFinish6() == 1) {
						arnf.setNeedFinish6(arnf.getNeedFinish6() + 1);
					}
					if (nrnf.getNeedFinish7() == 1) {
						arnf.setNeedFinish7(arnf.getNeedFinish7() + 1);
					}
					if (nrnf.getNeedFinish8() == 1) {
						arnf.setNeedFinish8(arnf.getNeedFinish8() + 1);
					}
					if (nrnf.getNeedFinish9() == 1) {
						arnf.setNeedFinish9(arnf.getNeedFinish9() + 1);
					}
					entityManager.merge(qnr);
					// entityManager.merge(nre);
				}
				entityManager.flush();
				entityManager.merge(ar);
				manf.sum(arnf);// 统计9项需完成指标

			}
			entityManager.flush();
			entityManager.merge(item);
		}
	}

	@Override
	public void show9ParamByNoStart(List<ModelAreaQuarterItem> items) {
		// TODO Auto-generated method stub
		if (items != null && items.size() > 0) {
			for (ModelAreaQuarterItem item : items) {
				if (item.getStatus() == 0) {
					ModelAreaQuarterItem lastItem = null;
					for (ModelAreaQuarterItem lvar : beforeQuarter(item
							.getBatch().getQuarter().getAnnual(), item
							.getBatch().getQuarter().getPeriod(), item
							.getModelArea().getId())) {
						// 不拿未开始的季度
						if (lvar.getStatus() == 0) {
							continue;
						}
						// 判断拿到的是不是本季度前的季度
						if ((item.getBatch().getQuarter().getAnnual() * 10 + item
								.getBatch().getQuarter().getPeriod()) > (lvar
								.getBatch().getQuarter().getAnnual() * 10 + lvar
								.getBatch().getQuarter().getPeriod())) {
							System.out.println(lvar.getBatch().getQuarter()
									.getAnnual()
									+ ""
									+ lvar.getBatch().getQuarter().getPeriod());
							lastItem = lvar;
							break;
						}
					}
					if (lastItem != null) {
						System.out.println("item："
								+ item.getModelArea().getName());
						System.out.println("lastItem:"
								+ lastItem.getModelArea().getName());
						System.out
								.println("lastItemTime:"
										+ lastItem.getBatch().getQuarter()
												.getAnnual()
										+ "年"
										+ (lastItem.getBatch().getQuarter()
												.getPeriod() + 1) + "季度");
						lastItem.getNeedFinish().copyTo(item.getNeedFinish());
						item.setArFinishPlan(lastItem.getArFinishPlan());
						item.setStartProject(lastItem.getStartProject());
						item.setFinishProject(lastItem.getFinishProject());
						entityManager.flush();
						entityManager.merge(item);
					}
				}
			}
		}
	}

}
