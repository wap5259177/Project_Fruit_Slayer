package cn.bonoon.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.controllers.utils.ExcelModel;
import cn.bonoon.core.FVCountyReportService;
import cn.bonoon.core.felicity.AreaReportInfo;
import cn.bonoon.core.felicity.FVTable3Info;
import cn.bonoon.core.felicity.ProjectInfo;
import cn.bonoon.core.felicity.RuralInfo;
import cn.bonoon.entities.felicityVillage.FVCoreRuralEntity;
import cn.bonoon.entities.felicityVillage.FVMACountyReportEntity;
import cn.bonoon.entities.felicityVillage.FVModelAreaReportEntity;
import cn.bonoon.entities.felicityVillage.FVPeripheralRuralEntity;
import cn.bonoon.entities.felicityVillage.FVProjectRuralEntity;
import cn.bonoon.entities.felicityVillage.FVReportEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.AbstractService;
import cn.bonoon.util.DoubleHelper;

@Service
@Transactional(readOnly = true)
public class FVCountyReportServiceImpl extends AbstractService<FVMACountyReportEntity> implements FVCountyReportService{

	@Override
	protected FVMACountyReportEntity __save(OperateEvent event, FVMACountyReportEntity entity) {
		return super.__save(event, entity);
	}

	

	/**
	 * 县下面所有的FVMACountyReportEntity
	 */
	@Override
	public List<FVMACountyReportEntity> allCountyReport( LogonUser user) {
		String pql = "select x.place.id from UnitEntity x where x.id=?";
		Long pid = __first(Long.class, pql,user.getOwnerId());
		String ql = "select x from FVMACountyReportEntity x where x.place.id=? and x.deleted=false";
		return __list(FVMACountyReportEntity.class, ql,pid);
		
	}
	/**
	 * 市下面的
	 */
	@Override
	public List<FVMACountyReportEntity> allCountyReportByCityPid(LogonUser user) {
		String ql = "select x from FVMACountyReportEntity x where x.cityId=? and x.deleted=false";
		return __list(FVMACountyReportEntity.class, ql,user.getOwnerId());
	}



	/**
	 * 点击保存片区,创建片区和片区字典
	 */
	@Override
	@Transactional
	public void saveModelArea(LogonUser user,
			List<FVModelAreaReportEntity> areas,Long id) {
		FVMACountyReportEntity cReport = __get(id);
		double _carea = 0.0;
		int _hous = 0;
		int _popus = 0;
		for(FVModelAreaReportEntity ma:areas){
			//创建片区report同时创建片区字典
//			FVModelAreaEntity dicma = new FVModelAreaEntity();//片区字典
//			dicma.setName(ma.getName());
//			dicma.setCreatorId(user.getId());
//			dicma.setCreatorName(user.getUsername());
//			dicma.setCreateAt(new Date());
//			entityManager.persist(dicma);
//			
//			
//			ma.setDicModelArea(dicma);
			ma.setCountyReport(cReport);
			ma.setCreatorId(user.getId());
			ma.setCreatorName(user.getUsername());
			ma.setCreateAt(new Date());
			entityManager.persist(ma);
			
			_carea = DoubleHelper.add(_carea, ma.getConstructionArea());
			_hous += ma.getHouseholds();
			_popus += ma.getPopulation();
		}
		cReport.setConstructionArea(DoubleHelper.add(cReport.getConstructionArea(), _carea));
		cReport.setHouseholds(cReport.getHouseholds() + _hous);
		cReport.setPopulation(cReport.getPopulation() + _popus);
		entityManager.merge(cReport);
	}



