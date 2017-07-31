package cn.bonoon.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.FelicityCountyReportService;
import cn.bonoon.core.ParamenterValue;
import cn.bonoon.entities.FelicityCountyEntity;
import cn.bonoon.entities.FelicityCountyReportEntity;
import cn.bonoon.entities.FelicityVillageEntity;
import cn.bonoon.entities.FelicityVillageReportEntity;
import cn.bonoon.entities.UnitEntity;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.AbstractService;
import cn.bonoon.kernel.util.StringHelper;

@Service
@Transactional(readOnly = true)
public class FelicityCountyReportServiceImpl extends AbstractService<FelicityCountyReportEntity> implements FelicityCountyReportService{

	@Override
	protected FelicityCountyReportEntity __update(OperateEvent event, FelicityCountyReportEntity entity) {
		if(event.is("applicant-submit")){
			String errormsg = "";
			int i = 1;
			if(StringHelper.isEmpty(entity.getInformant())){
				errormsg += i++ + ". 填报人不能为空！";
			}
			if(StringHelper.isEmpty(entity.getContact())){
				errormsg += i++ + ". 联系方式不能为空！";
			}
			if(null == entity.getReportingAt()){
				errormsg += i++ + ". 填报日期不能为空！";
			}
			if(!errormsg.isEmpty()){
				throw new RuntimeException(errormsg);
			}
			entity.setStatus(2);
			//进行统计计算
			entity.clear();
			for(FelicityVillageReportEntity fvr : entity.getItems()){
				entity.sum(fvr);
				
			}
//		}else if(){
//			entity.setStatus(0);
		}
		return super.__update(event, entity);
	}
	
