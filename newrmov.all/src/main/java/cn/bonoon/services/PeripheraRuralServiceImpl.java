package cn.bonoon.services;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.core.PeripheraRuralService;
import cn.bonoon.entities.AdministrationUncoreRuralEntity;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.PeripheralRuralEntity;
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
//public class PeripheraRuralServiceImpl extends AbstractService<PeripheralRuralEntity> implements PeripheraRuralService {
public class PeripheraRuralServiceImpl extends ConfigurableModelAreaService<PeripheralRuralEntity> implements PeripheraRuralService {

	
	@Override
	protected PeripheralRuralEntity __update(OperateEvent event, PeripheralRuralEntity entity) {
		//检查是否允许提交
		checkAndThrowError(entity.getModelArea(),event);
		boolean submit = "true".equalsIgnoreCase(event.getString("applicant-submit"));
		if (submit) {
		    String errmsg = "";
			int i = 1;
			if(StringHelper.isEmpty(entity.getCode())){
				errmsg += i++ + ". 请填写序号！\n\r\t";
			}
			if(entity.getPopulation() <= 0){
				errmsg += i++ + ". 人口数非合理数字！\n\r\t";
			}
			if(entity.getLabor() <= 0){
				errmsg += i++ + ". 劳动人口数非合理数字！\n\r\t";
			}
			if(entity.getPopulation() < entity.getLabor()){
				errmsg += i++ + ". 劳动人口不能超过村总人口！\n\r\t";
			}
			if(entity.getRuralArea() < entity.getArableLand()){
				errmsg += i++ + ". 总面积必须大于耕地面积！\n\r\t";
			}
			if(entity.getHouseHold() > entity.getPopulation()){
				errmsg += i++ + ". 户数必须小于人口数！\n\r\t";
			}
			if(entity.getHardBottom()==0 || entity.getHardBottom() > 100){
				errmsg += i++ + ". 道路和入户路硬底化率只能0-100之间！\n\r\t";
			}
			if(entity.getToiletPercent()==0 || entity.getToiletPercent() > 100){
				errmsg += i++ + ". 使用卫生厕所户数所占比例只能0-100之间！\n\r\t";
			}
//			if(entity.getPoorVillage().equals("是") && null==entity.getHelpUnit() || entity.getHelpUnit()==""){
//				errmsg += i++ + ". 请填写帮扶单位名称！\n\r\t";
//			}
			if("是".equals(entity.getFamousVillage()) && StringHelper.isEmpty(entity.getFamousBatch())){
				errmsg += i++ + ". 请选择广东名村批次！\n\r\t";
			}
			if("是".equals(entity.getPushVillage()) && StringHelper.isEmpty(entity.getPvAnnual())){
				errmsg += i++ + ". 请选择\"两不具备\"整村推进村年度！\n\r\t";
			}
			if("是".equals(entity.getTapWater()) && StringHelper.isEmpty(entity.getTapAnnual())){
				errmsg += i++ + ". 请选择通自来水年度！\n\r\t";
			}
			if("是".equals(entity.getHyFix()) && StringHelper.isEmpty(entity.getFixAnnual())){
				errmsg += i++ + ". 请选择已完成环境卫生整治年度！\n\r\t";
			}
			if("是".equals(entity.getCleanTeam()) && entity.getCleaners() <=0){
				errmsg += i++ + ". 请填写保洁队伍的人数！\n\r\t";
			}
			if("是".equals(entity.getBadWater()) && StringHelper.isEmpty(entity.getBadWaterAnn())){
				errmsg += i++ + ". 请选择建立污水处理设施年度！\n\r\t";
			}
			if("是".equals(entity.getPublicService()) && StringHelper.isEmpty(entity.getPsAnnual())){
				errmsg += i++ + ". 请选择建立统一的村级公共服务管理平台年度！\n\r\t";
			}
			if("是".equals(entity.getEniPlan()) && entity.getEniPlanDate()==-1){
				errmsg += i++ + ". 请选择是否编制村庄环境整治规划年度！\n\r\t";
			}
			if("是".equals(entity.getCouncil())){
				if(StringHelper.isEmpty(entity.getCouncilAnnual())){
					errmsg += i++ + ". 请选建立择村民理事会年度！\n\r\t";
				}
				if(entity.getCouncilMember() <= 0){
					errmsg += i++ + ". 请填写理事会成员数！\n\r\t";
				}
			}
			if (!errmsg.isEmpty()) {
				throw new RuntimeException(errmsg);
			}
			entity.setStatus(1);
		}else{
			entity.setStatus(0);
		}
		if(!"行政村".equals(entity.getType())){
			entity.setType("自然村");
		}
		return super.__update(event, entity);
	}
	