	/**
	 * 为了 实现表1 的层次关系,检查是否有辐射村,然后多生成出FVModelAreaReportEntity 记录,一行里面其他的字段设置为空,只要主体村名,辐射村名,和他们的类型
	 */
	@Override
	public List<AreaReportInfo> checkRuralToAddMa(Long id) {
		FVMACountyReportEntity cReport = __get(id);
		List<FVModelAreaReportEntity> mas = cReport.getModelAreas();
		
		List<AreaReportInfo> ainfos = new ArrayList<>();
		String  ql = "select x from FVCoreRuralEntity x where x.modelArea.id=? and x.deleted=false";
		for(FVModelAreaReportEntity ma:mas){
			List<FVCoreRuralEntity> crs = __list(FVCoreRuralEntity.class, ql,ma.getId());
			if(crs.size()==0){//1.没有主体村
				AreaReportInfo a = new AreaReportInfo(ma);
				//只set片区的字段
				ainfos.add(a);
			}else{
				for(FVCoreRuralEntity cr:crs){
					AreaReportInfo a_cr= new AreaReportInfo(ma,cr.getName(),cr.getType());
					//set片区的字段
					ainfos.add(a_cr);
					List<FVPeripheralRuralEntity> prs = cr.getFvprs();
					if(prs.size()>0){//3.有主体村也有辐射村
						for(FVPeripheralRuralEntity pr:prs){
							AreaReportInfo a_pr = new AreaReportInfo(ma,cr.getName(),pr.getName(),pr.getType());
							ainfos.add(a_pr);
						}
					}else{//有主体村,没有辐射村
						//set片区的字段和主体村名,
					}
				}
			}
		}
		return ainfos;
	}



	//修改片区
	@Override
	@Transactional
	public void UpdateModelArea(Long id,String modelAreaName,Double constructionArea,Integer  households, Integer population) {
		FVModelAreaReportEntity ma = entityManager.find(FVModelAreaReportEntity.class, id);
		FVMACountyReportEntity cReport = ma.getCountyReport();
		if(constructionArea==null)constructionArea=0.0;
		double _const = DoubleHelper.subtract(cReport.getConstructionArea(), ma.getConstructionArea());
		cReport.setConstructionArea( DoubleHelper.add(_const, constructionArea));
		ma.setConstructionArea(constructionArea);
		
		if(households==null)households=0;
		cReport.setHouseholds(cReport.getHouseholds() - ma.getHouseholds() + households);
		ma.setHouseholds(households);
		
		if(population==null)population=0;
		cReport.setPopulation(cReport.getPopulation() - ma.getPopulation() + population);
		ma.setPopulation(population);
		entityManager.merge(cReport);
		entityManager.merge(ma);
	}



	@Override
	@Transactional
	public void updateReportInfo(Long id,String reporter, String telephone,
			Date reportTime) {
		FVMACountyReportEntity cReport = __get(id);
		cReport.setReporter1(reporter);
		cReport.setTelephone1(telephone);
		cReport.setReportTime1(reportTime);
		
		cReport.setInit(true);//完成初始化工作,可填后续的表
		entityManager.merge(cReport);
	}
	
	@Override
	@Transactional
	public void updateReportInfo2(Long id,String reporter, String telephone,
			Date reportTime) {
		FVMACountyReportEntity cReport = __get(id);
		cReport.setReporter2(reporter);
		cReport.setTelephone2(telephone);
		cReport.setReportTime2(reportTime);
		entityManager.merge(cReport);
	}



	@Override
	public PlaceEntity getPlace(LogonUser user) {
		String ql = "select x.place.id from UnitEntity x where x.id=?";
		Long pid = __first(Long.class, ql, user.getOwnerId());
		PlaceEntity p = entityManager.find(PlaceEntity.class, pid);
		return p;
	}



	@Override
	@Transactional
	public void update(IOperator user, FVMACountyReportEntity cReport,
			List<FVModelAreaReportEntity> mas) {
		entityManager.merge(cReport);
		for(FVModelAreaReportEntity ma : mas){
			entityManager.merge(ma);
		}
	}



