package cn.bonoon.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.controllers.project.reportManager.ProjectImageShowEntity;
import cn.bonoon.core.ApplicantStatus;
import cn.bonoon.core.DirectoryExtattr;
import cn.bonoon.core.FileStatus;
import cn.bonoon.core.ModelAreaInserter;
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.core.ModelAreaStatisInfo;
import cn.bonoon.core.configs.ModelAreaConfig;
import cn.bonoon.core.plugins.PlaceService;
import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.entities.AdministrationRuralEntity;
import cn.bonoon.entities.AdministrationUncoreRuralEntity;
import cn.bonoon.entities.BaseRuralEntity;
import cn.bonoon.entities.CommissionerEntity;
import cn.bonoon.entities.DirectoryEntity;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.IndustryAreaEntity;
import cn.bonoon.entities.InvestmentInfo;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.ModelAreaPointEntity;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.PeripheralRuralEntity;
import cn.bonoon.entities.ProjectEntity;
import cn.bonoon.entities.ProjectReportEntity;
import cn.bonoon.entities.ProjectReportItem;
import cn.bonoon.entities.ResidentialEnvironmentEntity;
import cn.bonoon.entities.RuralExpertGroupEntity;
import cn.bonoon.entities.RuralPointEntity;
import cn.bonoon.entities.RuralUnitEntity;
import cn.bonoon.entities.RuralWorkGroupEntity;
import cn.bonoon.entities.UnitEntity;
import cn.bonoon.entities.logs.HandleLogEntity;
import cn.bonoon.entities.logs.InvestmentComparer;
import cn.bonoon.entities.logs.modelAreaManageModuleLogs.AdRuralStatusComparerEntity;
import cn.bonoon.entities.logs.modelAreaManageModuleLogs.AdminRuralFrushBaseRuralComparerEntity;
import cn.bonoon.entities.logs.modelAreaManageModuleLogs.DelPorSouInvComparerEntity;
import cn.bonoon.entities.logs.modelAreaManageModuleLogs.DelProRepItemComparerEntity;
import cn.bonoon.entities.logs.modelAreaManageModuleLogs.ExtractHouseComparerEntity;
import cn.bonoon.entities.logs.modelAreaManageModuleLogs.ModelAreaInvComparerEntity;
import cn.bonoon.entities.logs.modelAreaManageModuleLogs.NewRuralStatusComparerEntity;
import cn.bonoon.entities.logs.modelAreaManageModuleLogs.ProMsgComparerEntity;
import cn.bonoon.entities.logs.modelAreaManageModuleLogs.ProjectReportComparerEntity;
import cn.bonoon.entities.logs.modelAreaManageModuleLogs.RuralExpertGroupComparerEntity;
import cn.bonoon.entities.logs.modelAreaManageModuleLogs.RuralUnitComparerEntity;
import cn.bonoon.entities.logs.modelAreaManageModuleLogs.RuralWorkGroupComparerEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.entities.AbstractEntity;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.models.JsonResult;
import cn.bonoon.util.ControllerUtil;
import cn.bonoon.util.DoubleHelper;
import cn.bonoon.util.MonthAndQuarterUtil;