	@Override
	public List<RuralWorkGroupEntity> workGroups(Long id) {
		return __list(RuralWorkGroupEntity.class, "select x from RuralWorkGroupEntity x where x.peripheralRural.id=?", id);
	}

	@Override
	public List<RuralExpertGroupEntity> expertGroups(Long id) {
		return __list(RuralExpertGroupEntity.class, "select x from RuralExpertGroupEntity x where x.peripheralRural.id=?", id);
	}

	@Override
	public List<RuralUnitEntity> ruralUnits(Long id) {
		return __list(RuralUnitEntity.class, "select x from PeripheralRuralEntity x where x.peripheralRural.id=?", id);
	}
	
	@Override
	public List<PlaceEntity> getTowns(Long oid) {
//		Long tid = id;//所在的县的地区的ID
		//列出县的所有镇的信息
		String ql = "select x from PlaceEntity x where x.parent.id=?";
		return __list(PlaceEntity.class, ql, oid);
	}

//	@Override
//	@Transactional
//	public DirectoryEntity dir_buildingOrCreate(IOperator opt,Long id, int intValue ) {
//		PeripheralRuralEntity pe = __get(id);
//		DirectoryEntity de = pe.getDirectoryImg();
//		if(null == de){			
//			DirectoryEntity parent = pe.getDirectory();
//			if(null == parent){
//				parent = __create(opt, DirectoryExtattr.PROJECT, pe, pe.getModelArea().getDirectory());
//				pe.setDirectory(parent);
//			}
//			de = __create(opt, "项目图片", DirectoryExtattr.PROJECT_IMG, pe, parent);//new DirectoryEntity();
//		// 更新目录到实体
//			pe.setDirectoryImg(de);
//		//图片目录下面有三个目录，分别为：项目前、项目中、项目后
//		
//		DirectoryEntity d0 = __create(opt, "建设前", DirectoryExtattr.PROJECT_IMG_0, pe, de);
//		DirectoryEntity d1 = __create(opt, "建设中", DirectoryExtattr.PROJECT_IMG_1, pe, de);
//		DirectoryEntity d2 = __create(opt, "建设后", DirectoryExtattr.PROJECT_IMG_2, pe, de);
//		if(intValue == DirectoryExtattr.PROJECT_IMG_0)
//			return d0;
//		if(intValue == DirectoryExtattr.PROJECT_IMG_1)
//			return d1;
//		return d2;
//		}
//		for(DirectoryEntity it : de.getChildren()){
//			if(it.getExtattr2() == intValue){
//				return it;
//			}
//		}
//		return null;
//	}
//
//	
//
//	private DirectoryEntity __create(IOperator event, int extattr, AbstractEntity entity, DirectoryEntity parent){
//		Long id = entity.getId();
//		String name = entity.getName();
//		DirectoryEntity dir = new DirectoryEntity();
//		dir.setParent(parent);
//		dir.setExtattr2(extattr);
//		dir.setExtattr3(id);
//		dir.setExtattr5(name);
//		dir.setName(name);
//		entityManager.persist(_init_entity(event, dir));
//		return dir;
//	}
//	private DirectoryEntity __create(IOperator event, String name, int extattr, AbstractEntity entity, DirectoryEntity parent){
//		DirectoryEntity dir = new DirectoryEntity();
//		dir.setParent(parent);
//		dir.setExtattr2(extattr);
//		dir.setExtattr3(entity.getId());
//		dir.setExtattr5(entity.getName());
//		dir.setName(name);
//		entityManager.persist(_init_entity(event, dir));
//		return dir;
//	}


//
//	@Override
//	public DirectoryEntity dir_building(Long id, int extattr) {
//		PeripheralRuralEntity pe = __get(id);
//		DirectoryEntity de = pe.getDirectoryImg();
//		if(null == de){	
//			return null;
//		}
//		for(DirectoryEntity it : de.getChildren()){
//			if(it.getExtattr2() == extattr){
//				return it;
//			}
//		}
//		return null;
//	}
	
	
	@Override
	public List<Object[]> rurals(Long oid, Long mid) {
		String ql = "select distinct x.id,x.name,x.town,x.naturalVillage from NewRuralEntity x where x.ownerId=? and x.modelArea.id=? and x.deleted=false and x.name is not null";
		return __list(Object[].class, ql, oid, mid);
	}

	
	/**
	 * 添加非主体自然村
	 */
//	@Override
//	protected PeripheralRuralEntity __save(OperateEvent event, PeripheralRuralEntity entity) {
//		Long oid = event.getOwnerId();
//		String ql = "select x from ModelAreaEntity x where x.ownerId=? and x.deleted=false";
//		ModelAreaEntity ma = __first(ModelAreaEntity.class, ql,oid);
//		//提交的时候检查是否允许提交(当省级关闭填报功能的时候不允许再修改提交)
//		checkAndThrowError(ma,event);
//		entity.setModelArea(ma);
//		
//		entity.setType("自然村");
//		entity.setName(entity.getAdminRural().getName());
//		entity.setTown(entity.getAdminRural().getTown());
//		
//		
//		return super.__save(event, entity);
//	}

	
	//---------------------------以下可以忽略
//		@Override
//		protected PeripheralRuralEntity __save(OperateEvent event, PeripheralRuralEntity entity) {
//			if (null == entity.getModelArea()) {
//				throw new RuntimeException("请选择片区！");
//			}
//			super.__save(event, entity);
//			_init_entity(event, entity);
//			_init_directory(entity, event);
//			return entity;
//		}
//
//		private AbstractEntity _init_entity(IOperator event, AbstractEntity entity) {
//			entity.setCreateAt(new Date());
//			entity.setCreatorId(event.getId());
//			entity.setCreatorName(event.getUsername());
//			entity.setOwnerId(event.getOwnerId());
//			return entity;
//		}
//		
//		void _init_directory(PeripheralRuralEntity entity, OperateEvent event) {
//			// 查找区目录
//			ModelAreaEntity modelArea = entity.getModelArea();
//			DirectoryEntity dir_area = __first(DirectoryEntity.class,
//					"from DirectoryEntity where extattr2=? and extattr3=?",
//					DirectoryExtattr.AREA, modelArea.getId());
//			// 创建村目录，父节点为区
//			Long id = entity.getId();
//			String name = entity.getName();
//			DirectoryEntity dir_rural = new DirectoryEntity();
//			dir_rural.setName(name);
//			dir_rural.setParent(dir_area);
//			dir_rural.setExtattr2(DirectoryExtattr.RURAL);
//			dir_rural.setExtattr3(id);
//			dir_rural.setExtattr5(name);
//			_init_entity(event, dir_rural);
//			entityManager.persist(dir_rural);
//			// 创建村下的文档、图片目录，父节点为村目录
//			DirectoryEntity dir_rural_doc = new DirectoryEntity();
//			DirectoryEntity dir_rural_img = new DirectoryEntity();
//			DirectoryEntity dir_rural_video = new DirectoryEntity();
//			dir_rural_doc.setName("文档");
//			dir_rural_doc.setParent(dir_rural);
//			dir_rural_doc.setExtattr2(DirectoryExtattr.RURAL_DOC);
//			dir_rural_doc.setExtattr3(id);
//			dir_rural_doc.setExtattr5(name);
//			_init_entity(event, dir_rural_doc);
//			dir_rural_img.setName("图片");
//			dir_rural_img.setParent(dir_rural);
//			dir_rural_img.setExtattr2(DirectoryExtattr.RURAL_IMG);
//			dir_rural_img.setExtattr3(id);
//			dir_rural_img.setExtattr5(name);
//			
//			_init_entity(event, dir_rural_doc);
//			dir_rural_video.setName("视频");
//			dir_rural_video.setParent(dir_rural);
//			dir_rural_video.setExtattr2(DirectoryExtattr.RURAL_VIDEO);
//			dir_rural_video.setExtattr3(id);
//			dir_rural_video.setExtattr5(name);
//			
//			_init_entity(event, dir_rural_img);
//			_init_entity(event, dir_rural_video);
//			entityManager.persist(dir_rural_doc);
//			entityManager.persist(dir_rural_img);
//			entityManager.persist(dir_rural_video);
//			// 更新目录到实体
//			entity.setDirectory(dir_rural_doc);
//			// entity.setDirectoryImg(dir_rural_img);
//			entityManager.merge(entity);
//		}

		
	
//	private DirectoryEntity __create(OperateEvent event, String name,
//			int rural, PeripheralRuralEntity peripheralRural,
//			DirectoryEntity directory) {
//		DirectoryEntity dir = new DirectoryEntity();
//		dir.setParent(directory);
//		dir.setExtattr2(rural);
//		dir.setExtattr3(peripheralRural.getId());
//		dir.setExtattr5(peripheralRural.getName());
//		dir.setName(name);
//		entityManager.persist(_init_entity(event, dir));
//		return dir;
//	}

//	
//	@Override
//	public void createDirViedo(OperateEvent event,
//			PeripheralRuralEntity peripheralRural) {
//		DirectoryEntity directoryVideo = peripheralRural.getDirectoryVideo();
//		if (null == directoryVideo) {
//			directoryVideo = __create(event, "视频", DirectoryExtattr.RURAL_VIDEO, peripheralRural, peripheralRural.getDirectory());
//		}
//		
//	}

//	@Override
//	public void createDirImg(OperateEvent event, PeripheralRuralEntity peripheralRural) {
//		DirectoryEntity directoryImg = peripheralRural.getDirectoryImg();
//		if (null == directoryImg) {
//			directoryImg = __create(event, "图片", DirectoryExtattr.RURAL_IMG, peripheralRural, peripheralRural.getDirectory());
//		}
//	}

	
	@Override
	public Collection<Object[]> statistics(String batch) {
		batch  = BatchHelper.get(batch);                                                                     // and x.modelArea.status>0
		String ql = "select x from PeripheralRuralEntity x where x.deleted=false and x.modelArea.deleted=false  and x.modelArea.batch=? order by x.modelArea.id asc";
		List<PeripheralRuralEntity> items = __list(PeripheralRuralEntity.class, ql, batch);
		return __statistics(items);
	}