	@Override
	protected FelicityCountyReportEntity __save(OperateEvent event, FelicityCountyReportEntity entity) {
		Calendar cal = Calendar.getInstance();
		int cyear = cal.get(Calendar.YEAR), cmonth = cal.get(Calendar.MONTH);
		int year = entity.getAnnual();
		int month = entity.getMonth();
		int crd = cyear * 100 + cmonth, rd = year * 100 + month;
		if(rd > crd){
			throw new RuntimeException("只允许创建" + cyear + "年" + (cmonth + 1) + "月份以前的数据！");
		}
		Long uid = event.getOwnerId();
		String ql = "select x from FelicityCountyEntity x where x.ownerId=?";
		FelicityCountyEntity county = __first(FelicityCountyEntity.class, ql, uid);
		if(null == county){
			throw new RuntimeException("未创建幸福村居的示范片区！");
		}
		String qle = "select count(x) from FelicityCountyReportEntity x where x.annual=? and x.month=? and x.county.id=?";
		if(__exsit(qle, year, month, county.getId())){
			throw new RuntimeException(year + "年" + (month + 1) + "月份的月度报表已经创建！");
		}
		//使用copy的方式来处理
		String qll = "select x from FelicityCountyReportEntity x where x.county.id=? order by x.annual, x.month desc";
		FelicityCountyReportEntity old = __first(FelicityCountyReportEntity.class, qll, county.getId());
		List<FelicityVillageReportEntity> olds;
		entity.setCounty(county);
		if(null != old){
			olds = old.getItems();
			entity.setBiddingCompletedFalse(old.getBiddingCompletedFalse());
			entity.setBiddingCompletedTrue(old.getBiddingCompletedTrue());
			entity.setConstructionArea(old.getConstructionArea());
			entity.setConstructionCharacteristic0(old.getConstructionCharacteristic0());
			entity.setConstructionCharacteristic1(old.getConstructionCharacteristic1());
			entity.setConstructionCharacteristic2(old.getConstructionCharacteristic2());
			entity.setConstructionCharacteristic3(old.getConstructionCharacteristic3());
			entity.setConstructionCharacteristic4(old.getConstructionCharacteristic4());
			entity.setConstructionCharacteristic5(old.getConstructionCharacteristic5());
			entity.setConstructionCharacteristic6(old.getConstructionCharacteristic6());
			entity.setEffectCleaningTeamFalse(old.getEffectCleaningTeamFalse());
			entity.setEffectCleaningTeamTrue(old.getEffectCleaningTeamTrue());
			entity.setEffectCouncilFalse(old.getEffectCouncilFalse());
			entity.setEffectCouncilTrue(old.getEffectCouncilTrue());
			entity.setEffectHardBottomFalse(old.getEffectHardBottomFalse());
			entity.setEffectHardBottomTrue(old.getEffectHardBottomTrue());
			entity.setEffectRemediationFalse(old.getEffectRemediationFalse());
			entity.setEffectRemediationTrue(old.getEffectRemediationTrue());
			entity.setEffectSewageTreatmentFalse(old.getEffectSewageTreatmentFalse());
			entity.setEffectSewageTreatmentTrue(old.getEffectSewageTreatmentTrue());
			entity.setEffectSolveGarbageFalse(old.getEffectSolveGarbageFalse());
			entity.setEffectSolveGarbageTrue(old.getEffectSolveGarbageTrue());
			entity.setEffectThroughWaterFalse(old.getEffectThroughWaterFalse());
			entity.setEffectThroughWaterTrue(old.getEffectThroughWaterTrue());
			entity.setEffectUniformStyleFalse(old.getEffectUniformStyleFalse());
			entity.setEffectUniformStyleTrue(old.getEffectUniformStyleTrue());
//			entity.setFundsCity(old.getFundsCity());
//			entity.setFundsCounty(old.getFundsCounty());
//			entity.setFundsMasses(old.getFundsMasses());
//			entity.setFundsOther(old.getFundsOther());
//			entity.setFundsProvince(old.getFundsProvince());
//			entity.setFundsSociety(old.getFundsSociety());
//			entity.setFundsTotal(old.getFundsTotal());
//			entity.setFundsTown(old.getFundsTown());
//			entity.setFundsVillage(old.getFundsVillage());
			entity.setHouseholdCount(old.getHouseholdCount());
			entity.setInvestmentBudget(old.getInvestmentBudget());
			entity.setInvestmentCompleted(old.getInvestmentCompleted());
			entity.setPlanningCompletedFalse(old.getPlanningCompletedFalse());
			entity.setPlanningCompletedTrue(old.getPlanningCompletedTrue());
			entity.setPopulation(old.getPopulation());
			entity.setProjectCount(old.getProjectCount());
		}else{
			olds = Collections.emptyList();
		}
		entity.setCreateAt(event.now());
		entity.setOwnerId(event.getOwnerId());
		entity.setCreatorId(event.getId());
		entity.setCreatorName(event.getUsername());
		super.__save(event, entity);
//		 List<FelicityVillageReportEntity> fv = new ArrayList<>();
		for(FelicityVillageEntity fve : county.getVillages()){
			FelicityVillageReportEntity fer=new FelicityVillageReportEntity();
			fer.setVillage(fve);
			fer.setCountyReport(entity);
			fer.setCreateAt(event.now());
			fer.setOwnerId(event.getOwnerId());
			fer.setCreatorId(event.getId());
			fer.setCreatorName(event.getUsername());
			for(FelicityVillageReportEntity ofv : olds){
				if(fve.getId().equals(ofv.getVillage().getId())){
					fer.setBiddingCompleted(ofv.isBiddingCompleted());
					fer.setBiddingProgress(ofv.getBiddingProgress());
					fer.setConstructionArea(ofv.getConstructionArea());
					fer.setConstructionCharacteristic(ofv.getConstructionCharacteristic());
					fer.setConstructionFeature(ofv.getConstructionFeature());
					fer.setEffectCleaningTeam(ofv.isEffectCleaningTeam());
					fer.setEffectCouncil(ofv.isEffectCouncil());
					fer.setEffectHardBottom(ofv.isEffectHardBottom());
					fer.setEffectOther(ofv.getEffectOther());
					fer.setEffectRemediation(ofv.isEffectRemediation());
					fer.setEffectSewageTreatment(ofv.isEffectSewageTreatment());
					fer.setEffectSolveGarbage(ofv.isEffectSolveGarbage());
					fer.setEffectThroughWater(ofv.isEffectThroughWater());
					fer.setEffectUniformStyle(ofv.isEffectUniformStyle());

//					fer.setFundsCity(ofv.getFundsCity());
//					fer.setFundsCounty(ofv.getFundsCounty());
//					fer.setFundsMasses(ofv.getFundsMasses());
//					fer.setFundsOther(ofv.getFundsOther());
//					fer.setFundsProvince(ofv.getFundsProvince());
//					fer.setFundsSociety(ofv.getFundsSociety());
//					fer.setFundsTotal(ofv.getFundsTotal());
//					fer.setFundsTown(ofv.getFundsTown());
//					fer.setFundsVillage(ofv.getFundsVillage());
					fer.setPlanningCompleted(ofv.isPlanningCompleted());
					fer.setPlanningProgress(ofv.getPlanningProgress());
					fer.setHouseholdCount(ofv.getHouseholdCount());
					fer.setInvestmentBudget(ofv.getInvestmentBudget());
					fer.setInvestmentCompleted(ofv.getInvestmentCompleted());
					fer.setPopulation(ofv.getPopulation());
					fer.setProjectCount(ofv.getProjectCount());
					fer.setProjectProgress(ofv.getProjectProgress());
					
					break;
				}
			}
			entityManager.persist(fer);
//			fv.add(fer);
		 }
//		 entity.setItems(fv);
		return entity;
	}
	
