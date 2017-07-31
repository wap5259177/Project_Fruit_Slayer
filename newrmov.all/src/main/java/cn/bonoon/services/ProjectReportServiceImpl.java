package cn.bonoon.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.ParamenterValue;
import cn.bonoon.core.ProjectReportService;
import cn.bonoon.core.configs.ProjectConfig;
import cn.bonoon.core.json.ProjectReportItemBean;
import cn.bonoon.core.json.ProjectReportSaveInfo;
import cn.bonoon.entities.InvestmentInfo;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.ProjectEntity;
import cn.bonoon.entities.ProjectReportEntity;
import cn.bonoon.entities.ProjectReportItem;
import cn.bonoon.entities.ProjectReportType;
import cn.bonoon.entities.logs.HandleLogEntity;
import cn.bonoon.entities.logs.InvestmentComparer;
import cn.bonoon.entities.logs.ProRepItemComparerEntity;
import cn.bonoon.entities.logs.projectcomparer.ProjectComparer;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.AbstractService;
import cn.bonoon.kernel.web.ConfigManager;
import cn.bonoon.util.DoubleHelper;
import cn.bonoon.util.MonthAndQuarterUtil;

@Service
@Transactional(readOnly = true)
public class ProjectReportServiceImpl extends
		AbstractService<ProjectReportEntity> implements ProjectReportService {

	private ConfigManager configManager;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		configManager = ConfigManager.getManager();
	}

	protected ProjectConfig __config() {
		ProjectConfig config = new ProjectConfig();
		try {
			configManager.read(applicationContext, entityManager, config);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return config;
	}

	@Override
	public List<ProjectReportEntity> reports(IOperator user, int year) {
		String ql = "select x from ProjectReportEntity x where x.modelArea.ownerId=? and x.annual=?";
		return __list(ProjectReportEntity.class, ql, user.getOwnerId(), year);
	}

	@Override
	@Transactional
	public ProjectReportEntity start(IOperator user, int year, int month) {
		Long oid = user.getOwnerId();
		String ql0 = "select x from ProjectReportEntity x where x.modelArea.ownerId=? and x.annual=? and x.period=?";
		ProjectReportEntity report = __first(ProjectReportEntity.class, ql0,
				oid, year, month);
		if (null != report) {
			__parseProjectReport(user, report);
			return report;
		}

		String ql1 = "select x from ModelAreaEntity x where x.ownerId=? and x.deleted=false";
		ModelAreaEntity mae = __first(ModelAreaEntity.class, ql1, oid);
		if (null == mae)
			return null;

		int da = year * 100 + month;
		String ql = "select x from ProjectEntity x where x.status=1 and x.deleted=false and x.ownerId=? and x.modelArea.id=? and x.startAtY*100+x.startAtM<=?";
		List<ProjectEntity> pes = __list(ProjectEntity.class, ql,
				user.getOwnerId(), mae.getId(), da);
		if (pes.isEmpty())
			return null;
		/**
		 * 新建了月度报表
		 */
		report = new ProjectReportEntity();
		report.setAnnual(year);
		report.setPeriod(month);
		report.setModelArea(mae);
		report.setStatus(0);
		report.setTotalCount(pes.size());
		report.setType(ProjectReportType.MONTHLY);
		entityManager.persist(report);

		List<ProjectReportItem> pres = new ArrayList<>();
		/**
		 * 这里对该片区下的所有项目进行创建该月月度报表月报
		 * 需要注意的是如果你有需要新建的项目在本月月度报表要有月报如果先创建了月度报表则不会有该项目本月月报了,如下代码体现
		 */
		for (ProjectEntity pe : pes) {
			ProjectReportItem pr = new ProjectReportItem();
			pr.setProject(pe);
			pr.setReport(report);
			entityManager.persist(pr);
			pres.add(pr);
		}
		report.setReports(pres);
		return report;
	}

	private ProjectReportEntity __parseProjectReport(IOperator user,
			ProjectReportEntity pre) {
		if (pre.getStatus() == 0 && pre.getBack() != Integer.valueOf(1)) {
			int da = pre.getAnnual() * 100 + pre.getPeriod();
			String ql = "select x from ProjectEntity x where x.status=1 and x.deleted=false and x.ownerId=? and x.modelArea.id=? and x.startAtY*100+x.startAtM<=?";
			List<ProjectEntity> pes = __list(ProjectEntity.class, ql,
					user.getOwnerId(), pre.getModelArea().getId(), da);
			if (pes.isEmpty()) {
				// return null;
				// String ql2 =
				// "select x from ProjectEntity x where x.status in(2,3) and x.ownerId=? and x.modelArea.id=? and x.startAtY*100+x.startAtM<=?";
				// List<ProjectEntity> pey = __list(ProjectEntity.class, ql2,
				// user.getOwnerId(), pre.getModelArea().getId(), da);
				List<ProjectReportItem> items = new ArrayList<>(
						pre.getReports());
				String ql0 = "select x from ProjectReportEntity x where x.modelArea.ownerId=? and x.annual=? and x.period=?";
				List<ProjectReportEntity> pe = __list(
						ProjectReportEntity.class, ql0, user.getOwnerId(),
						pre.getAnnual() * 100, pre.getPeriod());
				for (ProjectReportItem it : items) {
					entityManager.remove(it);
					pre.getReports().remove(it);
					pe.remove(it);
				}
			}
			List<ProjectReportItem> items = new ArrayList<>(pre.getReports());
			for (int i = items.size() - 1; i >= 0; i--) {
				if (pes.remove(items.get(i).getProject())) {
					items.remove(i);
				}
			}

			for (ProjectReportItem it : items) {
				entityManager.remove(it);
				pre.getReports().remove(it);
			}

			for (ProjectEntity pe : pes) {
				ProjectReportItem pr = new ProjectReportItem();
				pr.setProject(pe);
				pr.setReport(pre);
				entityManager.persist(pr);
				pre.getReports().add(pr);
			}
		}
		return pre;
	}

	@Override
	@Transactional
	public ProjectReportEntity report(IOperator user, Long id) {
		String ql = "select x from ProjectReportEntity x where x.id=? and x.modelArea.ownerId=?";
		ProjectReportEntity pre = __first(ProjectReportEntity.class, ql, id,
				user.getOwnerId());
		if (null != pre) {
			// return __parseProjectReport(user, pre);
			return pre;
		}
		return null;
	}

	private void __read(ProjectReportItem pr, ParamenterValue pv) {
		InvestmentInfo ivn = pr.getInvestment();
		if (null == ivn) {
			ivn = new InvestmentInfo();
			pr.setInvestment(ivn);
		}
		Long pid = pr.getId();
		ivn.setStateFunds(pv.getDouble(pid, "st-"));
		ivn.setProvinceFunds(pv.getDouble(pid, "pr-"));
		ivn.setSpecialFunds(pv.getDouble(pid, "sp-"));
		// ivn.setLocalFunds(pv.getDouble(pid, "lo-"));
		ivn.setSocialFunds(pv.getDouble(pid, "so-"));
		ivn.setMassFunds(pv.getDouble(pid, "ma-"));
		ivn.setOtherFunds(pv.getDouble(pid, "ot-"));
		ivn.setCityFunds(pv.getDouble(pid, "ci-"));
		ivn.setCountyFunds(pv.getDouble(pid, "co-"));

		ivn.setTotalFunds(pv.getDouble(pid, "to-"));

		// 还是自己重新统计一下吧
		ivn.sumSelf();

		/*
		 * if(pv.getInt(pid, "fi-") == 2){ ProjectEntity pro = pr.getProject();
		 * //System.err.println(pv.getString(pid, "fi-remark-"));
		 * pro.setEndRemark(pv.getString(pid, "fi-remark-"));
		 * entityManager.merge(pro); }
		 */
		pr.setProjectStatus(pv.getInt(pid, "fi-"));
		pr.setLabourCount(pv.getInt(pid, "lc-"));
		pr.setSpend(pv.getDouble(pid, "sd-"));
		pr.setExitReason(pv.getString(pid, "er-"));
		// ivn.sumSelf();
		// return ivn;
	}

	@Override
	@Transactional
	public void save(IOperator ope, Long id, ParamenterValue pv, boolean submit) {
		ProjectReportEntity report = entityManager.find(
				ProjectReportEntity.class, id);
		if (null == report)
			throw new RuntimeException("数据操作异常！");
		for (ProjectReportItem pr : report.getReports()) {
			__read(pr, pv);
			entityManager.merge(pr);
		}
		if (submit) {
			__submit(report, null, null);
		} else {
			// 暂存：report 也要保存项目的投入资金情况,用于暂存后回来的显示
			_saveReportInvest(report);
		}
	}

	private void _saveReportInvest(ProjectReportEntity report) {
		InvestmentInfo rivn = report.getInvestment();
		if (null == rivn) {
			rivn = new InvestmentInfo();
			report.setInvestment(rivn);
		} else {
			rivn.clear();
		}
		int lc = 0;
		double sd = 0;
		for (ProjectReportItem pr : report.getReports()) {
			InvestmentInfo ivn = pr.getInvestment();
			lc += pr.getLabourCount();
			sd = DoubleHelper.add(sd, pr.getSpend());

			rivn.sum(ivn);
		}
		report.setLabourCount(lc);
		report.setSpend(sd);
		report.setInvestment(rivn);
		entityManager.merge(report);
	}

	private String __checkCanFinish(boolean needCheck, ProjectEntity pro,
			String type) {
		if (!needCheck)
			return "";
		if (null != pro.getModelArea()) {
			String ql1 = "select x.directoryMedia.id from ProjectEntity x where x.id=?";
			Long did = __first(Long.class, ql1, pro.getId());
			String ql = "select count(x) from FileEntity x left join x.directory y where x.deleted=false and x.extattr7=? and y.id=?";

			if (__exsit(ql, type, did)) {
				return "";
			}
		}
		// String code = pro.getCode();
		// String name = pro.getName();
		// return "[编号为：" +code+"，名称："+name+"--"+ type + "]\n";
		return "[" + type + "]";
	}

	@Override
	public List<?> getAllLocalFunds(String batch, boolean calcul) {
		Calendar cal = Calendar.getInstance();
		int range = cal.get(Calendar.YEAR) * 100 + cal.get(Calendar.MONTH);
		int lastRange = (cal.get(Calendar.YEAR) - 1) * 100
				+ cal.get(Calendar.MONTH);
		String sql = "SELECT m.C_NAME, pr.C_SPECIALFUNDS, C_ANNUAL, pr.C_PERIOD, C_ANNUAL * 100 + C_PERIOD as temp"
				+ " from t_projectreport pr, t_modelarea m"
				+ " where pr.R_MODELAREA_ID = m.C_ID and m.C_BATCH=? and C_ANNUAL * 100 + C_PERIOD <=? and C_ANNUAL * 100 + C_PERIOD >?"
				+ " ORDER BY m.C_NAME, temp";
		return entityManager.createNativeQuery(sql).setParameter(1, batch)
				.setParameter(2, range).setParameter(3, lastRange)
				.getResultList();
	}

	@Override
	public List<Object[]> getFunds(String batch, int startyear, int startMonth,
			int lastyear, int lastmonth) {
		String ql = "select x.modelArea.cityId,x.modelArea.name,x.annual,x.period,x.investment.specialFunds from ProjectReportEntity x where x.annual*100+x.period between ? and ? and x.modelArea.batch=?";
		return __list(Object[].class, ql, startyear * 100 + startMonth,
				lastyear * 100 + lastmonth, batch);
	}

	@Override
	public int[] lastMonthly(String batch) {
		String ql = "select x.annual,x.period from ProjectReportEntity x where x.modelArea.batch=? order by x.annual desc, x.period desc";
		Object[] ds = __first(Object[].class, ql, batch);
		if (null == ds)
			return null;
		return new int[] { (int) ds[0], (int) ds[1] };
	}

	@Override
	public int[] lastMonthly(Long id) {
		String ql = "select x.annual,x.period from ProjectReportEntity x where x.modelArea.ownerId=? order by x.annual desc, x.period desc";
		Object[] ds = __first(Object[].class, ql, id);
		if (null == ds)
			return null;
		return new int[] { (int) ds[0], (int) ds[1] };
	}

	@Override
	public List<Object[]> getFundsByModelId(Long id, int startyear,
			int startMonth, int lastyear, int lastmonth) {
		String ql = "select x.modelArea.cityId,x.modelArea.name,x.annual,x.period,x.investment.specialFunds from ProjectReportEntity x where x.annual*100+x.period between ? and ? and x.modelArea.id=?";
		return __list(Object[].class, ql, startyear * 100 + startMonth,
				lastyear * 100 + lastmonth, id);
	}

	@Override
	public List<Object[]> getInvestment(double schedule, String batch) {
		String ql = "select x.modelArea.cityId,x.modelArea.name,"
				+ "sum(x.totalInvestment.totalFunds),"
				+ "sum(x.investment.totalFunds)"
				+ " from ProjectEntity x where x.modelArea.batch=? group by x.modelArea.name";
		return __list(Object[].class, ql, batch);

	}

	// 计划
	@Override
	public List<Object[]> getAllKindsOfFoundTotal(Long ownerid) {
		String ql = "select "
				+ "sum(x.investment.totalFunds),"
				+ "sum(x.investment.stateFunds),"
				+ "sum(x.investment.provinceFunds),"
				+ "sum(x.investment.localFunds),"
				+ "sum(x.investment.specialFunds),"
				+ "sum(x.investment.socialFunds),"
				+ "sum(x.investment.otherFunds),"
				+ "sum(x.investment.cityFunds),"
				+ "sum(x.investment.countyFunds),"
				+ "sum(x.investment.massFunds) from ProjectEntity x where x.ownerId=?";
		return __list(Object[].class, ql, ownerid);
		// return __first(Object[].class, ql, ownerid);
	}

	// 累计完成的
	@Override
	public List<Object[]> getFinishedAllKindsOfFoundTotal(Long ownerId) {
		String ql = "select "
				+ "sum(x.totalInvestment.totalFunds),"
				+ "sum(x.totalInvestment.stateFunds),"
				+ "sum(x.totalInvestment.provinceFunds),"
				+ "sum(x.totalInvestment.localFunds),"
				+ "sum(x.totalInvestment.specialFunds),"
				+ "sum(x.totalInvestment.socialFunds),"
				+ "sum(x.totalInvestment.otherFunds),"
				+ "sum(x.totalInvestment.cityFunds),"
				+ "sum(x.totalInvestment.countyFunds),"
				+ "sum(x.totalInvestment.massFunds) from ProjectEntity x where x.ownerId=?";
		return __list(Object[].class, ql, ownerId);
	}

	// --------------市级的资金使用报表
	@Override
	public List<Object[]> getCityPredictFundTotal(Long ownerId, String batch) {
		String ql = "select x.place.id from UnitEntity x where x.id=?";
		Long pid = __first(Long.class, ql, ownerId);

		String q2 = "select "
				+ "sum(x.investment.totalFunds),"
				+ "sum(x.investment.stateFunds),"
				+ "sum(x.investment.provinceFunds),"
				+ "sum(x.investment.localFunds),"
				+ "sum(x.investment.specialFunds),"
				+ "sum(x.investment.socialFunds),"
				+ "sum(x.investment.otherFunds),"
				+ "sum(x.investment.cityFunds),"
				+ "sum(x.investment.countyFunds),"
				+ "sum(x.investment.massFunds) from ProjectEntity x where x.modelArea.cityId=? and x.modelArea.batch=?";
		return __list(Object[].class, q2, pid, batch);
	}

	@Override
	public List<Object[]> getCityFinishedFundTotal(Long ownerId, String batch) {
		String ql = "select x.place.id from UnitEntity x where x.id=?";
		Long pid = __first(Long.class, ql, ownerId);
		String q2 = "select "
				+ "sum(x.totalInvestment.totalFunds),"
				+ "sum(x.totalInvestment.stateFunds),"
				+ "sum(x.totalInvestment.provinceFunds),"
				+ "sum(x.totalInvestment.localFunds),"
				+ "sum(x.totalInvestment.specialFunds),"
				+ "sum(x.totalInvestment.socialFunds),"
				+ "sum(x.totalInvestment.otherFunds),"
				+ "sum(x.totalInvestment.cityFunds),"
				+ "sum(x.totalInvestment.countyFunds),"
				+ "sum(x.totalInvestment.massFunds) from ProjectEntity x where x.modelArea.cityId=? and x.modelArea.batch=?";
		return __list(Object[].class, q2, pid, batch);
	}

	@Override
	public List<ProjectReportEntity> getFunds(Long ownerId, int startyear,
			int startMonth, int lastyear, int lastmonth) {
		// String sql =
		// "select x.modelArea.cityId,x.modelArea.name,x.id,x.annual,x.period,"
		// +
		// "x.investment.stateFunds " +
		// "x.investment.provinceFunds " +
		// "x.investment.specialFunds " +
		// "x.investment.massFunds " +
		// "x.investment.otherFunds " +
		// "x.investment.cityFunds " +
		// "x.investment.countyFunds " +
		// "x.investment.socialFunds " +
		// "from ProjectReportEntity x where x.annual*100+x.period = ? ,x.annual*100+x.period between ? and ?   and x.ownerId=?";
		// return null;
		String ql = "select x from ProjectReportEntity x where x.annual*100+x.period between ? and ? and x.modelArea.ownerId=?";
		return __list(ProjectReportEntity.class, ql, startyear * 100
				+ startMonth, lastyear * 100 + lastmonth, ownerId);

	}

	@Override
	public List<ProjectReportEntity> getCityFundsUsed(Long ownerId,
			String batch, int startyear, int startMonth, int lastyear,
			int lastmonth) {
		String ql1 = "select x.place.id from UnitEntity x where x.id=?";
		Long pid = __first(Long.class, ql1, ownerId);
		String ql = "select x from ProjectReportEntity x where x.annual*100+x.period between ? and ? and x.modelArea.cityId=? and x.modelArea.batch=?";
		return __list(ProjectReportEntity.class, ql, startyear * 100
				+ startMonth, lastyear * 100 + lastmonth, pid, batch);

	}

	@Override
	public List<ProjectReportEntity> getRepot(Long ownerId) {
		//
		String ql = "select x from ProjectReportEntity x where x.modelArea.ownerId=? and x.status=1";
		return __list(ProjectReportEntity.class, ql, ownerId);
	}

	// 月度上报情况统计
	@Override
	public Object[] getReportSummary(ModelAreaEntity modelAreaEntity) {
		//
		String ql = "select "
				+ "sum(x.investment.totalFunds),"
				+ "sum(x.investment.stateFunds),"
				+ "sum(x.investment.specialFunds),"
				+ "sum(x.investment.provinceFunds),"
				+

				"sum(x.investment.cityFunds),"
				+ "sum(x.investment.countyFunds),"
				+ "sum(x.investment.socialFunds),"
				+ "sum(x.investment.massFunds),"
				+ "sum(x.investment.otherFunds)"
				+ " from ProjectReportEntity x where x.modelArea.id=? and x.status=1 ";
		return __first(Object[].class, ql, modelAreaEntity.getId());

	}

	// 累计截止到当月的月报资金
	@Override
	public List<ProjectReportEntity> getSumRepot(
			ModelAreaEntity modelAreaEntity, int year, int month) {
		//
		String ql = "select x from ProjectReportEntity x where x.modelArea.id=? and x.annual*100+x.period<=? and x.status=1";
		return __list(ProjectReportEntity.class, ql, modelAreaEntity.getId(),
				year * 100 + month);
	}

	// 当前月月报累计
	@Override
	public ProjectReportEntity getCurrent(ModelAreaEntity modelAreaEntity,
			int year, int month) {
		//
		String ql = "select x from ProjectReportEntity x where x.modelArea.id=? and x.annual=? and x.period=? and x.status=1";
		return __first(ProjectReportEntity.class, ql, modelAreaEntity.getId(),
				year, month);

	}

	/**
	 * @author wsf 修补了退回月报片区的 资金,斥资额 投工数 项目资金 斥资额 投工数 季度报表主体村的项目资金 斥资额投工数
	 *         没有减去的情况
	 */

	@Override
	@Transactional
	public void back(Long id, LogonUser user) {
		ProjectReportEntity pro = __get(id);
		pro.setBackAt(new Date());
		pro.setStatus(0);
		pro.setBack(1);
		/**
		 * 需要把所有的统计退回去
		 * 
		 */

		ModelAreaEntity ma = pro.getModelArea();
		if (ma != null) {
			if (ma.getInvestment() != null) {
				if (pro.getInvestment() != null) {
					ma.getInvestment().subtractTo(pro.getInvestment());
				} else {
					pro.setInvestment(new InvestmentInfo());
				}

			} else {
				ma.setInvestment(new InvestmentInfo());
			}
		}
		for (ProjectReportItem prItem : pro.getReports()) {
			if (prItem != null) {
				ProjectEntity pe = prItem.getProject();
				if (pe != null) {
					if (pe.getTotalInvestment() == null) {
						pe.setTotalInvestment(new InvestmentInfo());
					} else {
						if (prItem.getInvestment() == null)
							prItem.setInvestment(new InvestmentInfo());
						pe.getTotalInvestment().subtractTo(
								prItem.getInvestment());
					}

					if (pe.getSumSpend() == null) {
						pe.setSumSpend(new Double(0));
					} else {
						pe.setSumSpend(DoubleHelper.subtract(pe.getSumSpend(),
								prItem.getSpend()));
					}

					if (pe.getSumLabourCount() == null) {
						pe.setSumLabourCount(new Integer(0));
					} else {
						pe.setSumLabourCount(pe.getSumLabourCount()
								- prItem.getLabourCount());
					}

					entityManager.merge(pe);
				}
			}
		}
		entityManager.merge(pro);
		entityManager.merge(ma);
		/**
		 * 再刷下季报
		 */
		flashQuarterReport(pro);

		// 记录项目月报管理退回的操作
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(pro.getId());
		hlog.setTargetType(ProjectReportEntity.class.getName());
		hlog.setContent("退回了" + pro.getModelArea().getCounty()
				+ pro.getAnnual() + "年" + (pro.getPeriod() + 1) + "月项目月报");
		entityManager.persist(hlog);

	}

	@Override
	@Transactional
	public void capital(Long id, IOperator user) {

		ProjectReportEntity pr = __get(id);
		if (pr == null)
			throw new RuntimeException("该月度没有项目月报可上报");

		InvestmentInfo in = pr.getInvestment();

		// 创建需要比较项目月报资金比较的实体
		ProjectComparer proCom = new ProjectComparer();
		// 创建之前项目月报报告资金实体
		InvestmentInfo old_in = new InvestmentInfo();
		// 创建之前项目月报报告资金实体
		InvestmentInfo new_in = new InvestmentInfo();

		if (null == in) {
			in = new InvestmentInfo();
			// 得到原来项目报告资金的记录
			in.copy(old_in);
			proCom.getSource().setInfo(old_in);
		} else {
			// 得到原来项目报告资金的记录
			in.copy(old_in);
			proCom.getSource().setInfo(old_in);
			in.clear();
		}
		proCom.getSource().setLabourCount(pr.getLabourCount());
		proCom.getSource().setSpend(pr.getSpend());

		int lc = 0;
		double spend = 0D;
		for (ProjectReportItem it : pr.getReports()) {
			in.sum(it.getInvestment().sumSelf());
			lc += it.getLabourCount();
			spend = DoubleHelper.add(spend, it.getSpend());

			entityManager.merge(it);
		}
		pr.setInvestment(in);
		pr.setLabourCount(lc);
		pr.setSpend(spend);

		entityManager.merge(pr);

		in.copy(new_in);
		proCom.getTarget().setLabourCount(pr.getLabourCount());
		proCom.getTarget().setSpend(pr.getSpend());
		proCom.getTarget().setInfo(new_in);
		// 判断一下如果记录前后结果相同就不用记录
		if (proCom.needAdjust() == false) {

			// 记录项目月报管理统计资金的操作
			HandleLogEntity hlog = new HandleLogEntity();
			hlog.currentUser(user);
			hlog.setTargetId(pr.getId());
			hlog.setTargetType(ProjectReportEntity.class.getName());
			hlog.setContent(pr.getModelArea().getCounty() + pr.getAnnual()
					+ "年" + (pr.getPeriod() + 1) + "月统计资金");
			entityManager.persist(hlog);

			proCom.setLog(hlog);
			proCom.setName("[项目月报]" + pr.getModelArea().getCounty()
					+ pr.getAnnual() + "年" + (pr.getPeriod() + 1) + "月项目月报");
			proCom.setTargetId(pr.getId());
			proCom.setTargetType(ProjectReportEntity.class.getName());
			proCom.getTarget().setInfo(new_in);
			entityManager.persist(proCom);
		}
	}

	@Override
	@Transactional
	public void submit(Long id, IOperator user) {
		ProjectReportEntity pr = __get(id);
		__submit(pr, user, null);
		//
		// if(pr == null)throw new RuntimeException("该月度没有项目月报可上报");
		// InvestmentInfo in = pr.getInvestment();
		// if(null == in){
		// in = new InvestmentInfo();
		// }else{
		// in.clear();
		// }
		// if(null != pr.getBack() && pr.getBack().intValue() == 1){
		//
		// }else{
		//
		// }
		// int lc = 0;
		// double spend = 0D;
		// for(ProjectReportItem it : pr.getReports()){
		// in.sum(it.getInvestment().sumSelf());
		// lc += it.getLabourCount();
		// spend = DoubleHelper.add(spend, it.getSpend());
		//
		// entityManager.merge(it);
		//
		// //对项目进行累加
		// ProjectEntity pe = it.getProject();
		// pe.getTotalInvestment().sum(in);
		// Integer slc = pe.getSumLabourCount();
		// if(slc == null) slc = 0;
		// slc += it.getLabourCount();
		// pe.setSumLabourCount(slc);
		// Double ss = pe.getSumSpend();
		// if(ss == null) ss = 0D;
		// ss += it.getSpend();
		// pe.setSumSpend(ss);
		// entityManager.merge(pe);
		// }
		//
		// pr.setInvestment(in);
		// pr.setLabourCount(lc);
		// pr.setSpend(spend);
		// pr.setStatus(1);
		// entityManager.merge(pr);
		//
		// //累加到片区
		// ModelAreaEntity ma = pr.getModelArea();
		// InvestmentInfo mainv = ma.getInvestment();
		// if(null == mainv){
		// mainv = new InvestmentInfo();
		// }else{
		// mainv.clear();
		// }
		// mainv.sum(in);
		// ma.setInvestment(mainv);
		// entityManager.merge(ma);
	}

	/**
	 * 
	 * 这个方法只是将月度报表的资金信息全设置为0，方便之后的方法对该上报的月度报表的月报资金累加给本月度报表，还有就是作数据记录
	 */
	private void __submit(ProjectReportEntity report, IOperator user,
			List<ProRepItemComparerEntity> proRepIList) {

		// 创建需要比较资金比较的实体
		ProjectComparer proCom = new ProjectComparer();
		// 创建之前项目报告资金实体
		InvestmentInfo old_rivn = new InvestmentInfo();
		// 创建之前项目报告资金实体
		InvestmentInfo new_rivn = new InvestmentInfo();

		InvestmentInfo rivn = report.getInvestment();

		if (null == rivn) {
			rivn = new InvestmentInfo();
			report.setInvestment(rivn);
			// 得到原来项目报告资金的记录
			rivn.copy(old_rivn);
			proCom.getSource().setInfo(old_rivn);
		} else {
			// 得到原来项目报告资金的记录
			rivn.copy(old_rivn);
			proCom.getSource().setInfo(old_rivn);
			rivn.clear();
		}
		// 得到原来的投工数跟折资额
		proCom.getSource().setLabourCount(report.getLabourCount());
		proCom.getSource().setSpend(report.getSpend());

		Integer back = report.getBack();

		// 项目月报管理的操作
		HandleLogEntity hlog = null;
		if (user != null) {
			hlog = new HandleLogEntity();
			hlog.currentUser(user);
			hlog.setTargetId(report.getId());
			hlog.setTargetType(ProjectReportEntity.class.getName());
			hlog.setContent("提交了" + report.getModelArea().getCounty()
					+ report.getAnnual() + "年" + (report.getPeriod() + 1)
					+ "月项目月报");
			entityManager.persist(hlog);
		}
		if (proRepIList != null) {
			for (ProRepItemComparerEntity pric : proRepIList) {
				pric.setLog(hlog);
				entityManager.persist(pric);
			}
		}
		// 判断一下项目报告退回的状态
		if (null != back && back.intValue() == 1) {
			// 退回修改后再提交的情况
			__backSubmit(report, rivn, hlog, proCom);
		} else {
			// 第一次提交情况
			__firstSubmit(report, rivn, user, hlog);
		}

		// 得到现在的投工数跟折资额
		report.getInvestment().copy(new_rivn);
		proCom.getTarget().setLabourCount(report.getLabourCount());
		proCom.getTarget().setSpend(report.getSpend());
		proCom.getTarget().setInfo(new_rivn);
		// 如果统计不相同则做记录
		if (hlog != null && proCom.needAdjust() == false) {
			proCom.setLog(hlog);
			proCom.setName("[项目月报]" + report.getModelArea().getCounty()
					+ report.getAnnual() + "年" + (report.getPeriod() + 1)
					+ "月项目月报");
			proCom.setTargetId(report.getId());
			proCom.setTargetType(ProjectReportEntity.class.getName());
			proCom.getTarget().setInfo(new_rivn);
			entityManager.persist(proCom);

		}

		report.setSubmitAt(new Date());
		entityManager.merge(report);
	}

	/**
	 * 退回去修改再提交的情况，这个情况下，不需要检查是否定义了竣工时要求提交项目图片的功能
	 * 
	 * 也不能修改项目的状态，只允许更新一些数据
	 * 
	 * @param report
	 * @param rivn
	 */
	private void __backSubmit(ProjectReportEntity report, InvestmentInfo rivn,
			HandleLogEntity hlog, ProjectComparer proCom) {
		int lc = 0;
		double sd = 0;

		// List<ProjectEntity> projs = new ArrayList<>();
		Set<ProjectEntity> set = new TreeSet<ProjectEntity>(
				new Comparator<ProjectEntity>() {
					@Override
					public int compare(ProjectEntity o1, ProjectEntity o2) {
						// TODO Auto-generated method stub
						return (int) (o2.getId() - o1.getId());
					}
				});

		for (ProjectReportItem pr : report.getReports()) {
			InvestmentInfo ivn = pr.getInvestment();
			lc += pr.getLabourCount();
			sd = DoubleHelper.add(sd, pr.getSpend());

			rivn.sum(ivn);
			// TODO
			// projs.add(pr.getProject());
			set.add(pr.getProject());
		}
		report.setStatus(1);
		report.setBack(0);
		report.setLabourCount(lc);
		report.setSpend(sd);
		entityManager.merge(report);

		// 重新统计下项目和片区的情况
		for (ProjectEntity pe : set) {
			__statProject(pe, hlog);
		}

		// 处理片区的
		ModelAreaEntity ma = report.getModelArea();
		InvestmentInfo maivn = ma.getInvestment();

		// 创建需要比较片区资金比较的实体
		ProjectComparer prosCom = new ProjectComparer();
		// 创建之前片区资金实体
		InvestmentInfo old_maivn = new InvestmentInfo();
		// 创建修改后片区资金实体
		InvestmentInfo new_maivn = new InvestmentInfo();

		if (null == maivn) {
			maivn = new InvestmentInfo();
			ma.setInvestment(maivn);
			maivn.copy(old_maivn);
			prosCom.getSource().setInfo(old_maivn);
		} else {
			maivn.copy(old_maivn);
			prosCom.getSource().setInfo(old_maivn);
			maivn.clear();
		}
		// String ql =
		// "select x from ProjectReportEntity x where x.modelArea.id=? and x.status=1";
		// for(ProjectReportEntity pre : __list(ProjectReportEntity.class, ql,
		// ma.getId())){
		// maivn.sum(pre.getInvestment());
		// }
		// entityManager.merge(ma);
		String ql = "select x from ProjectEntity x where x.modelArea.id=? and x.deleted=false";
		List<ProjectEntity> prolist = __list(ProjectEntity.class, ql,
				ma.getId());
		ql = "select x from ProjectReportItem x where x.project.id=? and x.report.status=1";
		for (ProjectEntity pre : prolist) {
			if (pre.getSourceInvestment() == null)
				pre.setSourceInvestment(new InvestmentInfo());
			maivn.sum(pre.getSourceInvestment());
			List<ProjectReportItem> pritem = __list(ProjectReportItem.class,
					ql, pre.getId());
			if (pritem != null) {
				for (ProjectReportItem pri : pritem) {
					if (pri.getInvestment() == null)
						pri.setInvestment(new InvestmentInfo());
					maivn.sum(pri.getInvestment());
				}
			}
		}

		entityManager.merge(ma);

		maivn.copy(new_maivn);
		prosCom.getTarget().setInfo(new_maivn);
		// 记录片区资金比较如果不同记录一下
		if (hlog != null && prosCom.needAdjust() == false) {
			prosCom.setLog(hlog);
			prosCom.setName("[片区]" + ma.getName());
			prosCom.setTargetId(ma.getId());
			prosCom.setTargetType(ModelAreaEntity.class.getName());
			entityManager.persist(prosCom);
		}
	}

	private void __statProject(ProjectEntity pro, HandleLogEntity hlog) {
		InvestmentInfo sinv = pro.getSourceInvestment();
		InvestmentInfo tinv = pro.getTotalInvestment();

		// 创建需要比较项目资金比较的实体
		ProjectComparer proCom = new ProjectComparer();
		// 创建之前项目资金实体
		InvestmentInfo old_tinv = new InvestmentInfo();
		// 创建修改后项目资金实体
		InvestmentInfo new_tinv = new InvestmentInfo();

		if (null == tinv) {
			tinv = new InvestmentInfo();
			// 得到原来项目总资金的记录
			tinv.copy(old_tinv);
			proCom.getSource().setInfo(old_tinv);
		} else {
			// 得到原来项目总资金的记录
			tinv.copy(old_tinv);
			proCom.getSource().setInfo(old_tinv);
			tinv.clear();
		}
		tinv.sum(sinv);

		proCom.getSource().setLabourCount(pro.getLabourCount());
		proCom.getSource().setSpend(pro.getSpend());

		String ql = "select x from ProjectReportItem x where x.project.id=? and x.report.status=1 and x.project.deleted=false";
		int labourCount = 0;
		double spend = 0D;
		List<ProjectReportItem> items = __list(ProjectReportItem.class, ql,
				pro.getId());
		for (ProjectReportItem it : items) {
			tinv.sum(it.getInvestment());
			labourCount += it.getLabourCount();
			spend = DoubleHelper.add(spend, it.getSpend());

		}

		pro.setSumLabourCount(labourCount);
		pro.setSumSpend(spend);
		pro.setTotalInvestment(tinv);

		entityManager.merge(pro);

		// 得到现在的资金
		tinv.copy(new_tinv);
		proCom.getTarget().setInfo(new_tinv);
		proCom.getTarget().setLabourCount(labourCount);
		proCom.getTarget().setSpend(spend);
		// 记录项目资金比较的结果
		if (hlog != null && proCom.needAdjust() == false) {
			proCom.setLog(hlog);
			proCom.setName("[项目]" + pro.getName());
			proCom.setTargetId(pro.getId());
			proCom.setTargetType(ProjectEntity.class.getName());
			entityManager.persist(proCom);
		}
	}

	/**
	 * 将上报的月报信息對應累加到该月报的项目上，月度报表的信息也由项目的月报累加，片区资金由月度报表的资金累加
	 */
	private void __firstSubmit(ProjectReportEntity report, InvestmentInfo rivn,
			IOperator user, HandleLogEntity hlog) {
		int fc = 0, sc = 0, zc = 0;
		int lc = 0;
		double sd = 0;
		Date now = new Date();
		// 是否检查项目相片的参数
		ModelAreaEntity ma = report.getModelArea();
		String username = ma.getCreatorName();
		ProjectConfig config = __config();
		boolean checkBafore = config.checkBeforePhone(username), checkAfter = config
				.checkAfterPhone(username);

		String errmsg = "";
		int i = 1;
		for (ProjectReportItem pr : report.getReports()) {
			InvestmentInfo ivn = pr.getInvestment();
			lc += pr.getLabourCount();
			sd = DoubleHelper.add(sd, pr.getSpend());

			// 某个月下面的项目的状态
			ProjectEntity pro = pr.getProject();
			int ps = pr.getProjectStatus();
			if (ps == 1) {
				String before = __checkCanFinish(checkBafore, pro, "建设前");
				String after = __checkCanFinish(checkAfter, pro, "建设后");
				if (before.isEmpty()) {
					if (after.isEmpty()) {
						errmsg += "";
					} else {
						errmsg += i++ + "." + pro.getCode() + "--"
								+ pro.getName() + after + "\n\r\t</br>";
					}
				} else {
					errmsg += i++ + "." + pro.getCode() + "--" + pro.getName()
							+ before + after + "\n\r\t</br>";
				}

				// String msg = __checkCanFinish(checkBafore, pro, "建设前");
				// msg += __checkCanFinish(checkAfter, pro, "建设后");
				// if(!msg.isEmpty()){
				// throw new RuntimeException("请上传" + msg + "的相片！");
				// }

				// 项目完成
				pro.setStatus(2);
				pro.setFinishAtY(report.getAnnual());
				pro.setFinishAtM(report.getPeriod());

				fc++;
			} else if (ps == 2) {
				// 项目终止
				pro.setStatus(3);
				pro.setStopAt(now);

				pro.setEndRemark(pr.getExitReason());
				sc++;
			}
			rivn.sum(ivn);
			if (pr.zeroReport()) {
				zc++;
			}
			// 对项目的进行累加
			InvestmentInfo pinv = pro.getTotalInvestment();

			// 项目累计资金比较记录
			// 创建需要比较资金比较的实体
			ProjectComparer proCom = new ProjectComparer();
			// 创建之前片区资金实体
			InvestmentInfo old_pinv = new InvestmentInfo();
			// 创建修改后片区资金实体
			InvestmentInfo new_pinv = new InvestmentInfo();

			if (null == pinv) {
				pinv = new InvestmentInfo();
				pro.setTotalInvestment(pinv);
			}

			// 得到原来项目的资金
			proCom.getSource().setInfo(old_pinv);
			proCom.getSource().setLabourCount(pro.getLabourCount());
			proCom.getSource().setSpend(pro.getSpend());
			/**
			 * 项目加上对应上报的月度报表的项目item资金
			 */
			pinv.sum(ivn);
			/**
			 * 项目实体斥资额 ,投工数对应 加上当前上报的项目月度报表的斥资额,投工数
			 */
			if (pro.getSumSpend() == null)
				pro.setSumSpend(new Double(0));

			if (pro.getSumLabourCount() == null)
				pro.setSumLabourCount(new Integer(0));

			pro.setSumSpend(DoubleHelper.add(pro.getSumSpend().doubleValue(),
					pr.getSpend()));
			pro.setSumLabourCount(pro.getSumLabourCount() + pr.getLabourCount());
			// 得到现在项目的资金
			proCom.getTarget().setInfo(new_pinv);
			proCom.getTarget().setLabourCount(pro.getLabourCount());
			proCom.getTarget().setSpend(pro.getSpend());

			pro.setTotalInvestment(pinv);
			entityManager.merge(pro);

			// 提交项目月报项目累计资金发生改变记录一下
			if (hlog != null && proCom.needAdjust() == false) {
				proCom.setLog(hlog);
				proCom.setName("[项目]" + pro.getName());
				proCom.setTargetId(pro.getId());
				proCom.setTargetType(ProjectEntity.class.getName());
				entityManager.persist(proCom);
			}

		}

		if (!errmsg.isEmpty()) {
			throw new RuntimeException("请上传" + errmsg + "图片！！！！！！");
		}
		report.setStatus(1);

		// 月报的一些统计信息
		report.setTotalCount(report.getReports().size());
		report.setFinishCount(fc);
		report.setStopCount(sc);
		report.setZeroCount(zc);

		report.setLabourCount(lc);
		report.setSpend(sd);
		entityManager.merge(report);

		// 创建需要比较资金比较的实体
		ProjectComparer proCom = new ProjectComparer();
		// 创建之前片区资金实体
		InvestmentInfo old_maivn = new InvestmentInfo();
		// 创建修改后片区资金实体
		InvestmentInfo new_maivn = new InvestmentInfo();

		InvestmentInfo maivn = ma.getInvestment();
		if (null == maivn) {
			maivn = new InvestmentInfo();
			ma.setInvestment(maivn);
		}
		maivn.copy(old_maivn);
		maivn.sum(rivn);
		maivn.copy(new_maivn);
		entityManager.merge(ma);

		proCom.getSource().setInfo(old_maivn);
		proCom.getTarget().setInfo(new_maivn);

		// 提交项目月报片区资金发生改变记录一下
		if (hlog != null && proCom.needAdjust() == false) {
			proCom.setLog(hlog);
			proCom.setName("[片区]" + ma.getName());
			proCom.setTargetId(ma.getId());
			proCom.setTargetType(ModelAreaEntity.class.getName());
			entityManager.persist(proCom);
		}

	}

	@Override
	@Transactional
	public void resetProject(Long id) {
		ProjectReportEntity report = __get(id);
		Integer back = report.getBack();
		if (back != null && back == 10) {
			// 因为是第一次提交的才会出现这种情况，把非第一次提交的处理去掉，这样可以减少一下错误的操作
			// 如果提交后又有退回去修改处理的，则手动改一下数据库吧
			for (ProjectReportItem pr : report.getReports()) {
				// 获取单条月报的资金
				InvestmentInfo ivn = pr.getInvestment();
				// 获取单条月报所在的项目
				ProjectEntity pro = pr.getProject();

				// 这个是出错的，如果为null，则表示有问题的，这个是不需要处理的
				// 计划投入的资金
				InvestmentInfo errorInv = pro.getInvestment();
				// 对项目的进行累加
				InvestmentInfo pinv = pro.getTotalInvestment();
				if (null == pinv) {
					pinv = new InvestmentInfo();
				}

				// 把出错的减去原来的月报的数据
				// 计划投入的资金减去月报的资金
				errorInv.subtractTo(ivn);
				// 把需要累加的累加到正确的位置
				// 累计月报的资金
				pinv.sum(ivn);
			}

			report.setBack(0);
			entityManager.merge(report);
		}
	}

	@Override
	@Transactional
	public void backToReset(Long id) {
		ProjectReportEntity report = __get(id);
		report.setBack(10);
		entityManager.merge(report);
	}

	@Override
	public List<ProjectReportItem> reportItem(Long id) {
		String ql = "select x from ProjectReportItem x where x.report.id=? and x.project.deleted=false";
		return __list(ProjectReportItem.class, ql, id);

	}

	@Override
	@Transactional
	public void reCorrectt(Long id) {
		ProjectReportEntity report = __get(id);
		for (ProjectReportItem pr : report.getReports()) {
			ProjectEntity pro = pr.getProject();
			InvestmentInfo invest = new InvestmentInfo();

			String ql = "select x from ProjectReportItem x where x.project.id=? and x.report.status=1";

			List<ProjectReportItem> items = __list(ProjectReportItem.class, ql,
					pro.getId());
			for (ProjectReportItem it : items) {
				invest.sum(it.getInvestment());
			}
			//
			// //获取单条月报的资金
			// InvestmentInfo ivn = pr.getInvestment();
			// //获取单条月报所在的项目

			pro.setTotalInvestment(invest);

			// 错误的计划资金
			InvestmentInfo errorInv = pro.getInvestment();

			errorInv.reSubtract(invest);

			// entityManager.merge(pro);
		}
		entityManager.merge(report);
	}

	@Override
	@Transactional
	public void reUpdate(Long id) {
		ProjectReportEntity report = __get(id);
		for (ProjectReportItem pr : report.getReports()) {
			ProjectEntity pro = pr.getProject();
			InvestmentInfo invest = new InvestmentInfo();

			String ql = "select x from ProjectReportItem x where x.project.id=? and x.report.status=1 and "
					+ pro.getId().longValue() + "<313";

			List<ProjectReportItem> items = __list(ProjectReportItem.class, ql,
					pro.getId());
			for (ProjectReportItem it : items) {
				invest.sum(it.getInvestment());
			}

			// pro.setTotalInvestment(invest) ;

			__exec("update ProjectEntity set totalInvestment.totalFunds="
					+ invest.getTotalFunds() + ",totalInvestment.stateFunds="
					+ invest.getStateFunds()
					+ ",totalInvestment.provinceFunds="
					+ invest.getProvinceFunds()
					+ ",totalInvestment.specialFunds="
					+ invest.getSpecialFunds()
					+ ",totalInvestment.countyFunds=" + invest.getCountyFunds()
					+ ",totalInvestment.socialFunds=" + invest.getSocialFunds()
					+ ",totalInvestment.massFunds=" + invest.getMassFunds()
					+ ",totalInvestment.otherFunds=" + invest.getOtherFunds()
					+ " where id=" + pro.getId());

		}
		// entityManager.merge(report);
	}

	@Override
	public List<ProjectReportItem> allItems(Long id) {
		String ql = "select x from ProjectReportItem x where x.report.id=? and x.project.deleted=false  order by x.project.code  asc";
		return __list(ProjectReportItem.class, ql, id);
	}

	@Override
	@Transactional
	public void saveReport(ProjectReportSaveInfo pr, Boolean submit,
			IOperator user) {
		List<ProRepItemComparerEntity> pricList = new ArrayList<>();

		ProjectReportEntity report = entityManager.find(
				ProjectReportEntity.class, pr.getRid());
		if (null == report)
			throw new RuntimeException("数据操作异常！");
		for (ProjectReportItem pri : report.getReports()) {
			InvestmentInfo ivn = pri.getInvestment();
			if (null == ivn) {
				ivn = new InvestmentInfo();
				pri.setInvestment(ivn);
			}
			/*
			 * 月报的对比记录
			 */
			ProRepItemComparerEntity prce = new ProRepItemComparerEntity();
			prce.setTargetId(pri.getId());
			prce.setTargetType(pri.getClass().getName());
			prce.setName(pri.getProject().getName()
					+ pri.getReport().getAnnual() + "年"
					+ (pri.getReport().getPeriod() + 1) + "月月报");

			InvestmentComparer icp = prce.getIcp();
			InvestmentInfo source = new InvestmentInfo();
			icp.setSource(source);
			InvestmentInfo target = new InvestmentInfo();
			icp.setTarget(target);
			source.setCityFunds(pri.getInvestment().getCityFunds());
			source.setCountyFunds(pri.getInvestment().getCountyFunds());
			source.setLocalFunds(pri.getInvestment().getLocalFunds());
			source.setMassFunds(pri.getInvestment().getMassFunds());
			source.setOtherFunds(pri.getInvestment().getOtherFunds());
			source.setProvinceFunds(pri.getInvestment().getProvinceFunds());
			source.setSocialFunds(pri.getInvestment().getSocialFunds());
			source.setSpecialFunds(pri.getInvestment().getSpecialFunds());
			source.setStateFunds(pri.getInvestment().getStateFunds());
			source.setTotalFunds(pri.getInvestment().getTotalFunds());
			prce.setOldSpend(pri.getSpend());
			prce.setOldLabourCount(pri.getLabourCount());
			/*
			 * 
			 */

			for (ProjectReportItemBean prb : pr.getSave_data()) {
				if (pri.getId().equals(prb.getId())) {
					ivn.setStateFunds(prb.getState());
					ivn.setProvinceFunds(prb.getProvince());
					ivn.setSpecialFunds(prb.getSpecial());
					ivn.setSocialFunds(prb.getSocial());
					ivn.setMassFunds(prb.getMass());
					ivn.setOtherFunds(prb.getOther());
					ivn.setCityFunds(prb.getCity());
					ivn.setCountyFunds(prb.getCounty());

					ivn.setTotalFunds(prb.getTotal());

					// 还是自己重新统计一下吧
					ivn.sumSelf();

					pri.setProjectStatus(prb.getStatus());
					pri.setLabourCount(prb.getLabour());
					pri.setSpend(prb.getSpend());
					pri.setExitReason(prb.getReason());

					/*
					 * 改变后的月报信息
					 */
					target.setCityFunds(pri.getInvestment().getCityFunds());
					target.setCountyFunds(pri.getInvestment().getCountyFunds());
					target.setLocalFunds(pri.getInvestment().getLocalFunds());
					target.setMassFunds(pri.getInvestment().getMassFunds());
					target.setOtherFunds(pri.getInvestment().getOtherFunds());
					target.setProvinceFunds(pri.getInvestment()
							.getProvinceFunds());
					target.setSocialFunds(pri.getInvestment().getSocialFunds());
					target.setSpecialFunds(pri.getInvestment()
							.getSpecialFunds());
					target.setStateFunds(pri.getInvestment().getStateFunds());
					target.setTotalFunds(pri.getInvestment().getTotalFunds());
					prce.setNewSpend(pri.getSpend());
					prce.setNewLabourCount(pri.getLabourCount());
					pricList.add(prce);
					/*
					 * 
					 */
					break;
				}
			}
			entityManager.merge(pri);
		}

		if (submit) {
			__submit(report, user, pricList);
			flashQuarterReport(report);
		} else {
			// 暂存：report 也要保存项目的投入资金情况,用于暂存后回来的显示
			_saveReportInvest(report);
		}
	}

	public static void main(String[] args) {
		System.out.println(getQuarter(3).getKey());
		System.out.println(getQuarterMaxMonth(3));
	}

	/**
	 * 
	 * @return 一年中的所有个季度 key哪个季度(0,3) value该季度包含的月份(0,11)
	 */
	public static Map<Integer, int[]> setMonthByQuarter() {
		Map<Integer, int[]> map = new TreeMap<>();
		int monthList[] = new int[12];
		for (int i = 0; i < 12; i++) {
			monthList[i] = i;
		}
		int monthIndex = 0;
		for (int i = 0; i < 4; i++) {
			int[] monthByPeriod = new int[3];
			for (int i2 = 0; i2 < monthByPeriod.length; i2++) {
				monthByPeriod[i2] = monthList[monthIndex];
				monthIndex++;
			}
			map.put(new Integer(i), monthByPeriod);
		}
		return map;
	}

	/**
	 * 拿到某个季度的最大月份
	 * 
	 * @param jidu
	 * @return
	 */
	public static int getQuarterMaxMonth(int jidu) {
		Map<Integer, int[]> map = setMonthByQuarter();
		return map.get(jidu)[map.get(jidu).length - 1];
	}

	/**
	 * 
	 * @param period
	 *            某个月份
	 * @return 返回key 为季度值 value 为该季度包含的月份
	 */
	public static Entry<Integer, int[]> getQuarter(int period) {
		Map<Integer, int[]> map = setMonthByQuarter();
		for (Entry<Integer, int[]> set : map.entrySet()) {
			System.out.println(set.getKey());
			System.out.println("value:");
			for (int i : set.getValue()) {
				System.out.println(i);
				if (i == period) {
					return set;
				}
			}
		}
		return null;
	}

	/**
	 * 提交了某个月的项目月度报表后 对该月对应所在季度报表以及该季度报表往后的季度报表 进行重新统计资金
	 * (加上到该季度之前所有提交的项目月报里的主体村项目月报资金)
	 * 
	 * @param period
	 *            某月
	 * @param annual
	 *            某年
	 * @param ownerId
	 * @param mid
	 *            片区id
	 */
	private void flashQuarterReport(ProjectReportEntity projectReport) {

		Entry<Integer, int[]> set = MonthAndQuarterUtil
				.getQuarter(projectReport.getPeriod());
		if (set != null) {
			String ql = "select x from ModelAreaQuarterItem x where x.modelArea.ownerId=? and x.batch.quarter.annual*100+x.batch.quarter.period>=? order by x.batch.quarter.annual asc,x.batch.quarter.period asc";
			List<ModelAreaQuarterItem> maqiList = __list(
					ModelAreaQuarterItem.class, ql, projectReport
							.getModelArea().getOwnerId(),
					projectReport.getAnnual() * 100 + set.getKey());
			if (maqiList != null) {
				for (ModelAreaQuarterItem maqi : maqiList) {
					System.out.println(maqi.getBatch().getQuarter().getAnnual()
							+ "年"
							+ (maqi.getBatch().getQuarter().getPeriod() + 1)
							+ "季度");
					if (maqi.getInvestment() == null)
						maqi.setInvestment(new InvestmentInfo());
					maqi.getInvestment().clear();
					int maxMonthByQuarter = MonthAndQuarterUtil
							.getQuarterMaxMonth(maqi.getBatch().getQuarter()
									.getPeriod());
					ql = "select x from ProjectReportItem x where x.report.modelArea.id=? and x.report.status=1 and x.report.annual*100+x.report.period<=? and x.project.deleted=false";
					List<ProjectReportItem> projectReportItemList = __list(
							ProjectReportItem.class, ql, projectReport
									.getModelArea().getId(), maqi.getBatch()
									.getQuarter().getAnnual()
									* 100 + maxMonthByQuarter);

					Set<ProjectEntity> proSet = new TreeSet<>(
							new Comparator<ProjectEntity>() {
								@Override
								public int compare(ProjectEntity o1,
										ProjectEntity o2) {
									return Integer.parseInt(o1.getId()
											.toString())
											- Integer.parseInt(o2.getId()
													.toString());
								}
							});

					for (ProjectReportItem proRI : projectReportItemList) {
						if (proRI.getProject().getRural() instanceof NewRuralEntity) {
							// 应该是加上没被删除的项目月报资金
							if (!proRI.getProject().isDeleted()) {
								maqi.getInvestment().sum(proRI.getInvestment());
								proSet.add(proRI.getProject());
							}
						} else {

							System.out.println("非主体村的项目"
									+ proRI.getProject().getRural()
											.getNaturalVillage()
									+ proRI.getProject());
						}
					}

					int fp = 0;
					int stp = 0;
					for (ProjectEntity p : proSet) {
						maqi.getInvestment().sum(p.getSourceInvestment());
						System.out.println(maqi.getBatch().getQuarter()
								.getAnnual()
								+ "year"
								+ maqi.getBatch().getQuarter().getPeriod()
								+ "month 季度add Project source totalFunds="
								+ p.getSourceInvestment().getTotalFunds());
						if (p.getStatus() == 2) {
							fp++;
						}
						stp++;
					}

					// 下面是同步资金需要修改的项
					maqi.setFinishProject(fp);
					maqi.setStartProject(stp);
					entityManager.merge(maqi);
				}
			}
		}
	}

	@Override
	public TreeSet<Integer[]> getPeriod(Long mid) {
		List<Object[]> list = __list(
				Object[].class,
				"select x.annual,x.period from ProjectReportEntity as x where x.modelArea.id=? and (x.status=0 or (x.status=0 and x.back=1)) order by (x.annual*100+x.period) desc ",
				mid);
		TreeSet<Integer[]> ts = new TreeSet<Integer[]>(
				new Comparator<Integer[]>() {

					@Override
					public int compare(Integer[] o1, Integer[] o2) {
						return (o2[0] * 100 + o2[1]) - (o1[0] * 100 + o1[1]);

					}
				});
		if (list != null) {
			List<Integer[]> list2 = new ArrayList<Integer[]>();
			for (Object[] o : list) {
				list2.add(new Integer[] { new Integer(o[0].toString()),
						new Integer(o[1].toString()) });
			}
			for (Integer[] period : list2) {
				ts.add(period);
			}
			for (Integer[] set : ts) {
				System.out.println(set[0] + " " + set[1]);
			}
		} else {
			return null;
		}
		return ts;
	}
	// /**
	// * @author wsf
	// * 加到季度報表
	// */
	// public void saveJiDuReport(int jidu){
	// String ql =
	// "select x from ModelAreaQuarterItem x where x.status=1 and x.modelArea.ownerId=? and x.batch.quarter.annual=? and x.batch.quarter.period=?";
	// String hql= "select * from ? x where x.period=?";
	// ModelareaQueraItem ?= __find(?,hql,jidu);
	// ModelAreaQuarterItem maqi= __first(ModelAreaQuarterItem.class, ql,
	// ownerId,annual,period);
	//
	// InvestmentInfo mopinv= ?.getTotalFunds();
	// for(ProjectReportItem pri : report.getReports()){
	// InvestmentInfo ivn = pri.getInvestment();
	//
	//
	// mopinv.setStateFunds(DoubleHelper.add(mopinv.getStateFunds(),ivn.getStateFunds()));
	// mopinv.setProvinceFunds(DoubleHelper.add(mopinv.getProvinceFunds(),ivn.getProvinceFunds()));
	// mopinv.setSpecialFunds(DoubleHelper.add(mopinv.getSpecialFunds(),ivn.getSpecialFunds()));
	// mopinv.setSocialFunds(DoubleHelper.add(mopinv.getSocialFunds(),ivn.getSocialFunds()));
	// mopinv.setMassFunds(DoubleHelper.add(mopinv.getMassFunds(),ivn.getMassFunds()));
	// mopinv.setOtherFunds(DoubleHelper.add(mopinv.getOtherFunds()ivn.getOtherFunds()));
	// mopinv.setCityFunds(DoubleHelper.add(mopinv.getCityFunds(),ivn.getCityFunds()));
	// mopinv.setCountyFunds(DoubleHelper.add(mopinv.getCountyFunds(),ivn.getCountyFunds()));
	// mopinv.setTotalFunds(DoubleHelper.add(mopinv.getTotalFunds(),ivn.getTotalFunds()));
	//
	// }
	// }

}