	private Collection<Object[]> __statistics(List<PeripheralRuralEntity> items){
		Map<Long, Object[]> sts = new HashMap<>();
		
		for(PeripheralRuralEntity nre : items){
			Object[] its = sts.get(nre.getModelArea().getId());
			if(null == its){
				ModelAreaEntity ma = nre.getModelArea();
				its = new Object[52];
				sts.put(nre.getModelArea().getId(), its);
				//0~5
				its[0] = ma.getBatch();
				its[1] = ma.getReportAnnual();
				its[2] = ma.getCityName();
				its[3] = ma.getCounty();
				its[4] = ma.getName();
				its[5] = ma.getThemeName();
				its[6] = nre.getNaturalVillage();
				its[7] = nre.getName();
				
				its[8] = nre.getRuralArea();
				its[9] = nre.getArableLand();
				its[10] = nre.getHouseHold();
				its[11] = nre.getPopulation();
				its[12] = nre.getLabor();
				its[13] = "是".equals(nre.getPoorVillage()) ? 1 : 0;
				its[14] = "是".equals(nre.getFamousVillage()) ? 1 : 0;
				its[15] = "是".equals(nre.getPushVillage()) ? 1 : 0;
				its[16] = nre.getAnnualIncome();
				its[17] = nre.getHardBottom();
				its[18] = "是".equals(nre.getTapWater()) ? 1 : 0;
				its[19] = nre.getWaterBase();
				its[20] = nre.getSmallWater();
				its[21] = nre.getFarmland();
				its[22] = "是".equals(nre.getHyFix()) ? 1 : 0;
				its[23] = "是".equals(nre.getVillageManage()) ? 1 : 0;
				if("是".equals(nre.getCleanTeam())){
					its[24] = 1 ;
					its[25] = nre.getCleaners() ;
				}else{
					its[24] = 0 ;
					its[25] = 0 ;
				}
				its[26] = nre.getToilet();
				its[27] = "是".equals(nre.getBadWater()) ? 1 : 0;
				its[28] = nre.getNoValue();
				its[29] = nre.getBuildAgain();
				
				its[30] = "是".equals(nre.getChangePlan()) ? 1 : 0;
				its[31] = "是".equals(nre.getDesignPic()) ? 1 : 0;
				its[32] = nre.getPlanCount();
				its[33] = nre.getFinishCount();
				its[34] = nre.getCulturalAct();
				
				its[35] = nre.getCulturalActArea();
				its[36] = nre.getRuralPark();
				its[37] = nre.getRuralParkArea();
				its[38] = nre.getRuralSquare();
				its[39] = nre.getSquareArea();//dou
				its[40] = nre.getHealthStation();
				
				its[41] = nre.getHealthStationArea();//dou
				its[42] = nre.getVillageToilet();
				its[43] = nre.getVillageToiletArea();//dou
				its[44] = "是".equals(nre.getPublicService()) ? 1 : 0;
				its[45] = "是".equals(nre.getEniPlan()) ? 1 : 0;
				its[46] = 0;
				its[47] = 0;
				its[48] = "是".equals(nre.getConstitu()) ? 1 : 0;
				its[49] = nre.getAnnualIncome_13();
				its[50] = nre.getAnnualIncome_14();
				its[51] = nre.getAnnualIncome_15();
				//20160203新增加
				
			}else{
				
				__dou(its, 8, nre.getRuralArea());
				__dou(its, 9, nre.getArableLand());
				__int(its, 10, nre.getHouseHold());
				__int(its, 11, nre.getPopulation());
				__int(its, 12, nre.getLabor());
				__boo(its, 13, nre.getPoorVillage());
				__boo(its, 14, nre.getFamousVillage());
				__boo(its, 15, nre.getPushVillage());
				__dou(its, 16, nre.getAnnualIncome());//这个有以下的AnnualIncome_13--15替代，从47开始
				__dou(its, 17, nre.getHardBottom());
				__boo(its, 18, nre.getTapWater());
				__int(its, 19, nre.getWaterBase());
				__int(its, 20, nre.getSmallWater());
				__dou(its, 21, nre.getFarmland());
				__boo(its, 22, nre.getHyFix());
				__boo(its, 23, nre.getVillageManage());
				if(__boo(its, 24, nre.getCleanTeam())){
					__int(its, 25, nre.getCleaners());
				}
				__int(its, 26, nre.getToilet());
				__boo(its, 27, nre.getBadWater());
				__int(its, 28, nre.getNoValue());
				__int(its, 29, nre.getBuildAgain());
				//28
				__boo(its, 30, nre.getChangePlan());
				__boo(its, 31, nre.getDesignPic());
				__int(its, 32, nre.getPlanCount());
				__int(its, 33, nre.getFinishCount());
				__int(its, 34, nre.getCulturalAct());
				
				__dou(its, 35, nre.getCulturalActArea());
				__int(its, 36, nre.getRuralPark());
				__dou(its, 37, nre.getRuralParkArea());
				__int(its, 38, nre.getRuralSquare());
				__dou(its, 39, nre.getSquareArea());
				__int(its, 40, nre.getHealthStation());

				__dou(its, 41, nre.getHealthStationArea());
				__int(its, 42, nre.getVillageToilet());
				__dou(its, 43, nre.getVillageToiletArea());
				__boo(its, 44, nre.getPublicService());
				__boo(its, 45, nre.getEniPlan());
				__boo(its, 48, nre.getConstitu());
				__dou(its, 49, nre.getAnnualIncome_13());
				__dou(its, 50, nre.getAnnualIncome_14());
				__dou(its, 51, nre.getAnnualIncome_15());
			}
			//建立村名理事会村个数
			if("是".equals(nre.getCouncil())){
				its[46] = (Integer)its[46] + 1;
				its[47] = (Integer)its[47] + nre.getCouncilMember();
			}
		}
		return sts.values();
	}
	private boolean __boo(Object[] its, int i, String s){
		if("是".equals(s)){
			its[i] = (Integer)its[i] + 1;
			return true;
		}
		return false;
	}
	private void __int(Object[] its, int i, int v){
		its[i] = (Integer)its[i] + v;
	}
	private void __dou(Object[] its, int i, double v){
		its[i] = DoubleHelper.add(v, its[i]);
	}
	@Override
	public Collection<Object[]> statisticsLocal(IOperator opt, String batch) {                                 //and x.modelArea.status>0
		String ql = "select x from PeripheralRuralEntity x where x.deleted=false and x.modelArea.deleted=false and x.modelArea.status>0 and x.modelArea.ownerId=? order by x.modelArea.id asc";
		List<PeripheralRuralEntity> items = __list(PeripheralRuralEntity.class, ql, opt.getOwnerId());
		return __statistics(items);
	}