	@Override
	public List<FelicityCountyReportEntity> reports(IOperator user, int selectedYear) {
		String ql = "select x from FelicityCountyReportEntity x where x.annual=? and x.ownerId=?";
		return __list(FelicityCountyReportEntity.class, ql, selectedYear, user.getOwnerId());
	}

	@Override
	public FelicityCountyReportEntity report(IOperator user, Long id) {
		String ql = "select x from FelicityCountyReportEntity x where x.id=? and x.ownerId=?";
		FelicityCountyReportEntity fec=__first(FelicityCountyReportEntity.class, ql, id, user.getOwnerId());
		if(null != fec){
			return __parseProjectReport(user, fec);
		}
		return null;
	}

	@Override
	public FelicityCountyReportEntity start(IOperator user, int year, int month) {
		Long oid = user.getOwnerId();
		String ql0 = "select x from FelicityCountyReportEntity x where x.ownerId=? and x.annual=? and x.month=?";
		FelicityCountyReportEntity report = __first(FelicityCountyReportEntity.class, ql0, oid, year, month);
		if(null != report){
			__parseProjectReport(user, report);
			return report;
		}
		 String ql1="select x from FelicityCountyEntity x where x.ownerId=? and x.deleted=false";
		 FelicityCountyEntity fe=__first(FelicityCountyEntity.class,ql1,oid);
		 if(null == fe) return null;
		 
		 String ql2="select x from FelicityVillageEntity x where x.ownerId=? and x.county.id=?";
		 List<FelicityVillageEntity> fvi=__list(FelicityVillageEntity.class,ql2,user.getOwnerId(),fe.getId());
		 if(fvi.isEmpty()) return null;
		 long now = System.currentTimeMillis();
		 Date CurrTime = new Date(now);
		 report=new FelicityCountyReportEntity();
		 report.setCounty(fe);
		 report.setAnnual(year);
		 report.setMonth(month);	
		 report.setStatus(0);
		 report.setCreateAt(CurrTime);
		 report.setOwnerId(user.getOwnerId());
		 entityManager.persist(report);
		 List<FelicityVillageReportEntity> fv = new ArrayList<>();
		 for(FelicityVillageEntity  ef: fvi){
			FelicityVillageReportEntity fer=new FelicityVillageReportEntity();
			fer.setVillage(ef);
			fer.setCountyReport(report);
			fer.setCreateAt(CurrTime);
			entityManager.persist(fer);
			fv.add(fer);
		 }
		 report.setItems(fv);
		return report;
	}
	
