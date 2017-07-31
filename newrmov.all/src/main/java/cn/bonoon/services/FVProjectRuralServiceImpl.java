package cn.bonoon.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.FVProjectRuralService;
import cn.bonoon.core.felicity.ProjectInfo;
import cn.bonoon.entities.felicityVillage.FVBaseRuralEntity;
import cn.bonoon.entities.felicityVillage.FVMACountyReportEntity;
import cn.bonoon.entities.felicityVillage.FVModelAreaReportEntity;
import cn.bonoon.entities.felicityVillage.FVProjectEntity;
import cn.bonoon.entities.felicityVillage.FVProjectRuralEntity;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.services.AbstractService;
import cn.bonoon.util.DoubleHelper;

@Service
@Transactional(readOnly = true)
public class FVProjectRuralServiceImpl extends AbstractService<FVProjectRuralEntity> implements FVProjectRuralService{

	@Override
	@Transactional
	public void saveProject(LogonUser user,Long id,Long areaId, String pjType, String pjName,
			Long[] rurals, Double budgetMoney, Double finishMoney,
			Date startTime, Date finishTime, Date checkTime, String checkUnit) {
		if(budgetMoney==null)budgetMoney=0.0;
		if(finishMoney==null)finishMoney=0.0;
		if(id==null){
			FVModelAreaReportEntity ma = entityManager.find(FVModelAreaReportEntity.class, areaId);
			FVMACountyReportEntity cReport = ma.getCountyReport();
			FVProjectEntity pj = new FVProjectEntity();
			pj.setModelArea(ma);
			pj.setName(pjName);
			pj.setType(pjType);
			pj.setCreateAt(new Date());
			pj.setCreatorId(user.getId());
			pj.setCreatorName(user.getUsername());
//			ma.setProjectNum(ma.getProjectNum() + 1);//片区项目数累加
			ma.setProjectAreaCount(ma.getProjectAreaCount() + 1);
//			cReport.setProjectNum(cReport.getProjectNum() + 1);//县项目数累加
			cReport.setProjectAreaCount(cReport.getProjectAreaCount() + 1);
			entityManager.persist(pj);
			
			FVProjectRuralEntity pjr = new FVProjectRuralEntity();
			List<FVBaseRuralEntity> rs = new ArrayList<>();
			String name = "";
//			for(Long rid:rurals){
//				FVBaseRuralEntity baseR = entityManager.find(FVBaseRuralEntity.class, rid);
//				name = name+baseR.getName()+"("+baseR.getType()+")；";
//				rs.add(baseR);
//			}
			
			for(int i=0;i<rurals.length;i++){
				if(i==rurals.length - 1){
					FVBaseRuralEntity baseR = entityManager.find(FVBaseRuralEntity.class, rurals[i]);
					name = name+baseR.getName()+"("+baseR.getType()+")";
					rs.add(baseR);
				}else{
					FVBaseRuralEntity baseR = entityManager.find(FVBaseRuralEntity.class, rurals[i]);
					name = name+baseR.getName()+"("+baseR.getType()+")、";
					rs.add(baseR);
				}
			}
			pjr.setName(name);
			pjr.setModelArea(ma);
			pjr.setRurals(rs);
			pjr.setProject(pj);
			
			
			pjr.setStartTime(startTime);
			pjr.setFinishTime(finishTime);
			pjr.setCheckTime(checkTime);
			pjr.setCheckUnit(checkUnit);
			pjr.setBudgetMoney(budgetMoney);
			
			pjr.setFinishMoney(finishMoney);
			
			pjr.setCreateAt(new Date());
			pjr.setCreatorId(user.getId());
			pjr.setCreatorName(user.getUsername());
			entityManager.persist(pjr);
			
			ma.setBudgetMoney(DoubleHelper.add(ma.getBudgetMoney(), budgetMoney));//片区预算投入累加
			ma.setFinishMoney(DoubleHelper.add(ma.getFinishMoney(), finishMoney));//片区完成投入累加
			entityManager.merge(ma);
			
			
			cReport.setBudgetMoney(DoubleHelper.add(cReport.getBudgetMoney(),budgetMoney));//县预算投入累加
			cReport.setFinishMoney(DoubleHelper.add(cReport.getFinishMoney(),finishMoney));//县完成投入累加
			entityManager.merge(cReport);
		}else{
			//示范片名称应该是不能再该的
			
			FVProjectRuralEntity pr = __get(id);
			FVProjectEntity pj = pr.getProject();
			FVModelAreaReportEntity ma = pj.getModelArea();
			FVMACountyReportEntity cReport = ma.getCountyReport();
			
			pj.setName(pjName);
			
			pr.setStartTime(startTime);
			pr.setFinishTime(finishTime);
			pr.setCheckTime(checkTime);
			pr.setCheckUnit(checkUnit);
			
			//县
//			double crb = cReport.getBudgetMoney();
//			double prb = pr.getBudgetMoney();
			double cr_budgetMoney = DoubleHelper.subtract(cReport.getBudgetMoney(), pr.getBudgetMoney());
			cReport.setBudgetMoney( DoubleHelper.add(cr_budgetMoney, budgetMoney));
			double cr_finishMoney = DoubleHelper.subtract(cReport.getFinishMoney(), pr.getFinishMoney());
			cReport.setFinishMoney( DoubleHelper.add(cr_finishMoney, finishMoney));
			
			//片区
			double ma_budgetMoney = DoubleHelper.subtract(ma.getBudgetMoney(), pr.getBudgetMoney());
			ma.setBudgetMoney( DoubleHelper.add(ma_budgetMoney, budgetMoney));
			double ma_finishMoney = DoubleHelper.subtract(ma.getFinishMoney(), pr.getFinishMoney());
			ma.setFinishMoney( DoubleHelper.add(ma_finishMoney, finishMoney));
			
			
			pr.setBudgetMoney(budgetMoney);
			pr.setFinishMoney(finishMoney);
			entityManager.merge(pr);
			entityManager.merge(pj);
			
			entityManager.merge(ma);
			entityManager.merge(cReport);
		}
		
		
	}