	@Override
	public Collection<Object[]> statisticsCity(IOperator opt, String batch) {
		String ql = "select x.place.id from UnitEntity x where x.id=?";
		Long pid = __first(Long.class, ql, opt.getOwnerId());                                           //and x.modelArea.status>0 
		ql = "select x from PeripheralRuralEntity x where x.deleted=false and x.modelArea.deleted=false and x.modelArea.status>0 and x.modelArea.cityId=? order by x.modelArea.id asc";
		List<PeripheralRuralEntity> items = __list(PeripheralRuralEntity.class, ql, pid);
		return __statistics(items);
	}

	@Override
	public List<FileEntity> medias(Long id, int code, String buildType) {
		String ql = "select x.directoryMedia.id from PeripheralRuralEntity x where x.id=?";
		Long did = __first(Long.class, ql, id);
		if(null != did){
			ql = "select x from FileEntity x left join x.directory y where x.deleted=false and x.extattr1=? and x.extattr7=? and y.id=?";
			return __list(FileEntity.class, ql, code, buildType, did);
		}
		return Collections.emptyList();
	}

	@Override
	public List<FileEntity> mediasTime(Long id) {
		String ql = "select x.directoryMedia.id from PeripheralRuralEntity x where x.id=?";
		Long did = __first(Long.class, ql, id);
		if(null != did){
			ql="select x from FileEntity x left join x.directory y where x.deleted=false and y.id=?";
			return __list(FileEntity.class, ql, did);
		}
		return Collections.emptyList();
	}

