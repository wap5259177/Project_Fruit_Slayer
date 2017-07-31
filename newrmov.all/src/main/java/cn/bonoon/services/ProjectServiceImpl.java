package cn.bonoon.services;

import static cn.bonoon.util.DoubleHelper.add;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.controllers.utils.ExcelModel;
import cn.bonoon.core.FileStatus;
import cn.bonoon.core.ProjectService;
import cn.bonoon.core.configs.ProjectConfig;
import cn.bonoon.core.project.MaProjectInfo;
import cn.bonoon.entities.BaseRuralEntity;
import cn.bonoon.entities.DirectoryEntity;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.InvestmentInfo;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.PeripheralRuralEntity;
import cn.bonoon.entities.ProjectEntity;
import cn.bonoon.entities.ProjectReportEntity;
import cn.bonoon.entities.ProjectReportItem;
import cn.bonoon.entities.ProjectStatusChangeEntity;
import cn.bonoon.entities.UnitEntity;
import cn.bonoon.entities.logs.HandleLogEntity;
import cn.bonoon.entities.logs.projectcomparer.ProjectComparer;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.util.DoubleHelper;
import cn.bonoon.util.MonthAndQuarterUtil;

@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl extends ConfigableProjectService<ProjectEntity>
		implements ProjectService {

	// @Override
	// public List<ProjectEntity> getProject(Long newRural) {
	// String ql = "from ProjectEntity x where x.newRural.id=?";
	// return __list(ProjectEntity.class, ql, newRural);
	// }
	@Override
	@Transactional
	public void clear(Long id, LogonUser user) throws Exception {

		ProjectEntity pe = __get(id);
		// 这个项目所有月份的累计
		Object[] sumItem = getSumItem(id);
		double totalfunds = 0.0;

		// 获取片区的实体
		ModelAreaEntity ma = pe.getModelArea();

		for (int i = 1; i < 9; i++) {
			if (sumItem[i] == null) {
				throw new Exception("该项目没有月报或者月报没有上报过");
			}
			totalfunds = add(totalfunds, sumItem[i]);
		}

		InvestmentInfo invest = new InvestmentInfo();
		invest.setTotalFunds(totalfunds);
		invest.setStateFunds((double) sumItem[1]);
		invest.setSpecialFunds((double) sumItem[2]);
		invest.setProvinceFunds((double) sumItem[3]);
		invest.setCityFunds((double) sumItem[4]);
		invest.setCountyFunds((double) sumItem[5]);
		invest.setSocialFunds((double) sumItem[6]);
		invest.setMassFunds((double) sumItem[7]);
		invest.setOtherFunds((double) sumItem[8]);
		pe.setTotalInvestment(invest);
		// pe.getTotalInvestment().clear(pe.getSourceInvestment());//这里清除初识资金，用总得减去初始，将初始的清0

		InvestmentInfo mavin = ma.getInvestment();
		// 片区的资金比较
		ProjectComparer proCom = new ProjectComparer();
		// 创建之前片区资金实体
		InvestmentInfo old_mavin = new InvestmentInfo();
		// 创建之后片区资金实体
		InvestmentInfo new_mavin = new InvestmentInfo();

		mavin.copy(old_mavin);
		proCom.getSource().setInfo(old_mavin);

		// 项目初始资金减了，片区的资金也要减去
		mavin.subtractTo(pe.getSourceInvestment());
		pe.getSourceInvestment().clear();
		mavin.copy(new_mavin);
		proCom.getTarget().setInfo(new_mavin);

		if (proCom.needAdjust() == false) {
			/** 记录项目管理删除初始累计资金的操作 ****/
			HandleLogEntity hlog = new HandleLogEntity();
			hlog.currentUser(user);
			hlog.setTargetId(pe.getId());
			hlog.setTargetType(ProjectEntity.class.getName());
			hlog.setContent(pe.getCode() + pe.getName() + "删除初始累计资金");
			entityManager.persist(hlog);

			proCom.setLog(hlog);
			proCom.setName("[片区]" + ma.getName());
			proCom.setTargetId(ma.getId());
			proCom.setTargetType(ModelAreaEntity.class.getName());
			entityManager.persist(proCom);
		}

		entityManager.merge(pe);
		entityManager.merge(ma);
	}

	@Override
	protected ProjectEntity __save(OperateEvent event, ProjectEntity entity) {
		boolean rst = __doPre(event, entity);
		entity = super.__save(event, entity);
		if (rst) {
			__doAft(entity);
		}
		entityManager.merge(entity);
		return entity;
	}

	@Override
	protected ProjectEntity __update(OperateEvent event, ProjectEntity entity) {
		// if(entity.getStatus()==4){//省那边驳回的修改项目的基本数据。(是不允许修改项目所属村的)
		// __checkProjectInfo(entity);
		// if (event.is("applicant-submit")) {
		// entity.setStatus(1);
		// }
		// return super.__update(event, entity);
		// }
		// if (__doPre(event, entity)) {
		// __doAft(entity);
		// }
		// return super.__update(event, entity);
		Boolean flag = entity.getIsUpdate();
		if (flag != null && flag == true) {// 省那边驳回的修改项目的基本数据。(是不允许修改项目所属村的)
			__checkProjectInfo(entity);
			return super.__update(event, entity);
		}
		if (__doPre(event, entity)) {
			__doAft(entity);
		}
		return super.__update(event, entity);
	}

	// TODO 删除未提交或被退回的项目
	@Override
	protected Object _operate(int operand, OperateEvent event,
			ProjectEntity entity) {
		if (operand == OPERAND_DELETE) {
			List<ProjectReportItem> list = __getProjectReportItem(entity
					.getId());
			// 判断是否有上报过月报
			if (list.size() == 0) {
				entity.setDeleted(true);
				// TODO 删除该项目未提交的项目item
				// 上面判断了该项目是否有上报过月报，size等于0说明没有上报过，则把所有该项目下的月报item全部删除
				__exec("delete from ProjectReportItem  where project.id=?",
						entity.getId());
				entityManager.merge(entity);

			} else {
				throw new RuntimeException("不能删除已上传过月报的项目");
			}
		}

		return super._operate(operand, event, entity);
	}

	private void __doAft(ProjectEntity entity) {
		if (null != entity.getTotalInvestment()
				&& entity.getTotalInvestment().getTotalFunds() > 0) {
			// 有录入初始资金的情况
			InvestmentInfo inv = entity.getTotalInvestment();
			InvestmentInfo ins = entity.getSourceInvestment();

			// 不统计非主体村的专项资金
			if (entity.getRural() instanceof PeripheralRuralEntity) {
				// entity.getTotalInvestment().setSpecialFunds(0D);
				inv.setSpecialFunds(0.0);
			}

			if (null == ins) {
				ins = new InvestmentInfo();
				entity.setSourceInvestment(ins);
			}
			inv.copy(ins);
		}
	}

	/**
	 * 返回值是表示项目是否已经提交
	 * 
	 * @param entity
	 * @param submit
	 * @return
	 */
	private boolean __doPre(OperateEvent event, ProjectEntity entity/*
																	 * , String
																	 * submit
																	 */) {

		BaseRuralEntity nr = entity.getRural();
		if (null == nr) {
			throw new RuntimeException("请选择主导的主体村！");
		}
		if (null == entity.getModelArea()) {
			entity.setModelArea(nr.getModelArea());
		}
		entity.setAreaName(entity.getModelArea().getName());
		entity.setTown(nr.getTown());
		entity.setVillageName(nr.getName());

		boolean hsm = entity.getStatus() == 1;// 已经提交过了
		if (event.is("applicant-submit")) {
			__checkOnSubmit(event, entity);
			__checkProjectInfo(entity);
			entity.setStatus(1);
			return !hsm;
		}
		entity.setSourceLabourCount(entity.getLabourCount());
		entity.setSourceSpend(entity.getSourceSpend());
		return false;
	}

	private void __checkProjectInfo(ProjectEntity entity) {
		String errmsg = "";
		int i = 1;
		if (StringHelper.isEmpty(entity.getName())) {
			errmsg += i++ + ". 项目名称不能为空！\n\r\t";
		}
		if (entity.getProjectType().equals("其他")
				&& "".equals(entity.getExactKind())) {
			errmsg += i++ + ". 请填写具体类别！\n\r\t";
		}
		if (entity.getStartAtY() < 2013 || entity.getStartAtM() < 0) {
			errmsg += i++ + ". 项目的开工时间不能为空！\n\r\t";
		}
		if (entity.getEndAtY() < 2013 || entity.getEndAtM() < 0) {
			errmsg += i++ + ". 项目的预计竣工时间不能为空！\n\r\t";
		}
		int sa = entity.getStartAtY() * 100 + entity.getStartAtM();
		int ea = entity.getEndAtY() * 100 + entity.getEndAtM();
		if (sa > ea) {
			errmsg += i++ + ". 项目的预计竣工时间不能在项目开工时间之前！\n\r\t";
		}
		if (entity.getLabourCount() < 0) {
			errmsg += i++ + ". 项目的群众投工数字不正确！\n\r\t";
		}
		if (entity.getSpend() < 0) {
			errmsg += i++ + ". 项目的群众折资额数字不正确！\n\r\t";
		}
		// if(null != entity.getInvestment() && (
		// entity.getInvestment().getProvinceFunds() <
		// entity.getInvestment().getSpecialFunds())){
		// errmsg += i++ + ". 计划投入的省级新农村示范片专项资金不能大于省级的投入资金！\n\r\t";
		// }
		// if(null != entity.getTotalInvestment() && (
		// entity.getTotalInvestment().getProvinceFunds() <
		// entity.getTotalInvestment().getSpecialFunds())){
		// errmsg += i++ + ". 累计完成的省级新农村示范片专项资金不能大于省级的投入资金！\n\r\t";
		// }
		if (i > 1) {
			throw new RuntimeException(errmsg);
		}
	}

	private void __checkOnSubmit(OperateEvent event, ProjectEntity entity) {
		ProjectConfig config = __config();

		if (!config.checkOnSubmit(event))
			return;

		// 是否有例外的情况
		if (null != entity.getDirectoryMedia()) {
			Long did = entity.getDirectoryMedia().getId();
			String ql = "select count(x) from FileEntity x left join x.directory y where x.deleted=false and x.extattr7='建设前' and y.id=?";
			if (__exsit(ql, did)) {
				return;
			}
		}// else 异常

		throw new RuntimeException("请上传项目[建设前]图片");
	}

	// private DirectoryEntity __create(IOperator event, int extattr,
	// AbstractEntity entity, DirectoryEntity parent){
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
	// private DirectoryEntity __create(IOperator event, String name, int
	// extattr, AbstractEntity entity, DirectoryEntity parent){
	// DirectoryEntity dir = new DirectoryEntity();
	// dir.setParent(parent);
	// dir.setExtattr2(extattr);
	// dir.setExtattr3(entity.getId());
	// dir.setExtattr5(entity.getName());
	// dir.setName(name);
	// entityManager.persist(_init_entity(event, dir));
	// return dir;
	// }

	// private AbstractEntity _init_entity(IOperator event, AbstractEntity
	// entity) {
	// entity.setCreateAt(new Date());
	// entity.setCreatorId(event.getId());
	// entity.setCreatorName(event.getUsername());
	// entity.setOwnerId(event.getOwnerId());
	// return entity;
	// }
	//
	// void _init_directory(OperateEvent event, ProjectEntity entity) {
	// DirectoryEntity dir_project;
	// if(null == entity.getDirectory()){
	// // 查找村目录
	// DirectoryEntity dir_area = entity.getModelArea().getDirectoryProj();
	// // 创建项目目录，父节点为村目录
	// dir_project = __create(event, DirectoryExtattr.PROJECT, entity,
	// dir_area);
	// entity.setDirectory(dir_project);
	// // 创建项目下的文档、图片目录，父节点为项目目录
	//
	// }else{
	// dir_project = entity.getDirectory();
	// dir_project.setName(entity.getName());
	// entityManager.merge(dir_project);
	// }
	// if(null == entity.getDirectoryDoc()){
	// DirectoryEntity dir_project_doc = __create(event, "项目文档",
	// DirectoryExtattr.PROJECT_DOC, entity, dir_project);//new
	// DirectoryEntity();
	// entity.setDirectoryDoc(dir_project_doc);
	// }
	// if(null == entity.getDirectoryImg()){
	// DirectoryEntity dir_project_img = __create(event, "项目图片",
	// DirectoryExtattr.PROJECT_IMG, entity, dir_project);//new
	// DirectoryEntity();
	// // 更新目录到实体
	// entity.setDirectoryImg(dir_project_img);
	// //图片目录下面有三个目录，分别为：项目前、项目中、项目后
	//
	// __create(event, "建设前", DirectoryExtattr.PROJECT_IMG_0, entity,
	// dir_project_img);
	// __create(event, "建设中", DirectoryExtattr.PROJECT_IMG_1, entity,
	// dir_project_img);
	// __create(event, "建设后", DirectoryExtattr.PROJECT_IMG_2, entity,
	// dir_project_img);
	// }
	// entityManager.merge(entity);
	// }

	@Override
	public List<Object[]> newRurals(IOperator opt) {
		String ql = "select distinct x.id,x.name,x.town,x.naturalVillage from NewRuralEntity x where x.ownerId=? and x.deleted=false and x.name is not null and x.modelArea.status>=0";
		return __list(Object[].class, ql, opt.getOwnerId());
	}

	@Override
	public List<Object[]> peripheralRurals(IOperator opt) {
		String ql = "select distinct x.id,x.name,x.town,x.naturalVillage from PeripheralRuralEntity x where x.ownerId=? and x.deleted=false and x.name is not null and x.modelArea.status>=0 order by x.town";
		return __list(Object[].class, ql, opt.getOwnerId());
	}

	@Override
	public List<Object[]> newRurals(Long id) {
		String ql = "select distinct x.id,x.name,x.town,x.naturalVillage from NewRuralEntity x where x.modelArea.id=? and x.deleted=false and x.name is not null and x.modelArea.status>=0";
		return __list(Object[].class, ql, id);
	}

	@Override
	public List<Object[]> peripheralRurals(Long id) {
		String ql = "select distinct x.id,x.name,x.town,x.naturalVillage from PeripheralRuralEntity x where x.modelArea.id=? and x.deleted=false and x.name is not null and x.modelArea.status>=0 order by x.town";
		return __list(Object[].class, ql, id);
	}

	// 删除未提交或被退回的项目

	// @Override
	// protected void onDelete(OperateEvent event, ProjectEntity entity) {
	//
	// String a = "ddd";
	// System.out.println(a);
	// super.onDelete(event, entity);
	// }
	// @Override
	// protected void __delete(OperateEvent event, ProjectEntity entity) {
	// // TODO Auto-generated method stub判断该项目所对应的月报是否已经提交，提交则不能删除项目，报出异常
	// List<ProjectReportItem> list = __getProjectReportItem(entity.getId());
	//
	// if (null != list) {
	// throw new RuntimeException("不能删除已上传过月报的项目");
	// } else {
	// entity.setDeleted(true);
	// // TODO 删除该项目未提交的项目item
	// __exec("delete from ProjectReportItem where x.project.id=? and x.report.status=0 and x.project.status=0",entity.getId());
	// entityManager.merge(entity);
	// }
	//
	// super.__delete(event, entity);
	// }

	private List<ProjectReportItem> __getProjectReportItem(Long id) {
		String ql = "from ProjectReportItem x where x.project.id=? and x.report.status=1 and x.project.status=0";
		return __list(ProjectReportItem.class, ql, id);
	}

	// @Override
	// @Transactional
	// public DirectoryEntity dir_buildingOrCreate(IOperator opt, Long id, int
	// intValue) {
	// ProjectEntity pe = __get(id);
	// DirectoryEntity de = pe.getDirectoryImg();
	// if(null == de){
	// DirectoryEntity parent = pe.getDirectory();
	// if(null == parent){
	// parent = __create(opt, DirectoryExtattr.PROJECT, pe,
	// pe.getModelArea().getDirectory());
	// pe.setDirectory(parent);
	// }
	// de = __create(opt, "项目图片", DirectoryExtattr.PROJECT_IMG, pe,
	// parent);//new DirectoryEntity();
	// // 更新目录到实体
	// pe.setDirectoryImg(de);
	// //图片目录下面有三个目录，分别为：项目前、项目中、项目后
	//
	// DirectoryEntity d0 = __create(opt, "建设前", DirectoryExtattr.PROJECT_IMG_0,
	// pe, de);
	// DirectoryEntity d1 = __create(opt, "建设中", DirectoryExtattr.PROJECT_IMG_1,
	// pe, de);
	// DirectoryEntity d2 = __create(opt, "建设后", DirectoryExtattr.PROJECT_IMG_2,
	// pe, de);
	// if(intValue == DirectoryExtattr.PROJECT_IMG_0)
	// return d0;
	// if(intValue == DirectoryExtattr.PROJECT_IMG_1)
	// return d1;
	// return d2;
	// }
	// for(DirectoryEntity it : de.getChildren()){
	// if(it.getExtattr2() == intValue){
	// return it;
	// }
	// }
	// return null;
	// }
	//
	// @Override
	// public DirectoryEntity dir_building(Long id, int extattr) {
	// ProjectEntity pe = __get(id);
	// DirectoryEntity de = pe.getDirectoryImg();
	// if(null == de){
	// return null;
	// }
	// for(DirectoryEntity it : de.getChildren()){
	// if(it.getExtattr2() == extattr){
	// return it;
	// }
	// }
	// return null;
	// }

	@Override
	public List<Object[]> getStatus(Long owner, int year) {
		UnitEntity unit = entityManager.find(UnitEntity.class, owner);
		String ql = "select x.modelArea.name, x.period, count(x), sum(x.status) from ProjectReportEntity x where x.modelArea.cityId=? and x.annual=? group by x.modelArea.name, x.period";
		return __list(Object[].class, ql, unit.getPlace().getId(), year);
	}

	@Override
	public Collection<Object[]> statistics(String batch) {
		batch = BatchHelper.get(batch);
		String ql = "select x from ProjectEntity x where x.modelArea.deleted=false and x.modelArea.batch=? and x.deleted=false and x.status>0";
		List<ProjectEntity> items = __list(ProjectEntity.class, ql, batch);
		return __statistics(items);
	}

	private Collection<Object[]> __statistics(List<ProjectEntity> items) {
		Map<Long, Object[]> sts = new HashMap<>();
		for (ProjectEntity pe : items) {
			Object[] its = sts.get(pe.getModelArea().getId());
			if (null == its) {
				ModelAreaEntity ma = pe.getModelArea();
				its = new Object[41];
				sts.put(pe.getModelArea().getId(), its);
				// 0~5
				its[0] = ma.getBatch();
				its[1] = ma.getReportAnnual();
				its[2] = ma.getCityName();
				its[3] = ma.getCounty();
				its[4] = ma.getName();
				its[5] = ma.getThemeName();
				its[6] = 1;

				for (int i = 7; i < 21; i++) {
					its[i] = 0;
				}
				for (int i = 21; i < 39; i++) {
					its[i] = 0D;
				}
				its[39] = 0;
				its[40] = 0D;
			} else {
				its[6] = (Integer) its[6] + 1;
			}
			if (2 == pe.getStatus()) {
				its[7] = (Integer) its[7] + 1;
			} else {
				its[8] = (Integer) its[8] + 1;
			}
			if ("连线建设工程".equals(pe.getProProperty())) {
				its[9] = (Integer) its[9] + 1;
			} else if ("主体村工程".equals(pe.getProProperty())) {
				its[10] = (Integer) its[10] + 1;
			} else if ("非主体村工程".equals(pe.getProProperty())) {
				its[11] = (Integer) its[11] + 1;
			}
			String projectType = pe.getProjectType();
			__str(its, 12, projectType, "规划设计");
			__str(its, 13, projectType, "村庄环境整治（垃圾、污水处理等）");
			__str(its, 14, projectType, "村居外立面整治");
			__str(its, 15, projectType, "旧村旧房改造");
			__str(its, 16, projectType, "文化传承保护");
			__str(its, 17, projectType, "美化绿化建设");
			__str(its, 18, projectType, "基础设施建设");
			__str(its, 19, projectType, "连线工程项目");
			__str(its, 20, projectType, "其它");

			__investment(its, 21, pe.getInvestment());
			__investment(its, 30, pe.getTotalInvestment());

			its[39] = (Integer) its[39] + pe.getLabourCount();
			its[40] = add(pe.getSpend(), its[40]);
		}
		return sts.values();
	}

	private void __investment(Object[] its, int start, InvestmentInfo inv) {
		if (null != inv) {
			its[start] = add(inv.getTotalFunds(), its[start++]);// 21 // 30
			its[start] = add(inv.getStateFunds(), its[start++]);
			its[start] = add(inv.getSpecialFunds(), its[start++]);
			its[start] = add(inv.getProvinceFunds(), its[start++]);
			its[start] = add(inv.getCityFunds(), its[start++]);
			its[start] = add(inv.getCountyFunds(), its[start++]);// 29
			its[start] = add(inv.getSocialFunds(), its[start++]);// 25
			its[start] = add(inv.getMassFunds(), its[start++]);// 26
			its[start] = add(inv.getOtherFunds(), its[start++]);
			// 38

			// its[start] = (double)its[start++] + inv.getLocalFunds();
		}
	}

	private void __str(Object[] its, int i, String s, String v) {
		if (s.contains(v)) {
			its[i] = (Integer) its[i] + 1;
		}
	}

	@Override
	public Collection<Object[]> statisticsLocal(IOperator opt, String batch) {
		String ql = "select x from ProjectEntity x where x.modelArea.deleted=false and x.modelArea.status>0 and x.modelArea.ownerId=? and x.deleted=false and x.status>0 ";
		List<ProjectEntity> items = __list(ProjectEntity.class, ql,
				opt.getOwnerId());
		return __statistics(items);
	}

	@Override
	public Collection<Object[]> statisticsCity(IOperator opt, String batch) {
		String ql = "select x.place.id from UnitEntity x where x.id=?";
		Long pid = __first(Long.class, ql, opt.getOwnerId()); // and
		// x.modelArea.status>0
		ql = "select x from ProjectEntity x where x.modelArea.deleted=false and x.modelArea.status>0 and x.modelArea.cityId=? and x.deleted=false and x.status>0";
		List<ProjectEntity> items = __list(ProjectEntity.class, ql, pid);
		return __statistics(items);
	}

	@Override
	public List<FileEntity> medias(Long id, int code, String buildType) {
		String ql = "select x.directoryMedia.id from ProjectEntity x where x.id=?";
		Long did = __first(Long.class, ql, id);
		if (null != did) {
			ql = "select x from FileEntity x left join x.directory y where x.deleted=false and x.extattr1=? and x.extattr7=? and y.id=?";
			return __list(FileEntity.class, ql, code, buildType, did);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public void saveMedia(ProjectEntity pe, FileEntity _file) {
		DirectoryEntity dm = pe.getDirectoryMedia();
		if (null == dm) {
			dm = new DirectoryEntity();
			dm.setCreateAt(_file.getCreateAt());
			dm.setCreatorId(_file.getCreatorId());
			dm.setCreatorName(_file.getCreatorName());
			dm.setOwnerId(_file.getOwnerId());
			dm.setParent(pe.getDirectory());
			dm.setShare(FileStatus.SHARE_PRIVATE);
			dm.setStatus(FileStatus.STATUS_FORBID);
			dm.setName("多媒体文档");
			entityManager.persist(dm);
			pe.setDirectoryMedia(dm);
			entityManager.merge(pe);
		}
		_file.setDirectory(new ArrayList<DirectoryEntity>());
		_file.getDirectory().add(dm);
		_file.setStatus(FileStatus.STATUS_FORBID);
		_file.setVersion(1);
		entityManager.persist(_file);
	}

	@Override
	public void reportDetail(LogonUser user, Long id) {

	}

	/**
	 * 退回 退回应该要判断项目是否有填报月报，如果有不允许退回。
	 */
	@Override
	@Transactional
	public void back(Long id, LogonUser user) {
		String ql = "select count(*) from ProjectReportItem x where x.project.id=? and x.project.deleted=false";
		long pjCount = __first(Long.class, ql, id);
		if (pjCount > 0) {
			throw new RuntimeException("该项目填有月报了，不允许退回操作！");
		}
		ProjectEntity pro = __get(id);
		if (pro.getIsUpdate() == null)
			pro.setIsUpdate(new Boolean(true));
		pro.setIsUpdate(true);
		entityManager.merge(pro);

		// 记录项目管理退回的操作
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(pro.getId());
		hlog.setTargetType(ProjectEntity.class.getName());
		hlog.setContent(pro.getCode() + "退回");
		entityManager.persist(hlog);
	}

	@Override
	public List<ProjectReportItem> getProjectReport(Long id) {
		String sql = "select x from ProjectReportItem x where x.project.id=? and x.report.status=1";
		return __list(ProjectReportItem.class, sql, id);
	}

	@Override
	public Object[] getSumItem(Long id) {
		String ql = "select sum(x.investment.totalFunds), "
				+ "sum(x.investment.stateFunds),"
				+ "sum(x.investment.specialFunds),"
				+ "sum(x.investment.provinceFunds),"
				+ "sum(x.investment.cityFunds),"
				+ "sum(x.investment.countyFunds),"
				+ "sum(x.investment.socialFunds),"
				+ "sum(x.investment.massFunds),"
				+ "sum(x.investment.otherFunds),"
				+ "sum(x.labourCount),"
				+ "sum(x.spend)"
				+ "from ProjectReportItem x where x.project.id=? and x.report.status=1";
		return __first(Object[].class, ql, id);
	}

	@Override
	@Transactional
	public void restatistic(Long id, LogonUser user) {
		ProjectEntity pro = __get(id);
		InvestmentInfo sinv = pro.getSourceInvestment();
		InvestmentInfo tinv = pro.getTotalInvestment();

		// 创建需要比较项目资金比较的实体
		ProjectComparer prCom = new ProjectComparer();
		// 创建之前项目资金实体
		InvestmentInfo old_tinv = new InvestmentInfo();
		// 创建之处理过项目资金实体
		InvestmentInfo new_tinv = new InvestmentInfo();

		if (null == tinv) {
			tinv = new InvestmentInfo();
			tinv.copy(old_tinv);
			prCom.getSource().setInfo(old_tinv);
		} else {
			tinv.copy(old_tinv);
			prCom.getSource().setInfo(old_tinv);
			tinv.clear();
		}
		prCom.getSource().setLabourCount(pro.getLabourCount());
		prCom.getSource().setSpend(pro.getSpend());
		tinv.sum(sinv);

		String ql = "select x from ProjectReportItem x where x.project.id=? and x.report.status=1";
		int labourCount = 0;
		double spend = 0D;
		List<ProjectReportItem> items = __list(ProjectReportItem.class, ql, id);
		for (ProjectReportItem it : items) {
			tinv.sum(it.getInvestment());
			labourCount += it.getLabourCount();
			spend = DoubleHelper.add(spend, it.getSpend());
		}
		// 处理后的
		pro.setSumLabourCount(labourCount);
		pro.setSumSpend(spend);
		pro.setTotalInvestment(tinv);
		entityManager.merge(pro);

		tinv.copy(new_tinv);
		prCom.getTarget().setInfo(new_tinv);
		prCom.getTarget().setLabourCount(labourCount);
		prCom.getTarget().setSpend(spend);

		// 如果比较的结果不相同,则做记录
		if (prCom.needAdjust() == false) {
			// 记录项目同步资金的操作
			HandleLogEntity hlog = new HandleLogEntity();
			hlog.currentUser(user);
			hlog.setTargetId(pro.getId());
			hlog.setTargetType(ProjectEntity.class.getName());
			hlog.setContent(pro.getCode() + pro.getName() + "同步资金");
			entityManager.persist(hlog);

			prCom.setLog(hlog);
			prCom.setName("[项目]" + pro.getCode() + pro.getName());
			prCom.setTargetType(ProjectEntity.class.getName());
			prCom.setTargetId(pro.getId());
			entityManager.persist(prCom);
		}
	}

	@Override
	public List<FileEntity> getPic(Long id) {
		ProjectEntity entity = __get(id);
		DirectoryEntity dir = entity.getDirectoryMedia();
		if (dir != null) {
			Long did = dir.getId();
			String ql = "select x from FileEntity x left join x.directory y where x.deleted=false  and y.id=?";
			List<FileEntity> fis = __list(FileEntity.class, ql, did);
			return fis;
		}
		return null;
		// String ql =
		// "select count(x) from FileEntity x left join x.directory y where x.deleted=false and x.extattr7='建设前' and y.id=?";
	}

	// /**
	// * 驳回
	// */
	// @Override
	// @Transactional
	// public void backToUpdate(Long id) {
	// ProjectEntity pro = __get(id);
	// pro.setStatus(4);
	// entityManager.merge(pro);
	// }

	/**
	 * 点击修改基本信息：设置isUpdate为true
	 */
	@Override
	@Transactional
	public void backToUpdate(Long id, LogonUser user) {
		ProjectEntity pro = __get(id);
		pro.setIsUpdate(true);
		entityManager.merge(pro);

		/** 记录项目管理修改基本信息的操作 **/
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(pro.getId());
		hlog.setTargetType(ProjectEntity.class.getName());
		hlog.setContent(pro.getCode() + pro.getName() + "修改基本信息");
		entityManager.persist(hlog);
	}

	/**
	 * 修改项目所在村
	 */
	@Override
	@Transactional
	public void updatePjRural(Long id, Long ruralId, LogonUser user) {
		ProjectEntity pro = __get(id);
		BaseRuralEntity rural = entityManager.find(BaseRuralEntity.class,
				ruralId);
		pro.setRural(rural);
		entityManager.merge(pro);

		/** 记录项目管理修改项目所在村的操作 **/
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(pro.getId());
		hlog.setTargetType(ProjectEntity.class.getName());
		hlog.setContent(pro.getCode() + pro.getName() + "修改为" + rural.getTown()
				+ rural.getName() + rural.getNaturalVillage());
		entityManager.persist(hlog);

	}

	/**
	 * 修改项目状态
	 */
	@Override
	@Transactional
	public void updatePjStatus(Long id, Integer status, LogonUser user) {
		ProjectEntity pro = __get(id);

		ProjectStatusChangeEntity statusChange = new ProjectStatusChangeEntity();
		statusChange.setCreateAt(new Date());
		statusChange.setCreatorName(user.getDisplayName());
		statusChange.setProject(pro);
		statusChange.setPrestatus(pro.getStatus());

		int ss = pro.getStatus();
		if ((ss == 2 || ss == 3) && status == 1) {
			String ql = "select x from ProjectReportItem x where x.project.id=? and x.projectStatus in(1,2) and x.statusChange is  null";
			List<ProjectReportItem> items = __list(ProjectReportItem.class, ql,
					id);
			for (ProjectReportItem item : items) {
				item.setStatusChange(statusChange);
				entityManager.merge(item);
			}
		}
		if (status != null)
			pro.setStatus(status);
		entityManager.merge(pro);

		statusChange.setStatus(pro.getStatus());
		entityManager.persist(statusChange);

		/** 记录项目管理修改项目状态的操作 **/
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(pro.getId());
		hlog.setTargetType(ProjectEntity.class.getName());
		String statuss = "";
		if (status == 1) {
			statuss = "进行中";
		}
		if (status == 2) {
			statuss = "竣工";
		}
		if (status == 3) {
			statuss = "终止";
		}
		hlog.setContent(pro.getCode() + pro.getName() + statuss);
		entityManager.persist(hlog);
	}

	/**
	 * 拿到所有项目
	 */
	@Override
	public List<ProjectEntity> allProjects() {
		String ql = "select x from ProjectEntity x where x.status>=0 and x.deleted=false";
		return __list(ProjectEntity.class, ql);
	}

	/**
	 * 根据查询条件拿到项目
	 */
	@Override
	public List<MaProjectInfo> allProInfosBycondition(String batch,
			String cityName, String maName, int status) {

		List<MaProjectInfo> infos = new ArrayList<>();
		String conbatch = " and 1=1";
		String concityName = " and 1=1";
		String conmaName = " and 1=1";
		String constatus = " and 1=1";

		if (!"-1".equals(batch))
			conbatch = " and x.modelArea.batch='" + batch + "'";
		if (status != -1)
			constatus = " and x.status=" + status;
		if (!"".equals(cityName))
			concityName = " and x.modelArea.cityName like '%" + cityName + "%'";
		if (!"".equals(maName))
			conmaName = " and x.modelArea.name like '%" + maName + "%'";

		String ql = "select x from ProjectEntity x where x.deleted=false"
				+ conbatch + constatus + concityName + conmaName;
		List<ProjectEntity> pros = __list(ProjectEntity.class, ql);
		// List<ProjectEntity> pros = entityManager.createQuery(ql,
		// ProjectEntity.class).getResultList();
		for (ProjectEntity p : pros) {
			infos.add(new MaProjectInfo(p));
		}
		return infos;
	}

	/**
	 * 导出示范片项目一览表
	 */
	@Override
	public void exportMaPros(HttpServletRequest request,
			HttpServletResponse response, String batch, String cityName,
			String maName, int status) {

		List<MaProjectInfo> infos = allProInfosBycondition(batch, cityName,
				maName, status);

		ExcelModel em;
		String filePath = request.getSession().getServletContext()
				.getRealPath("/xls-templates/mapros-list.xls");
		try {
			em = new ExcelModel(filePath, response, "示范片项目一览表");
			em.bindCell("title", "示范片项目一览表");
			em.bindColumns(3, infos.toArray());
			em.write();
		} catch (Throwable t) {
			t.printStackTrace();
			throw new RuntimeException(t);
		}
	}

	/**
	 * 完成项目修改
	 */
	@Override
	@Transactional
	public void pjUpdated(Long id) {

		ProjectEntity entity = __get(id);
		entity.setIsUpdate(false);
		entityManager.merge(entity);
	}

	public String deleteProject(Long id) {
		StringBuffer msg = new StringBuffer();
		ProjectEntity pro = get(id);

		ModelAreaEntity ma = pro.getModelArea();
		try {
			// 将被删除的项目的所有月报
			List<ProjectReportItem> list = __list(ProjectReportItem.class,
					" from ProjectReportItem x where x.project.id=?", id);
			// 存储有删除月报的月度报表的id
			Set<Long> proRep = new TreeSet<Long>(new Comparator<Long>() {
				@Override
				public int compare(Long o1, Long o2) {
					return (Integer.parseInt(o1.toString()) - Integer
							.parseInt(o2.toString()));
				}
			});
			if (list != null && list.size() > 0) {
				if (pro.getName() != null) {
					msg.append("被删项目: [" + pro.getName() + "] 该项目被删去的具体月报如下:");
				} else {
					msg.append("该项目被删去的具体项目月报如下:");
				}
				msg.append(pro.getName());
				for (ProjectReportItem pritem : list) {
					msg.append(" [" + pritem.getReport().getAnnual() + "年"
							+ (pritem.getReport().getPeriod() + 1) + "月] ");
					proRep.add(pritem.getReport().getId());
					System.out.println(" ["
							+ pritem.getReport().getAnnual()
							+ "年"
							+ (pritem.getReport().getPeriod() + 1)
							+ "月]月报删除项目前 总资金"
							+ pritem.getReport().getInvestment()
									.getTotalFunds() + ",参数"
							+ pritem.getReport().getSpend() + ","
							+ pritem.getReport().getLabourCount() + ","
							+ pritem.getReport().getTotalCount() + ""
							+ pritem.getReport().getFinishCount() + ","
							+ pritem.getReport().getStopCount() + ","
							+ pritem.getReport().getZeroCount());
				}
				msg.append("项目月报");
			} else {
				msg.append("被删项目: [" + pro.getName() + "] 该项目没有项目月报!");
			}

			pro.setDeleted(true);
			entityManager.flush();
			entityManager.merge(pro);
			// __exec("update ProjectEntity set deleted=true where id="+pro.getId());

			// 重新进行累计
			InvestmentInfo maivn = ma.getInvestment();

			if (null == maivn) {
				maivn = new InvestmentInfo();
				ma.setInvestment(maivn);
			} else {
				System.out.println("删除前片区总资金" + maivn.getTotalFunds());
				System.out.println("删除前项目总资金"
						+ pro.getTotalInvestment().getTotalFunds());
				maivn.clear();
			}

			String iql1 = "select x from ProjectReportItem x where x.report.status=1 and x.project.modelArea.id=? and x.project.deleted=false";
			List<ProjectReportItem> items = __list(ProjectReportItem.class,
					iql1, ma.getId());

			for (ProjectReportItem item : items) {
				maivn.sum(item.getInvestment());
			}
			ma.setUpdateAt(new Date());
			entityManager.flush();
			entityManager.merge(ma);

			for (Long preId : proRep) {
				List<ProjectReportItem> proItemlist = __list(
						ProjectReportItem.class,
						"select x from ProjectReportItem as x where x.report.id=? and x.project.deleted=false",
						preId);
				ProjectReportEntity pre = __first(ProjectReportEntity.class,
						"from ProjectReportEntity where id=?", preId);

				int fc = 0, sc = 0, zc = 0;
				int lc = 0;
				double sd = 0;
				if (pre.getInvestment() == null) {
					pre.setInvestment(new InvestmentInfo());
				} else {
					pre.getInvestment().clear();
				}
				for (ProjectReportItem pri : proItemlist) {
					InvestmentInfo ivn = pri.getInvestment();
					if (ivn == null)
						pri.setInvestment(new InvestmentInfo());
					ivn = pri.getInvestment();
					pre.getInvestment().sum(ivn);
					lc += pri.getLabourCount();
					sd = DoubleHelper.add(sd, pri.getSpend());

					int ps = pri.getProjectStatus();
					if (ps == 1) {
						fc++;
					} else if (ps == 2) {
						sc++;
					}

					if (pri.zeroReport()) {
						zc++;
					}
				}

				// 月报的一些统计信息
				pre.setTotalCount(pre.getReports().size());
				pre.setFinishCount(fc);
				pre.setStopCount(sc);
				pre.setZeroCount(zc);
				pre.setLabourCount(lc);
				pre.setSpend(sd);
				entityManager.flush();
				entityManager.merge(pre);
				// 修改了一个月度报表就该刷下季报
				flashQuarterReport(pre);

			}

			Set<ProjectEntity> proSet = new TreeSet<>(
					new Comparator<ProjectEntity>() {
						@Override
						public int compare(ProjectEntity o1, ProjectEntity o2) {
							return Integer.parseInt(o1.getId().toString())
									- Integer.parseInt(o2.getId().toString());
						}
					});

			String qls = "select x from ModelAreaQuarterItem x where x.modelArea.ownerId=?";
			List<ModelAreaQuarterItem> itemss = __list(
					ModelAreaQuarterItem.class, qls, pro.getModelArea()
							.getOwnerId());
			for (ModelAreaQuarterItem item : itemss) {
				int year = item.getBatch().getQuarter().getAnnual();
				int month = MonthAndQuarterUtil.getQuarterMaxMonth(item
						.getBatch().getQuarter().getPeriod());
				if (item.getInvestment() == null)
					item.setInvestment(new InvestmentInfo());
				item.getInvestment().clear();

				String qlss = "select x from ProjectReportItem x where x.report.annual*100+x.report.period<? and x.project.modelArea.id=? and x.report.status=1 and x.project.deleted=false";
				System.out.println(pro.getModelArea().getId());
				List<ProjectReportItem> projectReportItemList = __list(
						ProjectReportItem.class, qlss, year * 100 + month, pro
								.getModelArea().getId());
				System.out.println(projectReportItemList.size());
				for (ProjectReportItem pro_item : projectReportItemList) {
					if (pro_item.getProject().getRural() instanceof NewRuralEntity) {
						item.getInvestment().sum(pro_item.getInvestment());
						proSet.add(pro_item.getProject());
					}
				}
				int fp = 0;
				int stp = 0;
				for (ProjectEntity p : proSet) {
					item.getInvestment().sum(p.getSourceInvestment());
					if (p.getStatus() == 2) {
						fp++;
					}
					stp++;
				}
				item.setFinishProject(fp);
				item.setStartProject(stp);
				entityManager.flush();
				entityManager.merge(item);
			}

		} catch (Exception e) {
			return "error:" + e;
		}

		return msg.toString();
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
			System.out.println(set.getKey() + "季度第一个月是" + set.getValue()[0]);
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
					System.out.println("这个季度最大月份" + maxMonthByQuarter);
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
							if (!proRI.getProject().isDeleted()) {
								proSet.add(proRI.getProject());
								maqi.getInvestment().sum(proRI.getInvestment());
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
					entityManager.flush();
					System.out.println(maqi.getBatch().getQuarter().getAnnual()
							+ "年"
							+ (maqi.getBatch().getQuarter().getPeriod() + 1)
							+ "月" + "总资金"
							+ maqi.getInvestment().getTotalFunds() + "启动的项目："
							+ maqi.getStartProject());
				}
			}
		}
	}

	public List<ProjectReportItem> notSetReoprt(Long projectId, int whitchYear,
			int whitchMonth, Long modelAreaId) {
		// or x.project.name like %"+projectName+"%"

		String sql = "select x from ProjectReportItem x where x.project.id=? and x.report.period+x.report.annual*100=? and x.project.deleted='false'";
		return __list(ProjectReportItem.class, sql, projectId, whitchYear * 100
				+ whitchMonth);
	}

	public TreeMap<ProjectEntity, TreeSet<Integer[]>> getProjectAndProNoItemPerriod(
			String projectCode, String projectName, Long modelAreaId,
			TreeSet<Integer[]> perriodSet) {
		// or x.project.name like %"+projectName+"%"
		TreeMap<ProjectEntity, TreeSet<Integer[]>> tm = null;
		String sql = "";
		// TreeSet<ProjectEntity> set = new TreeSet<ProjectEntity>(
		// new Comparator<ProjectEntity>() {
		// @Override
		// public int compare(ProjectEntity o1, ProjectEntity o2) {
		// if (o2.getId() > o1.getId()) {
		// return 1;
		// } else if (o2.getId() < o1.getId()) {
		// return -1;
		// }
		// return 0;
		//
		// }
		// });

		if (projectName != null && !projectName.trim().equals("")) {
			sql = "select x from ProjectEntity as x where x.modelArea.id=? and x.name like '%"
					+ projectName + "%' and x.deleted='false'";
			List<ProjectEntity> peList = __list(ProjectEntity.class, sql,
					modelAreaId);
			tm = getProAndProAllPerriod(peList, perriodSet);
		}
		if (projectCode != null && !projectCode.trim().equals("")) {
			sql = "select x from ProjectEntity as x where x.modelArea.id=? and x.code=? and x.deleted='false'";
			if (tm == null)
				tm = new TreeMap<ProjectEntity, TreeSet<Integer[]>>(
						new Comparator<ProjectEntity>() {
							@Override
							public int compare(ProjectEntity o1,
									ProjectEntity o2) {
								if (o2.getId() > o1.getId()) {
									return 1;
								} else if (o2.getId() < o1.getId()) {
									return -1;
								}
								return 0;
							}
						});
			tm.putAll(getProAndProAllPerriod(
					__list(ProjectEntity.class, sql, modelAreaId, projectCode),
					perriodSet));
		}
		if (projectCode == null
				&& projectName == null
				&& modelAreaId != null
				|| (projectCode.trim().equals("")
						&& projectName.trim().equals("") && modelAreaId != null)) {
			sql = "select x from ProjectEntity as x where x.createAt>=? and x.modelArea.id=? and x.status=1 and x.deleted='false'";

			Calendar cd = Calendar.getInstance();
			cd.set(cd.get(Calendar.YEAR) - 1, cd.get(Calendar.MONTH),
					cd.get(Calendar.DATE));
			System.out.println(cd.getTime());
			// 拿到最近一年的项目
			List<ProjectEntity> list = __list(ProjectEntity.class, sql,
					cd.getTime(), modelAreaId);
			tm = getProAndProAllPerriod(list, perriodSet);
		}
		return tm;
	}

	/**
	 * 拿到项目以及项目属于未提交或者提交了退回等待提交的月度报表的未有的月度
	 * 
	 * @param list
	 * @param perriodSet
	 * @return
	 */
	private TreeMap<ProjectEntity, TreeSet<Integer[]>> getProAndProAllPerriod(
			List<ProjectEntity> list, TreeSet<Integer[]> perriodSet) {

		TreeMap<ProjectEntity, TreeSet<Integer[]>> tm = new TreeMap<ProjectEntity, TreeSet<Integer[]>>(
				new Comparator<ProjectEntity>() {
					@Override
					public int compare(ProjectEntity o1, ProjectEntity o2) {
						if (o2.getId() > o1.getId()) {
							return 1;
						} else if (o2.getId() < o1.getId()) {
							return -1;
						}
						return 0;
					}
				});
		String sql = "select x from ProjectReportItem as x where x.project.id=? and x.project.deleted='false'";
		for (ProjectEntity pe : list) {
			// 拿每个项目的所有月报
			List<ProjectReportItem> itemList = __list(ProjectReportItem.class,
					sql, pe.getId());

			// 存储某个项目填写的月报的所有的月度
			TreeSet<Integer[]> proAllReportPerriod = new TreeSet<Integer[]>(
					new Comparator<Integer[]>() {
						@Override
						public int compare(Integer[] o1, Integer[] o2) {
							return (o2[0] * 100 + o2[1])
									- (o1[0] * 100 + o1[1]);
						}
					});

			// 存储项目没有的月度
			TreeSet<Integer[]> perriodNoHave = new TreeSet<Integer[]>(
					new Comparator<Integer[]>() {
						@Override
						public int compare(Integer[] o1, Integer[] o2) {
							return (o2[0] * 100 + o2[1])
									- (o1[0] * 100 + o1[1]);
						}
					});
			// 复制一份perriodSet用来减掉项目存在的月度
			for (Integer[] psI : perriodSet) {
				perriodNoHave.add(new Integer[] { psI[0], psI[1] });
			}
			for (ProjectReportItem item : itemList) {
				ProjectReportEntity pre = item.getReport();
				// 设置权限只有月度报表是未提交的和提交后退回来的才可显示修改
				if (pre.getStatus() == 0
						|| (pre.getBack() != null && pre.getBack() == 1 && pre
								.getStatus() == 0))
					proAllReportPerriod.add(new Integer[] { pre.getAnnual(),
							pre.getPeriod() });
			}
			// 拿到某个项目还没有的月度
			perriodNoHave.removeAll(proAllReportPerriod);
			tm.put(pe, perriodNoHave);
		}
		return tm;
	}

	public String setProRepItemReportParam(Long mid, Long projectId,
			Integer reportPerriod) {
		try {
			ProjectEntity pe = __get(projectId);
			if (pe.getStatus() != 1) {
				return "修改失败,不是进行中的项目不允许指定到指定月度报表中!可向省级操作员请求修改项目状态";
			}
			ProjectReportEntity report = __first(
					ProjectReportEntity.class,
					"select x from ProjectReportEntity as x where x.annual*100+x.period=? and x.modelArea.id=?",
					reportPerriod, mid);
			// 先检查该项目在该月度有没有月报
			List<ProjectReportItem> pri = __list(
					ProjectReportItem.class,
					"select x from ProjectReportItem as x where x.report.annual*100+x.report.period=? and x.project.id=?",
					reportPerriod, projectId);
			if (pri.size() > 0) {
				return "[" + pe.getName() + "] 在" + reportPerriod / 100 + "年"
						+ (reportPerriod % 100 + 1) + "月份已有 " + pri.size()
						+ " 个月报";
			}
			ProjectReportItem pr = new ProjectReportItem();

			pr.setProject(pe);
			pr.setReport(report);
			entityManager.persist(pr);
			return pe.getName() + reportPerriod / 100 + "年"
					+ (reportPerriod % 100 + 1) + "月度月报添加成功!";
		} catch (Exception e) {
			return e.toString();
		}
	}

	@Override
	public List<int[]> getProjectTime(Long mid) {
		Date[] proT = new Date[2];
		proT[0] = __first(
				Date.class,
				"select min(x.createAt) from ProjectEntity as x where x.deleted=false and x.modelArea.deleted=false and x.modelArea.id=?",
				mid);
		proT[1] = __first(
				Date.class,
				"select max(x.createAt) from ProjectEntity as x where x.deleted=false and x.modelArea.deleted=false and x.modelArea.id=?",
				mid);

		if (proT[0]!=null) {
			List<int[]> time = new ArrayList<>();
			Calendar cal = Calendar.getInstance();
			cal.setTime(proT[0]);
			int time1[] = new int[2];
			time1[0] = cal.get(Calendar.YEAR);
			time1[1] = cal.get(Calendar.MONTH);
			time.add(time1);
			if (!proT[0].equals(proT[1]) && proT[1] != null) {
				int time2[] = new int[2];
				cal.setTime(proT[1]);
				time2[0] = cal.get(Calendar.YEAR);
				time2[1] = cal.get(Calendar.MONTH);
				if (time1[0] != time2[0] || time1[1] != time2[1])
					time.add(time2);
			}
			return time;
		}
		return null;
	}

	public static void main(String[] args) {
		Date da = new Date();
		// da.getTime();

		try {
			Thread.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date da2 = new Date();
		System.out.println("dagetTime:" + da.getTime() + da.equals(da2)
				+ "da2.getTime:" + da2.getTime());
		da2.setTime(da.getTime());
		System.out.println(da.equals(da2));
	}
}
