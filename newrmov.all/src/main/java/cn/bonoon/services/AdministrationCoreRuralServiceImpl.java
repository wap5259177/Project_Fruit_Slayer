package cn.bonoon.services;

import static cn.bonoon.util.DoubleHelper.add;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.AdministrationCoreRuralService;
import cn.bonoon.core.IRuralEditor;
import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.entities.AdministrationRuralEntity;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.RuralExpertGroupEntity;
import cn.bonoon.entities.RuralUnitEntity;
import cn.bonoon.entities.RuralWorkGroupEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.util.DoubleHelper;

@Service
@Transactional(readOnly = true)
// public class AdministrationCoreRuralServiceImpl extends
// AbstractService<AdministrationCoreRuralEntity> implements
// AdministrationCoreRuralService {
public class AdministrationCoreRuralServiceImpl extends
		ConfigurableModelAreaService<AdministrationCoreRuralEntity> implements
		AdministrationCoreRuralService {
	@Override
	public AdministrationCoreRuralEntity getAdministrationByAdminRuralId(Long id) {
		AdministrationCoreRuralEntity are = entityManager.find(
				AdministrationCoreRuralEntity.class, id);
		return are;
	}

	/*
	 * 重写__update
	 * 
	 * 点击确认前验证
	 */
	@Override
	protected AdministrationCoreRuralEntity __update(OperateEvent event,
			AdministrationCoreRuralEntity entity) {

		checkAndThrowError(entity.getModelArea(), event);
		boolean submit = "true".equalsIgnoreCase(event
				.getString("applicant-submit"));
		PlaceEntity pl = entity.getPlace();
		entity.setTown(pl.getParent().getName());
		if (submit) {// 确认
			String errmsg = "";
			int i = 1;
			if (StringHelper.isEmpty(entity.getCode())) {
				errmsg += i++ + ". 请填写序号！\n\r\t";
			}

			// if(entity.getNatrualruralNum()<=0){
			// errmsg += i++ +".自然村个数非合理数字!\n\r\t";
			// }

			if (entity.getRuralArea() < entity.getArableLand()) {
				errmsg += i++ + ". 总面积必须大于耕地面积！\n\r\t";
			}

			if (entity.getPopulation() <= 0) {
				errmsg += i++ + ". 人口数非合理数字！\n\r\t";
			}
			if (entity.getLabor() <= 0) {
				errmsg += i++ + ". 劳动人口数非合理数字！\n\r\t";
			}
			if (entity.getPopulation() < entity.getLabor()) {
				errmsg += i++ + ". 劳动人口不能超过村总人口！\n\r\t";
			}
			if (entity.getHouseHold() > entity.getPopulation()) {
				errmsg += i++ + ". 户数必须小于人口数！\n\r\t";
			}

			if ("是".equals(entity.getPoorVillage())
					&& StringHelper.isEmpty(entity.getHelpUnit())) {
				errmsg += i++ + ". 请填写帮扶单位名称！\n\r\t";
			}

			if ("是".equals(entity.getPublicService())
					&& StringHelper.isEmpty(entity.getPsAnnual())) {
				errmsg += i++ + ". 请选择建立统一的村级公共服务管理平台年度！\n\r\t";
			}

			if ("是".equals(entity.getGeneralPlan())
					&& null == entity.getGpDate()) {
				errmsg += i++ + ". 请填写完成总体规划的日期！\n\r\t";
			}
			if ("是".equals(entity.getAreaPlan()) && null == entity.getApDate()) {
				errmsg += i++ + ". 请填写完成连线连片规划日期！\n\r\t";
			}
			if ("是".equals(entity.getDepthPlanning())
					&& null == entity.getDpDate()) {
				errmsg += i++ + ". 请填写完成村庄深度规划设计日期！\n\r\t";
			}

			if (!errmsg.isEmpty()) {
				throw new RuntimeException(errmsg);
			}

			// 如果挂点县领导没有填写名字,则后台的数据应该被清空
			if (StringHelper.isEmpty(entity.getLeaderName())) {
				entity.setLeaderJob("");
				entity.setLeaderHP("");
			}
			if (StringHelper.isEmpty(entity.getLeaderName2())) {
				entity.setLeaderJob2("");
				entity.setLeaderHP2("");
			}
			entity.setStatus(1);
		} else {// 暂存
			entity.setStatus(0);
		}
		entityManager.merge(entity);

		Long aid = entity.getId();
		__exec("delete from RuralUnitEntity where adminRural.id=?", aid);
		__exec("delete from RuralWorkGroupEntity where adminRural.id=?", aid);
		__exec("delete from RuralExpertGroupEntity where adminRural.id=?", aid);
		IRuralEditor mae = (IRuralEditor) event.getSource();
		int count = 0;
		// 保存工作小组

		if (null != mae.getWorkgroupItems()) {
			for (String i : mae.getWorkgroupItems()) {
				String name = event.getString("wn_" + i);
				if (!submit || StringHelper.isNotEmpty(name)) {
					RuralWorkGroupEntity rwg = new RuralWorkGroupEntity();
					// rwg.setNewRural(entity);
					rwg.setAdminRural(entity);
					rwg.setUnitJob(event.getString("uj_" + i));
					rwg.setWorkName(name);
					rwg.setWorkPhone(event.getString("wp_" + i));
					rwg.setWorkRemark(event.getString("wr_" + i));
					entityManager.persist(rwg);
				}
				count++;
			}
		}
		entity.setWorkGroupCount(count);

		// 保存专家指导组
		count = 0;
		if ("是".equals(entity.getExpertGroup())) {
			if (null != mae.getExpertgroupItems()) {
				for (String i : mae.getExpertgroupItems()) {
					String name = event.getString("en_" + i);
					if (!submit || StringHelper.isNotEmpty(name)) {// 点击暂存,专家的名字不能为空
						RuralExpertGroupEntity reg = new RuralExpertGroupEntity();
						reg.setAdminRural(entity);
						reg.setExpertName(name);
						reg.setExpertJob(event.getString("ej_" + i));
						reg.setExpertPhone(event.getString("ep_" + i));
						reg.setExpertRemark(event.getString("er_" + i));
						entityManager.persist(reg);
					}
					count++;
				}
			}
			if (submit && count <= 0) {// 点了"是",但是没有写专家小组
				throw new RuntimeException("请填写专家小组!");
			}
		}
		entity.setExpertGroupCount(count);

		// 保存规划单位
		count = 0;
		if (mae.getUnitItems() != null) {
			for (String i : mae.getUnitItems()) {
				String name = event.getString("ucn_" + i);
				if (!submit || StringHelper.isNotEmpty(name)) {
					RuralUnitEntity rue = new RuralUnitEntity();
					rue.setAdminRural(entity);
					rue.setContactName(name);
					rue.setUnitName(event.getString("un_" + i));
					rue.setRegisteredAddress(event.getString("ra_" + i));
					rue.setUnitPhone(event.getString("up_" + i));
					entityManager.persist(rue);
				}
				count++;
			}
		}
		entity.setUnitCount(count);

		entityManager.merge(entity);
		return super.__update(event, entity);
	}

	// 工作小组
	@Override
	public List<RuralWorkGroupEntity> workGroupsByAdminRural(Long id) {
		return __list(RuralWorkGroupEntity.class,
				"select x from RuralWorkGroupEntity x where x.adminRural.id=?",
				id);
	}

	// 规划设计单位
	@Override
	public List<RuralUnitEntity> ruralUnitsByAdminRural(Long id) {
		return __list(RuralUnitEntity.class,
				"select x from RuralUnitEntity x where x.adminRural.id=?", id);
	}

	// 专家指导组
	@Override
	public List<RuralExpertGroupEntity> experGroupByAdminRural(Long id) {
		return __list(
				RuralExpertGroupEntity.class,
				"select x from RuralExpertGroupEntity x where x.adminRural.id=?",
				id);
	}

	// 自然村名单
	@Override
	public List<NewRuralEntity> getRuralByAdminRuralId(Long id) {
		String ql = "select x from NewRuralEntity x where x.adminRural.id=? and x.deleted=false";
		return __list(NewRuralEntity.class, ql, id);
	}

	// TODO:判断 村子是否为删除状态 where x.deleted=false;
	@Override
	public List<String> getNaturalVillage(Long adminId) {
		// String ql =
		// "select x.naturalVillage from NewRuralEntity x  where  x.adminRural.id=?";
		String ql = "select x.naturalVillage from NewRuralEntity x  where  x.adminRural.id=? and x.deleted=false";
		return __list(String.class, ql, adminId);
	}

	// 检查该村子所属的批次是否已经关闭上报功能
	@Override
	public boolean check(Long id, IOperator opt) {
		String ql = "select x.modelArea.batch from AdministrationRuralEntity x where id=? and x.deleted=false";
		return __config().check(__first(String.class, ql, id), opt, false);
	}

	// private final String statistics_ql =
	// "select x.modelArea.batch,x.reportAnnual,x.modelArea.cityName,x.county,x.name,x.themeName,"
	// //6-double,7~14-int
	// +"x.name,x.natrualruralNum,x.ruralArea,x.arableLand,x.houseHold,x.population,x.labor,x.poorHouseHold,x.poorPopulation,x.lowHouseHold,x.lowPopulation,x.wubaoHouseHold,"
	// +"x.dangerHouse,(case when (x.poorVillage='是') then '是' else '否' end)," +
	// "x.annualIncome_13,x.annualIncome_14,x.annualIncome_15,x.annualIncome_16,x.annualIncome_17,"
	// +
	// "x.collectivityAnnulIncome_13,x.collectivityAnnulIncome_14,x.collectivityAnnulIncome_15,x.collectivityAnnulIncome_16,x.collectivityAnnulIncome_17,"
	// +"(case when (length(x.histroyDesc)> 0) then '是' else '否' end),"
	// +"(case when (length(x.ecologicalDesc)> 0) then '是' else '否' end),"
	// +"(case when (length(x.civilianDesc)> 0) then '是' else '否' end),"
	// +"(case when (length(x.tourDesc)> 0) then '是' else '否' end),"
	// +"(case when (length(x.industryDesc)> 0) then '是' else '否' end),"
	// +"(case when (length(x.fisheryDesc)> 0) then '是' else '否' end),"
	// +"(case when (length(x.otherDesc)> 0) then '是' else '否' end),"
	//
	// +"x.viewSpot,x.culturalAct,x.culturalActArea,x.ruralPark,x.ruralParkArea,x.ruralSquare,x.squareArea,x.healthStation,x.healthStationArea,x.villageToilet,"
	// +"x.villageToiletArea,"
	// +"(case when (x.publicService='是') then '是' else '否' end),"
	// +"(case when ((length(x.leaderName) > 0) or (length(x.leaderName2) > 0)) then '是' else '否' end),"
	// +"(case when (x.workGroupCount > 0) then '是' else '否' end) ,"
	// +"x.workGroupCount,"
	// +"(case when (x.expertGroup='是') then '是' else '否' end),"
	// +"(case when (x.generalPlan='是') then '是' else '否' end),"
	// +"(case when (x.areaPlan='是') then '是' else '否' end),"
	// +"(case when (x.depthPlanning='是') then '是' else '否' end) "
	// +
	// " from AdministrationCoreRuralEntity x where x.deleted=false and x.status>0";
	//
	@Override
	public Collection<Object[]> statistics(String batch) {
		batch = StringHelper.get(batch, "一");
		String ql = "select x from AdministrationCoreRuralEntity x where x.deleted=false and x.modelArea.deleted=false and x.modelArea.batch=? and x.modelArea.status>0 order by x.modelArea.ordinalModel asc";
		List<AdministrationCoreRuralEntity> items = __list(
				AdministrationCoreRuralEntity.class, ql, batch);
		return __statistics(items);
	}



	private Collection<Object[]> __statistics(
			List<AdministrationCoreRuralEntity> items) {
		Map<Long, Object[]> sts = new LinkedHashMap<>();

		for (AdministrationCoreRuralEntity acre : items) {

			Object[] its = sts.get(acre.getId());

			if (its == null) {
				ModelAreaEntity ma = acre.getModelArea();
				its = new Object[56];
				sts.put(acre.getId(), its);

				its[0] = ma.getBatch();
				its[1] = acre.getAnnual();
				its[2] = ma.getCityName();
				its[3] = ma.getCounty();
				its[4] = ma.getName();
				its[5] = ma.getThemeName();
				its[6] = acre.getName();

				its[7] = acre.getNatrualruralNum();// 自然村个数
				its[8] = acre.getRuralArea();
				its[9] = acre.getArableLand();
				its[10] = acre.getHouseHold();
				its[11] = acre.getPopulation();
				its[12] = acre.getLabor();
				its[13] = acre.getPoorHouseHold();
				its[14] = acre.getPoorPopulation();
				its[15] = acre.getLowHouseHold();
				its[16] = acre.getLowPopulation();
				its[17] = acre.getWubaoHouseHold();
				its[18] = acre.getDangerHouse();
				its[19] = acre.getPoorVillage();
				its[20] = acre.getAnnualIncome_13();
				its[21] = acre.getAnnualIncome_14();
				its[22] = acre.getAnnualIncome_15();
				its[23] = acre.getAnnualIncome_16();
				its[24] = acre.getAnnualIncome_17();
				its[25] = acre.getCollectivityAnnulIncome_13();
				its[26] = acre.getCollectivityAnnulIncome_14();
				its[27] = acre.getCollectivityAnnulIncome_15();
				its[28] = acre.getCollectivityAnnulIncome_16();
				its[29] = acre.getCollectivityAnnulIncome_17();

				its[30] = StringHelper.isNotEmpty(acre.getHistroyDesc()) ? "是"
						: "否";
				its[31] = StringHelper.isNotEmpty(acre.getEcologicalDesc()) ? "是"
						: "否";
				its[32] = StringHelper.isNotEmpty(acre.getCivilianDesc()) ? "是"
						: "否";
				its[33] = StringHelper.isNotEmpty(acre.getTourDesc()) ? "是"
						: "否";
				its[34] = StringHelper.isNotEmpty(acre.getIndustryDesc()) ? "是"
						: "否";
				its[35] = StringHelper.isNotEmpty(acre.getFisheryDesc()) ? "是"
						: "否";
				its[36] = StringHelper.isNotEmpty(acre.getOtherDesc()) ? "是"
						: "否";

				its[37] = acre.getViewSpot();
				its[38] = acre.getCulturalAct();
				its[39] = acre.getCulturalActArea();
				its[40] = acre.getRuralPark();
				its[41] = acre.getRuralParkArea();
				its[42] = acre.getRuralSquare();
				its[43] = acre.getSquareArea();
				its[44] = acre.getHealthStation();
				its[45] = acre.getHealthStationArea();
				its[46] = acre.getVillageToilet();
				its[47] = acre.getVillageToiletArea();
				its[48] = acre.getPublicService();
				// its[49] = 0;
				if (StringHelper.isNotEmpty(acre.getLeaderName())
						|| StringHelper.isNotEmpty(acre.getLeaderName2())) {
					its[49] = "是";
				} else {
					its[49] = "否";
				}

				if (acre.getWorkGroupCount() != 0) {
					its[50] = "是";
				} else {
					its[50] = "否";
				}
				its[51] = acre.getWorkGroupCount();
				its[52] = acre.getExpertGroup();

				its[53] = acre.getGeneralPlan();
				its[54] = acre.getAreaPlan();
				its[55] = acre.getDepthPlanning();

			} else {
				__int(its, 7, acre.getNatrualruralNum());
				__dou(its, 8, acre.getRuralArea());
				__dou(its, 9, acre.getArableLand());
				__int(its, 10, acre.getHouseHold());
				__int(its, 11, acre.getPoorPopulation());
				__int(its, 12, acre.getLabor());
				__int(its, 13, acre.getPoorHouseHold());
				__int(its, 14, acre.getPoorPopulation());
				__int(its, 15, acre.getLowHouseHold());
				__int(its, 16, acre.getLowPopulation());
				__int(its, 17, acre.getWubaoHouseHold());
				__int(its, 18, acre.getDangerHouse());
				__boo(its, 19, acre.getPoorVillage());

				__dou(its, 20, acre.getAnnualIncome_13());
				__dou(its, 21, acre.getAnnualIncome_14());
				__dou(its, 22, acre.getAnnualIncome_15());
				__dou(its, 23, acre.getAnnualIncome_16());
				__dou(its, 24, acre.getAnnualIncome_17());
				__dou(its, 25, acre.getCollectivityAnnulIncome_13());
				__dou(its, 26, acre.getCollectivityAnnulIncome_14());
				__dou(its, 27, acre.getCollectivityAnnulIncome_15());
				__dou(its, 28, acre.getCollectivityAnnulIncome_16());
				__dou(its, 29, acre.getCollectivityAnnulIncome_17());

				__nem(its, 30, acre.getHistroyDesc());
				__nem(its, 31, acre.getEcologicalDesc());
				__nem(its, 32, acre.getCivilianDesc());
				__nem(its, 33, acre.getTourDesc());
				__nem(its, 34, acre.getIndustryDesc());
				__nem(its, 35, acre.getFisheryDesc());
				__nem(its, 36, acre.getOtherDesc());

				__int(its, 37, acre.getViewSpot());

				__int(its, 38, acre.getCulturalAct());
				__dou(its, 39, acre.getCulturalActArea());
				__int(its, 40, acre.getRuralPark());
				__dou(its, 41, acre.getRuralParkArea());
				__int(its, 42, acre.getRuralSquare());
				__dou(its, 43, acre.getSquareArea());
				__int(its, 44, acre.getHealthStation());
				__dou(its, 45, acre.getHealthStationArea());
				__int(its, 46, acre.getVillageToilet());
				__dou(its, 47, acre.getVillageToiletArea());

				__boo(its, 48, acre.getPublicService());
				// 是否有挂点领导
				__nem(its, 49, acre.getLeaderName());
				// 是否成立工作小组
				// 待
				__int(its, 50, acre.getWorkGroupCount());

				__int(its, 51, acre.getWorkGroupCount());
				__boo(its, 52, acre.getExpertGroup());
				__boo(its, 53, acre.getGeneralPlan());
				__boo(its, 54, acre.getAreaPlan());
				__boo(its, 55, acre.getDepthPlanning());

			}
		}

		return sts.values();
	}

	private void __nem(Object[] its, int i, String s) {
		if (StringHelper.isNotEmpty(s)) {
			its[i] = (Integer) its[i] + 1;
		}
	}

	private boolean __boo(Object[] its, int i, String s) {
		if ("是".equals(s)) {
			its[i] = (Integer) its[i] + 1;
			return true;
		}
		return false;
	}

	private void __int(Object[] its, int i, int v) {
		its[i] = (Integer) its[i] + v;
	}

	private void __dou(Object[] its, int i, double v) {
		its[i] = DoubleHelper.add(v, its[i]);
	}

	// private final String statistics_show =
	// "select x.modelArea.batch,x.reportAnnual,x.cityName,x.county,x.name,x.themeName";
	// @Override
	// public Collection<Object[]> advanStatistics(String batch) {
	// String ql =statistics_show +
	// " from AdministrationCoreRuralEntity x where x.deleted=false and x.modelArea.deleted=false and x.modelArea.batch=? and x.modelArea.status>0 order by x.modelArea.ordinalModel asc";
	// return null;
	// }

	@Override
	public List<AdministrationRuralEntity> annualIncome(Long id) {
		// String
		// ql="select y.batch,y.county,x.name,x.annualIncome_13,x.annualIncome_14,x.annualIncome_15,x.annualIncome_16,x.annualIncome_17 "
		// +"from AdministrationRuralEntity x left join x.modelArea y " +
		// "where x.deleted=false and y.deleted=false order by y.batch,y.ordinalModel";
		String ql = "select x from AdministrationRuralEntity x where x.deleted=false and x.modelArea.deleted=false order by x.modelArea.batch,x.modelArea.ordinalModel";
		return __list(AdministrationRuralEntity.class, ql);
	}

//	public Collection<Object[]> showItems(Collection<Object[]> items) {
//		List<Object[]> list = new ArrayList<Object[]>();
//
//		int i = 1;
//		double B5 = 0, B6 = 0, x20 = 0, x21 = 0, x22 = 0, x23 = 0, x24 = 0, x25 = 0, x26 = 0, x27 = 0, x28 = 0, x29 = 0;
//		int B4 = 0, B7 = 0, B8 = 0, B9 = 0, B10 = 0, B11 = 0, B12 = 0, B13 = 0, B14 = 0, B15 = 0, B38 = 0, B461 = 0;
//		int B39 = 0, B40 = 0, B41 = 0, B42 = 0, B43 = 0;
//		double B391 = 0, B401 = 0, B411 = 0, B421 = 0, B431 = 0;
//		int B16 = 0, B31 = 0, B32 = 0, B33 = 0, B34 = 0, B35 = 0, B36 = 0, B37 = 0, B44 = 0, B45 = 0;
//		int B46 = 0, B47 = 0, B48 = 0, B49 = 0, B50 = 0;
//
//		for (Object[] it : items) {
//			Object[] o = new Object[26];
//			o[0] = i++;
//			int index = 1;
//			for (Object obj : it) {
//				o[index++] = obj;
//			}
//			if (it[7] instanceof Number) {
//				B4 += ((Number) it[7]).intValue();
//			}
//
//			B5 = add(B5, it[8]);
//
//			B6 = add(B6, it[9]);
//
//			if (it[10] instanceof Number) {
//				B7 += ((Number) it[10]).intValue();
//			}
//			if (it[11] instanceof Number) {
//				B8 += ((Number) it[11]).intValue();
//			}
//			if (it[12] instanceof Number) {
//				B9 += ((Number) it[12]).intValue();
//			}
//			if (it[13] instanceof Number) {
//				B10 += ((Number) it[13]).intValue();
//			}
//			if (it[14] instanceof Number) {
//				B11 += ((Number) it[14]).intValue();
//			}
//			if (it[15] instanceof Number) {
//				B12 += ((Number) it[15]).intValue();
//			}
//			if (it[16] instanceof Number) {
//				B13 += ((Number) it[16]).intValue();
//			}
//			if (it[17] instanceof Number) {
//				B14 += ((Number) it[17]).intValue();
//			}
//			if (it[18] instanceof Number) {
//				B15 += ((Number) it[18]).intValue();
//			}
//			if ("是".equals(it[19])) {
//				B16 += 1;
//			}
//
//			x20 = add(x20, it[20]);
//			x21 = add(x21, it[21]);
//			x22 = add(x22, it[22]);
//			x23 = add(x23, it[23]);
//			x24 = add(x24, it[24]);
//			x25 = add(x25, it[25]);
//			x26 = add(x26, it[26]);
//			x27 = add(x27, it[27]);
//			x28 = add(x28, it[28]);
//			x29 = add(x29, it[29]);
//
//			// 资源优势
//			if (it[30].equals("是")) {
//				B31 += 1;
//			}
//
//			list.add(o);
//		}
//		Object[] o = new Object[26];
//		o[0] = null;
//		o[1] = "全省汇总";
//		o[2] = null;
//		Object[] var = new Object[] { B4, B5, B6, B7, B8, B9, B10, B11, B12,
//				B13, B14, B15, B16, x20, x21, x22, x23, x24, x25, x26, x27,
//				x28, x29 };
//		for (i = 3; i < 26; i++) {
//			o[i] = var[i - 3];
//		}
//		list.add(o);
//
//		return list;
//	}
	/**
	 * 基本情况所需数据
	 */
	public Collection<Object[]> excelValue(String batch) {
		String ql = "select x.modelArea.county ,x.name ,x.natrualruralNum ,x.ruralArea ,x.arableLand ,"
				+ "x.houseHold ,x.population ,x.labor ,x.poorHouseHold ,x.poorPopulation ,x.lowHouseHold ,x.lowPopulation ,x.wubaoHouseHold ,x.dangerHouse ,x.poorVillage ,"
				+ "x.annualIncome_13 ,x.annualIncome_14 ,x.annualIncome_15 ,x.annualIncome_16 ,x.annualIncome_17 ,x.collectivityAnnulIncome_13 ,x.collectivityAnnulIncome_14 ,x.collectivityAnnulIncome_15 ,x.collectivityAnnulIncome_16 ,x.collectivityAnnulIncome_17 "
				+ "from AdministrationCoreRuralEntity x where x.deleted=false and x.modelArea.deleted=false and x.modelArea.batch=? and x.modelArea.status>0 order by x.modelArea.ordinalModel asc";
		List<Object[]> items = __list(
				Object[].class, ql, batch);


		return items;
	}
	/**
	 * 
	 * 农村公共服务情况所需数据
	 * 
	 */
	public Collection<Object[]> excelValue3(String batch) {
		batch = StringHelper.get(batch, "一");
		String ql = "select x.modelArea.county ,x.name ,x.culturalAct ,x.culturalActArea ,x.ruralPark ,"
				+ "x.ruralParkArea ,x.ruralSquare ,x.squareArea ,x.healthStation ,x.healthStationArea ,x.villageToilet ,x.villageToiletArea ,x.publicService "
				+ "from AdministrationCoreRuralEntity x where x.deleted=false and x.modelArea.deleted=false and x.modelArea.batch=? and x.modelArea.status>0 order by x.modelArea.ordinalModel asc";
		List<Object[]> items = __list(
				Object[].class, ql, batch);
		
//		its[38] = acre.getCulturalAct();
//		its[39] = acre.getCulturalActArea();
//		its[40] = acre.getRuralPark();
//		its[41] = acre.getRuralParkArea();
//		its[42] = acre.getRuralSquare();
//		its[43] = acre.getSquareArea();
//		its[44] = acre.getHealthStation();
//		its[45] = acre.getHealthStationArea();
//		its[46] = acre.getVillageToilet();
//		its[47] = acre.getVillageToiletArea();
//		its[48] = acre.getPublicService();
		
		return items;
	}
}