	public FelicityCountyReportEntity __parseProjectReport(IOperator user,
			FelicityCountyReportEntity pre) {
		if(pre.getStatus() == 0){
			 String ql1="select x from FelicityVillageEntity x where x.county.id=? and x.ownerId=? ";
			 List<FelicityVillageEntity> fe=__list(FelicityVillageEntity.class,ql1,pre.getId(),pre.getOwnerId());
			 if(fe.isEmpty()) return null;	
			 List<FelicityVillageReportEntity> items = new ArrayList<>(pre.getItems());
			 for(int i = items.size() - 1; i >= 0; i--){
					if(fe.remove(items.get(i).getVillage())){
						items.remove(i);
					}
			}
			for(FelicityVillageReportEntity it : items){
					entityManager.remove(it);
					pre.getItems().remove(it);
			}
			for(FelicityVillageEntity pe : fe){
				FelicityVillageReportEntity pr = new FelicityVillageReportEntity();
				pr.setVillage(pe);
				pr.setCountyReport(pre);
				entityManager.persist(pr);
				pre.getItems().add(pr);
			}
		}
		
		return pre;
	}

	@Override
	@Transactional
	public void save(Long id, ParamenterValue pv, boolean submit) {
		FelicityCountyReportEntity report= entityManager.find(FelicityCountyReportEntity.class, id);
		if(null == report) throw new RuntimeException("数据操作异常！");
		if(submit){
			FelicityCountyEntity fe=report.getCounty();
			if(null==fe){
				//fe=new FelicityCountyEntity();
				
				//report.setCounty(fe);
			}
			for(FelicityVillageReportEntity fv : report.getItems()){
				FelicityVillageEntity fc=fv.getVillage();
				if(null==fc){
					fc=new FelicityVillageEntity();
					fv.setVillage(fc);
				}
				Long pid = fv.getId();
				
				fv.setConstructionArea(pv.getDouble(pid, "co-"));
				fv.setHouseholdCount(pv.getInt(pid, "ho-"));
				fv.setPopulation(pv.getInt(pid, "po-"));
				fv.setConstructionCharacteristic(StringHelper.join(',',pv.getStrings(pid, "con-")));
				fv.setProjectCount(pv.getInt(pid, "pr-"));
				fv.setProjectProgress(pv.getDouble(pid, "pro-"));
				fv.setInvestmentBudget(pv.getDouble(pid, "in-"));
				fv.setInvestmentCompleted(pv.getDouble(pid, "inv-"));
				fv.setFundsTotal(pv.getDouble(pid, "fu-"));
				fv.setFundsProvince(pv.getDouble(pid, "fun-"));
				fv.setFundsCity(pv.getDouble(pid, "funs-"));
				fv.setFundsCounty(pv.getDouble(pid, "funt-"));
				fv.setFundsTown(pv.getDouble(pid, "funv-"));
				fv.setFundsVillage(pv.getDouble(pid, "fundv-"));
				fv.setFundsMasses(pv.getDouble(pid, "funm-"));
				fv.setFundsSociety(pv.getDouble(pid, "funs-"));
				fv.setFundsOther(pv.getDouble(pid, "funo-"));
				fv.setPlanningCompleted(pv.getboolean(pid, "pc-"));
				fv.setProjectProgress(pv.getDouble(pid, "pp-"));
				fv.setBiddingCompleted(pv.getboolean(pid, "bc-"));
				fv.setBiddingProgress(pv.getDouble(pid, "bp-"));
				fv.setEffectRemediation(pv.getboolean(pid, "er-"));
				fv.setEffectUniformStyle(pv.getboolean(pid, "eu-"));
				fv.setEffectSolveGarbage(pv.getboolean(pid, "es-"));
				fv.setEffectSewageTreatment(pv.getboolean(pid, "et-"));
				fv.setEffectCleaningTeam(pv.getboolean(pid, "ec-"));
				fv.setEffectCouncil(pv.getboolean(pid, "efc-"));
				fv.setEffectHardBottom(pv.getboolean(pid, "eh-"));
				fv.setEffectThroughWater(pv.getboolean(pid, "etw-"));
				fv.setEffectOther(pv.getString(pid, "eo-"));
				fv.setNextStagePlanning(pv.getString(pid, "ds-"));
				fv.setVillage(fc);
				fv.setCountyReport(report);
				entityManager.merge(fv);
				
			}
			Long rid=report.getId();
			report.setProjectProgress(pv.getDouble(rid, "gpp-"));
			report.setPlanningProgress(pv.getDouble(rid, "gpr-"));
			report.setBiddingProgress(pv.getDouble(rid, "gbp-"));
			report.setInformant(pv.getString(rid, "im-"));
			report.setContact(pv.getString(rid, "ct-"));
			report.setReportingAt(pv.getDate(rid, "at-"));
			report.setStatus(1);
			entityManager.merge(report);
			
		}else{
			for(FelicityVillageReportEntity fv : report.getItems()){
				FelicityVillageEntity fc=fv.getVillage();
				if(null==fc){
					fc=new FelicityVillageEntity();
					fv.setVillage(fc);
				}
				Long pid = fv.getId();
				fv.setConstructionArea(pv.getDouble(pid, "co-"));
				fv.setHouseholdCount(pv.getInt(pid, "ho-"));
				fv.setPopulation(pv.getInt(pid, "po-"));
				fv.setConstructionCharacteristic(pv.getString(pid, "con-"));
				fv.setProjectCount(pv.getInt(pid, "pr-"));
				fv.setProjectProgress(pv.getDouble(pid, "pro-"));
				fv.setInvestmentBudget(pv.getDouble(pid, "in-"));
				fv.setInvestmentCompleted(pv.getDouble(pid, "inv-"));
				fv.setFundsTotal(pv.getDouble(pid, "fu-"));
				fv.setFundsProvince(pv.getDouble(pid, "fun-"));
				fv.setFundsCity(pv.getDouble(pid, "funs-"));
				fv.setFundsCounty(pv.getDouble(pid, "funt-"));
				fv.setFundsTown(pv.getDouble(pid, "funv-"));
				fv.setFundsVillage(pv.getDouble(pid, "fundv-"));
				fv.setFundsMasses(pv.getDouble(pid, "funm-"));
				fv.setFundsSociety(pv.getDouble(pid, "funs-"));
				fv.setFundsOther(pv.getDouble(pid, "funo-"));
				fv.setPlanningCompleted(pv.getboolean(pid, "pc-"));
				fv.setProjectProgress(pv.getDouble(pid, "pp-"));
				fv.setBiddingCompleted(pv.getboolean(pid, "bc-"));
				fv.setBiddingProgress(pv.getDouble(pid, "bp-"));
				fv.setEffectRemediation(pv.getboolean(pid, "er-"));
				fv.setEffectUniformStyle(pv.getboolean(pid, "eu-"));
				fv.setEffectSolveGarbage(pv.getboolean(pid, "es-"));
				fv.setEffectSewageTreatment(pv.getboolean(pid, "et-"));
				fv.setEffectCleaningTeam(pv.getboolean(pid, "ec-"));
				fv.setEffectCouncil(pv.getboolean(pid, "efc-"));
				fv.setEffectHardBottom(pv.getboolean(pid, "eh-"));
				fv.setEffectThroughWater(pv.getboolean(pid, "etw-"));
				fv.setEffectOther(pv.getString(pid, "eo-"));
				fv.setNextStagePlanning(pv.getString(pid, "ds-"));
				fv.setVillage(fc);
				fv.setCountyReport(report);
				entityManager.merge(fv);
			}
			Long rid=report.getId();
			report.setProjectProgress(pv.getDouble(rid, "gpp-"));
			report.setPlanningProgress(pv.getDouble(rid, "gpr-"));
			report.setBiddingProgress(pv.getDouble(rid, "gbp-"));
			report.setInformant(pv.getString(rid, "im-"));
			report.setContact(pv.getString(rid, "ct-"));
			report.setReportingAt(pv.getDate(rid, "at-"));
			report.setStatus(0);
			entityManager.merge(report);
		}
		
	}

