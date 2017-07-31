package cn.bonoon.core;

import java.util.Date;

import cn.bonoon.entities.ruralBuild.ModelAreaCRBuildQuarterItem;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.services.GenericService;

public interface CityCRBuildQuarterReportService  extends GenericService<ModelAreaCRBuildQuarterItem>{
	void auditPass(OperateEvent event, Long id,String auditContent,String auditName,Date auditAt);
	void auditReject(OperateEvent event, Long id, String auditContent,String auditName,Date auditAt);
//	List<Object[]> getQuarterReport(Long ownerId, String batchName,int period,int annual);
//	List<Object[]> getQuarterBatch(Long ownerId, String batchName, int period,int annual);
	
	
	
////	String getCityN(Long ownerId);
//	ModelAreaQuarterItem getItem(Long ownerId,int annual, String batchName, int period);
////	List<Object[]> getQuarterReport(String batchName, int period, int annual);
//	List<ModelAreaQuarterItem> getQuarterReport(String batchName, int period, int annual);
//	ModelAreaQuarterItem getItem(String batchName,int annual,int period);
//	ModelAreaQuarterBatch getBatch(String batchName, int annual, int period);
}
