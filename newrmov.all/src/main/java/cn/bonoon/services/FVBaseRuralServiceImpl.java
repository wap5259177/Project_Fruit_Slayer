package cn.bonoon.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.FVBaseRuralService;
import cn.bonoon.entities.felicityVillage.FVBaseRuralEntity;
import cn.bonoon.entities.felicityVillage.FVCoreRuralEntity;
import cn.bonoon.entities.felicityVillage.FVMACountyReportEntity;
import cn.bonoon.entities.felicityVillage.FVModelAreaReportEntity;
import cn.bonoon.entities.felicityVillage.FVPeripheralRuralEntity;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class FVBaseRuralServiceImpl extends AbstractService<FVBaseRuralEntity> implements FVBaseRuralService{

	
	@Override
	@Transactional
	public void saveRural(LogonUser user,Long id, boolean ismaster, String crName,
			String crType, String prName, String prType, Long coreRural,
			Boolean finishedPlan, Boolean finishedBid,Integer projectNum,Integer projectFinishNum, Date startTime,
			Date predictFinishTime,String constructProgress) {
		FVModelAreaReportEntity ma = entityManager.find(FVModelAreaReportEntity.class, id);
		FVMACountyReportEntity cReport = ma.getCountyReport();
		//还可以用ismaster判断
		if(coreRural==null){
			//主体村
			FVCoreRuralEntity cr = new FVCoreRuralEntity();
			cr.setCreateAt(new Date());
			cr.setCreatorId(user.getId());
			cr.setCreatorName(user.getUsername());
			
			cr.setName(crName);
			cr.setType(crType);
			
			cr.setFinishedPlan(finishedPlan);
			cr.setFinishedBid(finishedBid);
			
			cr.setProjectNum(projectNum);
			cr.setProjectFinishNum(projectFinishNum);
			
			cr.setStartTime(startTime);
			cr.setPredictFinishTime(predictFinishTime);
			cr.setConstructProgress(constructProgress);
			
			cr.setModelArea(ma);
			cr.setPlace(ma.getCountyReport().getPlace());
			entityManager.persist(cr);
			
			ma.setCrNum(ma.getCrNum() + 1);//片小计主体村
			cReport.setCrNum(cReport.getCrNum() + 1);
			if(cr.isFinishedPlan()){
				ma.setFinishedPlanNum(ma.getFinishedPlanNum() + 1);
				cReport.setFinishedPlanNum(cReport.getFinishedPlanNum() + 1);
			}else{
				ma.setNotFinishedPlanNum(ma.getNotFinishedPlanNum() + 1);
				cReport.setNotFinishedPlanNum(cReport.getNotFinishedPlanNum() + 1);
			}
			
			if(cr.isFinishedBid()){
				ma.setFinishedBidNum(ma.getFinishedBidNum() + 1);
				cReport.setFinishedBidNum(cReport.getFinishedBidNum() + 1);
			}else{
				ma.setNotFinishedBidNum(ma.getNotFinishedBidNum() + 1);
				cReport.setNotFinishedBidNum(cReport.getNotFinishedBidNum() + 1);
			}
			
			ma.setProjectNum(ma.getProjectNum()+ cr.getProjectNum());//小计项目
			ma.setProjectFinishNum(ma.getProjectFinishNum() + cr.getProjectFinishNum());
			
			cReport.setProjectNum(cReport.getProjectNum()+ cr.getProjectNum());//小计项目
			cReport.setProjectFinishNum(cReport.getProjectFinishNum() + cr.getProjectFinishNum());
		}else{
			FVPeripheralRuralEntity pr = new FVPeripheralRuralEntity();
			FVCoreRuralEntity rural = entityManager.find(FVCoreRuralEntity.class, coreRural);
			pr.setCreateAt(new Date());
			pr.setCreatorId(user.getId());
			pr.setCreatorName(user.getUsername());
			
			pr.setName(prName);
			pr.setType(prType);
			
			
			if(projectNum==null)projectNum=0;
			pr.setProjectNum(projectNum);
			if(projectFinishNum==null)projectFinishNum=0;
			pr.setProjectFinishNum(projectFinishNum);
			
			pr.setStartTime(startTime);
			pr.setPredictFinishTime(predictFinishTime);
			pr.setConstructProgress(constructProgress);
			pr.setCoreRural(rural);
			
			pr.setModelArea(ma);
			pr.setPlace(ma.getCountyReport().getPlace());
			
			ma.setPrNum(ma.getPrNum() + 1);
			ma.setProjectNum(ma.getProjectNum()+ pr.getProjectNum());//小计项目
			ma.setProjectFinishNum(ma.getProjectFinishNum() + pr.getProjectFinishNum());
			
			cReport.setPrNum(cReport.getPrNum() + 1);
			cReport.setProjectNum(cReport.getProjectNum()+ pr.getProjectNum());//小计项目
			cReport.setProjectFinishNum(cReport.getProjectFinishNum() + pr.getProjectFinishNum());
			entityManager.persist(pr);
		}
			entityManager.merge(ma);
			entityManager.merge(cReport);
		
		
	}

	@Override
	@Transactional
	public void updatePRural(LogonUser user, Long id, String prName,
			String prType, Long coreRural, Integer projectNum,
			Integer projectFinishNum, Date startTime, Date predictFinishTime,
			String constructProgress) {
		FVPeripheralRuralEntity entity = entityManager.find(FVPeripheralRuralEntity.class, id);
		entity.setName(prName);
		entity.setType(prType);
//		FVCoreRuralEntity cr = entityManager.find(FVCoreRuralEntity.class, coreRural);
//		FVModelAreaReportEntity ma = cr.getModelArea();
//		FVMACountyReportEntity cReport = ma.getCountyReport();
//		entity.setCoreRural(cr);
		
		FVModelAreaReportEntity ma = entity.getModelArea();
		FVMACountyReportEntity cReport = ma.getCountyReport();
		
		if(projectNum==null)projectNum=0;
		ma.setProjectNum(ma.getProjectNum() - entity.getProjectNum() + projectNum);//xi
		cReport.setProjectNum(cReport.getProjectNum() - entity.getProjectNum() + projectNum);//xi
		entity.setProjectNum(projectNum);
		
		
		if(projectFinishNum==null)projectFinishNum=0;
		ma.setProjectFinishNum(ma.getProjectFinishNum() - entity.getProjectFinishNum() + projectFinishNum);//相应的片区的统计也要做修改
		cReport.setProjectFinishNum(cReport.getProjectFinishNum() - entity.getProjectFinishNum() + projectFinishNum);//相应的片区的统计也要做修改
		entity.setProjectFinishNum(projectFinishNum);
		
		
		entity.setStartTime(startTime);
		entity.setPredictFinishTime(predictFinishTime);
		entity.setConstructProgress(constructProgress);
		
		entityManager.merge(cReport);
		entityManager.merge(ma);
		entityManager.merge(entity);
		
		
	}

	@Override
	@Transactional
	public void updateCRural(LogonUser user, Long id, String crName,
			String crType, Boolean finishedPlan, Boolean finishedBid,
			Integer projectNum, Integer projectFinishNum, Date startTime,
			Date predictFinishTime, String constructProgress) {
		
		FVCoreRuralEntity entity = entityManager.find(FVCoreRuralEntity.class, id);
		FVModelAreaReportEntity ma = entity.getModelArea();
		FVMACountyReportEntity cReport = ma.getCountyReport();
		entity.setName(crName);
		entity.setType(crType);
		
		if(finishedPlan==true){
			if(!entity.isFinishedPlan()){
				ma.setFinishedPlanNum(ma.getFinishedPlanNum() + 1);
				ma.setNotFinishedPlanNum(ma.getNotFinishedPlanNum() - 1);
				cReport.setFinishedPlanNum(cReport.getFinishedPlanNum() + 1);
				cReport.setNotFinishedPlanNum(cReport.getNotFinishedPlanNum() - 1);
			}
		}else{
			if(entity.isFinishedPlan()){
				ma.setFinishedPlanNum(ma.getFinishedPlanNum() - 1);
				ma.setNotFinishedPlanNum(ma.getNotFinishedPlanNum() + 1);
				cReport.setFinishedPlanNum(cReport.getFinishedPlanNum() - 1);
				cReport.setNotFinishedPlanNum(cReport.getNotFinishedPlanNum() + 1);
			}
		}
		
		if(finishedBid==true){
			if(!entity.isFinishedBid()){
				ma.setFinishedBidNum(ma.getFinishedBidNum() + 1);
				ma.setNotFinishedBidNum(ma.getNotFinishedBidNum() - 1);
				cReport.setFinishedBidNum(cReport.getFinishedBidNum() + 1);
				cReport.setNotFinishedBidNum(cReport.getNotFinishedBidNum() - 1);
			}
		}else{
			if(entity.isFinishedBid()){
				ma.setFinishedBidNum(ma.getFinishedBidNum() - 1);
				ma.setNotFinishedBidNum(ma.getNotFinishedBidNum() + 1);
				cReport.setFinishedBidNum(cReport.getFinishedBidNum() - 1);
				cReport.setNotFinishedBidNum(cReport.getNotFinishedBidNum() + 1);
			}
		}
		
		entity.setFinishedBid(finishedBid);
		entity.setFinishedPlan(finishedPlan);
		
		if(projectNum==null)projectNum=0;
		ma.setProjectNum( ma.getProjectNum() - entity.getProjectNum() + projectNum);//相应的片区的统计也要做修改
		cReport.setProjectNum( cReport.getProjectNum() - entity.getProjectNum() + projectNum);
		entity.setProjectNum(projectNum);
		
		
		if(projectFinishNum==null)projectFinishNum=0;
		ma.setProjectFinishNum(ma.getProjectFinishNum() - entity.getProjectFinishNum() + projectFinishNum);//相应的片区的统计也要做修改
		cReport.setProjectFinishNum(cReport.getProjectFinishNum() - entity.getProjectFinishNum() + projectFinishNum);//相应的片区的统计也要做修改
		entity.setProjectFinishNum(projectFinishNum);
		
		entity.setStartTime(startTime);
		entity.setPredictFinishTime(predictFinishTime);
		entity.setConstructProgress(constructProgress);
		
		entityManager.merge(cReport);
		entityManager.merge(ma);
		entityManager.merge(entity);
	}

	@Override
	public List<FVBaseRuralEntity> allRuralByModelArea(Long id) {
		String ql = "select x from FVBaseRuralEntity x where x.modelArea.id=? and x.deleted=false";
		List<FVBaseRuralEntity> rurals = __list(FVBaseRuralEntity.class, ql,id);
		return rurals;
	}

	@Override
	@Transactional
	public void deleteRural(Long id) {
		FVBaseRuralEntity rural = __get(id);
		rural.setDeleted(true);
		
		FVModelAreaReportEntity ma = rural.getModelArea();
		FVMACountyReportEntity cReport = ma.getCountyReport();
		int _pjNum = 0;
		int _pjFinNum = 0;
		int _prNum = 0;//删除主体村后辐射村的数目也要做相应的更改
		_pjNum+=rural.getProjectNum();
		_pjFinNum+=rural.getProjectFinishNum();
		
		if(rural instanceof FVCoreRuralEntity){
			ma.setCrNum(ma.getCrNum() - 1);
			cReport.setCrNum(cReport.getCrNum() - 1);
			FVCoreRuralEntity cr = (FVCoreRuralEntity) rural;
			if(cr.isFinishedPlan()){
				ma.setFinishedPlanNum(ma.getFinishedPlanNum() - 1);
				cReport.setFinishedPlanNum(cReport.getFinishedPlanNum() - 1);
			}else{
				ma.setNotFinishedPlanNum(ma.getNotFinishedPlanNum() - 1);
				cReport.setNotFinishedPlanNum(cReport.getNotFinishedPlanNum() - 1);
			}
			if(cr.isFinishedBid()){
				ma.setFinishedBidNum(ma.getFinishedBidNum() - 1);
				cReport.setFinishedBidNum(cReport.getFinishedBidNum() - 1);
			}else{
				ma.setNotFinishedBidNum(ma.getNotFinishedBidNum() - 1);
				cReport.setNotFinishedBidNum(cReport.getNotFinishedBidNum() - 1);
			}
			List<FVPeripheralRuralEntity> prs = cr.getFvprs();
			for(FVPeripheralRuralEntity pr:prs){//主体村下面的辐射村的项目也要做相应的修改
				if(!pr.isDeleted()){
					_pjNum+=pr.getProjectNum();
					_pjFinNum+=pr.getProjectFinishNum();
					_prNum+=1;
				}
				
			}
			
//			//另一个种做法,主体村下面有辐射村,则不能删除主体村
//			if(cr.getFvprs().size()>0){
//				throw new RuntimeException("主体村下面还有辐射村,不能删除!");
//			}
		}else{
			ma.setPrNum(ma.getPrNum() - 1);
			cReport.setPrNum(cReport.getPrNum() - 1);
		}
//		ma.setProjectNum(ma.getProjectNum() - rural.getProjectNum());
//		ma.setProjectFinishNum(ma.getProjectFinishNum() - rural.getProjectFinishNum());
		ma.setProjectNum(ma.getProjectNum() - _pjNum);
		ma.setProjectFinishNum(ma.getProjectFinishNum() - _pjFinNum);
		ma.setPrNum(ma.getPrNum() - _prNum);
		
		cReport.setProjectNum(cReport.getProjectNum() - _pjNum);
		cReport.setProjectFinishNum(cReport.getProjectFinishNum() - _pjFinNum);
		cReport.setPrNum(cReport.getPrNum() - _prNum);
		
		entityManager.merge(cReport);
		entityManager.merge(ma);
		entityManager.merge(rural);
	}

	

}