@Service
@Transactional(readOnly = true)
public class ModelAreaServiceImpl extends
		ConfigurableModelAreaService<ModelAreaEntity> implements
		ModelAreaService {

	private PlaceEntity __setBase(OperateEvent event, ModelAreaEntity entity) {
		// 县ID
		UnitEntity unit = entity.getUnit();
		if (null == unit) {
			Long cId = event.getOwnerId();
			unit = entityManager.find(UnitEntity.class, cId);
			entity.setUnit(unit);
		}
		PlaceEntity place = entity.getPlace();
		if (null == place) {
			place = unit.getPlace();
			// 判断级别
			int level = place.getLevel();
			if (level != PlaceService.LEVEL_COUNTY
					&& level != PlaceService.LEVEL_TOWN) {
				// 县级和镇级都可以提交
				throw new RuntimeException("没有权限，不能提交！");
			}
			entity.setPlace(place);
		}
		entity.setCode(event.getUsername());

		return __setBase(entity, place);
	}

	private PlaceEntity __setBase(ModelAreaEntity entity, PlaceEntity place) {
		PlaceEntity city = place.getParent();
		if (city != null) {
			entity.setCityId(city.getId());
			entity.setCityName(city.getName());
		}
		entity.setPlace(place);
		entity.setCounty(place.getName());

		if (StringHelper.isEmpty(entity.getName())) {
			entity.setName(place.getName() + "示范片区");
		}

		return city;
	}

	private void __init(AdministrationRuralEntity are, OperateEvent event,
			BaseRuralEntity rural) {
		are.setCreateAt(event.now());
		are.setCreatorId(event.getId());
		are.setCreatorName(event.getUsername());
		are.setPlace(rural.getPlace());
		are.setName(rural.getName());
		are.setTown(rural.getTown());

		ModelAreaEntity ma = rural.getModelArea();
		are.setOwnerId(ma.getOwnerId());
		are.setModelArea(ma);

		entityManager.persist(are);

	}

	// TODO:更新的方法
	@Override
	protected ModelAreaEntity __update(OperateEvent event,
			ModelAreaEntity entity) {
		// 检查是否允许提交
		checkAndThrowError(entity, event);
		if (event.getSource() instanceof ModelAreaInserter) {
			// 判断数据错位的情况
			if (!event.getOwnerId().equals(entity.getOwnerId())) {
				throw new RuntimeException("检测到数据非来自同一个账号的，请检查是否同时登录两个账号！");
			}
			if (entity.getStatus() != -1)
				throw new RuntimeException("不允许修改主体建设村！");
			// --设置ma的place,cityid,name
			PlaceEntity city = __setBase(event, entity);

			// 主体自然村
			String qlr = "select x from NewRuralEntity x where x.modelArea.id=? and x.modelArea.deleted=false and x.deleted=false";
			List<NewRuralEntity> db_nrs = __list(NewRuralEntity.class, qlr,
					entity.getId());
			// 非主体自然村
			String qlp = "select x from PeripheralRuralEntity x where x.modelArea.id=? and x.modelArea.deleted=false and x.deleted=false";
			List<PeripheralRuralEntity> pres = __list(
					PeripheralRuralEntity.class, qlp, entity.getId());

			// TODO:
			/*
			 * 处理顺序: 1.更新 2.添加 3.删除
			 */
			/*
			 * nrs //存放所有需要添加或是需要修改的自然村 acrCanUse //可以使用的行政村<行政村的placeId,行政村>
			 * acrNeedDeleted //需要被删除的行政村<行政村的id,行政村> //needDeletedNR
			 * //需要被删除的自然村 usedARIds //正在使用的行政村的id db_acrs //数据库中的行政村 思路:
			 * 1.去重复的行政村,把他添加到可以用的行政村map里面 2.处理更新 a.对的上号,更新
			 * b.对不上号,把自然村放到需要删除的自然村list里面 3.删除自然村//遍历needDeletedNR,删除 4.添加
			 * a.删除行政村//数据库中的db_acrs 里面的行政村是否被引用到,被应用到则不能被删除.
			 * b.添加创建自然村//尝试从缓存中acrNeedDeleted拿行政村,如果可以拿到,记得把他重acrNeedDeleted中移除
			 */

			/*
			 * one.更新
			 */
			List<NewRuralEntity> nrs = new ArrayList<>();// 存放的是添加后需要添加或是保存数据库的自然村
			Map<Long, AdministrationCoreRuralEntity> acrCanUse = new HashMap<>();// <主体行政村的placeId,行政村>
			// Map<Long, AdministrationCoreRuralEntity> acrNeedDeleted = new
			// HashMap<>();//<主体行政村id,行政村>
			// 处理主体村的行政村
			List<NewRuralEntity> needDeleteNR = new ArrayList<>();
			List<Long> usedARIds = new ArrayList<>();
			for (NewRuralEntity nr : db_nrs) {
				__doNewRural(event, nrs, acrCanUse, /* acrNeedDeleted, */
						needDeleteNR, usedARIds, nr);
			}

			List<PeripheralRuralEntity> prs = new ArrayList<>();
			Map<Long, AdministrationUncoreRuralEntity> aurCanUse = new HashMap<>();
			// Map<Long, AdministrationUncoreRuralEntity> aurNeedDelete = new
			// HashMap<>();
			// 处理非主体村的行政村
			List<PeripheralRuralEntity> needDeletePR = new ArrayList<>();
			List<Long> usedAURIds = new ArrayList<>();
			for (PeripheralRuralEntity pr : pres) {
				__doPreipheralRural(event, prs, aurCanUse, /* aurNeedDelete, */
						needDeletePR, usedAURIds, pr);
			}

			/*
			 * two.添加
			 */
			ModelAreaEntity rt = __setRural(event, entity, nrs, prs, city,
					acrCanUse, /* acrNeedDeleted.values(), */
					aurCanUse, /* aurNeedDelete.values(), */usedARIds,
					usedAURIds);

			/*
			 * three.删除
			 */
			// --删除主体自然村
			for (NewRuralEntity nr : needDeleteNR) {
				_doDeleteNewRural(pres, nr);
			}
			// --删除主体行政村
			_doDeletedACR(entity, usedARIds);

			// --删除非主体自然村
			for (PeripheralRuralEntity pr : needDeletePR) {
				_doDeletePreRural(pr);
			}
			// --删除非主体行政村
			_doDeletedAUCR(entity, usedAURIds);

			/*
			 * for(AdministrationCoreRuralEntity dar : acrNeedDeleted){
			 * __delACR(dar); }
			 */

			// for(AdministrationUncoreRuralEntity daur : aurNeedDelete){
			// daur.setDeleted(true);
			// entityManager.merge(daur);
			// // entityManager.remove(daur);
			// }
			return rt;
		}

		checkAndThrowError(entity, event);

		PlaceEntity place = entity.getPlace();
		__setBase(entity, place);
		if (StringHelper.isEmpty(entity.getCode())) {
			entity.setCode(event.getUsername());
		}
		__checkIfCanUpdate(entity);
		return super.__update(event, entity);
	}

	private void _doDeletedACR(ModelAreaEntity entity, List<Long> usedARIds) {
		String qlr2 = "select x from AdministrationCoreRuralEntity x where x.modelArea.id=? and x.modelArea.deleted=false and x.deleted=false";
		List<AdministrationCoreRuralEntity> db_acrs = __list(
				AdministrationCoreRuralEntity.class, qlr2, entity.getId());

		for (int i = db_acrs.size() - 1; i >= 0; i--) {
			Long did = db_acrs.get(i).getId();
			if (usedARIds.remove(did)) {
				//
				db_acrs.remove(i);
			}
		}
		for (AdministrationCoreRuralEntity acr : db_acrs) {
			__delACR(acr);
		}
	}

	private void _doDeletedAUCR(ModelAreaEntity entity, List<Long> usedAURIds) {
		String qlr2 = "select x from AdministrationUncoreRuralEntity x where x.modelArea.id=? and x.modelArea.deleted=false and x.deleted=false";
		List<AdministrationUncoreRuralEntity> db_aurs = __list(
				AdministrationUncoreRuralEntity.class, qlr2, entity.getId());

		for (int i = db_aurs.size() - 1; i >= 0; i--) {
			Long did = db_aurs.get(i).getId();
			if (usedAURIds.remove(did)) {
				//
				db_aurs.remove(i);
			}
		}
		for (AdministrationUncoreRuralEntity aur : db_aurs) {
			// __delACR(acr);
			// 删除非主体行政村,是不是就不像删除主体行政村那样,
			__delAUR(aur);
		}
	}

	private void __delAUR(AdministrationUncoreRuralEntity aur) {
		// 删除非主体行政村后是否 还要做其他处理
		// Long aid = dar.getId();
		// __exec("delete from RuralUnitEntity where adminRural.id=?", aid);
		// __exec("delete from RuralWorkGroupEntity where adminRural.id=?",
		// aid);
		// __exec("delete from RuralExpertGroupEntity where adminRural.id=?",
		// aid);
		aur.setDeleted(true);
		entityManager.merge(aur);
	}

	private void __doNewRural(OperateEvent event, List<NewRuralEntity> nrs,
			Map<Long, AdministrationCoreRuralEntity> acrCanUse,
			/* Map<Long, AdministrationCoreRuralEntity> acrNeedDeleted, */
			List<NewRuralEntity> needDeleteNR, List<Long> usedARIds,
			NewRuralEntity nr) {
		AdministrationCoreRuralEntity are;
		are = nr.getAdminRural();
		if (null != are) {
			// --过滤重复的行政村
			are = _filterRepeatedACR(acrCanUse, are);
			// usedARIds.add(are.getId());
		}
		Long nrid = nr.getId();
		if (nrid.equals(event.getLong("scrid_" + nrid))) {
			// --能够对上号的,说明这些自然村是需要做修改的
			// _updateNewRural(event, nrs, acrCanUse, acrNeedDeleted, are, nr,
			// nrid);
			_updateNewRural(event, nrs, acrCanUse, /* acrNeedDeleted, */are,
					nr, nrid, usedARIds);
		} else {
			// --对不上号的,说明是需要被删除的自然村,把他放到需要删除的自然村list里面.
			needDeleteNR.add(nr);
			// _deleteNewRural(pres, acrCanUse, acrNeedDeleted, are, nr);
		}
	}

	private void __doPreipheralRural(OperateEvent event,
			List<PeripheralRuralEntity> prs,
			Map<Long, AdministrationUncoreRuralEntity> aurCanUse,
			/* Map<Long, AdministrationUncoreRuralEntity> aurNeedDelete, */
			List<PeripheralRuralEntity> needDeletePR, List<Long> usedAURIds,
			PeripheralRuralEntity pr) {
		// PlaceEntity pe;
		AdministrationUncoreRuralEntity aur;
		// AdministrationUncoreRuralEntity _aur;
		aur = pr.getAdminRural();
		if (null != aur) {
			aur = _filterRepeatedAUCR(aurCanUse, pr, aur);
		}
		Long prid = pr.getId();
		if (prid.equals(event.getLong("sprid_" + prid))) {
			// --能够对上号的,说明这些自然村是需要做修改的
			_updatePreRural(event, prs, aurCanUse, pr, aur, prid, usedAURIds);
		} else {
			/*
			 * if(null != aur && !aurCanUse.containsKey(aur.getId())){
			 * aurNeedDelete.put(aur.getId(), aur); }
			 */
			// --对不上号的,说明是需要被删除的自然村,把他放到需要删除的自然村list里面.
			needDeletePR.add(pr);
			// _doDeletePreRural(pr);
		}
	}

	private void _doDeletePreRural(PeripheralRuralEntity pr) {
		Long aid = pr.getId();

		// TODO 删除一个非主体村的情况
		// 处理掉关联的项目
		// __exec("delete from ProjectEntity where rural.id=?", aid);
		__exec("update ProjectEntity set deleted=true where rural.id=?", aid);
		// __exec("delete from IndustryAreaEntity where rural.id=?", aid);
		__exec("update IndustryAreaEntity set deleted=true where rural.id=?",
				aid);
		__exec("delete from RuralPointEntity where rural.id=?", aid);
		__exec("delete from ResidentialEnvironmentEntity where rural.id=?", aid);

		pr.setAdminRural(null);
		pr.setDeleted(true);
		entityManager.merge(pr);
		// entityManager.remove(pr);
	}

	private void _updatePreRural(OperateEvent event,
			List<PeripheralRuralEntity> prs,
			Map<Long, AdministrationUncoreRuralEntity> aurCanUse,
			PeripheralRuralEntity pr, AdministrationUncoreRuralEntity aur,
			Long prid, List<Long> usedAURIds) {
		PlaceEntity pe;
		String nvName = event.getString("sprnv_" + prid);
		// 验证输入的自然村名字
		checkAndThrowErrorVillageName(nvName);

		pr.setNaturalVillage(nvName);
		pr.setDeleted(false);
		prs.add(pr);

		pe = InternalRuralHelper.__findPlace(entityManager, pr);

		if (null == aur || !pe.getId().equals(aur.getPlace().getId())) {
			/*
			 * if(null != aur){ aurNeedDelete.put(aur.getId(), aur); }
			 */
			// 处理更新时没有行政村的情况
			aur = new AdministrationUncoreRuralEntity();
			__init(aur, event, pr);
			aurCanUse.put(pe.getId(), aur);
		}/*
		 * else{ aurNeedDelete.remove(aur.getId()); }
		 */

		// 添加到正在使用的行政村里面
		_addToUsedAURIdsAndSetDelStatus(aur, usedAURIds);
		pr.setAdminRural(aur);
	}

	private void _addToUsedAURIdsAndSetDelStatus(
			AdministrationUncoreRuralEntity aur, List<Long> usedAURIds) {
		if (!usedAURIds.contains(aur.getId())) {
			usedAURIds.add(aur.getId());
			aur.setDeleted(false);
			entityManager.merge(aur);
		}
	}

	private AdministrationUncoreRuralEntity _filterRepeatedAUCR(
			Map<Long, AdministrationUncoreRuralEntity> aurCanUse,
			PeripheralRuralEntity pr, AdministrationUncoreRuralEntity aur) {
		AdministrationUncoreRuralEntity _aur;
		Long prpid = pr.getPlace().getId();
		// _aur = null;
		_aur = aurCanUse.get(prpid);
		if (null == _aur) {
			aurCanUse.put(prpid, aur);
		} else if (!_aur.getId().equals(aur.getId())) {
			// aurNeedDelete.put(aur.getId(), aur);
			// 直接删
			aur.setDeleted(true);
			entityManager.merge(aur);

			aur = _aur;// 使用第一个行政村
		}
		return aur;
	}

	/*
	 * 过滤重复的主体行政村:一个行政村下面可能有很多个自然村记录.但只需要一个行政村
	 */
	private AdministrationCoreRuralEntity _filterRepeatedACR(
			Map<Long, AdministrationCoreRuralEntity> acrCanUse,
			AdministrationCoreRuralEntity are) {
		AdministrationCoreRuralEntity _are;
		long pid;
		pid = are.getPlace().getId();
		// _are = null;
		_are = acrCanUse.get(pid);
		if (null == _are) {
			acrCanUse.put(pid, are);
		} else if (!are.getId().equals(_are.getId())) {// ???
			// 需要把旧的删除
			// acrNeedDeleted.put(are.getId(), are);
			// 直接删
			are.setDeleted(true);
			entityManager.merge(are);

			are = _are;// 使用第一个的行政村
		}
		return are;
	}

	// private void _deleteNewRural(List<PeripheralRuralEntity> pres,
	// Map<Long, AdministrationCoreRuralEntity> acrCanUse,
	// Map<Long, AdministrationCoreRuralEntity> acrNeedDeleted,
	// AdministrationCoreRuralEntity are, NewRuralEntity nr) {
	// if(null != are && !acrCanUse.containsKey(are.getPlace().getId())){
	// //判断是否是需要删除的
	// acrNeedDeleted.put(are.getId(), are);
	// }
	// _doDeleteNewRural(pres, nr);
	// }

	private void _doDeleteNewRural(List<PeripheralRuralEntity> pres,
			NewRuralEntity nr) {
		Long aid = nr.getId();

		// TODO 删除一个主体村的情况
		__exec("delete from RuralUnitEntity where newRural.id=?", aid);
		__exec("delete from RuralWorkGroupEntity where newRural.id=?", aid);
		__exec("delete from RuralExpertGroupEntity where newRural.id=?", aid);

		// 处理掉关联的项目
		// __exec("delete from IndustryAreaEntity where rural.id=?", aid);
		__exec("update IndustryAreaEntity set deleted=true where rural.id=?",
				aid);
		__exec("delete from RuralPointEntity where rural.id=?", aid);

		// 出现了需要删除项目的月报的情况
		// __exec("delete from ProjectEntity where rural.id=?", aid);
		__exec("update ProjectEntity set deleted=true where rural.id=?", aid);

		// 需要把环境整治的也一起删除掉
		__exec("delete from ResidentialEnvironmentEntity where rural.id=?", aid);

		/*
		 * 删除了主体自然村,对应的辐射村也要设置这个他对应的这个主体村为null
		 */
		for (PeripheralRuralEntity pr : pres) {
			if (pr.getNewRural() != null
					&& aid.equals(pr.getNewRural().getId())) {
				pr.setNewRural(null);
				entityManager.merge(pr);
			}
		}
		nr.setAdminRural(null);
		nr.setDeleted(true);
		entityManager.merge(nr);
		// entityManager.remove(nr);
	}

	private void _updateNewRural(OperateEvent event, List<NewRuralEntity> nrs,
			Map<Long, AdministrationCoreRuralEntity> acrCanUse,
			/* Map<Long, AdministrationCoreRuralEntity> acrNeedDeleted, */
			AdministrationCoreRuralEntity are, NewRuralEntity nr, Long nrid,
			List<Long> usedARIds) {
		PlaceEntity pe;
		String nvName = event.getString("scrnv_" + nrid);

		// 对输入的自然村验证
		checkAndThrowErrorVillageName(nvName);

		nr.setNaturalVillage(nvName);
		nr.setDeleted(false);
		nrs.add(nr);
		// TODO 查找地区
		pe = InternalRuralHelper.__findPlace(entityManager, nr);

		if (null == are || !pe.getId().equals(are.getPlace().getId())) {// 什么
																		// 意思?
			/*
			 * if(null != are){ acrNeedDeleted.put(are.getId(), are); }
			 */
			// 处理更新时没有行政村的情况
			are = new AdministrationCoreRuralEntity();
			__init(are, event, nr);
			acrCanUse.put(pe.getId(), are);

		}/*
		 * else{ acrNeedDeleted.remove(are.getId()); }
		 */

		// --新添加的代码 ? 是否需要这样执行,因为你new 出来的,数据库里面就根本没有这个东西,所以就不存在引用的情况.
		_addToUsedARIdsAndSetDelStatus(are, usedARIds);
		nr.setAdminRural(are);
	}

	private void checkAndThrowErrorVillageName(String nvName) {
		// String regex = "[\u4e00-\u9fa5\\w]+";//其中：\u4e00-\u9fa5
		// 代表中文，\\w代表英文、数字和“_"，中括号代表其中的任意字符，最后的加号代表至少出现一次。
		// String regex="^[\u4E00-\u9FA5A-Za-z0-9]+$";
		// Pattern pattern = Pattern.compile(regex);
		// Matcher match=pattern.matcher(nvName);
		// boolean b=match.matches();//b=true,表示匹配成功，为false匹配失败
		// if(!b){
		// throw new
		// RuntimeException("【"+nvName+"】:一个行政村对应只能输入一个自然村（或村民小组），若有多个自然村，请添加多个行政村进来填写！输入的字符串只能含有中文、字母、数字！");
		// }

		// 对输入的自然村（或村民小组 ） 字符串做验证，
		String[] specialChars = { "、", "/", "\\", ";", "；", ",", "，", " ", "。" };
		for (String s : specialChars) {
			if (nvName.contains(s)) {
				throw new RuntimeException(
						"【"
								+ nvName
								+ "】:一个行政村对应只能输入一个自然村（或村民小组），若有多个自然村，请添加多个行政村进来填写！输入的字符串不能含有：顿号、分号、逗号、空格、句号、斜杠！");
			}
		}
	}

	private void _addToUsedARIdsAndSetDelStatus(
			AdministrationCoreRuralEntity are, List<Long> usedARIds) {
		// if(usedARIds==null){
		//
		// }
		if (!usedARIds.contains(are.getId())) {
			usedARIds.add(are.getId());
			are.setDeleted(false);
			entityManager.merge(are);
		}
	}

	/*
	 * 检查是否可以保存更新,例如输入的一些数据不合理
	 */
	private void __checkIfCanUpdate(ModelAreaEntity entity) {
		if (entity.getPopHous() < entity.getFarmerHous()) {
			throw new RuntimeException("县总人口户数要大于县农户总数！");
		}
		if (entity.getSumTownPopu() < entity.getSumFarmers()) {
			throw new RuntimeException("县总人口数要大于县农村人口总数。！");
		}
		if (entity.getSumTownPopu() < entity.getPopHous()) {
			throw new RuntimeException("县总人口总数要大于县人口总户数。！");
		}
		if (entity.getSumFarmers() < entity.getFarmerHous()) {
			throw new RuntimeException("县农村人口总数要大于县农村人口总户数。！");
		}
	}

	private ModelAreaEntity __setRural(OperateEvent event,
			ModelAreaEntity entity, List<NewRuralEntity> nrs,
			List<PeripheralRuralEntity> prs, PlaceEntity city,

			// 处理新添加的自然村与旧的自然村的关联关系
			Map<Long, AdministrationCoreRuralEntity> acrCanUse,
			/* Collection<AdministrationCoreRuralEntity> acrNeedDeleted, */
			Map<Long, AdministrationUncoreRuralEntity> aurCanUse,
			/* Collection<AdministrationUncoreRuralEntity> aurNeedDelete, */
			List<Long> usedARIds, List<Long> usedAURIds) {
		ModelAreaInserter mai = (ModelAreaInserter) event.getSource();

		// 创建主体村
		if (null != mai.getCrorder()) {
			_createNewRural(event, entity, nrs, acrCanUse, /* acrNeedDeleted, */
					mai, usedARIds);

		}
		// 创建非主体村
		if (null != mai.getProrder()) {
			_createPreRural(event, entity, prs, aurCanUse, mai, usedAURIds);
		}

		/*
		 * 创建的情况
		 */
		if (event.is("applicant-create")) {

			if (nrs.isEmpty()) {
				throw new RuntimeException("必须选择主体村！");
			}
			DirectoryEntity dir_area = _init_directory(event, entity, city);
			// 创建主体村
			for (NewRuralEntity ruralEntity : nrs) {
				// TODO 文档管理 需要处理文档、目录等
				// 创建村目录，父节点为区
				DirectoryEntity dir_rural = ruralEntity.getDirectory();
				if (null == dir_rural) {
					dir_rural = __create(event, DirectoryExtattr.ROOT_RURAL,
							ruralEntity, dir_area);// new DirectoryEntity();
					ruralEntity.setDirectory(dir_rural);
					entityManager.merge(ruralEntity);
				}
			}
			// 创建非主体村
			for (PeripheralRuralEntity perEntity : prs) {
				if (null != perEntity.getDirectory()) {
					DirectoryEntity dir_per = __create(event,
							DirectoryExtattr.ROOT_RURAL, perEntity, dir_area); // 非主体村主目录
					perEntity.setDirectory(dir_per);
					entityManager.merge(perEntity);
				}
			}
			entity.setStatus(0);
			// 处理信息专员的信息
			String ql = "select x from CommissionerEntity x where x.deleted=false and x.history=false and x.ownerId=?";
			List<CommissionerEntity> comms = __list(CommissionerEntity.class,
					ql, event.getOwnerId());
			if (!comms.isEmpty()) {
				String cn = entity.getContactName();
				CommissionerEntity oce = null;
				for (CommissionerEntity co : comms) {
					if (cn.equals(co.getName())) {
						oce = co;
						break;
					}
				}
				if (null == oce) {
					oce = comms.get(0);
				}
				oce.setHistory(true);
				event.update(oce);
				entityManager.merge(oce);
			}
			CommissionerEntity ce = new CommissionerEntity();
			event.init(ce);

			ce.setJob(entity.getContactJob());
			ce.setName(entity.getContactName());
			ce.setPhone1(entity.getContactPhone());
			ce.setPhone2(entity.getContactPhone2());
			ce.setUnit(entity.getUnit());
			entityManager.persist(ce);
		} else {
			/*
			 * 暂存的情况
			 */
			entity.setStatus(-1);
		}
		return entityManager.merge(entity);
	}

	private void _createPreRural(OperateEvent event, ModelAreaEntity entity,
			List<PeripheralRuralEntity> prs,
			Map<Long, AdministrationUncoreRuralEntity> aurCanUse,
			ModelAreaInserter mai, List<Long> usedAURIds) {
		for (int por : mai.getProrder()) {
			Long pid = event.getLong("prid_" + por);
			PeripheralRuralEntity perEntity = new PeripheralRuralEntity();
			event.init(perEntity);

			String nvName = event.getString("prnv_" + por);
			// 验证输入的自然村
			checkAndThrowErrorVillageName(nvName);
			perEntity.setNaturalVillage(nvName);

			perEntity.setModelArea(entity);
			perEntity.setPlaceId(pid);
			PlaceEntity pr = entityManager.find(PlaceEntity.class, pid);
			perEntity.setName(pr.getName());
			perEntity.setTown(pr.getParent().getName());
			perEntity.setPlace(pr);
			AdministrationUncoreRuralEntity aur = aurCanUse.get(pr.getId());

			if (null == aur) {
				/*
				 * for(AdministrationUncoreRuralEntity daur : aurNeedDelete){
				 * if(pr.getId().equals(daur.getPlace().getId())){ aur = daur;
				 * break; } }
				 */
				if (null == aur) {
					aur = new AdministrationUncoreRuralEntity();
					__init(aur, event, perEntity);
				}/*
				 * else{ aurNeedDelete.remove(aur); }
				 */
				aurCanUse.put(pr.getId(), aur);
			}

			_addToUsedAURIdsAndSetDelStatus(aur, usedAURIds);

			perEntity.setAdminRural(aur);
			entityManager.persist(perEntity);
			prs.add(perEntity);
		}
	}

	private void _createNewRural(OperateEvent event, ModelAreaEntity entity,
			List<NewRuralEntity> nrs,
			Map<Long, AdministrationCoreRuralEntity> acrCanUse,
			/* Collection<AdministrationCoreRuralEntity> acrNeedDeleted, */
			ModelAreaInserter mai, List<Long> usedARIds) {
		for (int cor : mai.getCrorder()) {
			Long cid = event.getLong("crid_" + cor);
			NewRuralEntity ruralEntity = new NewRuralEntity();
			event.init(ruralEntity);

			String nvName = event.getString("crnv_" + cor);
			// 验证输入的自然村
			checkAndThrowErrorVillageName(nvName);
			ruralEntity.setNaturalVillage(nvName);

			ruralEntity.setModelArea(entity);
			ruralEntity.setPlaceId(cid);
			PlaceEntity pe = entityManager.find(PlaceEntity.class, cid);
			ruralEntity.setName(pe.getName());
			ruralEntity.setTown(pe.getParent().getName());
			ruralEntity.setPlace(pe);
			AdministrationCoreRuralEntity are = acrCanUse.get(cid);
			// 应该还要从数据库中判断，是否存在这个行政村。TODO: 这里仅仅是对map不重复添加做操作而已

			if (null == are) {
				/*
				 * for(AdministrationCoreRuralEntity dar : acrNeedDeleted){
				 * if(dar.getPlace().getId().equals(cid)){ are =
				 * dar;//尝试从删除的行政村缓存里面拿 break; } }
				 */
				if (null == are) {// 删除的缓存里面拿不到,则创建一个
					are = new AdministrationCoreRuralEntity();
					__init(are, event, ruralEntity);
				}/*
				 * else{//可以拿到则从删除的缓存中移除这个可用的行政村 acrNeedDeleted.remove(are); }
				 */
				acrCanUse.put(cid, are);// 放到可用的行政村里面
			}

			_addToUsedARIdsAndSetDelStatus(are, usedARIds);

			ruralEntity.setAdminRural(are);
			entityManager.persist(ruralEntity);
			nrs.add(ruralEntity);
		}
	}

	/**
	 * 删除与行政村有关联的RuralUnitEntity RuralWorkGroupEntity RuralExpertGroupEntity
	 * 这三个实体的记录 以及设定AdministrationCoreRuralEntity setDeleted为true删除状态
	 * 
	 * @param dar
	 */
	private void __delACR(AdministrationCoreRuralEntity dar) {
		Long aid = dar.getId();
		__exec("delete from RuralUnitEntity where adminRural.id=?", aid);
		__exec("delete from RuralWorkGroupEntity where adminRural.id=?", aid);
		__exec("delete from RuralExpertGroupEntity where adminRural.id=?", aid);

		dar.setDeleted(true);
		entityManager.merge(dar);
		// entityManager.remove(dar);
	}

	// TODO:创建的方法
	@Override
	protected ModelAreaEntity __save(OperateEvent event, ModelAreaEntity entity) {
		PlaceEntity city = __setBase(event, entity);
		super.__save(event, entity);

		Map<Long, AdministrationCoreRuralEntity> acrCanUse = new HashMap<>();
		/*
		 * Collection<AdministrationCoreRuralEntity> needDeleted = new
		 * ArrayList<>();
		 */
		List<NewRuralEntity> nrs = new ArrayList<>();
		List<PeripheralRuralEntity> prs = new ArrayList<>();

		Map<Long, AdministrationUncoreRuralEntity> aurCanUse = new HashMap<>();
		// Collection<AdministrationUncoreRuralEntity> aurNeedDelete = new
		// ArrayList<>();
		List<Long> usedARIds = new ArrayList<>();
		List<Long> usedAURIds = new ArrayList<>();
		return __setRural(event, entity, nrs, prs, city, acrCanUse, /*
																	 * needDeleted,
																	 */
				aurCanUse, usedARIds, usedAURIds);
	}

	private DirectoryEntity __parentDir(OperateEvent event, String extattr,
			PlaceEntity place) {
		Long id = place.getId();
		DirectoryEntity dir = __first(DirectoryEntity.class,
				"from DirectoryEntity where extattr6=? and extattr3=?",
				extattr, id);
		if (null == dir) {
			PlaceEntity p = place.getParent();
			DirectoryEntity parent = null;
			if (null != p) {
				parent = __parentDir(event, DirectoryExtattr.ROOT_PROVINCE, p);
			}
			dir = new DirectoryEntity();
			event.init(dir);
			dir.setOwnerId(0);
			dir.setStatus(FileStatus.STATUS_FORBID);
			dir.setShare(FileStatus.SHARE_PRIVATE);
			String name = place.getName();
			dir.setParent(parent);
			dir.setExtattr3(place.getId());

			dir.setExtattr5(name);
			dir.setName(name);
			dir.setExtattr6(extattr);

			entityManager.persist(dir);
		}
		return dir;
	}

	private DirectoryEntity __create(OperateEvent event, String name,
			String extattr, AbstractEntity entity, DirectoryEntity parent) {
		DirectoryEntity dir = new DirectoryEntity();
		event.init(dir);
		dir.setStatus(FileStatus.STATUS_FORBID);
		dir.setShare(FileStatus.SHARE_PRIVATE);
		dir.setParent(parent);
		dir.setExtattr3(entity.getId());
		dir.setExtattr5(entity.getName());
		dir.setExtattr6(extattr);
		dir.setName(name);
		entityManager.persist(dir);
		return dir;
	}

	private DirectoryEntity __create(OperateEvent event, String extattr,
			AbstractEntity entity, DirectoryEntity parent) {
		DirectoryEntity dir = new DirectoryEntity();
		event.init(dir);
		dir.setStatus(FileStatus.STATUS_FORBID);
		dir.setShare(FileStatus.SHARE_PRIVATE);
		String name = entity.getName();
		dir.setParent(parent);
		dir.setExtattr3(entity.getId());

		dir.setExtattr5(name);
		dir.setName(name);
		dir.setExtattr6(extattr);

		entityManager.persist(dir);
		return dir;
	}

	private DirectoryEntity _init_directory(OperateEvent event,
			ModelAreaEntity entity, PlaceEntity place) {
		// TODO 文档管理 检查是否存在市目录
		DirectoryEntity dir_area = entity.getDirectory();
		if (null == dir_area) {
			DirectoryEntity dir_city = __parentDir(event,
					DirectoryExtattr.ROOT_CITY, place);
			dir_area = __create(event, DirectoryExtattr.MODEL_AREA, entity,
					dir_city);
			// new DirectoryEntity();
			// event.init(dir_area);
			// String name = entity.getName();
			// dir_area.setParent(dir_city);
			// dir_area.setExtattr3(entity.getId());
			//
			// dir_area.setExtattr5(name);
			// dir_area.setName(name);
			// dir_area.setExtattr6(DirectoryExtattr.MODEL_AREA);
			// entityManager.persist(dir_area);

			// DirectoryEntity dir_area = __create(event,
			// DirectoryExtattr.MODEL_AREA, entity, dir_city);
			entity.setDirectory(dir_area);
		}
		// 创建区下的文档、图片目录，父节点为区目录
		DirectoryEntity dir_area_proj = entity.getDirectoryProject();
		if (null == dir_area_proj) {
			dir_area_proj = __create(event, DirectoryExtattr.MA_PROJECT,
					DirectoryExtattr.MA_PROJECT, entity, dir_area);// new
																	// DirectoryEntity();
			entity.setDirectoryProject(dir_area_proj);
		}
		DirectoryEntity dir_area_nrl = entity.getDirectoryRural();
		if (null == dir_area_nrl) {
			dir_area_nrl = __create(event, DirectoryExtattr.MA_RURAL,
					DirectoryExtattr.MA_RURAL, entity, dir_area);
			entity.setDirectoryRural(dir_area_nrl);
		}
		// 更新目录到实体
		// DirectoryEntity dir_area_doc = __create(event, "文档",
		// DirectoryExtattr.AREA_DOC, entity, dir_area);//new DirectoryEntity();
		// DirectoryEntity dir_area_img = __create(event, "多媒体",
		// DirectoryExtattr.AREA_IMG, entity, dir_area);//new DirectoryEntity();
		// entity.setDirectoryDoc(dir_area_doc);
		// entity.setDirectoryImg(dir_area_img);
		// entity.setDirectoryProj(dir_area_proj);
		// entity.setDirectoryNrl(dir_area_nrl);
		entityManager.merge(entity);
		return dir_area_nrl;
	}

	@Override
	public UnitEntity getUnit(Long uid) {
		return entityManager.find(UnitEntity.class, uid);
	}

	@Override
	@Transactional
	public void auditPass(OperateEvent event, Long id) {
		ModelAreaEntity mae = __get(id);
		mae.setStatus(FINISH);
		entityManager.merge(mae);
	}

	@Override
	@Transactional
	public void auditReject(OperateEvent event, Long id) {
		ModelAreaEntity mae = __get(id);
		mae.setStatus(REJECT);
		entityManager.merge(mae);
	}

	@Override
	@Transactional
	public void submit(Long id, IOperator ope) {
		ModelAreaEntity mae = __get(id);
		// 检查是否允许提交
		checkSubmitAndThrowError(mae, ope);

		// 检查主体村
		String ql = "select x from NewRuralEntity x where x.deleted=false and x.modelArea.id=?";
		List<NewRuralEntity> nrs = __list(NewRuralEntity.class, ql, id);
		if (nrs.isEmpty()) {
			throw new RuntimeException("必须填写主体村才能提交台账！");
		}
		// 需要进行是否已经创建的行政村的判断处理
		String error = "";
		for (NewRuralEntity nr : nrs) {
			if (nr.getStatus() == 0) {
				error += "," + nr.getName() + nr.getNaturalVillage() + "(未确认)";
			} else if (null == nr.getAdminRural()) {
				nr.setStatus(0);
				entityManager.merge(nr);
				error += "," + nr.getName() + nr.getNaturalVillage()
						+ "(需重新确认一次)";
			}
		}

		// 检查所有的主体行政村
		// ql =
		// "select x from AdministrationRuralEntity x where x.deleted=false and x.modelArea.id=?";
		ql = "select x from AdministrationCoreRuralEntity x where x.deleted=false and x.modelArea.id=?";
		List<AdministrationCoreRuralEntity> ars = __list(
				AdministrationCoreRuralEntity.class, ql, id);

		for (AdministrationCoreRuralEntity ar : ars) {
			if (ar.getStatus() == 0) {
				error += "," + ar.getName() + "(未确认)";
			}
		}

		if (!error.isEmpty()) {
			throw new RuntimeException(error.substring(1));
		}
		// if(__exsit("select count(x) from NewRuralEntity x where x.deleted=false and x.modelArea.id=? and x.status=0",
		// id)){
		// throw new RuntimeException("需要所有主体村都确定后才能提交台账！");
		// }else
		// if(!__exsit("select count(x) from NewRuralEntity x where x.deleted=false and x.modelArea.id=?",
		// id)){
		//
		// }
		// 检查非主体村
		if (__exsit(
				"select count(x) from PeripheralRuralEntity x where x.deleted=false and x.modelArea.id=? and x.status=0",
				id)) {
			throw new RuntimeException("需要所有非主体村都确定后才能提交台账！");
			// }else
			// if(!__exsit("select count(x) from PeripheralRuralEntity x where x.deleted=false and x.modelArea.id=?",
			// id)){
			// throw new RuntimeException("必须填写非主体村才能提交台账！");
		}
		// 检查产业发展
		if (__exsit(
				"select count(x) from IndustryAreaEntity x where x.deleted=false and x.modelArea.id=? and x.status=0",
				id)) {
			throw new RuntimeException("需要所有产业发展都确定后才能提交台账！");
		}
		mae.setStatus(WAIT_AUDIT);
		entityManager.merge(mae);
	}

	@Override
	public List<Object[]> summary(Long uid) {
		String ql = "select x.id,x.name,x.status from ModelAreaEntity x where x.deleted=false and x.ownerId=? and x.status>=0";

		// String ql2 =
		// "select x from CommissionerEntity x where x.history=false and x.deleted=false and x.ownerId=?";
		//
		// CommissionerEntity f = __first(CommissionerEntity.class, ql2, uid);
		//
		// if(f == null){
		// throw new RuntimeException("请填写信息专员信息");
		// }
		return __list(Object[].class, ql, uid);
	}

	@Override
	public List<Object[]> nrurals(Long id) {
		String ql = "select x.status,count(x) from NewRuralEntity x where x.deleted=false and x.modelArea.id=? and x.status>=0 and x.modelArea.status>=0 group by x.status";
		return __list(Object[].class, ql, id);
	}

	@Override
	public List<Object[]> prurals(Long id) {
		String ql = "select x.status,count(x) from PeripheralRuralEntity x where x.deleted=false and x.modelArea.id=? and x.status>=0 and x.modelArea.status>=0 group by x.status";
		return __list(Object[].class, ql, id);
	}

	@Override
	public List<Object[]> iarea(Long id) {
		String ql = "select x.status,count(x) from IndustryAreaEntity x where x.deleted=false and x.modelArea.id=? and x.status>=0 group by x.status";
		return __list(Object[].class, ql, id);
	}

	@Override
	public List<Object[]> newRurals(IOperator opt) {
		String ql = "select distinct x.id,x.name,x.modelArea.name,x.naturalVillage,x.town from NewRuralEntity x where x.ownerId=? and x.deleted=false and x.name is not null and x.modelArea.status>=0";
		return __list(Object[].class, ql, opt.getOwnerId());
	}

	@Override
	public List<Object[]> peripheralRurals(IOperator opt) {
		String ql = "select distinct x.id,x.name,x.modelArea.name,x.naturalVillage,x.town from PeripheralRuralEntity x where x.ownerId=? and x.deleted=false and x.placeId is not null and x.name is not null and x.modelArea.status>=0";
		return __list(Object[].class, ql, opt.getOwnerId());
	}

	@Override
	public List<Object[]> newRurals(Long maid) {
		String ql = "select x.id,x.town,x.name,x.naturalVillage from NewRuralEntity x where x.modelArea.id=? and x.deleted=false and x.name is not null";
		return __list(Object[].class, ql, maid);
	}

	@Override
	public List<Object[]> peripheralRurals(Long maid) {
		String ql = "select x.id,x.town,x.name,x.naturalVillage from PeripheralRuralEntity x where x.modelArea.id=? and x.deleted=false and x.placeId is not null and x.name is not null";
		return __list(Object[].class, ql, maid);
	}

	@Override
	public ModelAreaEntity getByOnwer(Long uid) {
		String ql = "select x from ModelAreaEntity x where x.ownerId=? and x.deleted=false";
		return __first(ModelAreaEntity.class, ql, uid);
	}

	// @Override
	// @Transactional
	// public void createDirViedo(OperateEvent event, NewRuralEntity newRural) {
	// DirectoryEntity directoryVideo = newRural.getDirectoryVideo();
	// if (null == directoryVideo) {
	// directoryVideo = __create(event, "视频", DirectoryExtattr.RURAL_VIDEO,
	// newRural, newRural.getDirectory());
	// }
	// }

	@Override
	public List<Object[]> cityModelArea(Long owner) {
		String ql = "select x.place.id from UnitEntity x where x.id=?";
		Long pid = __first(Long.class, ql, owner);
		String ql2 = "select x.id, x.name, x.batch from ModelAreaEntity x where x.cityId=? and x.deleted=false";
		return __list(Object[].class, ql2, pid);
	}

	// 查出市下面的所有片区点
	@Override
	public List<ModelAreaPointEntity> cityModelAreaPoint(Long owner) {
		String ql = "select x.place.id from UnitEntity x where x.id=?";
		Long pid = __first(Long.class, ql, owner);
		String ql2 = "select x from ModelAreaPointEntity x where x.modelArea.cityId=? and x.modelArea.deleted=false";
		return __list(ModelAreaPointEntity.class, ql2, pid);

	}

	// 市下面所有的村子点
	@Override
	public List<RuralPointEntity> cityRuralPoint(Long owner) {
		String ql = "select x.place.id from UnitEntity x where x.id=?";
		Long pid = __first(Long.class, ql, owner);
		String ql2 = "select x from RuralPointEntity x where x.modelAreaPoint.modelArea.cityId=? and x.modelAreaPoint.modelArea.deleted=false";
		return __list(RuralPointEntity.class, ql2, pid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object[]> getNewRurals(Long id) {
		// String ql =
		// "select x.id, x.name from NewRuralEntity x where x.deleted=false and x.modelArea.id=?";
		String ql = "select x.id,x.naturalVillage,x.adminRural.id,x.town || ',' || x.name from NewRuralEntity x where x.deleted=false and x.modelArea.id=? and x.modelArea.deleted=false";
		// return __list(Object[].class, ql, id);
		Map<Object, Object[]> maps = new HashMap<>();
		for (Object[] it : __list(Object[].class, ql, id)) {
			Object vid = it[3];
			Object[] vs = maps.get(vid);
			if (null == vs) {
				vs = new Object[3];
				vs[0] = vid;
				vs[1] = it[2];
				vs[2] = new ArrayList<Object>();
				maps.put(vid, vs);
			}
			((List<Object>) vs[2]).add(it);
		}
		return maps.values();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object[]> getPeripheralRurals(Long id) {
		// String ql =
		// "select x.id, x.name from PeripheralRuralEntity x where x.deleted=false and x.modelArea.id=?";
		String ql = "select x.id,x.naturalVillage,x.adminRural.id,x.town || ',' || x.name from PeripheralRuralEntity x where x.deleted=false and x.modelArea.id=? and x.modelArea.deleted=false";
		// return __list(Object[].class, ql, id);
		Map<Object, Object[]> maps = new HashMap<>();
		for (Object[] it : __list(Object[].class, ql, id)) {
			Object vid = it[3];
			Object[] vs = maps.get(vid);
			if (null == vs) {
				vs = new Object[3];
				vs[0] = vid;
				vs[1] = it[2];
				vs[2] = new ArrayList<Object>();
				maps.put(vid, vs);
			}
			((List<Object>) vs[2]).add(it);
		}
		return maps.values();
	}

	@Override
	public List<Object[]> getIndustries(Long id) {
		String ql = "select x.id, x.coopName from IndustryAreaEntity x where x.deleted=false and x.modelArea.id=? and x.modelArea.deleted=false";
		return __list(Object[].class, ql, id);
	}

	@Override
	public List<IndustryAreaEntity> getIndustries_(Long id) {
		String ql = "select x from IndustryAreaEntity x where x.deleted=false and x.modelArea.id=? and x.modelArea.deleted=false";
		return __list(IndustryAreaEntity.class, ql, id);
	}

	@Override
	public List<Object[]> getProjects(Long id) {
		String ql = "select x.id, x.name from ProjectEntity x where x.deleted=false and x.modelArea.id=? and x.modelArea.deleted=false";
		return __list(Object[].class, ql, id);
	}

	// @Override
	// public List<Object[]> getProjects_type(Long id) {
	// String ql =
	// "select x.id, x.name x.projectType from ProjectEntity x where x.deleted=false and x.modelArea.id=? and x.modelArea.deleted=false";
	// return __list(Object[].class, ql, id);
	// }
	@Override
	public List<Object[]> getProjects_type(Long id) {
		String ql = "select x.id,x.name,x.projectType,x.status,x.rural from ProjectEntity x where x.deleted=false and x.modelArea.id=? and x.modelArea.deleted=false";
		return __list(Object[].class, ql, id);
	}

	@Override
	public List<ResidentialEnvironmentEntity> getResidentialEnvironments(Long id) {
		String ql = "select x from ResidentialEnvironmentEntity x where x.rural.modelArea.id=? and x.rural.modelArea.deleted=false order by x.ordinal asc";
		return __list(ResidentialEnvironmentEntity.class, ql, id);
	}

	@Override
	public ModelAreaEntity getNeedAudit(Long owner) {
		String ql = "select x.place.id from UnitEntity x where x.id=?";
		Long pid = __first(Long.class, ql, owner);
		// String ql2 = "select x.id from ModelAreaEntity x where x.cityId=?";
		String ql2 = "select x from ModelAreaEntity x where x.deleted=false and x.cityId=? and x.status=?";
		return __first(ModelAreaEntity.class, ql2, pid, WAIT_AUDIT);
	}

	@Override
	@Transactional
	public void pass(Long id, String content, String name, Date date) {
		__audit(id, FINISH, content, name, date, "通过");
	}

	private void __audit(Long id, int status, String content, String name,
			Date date, String ar) {
		ModelAreaEntity entity = __get(id);
		if (entity.getStatus() != WAIT_AUDIT) {
			throw new RuntimeException("该状态不能进行审核操作！");
		}
		entity.setStatus(status);
		entity.setAuditAt(date);
		entity.setAuditName(name);
		entity.setAuditContent(content);
		entity.setAuditResult(ar);
		entityManager.merge(entity);
	}

	@Override
	@Transactional
	public void reject(Long id, String content, String name, Date date) {
		__audit(id, REJECT, content, name, date, "驳回");
	}

	@Override
	public Map<Object, List<Object[]>> items() {
		String ql = "select x.batch,x.id,x.name,x.ordinalModel,x.county from ModelAreaEntity x where x.deleted=false and x.batch is not null order by x.batch desc,x.ordinalModel asc";
		Map<Object, List<Object[]>> map = new HashMap<>();
		for (Object[] it : __list(Object[].class, ql)) {
			List<Object[]> tmp = map.get(it[0]);
			if (null == tmp) {
				tmp = new ArrayList<>();
				map.put(it[0], tmp);
			}
			tmp.add(it);
		}
		return map;
	}

	// 下标6开始： 0-5
	private final String statistics_ql = "select x.batch,x.reportAnnual,x.cityName,x.county,x.name,x.themeName"
			// 6-double,7~14-int
			+ ",x.ruralsArea,x.townNumber,x.adminVN,x.naturalVN,x.villagersGroup,x.popHous,x.farmerHous,x.sumTownPopu,x.sumFarmers"
			// 15~24-double
			+ ",x.ci3,x.ci4,x.ci5,x.ci6,x.ci7,x.vi3,x.vi4,x.vi5,x.vi6,x.vi7"
			// 25~27-int,28-double
			+ ",x.mainBuild,x.around,x.coverTown, x.mainAreaAcreage+x.aroundAreaAcreage"
			// 29~30-int
			+ ",x.mainSumHouse+x.aroundSumHouse,x.mainSumP+x.aroundSumP"
			// ,31-double,32-string,33~34-int,35-double,36~38-int,39-double
			+ ",x.lineScale,x.startMark,x.planMark,x.postCount,x.greenRoad,x.viewPlatform,x.introCard,x.greenProject,x.areaRoad"
			+ " from ModelAreaEntity x where x.deleted=false and x.status>0";

	// TODO 以下的需要添加行政村的排序
	@Override
	public List<Object[]> statistics(String batch) {
		batch = StringHelper.get(batch, "一");
		String ql = statistics_ql
				+ " and x.batch=? order by x.ordinalModel asc";
		return __list(Object[].class, ql, batch);
	}

	@Override
	public List<Object[]> statisticsLocal(IOperator opt, String batch) {
		String ql = statistics_ql
				+ " and x.ownerId=? order by x.ordinalModel asc";
		return __list(Object[].class, ql, opt.getOwnerId());
	}

	@Override
	public List<Object[]> statisticsCity(IOperator opt, String batch) {
		String ql = "select x.place.id from UnitEntity x where x.id=?";
		Long pid = __first(Long.class, ql, opt.getOwnerId());
		String ql0 = statistics_ql
				+ " and x.cityId=? order by x.ordinalModel asc";
		return __list(Object[].class, ql0, pid);
	}

	@Override
	public List<Object[]> getplace(String batch) {
		String ql = "select x.id,x.name from ModelAreaEntity x where x.batch=? order by x.ordinalModel asc";
		return __list(Object[].class, ql, batch);
	}

	public List<ModelAreaEntity[]> getplace1(String batch) {
		String ql = "select x.id,x.name from ModelAreaEntity x where x.batch=? order by x.ordinalModel asc";
		return __list(ModelAreaEntity[].class, ql, batch);
	}

	public String getPlaceName(Long id) {
		String ql = "select x.name from ModelAreaEntity x where x.id=?";
		return __first(String.class, ql, id);
	}

	@Override
	public String getNewRural(Long id) {
		String ql = "select x.name from NewRuralEntity x where x.deleted=false and x.id=?";
		return __first(String.class, ql, id);
	}

	@Override
	public String getPeripheral(Long id) {
		String ql = "select x.name from PeripheralRuralEntity x where x.deleted=false and x.id=?";
		return __first(String.class, ql, id);
	}

	@Override
	public String getKey(Long uid) {
		String ql = "select x.role.roleKey from RoleAssignEntity x where x.account.id=?";
		List<String> items = __list(String.class, ql, uid);
		String key = "";
		for (String it : items) {
			key += it + ",";
		}
		return key;
	}

	@Override
	public List<NewRuralEntity> getNewRurals1(Long id) {
		String ql = "select x.id, x.name from NewRuralEntity x where x.deleted=false and x.modelArea.id=?";
		return __list(NewRuralEntity.class, ql, id);
	}

	@Override
	public CommissionerEntity getByUnit(Long uid) {
		String ql = "select x from CommissionerEntity x where x.deleted=false and x.history=false and x.ownerId=?";
		return __first(CommissionerEntity.class, ql, uid);
	}

	@Override
	public Long getNewRuralFirstId(Long id) {
		String ql = "select x.id from NewRuralEntity x where x.deleted=false and x.modelArea.id=? order by x.id";
		return __first(Long.class, ql, id);
	}

	@Override
	public Long getNewRuralLastId(Long id) {
		String ql = "select x.id from NewRuralEntity x where x.deleted=false and x.modelArea.id=? order by x.id desc";
		return __first(Long.class, ql, id);
	}

	@Override
	public Long getPeripheralFirstId(Long id) {
		String ql = "select x.id from PeripheralRuralEntity x where x.deleted=false and x.modelArea.id=? order by x.id";
		return __first(Long.class, ql, id);
	}

	@Override
	public Long getPeripheralLastId(Long id) {
		String ql = "select x.id from PeripheralRuralEntity x where x.deleted=false and x.modelArea.id=? order by x.id desc";
		return __first(Long.class, ql, id);
	}

	@Override
	public List<BaseRuralEntity> getRurals(Long id) {
		String ql = "select x from BaseRuralEntity x where x.id=?";
		return __list(BaseRuralEntity.class, ql, id);
	}

	@Override
	@Transactional
	public void optimize(IOperator opt, OperateEvent event, Long id) {
		// IOperator opt

		ModelAreaEntity ma = __get(id);
		HandleLogEntity hle = new HandleLogEntity();
		hle.currentUser(opt);
		hle.setTargetType(ModelAreaEntity.class.getName());
		hle.setContent("检查并更正每个片区下行政村ownerId可能不是其所在片区的ownerId情况,行政村所在地区出现多个行政村去除剩一个并将去除掉的行政村所有的自然村归属到所在地区没被删去的行政村下,校验没有行政村的自然村的所在地区和所在地区id如果有自然村没有行政村的则通过其所在地区对应的行政村重新赋值,如果所在地区也没有行政村,则新建一个行政村赋值,删去所有没有自然村的行政村(在对没有行政村的自然村重新赋值的时候如果找到了自然村对应的地区下有行政村存在,该行政村如果有在需要被删除的没有自然村的行政村列表里面,则从列表剔除,自然村赋值上此行政村)");
		entityManager.persist(hle);
		// List<RuralStatusCompare> list=new ArrayList<>();
		// 检查所有自然村，是否存在没有行政村的情况
		// 检查所有行政村，是否存在没有自然村的情况，如果存在，则删除
		List<NewRuralEntity> nrs = __list(NewRuralEntity.class,
				"select x from NewRuralEntity x where x.modelArea.id=?", id);
		List<AdministrationCoreRuralEntity> acrs = __list(
				AdministrationCoreRuralEntity.class,
				"select x from AdministrationCoreRuralEntity x where x.modelArea.id=? and x.deleted=false",
				id);

		// 这是没有关联到行政村的自然村
		List<NewRuralEntity> notAdminCR = new ArrayList<>();
		// 这是已经关联到自然村的行政村的ID
		List<Long> usedAdminCR = new ArrayList<>();
		for (NewRuralEntity t : nrs) {
			if (null == t.getAdminRural()) {
				notAdminCR.add(t);
			} else {
				Long ti = t.getAdminRural().getId();
				/**
				 * 可以有多个自然村对应一个行政村所以还是要判断一下列表里面有没有
				 */
				if (!usedAdminCR.contains(ti)) {
					usedAdminCR.add(ti);
				}
			}
		}

		// 用来处理存在多个相同的行政村的情况，即地区表的ID相同
		Map<AdministrationCoreRuralEntity, AdministrationCoreRuralEntity> replaces = new HashMap<>();

		// Long 为地区表的ID
		Map<Long, AdministrationCoreRuralEntity> canbeUse = new HashMap<>();

		// 存储改变了ownerId的行政村前后ownerId值
		Map<Long, Object[]> adRuralOwnerIdComper = new HashMap<>();

		List<AdministrationCoreRuralEntity> needDelACR = new ArrayList<>();
		// 遍历某一片区下所有行政村
		for (AdministrationCoreRuralEntity a : acrs) {
			// 目前出现了行政村的ownerid被设置为0的情况，这里需要重新赋值
			Long ownerIDOld = a.getOwnerId();
			a.setOwnerId(ma.getOwnerId());
			Long ownerIDNew = a.getOwnerId();
			if (!ownerIDOld.equals(ownerIDNew)) {
				adRuralOwnerIdComper.put(a.getId(), new Object[] { a,
						ownerIDOld, ownerIDNew });
			}

			// 这里的判断作用是: 如果是行政村没有关联到自然村的的就往needDelACR列表添加该行政村
			// needDelACR存储了该片区下没有关联自然村的行政村
			if (!usedAdminCR.contains(a.getId())) {
				needDelACR.add(a);
			}
			// 拿到该片区的某个行政村的地区id 不管是不是有关联到自然村的
			Long pid = a.getPlace().getId();
			// 存放某一地区的行政村是从某一片区下所有行政村拿的 由于map集合key是唯一的所以某一地区只有一个行政村!!!
			AdministrationCoreRuralEntity tmp = canbeUse.get(pid);
			if (null == tmp) {
				canbeUse.put(pid, a);
			} else {
				// 如果canbeUse map集合里面已经有了相同id的行政村即走到这查询该片区的行政村其实是有
				// 出现了俩个行政村的地区id相同的
				// 应该把所有出现a的自然村，替换成tmp tmp是 前一个行政村 a是当前行政村它们id相同
				// 并且需要把a删除
				replaces.put(a, tmp);
			}
		}
		// 存储改变了行政村的自然村前后行政村记录
		Map<Long, Object[]> adRuralChangeByNewRuralComper = new HashMap<>();
		// 记录行政村的删除状态,
		Map<Long, AdministrationCoreRuralEntity> adRuralDelComper = new HashMap<>();

		// 处理地区相同的行政村 把该片区所有属于该行政村的自然村搬到replacesMap集合的对应id的value行政村里
		// 主要是有些属于key的行政村要换到value里 删除replaces key行政村
		for (Map.Entry<AdministrationCoreRuralEntity, AdministrationCoreRuralEntity> entry : replaces
				.entrySet()) {
			AdministrationCoreRuralEntity key = entry.getKey();
			AdministrationCoreRuralEntity val = entry.getValue();
			for (NewRuralEntity t : nrs) {
				// 找到有自然村的key行政村 如果没有就不用将key行政村拥有的自然村赋值给拥有相同地区id的val行政村了,直接删除key
				// 此时val里有没有自然村就不知道了
				// 判断自然村的行政村是否有等于key的
				// 修改setAdminRural之前的AdminRural一定是有的并且就在usedAdminCR中
				if (t.getAdminRural() != null) {
					if (key.getId().equals(t.getAdminRural().getId())) {

						t.setAdminRural(val);

						entityManager.merge(t);
						adRuralChangeByNewRuralComper.put(t.getId(),
								new Object[] { t, key, val });

					}
				}
			}
			// if(needDelACR.contains(o))
			// 如果相同片区的行政村现在拥有了自然村则需从删除列表去除
			if (needDelACR.contains(val)) {
				needDelACR.remove(val);
			}
			for (AdministrationCoreRuralEntity adminCR : needDelACR) {
				if (val.getId().equals(adminCR.getId())) {
					needDelACR.remove(adminCR);
				}
			}

			List<RuralUnitEntity> ruralUnitList = __list(RuralUnitEntity.class,
					"from RuralUnitEntity where adminRural.id=?", key.getId());
			List<RuralWorkGroupEntity> ruralWorkGroupComparerList = __list(
					RuralWorkGroupEntity.class,
					"from RuralWorkGroupEntity as x where x.adminRural.id=?",
					key.getId());
			List<RuralExpertGroupEntity> ruralExpertGroupComparerList = __list(
					RuralExpertGroupEntity.class,
					"from RuralExpertGroupEntity as x where x.adminRural.id=?",
					key.getId());
			for (RuralUnitEntity ruitem : ruralUnitList) {
				RuralUnitComparerEntity var = new RuralUnitComparerEntity(
						ruitem.getAdminRural(), ruitem.getNewRural(),
						ruitem.getUnitName(), ruitem.getRegisteredAddress(),
						ruitem.getContactName(), ruitem.getUnitPhone());

				var.setLog(hle);
				var.setTargetType(RuralUnitEntity.class.getName());
				var.setTargetId(ruitem.getId());
				var.setName("[规划设计单位]" + ruitem.getUnitName());
				entityManager.merge(var);

			}

			for (RuralWorkGroupEntity rwgcitem : ruralWorkGroupComparerList) {

				RuralWorkGroupComparerEntity var = new RuralWorkGroupComparerEntity(
						rwgcitem.getNewRural(), rwgcitem.getAdminRural(),
						rwgcitem.getWorkName(), rwgcitem.getUnitJob(),
						rwgcitem.getWorkPhone(), rwgcitem.getWorkRemark());
				var.setLog(hle);
				var.setTargetType(RuralWorkGroupComparerEntity.class.getName());
				var.setTargetId(rwgcitem.getId());
				var.setName("[工作小组]" + rwgcitem.getWorkName());
				entityManager.merge(var);

			}

			for (RuralExpertGroupEntity reg : ruralExpertGroupComparerList) {

				RuralExpertGroupComparerEntity var = new RuralExpertGroupComparerEntity(
						reg.getNewRural(), reg.getAdminRural(),
						reg.getExpertName(), reg.getExpertJob(),
						reg.getExpertPhone(), reg.getExpertRemark());
				var.setLog(hle);
				var.setTargetType(RuralExpertGroupComparerEntity.class
						.getName());
				var.setTargetId(reg.getId());
				var.setName("[专家指导组名单]" + reg.getExpertName());
				entityManager.merge(var);

			}

			// 直接在数据库里删除
			// 有可能有自然村也有可能没有,有自然村也已经全部赋值到所在同一地区没被删除的行政村上了
			__delACR(key);

			adRuralDelComper.put(key.getId(), key);
		}
		// 记录改变了地区的自然村地区前后
		Map<Long, Object[]> PLByNewRuralComper = new HashMap<>();
		// 记录自然村地区id前后
		Map<Long, Object[]> PLIdByNewRuralComper = new HashMap<>();
		// 记录新的行政村
		List<AdministrationCoreRuralEntity> newAdRural = new ArrayList<>();
		// 处理还没有行政村的自然村,自然村必须有所属行政村
		for (NewRuralEntity t : notAdminCR) {
			PlaceEntity peOld = t.getPlace();
			Long pIdByNewRuralOld = t.getPlaceId();
			PlaceEntity pe = InternalRuralHelper.__findPlace(entityManager, t);
			t.setPlace(pe);
			Long pi = pe.getId();

			/**
			 * 通过重新设定的自然村地区id找到该地区下的行政村
			 */
			AdministrationCoreRuralEntity nar = canbeUse.get(pi);
			// 说明该自然村所在地区没有行政村... null ==
			// nar说明没有id=pi的地区,可自然村有这个地区所有必须加回canbeUse
			if (null == nar) {
				nar = new AdministrationCoreRuralEntity();
				// 将自然村所在片区 地区赋值给 行政村
				__init(nar, event, t);
				// 新创建的行政村
				newAdRural.add(nar);
				// 加回去，下一个自然村可能需要使用 重新加上一个地区-行政村 不用担心新行政村会被
				// needDelACR.remove里面没有这个新的行政村id
				canbeUse.put(pi, nar);

			} else {
				// 尝试在需要删除的列表里去掉
				// 上面找回了或者本身该自然村就有地区通过该自然村地区的id找到了行政村的存在就不该删除了,该自然村下所在地区有该行政村的就不该删除
				// 这里的needDelACR 存储的行政村是之前的在数据库里的 所以在 canbeUse.put(pi,
				// nar);就算加进来用remove(nar)也是删除不了的 因为nar = canbeUse.get(pi);
				// nar是新找到的行政村

				needDelACR.remove(nar);
			}
			// 最终创建了一个新的行政村给自然村用,保存到数据库了
			AdministrationCoreRuralEntity adRuralOld = t.getAdminRural();
			t.setAdminRural(nar);
			entityManager.merge(t);
			adRuralChangeByNewRuralComper.put(t.getId(), new Object[] { t,
					adRuralOld, t.getAdminRural() });
			PlaceEntity peNew = t.getPlace();
			PLByNewRuralComper.put(t.getId(), new Object[] { t, peOld, peNew });
			Long pIdByNewRuralNew = t.getPlaceId();
			PLIdByNewRuralComper.put(t.getId(), new Object[] { t,
					pIdByNewRuralOld, pIdByNewRuralNew });
		}
		// 把检测到没有自然村的行政村全部删掉
		for (AdministrationCoreRuralEntity a : needDelACR) {

			__delACR(a);

			adRuralDelComper.put(a.getId(), a);
		}

		// adRuralOwnerIdComper adRuralChangeByNewRuralComper adRuralDelComper
		// PLByNewRuralComper PLIdByNewRuralComper
		Map<Long, NewRuralStatusComparerEntity> rSC = new HashMap<>();
		for (Map.Entry<Long, Object[]> entry : adRuralChangeByNewRuralComper
				.entrySet()) {

			Long nrid = entry.getKey();

			NewRuralStatusComparerEntity rsc = new NewRuralStatusComparerEntity();

			rsc.setLog(hle);
			rsc.setMaid(ma.getId());
			rsc.setName("[自然村]"
					+ ((NewRuralEntity) entry.getValue()[0])
							.getNaturalVillage());
			rsc.setTargetId(nrid);
			rsc.setTargetType(NewRuralEntity.class.getName());
			rsc.setAdRuralOld((AdministrationCoreRuralEntity) (entry.getValue()[1]));
			rsc.setAdRuralNow((AdministrationCoreRuralEntity) (entry.getValue()[2]));

			// 对于基本类型的字段需要将不需要比较的设置为-1,否则查询会报基本类型值为空值异常

			rsc.setNewRuralPlaceIdOld(0l);
			rsc.setNewRuralPlaceIdNow(0l);

			rSC.put(rsc.getTargetId(), rsc);
		}
		// 新建一个行政村记录表吧

		// 先看修改过数据的自然村列表里有没有对应的

		if (!PLByNewRuralComper.isEmpty() && PLByNewRuralComper.size() > 0) {
			for (Map.Entry<Long, Object[]> entry : PLByNewRuralComper
					.entrySet()) {
				Long nrid = entry.getKey();
				NewRuralStatusComparerEntity savePL = rSC.get(nrid);
				if (savePL != null) {
					savePL.setRuralPlaceOld((PlaceEntity) entry.getValue()[1]);
					savePL.setRuralPlaceNow((PlaceEntity) entry.getValue()[2]);

				} else {
					// newRSC.put(nrid, value)
					NewRuralStatusComparerEntity newRSC = new NewRuralStatusComparerEntity();

					newRSC.setLog(hle);
					newRSC.setMaid(ma.getId());
					newRSC.setName("[自然村]"
							+ ((NewRuralEntity) entry.getValue()[0])
									.getNaturalVillage());
					newRSC.setTargetId(nrid);
					newRSC.setTargetType(NewRuralEntity.class.getName());
					newRSC.setRuralPlaceOld((PlaceEntity) entry.getValue()[1]);
					newRSC.setRuralPlaceNow((PlaceEntity) entry.getValue()[2]);

					// 对于基本类型的字段需要将不需要比较的设置为-1,否则查询会报基本类型值为空值异常

					newRSC.setNewRuralPlaceIdOld(0l);
					newRSC.setNewRuralPlaceIdNow(0l);
					rSC.put(newRSC.getTargetId(), newRSC);
				}
			}
		}
		if (!PLIdByNewRuralComper.isEmpty() && PLIdByNewRuralComper.size() > 0) {
			for (Map.Entry<Long, Object[]> entry : PLIdByNewRuralComper
					.entrySet()) {

				Long nrid = entry.getKey();
				NewRuralStatusComparerEntity savePL = rSC.get(nrid);
				if (savePL != null) {
					savePL.setNewRuralPlaceIdOld((Long) entry.getValue()[1]);
					savePL.setNewRuralPlaceIdNow((Long) entry.getValue()[2]);
				} else {
					// newRSC.put(nrid, value)
					NewRuralStatusComparerEntity newRSC = new NewRuralStatusComparerEntity();

					newRSC.setLog(hle);
					newRSC.setMaid(ma.getId());
					newRSC.setName("[自然村]"
							+ ((NewRuralEntity) entry.getValue()[0])
									.getNaturalVillage());
					newRSC.setTargetId(nrid);
					newRSC.setTargetType(NewRuralEntity.class.getName());
					newRSC.setNewRuralPlaceIdOld((Long) entry.getValue()[1]);
					newRSC.setNewRuralPlaceIdNow((Long) entry.getValue()[2]);

					// 对于基本类型的字段需要将不需要比较的设置为-1,否则查询会报基本类型值为空值异常

					rSC.put(newRSC.getTargetId(), newRSC);

				}
			}

		}
		Map<Long, AdRuralStatusComparerEntity> ruralStatusComparerByAdRural = new HashMap<>();
		if (!adRuralOwnerIdComper.isEmpty() && adRuralOwnerIdComper.size() > 0) {
			for (Map.Entry<Long, Object[]> entry : adRuralOwnerIdComper
					.entrySet()) {

				Long nrid = entry.getKey();
				AdRuralStatusComparerEntity rscByAdRural = new AdRuralStatusComparerEntity();
				rscByAdRural.setLog(hle);
				rscByAdRural.setMaid(ma.getId());
				rscByAdRural.setName("[主体村]"
						+ ((AdministrationCoreRuralEntity) entry.getValue()[0])
								.getName());
				rscByAdRural.setTargetId(nrid);
				rscByAdRural.setTargetType(AdministrationCoreRuralEntity.class
						.getName());
				rscByAdRural.setAdRuralOwnerIdOld((Long) entry.getValue()[1]);
				rscByAdRural.setAdRuralOwnerIdNow((Long) entry.getValue()[2]);

				// 对于基本类型的字段需要将不需要比较的设置为-1,否则查询会报基本类型值为空值异常

				AdministrationCoreRuralEntity delByAdrural = adRuralDelComper
						.get(nrid);
				if (delByAdrural != null) {
					rscByAdRural.setAdRuralIsDel(delByAdrural.isDeleted());
					adRuralDelComper.remove(nrid);
				}
				ruralStatusComparerByAdRural.put(nrid, rscByAdRural);
			}
		}

		if (!adRuralDelComper.isEmpty() && adRuralDelComper.size() > 0) {
			for (Map.Entry<Long, AdministrationCoreRuralEntity> entry : adRuralDelComper
					.entrySet()) {
				Long nrid = entry.getKey();

				AdRuralStatusComparerEntity rscByAdRural = new AdRuralStatusComparerEntity();
				rscByAdRural.setLog(hle);
				rscByAdRural.setMaid(ma.getId());
				rscByAdRural.setName("[主体村]"
						+ ((AdministrationCoreRuralEntity) entry.getValue())
								.getName());
				rscByAdRural.setTargetId(nrid);
				rscByAdRural.setTargetType(AdministrationCoreRuralEntity.class
						.getName());
				rscByAdRural.setAdRuralIsDel(entry.getValue().isDeleted());

				// 对于基本类型的字段需要将不需要比较的设置为0l,否则查询会报基本类型值为空值异常
				rscByAdRural.setAdRuralOwnerIdOld(0l);
				rscByAdRural.setAdRuralOwnerIdNow(0l);

				ruralStatusComparerByAdRural.put(nrid, rscByAdRural);
				// System.out.println(ruralStatusComparerByAdRural.get(nrid)
				// .getName());
			}
		}

		if (newAdRural != null && newAdRural.size() > 0) {
			for (AdministrationCoreRuralEntity adRural : newAdRural) {

				Long nrid = adRural.getId();
				AdRuralStatusComparerEntity rscByAdRural = new AdRuralStatusComparerEntity();
				rscByAdRural.setLog(hle);
				rscByAdRural.setMaid(ma.getId());
				rscByAdRural.setName("[主体村]" + adRural.getName());
				rscByAdRural.setTargetId(nrid);
				rscByAdRural.setTargetType(AdministrationCoreRuralEntity.class
						.getName());
				rscByAdRural.setNewAddAdRural(true);
				rscByAdRural.setAdRuralIsDel(false);
				rscByAdRural.setRuralPlaceNow(adRural.getPlace());
				rscByAdRural.setAdRuralOwnerIdNow(adRural.getOwnerId());
				// 对于基本类型的字段需要将不需要比较的设置为0l,否则查询会报基本类型值为空值异常
				rscByAdRural.setAdRuralOwnerIdOld(0l);

				ruralStatusComparerByAdRural.put(nrid, rscByAdRural);

			}
		}

		Set<Long> set = rSC.keySet();

		for (Long key : set) {
			NewRuralStatusComparerEntity rsc = rSC.get(key);
			entityManager.merge(rsc);
		}
		for (Long key : ruralStatusComparerByAdRural.keySet()) {
			AdRuralStatusComparerEntity rsc = ruralStatusComparerByAdRural
					.get(key);
			entityManager.merge(rsc);
		}

	}

	@Override
	@Transactional
	public void back(Long id, IOperator user) {
		ModelAreaEntity ma = __get(id);
		int statusOld = ma.getStatus();
		ma.setStatus(ApplicantStatus.UNSTART);
		HandleLogEntity hle = new HandleLogEntity();
		hle.currentUser(user);
		hle.setTargetType(ma.getClass().getName());
		hle.setTargetId(ma.getId());
		hle.setContent("[片区]" + ma.getName() + "被退回 由状态:" + statusOld + "改为状态:"
				+ ApplicantStatus.UNSTART);
		entityManager.persist(hle);
		entityManager.merge(ma);
	}

	@Override
	@Transactional
	public void returnMA(Long id, IOperator user) {
		ModelAreaEntity ma = __get(id);
		int statusOld = ma.getStatus();
		ma.setStatus(ApplicantStatus.DRAFT);
		HandleLogEntity hle = new HandleLogEntity();
		hle.currentUser(user);
		hle.setTargetType(ma.getClass().getName());
		hle.setTargetId(ma.getId());
		hle.setContent("[片区]" + ma.getName() + "被返回 由状态:" + statusOld + "改为状态:"
				+ ApplicantStatus.DRAFT);
		entityManager.persist(hle);
		entityManager.merge(ma);
	}

	/**
	 * 刷新行政村
	 */
	@Override
	@Transactional
	public void refreshing(Long id, IOperator user) {
		HandleLogEntity hle = new HandleLogEntity();
		hle.currentUser(user);
		hle.setTargetId(id);
		hle.setTargetType(ModelAreaEntity.class.getName());
		hle.setContent("对id=" + id + "片区下的所有行政村(主体行政村,非主体行政村)所拥有的自然村数进行刷新");
		entityManager.persist(hle);
		// 主体行政村
		String ql = "select x from AdministrationCoreRuralEntity x where x.modelArea.id=? and x.deleted=false";
		String nql = "select x.naturalVillage from NewRuralEntity x  where  x.adminRural.id=? and x.deleted=false";
		List<AdministrationCoreRuralEntity> coreList = __list(
				AdministrationCoreRuralEntity.class, ql, id);
		for (AdministrationCoreRuralEntity acr : coreList) {
			String natrualruralListOld = acr.getNatrualruralList();
			int natrualruralNumOld = acr.getNatrualruralNum();
			List<String> natureVillages = __list(String.class, nql, acr.getId());
			StringBuffer natrualruralList = new StringBuffer("");
			if (natureVillages != null && natureVillages.size() > 0) {
				for (int i = 0; i < natureVillages.size(); i++) {
					natrualruralList = i == natureVillages.size() - 1 ? natrualruralList
							.append(natureVillages.get(i)).append("")
							: natrualruralList.append(natureVillages.get(i))
									.append(",");
				}
				acr.setNatrualruralNum(natureVillages.size());

			}
			String natrualruralListS = natrualruralList.toString();
			if (acr.getNatrualruralNum() != natrualruralNumOld
					|| !natrualruralListS.equals(natrualruralListOld)) {
				AdminRuralFrushBaseRuralComparerEntity arfbrc = new AdminRuralFrushBaseRuralComparerEntity();
				arfbrc.setMSG(hle, id, "[主体行政村]" + acr.getName(), acr
						.getClass().getName(), acr.getId());
				arfbrc.setNatrualruralListOld(natrualruralListOld);
				arfbrc.setNatrualruralListNew(natrualruralListS);
				arfbrc.setNatrualruralNumOld(natrualruralNumOld);
				arfbrc.setNatrualruralNumNew(natureVillages.size());
				entityManager.persist(arfbrc);
			}
			// AdminRuralFrushBaseRuralComparerEntity arfbrc =new
			// AdminRuralFrushBaseRuralComparerEntity();
			//
			// arfbrc.setMSG(hle, id, "[行政村]"+acr.getName(),
			// acr.getClass().getName(), acr.getId());
			// arfbrc.setNatrualruralListOld(natrualruralListOld);
			// arfbrc.setNatrualruralListNew(natrualruralListS);
			// arfbrc.setNatrualruralNumOld(natrualruralNumOld);
			// arfbrc.setNatrualruralNumNew(natureVillages.size());
			// entityManager.persist(arfbrc);
			acr.setNatrualruralList(natrualruralListS);

			entityManager.merge(acr);
		}

		// 非主体行政村
		String unql = "select x from AdministrationUncoreRuralEntity x where x.modelArea.id=? and x.deleted=false";
		String unnql = "select x.naturalVillage from PeripheralRuralEntity x  where  x.adminRural.id=? and x.deleted=false";
		List<AdministrationUncoreRuralEntity> uncoreList = __list(
				AdministrationUncoreRuralEntity.class, unql, id);
		for (AdministrationUncoreRuralEntity ucacr : uncoreList) {
			String natrualruralListOld = ucacr.getNatrualruralList();
			int natrualruralNumOld = ucacr.getNatrualruralNum();
			List<String> natureVillages = __list(String.class, unnql,
					ucacr.getId());
			StringBuffer natrualruralList = new StringBuffer("");
			if (natureVillages != null && natureVillages.size() > 0) {
				for (int i = 0; i < natureVillages.size(); i++) {
					natrualruralList = i == natureVillages.size() - 1 ? natrualruralList
							.append(natureVillages.get(i)).append("")
							: natrualruralList.append(natureVillages.get(i))
									.append(",");
				}
				ucacr.setNatrualruralNum(natureVillages.size());
			}
			String natrualruralListS = natrualruralList.toString();
			if (ucacr.getNatrualruralNum() != natrualruralNumOld
					|| !natrualruralListS.equals(natrualruralListOld)) {
				AdminRuralFrushBaseRuralComparerEntity arfbrc = new AdminRuralFrushBaseRuralComparerEntity();

				arfbrc.setMSG(hle, id, "[非主体行政村]" + ucacr.getName(), ucacr
						.getClass().getName(), ucacr.getId());
				arfbrc.setNatrualruralListOld(natrualruralListOld);
				arfbrc.setNatrualruralListNew(natrualruralListS);
				arfbrc.setNatrualruralNumOld(natrualruralNumOld);
				arfbrc.setNatrualruralNumNew(natureVillages.size());
				entityManager.persist(arfbrc);
			}
			ucacr.setNatrualruralList(natrualruralList.toString());
			entityManager.merge(ucacr);
		}
	}

	@Override
	@Transactional
	public void extract(Long id) {
		String ql = "select x from AdministrationCoreRuralEntity x where x.modelArea.id=? and x.deleted=false";
		String nql = "select x from NewRuralEntity x where x.adminRural.id=? and x.deleted=false";
		for (AdministrationCoreRuralEntity acr : __list(
				AdministrationCoreRuralEntity.class, ql, id)) {
			for (NewRuralEntity nr : __list(NewRuralEntity.class, nql,
					acr.getId())) {
				acr.setHistroyDesc(StringHelper.get(acr.getHistroyDesc(),
						nr.getHistroyDesc()));
				acr.setEcologicalDesc(StringHelper.get(acr.getEcologicalDesc(),
						nr.getEcologicalDesc()));
				acr.setCivilianDesc(StringHelper.get(acr.getCivilianDesc(),
						nr.getCivilianDesc()));
				acr.setTourDesc(StringHelper.get(acr.getTourDesc(),
						nr.getTourDesc()));
				acr.setIndustryDesc(StringHelper.get(acr.getIndustryDesc(),
						nr.getIndustryDesc()));
				acr.setFisheryDesc(StringHelper.get(acr.getFisheryDesc(),
						nr.getFisheryDesc()));
				acr.setOtherDesc(StringHelper.get(acr.getOtherDesc(),
						nr.getOtherDesc()));
				if (acr.getViewSpot() == 0) {
					acr.setViewSpot(nr.getViewSpot());
				}
				acr.setViewSpot1(StringHelper.get(acr.getViewSpot1(),
						nr.getViewSpot1()));
				acr.setViewSpot2(StringHelper.get(acr.getViewSpot2(),
						nr.getViewSpot2()));
				acr.setViewSpot3(StringHelper.get(acr.getViewSpot3(),
						nr.getViewSpot3()));

				// if(acr.getCulturalAct() == 0){
				// acr.setCulturalAct(nr.getCulturalAct());
				// }
				// if(acr.getCulturalActArea() == 0){
				// acr.setCulturalActArea(nr.getCulturalActArea());
				// }
				// acr.setCulturalActAnn(StringHelper.get(acr.getCulturalActAnn(),
				// nr.getCulturalActAnn()));

			}
		}
	}

	@Override
	@Transactional
	public void ordinal(Long[] ids, Map<?, ?> map, IOperator user) {
		if (null != ids) {
			for (Long id : ids) {
				Object it = map.get("val_" + id);
				if (it instanceof String) {
					__exec("update ModelAreaEntity set ordinalModel=" + it
							+ " where id=" + id);
					HandleLogEntity hlog = new HandleLogEntity();
					hlog.currentUser(user);
					hlog.setTargetId(id);
					hlog.setTargetType(ModelAreaEntity.class.getName());
					hlog.setContent("修改自定义排序id" + id + "改成" + it);
					entityManager.persist(hlog);
				} else if (it instanceof String[]) {
					String[] ps = (String[]) it;
					if (ps.length > 0 && StringHelper.isNotEmpty(ps[0])) {
						__exec("update ModelAreaEntity set ordinalModel="
								+ ps[0] + " where id=" + id);
						HandleLogEntity hlog = new HandleLogEntity();
						hlog.currentUser(user);
						hlog.setTargetId(id);
						hlog.setTargetType(ModelAreaEntity.class.getName());
						hlog.setContent("修改自定义排序id" + id + "改成" + ps[0]);
						entityManager.persist(hlog);
					}
				}
			}
		}
	}

	@Override
	public ModelAreaConfig getConfig() {
		return __config();
	}

	@Override
	public boolean check(Long id, IOperator opt) {
		String ql = "select x.batch from ModelAreaEntity x where id=? and x.deleted=false";
		return __config().check(__first(String.class, ql, id), opt, false);
	}

	@Override
	public List<Object[]> progress() {
		String ql = "select x.batch,x.status,x.name,x.county from ModelAreaEntity x where x.deleted=false";
		return __list(Object[].class, ql);
	}

	@Override
	public int countBy(Class<?> clsName) {
		String ql = "select count(x) from " + clsName.getName()
				+ " x where x.deleted=false and x.modelArea.deleted=false";
		return __first(Number.class, ql).intValue();
	}

	@Override
	public Object[] getSum(Long ModelAreaId) {
		String ql = "select sum(x.houseHold),sum(x.population) from AdministrationCoreRuralEntity x where x.modelArea.id=? and x.deleted=false";
		return __first(Object[].class, ql, ModelAreaId);
	}

	@Override
	public Object[] getPSum(Long ModelAreaId) {
		String ql = "select sum(x.houseHold),sum(x.population) from AdministrationUncoreRuralEntity x where x.modelArea.id=? and x.deleted=false";
		return __first(Object[].class, ql, ModelAreaId);
	}

	// 省级获取片区台账户数与人口数
	@Override
	@Transactional
	public void extractHouse(Long ModelAreaId, IOperator user) {
		ModelAreaEntity mae = __get(ModelAreaId);
		__extractHouse(mae, user);
		entityManager.merge(mae);
	}

	private void __extractHouse(ModelAreaEntity mae, IOperator user) {
		HandleLogEntity hle = new HandleLogEntity();
		hle.currentUser(user);
		hle.setTargetType(mae.getClass().getName());
		hle.setTargetId(mae.getId());
		hle.setContent("对id=" + mae.getId() + "片区" + mae.getName()
				+ "提取人口数进行记录");
		entityManager.persist(hle);
		int mainSumHouseOld = mae.getMainSumHouse();
		int mainSumPOld = mae.getMainSumP();
		int aroundSumHouseOld = mae.getAroundSumHouse();
		int aroundSumPOld = mae.getAroundSumP();

		String ql = "select sum(x.houseHold),sum(x.population) from AdministrationCoreRuralEntity x where x.modelArea.id=? and x.deleted=false";
		String ql2 = "select sum(x.houseHold),sum(x.population) from AdministrationUncoreRuralEntity x where x.modelArea.id=? and x.deleted=false";

		Long maId = mae.getId();
		Object[] main = __first(Object[].class, ql, maId);
		Object[] around = __first(Object[].class, ql2, maId);

		if (main[0] != null) {
			mae.setMainSumHouse(((Number) main[0]).intValue());
		} else {
			mae.setMainSumHouse(0);
		}
		if (main[1] != null) {
			mae.setMainSumP(((Number) main[1]).intValue());
		} else {
			mae.setMainSumP(0);
		}
		if (around[0] != null) {
			mae.setAroundSumHouse(((Number) around[0]).intValue());
		} else {
			mae.setAroundSumHouse(0);
		}
		if (around[1] != null) {
			mae.setAroundSumP(((Number) around[1]).intValue());
		} else {
			mae.setAroundSumP(0);
		}
		if (mainSumHouseOld == mae.getMainSumHouse()
				&& mainSumPOld == mae.getMainSumP()
				&& aroundSumHouseOld == mae.getAroundSumHouse()
				&& aroundSumPOld == mae.getAroundSumP()) {
			return;
		}

		ExtractHouseComparerEntity ehce = new ExtractHouseComparerEntity(
				mainSumHouseOld, mainSumPOld, aroundSumHouseOld, aroundSumPOld,
				mae.getMainSumHouse(), mae.getMainSumP(),
				mae.getAroundSumHouse(), mae.getAroundSumP());
		ehce.setLog(hle);
		ehce.setTargetType(mae.getClass().getName());
		ehce.setTargetId(mae.getId());
		ehce.setName(mae.getName());

		entityManager.persist(ehce);
	}

	@Override
	public CommissionerEntity getByUser(IOperator user) {
		// TODO 查询信息专员
		String ql = "select x from CommissionerEntity x where x.history=false and x.deleted=false and x.ownerId=?";
		return __first(CommissionerEntity.class, ql, user.getOwnerId());
	}

	// ------------------------------------------------------------------------------------------------
	@Override
	public List<Object[]> getModelAreaByBatch(String batch) {
		String ql = "select x.id,x.cityName from ModelAreaEntity x where x.deleted=false and x.batch=?";
		List<Object[]> lists = __list(Object[].class, ql, batch);
		return lists;
	}

	@Override
	public List<ModelAreaEntity> getModelArea(String batch) {
		String ql = "select x from ModelAreaEntity x where x.deleted=false and x.batch=? order by x.ordinalModel asc";
		List<ModelAreaEntity> lists = __list(ModelAreaEntity.class, ql, batch);
		return lists;
	}

	@Override
	public Map<String, Object> getMa_title_show(Long maid) {
		Map<String, Object> map = new HashMap<>();
		String acNum_ql = "select count(x.id) from AdministrationCoreRuralEntity x where x.deleted=false and x.modelArea.id=?";
		String aucNum_ql = "select count(x.id) from AdministrationUncoreRuralEntity x where x.deleted=false and x.modelArea.id=?";
		String nrNum_ql = "select count(x.id) from NewRuralEntity x where x.deleted=false and x.modelArea.id=?";
		String prNum_ql = "select count(x.id) from PeripheralRuralEntity x where x.deleted=false and x.modelArea.id=?";
		Long acNum = __first(Long.class, acNum_ql, maid);
		Long aucNum = __first(Long.class, aucNum_ql, maid);
		Long nrNum = __first(Long.class, nrNum_ql, maid);
		Long prNum = __first(Long.class, prNum_ql, maid);
		map.put("acNum", acNum);
		map.put("aucNum", aucNum);
		map.put("nrNum", nrNum);
		map.put("prNum", prNum);
		return map;
	}

	/**
	 * @author wsf 1: 对删除项目原始的累计投入资金做了资金改变记录 2:
	 *         删除项目原始的累计投入资金并对片区总的累计已经投入使用的资金进行重新累加
	 */
	@Override
	@Transactional
	public void clearProjectFunds(Long id, IOperator user) {
		ModelAreaEntity ma = __get(id);
		HandleLogEntity hle = new HandleLogEntity();
		hle.currentUser(user);
		hle.setTargetType(ma.getClass().getName());
		hle.setTargetId(ma.getId());
		hle.setContent("删除了片区: " + ma.getName() + " 下所有项目的初始累计资金");
		entityManager.persist(hle);
		InvestmentInfo maivn = ma.getInvestment();
		InvestmentComparer investmentC = new InvestmentComparer();
		InvestmentInfo investmentI = new InvestmentInfo();
		investmentC.setSource(investmentI);
		investmentC.setTarget(maivn);
		if (maivn != null) {
			maivn.copy(investmentI);
			maivn.clear();
		} else {
			maivn = new InvestmentInfo();
			ma.setInvestment(maivn);
		}
		ModelAreaInvComparerEntity maic = new ModelAreaInvComparerEntity();
		maic.setTargetType(ma.getClass().getName());
		maic.setTargetId(ma.getId());
		maic.setName("[片区]" + ma.getName());
		maic.setLog(hle);
		maic.setInvest(investmentC);
		InvestmentInfo test = new InvestmentInfo();
		String ql = "select x from ProjectEntity x where x.modelArea.id=? and x.deleted=false";
		List<ProjectEntity> pes = __list(ProjectEntity.class, ql, id);
		ql = "select x from ProjectReportItem x where x.report.status=1 and x.project.id=?";
		for (ProjectEntity pe : pes) {
			DelPorSouInvComparerEntity dps = new DelPorSouInvComparerEntity();
			dps.setMSG(hle, pe.getModelArea().getId(), "[项目]" + pe.getName(),
					pe.getClass().getName(), pe.getId());
			InvestmentInfo sourceInvestmentIOld = new InvestmentInfo();
			pe.getSourceInvestment().copy(sourceInvestmentIOld);
			dps.setSourceInvestment(sourceInvestmentIOld);
			InvestmentComparer invest = new InvestmentComparer();
			InvestmentInfo totalInvestmentIOld = new InvestmentInfo();
			pe.getTotalInvestment().copy(totalInvestmentIOld);
			invest.setSource(totalInvestmentIOld);
			pe.getTotalInvestment().clear(pe.getSourceInvestment());
			entityManager.merge(pe);
			invest.setTarget(pe.getTotalInvestment());
			dps.setInvestCom(invest);
			entityManager.persist(dps);
			/**
			 * maivn.sum(pe.getTotalInvestment());
			 */

			/**
			 * test project的资金和project下的projectReportItem资金累加起来是否一致
			 * 
			 */
			test.sum(pe.getTotalInvestment());
			// 直接拿ProjectReportItem累计给片区总资金吧
			List<ProjectReportItem> pril = __list(ProjectReportItem.class, ql,
					pe.getId());
			for (ProjectReportItem pri : pril) {
				maivn.sum(pri.getInvestment());
			}
		}

		System.out.println(test.getCityFunds());
		System.out.println(test.getCountyFunds());
		System.out.println(test.getLocalFunds());
		System.out.println(test.getMassFunds());
		System.out.println(test.getOtherFunds());
		System.out.println(test.getProvinceFunds());
		System.out.println(test.getSocialFunds());
		System.out.println(test.getSpecialFunds());
		System.out.println(test.getStateFunds());
		System.out.println(test.getTotalFunds());

		System.out.println("\n");
		System.out.println(maivn.getCityFunds());
		System.out.println(maivn.getCountyFunds());
		System.out.println(maivn.getLocalFunds());
		System.out.println(maivn.getMassFunds());
		System.out.println(maivn.getOtherFunds());
		System.out.println(maivn.getProvinceFunds());
		System.out.println(maivn.getSocialFunds());
		System.out.println(maivn.getSpecialFunds());
		System.out.println(maivn.getStateFunds());
		System.out.println(maivn.getTotalFunds());
		ma.setUpdateAt(new Date());
		entityManager.merge(maic);
		entityManager.merge(ma);

	}

	/**
	 * 删除某年某月月度项目报表,所有属于该年该月的项目月报projectReportItem都要删去
	 */
	@Override
	@Transactional
	public void removeProjectMonthly(Long id, int py, int pm, IOperator user) {
		String ql = "select x from ProjectReportEntity x where x.modelArea.id=? and x.annual=? and x.period=?";
		ProjectReportEntity pre = __first(ProjectReportEntity.class, ql, id,
				py, pm);
		if (null == pre) {
			throw new RuntimeException("该月报不存在！");
		}
		ModelAreaEntity ma = __first(ModelAreaEntity.class,
				"from ModelAreaEntity where id=?", id);
		HandleLogEntity hle = new HandleLogEntity();
		hle.currentUser(user);
		hle.setTargetType(ModelAreaEntity.class.getName());
		hle.setTargetId(ma.getId());
		hle.setContent("删除了片区为:" + ma.getName() + " " + py + "年" + (pm + 1)
				+ "月月度项目报表");
		entityManager.persist(hle);

		// 删除了哪个月度项目报表ProjectReportEntity
		ProjectReportComparerEntity prc = new ProjectReportComparerEntity(
				pre.getInvestment(), pre.getLabourCount(), pre.getSpend(),
				pre.getBack(), pre.getStatus(), pre.getSubmitAt(),
				pre.getBackAt(), pre.getPeriod(), pre.getAnnual(),
				pre.getType(), pre.getReports(), pre.getModelArea(),
				pre.getTotalCount(), pre.getFinishCount(), pre.getStopCount(),
				pre.getZeroCount());
		prc.setLog(hle);
		prc.setTargetType(pre.getClass().getName());
		prc.setTargetId(pre.getId());
		prc.setName(py + "年" + (pm + 1) + "月月度项目报表");

		entityManager.persist(prc);

		// 存下改变了的Project
		Set<ProjectEntity> needChangePro = new TreeSet<ProjectEntity>(
				new Comparator<ProjectEntity>() {
					@Override
					public int compare(ProjectEntity o1, ProjectEntity o2) {
						// TODO Auto-generated method stub
						return (int) (o1.getId() - o2.getId());
					}
				});

		// 删除了哪些项目item
		// 把这个月报删了，再重新统计项目的累计资金
		for (ProjectReportItem pri : pre.getReports()) {
			// this.report = report;
			// this.project = project;
			// this.statusChange = statusChange;
			// this.investment = investment;
			// this.labourCount = labourCount;
			// this.spend = spend;
			// this.projectStatus = projectStatus;
			// this.exitReason = exitReason;

			needChangePro.add(pri.getProject());

			DelProRepItemComparerEntity dpic = new DelProRepItemComparerEntity(
					pri.getReport(), pri.getProject(), pri.getStatusChange(),
					pri.getInvestment(), pri.getLabourCount(), pri.getSpend(),
					pri.getProjectStatus(), pri.getExitReason());
			dpic.setLog(hle);
			dpic.setName("[项目月报]" + py + "年" + (pm + 1) + "月"
					+ pri.getProject().getName() + "月报");
			dpic.setTargetId(pri.getId());
			dpic.setTargetType(pri.getClass().getName());

			entityManager.merge(dpic);
			entityManager.remove(pri);
		}

		entityManager.remove(pre);
		// 重新处理项目的累计资金

		InvestmentInfo maivn = ma.getInvestment();
		InvestmentComparer investmentC = new InvestmentComparer();
		InvestmentInfo investmentI = new InvestmentInfo();

		investmentC.setSource(investmentI);
		investmentC.setTarget(maivn);
		ModelAreaInvComparerEntity maic = new ModelAreaInvComparerEntity();
		maic.setTargetType(ma.getClass().getName());
		maic.setTargetId(ma.getId());
		maic.setName("[片区]" + ma.getName());
		maic.setLog(hle);
		maic.setInvest(investmentC);
		if (null == maivn) {
			maivn = new InvestmentInfo();
			ma.setInvestment(maivn);
			maivn.copy(investmentI);
		} else {
			// 重新进行累计
			maivn.copy(investmentI);
			maivn.clear();
		}

		String iql = "select x from ProjectReportItem x where x.report.status=1 and x.project.id=?";
		ql = "select x from ProjectEntity x where x.modelArea.id=? and x.deleted=false";
		// 存一下所有的项目变化前的
		Map<Long, Object[]> oldVar = new HashMap<>();

		List<ProjectEntity> pes = __list(ProjectEntity.class, ql, id);
		for (ProjectEntity pe : pes) {

			InvestmentInfo investmentIOld = new InvestmentInfo();
			InvestmentInfo pivn = pe.getTotalInvestment();
			int rplcOld = pe.getLabourCount();
			double rpsdOld = pe.getSpend();
			Object[] var = new Object[6];
			oldVar.put(pe.getId(), var);
			var[0] = investmentIOld;
			var[1] = rplcOld;
			var[2] = rpsdOld;
			if (null == pivn) {
				pivn = new InvestmentInfo();
				pe.setTotalInvestment(pivn);
				pivn.copy(investmentIOld);
			} else {
				pivn.copy(investmentIOld);
				pivn.clear();
			}
			pivn.sum(pe.getSourceInvestment());
			int rplc = 0;
			double rpsd = 0;
			for (ProjectReportItem it : __list(ProjectReportItem.class, iql,
					pe.getId())) {
				pivn.sum(it.getInvestment());
				rplc += it.getLabourCount();
				rpsd = DoubleHelper.add(rpsd, it.getSpend());
			}
			pe.setSumLabourCount(rplc);
			pe.setSumSpend(rpsd);

			var[3] = pivn;
			var[4] = rplc;
			var[5] = rpsd;
			// 这个判断有问题如果各种资金本来就是全0投工数0那比较不出来的但实际上月报都删除了这里就没得记录项目资金改变的记录 不过也没改变资金
			// if(!investmentIOld.equals(pivn)||rplcOld!=rplc||rpsdOld!=rpsd){
			// ProMsgComparerEntity promsgC=new ProMsgComparerEntity();
			// InvestmentComparer investC=new InvestmentComparer();
			// investC.setSource(investmentIOld);
			// investC.setTarget(pivn);
			// promsgC.setInvest(investC);
			// promsgC.setLabourCountOld(rplcOld);
			// promsgC.setLabourCountNow(rplc);
			// promsgC.setSpendOld(rpsdOld);
			// promsgC.setSpendNow(rpsd);
			// promsgC.setTargetType(pe.getClass().getName());
			// promsgC.setTargetId(pe.getId());
			// promsgC.setName(pe.getName());
			// entityManager.persist(promsgC);
			// }
			// 删了项目的item资金 如果item资金为0 那么项目资金跟没删一样为0上面就保存不了了不过item没资金还报上来干掉

			entityManager.merge(pe);
			maivn.sum(pivn);
		}
		for (ProjectEntity pro : needChangePro) {
			Set<Long> keySet = oldVar.keySet();
			Iterator<Long> it = keySet.iterator();
			while (it.hasNext()) {
				Long key = it.next();
				if (pro.getId().equals(key)) {
					Object[] compareVar = oldVar.get(key);
					ProMsgComparerEntity promsgC = new ProMsgComparerEntity();
					InvestmentComparer investC = new InvestmentComparer();
					investC.setSource((InvestmentInfo) compareVar[0]);
					investC.setTarget((InvestmentInfo) compareVar[3]);
					promsgC.setInvest(investC);
					promsgC.setLabourCountOld((int) compareVar[1]);
					promsgC.setLabourCountNow((int) compareVar[4]);
					promsgC.setSpendOld((double) compareVar[2]);
					promsgC.setSpendNow((double) compareVar[5]);
					promsgC.setTargetType(pro.getClass().getName());
					promsgC.setTargetId(pro.getId());
					promsgC.setName("[项目]" + pro.getName());
					promsgC.setLog(hle);
					entityManager.persist(promsgC);
				}
			}

		}

		entityManager.persist(maic);

		ma.setUpdateAt(new Date());
		entityManager.merge(ma);

		flashQuarterReport(pre);

	}

	public static void main(String[] args) {
		int x = 2;
		System.out.println(x + 1 + "月是第"
				+ (MonthAndQuarterUtil.getQuarter(x).getKey() + 1) + "季度");
		x = 0;
		System.out.println(x + 1 + "季度最大月份是"
				+ (MonthAndQuarterUtil.getQuarterMaxMonth(x) + 1) + "月");
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
		;
		if (set != null) {
			String ql = "select x from ModelAreaQuarterItem x where x.modelArea.ownerId=? and x.batch.quarter.annual*100+x.batch.quarter.period>=? order by x.batch.quarter.annual asc,x.batch.quarter.period asc";
			List<ModelAreaQuarterItem> maqiList = __list(
					ModelAreaQuarterItem.class, ql, projectReport
							.getModelArea().getOwnerId(),
					projectReport.getAnnual() * 100 + set.getKey());
			if (maqiList != null) {
				for (ModelAreaQuarterItem maqi : maqiList) {
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

					for (ProjectReportItem proRI : projectReportItemList) {
						if (proRI.getProject().getRural() instanceof NewRuralEntity) {
							NewRuralEntity newRural = (NewRuralEntity) proRI
									.getProject().getRural();
							if (newRural.getAdminRural() != null) {
								maqi.getInvestment().sum(proRI.getInvestment());
								System.out.println("季报的资金加上了自然村为:"
										+ newRural.getNaturalVillage());
							} else {
								System.out.println(newRural.getName()
										+ "行政村为空null");
							}
						} else {

							System.out.println("非主体村的项目"
									+ proRI.getProject().getRural()
											.getNaturalVillage()
									+ proRI.getProject());
						}
					}
					entityManager.merge(maqi);
				}
			}
		}
	}

	@Override
	@Transactional
	public void changeBacth(Long id, String batch, IOperator user) {
		if (!BatchHelper.contains(batch)) {
			throw new RuntimeException("非法批次！");
		}
		ModelAreaEntity ma = __get(id);
		if (batch.equals(ma.getBatch())) {
			throw new RuntimeException("同一批次，不需要修改！");
		}
		String batch_old = ma.getBatch();
		ma.setBatch(batch);
		ma.setUpdateAt(new Date());
		entityManager.merge(ma);

		/**** 记录片区排序操作的记录 ***/
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(ma.getId());
		hlog.setTargetType(ModelAreaEntity.class.getName());
		hlog.setContent(ma.getName() + "从第" + batch_old + "批次改成第" + batch
				+ "批次");
		entityManager.persist(hlog);
	}

	private final String statistics_show = "select x.batch,x.reportAnnual,x.cityName,x.county,x.name,x.themeName";

	// 25~27-int,28-double

	// TODO 以下的需要添加行政村的排序
	@Override
	public List<Object[]> basicStatistics(String batch) {
		batch = StringHelper.get(batch, "一");
		String ql = statistics_show // 6-double,7~14-int
				+ ",x.ruralsArea,x.townNumber,x.adminVN,x.naturalVN,x.villagersGroup,x.popHous,x.farmerHous,x.sumTownPopu,x.sumFarmers"
				// 15~24-double
				+ ",x.ci3,x.ci4,x.ci5,x.ci6,x.ci7,x.vi3,x.vi4,x.vi5,x.vi6,x.vi7"
				+ " from ModelAreaEntity x where x.deleted=false and x.status>0"
				+ " and x.batch=? order by x.ordinalModel asc";
		return __list(Object[].class, ql, batch);
	}

	@Override
	public List<Object[]> totalStatistics(String batch) {
		batch = StringHelper.get(batch, "一");
		String ql = statistics_show // 6-double,7~14-int
				+ ",x.mainBuild,x.around,x.sumMAdmin,x.sumMRural,x.sumArAdmin,x.sumArRural,x.coverTown, x.mainAreaAcreage+x.aroundAreaAcreage"
				// 29~30-int
				+ ",x.mainSumHouse+x.aroundSumHouse,x.mainSumP+x.aroundSumP"
				+ " from ModelAreaEntity x where x.deleted=false and x.status>0"
				+ " and x.batch=? order by x.ordinalModel asc";
		return __list(Object[].class, ql, batch);
	}

	public List<Object[]> totalStatisticsNew(String batch) {
		batch = StringHelper.get(batch, "一");
		String ql = "select x.county,x.sumMAdmin,x.sumMRural,x.sumArAdmin,x.sumArRural,x.coverTown, x.mainAreaAcreage+x.aroundAreaAcreage"
				+ ",x.mainSumHouse+x.aroundSumHouse,x.mainSumP+x.aroundSumP"
				+ " from ModelAreaEntity as x where x.deleted=false and x.status>0"
				+ " and x.batch=? order by x.ordinalModel asc";
		return __list(Object[].class, ql, batch);
	}

	@Override
	public List<Object[]> buildStatistics(String batch) {
		batch = StringHelper.get(batch, "一");
		String ql = statistics_show // 6-double,7~14-int
				+ ",x.lineScale,x.startMark,x.planMark,x.postCount,x.greenRoad,x.viewPlatform,x.introCard,x.greenProject,x.areaRoad"
				+ " from ModelAreaEntity x where x.deleted=false and x.status>0"
				+ " and x.batch=? order by x.ordinalModel asc";
		return __list(Object[].class, ql, batch);

	}

	public List<Object[]> buildStatisticsNew(String batch) {
		batch = StringHelper.get(batch, "一");
		String ql = "select x.county,x.sumMAdmin,x.sumMRural,x.sumArAdmin,x.sumArRural,x.coverTown,x.areaAcreage,x.sumHouse,x.sumP"
				+ " from ModelAreaEntity x where x.deleted=false and x.status>0"
				+ " and x.batch=? order by x.ordinalModel asc";
		return __list(Object[].class, ql, batch);

	}

	@Override
	public List<AdministrationCoreRuralEntity> getAdminRural(Long id) {
		String ql = "select x from AdministrationCoreRuralEntity x where x.deleted=false and x.modelArea.id=? and x.modelArea.deleted=false";
		return __list(AdministrationCoreRuralEntity.class, ql, id);
	}

	@Override
	public List<AdministrationUncoreRuralEntity> getUCAdminRural(Long id) {
		String ql = "select x from AdministrationUncoreRuralEntity x where x.deleted=false and x.modelArea.id=? and x.modelArea.deleted=false";
		return __list(AdministrationUncoreRuralEntity.class, ql, id);
	}

	// 获取片区的季度报表
	@Override
	public List<ModelAreaQuarterItem> getAreaQuarterItems(Long id) {
		Date start = new Date();
		// "x.modelArea.ownerId={USER owner} and x.batch.quarter.startAt<={USER now} and x.disabled=false and x.modelArea.status<>-1")
		String ql = "select x from ModelAreaQuarterItem x where x.modelArea.id=? and x.batch.quarter.startAt<=? and x.disabled=false and x.modelArea.status<>-1 ";
		List<ModelAreaQuarterItem> aqItems = __list(ModelAreaQuarterItem.class,
				ql, id, start);
		return aqItems;
	}

	@Override
	public List<Integer> getResidentialEnvironmentsGroupByAnnual(Long id) {
		String ql = "select x.annual from ResidentialEnvironmentEntity x where x.rural.modelArea.id=? and x.rural.modelArea.deleted=false group by x.annual order by x.annual asc";
		return __list(Integer.class, ql, id);
	}

	@Override
	public List<ResidentialEnvironmentEntity> getResidentialEnvironmentsByAnnual(
			Long id, Integer annual) {
		String ql = "select x from ResidentialEnvironmentEntity x where x.rural.modelArea.id=? and x.rural.modelArea.deleted=false and x.annual=? order by x.ordinal asc";
		return __list(ResidentialEnvironmentEntity.class, ql, id, annual);
	}

	@Override
	public List<ModelAreaEntity> allModelArea() {
		String ql = "select x from ModelAreaEntity x where x.deleted=false order by batch, ordinalModel";
		return __list(ModelAreaEntity.class, ql);
	}

	/**
	 * TODO:统计速度太慢，待优化...
	 */
	@Override
	public ModelAreaStatisInfo statisticsInvest(ModelAreaEntity ma) {
		ModelAreaStatisInfo mInfo = new ModelAreaStatisInfo(ma);
		InvestmentInfo coreInvs = new InvestmentInfo();
		InvestmentInfo uncoreInvs = new InvestmentInfo();

		// String ql =
		// "select x from ProjectReportItem x where  x.project.modelArea.id=? and x.report.status=1";
		// List<ProjectReportItem> prs = __list(ProjectReportItem.class, ql,
		// ma.getId());
		// for(ProjectReportItem pr : prs){
		// if(pr.getProject().getRural() instanceof NewRuralEntity){
		// coreInvs.sum(pr.getInvestment());
		// }else if(pr.getProject().getRural() instanceof
		// PeripheralRuralEntity){
		// uncoreInvs.sum(pr.getInvestment());
		// }
		// }

		String ql = "select x.id, x.project.rural,x.investment from ProjectReportItem x where  x.project.modelArea.id=? and x.report.status=1";
		List<Object[]> obs = __list(Object[].class, ql, ma.getId());
		for (Object[] o : obs) {
			BaseRuralEntity rural = (BaseRuralEntity) o[1];
			InvestmentInfo investment = (InvestmentInfo) o[2];
			if (rural instanceof NewRuralEntity) {
				coreInvs.sum(investment);
			} else if (rural instanceof PeripheralRuralEntity) {
				uncoreInvs.sum(investment);
			}
		}

		mInfo.setCoreInvest(coreInvs);
		mInfo.setUncoreInvest(uncoreInvs);

		int fp = 0, ucfp = 0;
		int stp = 0, ucstp = 0;

		ql = "select distinct x.project from ProjectReportItem x where x.report.modelArea.id=? and x.report.status=1 and x.project.deleted=false";
		List<ProjectEntity> pes = __list(ProjectEntity.class, ql, ma.getId());
		for (ProjectEntity p : pes) {
			if (p.getRural() instanceof NewRuralEntity) {
				if (p.getStatus() == 2) {
					fp++;
				}
				stp++;
			} else if (p.getRural() instanceof PeripheralRuralEntity) {
				if (p.getStatus() == 2) {
					ucfp++;
				}
				ucstp++;
			}
		}
		// /*以下统计有问题*/
		// ql =
		// "select distinct x.project.rural,x.project.status from ProjectReportItem x where x.report.modelArea.id=? and x.report.status=1 and x.project.deleted=false";
		// List<Object[]> obs2 = __list(Object[].class, ql, ma.getId());
		// for(Object[] o : obs2){
		// BaseRuralEntity rural = (BaseRuralEntity) o[0];
		// int status = (int) o[1];
		// if(rural instanceof NewRuralEntity){
		// if(status == 2){
		// fp++;
		// }
		// stp++;
		// }else if(rural instanceof PeripheralRuralEntity){
		// if(status == 2){
		// ucfp++;
		// }
		// ucstp++;
		// }
		// }
		mInfo.setCoreFp(fp);
		mInfo.setUncoreFp(ucfp);
		mInfo.setCoreStp(stp);
		mInfo.setUncoreStp(ucstp);
		return mInfo;
	}

	/**
	 * <pre>
	 * 对某片区从ProjectReportItem进行对project资金 spend LabourCount进行累加 modelarea资金进行累加
	 * </pre>
	 * 
	 * @author wsf
	 */
	public void coumtModelAreaInvAndProjectInv(long mid) {
		ModelAreaEntity ma = __get(mid);
		if (ma != null) {
			ma.getInvestment().clear();
			String ql = "select x from ProjectEntity x where x.modelArea.id=? and x.deleted=false";
			List<ProjectEntity> pes = __list(ProjectEntity.class, ql,
					ma.getId());
			if (pes != null && pes.size() > 0) {
				ql = "select x from ProjectReportItem x where x.report.status=1 and x.project.id=?";
				for (ProjectEntity pe : pes) {
					pe.getInvestment().clear();
					pe.setSpend(0);
					pe.setLabourCount(0);
					if (pe.getSourceInvestment() != null) {
						ma.getInvestment().sum(pe.getSourceInvestment());
						pe.getInvestment().sum(pe.getSourceInvestment());
					}
					for (ProjectReportItem pri : __list(
							ProjectReportItem.class, ql, pe.getId())) {
						pe.getInvestment().sum(pri.getInvestment());
						ma.getInvestment().sum(pri.getInvestment());
						pe.setSpend(DoubleHelper.add(pe.getSpend(),
								pri.getSpend()));
						pe.setLabourCount(pe.getLabourCount()
								+ pri.getLabourCount());
					}
					entityManager.merge(pe);

				}
				entityManager.merge(ma);
				System.out.println(ma.getInvestment().getCityFunds());
				System.out.println(ma.getInvestment().getCountyFunds());
				System.out.println(ma.getInvestment().getLocalFunds());
				System.out.println(ma.getInvestment().getMassFunds());
				System.out.println(ma.getInvestment().getOtherFunds());
				System.out.println(ma.getInvestment().getProvinceFunds());
				System.out.println(ma.getInvestment().getSocialFunds());
				System.out.println(ma.getInvestment().getSpecialFunds());
				System.out.println(ma.getInvestment().getStateFunds());
				System.out.println(ma.getInvestment().getTotalFunds());
			}

		}
	}

	@Override
	public List<ProjectImageShowEntity> recentAddProjectByModelArea(Long id,
			int year, int month, int monthInterval, int imageManageViewWidth,
			int imageManageViewHeight, String projectName) {
		if (projectName != null && projectName.length() > 25)
			return null;
		if (projectName == null || projectName.trim().equals("")) {
			return getProImageShowByTime(id, year, month, monthInterval,
					imageManageViewWidth, imageManageViewHeight);
		} else {
			return getProImageShowByProName(id, projectName,
					imageManageViewWidth, imageManageViewHeight);
		}
	}

	// 按项目名查
	private List<ProjectImageShowEntity> getProImageShowByProName(Long id,
			String projectName, int imageManageViewWidth,
			int imageManageViewHeight) {
		List<Object[]> res = __list(
				Object[].class,
				"select x.name ,x.rural.name ,x.createAt ,x.status, x.isUpdate ,x.directoryMedia.id,x.id from ProjectEntity as x where x.deleted=false and x.modelArea.deleted=false and x.modelArea.id=? and x.name like '%"
						+ projectName + "%'", id);
		return getProImageShow(res, imageManageViewWidth, imageManageViewHeight);

	}

	// 按时间查
	private List<ProjectImageShowEntity> getProImageShowByTime(Long id,
			int year, int month, int monthInterval, int imageManageViewWidth,
			int imageManageViewHeight) {

		int month2 = month + monthInterval;
		int year2 = year;
		if (month2 > 11) {
			year2 = year + month2 / 12;
			month2 = month2 % 12;
		}
		int lastMonthDay = ControllerUtil.getLastMonthDay(year2, month2);
		if (lastMonthDay == -1)
			return null;
		String betweenFirstTime = year + "-" + (month + 1) + "-" + 1 + " 0:0:0";
		String betweenLastTime = year2 + "-" + (month2 + 1) + "-"
				+ lastMonthDay + " 23:59:59";

		List<Object[]> res = __list(
				Object[].class,
				"select x.name ,x.rural.name ,x.createAt ,x.status, x.isUpdate ,x.directoryMedia.id,x.id from ProjectEntity as x where x.deleted=false and x.modelArea.deleted=false and x.modelArea.id=? and x.createAt >='"
						+ betweenFirstTime
						+ "' and x.createAt <='"
						+ betweenLastTime + "'", id);

		return getProImageShow(res, imageManageViewWidth, imageManageViewHeight);

	}

	private List<ProjectImageShowEntity> getProImageShow(List<Object[]> res,
			int imageManageViewWidth, int imageManageViewHeight) {
		List<ProjectImageShowEntity> list = new ArrayList<ProjectImageShowEntity>();
		for (Object[] var : res) {
			ProjectImageShowEntity pise = new ProjectImageShowEntity();
			if (var[0] == null)
				var[0] = "";
			if (var[1] == null)
				var[1] = "";
			if (var[2] == null) {
				var[2] = "";
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("y-M-d");
				var[2] = sdf.format(var[2]);
			}
			if (var[3] == null)
				var[3] = "";
			if (var[4] == null)
				var[4] = false;

			pise.setVar1(var[0].toString());
			pise.setVar2(var[1].toString());
			pise.setVar3(var[2].toString());
			pise.setVar4(var[3].toString());
			pise.setVar5(var[4].toString());
			pise.setVar6(var[5]);
			pise.setVar7(var[6].toString());
			list.add(pise);

		}
		// 放项目图片路径
		for (ProjectImageShowEntity var : list) {
			// x.deleted=false and x.extattr1=? and x.extattr7=?
			if (var.getVar6() != null) {
				List<FileEntity> fileList = __list(
						FileEntity.class,
						"select x from FileEntity as x left join x.directory y where x.deleted=false and x.extattr1=0 and y.id=?",
						var.getVar6());
				var.setVar6(null);
				if (fileList != null && fileList.size() > 0) {
					Object[] imageMsgList = new Object[fileList.size()];

					int index = 0;
					for (FileEntity file : fileList) {
						String[] imageMsg = new String[9];

						int i = 0;
						imageMsg[i++] = file.getId().toString();

						BufferedImage bufferedImage;

						String urlImg = this.getClass().getResource("/")
								.getPath();
						// 去掉 target/classes/

						String[] urlArray = urlImg.split("/");
						urlImg = "";
						for (int index2 = 0; index2 < urlArray.length - 2; index2++) {
							if(index2==0)
								continue;
							urlImg += urlArray[index2] + "/";
						}
						urlImg += "src/main/webapp" + file.getMapPath();
						System.out.println(urlImg);
						try {
							bufferedImage = ImageIO.read(new File(urlImg));
							int width = bufferedImage.getWidth();
							int height = bufferedImage.getHeight();
							if (width > imageManageViewWidth)
								width = imageManageViewWidth;
							if (height > imageManageViewHeight)
								height = imageManageViewHeight;
							imageMsg[i++] = width + "";
							imageMsg[i++] = height + "";
						} catch (IOException e) {
							imageMsg[i++] = -1 + "";
							imageMsg[i++] = -1 + "";
							continue;
						}

						imageMsg[i++] = file.getName();

						imageMsg[i++] = file.getExtattr7();
						String len = "";
						if (file.getLength() > 1024) {

							Long lenL = file.getLength() / 1024;
							len = lenL.toString() + "kb";
							if (lenL > 1024) {
								lenL = lenL / 1024;
								len = lenL.toString() + "mb";
							}
						} else {
							len = file.getLength() + "b";
						}
						imageMsg[i++] = len;

						SimpleDateFormat sdf = new SimpleDateFormat("y-M-d");

						imageMsg[i++] = sdf.format(file.getCreateAt());

						imageMsg[i++] = file.getCreatorName();

						imageMsg[i++] = file.getMapPath();

						imageMsgList[index++] = imageMsg;

					}
					var.setVar6(imageMsgList);
				}
			}

		}
		return list;
	}


	public void saveMedia(ModelAreaEntity ma, FileEntity _file) {
		ma.setModelAreaImg(_file);
		_file.setDirectory(new ArrayList<DirectoryEntity>());
		_file.getDirectory().add(ma.getDirectory());
		_file.setStatus(FileStatus.STATUS_FORBID);
		_file.setVersion(1);
		entityManager.persist(_file);
		__exec("update ModelAreaEntity as ma set ma.modelAreaImg.id="+_file.getId()+" where ma.id="+ma.getId());

	}


}