	@Override
	@Transactional
	public void copy(Long id, Long copyId,LogonUser user) {
		FVReportEntity report = entityManager.find(FVReportEntity.class, id);
		FVMACountyReportEntity cReport = __get(copyId);
		List<FVModelAreaReportEntity> mas = cReport.getModelAreas();
		
		String  crql = "select x from FVCoreRuralEntity x where x.modelArea.id=? and x.deleted=false";
		FVMACountyReportEntity ncReport = new FVMACountyReportEntity(cReport);
		ncReport.setCreateAt(new Date());
		ncReport.setCreatorId(user.getId());
		ncReport.setCreatorName(user.getUsername());
		
		
		ncReport.setReport(report);
		ncReport.setStatus(2);//0:未开始 1:完成  2:正在填报
		entityManager.persist(ncReport);
		for(FVModelAreaReportEntity ma:mas){
			//片区
			FVModelAreaReportEntity nma = new FVModelAreaReportEntity(ma);
			nma.setName(ma.getName());
			nma.setCountyReport(ncReport);
			List<FVCoreRuralEntity> crs = __list(FVCoreRuralEntity.class, crql, ma.getId());
			nma.setCreateAt(new Date());
			nma.setCreatorId(user.getId());
			nma.setCreatorName(user.getUsername());
			entityManager.persist(nma);
			for(FVCoreRuralEntity cr:crs){
				//主体村
				FVCoreRuralEntity ncr = new FVCoreRuralEntity(cr);
				ncr.setName(cr.getName());
				ncr.setType(cr.getType());
				ncr.setModelArea(nma);
				List<FVPeripheralRuralEntity> prs = cr.getFvprs();
				ncr.setCreateAt(new Date());
				ncr.setCreatorId(user.getId());
				ncr.setCreatorName(user.getUsername());
				entityManager.persist(ncr);
				for(FVPeripheralRuralEntity pr:prs){
					//辐射村
					FVPeripheralRuralEntity npr = new FVPeripheralRuralEntity();
					npr.setName(pr.getName());
					npr.setType(pr.getType());
					npr.setCoreRural(ncr);
					npr.setModelArea(nma);
					npr.setCreateAt(new Date());
					npr.setCreatorId(user.getId());
					npr.setCreatorName(user.getUsername());
					entityManager.persist(npr);
				}
			}
		}
		
		
	}
	

	@Override
	@Transactional
	public void submitReport(Long id) {
		FVMACountyReportEntity cReport = __get(id);
		
//		List<FVModelAreaReportEntity> mas = cReport.getModelAreas();
//		for(FVModelAreaReportEntity ma:mas){
//			int f1 = ma.getFeature().length();//676
//			int f2 = ma.getProblem().length();//156
//			int f3 = ma.getRemark().length();//52
//			if(f1>1){
//				throw new RuntimeException("字数超出限制!");
//			}
//		}
		cReport.setStatus(4);//提交以后这条县报变成 4:待审核
//		FVReportEntity report = cReport.getReport();
//		report.setStatus(2);//待审核状态
//		entityManager.merge(report);
		entityManager.merge(cReport);
	}