	@Override
	public List<FelicityCountyReportEntity> statistics(int startAt, int endAt, String inids) {
		if(inids.isEmpty())return Collections.emptyList();
		String ql = "select x from FelicityCountyReportEntity x where x.county.id in(" + inids + ")";
		if(startAt > 0){
			ql += " and x.annual*100+x.month>=" + startAt;
		}
		if(endAt > 0){
			ql += " and x.annual*100+x.month<=" + endAt;
		}
		return __list(FelicityCountyReportEntity.class, ql);
	}

	@Override
	public List<FelicityCountyEntity> byCity(IOperator user) {
		UnitEntity unit = entityManager.find(UnitEntity.class, user.getOwnerId());
		Long pid = unit.getPlace().getId();
		String ql = "select x from FelicityCountyEntity x where x.cityId=? and x.deleted=false and x.status>=0";
		return __list(FelicityCountyEntity.class, ql, pid);
	}

	@Override
	public List<FelicityCountyEntity> byCounty(IOperator user) {
		String ql = "select x from FelicityCountyEntity x where x.ownerId=? and x.deleted=false and x.status>=0";
		return __list(FelicityCountyEntity.class, ql, user.getOwnerId());
	}

	@Override
	public List<FelicityCountyEntity> byProvince(IOperator user) {
		String ql = "select x from FelicityCountyEntity x where x.deleted=false and x.status>=0";
		return __list(FelicityCountyEntity.class, ql);
	}


