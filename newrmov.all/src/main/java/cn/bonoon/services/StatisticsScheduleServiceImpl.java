package cn.bonoon.services;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.hql.internal.ast.util.PathHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.controllers.utils.ExcelModel;
import cn.bonoon.core.MaPercentInfo;
import cn.bonoon.core.ModelAreaStatisInfo;
import cn.bonoon.core.StatisticsScheduleService;
import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.entities.BaseRuralEntity;
import cn.bonoon.entities.InvestmentInfo;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.ModelAreaQuarterAdministrationRural;
import cn.bonoon.entities.ModelAreaQuarterBatch;
import cn.bonoon.entities.ModelAreaQuarterEntity;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.entities.ModelAreaQuarterNaturalRural;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.PeripheralRuralEntity;
import cn.bonoon.entities.ProjectEntity;
import cn.bonoon.entities.UnitEntity;
import cn.bonoon.entities.logs.HandleLogEntity;
import cn.bonoon.entities.logs.ModelAreaQuarterComparer;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class StatisticsScheduleServiceImpl extends
		AbstractService<ModelAreaQuarterEntity> implements
		StatisticsScheduleService {

	// private final static String batchs = "一二三";
	/*
	 * private static int indexOf(String batch){ if(null == batch ||
	 * batch.isEmpty()) return -1; return batchs.indexOf(batch); }
	 */
	private String unitPl = "select x from UnitEntity x where x.place.id=?";

	@Override
	@Transactional
	protected ModelAreaQuarterEntity __save(OperateEvent event,
			ModelAreaQuarterEntity entity) {
		/*
		 * 1.先判断这个年度的记录是否存在，如果存在，则不能再提交了 2.创建所有市级的上报记录
		 */
		int annual = entity.getAnnual();
		int period = entity.getPeriod();
		String ql = "select count(x) from ModelAreaQuarterEntity x where x.annual=? and x.period=?";
		if (__exsit(ql, annual, period)) {
			throw new RuntimeException("已经存在" + annual + "年的该季度的汇总记录！");
		}
		ql = "select x from ModelAreaEntity x where x.deleted=false";
		List<ModelAreaEntity> mas = __list(ModelAreaEntity.class, ql);
		int count = 0;
		super.__save(event, entity);
		ModelAreaQuarterBatch[] quarterBatch = new ModelAreaQuarterBatch[BatchHelper
				.length()];
		count = __parseMA(entity, mas, quarterBatch);
		int batchCount = 0; // 批次
		for (ModelAreaQuarterBatch qb : quarterBatch) {
			if (null != qb) {
				batchCount++;
				entityManager.merge(qb);
			}
		}
		entity.setMaCount(count);
		entity.setBatchCount(batchCount);
		entityManager.merge(entity);
		return entity;
	}

	private int __parseMA(ModelAreaQuarterEntity entity,
			List<ModelAreaEntity> mas, ModelAreaQuarterBatch[] quarterBatch) {
		int count = 0;
		for (ModelAreaEntity ma : mas) {
			String batchName = ma.getBatch();
			int ordinal = BatchHelper.indexOf(batchName);
			if (ordinal != -1) {
				ModelAreaQuarterBatch ssc = __getBatch(entity, batchName,
						quarterBatch, ordinal);

				ModelAreaQuarterItem item = new ModelAreaQuarterItem();
				item.setBatch(ssc);
				item.setModelArea(ma);
				item.setOrdinal(ma.getOrdinalModel());

				// city
				item.setCityId(ma.getCityId());
				item.setCityName(ma.getCityName());

				UnitEntity cityUntiy = __first(UnitEntity.class, unitPl,
						ma.getCityId());
				item.setCityUnit(cityUntiy);

				// TODO 资金使用情况
				item.setInvestment(ma.getInvestment().copy());
				// 统计
				count++;
				ssc.setMaCount(ssc.getMaCount() + 1);
				entityManager.persist(item);
			}
		}
		return count;
	}

	private ModelAreaQuarterBatch __getBatch(ModelAreaQuarterEntity entity,
			String batchName, ModelAreaQuarterBatch[] quarterBatch, int ordinal) {
		ModelAreaQuarterBatch ssc = quarterBatch[ordinal];
		if (null == ssc) {
			ssc = new ModelAreaQuarterBatch();
			ssc.setQuarter(entity);
			ssc.setBatchName(batchName);
			ssc.setOrdinal(ordinal);
			quarterBatch[ordinal] = ssc;
			entityManager.persist(ssc);
		}
		return ssc;
	}

	@Override
	@Transactional
	public void refreshItem(LogonUser user, Long id) {
		ModelAreaQuarterEntity entity = __get(id);
		String ql = "select x from ModelAreaEntity x where x.deleted=false";
		List<ModelAreaEntity> mas = __list(ModelAreaEntity.class, ql);

		ModelAreaQuarterBatch[] quarterBatch = new ModelAreaQuarterBatch[BatchHelper
				.length()];
		List<ModelAreaQuarterItem> needDeletes = new ArrayList<>();

		List<ModelAreaQuarterItem> needUpdates = new ArrayList<>();

		for (ModelAreaQuarterBatch qb : entity.getBatchs()) {
			String batch = qb.getBatchName();
			int ord = BatchHelper.indexOf(batch);
			if (ord != -1) {
				quarterBatch[ord] = qb;
				qb.setMaCount(0);
				for (ModelAreaQuarterItem it : qb.getItems()) {
					ModelAreaEntity maok = null;
					for (ModelAreaEntity ma : mas) {
						if (ma.getId().equals(it.getModelArea().getId())) {
							maok = ma;
							break;
						}
					}

					if (null != maok) {
						mas.remove(maok);
						needUpdates.add(it);
					} else {
						needDeletes.add(it);
					}
				}
			}
		}

		for (ModelAreaQuarterItem update : needUpdates) {
			ModelAreaQuarterBatch qb = update.getBatch();
			String oldBatch = qb.getBatchName();
			String newBatch = update.getModelArea().getBatch();

			if (oldBatch.equals(newBatch)) {
				qb.setMaCount(qb.getMaCount() + 1);
				continue;
			}

			int ordinal = BatchHelper.indexOf(newBatch);
			if (ordinal != -1) {
				ModelAreaQuarterBatch ssc = __getBatch(entity, newBatch,
						quarterBatch, ordinal);
				update.setBatch(ssc);
				entityManager.merge(update);
				ssc.setMaCount(ssc.getMaCount() + 1);

				// 记录工程进展刷新的操作,这里的批次对应不上记录一下修改
				HandleLogEntity hlog = new HandleLogEntity();
				hlog.currentUser(user);
				hlog.setTargetId(update.getId());
				hlog.setTargetType(ModelAreaQuarterItem.class.getName());
				hlog.setContent("刷新" + entity.getAnnual() + "年"
						+ getPeriod(entity.getPeriod()) + "-"
						+ update.getModelArea().getCounty() + "从第" + oldBatch
						+ "批次改成第" + newBatch + "批次");
				entityManager.persist(hlog);
			} else {
				needDeletes.add(update);
			}
		}

		__parseMA(entity, mas, quarterBatch); // 增加

		// 删除
		for (ModelAreaQuarterItem it : needDeletes) {
			for (ModelAreaQuarterAdministrationRural ral : it.getAdminRurals()) {
				for (ModelAreaQuarterNaturalRural ural : ral.getNaturaRurals()) {
					entityManager.remove(ural);
				}
				entityManager.remove(ral);
			}
			entityManager.remove(it);

			/**** 记录批次删除的记录 ***/
			HandleLogEntity hlog = new HandleLogEntity();
			hlog.currentUser(user);
			hlog.setTargetId(it.getId());
			hlog.setTargetType(ModelAreaQuarterItem.class.getName());
			hlog.setContent("刷新" + entity.getAnnual() + "年"
					+ getPeriod(entity.getPeriod()) + "-删除"
					+ it.getModelArea().getCounty() + "记录");
			entityManager.persist(hlog);
		}

		int batchCount = 0;
		int count = 0;
		for (ModelAreaQuarterBatch qb : quarterBatch) {
			if (null == qb)
				continue;
			if (qb.getMaCount() > 0) {
				count += qb.getMaCount();
				batchCount++;
				entityManager.merge(qb);
			} else {
				entityManager.remove(qb);
			}
		}
		entity.setMaCount(count);
		entity.setBatchCount(batchCount);
		entityManager.merge(entity);

	}

	@Override
	@Transactional
	public void finishEntity(LogonUser user, Long id) {
		// 结束
		ModelAreaQuarterEntity entity = __get(id);
		if (__exsit(
				"select count(x) from ModelAreaQuarterItem x where x.batch.quarter.id=? and x.status<>1 and x.disabled=false",
				id)) {
			throw new RuntimeException("需要所有非禁止片区都提交后才能结束！");
		}
		entity.setStatus(1);
		entityManager.merge(entity);

		/**** 记录工程进展结束的操作 ***/
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(entity.getId());
		hlog.setTargetType(ModelAreaQuarterEntity.class.getName());
		hlog.setContent("结束了" + entity.getAnnual() + "年"
				+ getPeriod(entity.getPeriod()) + "工程进展");
		entityManager.persist(hlog);
	}

	@Override
	public List<ModelAreaQuarterItem> itemSurveies(Long id) {
		String ql = "select x from ModelAreaQuarterItem x where x.batch.id=? order by x.modelArea.ordinalModel asc";
		return __list(ModelAreaQuarterItem.class, ql, id);
	}

	@Override
	public List<ModelAreaQuarterBatch> batchSurveies(Long pid) {
		String ql = "select x from ModelAreaQuarterBatch x where x.quarter.id=?";
		return __list(ModelAreaQuarterBatch.class, ql, pid);
	}

	@Override
	@Transactional
	public void urge(Long id, IOperator opt) {
		// 催报
		ModelAreaQuarterItem ssc = entityManager.find(
				ModelAreaQuarterItem.class, id);
		ssc.setUrge(ssc.getUrge() + 1);
		entityManager.merge(ssc);

		/**** 记录工程进展催报的操作 ***/
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.setTargetId(ssc.getId());
		hlog.currentUser(opt);
		hlog.setTargetType(ModelAreaQuarterItem.class.getName());
		hlog.setContent(ssc.getBatch().getQuarter().getAnnual() + "年第"
				+ getPeriod(ssc.getBatch().getQuarter().getPeriod())
				+ ssc.getModelArea().getCounty() + "催报工程进展");
		entityManager.persist(hlog);
	}

	@Override
	@Transactional
	public void back(Long id, IOperator opt) {
		// 退回
		ModelAreaQuarterItem ssc = entityManager.find(
				ModelAreaQuarterItem.class, id);
		ssc.setStatus(0);
		entityManager.merge(ssc);
		/**** 记录工程进展退回的操作 ***/
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.setTargetId(ssc.getId());
		hlog.currentUser(opt);
		hlog.setTargetType(ModelAreaQuarterItem.class.getName());
		hlog.setContent(ssc.getBatch().getQuarter().getAnnual() + "年第"
				+ getPeriod(ssc.getBatch().getQuarter().getPeriod())
				+ ssc.getModelArea().getCounty() + "退回工程进展");
		entityManager.persist(hlog);
	}

	@Override
	@Transactional
	public void ban(Long id, IOperator opt) {
		// 禁止上报
		ModelAreaQuarterItem ssc = entityManager.find(
				ModelAreaQuarterItem.class, id);
		ssc.setDisabled(true);
		entityManager.merge(ssc);
		/**** 记录工程进展禁止上报的操作 ***/
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.setTargetId(ssc.getId());
		hlog.currentUser(opt);
		hlog.setTargetType(ModelAreaQuarterItem.class.getName());
		hlog.setContent(ssc.getBatch().getQuarter().getAnnual() + "年第"
				+ getPeriod(ssc.getBatch().getQuarter().getPeriod())
				+ ssc.getModelArea().getCounty() + "禁止上报工程进展");
		entityManager.persist(hlog);
	}

	@Override
	@Transactional
	public void regain(Long id, IOperator opt) {
		// 恢复上报
		ModelAreaQuarterItem ssc = entityManager.find(
				ModelAreaQuarterItem.class, id);
		ssc.setDisabled(false);
		entityManager.merge(ssc);
		/**** 记录工程进展恢复上报的操作 ***/
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.setTargetId(ssc.getId());
		hlog.currentUser(opt);
		hlog.setTargetType(ModelAreaQuarterItem.class.getName());

		hlog.setContent(ssc.getBatch().getQuarter().getAnnual() + "年第"
				+ getPeriod(ssc.getBatch().getQuarter().getPeriod())
				+ ssc.getModelArea().getCounty() + "恢复上报工程进展");
		entityManager.persist(hlog);
	}

	@Override
	public List<ModelAreaQuarterEntity> get(IOperator user, int count) {
		String ql = "select x from ModelAreaQuarterEntity x where x.status=1 order by x.annual desc";
		return __top(count, ModelAreaQuarterEntity.class, ql);
	}

	// 锁定季度填报
	@Override
	@Transactional
	public void lockItem(LogonUser user, Long id, IOperator opt) {
		// 结束
		ModelAreaQuarterEntity entity = __get(id);
		boolean lockOrNot = entity.isLock();
		if (lockOrNot == false) {
			lockOrNot = true;
		} else {
			lockOrNot = false;
		}
		entity.setLock(lockOrNot);

		// 锁定片区数量
		// int ModelAreaQuarterCount = 0;

		List<ModelAreaQuarterBatch> batchs = entity.getBatchs();
		for (ModelAreaQuarterBatch batch : batchs) {
			List<ModelAreaQuarterItem> items = batch.getItems();
			for (ModelAreaQuarterItem item : items) {

				item.setLock(lockOrNot);
				// ModelAreaQuarterCount++;
			}
		}

		entityManager.merge(entity);
		/**** 记录工程进展锁定解锁的操作 ***/
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.setTargetId(entity.getId());
		hlog.currentUser(opt);
		hlog.setTargetType(ModelAreaQuarterEntity.class.getName());
		// hlog.setContent("工程进展情况"+(lockOrNot==true?"锁定":"解锁")+
		// ModelAreaQuarterCount+"个片区");
		hlog.setContent((lockOrNot == true ? "锁定" : "解锁") + entity.getAnnual()
				+ "年" + getPeriod(entity.getPeriod()) + "片区");
		entityManager.persist(hlog);

	}

	@Override
	@Transactional
	public void unlock(Long id) {
		ModelAreaQuarterItem ssc = entityManager.find(
				ModelAreaQuarterItem.class, id);
		ssc.setLock(false);
		entityManager.merge(ssc);

	}

	@Override
	public List<ModelAreaQuarterBatch> allBatchs() {
		String ql = "select x from ModelAreaQuarterBatch x";
		return __list(ModelAreaQuarterBatch.class, ql);
	}

	/**
	 * 导出主体村项目资金
	 */
	@Override
	public void exportCoreRuralPjInvest(HttpServletRequest request,
			HttpServletResponse response, List<ModelAreaStatisInfo> items) {
		String filePath = request.getSession().getServletContext()
				.getRealPath("/xls-templates/pj_invest_corerural.xls");
		// SimpleDateFormat simple=new SimpleDateFormat("yyyy年MM月dd日");
		ExcelModel em;
		try {
			em = new ExcelModel(filePath, response, "省级新农村示范片主体村建设进展情况统计表");
			em.bindCell("title", "省级新农村示范片主体村建设进展情况统计表");
			em.bindColumns(4, items.toArray());
			em.write();
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	/**
	 * 导出非主体村项目资金
	 */
	@Override
	public void exportUnCoreRuralPjInvest(HttpServletRequest request,
			HttpServletResponse response, List<ModelAreaStatisInfo> items) {
		String filePath = request.getSession().getServletContext()
				.getRealPath("/xls-templates/pj_invest_uncorerural.xls");
		ExcelModel em;
		try {
			em = new ExcelModel(filePath, response, "省级新农村示范片非主体村建设进展情况统计表");
			em.bindCell("title", "省级新农村示范片非主体村建设进展情况统计表");
			em.bindColumns(4, items.toArray());
			em.write();
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	/**
	 * 查询项目的资金投入情况
	 */
	@Override
	public ModelAreaStatisInfo statisProInvest(ModelAreaEntity ma,
			String batch, int ruralType, int year, int period, int status) {
		ModelAreaStatisInfo mInfo = new ModelAreaStatisInfo(ma);
		InvestmentInfo invs = new InvestmentInfo();
		InvestmentInfo coreInvs = new InvestmentInfo();
		InvestmentInfo uncoreInvs = new InvestmentInfo();
		int arr[] = { 3, 6, 9, 12 };
		String _and = " and 1=1 ";
		// String _batch =
		// "-1".equals(batch)?_and:" and x.project.modelArea.batch='"+batch+"'";
		// 如果没选季度的话，就是截止到12月的累计资金
		int da = -1 == period ? year * 100 + arr[3] : year * 100 + arr[period];
		String _status = -1 == status ? _and : " and x.project.status="
				+ status;
		String ql = "select x.id,x.project.rural,x.investment,x.project from ProjectReportItem x where  x.report.modelArea.id=? and x.report.status=1 and x.report.annual*100+x.report.period<? and x.project.deleted=false";
		ql += _status;
		// List<ProjectReportItem> items = __list(ProjectReportItem.class, ql,
		// ma.getId(),da);
		List<Object[]> items = __list(Object[].class, ql, ma.getId(), da);

		for (Object[] item : items) {
			BaseRuralEntity rural = (BaseRuralEntity) item[1];
			InvestmentInfo invest = (InvestmentInfo) item[2];
			if (rural instanceof NewRuralEntity) {
				coreInvs.sum(invest);
			} else if (rural instanceof PeripheralRuralEntity) {
				uncoreInvs.sum(invest);
			}
			invs.sum(invest);
		}

		int fp = 0, ucfp = 0, endp = 0;// 其中竣工项目数
		int stp = 0, ucstp = 0, ucendp = 0;// 已启动项目数
		ql = "select distinct x.project from ProjectReportItem x where  x.report.modelArea.id=? and x.report.status=1 and x.report.annual*100+x.report.period<? and x.project.deleted=false";
		List<ProjectEntity> pes = __list(ProjectEntity.class, ql, ma.getId(),
				da);
		for (ProjectEntity p : pes) {
			int proStatus = p.getStatus();
			if (p.getRural() instanceof NewRuralEntity) {
				if (proStatus == 2) {
					fp++;
				} else if (proStatus == 3) {
					endp++;
				}
				stp++;
			} else if (p.getRural() instanceof PeripheralRuralEntity) {
				if (proStatus == 2) {
					ucfp++;
				} else if (proStatus == 3) {
					ucendp++;
				}
				ucstp++;
			}
			if (status == proStatus) {// 某个项目状态下的资金情况
				if (p.getRural() instanceof NewRuralEntity) {
					coreInvs.sum(p.getSourceInvestment());
				} else if (p.getRural() instanceof PeripheralRuralEntity) {
					uncoreInvs.sum(p.getSourceInvestment());
				}
				invs.sum(p.getSourceInvestment());
			} else if (status == -1) {// 全部
				if (p.getRural() instanceof NewRuralEntity) {
					coreInvs.sum(p.getSourceInvestment());
				} else if (p.getRural() instanceof PeripheralRuralEntity) {
					uncoreInvs.sum(p.getSourceInvestment());
				}
				invs.sum(p.getSourceInvestment());
			}
		}
		switch (ruralType) {
		case 0:// 主体村
			mInfo.setInvest(coreInvs);
			mInfo.setFp(fp);
			mInfo.setStp(stp);
			mInfo.setEndp(endp);
			break;
		case 1:// 非主体村
			mInfo.setInvest(uncoreInvs);
			mInfo.setFp(ucfp);
			mInfo.setStp(ucstp);
			mInfo.setEndp(ucendp);
			break;
		case 2:// 主体村+非主体村
			mInfo.setInvest(invs);
			mInfo.setFp(ucfp + fp);
			mInfo.setStp(ucstp + stp);
			mInfo.setEndp(ucendp + endp);
			break;
		default:
			mInfo.setInvest(invs);
			mInfo.setFp(ucfp + fp);
			mInfo.setStp(ucstp + stp);
			mInfo.setEndp(ucendp + endp);
			break;
		}
		return mInfo;

	}

	@Override
	public void exportRuralPjInvest(HttpServletRequest request,
			HttpServletResponse response, List<ModelAreaStatisInfo> items,
			int ruralType) {
		String filePath = request.getSession().getServletContext()
				.getRealPath("/xls-templates/pj_invest.xls");
		ExcelModel em;
		try {
			String title = ruralType == 0 ? "省级新农村示范片建设进展情况统计表(主体村)"
					: ruralType == 1 ? "省级新农村示范片建设进展情况统计表(非主体村)"
							: "省级新农村示范片建设进展情况统计表(主体村+非主体村)";
			em = new ExcelModel(filePath, response, title);
			em.bindCell("title", title);
			em.bindColumns(4, items.toArray());
			em.write();
		} catch (Throwable t) {
			t.printStackTrace();
			throw new RuntimeException(t);
		}
	}

	@Override
	@Transactional
	public void refreshRural(LogonUser user, Long id) {
		ModelAreaQuarterEntity quarter = __get(id);
		/**** 记录工程进展刷新9个指标项的数据的操作 ***/
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(quarter.getId());
		hlog.setTargetType(ModelAreaQuarterEntity.class.getName());
		hlog.setContent("刷新" + quarter.getAnnual() + "年"
				+ getPeriod(quarter.getPeriod()) + "报表中9个指标项的数据");
		Boolean record = true;

		List<ModelAreaQuarterBatch> batchs = quarter.getBatchs();
		for (ModelAreaQuarterBatch batch : batchs) {
			List<ModelAreaQuarterItem> items = batch.getItems();
			for (ModelAreaQuarterItem item : items) {
				__setRuralNf(item, hlog, record);
			}
		}
	}

	private String getPeriod(int period) {
		String periodStr = "";
		if (period == 0) {
			periodStr = "第一季度";
		}
		if (period == 1) {
			periodStr = "第二季度";
		}
		if (period == 2) {
			periodStr = "第三季度";
		}
		if (period == 3) {
			periodStr = "第四季度";
		}
		return periodStr;
	}

	/*
	 * 原始村子的needfish 指标有两个值： 0：未完成（默认）
	 * 1：已完成（勾选）：那么已经勾选的村子，在下一个季度的时候就不应该再让用户勾选，应该灰掉那个input
	 * 提交并完成上报的时候就应该，处理好原始村子的那些指标。
	 */
	private void __setRuralNf(ModelAreaQuarterItem item, HandleLogEntity hlog,
			Boolean record) {
		for (ModelAreaQuarterAdministrationRural qar : item.getAdminRurals()) {
			AdministrationCoreRuralEntity are = qar.getAdminRural();// 同时要保存are的needfinish
			are.setArFinishPlan(NewRuralEntity.__checkSetNf(
					qar.getArFinishPlan(), are.getArFinishPlan()));
			for (ModelAreaQuarterNaturalRural qnr : qar.getNaturaRurals()) {
				NewRuralEntity nre = qnr.getNewRural();// 同时要保存nre的needfinish

				nre.parseNeedFinish(qnr.getNeedFinish());

				ModelAreaQuarterComparer ruralCom = new ModelAreaQuarterComparer();
				// 调整前的原数据
				nre.getNeedFinish()
						.copyTo(ruralCom.getRnfCompare().getSource());
				// 处理后的
				ruralCom.getRnfCompare().getTarget()
						.parseFinish(qnr.getNeedFinish());

				/**** 如果有发生改变则应该记录 ***/
				if (ruralCom.getRnfCompare().needAdjust()) {
					if (record == true) {
						entityManager.persist(hlog);
						record = false;
					}
					ruralCom.setLog(hlog);
					ruralCom.setMaid(nre.getModelArea().getId());
					ruralCom.setName("[自然村]" + nre.getName());
					ruralCom.setStatus(1);
					ruralCom.setTargetType(NewRuralEntity.class.getName());
					ruralCom.setTargetId(nre.getId());

					ruralCom.getRnfCompare().getTarget()
							.copyTo(nre.getNeedFinish());

					entityManager.persist(ruralCom);
				}
				entityManager.merge(nre);
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

			}
			entityManager.merge(are);
		}
	}

	// public int __checkSetNf(int value){
	// int result = 0;
	// if(value==0)result = 0;
	// if(value==1)result = 1;
	// if(value==2)result = 1;
	// return result;
	// }

	@Override
	public void exportNfPercent(HttpServletRequest request,
			HttpServletResponse response, Long id) {

		ModelAreaQuarterEntity quarter = __get(id);

		String bql = "select x from ModelAreaQuarterBatch x where x.quarter.id=? and x.batchName=?";
		ModelAreaQuarterBatch batch1 = __first(ModelAreaQuarterBatch.class,
				bql, id, "一");
		ModelAreaQuarterBatch batch2 = __first(ModelAreaQuarterBatch.class,
				bql, id, "二");

		List<MaPercentInfo> list1 = new ArrayList<>();// 一个片区有9个指标的完成比例，多个片区泽有多个。
		Integer all_nfedRuralNum1[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };// 第一批各指标的完成村塾
		String all_nfRuralpercent1[] = new String[9];// 第一批各指标的平均值

		List<MaPercentInfo> list2 = new ArrayList<>();// 一个片区有9个指标的完成比例，多个片区泽有多个。
		Integer all_nfedRuralNum2[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };// 第一批各指标的完成村数
		String all_nfRuralpercent2[] = new String[9];// 第二批各指标的平均值

		int all_nrNum1 = _setPercent(batch1, all_nfRuralpercent1, list1,
				all_nfedRuralNum1);
		int all_nrNum2 = _setPercent(batch2, all_nfRuralpercent2, list2,
				all_nfedRuralNum2);

		List<MaPercentInfo> miList = new ArrayList<>();
		MaPercentInfo mi = null;
		Map<Integer, MaPercentInfo> map = new HashMap<>();

		if (list1.size() > list2.size()) {
			for (MaPercentInfo m : list2) {
				map.put(m.getIndex(), m);
			}
			// boolean isEnd2=false;
			int la2row = 0;
			for (MaPercentInfo m : list1) {
				mi = new MaPercentInfo();
				MaPercentInfo ms = map.get(m.getIndex());
				mi.setName(m.getName());
				if (ms != null) {
					mi.setName2(ms.getName());
					mi.setOrder2(ms.getOrder());
					mi.setPercent2(ms.getPercent());
					mi.setNfedRuralNum2(ms.getNfedRuralNum());
					mi.setRuralNum2(ms.getRuralNum());
				} else /* if(isEnd2!=true) */{

					if (la2row == 0) {
						mi.setName("平均值：");
						mi.setPercent2(all_nfRuralpercent2);
					}
					if (la2row == 1) {
						mi.setName("总数：");
						mi.setRuralNum2(all_nrNum2);
						mi.setNfedRuralNum2(all_nfedRuralNum2);
					}
					la2row++;
					// mi.setName2("平均值：");
					// mi.setPercent2(all_nfRuralpercent2);
					// isEnd2=true;
				}
				mi.setOrder(m.getOrder());
				mi.setPercent(m.getPercent());
				mi.setRuralNum(m.getRuralNum());
				mi.setNfedRuralNum(m.getNfedRuralNum());
				miList.add(mi);
			}
			MaPercentInfo av = new MaPercentInfo();
			av.setPercent(all_nfRuralpercent1);
			av.setName("平均值：");
			miList.add(av);
			MaPercentInfo sm = new MaPercentInfo();
			sm.setName("总数：");
			sm.setRuralNum(all_nrNum1);
			sm.setNfedRuralNum(all_nfedRuralNum1);
		} else {
			for (MaPercentInfo m : list1) {
				map.put(m.getIndex(), m);
			}
			// boolean isEnd1=false;
			int la2row = 0;
			for (MaPercentInfo m : list2) {
				mi = new MaPercentInfo();
				MaPercentInfo ms = map.get(m.getIndex());
				mi.setName2(m.getName());
				if (ms != null) {
					mi.setName(ms.getName());
					mi.setOrder(ms.getOrder());
					mi.setPercent(ms.getPercent());
					mi.setNfedRuralNum(ms.getNfedRuralNum());
					mi.setRuralNum(ms.getRuralNum());
				} else /* if(isEnd1!=true) */{
					if (la2row == 0) {
						mi.setName("平均值：");
						mi.setPercent(all_nfRuralpercent1);
					}
					if (la2row == 1) {
						mi.setName("总数：");
						mi.setRuralNum(all_nrNum1);
						mi.setNfedRuralNum(all_nfedRuralNum1);
					}
					la2row++;
					// mi.setName("平均值：");
					// mi.setPercent(all_nfRuralpercent1);
					// isEnd1=true;
				}
				mi.setOrder2(m.getOrder());
				mi.setPercent2(m.getPercent());
				mi.setNfedRuralNum2(m.getNfedRuralNum());
				mi.setRuralNum2(m.getRuralNum());
				miList.add(mi);
			}
			MaPercentInfo av = new MaPercentInfo();
			av.setName2("平均值：");
			av.setPercent2(all_nfRuralpercent2);
			miList.add(av);
			MaPercentInfo sm = new MaPercentInfo();
			sm.setName2("总数：");
			sm.setRuralNum2(all_nrNum2);
			sm.setNfedRuralNum2(all_nfedRuralNum2);
			miList.add(sm);
		}
		ExcelModel em;
		String filePath = request.getSession().getServletContext()
				.getRealPath("/xls-templates/nf-percent.xls");
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			String deadline = sdf.format(quarter.getDeadline());
			String title = "第一、二批省级新农村示范片情况调查表(截止时间：" + deadline + ")";
			em = new ExcelModel(filePath, response, title);
			for (int i = 0; i < 8; i++) {
				if (i == 0)
					title = "第一、二批省级新农村示范片通自来水情况调查表(截止时间：" + deadline + ")";
				if (i == 1)
					title = "第一、二批省级新农村示范片卫生改厕情况调查表(截止时间：" + deadline + ")";
				if (i == 2)
					title = "第一、二批省级新农村示范片道路硬底化情况调查表(截止时间：" + deadline + ")";
				if (i == 3)
					title = "第一、二批省级新农村示范片外立面情况调查表(截止时间：" + deadline + ")";
				if (i == 4)
					title = "第一、二批省级新农村示范片保洁机制情况调查表(截止时间：" + deadline + ")";
				if (i == 5)
					title = "第一、二批省级新农村示范片污水处理情况调查表(截止时间：" + deadline + ")";
				if (i == 6)
					title = "第一、二批省级新农村示范片人畜分离情况调查表(截止时间：" + deadline + ")";
				if (i == 7)
					title = "第一、二批省级新农村示范片村规民约情况调查表(截止时间：" + deadline + ")";
				if (i == 0) {
					em.bindColumns(4, miList.toArray(), i);
				} else {
					em.bindColumns(5, miList.toArray(), i);
				}
				em.bindCell("title", title, i);
			}
			em.write();
		} catch (Throwable t) {
			t.printStackTrace();
			throw new RuntimeException(t);
		}

	}

	private int _setPercent(ModelAreaQuarterBatch batch1,
			String[] all_nfRuralpercent1, List<MaPercentInfo> list,
			Integer[] all_nfedRuralNum1) {
		int all_nrNum1 = 0, index = 0;
		DecimalFormat df = new DecimalFormat();// 格式化小数
		String ql12 = "select x from ModelAreaQuarterNaturalRural x where x.arQuarter.item.id=?";
		String qlruralNum = "select count(x) from NewRuralEntity x where x.modelArea.id=? and x.deleted=false";
		String qlitem = "select x from ModelAreaQuarterItem x where x.batch.id=? order by x.modelArea.ordinalModel asc";

		for (ModelAreaQuarterItem item : __list(ModelAreaQuarterItem.class,
				qlitem, batch1.getId())) {
			List<ModelAreaQuarterNaturalRural> ma_qnrs = __list(
					ModelAreaQuarterNaturalRural.class, ql12, item.getId());
			// int nrNum = ma_qnrs.size();//某一片区自然村数
			int nrNum = __first(Number.class, qlruralNum,
					item.getModelArea().getId()).intValue();
			all_nrNum1 += nrNum;
			Integer nfedRuralNum[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };// 某个片区各个指标的完成村数
			MaPercentInfo mp = new MaPercentInfo();
			String nfRuralpercent[] = new String[9];
			mp.setId(item.getModelArea().getId());
			mp.setName(item.getModelArea().getCityName()
					+ item.getModelArea().getCounty());
			mp.setOrder(item.getModelArea().getOrdinalModel());
			// mp.setBatch(item.getModelArea().getBatch());
			mp.setIndex(index);
			index++;
			for (ModelAreaQuarterNaturalRural qnr : ma_qnrs) {
				if (qnr.getNeedFinish().getNeedFinish2() > 0)
					nfedRuralNum[0]++;
				if (qnr.getNeedFinish().getNeedFinish3() > 0)
					nfedRuralNum[1]++;
				if (qnr.getNeedFinish().getNeedFinish4() > 0)
					nfedRuralNum[2]++;
				if (qnr.getNeedFinish().getNeedFinish5() > 0)
					nfedRuralNum[3]++;
				if (qnr.getNeedFinish().getNeedFinish6() > 0)
					nfedRuralNum[4]++;
				if (qnr.getNeedFinish().getNeedFinish7() > 0)
					nfedRuralNum[5]++;
				if (qnr.getNeedFinish().getNeedFinish8() > 0)
					nfedRuralNum[6]++;
				if (qnr.getNeedFinish().getNeedFinish9() > 0)
					nfedRuralNum[7]++;
			}
			for (int i = 0; i < 9; i++) {
				if (nrNum == 0) {
					nrNum = 1;
				}
				BigDecimal result = new BigDecimal(
						String.valueOf(nfedRuralNum[i])).divide(new BigDecimal(
						String.valueOf(nrNum)), 2, BigDecimal.ROUND_HALF_UP);
				result = result.multiply(new BigDecimal(100));
				nfRuralpercent[i] = df
						.format(Double.valueOf(result.toString())).toString()
						+ "%";
				// nfRuralpercent[i] = df.format(nfedRuralNum[i]/(float)nrNum);
				all_nfedRuralNum1[i] += nfedRuralNum[i];
			}
			mp.setPercent(nfRuralpercent);
			mp.setRuralNum(nrNum);
			mp.setNfedRuralNum(nfedRuralNum);
			list.add(mp);
		}
		for (int i = 0; i < 9; i++) {
			BigDecimal result = new BigDecimal(
					String.valueOf(all_nfedRuralNum1[i])).divide(
					new BigDecimal(String.valueOf(all_nrNum1)), 2,
					BigDecimal.ROUND_HALF_UP);
			result = result.multiply(new BigDecimal(100));
			all_nfRuralpercent1[i] = df.format(
					Double.valueOf(result.toString())).toString()
					+ "%";
			// all_nfRuralpercent1[i] =
			// df.format(all_nfedRuralNum1[i]/(float)all_nrNum1);
		}
		return all_nrNum1;
	}

	@Override
	public String upadateBatch(Long itemId, int batch, Long quarterId,
			IOperator opt) {
		String hql = "from ModelAreaQuarterItem ";
		ModelAreaQuarterItem maqi = null;
		List<ModelAreaQuarterItem> list = __list(ModelAreaQuarterItem.class,
				hql);
		for (ModelAreaQuarterItem it : list) {
			if (itemId.equals(it.getId())) {
				maqi = it;
			}

		}
		String oldBatch;
		if (maqi != null) {
			oldBatch = maqi.getBatch().getBatchName();
			oldBatch = oldBatch + " ";
		} else {
			return null;

		}
		hql = "from ModelAreaQuarterBatch as m where m.batchName='"
				+ BatchHelper.getValue(batch) + "' and m.quarter.id="
				+ quarterId;
		ModelAreaQuarterBatch maqb = __first(ModelAreaQuarterBatch.class, hql);

		__exec("update from ModelAreaQuarterItem as m set m.batch=? where m.id=?",
				maqb, itemId);
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.setTargetId(maqb.getId());
		hlog.currentUser(opt);
		hlog.setTargetType(ModelAreaQuarterBatch.class.getName());
		hlog.setContent(maqi.getBatch().getQuarter().getAnnual() + "年"
				+ getPeriod(maqi.getBatch().getQuarter().getPeriod())
				+ maqi.getModelArea().getCounty() + "从第"
				+ maqi.getBatch().getBatchName() + "批次改成第"
				+ BatchHelper.getValue(batch) + "批次");
		entityManager.persist(hlog);
		return maqi.getModelArea().getName() + "从批次： [" + oldBatch
				+ "] 修改为 批次： [" + maqb.getBatchName() + "]";
	}

	@Override
	public List<Object[]> periodSet() {
		 return __list(Object[].class,
				"select x.annual,x.period from ModelAreaQuarterEntity as x order by  x.annual*10+x.period desc");
		
	}

}