	@Override
	public void exportTable1(HttpServletRequest request,HttpServletResponse response,Long id) {
		String filePath = request.getSession().getServletContext().getRealPath("/xls-templates/table1.xls");
		SimpleDateFormat simple=new SimpleDateFormat("yyyy年MM月dd日");
//		SimpleDateFormat simple1=new SimpleDateFormat("MMddhhmmss");
		List<RuralInfo> rinfos = new ArrayList<>();
		FVMACountyReportEntity cReport = __get(id);
		String startTime = simple.format( cReport.getReport().getStartTime());
		String deadline = simple.format(cReport.getReport().getDeadline());
		String titleTile = "(统计起止时间"+startTime+"至"+deadline+")";
		List<FVModelAreaReportEntity> mas = cReport.getModelAreas();
		String  ql = "select x from FVCoreRuralEntity x where x.modelArea.id=? and x.deleted=false  order by x.id desc";
		rinfos.add(new RuralInfo(cReport));//小计行
		for(FVModelAreaReportEntity ma:mas){
			rinfos.add(new RuralInfo(ma));
			List<FVCoreRuralEntity> crs = __list(FVCoreRuralEntity.class, ql,ma.getId());
			for(FVCoreRuralEntity cr:crs){
				rinfos.add(new RuralInfo(cr));
				List<FVPeripheralRuralEntity> prs = cr.getFvprs();
				for(FVPeripheralRuralEntity pr:prs){
					if(!pr.isDeleted()){
						rinfos.add(new RuralInfo(pr));
					}
				}
			}
		}
		ExcelModel em;
		try {
			em=new ExcelModel(filePath, response, "中央苏区县项目库1");
			em.bindCell("titleTime", titleTile);
			em.bindColumns(4, rinfos.toArray());
	    	em.write();
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
		
	}



	@Override
	public void exportTable2(HttpServletRequest request,
			HttpServletResponse response, Long id) {
		String filePath = request.getSession().getServletContext().getRealPath("/xls-templates/table2.xls");
		SimpleDateFormat simple=new SimpleDateFormat("yyyy年MM月dd日");
//		SimpleDateFormat simple1=new SimpleDateFormat("MMddhhmmss");
		List<ProjectInfo> pinfos = new ArrayList<>();
		FVMACountyReportEntity cReport = __get(id);
		String startTime = simple.format( cReport.getReport().getStartTime());
		String deadline = simple.format(cReport.getReport().getDeadline());
		String titleTile = "(统计起止时间"+startTime+"至"+deadline+")";
		
		List<FVModelAreaReportEntity> mas = cReport.getModelAreas();
		String ql = "select x from FVProjectRuralEntity x where x.modelArea.id=? and x.deleted=false";
		pinfos.add(new ProjectInfo(cReport));//第一行,小计
		for(FVModelAreaReportEntity ma:mas){
			List<FVProjectRuralEntity> pjrs = __list(FVProjectRuralEntity.class, ql,ma.getId());
			for(int i=0;i<pjrs.size();i++){
				FVProjectRuralEntity pr = pjrs.get(i);
				if(i==0){
					pinfos.add(new ProjectInfo(pr));//第一行要显示片区名
				}else{
					ProjectInfo pi = new ProjectInfo(pr);
					pi.setMaName("");//其他行不显示片区名
					pi.setCityCountyMaName("");
					pinfos.add(pi);
				}
			}
		}
		ExcelModel em;
		try {
			em=new ExcelModel(filePath, response, "中央苏区县项目库2");
			em.bindCell("titleTime", titleTile);
			em.bindColumns(4, pinfos.toArray());
	    	em.write();
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
		
	}



	@Override
	public void exportTable3(HttpServletRequest request,
			HttpServletResponse response, Long id) {
		String filePath = request.getSession().getServletContext().getRealPath("/xls-templates/table3.xls");
		SimpleDateFormat simple=new SimpleDateFormat("yyyy年MM月dd日");
//		SimpleDateFormat simple1=new SimpleDateFormat("MMddhhmmss");
		List<Object> infos = new ArrayList<>();
		FVMACountyReportEntity cReport = __get(id);
		String startTime = simple.format( cReport.getReport().getStartTime());
		String deadline = simple.format(cReport.getReport().getDeadline());
		String titleTile = "(统计起止时间"+startTime+"至"+deadline+")";
		
		List<FVModelAreaReportEntity> mas = cReport.getModelAreas();
		infos.add(new FVTable3Info(cReport));//第一行,小计
		for(FVModelAreaReportEntity ma:mas){
			infos.add(new FVTable3Info(ma));
		}
		
		ExcelModel em;
		try {
			em=new ExcelModel(filePath, response, "中央苏区县项目库3");
			em.bindCell("titleTime", titleTile);
			em.bindColumns(4, infos.toArray());
	    	em.write();
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}



	@Override
	public Long lastReport(Long id, LogonUser user) {
		String ql =  "select x.place.id from UnitEntity x where x.id=?";
		Long placeid = __first(Long.class,ql,user.getOwnerId());
		
		String sql = "select x.id from FVMACountyReportEntity x where x.place.id=? order by x.id desc ";//limit 0,1
		Query qu = entityManager.createQuery(sql);
		qu.setParameter(1, placeid);
		qu.setFirstResult(0);
		qu.setMaxResults(1);
		Long lastReportId = (Long)qu.getSingleResult();
		
		return lastReportId;
	}



	@Override
	public List<FVMACountyReportEntity> allCountyReportByReport(Long id) {
		String ql = "select x from FVMACountyReportEntity x where x.report.id=?and x.deleted=false";
		return __list(FVMACountyReportEntity.class, ql,id);
	}



	@Override
	public int checkWords(Long id) {
		FVMACountyReportEntity cReport = __get(id);
		List<FVModelAreaReportEntity> mas = cReport.getModelAreas();
		for(FVModelAreaReportEntity ma:mas){
			if(ma.getFeature()==null)return 1;
			if(ma.getProblem()==null)return 2;
			if(ma.getRemark()==null)return 3;
			int f1 = ma.getFeature().length();
			int f2 = ma.getProblem().length();
			int f3 = ma.getRemark().length();
			if(f1<500){
				return 1;//建设特点
			}
			if(f2>200){
				return 2;//存在问题
			}
			if(f3>200){
				return 3;//备注
			}
		}
		return 0;
	}



	
	
	

}
