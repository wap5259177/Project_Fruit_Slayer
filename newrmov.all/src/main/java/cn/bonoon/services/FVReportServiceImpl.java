package cn.bonoon.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.controllers.utils.ExcelModel;
import cn.bonoon.core.FVReportService;
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
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class FVReportServiceImpl extends AbstractService<FVReportEntity> implements FVReportService{

	@Override
	protected FVReportEntity __save(OperateEvent event, FVReportEntity entity) {
		return super.__save(event, entity);
	}

	//提交
	@Override
	@Transactional
	public void startReport(Long id) {
		FVReportEntity entity = __get(id);
		entity.setStatus(1);
		entityManager.merge(entity);
	}

	//查出省单位创建的4次年报
	@Override
	public List<FVReportEntity> allReport() {
		String ql = "select x from FVReportEntity x where x.status in (1,2,3) and x.deleted=false";
		return __list(FVReportEntity.class, ql);
		
	}

	@Override
	@Transactional
	public FVMACountyReportEntity toReport(Long reportId,LogonUser user) {
//		String ql = "select x from PlaceEntity x where x.id=? and x.deleted=false";
//		PlaceEntity place = __first(PlaceEntity.class, ql,user.getOwnerId());
		
		String pql = "select x.place.id from UnitEntity x where x.id=?";
		Long pid = __first(Long.class, pql,user.getOwnerId());
		PlaceEntity place = entityManager.find(PlaceEntity.class, pid);
		FVReportEntity entity = __get(reportId);
		
		FVMACountyReportEntity fvcr = new FVMACountyReportEntity();
		fvcr.setReport(entity);
		fvcr.setPlace(place);
		fvcr.setCityId(place.getParent().getId());
		fvcr.setCityName(place.getParent().getFullName());
		
		fvcr.setCreateAt(new Date());//这个是不能为空的
		fvcr.setCreatorId(user.getId());
		fvcr.setCreatorName(user.getUsername());
		
		fvcr.setStatus(2);//0:未开始 1:完成  2:正在填报
		
		entityManager.persist(fvcr);
		
		return fvcr;
		
	}

	@Override
	public List<Object> table1Items(Long reportId, LogonUser user) {
		List<Object> infos = new ArrayList<>();
		__makeTable1Items(reportId, user, infos);
		return infos;
	}
	
	@Override
	public List<Object> table2Items(Long reportId, LogonUser user) {
		List<Object> infos = new ArrayList<>();
		__makeTable2Items(reportId, user, infos);
		return infos;
	}
	
	@Override
	public List<Object> table3Items(Long reportId, LogonUser user) {
		List<Object> infos = new ArrayList<>();
		__makeTable3Items(reportId, user, infos);
		return infos;
	}

	@Override
	public void exportTable(HttpServletRequest request,
			HttpServletResponse response, Long reportId, Integer tableType,LogonUser user) {
		SimpleDateFormat simple=new SimpleDateFormat("yyyy年MM月dd日");
		FVReportEntity report = __get(reportId);
		String startTime = simple.format( report.getStartTime());
		String deadline = simple.format(report.getDeadline());
		String titleTile = "(统计起止时间"+startTime+"至"+deadline+")";
		List<Object> infos = new ArrayList<>();
//		List<Object> infos2 = new ArrayList<>();
		switch (tableType) {
		case 1://导出项目库一
			__makeTable1Items(reportId, user, infos);
			break;
		case 2:
			__makeTable2Items(reportId, user, infos);
			break;
		case 3:
			__makeTable3Items(reportId, user, infos);
			break;
		}
		ExcelModel em;
		String filePath = request.getSession().getServletContext().getRealPath("/xls-templates/table"+tableType+".xls");
		try {
			em=new ExcelModel(filePath, response, "中央苏区县项目库"+tableType);
			em.bindCell("titleTime", titleTile);
			em.bindColumns(4, infos.toArray());
	    	em.write();
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
	
	
	
	//TODO:应该是统计县报的已经提交了的,不然有些没提交,统计的数据要是他没填有的话就是0
	private void __makeTable3Items(Long reportId, LogonUser user,
			List<Object> infos) {
		String ql1 = "";
		List<FVMACountyReportEntity> cReports = null;
		//可以通过user  判断是省统计还是市统计,来执行不同的sql
		String sumQl = "";
		Object[] sums = null;
		String cityQl = "select x.place.id from UnitEntity x where x.id=?";
		if("广东省".equals(user.getDisplayName())){//查全省的
			ql1 = "select x from FVMACountyReportEntity x where x.report.id=? and x.deleted=false";
			cReports = __list(FVMACountyReportEntity.class, ql1,reportId);
			sumQl = "select " +
					"sum(x.investment.totalFunds)," +
					"sum(x.investment.provinceFunds)," +
					"sum(x.investment.cityFunds)," +
					"sum(x.investment.countyFunds)," +
					"sum(x.investment.socialFunds)," +
					"sum(x.investment.massFunds)," +
					"sum(x.investment.otherFunds)," +
					"sum(x.invested)" +//已投入资金
					"from FVMACountyReportEntity x where x.report.id=? and x.deleted=false";
			sums = __first(Object[].class,sumQl,reportId);
		}else{//查某个市的
			Long cityId = __first(Long.class, cityQl,user.getOwnerId());
			ql1 = "select x from FVMACountyReportEntity x where x.cityId=? and x.report.id=? and x.deleted=false";
			cReports = __list(FVMACountyReportEntity.class, ql1, cityId,reportId);
			sumQl = "select " +
					"sum(x.investment.totalFunds)," +
					"sum(x.investment.provinceFunds)," +
					"sum(x.investment.cityFunds)," +
					"sum(x.investment.countyFunds)," +
					"sum(x.investment.socialFunds)," +
					"sum(x.investment.massFunds)," +
					"sum(x.investment.otherFunds)," +
					"sum(x.invested)" +//已投入资金
					"from FVMACountyReportEntity x where x.cityId=? and x.report.id=? and x.deleted=false";
			sums = __first(Object[].class,sumQl,cityId,reportId);
		}
		
		
		FVTable3Info info = new FVTable3Info();
		info.setTotalFunds(sums[0]);
		info.setProvinceFunds(sums[1]);
		info.setCityFunds(sums[2]);
		info.setCountyFunds(sums[3]);
		info.setSocialFunds(sums[4]);
		info.setMassFunds(sums[5]);
		info.setOtherFunds(sums[6]);
		info.setInvested(sums[7]);
		info.setCityCountyMaName("总计");//导出excel的显示
		infos.add(info);
		
		for(FVMACountyReportEntity cReport:cReports){
			List<FVModelAreaReportEntity> mas = cReport.getModelAreas();
			infos.add(new FVTable3Info(cReport));//第一行,小计
			for(FVModelAreaReportEntity ma:mas){
				infos.add(new FVTable3Info(ma));
			}
		}
		
		
	}
	

	//TODO:应该是统计县报的已经提交了的,不然有些没提交,统计的数据要是他没填有的话就是0
	private void __makeTable2Items(Long reportId, LogonUser user,
			List<Object> infos) {
		String ql1 = "";
		List<FVMACountyReportEntity> cReports = null;
		//可以通过user  判断是省统计还是市统计,来执行不同的sql
		String sumQl = "";
		Object[] sums = null;
		String cityQl = "select x.place.id from UnitEntity x where x.id=?";
		if("广东省".equals(user.getDisplayName())){
			ql1 = "select x from FVMACountyReportEntity x where  x.report.id=? and x.deleted=false";
			cReports = __list(FVMACountyReportEntity.class, ql1, reportId);
			sumQl = "select " +
					"sum(x.projectAreaCount)," +//项目数量,表二项目创建的数量
					"sum(x.budgetMoney)," +//预算投入
					"sum(x.finishMoney)" +//完成投入
					"from FVMACountyReportEntity x where  x.report.id=? and x.deleted=false";
			sums = __first(Object[].class,sumQl,reportId);
		}else{
			Long cityId = __first(Long.class, cityQl,user.getOwnerId());
			ql1 = "select x from FVMACountyReportEntity x where x.cityId=? and x.report.id=? and x.deleted=false";
			cReports = __list(FVMACountyReportEntity.class, ql1, cityId,reportId);
			
			sumQl = "select " +
					"sum(x.projectAreaCount)," +//项目数量,表二项目创建的数量
					"sum(x.budgetMoney)," +//预算投入
					"sum(x.finishMoney)" +//完成投入
					"from FVMACountyReportEntity x where x.cityId=? and x.report.id=? and x.deleted=false";
			sums = __first(Object[].class,sumQl,cityId,reportId);
		}
		
		ProjectInfo info = new ProjectInfo();
		info.setPjName(sums[0]);
		info.setBudgetMoney(sums[1]);
		info.setFinishMoney(sums[2]);
		info.setMaName("总计");
		info.setCityCountyMaName("总计");//导出excel的显示
		infos.add(info);
		String ql = "select x from FVProjectRuralEntity x where x.modelArea.id=? and x.deleted=false";
		for(FVMACountyReportEntity cReport:cReports){
			
			List<FVModelAreaReportEntity> mas = cReport.getModelAreas();
			infos.add(new ProjectInfo(cReport));//县小计他的片区,
			for(FVModelAreaReportEntity ma:mas){
				List<FVProjectRuralEntity> pjrs = __list(FVProjectRuralEntity.class, ql,ma.getId());
				for(int i=0;i<pjrs.size();i++){
					FVProjectRuralEntity pr = pjrs.get(i);
					if(i==0){
						infos.add(new ProjectInfo(pr));//第一行要显示片区名
					}else{
						ProjectInfo pi = new ProjectInfo(pr);
						pi.setMaName("");//其他行不显示片区名
						pi.setCityCountyMaName("");
						infos.add(pi);
					}
				}
			}
		}
	}
	
private void __makeTable1Items(Long reportId, LogonUser user,List<Object> infos) {
		
		String ql1 = "";
		List<FVMACountyReportEntity> cReports = null;
		String sumQl = "";
		Object[] sums = null;
		String cityQl = "select x.place.id from UnitEntity x where x.id=?";
//		if(place.getLevel()==2){}
		String disName = user.getDisplayName();
//		String pql = "select x from  PlaceEntity x where x.id=?";
		if(/*__first(PlaceEntity.class,pql,user.getOwnerId()).getLevel()==1*/
				"广东省".equals(disName)){//省的统计
			ql1 = "select x from FVMACountyReportEntity x where x.report.id=? and x.deleted=false";
			cReports = __list(FVMACountyReportEntity.class, ql1,reportId);
			sumQl = "select " +
					"sum(x.crNum)," +
					"sum(x.prNum)," +
					"sum(x.finishedPlanNum)," +
					"sum(x.notFinishedPlanNum)," +
					"sum(x.finishedBidNum)," +
					"sum(x.notFinishedBidNum)," +
					"sum(x.projectNum)," +
					"sum(x.projectFinishNum)," +
					"sum(x.constructionArea),"+
					"sum(x.households),"+
					"sum(x.population)"+
					"from FVMACountyReportEntity x where  x.report.id=? and x.deleted=false";
			sums = __first(Object[].class,sumQl,reportId);
		}else{//市
			
			Long cityId = __first(Long.class, cityQl,user.getOwnerId());
			ql1 = "select x from FVMACountyReportEntity x where x.cityId=? and x.report.id=? and x.deleted=false";
			cReports = __list(FVMACountyReportEntity.class, ql1, cityId,reportId);
			sumQl = "select " +
					"sum(x.crNum)," +
					"sum(x.prNum)," +
					"sum(x.finishedPlanNum)," +
					"sum(x.notFinishedPlanNum)," +
					"sum(x.finishedBidNum)," +
					"sum(x.notFinishedBidNum)," +
					"sum(x.projectNum)," +
					"sum(x.projectFinishNum)," +
					"sum(x.constructionArea),"+
					"sum(x.households),"+
					"sum(x.population)"+
					"from FVMACountyReportEntity x where x.cityId=? and x.report.id=? and x.deleted=false";
			sums = __first(Object[].class,sumQl,cityId,reportId);
		}
		
		String  ql2 = "select x from FVCoreRuralEntity x where x.modelArea.id=? and x.deleted=false  order by x.id desc";
		
		
		RuralInfo ri = new RuralInfo();
		ri.setCrName(sums[0]);
		ri.setPrName(sums[1]);
		ri.setFinishedPlan(sums[2]);
		ri.setNoFinishedPlan(sums[3]);
		ri.setFinishedBid(sums[4]);
		ri.setNoFinishedBid(sums[5]);
		ri.setProjectNum(sums[6]);
		ri.setProjectFinishNum(sums[7]);
		ri.setConstructionArea(sums[8]);
		ri.setHouseholds(sums[9]);
		ri.setPopulation(sums[10]);
		ri.setModelAreaName("总计");
		infos.add(ri);//小计行
		
		for(FVMACountyReportEntity cReport:cReports){
			List<FVModelAreaReportEntity> mas = cReport.getModelAreas();
			for(int i=0;i<mas.size();i++){
				infos.add(new RuralInfo(mas.get(i)));
				List<FVCoreRuralEntity> crs = __list(FVCoreRuralEntity.class, ql2,mas.get(i).getId());
				for(FVCoreRuralEntity cr:crs){
					infos.add(new RuralInfo(cr));//主体村行的 显示
					List<FVPeripheralRuralEntity> prs = cr.getFvprs();
					for(FVPeripheralRuralEntity pr:prs){
						if(!pr.isDeleted()){
							infos.add(new RuralInfo(pr));//辐射村行的显示
						}
					}
				}
			}
		}
	}

/*退回*/
@Override
@Transactional
public void rejectReport(Long id) {
	FVMACountyReportEntity entity = entityManager.find(FVMACountyReportEntity.class, id);
	entity.setStatus(3);
	entityManager.merge(entity);
	
}

/*通过*/
@Override
@Transactional
public void paseReport(Long id) {
	FVMACountyReportEntity entity = entityManager.find(FVMACountyReportEntity.class, id);
	entity.setStatus(1);
	entityManager.merge(entity);
	
}

//@Override
//@Transactional
//public void finishReport(Long id) {
//	FVReportEntity entity = __get(id);
//	entity.setStatus(4);
//	entityManager.merge(entity);
//}
	
	

}
