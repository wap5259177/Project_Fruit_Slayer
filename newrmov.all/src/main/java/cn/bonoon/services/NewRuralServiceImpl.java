package cn.bonoon.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.bonoon.core.NewRuralService;
import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.RuralExpertGroupEntity;
import cn.bonoon.entities.RuralUnitEntity;
import cn.bonoon.entities.RuralWorkGroupEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.io.DirectoryStrategy;
import cn.bonoon.kernel.io.FileInfo;
import cn.bonoon.kernel.io.FileManager;
import cn.bonoon.kernel.io.FilenameStrategy;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.util.DoubleHelper;

@Service
@Transactional(readOnly = true)
// public class NewRuralServiceImpl extends AbstractService<NewRuralEntity>
// implements NewRuralService {
public class NewRuralServiceImpl extends
		ConfigurableModelAreaService<NewRuralEntity> implements NewRuralService {

	@Autowired
	private FileManager fileManager;

	@Override
	protected NewRuralEntity __update(OperateEvent event, NewRuralEntity entity) {
		// 提交的时候检查是否允许提交(当省级关闭填报功能的时候不允许再修改提交)
		checkAndThrowError(entity.getModelArea(), event);
		boolean submit = "true".equalsIgnoreCase(event
				.getString("applicant-submit"));
		if (submit) {
			String errmsg = "";
			int i = 1;
			if (StringHelper.isEmpty(entity.getCode())) {
				errmsg += i++ + ". 请填写序号！\n\r\t";
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
			if (entity.getRuralArea() < entity.getArableLand()) {
				errmsg += i++ + ". 总面积必须大于耕地面积！\n\r\t";
			}
			if (entity.getHouseHold() > entity.getPopulation()) {
				errmsg += i++ + ". 户数必须小于人口数！\n\r\t";
			}
			if (entity.getHardBottom() < 0 || entity.getHardBottom() > 100) {
				errmsg += i++ + ". 道路和入户路硬底化率只能0-100之间！\n\r\t";
			}
			if (entity.getToiletPercent() < 0
					|| entity.getToiletPercent() > 100) {
				errmsg += i++ + ". 使用卫生厕所户数所占比例只能0-100之间！\n\r\t";
			}
			if ("是".equals(entity.getPoorVillage())
					&& StringHelper.isEmpty(entity.getHelpUnit())) {
				errmsg += i++ + ". 请填写帮扶单位名称！\n\r\t";
			}

			if ("是".equals(entity.getFamousVillage())
					&& StringHelper.isEmpty(entity.getFamousBatch())) {
				errmsg += i++ + ". 请选择广东名村批次！\n\r\t";
			}
			if ("是".equals(entity.getPushVillage())
					&& StringHelper.isEmpty(entity.getPvAnnual())) {
				errmsg += i++ + ". 请选择\"两不具备\"整村推进村年度！\n\r\t";
			}
			if ("是".equals(entity.getTapWater())
					&& StringHelper.isEmpty(entity.getTapAnnual())) {
				errmsg += i++ + ". 请选择通自来水年度！\n\r\t";
			}
			if ("是".equals(entity.getHyFix())
					&& StringHelper.isEmpty(entity.getFixAnnual())) {
				errmsg += i++ + ". 请选择已完成环境卫生整治年度！\n\r\t";
			}
			if ("是".equals(entity.getCleanTeam()) && entity.getCleaners() <= 0) {
				errmsg += i++ + ". 请填写保洁队伍的人数！\n\r\t";
			}
			if ("是".equals(entity.getBadWater())
					&& StringHelper.isEmpty(entity.getBadWaterAnn())) {
				errmsg += i++ + ". 请选择建立污水处理设施年度！\n\r\t";
			}
			if ("是".equals(entity.getFaceView())) {
				if (StringHelper.isEmpty(entity.getFaceStyle())) {
					errmsg += i++ + ". 请填写完成统一民居外立面风貌的风格！\n\r\t";
				}
				if (StringHelper.isEmpty(entity.getFvAnnual())) {
					errmsg += i++ + ". 请选择完成统一民居外立面风貌年度！\n\r\t";
				}
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
			if ("是".equals(entity.getCouncil())) {
				if (StringHelper.isEmpty(entity.getCouncilAnnual())) {
					errmsg += i++ + ". 请选建立择村民理事会年度！\n\r\t";
				}
				if (entity.getCouncilMember() <= 0) {
					errmsg += i++ + ". 请填写理事会成员数！\n\r\t";
				}
			}

			if (!errmsg.isEmpty()) {
				throw new RuntimeException(errmsg);
			}

			// 如果挂点县领导没有填写名字,则后台的数据应该被清空
			// if(StringHelper.isEmpty(entity.getLeaderName())){
			// entity.setLeaderHP("");
			// entity.setLeaderJob("");
			// entity.setUnitProperty("");
			// }
			// if(StringHelper.isEmpty(entity.getLeaderName2())){
			// entity.setLeaderHP2("");
			// entity.setLeaderJob2("");
			// entity.setUnPro2("");
			// }

			entity.setStatus(1);
			//
		} else {
			entity.setStatus(0);
		}

		if ("是".equals(entity.getCouncil())) {
			// 读取文档
			MultipartFile file = event.getFile("councilFile");
			if (null != file && !file.isEmpty()) {
				try {
					// 保存 上传纳入示范片建设的申请书电子版(带签名和手摸)
					FileInfo fi = fileManager.save("modelarea/area_"
							+ entity.getModelArea().getId() + "/rural/rural_"
							+ entity.getId(), DirectoryStrategy.YEAR_MONTH,
							FilenameStrategy.MD5, file);
					entity.setCouncilFilePath(fi.getRelativePath());
					entity.setCouncilFileName(fi.getOriginalFilename());
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			entity.setCouncilAnnual("");
			entity.setCouncilMember(0);
			entity.setCouncilName1("");
			entity.setCouncilName2("");
			entity.setCouncilHP1("");
			entity.setCouncilHP2("");
			entity.setWorkSituation("");
			entity.setConstitu("");
			entity.setCouncilFileName(null);
			entity.setCouncilFilePath(null);
		}
		if (!"行政村".equals(entity.getType())) {
			entity.setType("自然村");
		}

		/*
		 * 1.先判断自然村是否已经创建行政村如果是就直接使用
		 * 2.如果没有的话就找出相同行政村下面的自然村然后判断是否已经创建行政村如果创建就直接使用 3.如果不存在就创建新的行政村
		 */
		// AdministrationCoreRuralEntity are = entity.getAdminRural();
		// if(null == are){
		// PlaceEntity pe = entity.getPlace();//TODO -----------------------
		// if(null == pe) throw new RuntimeException("没有行政村！");
		// String ql =
		// "select x from NewRuralEntity x where x.placeId=? and x.adminRural is not null";
		// NewRuralEntity other = __first(NewRuralEntity.class, ql, pe.getId());
		// if(null != other){
		// are = other.getAdminRural();
		// }else{
		// are = new AdministrationCoreRuralEntity();
		// are.setCreateAt(event.now());
		// are.setCreatorId(event.getId());
		// are.setCreatorName(event.getUsername());
		// are.setOwnerId(event.getOwnerId());
		// are.setModelArea(entity.getModelArea());
		// are.setPlace(pe);
		// are.setName(pe.getName());
		// are.setTown(entity.getTown());
		// entityManager.persist(are);
		// }
		// entity.setAdminRural(are);
		// }
		// __copy(entity, are);
		// entityManager.merge(are);
		// // entityManager.persist(are);
		// // 处理工作小组、专家、单位列表
		// // 删除原来的记录
		// // Long eid = entity.getId();
		// // __exec("delete from RuralExpertGroupEntity where newRural.id=?",
		// eid);
		//
		// Long aid = are.getId();
		// __exec("delete from RuralUnitEntity where adminRural.id=?", aid);
		// __exec("delete from RuralWorkGroupEntity where adminRural.id=?",
		// aid);
		// __exec("delete from RuralExpertGroupEntity where adminRural.id=?",
		// aid);
		// IRuralEditor mae = (IRuralEditor) event.getSource();
		// int count = 0;
		// if("是".equals(entity.getExpertGroup())){
		// if (null != mae.getExpertgroupItems()) {
		// for (String i : mae.getExpertgroupItems()) {
		// String name = event.getString("en_" + i);
		// if(!submit || StringHelper.isNotEmpty(name)){
		// RuralExpertGroupEntity reg = new RuralExpertGroupEntity();
		// reg.setAdminRural(are);
		// reg.setExpertJob(event.getString("ej_" + i));
		// reg.setExpertName(name);
		// reg.setExpertPhone(event.getString("ep_" + i));
		// reg.setExpertRemark(event.getString("er_" + i));
		// entityManager.persist(reg);
		// count++;
		// }
		// }
		// }
		// if(submit && count <= 0){
		// throw new RuntimeException("请填写指导专家！");
		// }
		// }
		// entity.setExpertGroupCount(count);
		// count = 0;
		// if (null != mae.getUnitItems()) {
		// for (String i : mae.getUnitItems()) {
		// String name = event.getString("ucn_" + i);
		// if(!submit || StringHelper.isNotEmpty(name)){
		// RuralUnitEntity rue = new RuralUnitEntity();
		// //rue.setNewRural(entity);
		// rue.setAdminRural(are);
		// rue.setContactName(name);
		// rue.setRegisteredAddress(event.getString("ra_" + i));
		// rue.setUnitName(event.getString("un_" + i));
		// rue.setUnitPhone(event.getString("up_" + i));
		// entityManager.persist(rue);
		// count++;
		// }
		// }
		// }
		// entity.setUnitCount(count);
		// count = 0;
		// if (null != mae.getWorkgroupItems()) {
		// for (String i : mae.getWorkgroupItems()) {
		// String name = event.getString("wn_" + i);
		// if(!submit || StringHelper.isNotEmpty(name)){
		// RuralWorkGroupEntity rwg = new RuralWorkGroupEntity();
		// //rwg.setNewRural(entity);
		// rwg.setAdminRural(are);
		// rwg.setUnitJob(event.getString("uj_" + i));
		// rwg.setWorkName(name);
		// rwg.setWorkPhone(event.getString("wp_" + i));
		// rwg.setWorkRemark(event.getString("wr_" + i));
		// entityManager.persist(rwg);
		// }
		// count++;
		// }
		// }
		// entity.setWorkGroupCount(count);
		// entityManager.merge(are);
		return super.__update(event, entity);
	}

	@Override
	protected NewRuralEntity __save(OperateEvent event, NewRuralEntity entity) {
		Long oid = event.getOwnerId();
		String ql = "select x from ModelAreaEntity x where x.ownerId=? and x.deleted=false";
		ModelAreaEntity ma = __first(ModelAreaEntity.class, ql, oid);
		// 提交的时候检查是否允许提交(当省级关闭填报功能的时候不允许再修改提交)
		checkAndThrowError(ma, event);
		entity.setModelArea(ma);

		entity.setType("自然村");
		entity.setName(entity.getAdminRural().getName());
		entity.setTown(entity.getAdminRural().getTown());

		return super.__save(event, entity);
	}

	private void __copy(NewRuralEntity entity, AdministrationCoreRuralEntity are) {
		// 6领导
		are.setLeaderHP(entity.getLeaderHP());
		are.setLeaderName(entity.getLeaderName());
		are.setLeaderJob(entity.getLeaderJob());
		are.setLeaderName2(entity.getLeaderName2());
		are.setLeaderJob2(entity.getLeaderJob2());
		are.setLeaderHP2(entity.getLeaderHP2());
		are.setUnitProperty(entity.getUnitProperty());
		are.setUnPro2(entity.getUnPro2());
		// entityManager.persist(are);
		// 8规划进展
		are.setGeneralPlan(entity.getGeneralPlan());
		are.setDepthPlanning(entity.getDepthPlanning());
		are.setAreaPlan(entity.getAreaPlan());
		are.setApDate(entity.getApDate());
		are.setDpDate(entity.getDpDate());
		are.setGpDate(entity.getGpDate());
		are.setExpertGroup(entity.getExpertGroup());
	}

	@Override
	public List<RuralWorkGroupEntity> workGroups(Long id) {
		return __list(RuralWorkGroupEntity.class,
				"select x from RuralWorkGroupEntity x where x.newRural.id=?",
				id);
	}

	@Override
	public List<RuralExpertGroupEntity> expertGroups(Long id) {
		return __list(RuralExpertGroupEntity.class,
				"select x from RuralExpertGroupEntity x where x.newRural.id=?",
				id);
	}

	@Override
	public List<RuralUnitEntity> ruralUnits(Long id) {
		return __list(RuralUnitEntity.class,
				"select x from RuralUnitEntity x where x.newRural.id=?", id);
	}

	@Override
	public List<PlaceEntity> village(Long oId) {
		// 列出县的村委会的信息
		String ql = "select x from PlaceEntity x where x.parent.id=?";
		return __list(PlaceEntity.class, ql, oId);
	}

	@Override
	public List<PlaceEntity> towns(Long oId) {
		// 列出县的所有镇的信息
		String ql = "select x from PlaceEntity x where x.id=?";
		return __list(PlaceEntity.class, ql, oId);
	}

	@Override
	public Collection<Object[]> statistics(String batch) {
		batch = StringHelper.get(batch, "一"); // and x.modelArea.status>0
		String ql = "select x from NewRuralEntity x where x.deleted=false and x.modelArea.deleted=false and x.modelArea.batch=? and x.modelArea.status>0 order by x.modelArea.ordinalModel asc";
		List<NewRuralEntity> items = __list(NewRuralEntity.class, ql, batch);

		return __statistics(items);
	}

	private Collection<Object[]> __statistics(List<NewRuralEntity> items) {
		Map<Long, Object[]> sts = new HashMap<>();

		for (NewRuralEntity nre : items) {
			Object[] its = sts.get(nre.getId());
			if (null == its) {
				ModelAreaEntity ma = nre.getModelArea();
				its = new Object[48];
				sts.put(nre.getId(), its);
				// 0~5
				its[0] = ma.getBatch();
				its[1] = ma.getReportAnnual();
				its[2] = ma.getCityName();
				its[3] = ma.getCounty();
				its[4] = ma.getName();
				its[5] = ma.getThemeName();

				its[6] = nre.getAdminRural().getName();
				its[7] = nre.getNaturalVillage();
				// （一）基本情况
				its[8] = nre.getRuralArea();
				its[9] = nre.getArableLand();
				its[10] = nre.getHouseHold();
				its[11] = nre.getPopulation();
				its[12] = nre.getLabor();
				its[13] = nre.getPoorHouseHold();
				its[14] = nre.getPoorPopulation();
				its[15] = nre.getLowHouseHold();
				its[16] = nre.getLowPopulation();
				its[17] = nre.getWubaoHouseHold();
				its[18] = nre.getDangerHouse();
				// 是否是贫困村
				its[19] = nre.getFamousVillage();
				its[20] = nre.getPushVillage();

				// （二）基础设施建设和环境卫生整治情况
				its[21] = nre.getHardBottom();
				// 是否通自来水
				its[22] = nre.getTapWater();
				its[23] = nre.getWaterBase();
				its[24] = nre.getSmallWater();
				its[25] = nre.getFarmland();
				// 是否已完成环境卫生整治
				its[26] = nre.getHyFix();
				// 是否已开展过村庄垃圾、生活污水治理
				its[27] = nre.getVillageManage();
				// 是否建立村保洁队伍
				its[28] = nre.getCleanTeam();
				its[29] = nre.getCleaners();
				its[30] = nre.getToilet();
				// 污水处理
				its[31] = nre.getBadWater();

				// （三）农村旧房政治情况
				its[32] = nre.getNoValue();
				its[33] = nre.getBuildAgain();
				its[34] = nre.getOldProtect();
				// 是否编制旧房整治改造规划
				its[35] = nre.getChangePlan();
				its[36] = nre.getDesignPic();
				its[37] = nre.getPlanCount();
				its[38] = nre.getFinishCount();
				its[39] = nre.getFaceView();

				// （四）村民理事会
				its[40] = nre.getCouncil();
				its[41] = nre.getCouncilMember();
				its[42] = nre.getConstitu();
				// （五）民间问题调查梳理情况
				its[43] = nre.getFinishNatureVillageNum();
				its[44] = nre.getTeasedProjectNum();
				its[45] = nre.getTroubleshooting();
				its[46] = nre.getResolvedTroubleshooting();
				its[47] = nre.getModelArea().getOrdinalModel();
				/*
				 * TODO:its[34]:统一民居外立面风貌风格列表,需要列出所有的风格.暂时不处理.
				 */

				// its[37] = StringHelper.isNotEmpty(nre.getHistroyDesc()) ? 1 :
				// 0;
				// its[38] = StringHelper.isNotEmpty(nre.getEcologicalDesc()) ?
				// 1 : 0;
				// its[39] = StringHelper.isNotEmpty(nre.getCivilianDesc()) ? 1
				// : 0;
				// its[40] = StringHelper.isNotEmpty(nre.getTourDesc()) ? 1 : 0;
				// its[41] = StringHelper.isNotEmpty(nre.getIndustryDesc()) ? 1
				// : 0;
				// its[42] = StringHelper.isNotEmpty(nre.getFisheryDesc()) ? 1 :
				// 0;
				// its[43] = StringHelper.isNotEmpty(nre.getOtherDesc()) ? 1 :
				// 0;

				// its[44] = nre.getViewSpot();
				/*
				 * TODO:its[43]:现有的或正在打造的旅游景点或节点名称列表.暂时不处理.
				 */

				// its[46] = nre.getCulturalAct();
				// its[47] = nre.getCulturalActArea();
				// its[48] = nre.getRuralPark();
				// its[49] = nre.getRuralParkArea();
				//
				// its[50] = nre.getRuralSquare();
				// its[51] = nre.getSquareArea();// dou
				// its[52] = nre.getHealthStation();
				// its[53] = nre.getHealthStationArea();// dou
				// its[54] = nre.getVillageToilet();
				// its[55] = nre.getVillageToiletArea();// dou
				// its[56] = "是".equals(nre.getPublicService()) ? 1 : 0;
				// its[57] = 0;
				//
				// its[58] = 0;
				// its[59] = 0;
				//
				// its[60] = "是".equals(nre.getExpertGroup()) ? 1 : 0;
				// its[61] = "是".equals(nre.getGeneralPlan()) ? 1 : 0;
				// its[62] = "是".equals(nre.getAreaPlan()) ? 1 : 0;
				// its[63] = "是".equals(nre.getDepthPlanning()) ? 1 : 0;

				// 建立村名理事会村个数

				// its[64] =nre.getCouncil();
				// its[65] = nre.getCouncilMember();
				// //是否制定村规民约和章程
				// its[66] = nre.getConstitu();
				// its[67] = nre.getAnnualIncome_13();
				// its[68] = nre.getAnnualIncome_14();
				// its[69] = nre.getAnnualIncome_15();
			} else {
				__dou(its, 8, nre.getRuralArea());
				__dou(its, 9, nre.getArableLand());
				__int(its, 10, nre.getHouseHold());
				__int(its, 11, nre.getPopulation());
				__int(its, 12, nre.getLabor());
				__int(its, 13, nre.getPoorHouseHold());
				__int(its, 14, nre.getPoorPopulation());
				__int(its, 15, nre.getLowHouseHold());
				__int(its, 16, nre.getLowPopulation());
				__int(its, 17, nre.getWubaoHouseHold());
				__int(its, 18, nre.getDangerHouse());
				__boo(its, 19, nre.getFamousVillage());
				__boo(its, 20, nre.getPushVillage());

				__dou(its, 21, nre.getHardBottom());
				__boo(its, 22, nre.getTapWater());
				__int(its, 23, nre.getWaterBase());
				__int(its, 24, nre.getSmallWater());
				__dou(its, 25, nre.getFarmland());
				__boo(its, 26, nre.getHyFix());
				__boo(its, 27, nre.getVillageManage());
				__boo(its, 28, nre.getCleanTeam());
				__int(its, 29, nre.getCleaners());
				__int(its, 30, nre.getToilet());
				__boo(its, 31, nre.getBadWater());

				__int(its, 32, nre.getNoValue());
				__int(its, 33, nre.getBuildAgain());
				__int(its, 34, nre.getOldProtect());
				__boo(its, 35, nre.getChangePlan());
				__boo(its, 36, nre.getDesignPic());
				__int(its, 37, nre.getPlanCount());
				__int(its, 38, nre.getFinishCount());
				__boo(its, 39, nre.getFaceView());

				__boo(its, 40, nre.getCouncil());
				__int(its, 41, nre.getCouncilMember());
				__boo(its, 42, nre.getConstitu());

				__int(its, 43, nre.getFinishNatureVillageNum());
				__int(its, 44, nre.getTeasedProjectNum());
				__int(its, 45, nre.getTroubleshooting());
				__int(its, 46, nre.getResolvedTroubleshooting());
				//
				// if (__boo(its, 24, nre.getCleanTeam())) {
				// __int(its, 25, nre.getCleaners());
				// }
				// __int(its, 26, nre.getToilet());
				// // othor:wzg
				// __boo(its, 27, nre.getBadWater());
				// __int(its, 28, nre.getNoValue());
				// __int(its, 29, nre.getBuildAgain());
				// __int(its, 30, nre.getOldProtect());
				// __boo(its, 31, nre.getChangePlan());
				// __boo(its, 32, nre.getDesignPic());
				// __int(its, 33, nre.getPlanCount());
				// __int(its, 34, nre.getFinishCount());
				// if (__boo(its, 33, nre.getFaceView())) {
				// if (null == its[34]) {
				// its[34] = nre.getFaceStyle();
				// } else {
				// its[34] = its[34] + "\n" + nre.getFaceStyle();
				// }
				// }

				// __nem(its, 37, nre.getHistroyDesc());
				// __nem(its, 38, nre.getEcologicalDesc());
				// __nem(its, 39, nre.getCivilianDesc());
				// __nem(its, 40, nre.getTourDesc());
				// __nem(its, 41, nre.getIndustryDesc());
				// __nem(its, 42, nre.getFisheryDesc());
				// __nem(its, 43, nre.getOtherDesc());
				//
				// __int(its, 44, nre.getViewSpot());

				// __int(its, 46, nre.getCulturalAct());
				// __dou(its, 47, nre.getCulturalActArea());
				// __int(its, 48, nre.getRuralPark());
				// __dou(its, 49, nre.getRuralParkArea());
				//
				// __int(its, 50, nre.getRuralSquare());
				// __dou(its, 51, nre.getSquareArea());
				// __int(its, 52, nre.getHealthStation());
				// __dou(its, 53, nre.getHealthStationArea());
				// __int(its, 54, nre.getVillageToilet());
				// __dou(its, 55, nre.getVillageToiletArea());
				// __boo(its, 54, nre.getPublicService());
				//
				// __boo(its, 60, nre.getExpertGroup());
				// __boo(its, 61, nre.getGeneralPlan());
				// __boo(its, 62, nre.getAreaPlan());
				// __boo(its, 63, nre.getDepthPlanning());

				// if (__boo(its, 64, nre.getCouncil())) {
				// __int(its, 65, nre.getCouncilMember());
				// }
				// __boo(its, 66, nre.getConstitu());
				// __dou(its, 67, nre.getAnnualIncome_13());
				// __dou(its, 68, nre.getAnnualIncome_14());
				// __dou(its, 69, nre.getAnnualIncome_15());
			}
			// if (nre.getViewSpot() > 0) {// its[43]
			// String str = StringHelper.join(',',
			// nre.getViewSpot1(),
			// nre.getViewSpot2(),
			// nre.getViewSpot3());
			// if (StringHelper.isNotEmpty(str)) {
			// if (null == its[43]) {
			// its[43] = str;
			// } else {
			// its[43] = its[43] + "\n" + str;
			// }
			// }
			// }
			// if (StringHelper.isNotEmpty(nre.getLeaderName())
			// || StringHelper.isNotEmpty(nre.getLeaderName2())) {
			// its[57] = (Integer) its[57] + 1;
			// }
			// if (nre.getWorkGroupCount() > 0) {
			// its[58] = (Integer) its[58] + 1;
			// its[59] = (Integer) its[59] + nre.getWorkGroupCount();
			// }
		}
	
		return sort(sts);
	}
/**
 * 由於sts用了hashMap導致排序變亂這裡写了个算法重新排序
 * @param sts
 * @return
 */
	private Collection<Object[]> sort(Map<Long,Object[]> sts) {
		List<Object[]> list = new ArrayList<Object[]>();
		int count = 0;
		for (Object[] var : sts.values()) {
			count++;
			System.out.println(var[47] + "" + var[4]);
			if (list.size() == 0) {
				list.add(var);
				continue;
			}
			if ((int) var[47] >= (int) list.get(list.size() - 1)[47]) {
				list.add(var);
			} else {
				for (int i = list.size() - 2; i > -1; i--) {
					Object[] var2 = list.get(i);
					if (i == 0 && (int) var[47] < (int) var2[47]) {
						for (int i2 = list.size(); i2 > i; i2--) {
							if (i2 == list.size()) {
								list.add(list.get(list.size() - 1));
							} else {
								list.set(i2, list.get(i2 - 1));
							}
						}
						list.set(i, var);
						break;
					}
					if ((int) var[47] >= (int) var2[47]) {
						for (int i2 = list.size(); i2 > i + 1; i2--) {
							if (i2 == list.size()) {
								list.add(list.get(list.size() - 1));
							} else {
								list.set(i2, list.get(i2 - 1));
							}
						}
						list.set(i + 1, var);
						break;
					}
				}
			}
		}
		for (Object[] o : list) {
			System.out.println("paixu :" + o[47] + "" + o[4] + "自然村名：" + o[7]);
		}
		System.out.println("排序后共有" + list.size() + "个排序前" + count);
		return list;
	}

	// private void __nem(Object[] its, int i, String s) {
	// if (StringHelper.isNotEmpty(s)) {
	// its[i] = (Integer) its[i] + 1;
	// }
	// }

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

	// 主题村情况汇总统计表
	@Override
	public Collection<Object[]> statisticsLocal(IOperator opt, String batch) { // and
																				// x.modelArea.status>0
		String ql = "select x from NewRuralEntity x where x.deleted=false and x.modelArea.deleted=false and x.modelArea.status>0 and x.modelArea.ownerId=? order by x.modelArea.ordinalModel asc";
		List<NewRuralEntity> items = __list(NewRuralEntity.class, ql,
				opt.getOwnerId());
		return __statistics(items);
	}

	@Override
	public Collection<Object[]> statisticsCity(IOperator opt, String batch) {
		String ql = "select x.place.id from UnitEntity x where x.id=?";
		Long pid = __first(Long.class, ql, opt.getOwnerId()); // and
																// x.modelArea.status>0
		ql = "select x from NewRuralEntity x where x.deleted=false and x.modelArea.deleted=false and x.modelArea.status>0 and x.modelArea.cityId=? order by x.modelArea.ordinalModel asc";
		List<NewRuralEntity> items = __list(NewRuralEntity.class, ql, pid);
		return __statistics(items);
	}

	@Override
	public List<FileEntity> medias(Long id, int code, String buildType) {
		String ql = "select x.directoryMedia.id from NewRuralEntity x where x.id=?";
		Long did = __first(Long.class, ql, id);
		if (null != did) {
			// ql =
			// "select x.name,x.createAt,x.remark,x.mapPath,x.issueAt from FileEntity x left join x.directory y where x.deleted=false and x.extattr1=? and x.extattr7=? and y.id=? group by x.name,x.createAt,x.remark,x.mapPath,x.issueAt order by x.issueAt";
			ql = "select x from FileEntity x left join x.directory y where x.deleted=false and x.extattr1=? and x.extattr7=? and y.id=?";
			return __list(FileEntity.class, ql, code, buildType, did);
		}
		return Collections.emptyList();
	}

	@Override
	public List<FileEntity> mediasTime(Long id) {
		String ql = "select x.directoryMedia.id from NewRuralEntity x where x.id=?";
		Long did = __first(Long.class, ql, id);
		if (null != did) {
			// ql =
			// "select x.name,x.createAt,x.remark,x.mapPath,x.issueAt from FileEntity x left join x.directory y where x.deleted=false and x.extattr1=? and x.extattr7=? and y.id=? group by x.name,x.createAt,x.remark,x.mapPath,x.issueAt order by x.issueAt";
			ql = "select x from FileEntity x left join x.directory y where x.deleted=false and y.id=?";
			return __list(FileEntity.class, ql, did);
		}
		return Collections.emptyList();
	}

	@Override
	public List<FileEntity> mediasTime1(Long id, int code) {
		String ql = "select x.directoryMedia.id from NewRuralEntity x where x.id=?";
		Long did = __first(Long.class, ql, id);
		if (null != did) {
			// ql =
			// "select x.name,x.createAt,x.remark,x.mapPath,x.issueAt from FileEntity x left join x.directory y where x.deleted=false and x.extattr1=? and x.extattr7=? and y.id=? group by x.name,x.createAt,x.remark,x.mapPath,x.issueAt order by x.issueAt";
			ql = "select x from FileEntity x left join x.directory y where x.deleted=false and x.extattr1=? and y.id=? and x.issueAt is not null order by x.issueAt desc";
			return __list(FileEntity.class, ql, code, did);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public AdministrationCoreRuralEntity getAdministrationRural(IOperator opt,
			Long id) {
		NewRuralEntity nre = entityManager.find(NewRuralEntity.class, id);
		PlaceEntity place = InternalRuralHelper.__findPlace(entityManager, nre);

		AdministrationCoreRuralEntity are = nre.getAdminRural();
		if (null != are) {
			PlaceEntity arPlace = are.getPlace();
			if (null != arPlace && arPlace.getId().equals(place.getId())) {
				return are;
			}
			are = null;
			nre.setAdminRural(null);
		}
		String ql = "select x.adminRural from NewRuralEntity x where x.placeId=? and x.adminRural is not null";
		List<AdministrationCoreRuralEntity> ars = __list(
				AdministrationCoreRuralEntity.class, ql, place.getId());
		for (AdministrationCoreRuralEntity ar : ars) {
			PlaceEntity arPlace = ar.getPlace();
			if (null != arPlace && arPlace.getId().equals(place.getId())) {
				are = ar;
				break;
			}
		}
		if (null == are) {
			// 还是创建一个吧，因为打开的时候上传附件时需要使用到行政村的ID
			are = new AdministrationCoreRuralEntity();
			are.setCreateAt(new Date());
			are.setCreatorId(opt.getId());
			are.setCreatorName(opt.getUsername());
			are.setOwnerId(opt.getOwnerId());
			are.setModelArea(nre.getModelArea());
			are.setPlace(place);
			are.setName(place.getName());
			are.setTown(nre.getTown());

			// TODO copy一些数据
			__copy(nre, are);

			entityManager.persist(are);

			Long nid = nre.getId();
			ql = "select x from RuralUnitEntity x where x.newRural.id=?";
			for (RuralUnitEntity ru : __list(RuralUnitEntity.class, ql, nid)) {
				ru.setAdminRural(are);
				ru.setNewRural(null);
				entityManager.merge(ru);
			}
			ql = "select x from RuralWorkGroupEntity x where x.newRural.id=?";
			for (RuralWorkGroupEntity rwg : __list(RuralWorkGroupEntity.class,
					ql, nid)) {
				rwg.setAdminRural(are);
				rwg.setNewRural(null);
				entityManager.merge(rwg);
			}
			ql = "select x from RuralExpertGroupEntity x where x.newRural.id=?";
			for (RuralExpertGroupEntity reg : __list(
					RuralExpertGroupEntity.class, ql, nid)) {
				reg.setAdminRural(are);
				reg.setNewRural(null);
				entityManager.merge(reg);
			}

		}
		nre.setAdminRural(are);
		entityManager.merge(nre);
		return are;
	}

	@Override
	public AdministrationCoreRuralEntity getAdministrationRural(Long id) {
		NewRuralEntity nre = entityManager.find(NewRuralEntity.class, id);
		AdministrationCoreRuralEntity are = nre.getAdminRural();
		return are;
	}

	@Override
	public List<RuralWorkGroupEntity> workGroupsByAdminRural(Long id) {
		return __list(RuralWorkGroupEntity.class,
				"select x from RuralWorkGroupEntity x where x.adminRural.id=?",
				id);
	}

	@Override
	public List<RuralUnitEntity> ruralUnitsByAdminRural(Long id) {
		return __list(RuralUnitEntity.class,
				"select x from RuralUnitEntity x where x.adminRural.id=?", id);
	}

	@Override
	public List<RuralExpertGroupEntity> experGroupByAdminRural(Long id) {
		return __list(
				RuralExpertGroupEntity.class,
				"select x from RuralExpertGroupEntity x where x.adminRural.id=?",
				id);
	}

	@Override
	public boolean check(Long id, IOperator opt) {
		String ql = "select x.modelArea.batch from NewRuralEntity x where id=? and x.deleted=false";
		return __config().check(__first(String.class, ql, id), opt, false);
	}

}

// @Override
// public DirectoryEntity dir_buildingOrCreate(IOperator opt, Long id,
// int intValue) {
// NewRuralEntity pe = __get(id);
// DirectoryEntity de = pe.getDirectoryImg();
// if (null == de) {
// DirectoryEntity parent = pe.getDirectory();
// if (null == parent) {
// parent = __create(opt, DirectoryExtattr.PROJECT, pe, pe
// .getModelArea().getDirectory());
// pe.setDirectory(parent);
// }
// de = __create(opt, "项目图片", DirectoryExtattr.PROJECT_IMG, pe, parent);// new
// // DirectoryEntity();
// // 更新目录到实体
// pe.setDirectoryImg(de);
// // 图片目录下面有三个目录，分别为：项目前、项目中、项目后
//
// DirectoryEntity d0 = __create(opt, "建设前",
// DirectoryExtattr.PROJECT_IMG_0, pe, de);
// DirectoryEntity d1 = __create(opt, "建设中",
// DirectoryExtattr.PROJECT_IMG_1, pe, de);
// DirectoryEntity d2 = __create(opt, "建设后",
// DirectoryExtattr.PROJECT_IMG_2, pe, de);
// if (intValue == DirectoryExtattr.PROJECT_IMG_0)
// return d0;
// if (intValue == DirectoryExtattr.PROJECT_IMG_1)
// return d1;
// return d2;
// }
// for (DirectoryEntity it : de.getChildren()) {
// if (it.getExtattr2() == intValue) {
// return it;
// }
// }
// return null;
// }

// private DirectoryEntity __create(IOperator event, int extattr,
// AbstractEntity entity, DirectoryEntity parent) {
// Long id = entity.getId();
// String name = entity.getName();
// DirectoryEntity dir = new DirectoryEntity();
// dir.setParent(parent);
// dir.setExtattr2(extattr);
// dir.setExtattr3(id);
// dir.setExtattr5(name);
// dir.setName(name);
// entityManager.persist(_init_entity(event, dir));
// return dir;
// }

// private AbstractEntity _init_entity(IOperator event, AbstractEntity entity) {
// entity.setCreateAt(new Date());
// entity.setCreatorId(event.getId());
// entity.setCreatorName(event.getUsername());
// entity.setOwnerId(event.getOwnerId());
// return entity;
// }

// private DirectoryEntity __create(IOperator event, String name, int extattr,
// AbstractEntity entity, DirectoryEntity parent) {
// DirectoryEntity dir = new DirectoryEntity();
// dir.setParent(parent);
// dir.setExtattr2(extattr);
// dir.setExtattr3(entity.getId());
// dir.setExtattr5(entity.getName());
// dir.setName(name);
// entityManager.persist(_init_entity(event, dir));
// return dir;
// }

// @Override
// public DirectoryEntity dir_building(Long id, int extattr) {
// NewRuralEntity pe = __get(id);
// DirectoryEntity de = pe.getDirectoryImg();
// if (null == de) {
// return null;
// }
// for (DirectoryEntity it : de.getChildren()) {
// if (it.getExtattr2() == extattr) {
// return it;
// }
// }
// return null;
// }
// ---------------------------以下可以忽略
// @Override
// protected NewRuralEntity __save(OperateEvent event, NewRuralEntity entity) {
// if (null == entity.getModelArea()) {
// throw new RuntimeException("请选择片区！");
// }
// super.__save(event, entity);
// _init_entity(event, entity);
// _init_directory(entity, event);
// return entity;
// }

// void _init_directory(NewRuralEntity entity, OperateEvent event) {
// // 查找区目录
// ModelAreaEntity modelArea = entity.getModelArea();
// DirectoryEntity dir_area = __first(DirectoryEntity.class,
// "from DirectoryEntity where extattr2=? and extattr3=?",
// DirectoryExtattr.AREA, modelArea.getId());
// // 创建村目录，父节点为区
// Long id = entity.getId();
// String name = entity.getName();
// DirectoryEntity dir_rural = new DirectoryEntity();
// dir_rural.setName(name);
// dir_rural.setParent(dir_area);
// dir_rural.setExtattr2(DirectoryExtattr.RURAL);
// dir_rural.setExtattr3(id);
// dir_rural.setExtattr5(name);
// _init_entity(event, dir_rural);
// entityManager.persist(dir_rural);
// // 创建村下的文档、图片目录，父节点为村目录
// DirectoryEntity dir_rural_doc = new DirectoryEntity();
// DirectoryEntity dir_rural_img = new DirectoryEntity();
// DirectoryEntity dir_rural_video = new DirectoryEntity();
// dir_rural_doc.setName("文档");
// dir_rural_doc.setParent(dir_rural);
// dir_rural_doc.setExtattr2(DirectoryExtattr.RURAL_DOC);
// dir_rural_doc.setExtattr3(id);
// dir_rural_doc.setExtattr5(name);
// _init_entity(event, dir_rural_doc);
// dir_rural_img.setName("图片");
// dir_rural_img.setParent(dir_rural);
// dir_rural_img.setExtattr2(DirectoryExtattr.RURAL_IMG);
// dir_rural_img.setExtattr3(id);
// dir_rural_img.setExtattr5(name);
//
// _init_entity(event, dir_rural_doc);
// dir_rural_video.setName("视频");
// dir_rural_video.setParent(dir_rural);
// dir_rural_video.setExtattr2(DirectoryExtattr.RURAL_VIDEO);
// dir_rural_video.setExtattr3(id);
// dir_rural_video.setExtattr5(name);
//
// _init_entity(event, dir_rural_img);
// _init_entity(event, dir_rural_video);
// entityManager.persist(dir_rural_doc);
// entityManager.persist(dir_rural_img);
// entityManager.persist(dir_rural_video);
// // 更新目录到实体
// entity.setDirectory(dir_rural_doc);
// // entity.setDirectoryImg(dir_rural_img);
// entityManager.merge(entity);
// }

// private DirectoryEntity __create(OperateEvent event, String name,
// int rural, NewRuralEntity newRural, DirectoryEntity directory) {
// DirectoryEntity dir = new DirectoryEntity();
// dir.setParent(directory);
// dir.setExtattr2(rural);
// dir.setExtattr3(newRural.getId());
// dir.setExtattr5(newRural.getName());
// dir.setName(name);
// entityManager.persist(_init_entity(event, dir));
// return dir;
//
// }
// @Override
// @Transactional
// public void createDirViedo(OperateEvent event, NewRuralEntity newRural) {
// DirectoryEntity directoryVideo = newRural.getDirectoryVideo();
// if (null == directoryVideo) {
// directoryVideo = __create(event, "视频", DirectoryExtattr.RURAL_VIDEO,
// newRural, newRural.getDirectory());
// }
// }
//
// @Override
// @Transactional
// public void createDirImg(OperateEvent event, NewRuralEntity newRural) {
// DirectoryEntity directoryImg = newRural.getDirectoryImg();
// if (null == directoryImg) {
// directoryImg = __create(event, "图片", DirectoryExtattr.RURAL_IMG,
// newRural, newRural.getDirectory());
// }
// }