	@Override
	public List<FileEntity> mediasTime1(Long id, int code) {
		String ql = "select x.directoryMedia.id from PeripheralRuralEntity x where x.id=?";
		Long did = __first(Long.class, ql, id);
		if(null != did){
			ql = "select x from FileEntity x left join x.directory y where x.deleted=false and x.extattr1=? and y.id=? and x.issueAt is not null order by x.issueAt desc";
			return __list(FileEntity.class, ql,code,did);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public void extract() {
		String ql = "select x from PeripheralRuralEntity x where x.adminRural is null and x.deleted=false";
		List<PeripheralRuralEntity> prs = __list(PeripheralRuralEntity.class, ql);
		if(prs.isEmpty()) return;
		ql = "select x from AdministrationUncoreRuralEntity x where x.deleted=false";
		List<AdministrationUncoreRuralEntity> aurs = __list(AdministrationUncoreRuralEntity.class, ql);
		Map<Long, AdministrationUncoreRuralEntity> caches = new HashMap<>();
		for(AdministrationUncoreRuralEntity aur : aurs){
			caches.put(aur.getPlace().getId(), aur);
		}
		Date now = new Date();
		for(PeripheralRuralEntity pr : prs){
			PlaceEntity pe = InternalRuralHelper.__findPlace(entityManager, pr);
			AdministrationUncoreRuralEntity aur = caches.get(pe.getId());
			if(null == aur){
				aur = new AdministrationUncoreRuralEntity();
				aur.setPlace(pe);
				aur.setName(pe.getName());
				aur.setModelArea(pr.getModelArea());
				aur.setOwnerId(pr.getOwnerId());
				aur.setCreateAt(now);
				aur.setCreatorId(pr.getCreatorId());
				aur.setCreatorName(pr.getCreatorName());
				aur.setTown(pr.getTown());
				
				entityManager.persist(aur);
				caches.put(pe.getId(), aur);
			}
			pr.setAdminRural(aur);
			entityManager.merge(pr);
		}
	}

	@Override
	public boolean check(Long id, IOperator opt) {
		String ql = "select x.modelArea.batch from PeripheralRuralEntity x where id=? and x.deleted=false";
		return __config().check(__first(String.class, ql, id), opt, false);
	}
}