	@Override
	@Transactional
	public void auditReject(OperateEvent event, Long id, String auditContent,String auditName,Date audiAt) {
		if(StringHelper.isEmpty(auditName)){
			throw new RuntimeException("审核人名称不能为空!");
		}
		if(null == audiAt){
			throw new RuntimeException("审核时间不能为空!");
		}
		if(StringHelper.isEmpty(auditContent)){
			throw new RuntimeException("请输入驳回原因进行说明!");
		}
		FelicityCountyReportEntity fcre = __get(id);
		fcre.setStatus(3);
		fcre.setAuditAt(audiAt);
		fcre.setAuditName(auditName);
		fcre.setAuditContent(auditContent);
		entityManager.merge(fcre);
	}

	@Override
	@Transactional
	public void auditPass(OperateEvent event, Long id,String auditContent,String auditName,Date audiAt) {
		if(StringHelper.isEmpty(auditName)){
			throw new RuntimeException("审核人名称不能为空!");
		}
		if(null == audiAt){
			throw new RuntimeException("审核时间不能为空!");
		}
		FelicityCountyReportEntity fcre = __get(id);
		fcre.setStatus(1);
		fcre.setAuditAt(audiAt);
		fcre.setAuditName(auditName);
		fcre.setAuditContent(auditContent);
		
		//处理县
		FelicityCountyEntity county = fcre.getCounty();
		county.sync(fcre);
		for(FelicityVillageReportEntity fvr : fcre.getItems()){
			fvr.getVillage().sync(fvr);
		}
		
		entityManager.merge(fcre);
	}

}