	@Override
	public List<ProjectInfo> makePjList(Long id) {
		List<ProjectInfo> pinfos = new ArrayList<>();
				
		FVMACountyReportEntity cReport = entityManager.find(FVMACountyReportEntity.class, id);
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
					pinfos.add(pi);
				}
			}
		}
		return pinfos;
	}
	
	
	@Override
	public List<ProjectInfo> makePjListByMaId(Long id) {
		List<ProjectInfo> pinfos = new ArrayList<>();
		String ql = "select x from FVProjectRuralEntity x where x.modelArea.id=? and x.deleted=false";
		List<FVProjectRuralEntity> pjrs = __list(FVProjectRuralEntity.class, ql,id);
		FVModelAreaReportEntity ma = entityManager.find(FVModelAreaReportEntity.class, id);
		pinfos.add(new ProjectInfo(ma));
		for(int i=0;i<pjrs.size();i++){
			FVProjectRuralEntity pr = pjrs.get(i);
			if(i==0){
				pinfos.add(new ProjectInfo(pr));//第一行要显示片区名
			}else{
				ProjectInfo pi = new ProjectInfo(pr);
				pi.setMaName("");//其他行不显示片区名
				pinfos.add(pi);
			}
		}
		return pinfos;
	}

	//删除后,小计也要做相应的更改
	@Override
	@Transactional
	public void deleteProject(Long id) {
		FVProjectRuralEntity pr = __get(id);
		
		FVModelAreaReportEntity ma = pr.getModelArea();
		FVMACountyReportEntity cReport = ma.getCountyReport();
		
		//县
		double cr_budgetMoney = DoubleHelper.subtract(cReport.getBudgetMoney(), pr.getBudgetMoney());
		cReport.setBudgetMoney( cr_budgetMoney);
		double cr_finishMoney = DoubleHelper.subtract(cReport.getFinishMoney(), pr.getFinishMoney());
		cReport.setFinishMoney( cr_finishMoney);
		cReport.setProjectAreaCount(cReport.getProjectAreaCount() - 1);
		
		//片区
		double ma_budgetMoney = DoubleHelper.subtract(ma.getBudgetMoney(), pr.getBudgetMoney());
		ma.setBudgetMoney( ma_budgetMoney);
		double ma_finishMoney = DoubleHelper.subtract(ma.getFinishMoney(), pr.getFinishMoney());
		ma.setFinishMoney( ma_finishMoney);
		ma.setProjectAreaCount(ma.getProjectAreaCount() - 1);
		
		pr.setDeleted(true);
		entityManager.merge(pr);
		entityManager.merge(ma);
		entityManager.merge(cReport);
	}

	

	

	

}
