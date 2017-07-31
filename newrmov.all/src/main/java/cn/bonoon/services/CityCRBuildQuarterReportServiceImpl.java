package cn.bonoon.services;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.CityCRBuildQuarterReportService;
import cn.bonoon.core.QuarterReportStatus;
import cn.bonoon.entities.ruralBuild.ModelAreaCRBuildQuarterItem;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.services.AbstractService;
import cn.bonoon.kernel.util.StringHelper;

@Service
@Transactional(readOnly = true)
public class CityCRBuildQuarterReportServiceImpl extends AbstractService<ModelAreaCRBuildQuarterItem> implements CityCRBuildQuarterReportService{

	@Override
	@Transactional
	public void auditPass(OperateEvent event, Long id, String auditContent,String auditName, Date auditAt) {
		if(StringHelper.isEmpty(auditName)){
			throw new RuntimeException("审核人名称不能为空!");
		}
		if(null == auditAt){
			throw new RuntimeException("审核时间不能为空!");
		}
		ModelAreaCRBuildQuarterItem maqe = __get(id);
		maqe.setStatus(QuarterReportStatus.FINISH);
		maqe.setAuditAt(auditAt);
		maqe.setAuditName(auditName);
		maqe.setAuditContent(auditContent);
		
		
		entityManager.merge(maqe);
	}

	@Override
	@Transactional
	public void auditReject(OperateEvent event, Long id, String auditContent,String auditName, Date auditAt) {
		if(StringHelper.isEmpty(auditName)){
			throw new RuntimeException("审核人名称不能为空!");
		}
		if(null == auditAt){
			throw new RuntimeException("审核时间不能为空!");
		}
		if(StringHelper.isEmpty(auditContent)){
			throw new RuntimeException("请输入驳回原因进行说明!");
		}
		ModelAreaCRBuildQuarterItem maqe = __get(id);
		maqe.setStatus(QuarterReportStatus.REJECT);
		maqe.setAuditAt(auditAt);
		maqe.setAuditName(auditName);
		maqe.setAuditContent(auditContent);
		entityManager.merge(maqe);
	}
	

//	@Override
//	public ModelAreaQuarterItem getItem(Long ownerId,int annual, String batchName, int period) {
//		String ql1 = "select x.place.id from UnitEntity x where x.id=?";
//		Long pid = __first(Long.class, ql1, ownerId);
//		String ql = "select x" +
//				" from ModelAreaQuarterItem x " +
//				"where x.modelArea.cityId=? " +
//				"and x.batch.batchName=? " +
//				"and x.batch.quarter.period=? " +
//				"and x.batch.quarter.annual=?";//
//		return __first(ModelAreaQuarterItem.class, ql,pid,batchName,period,annual);
//	}
//	
//	@Override
//	public List<ModelAreaQuarterItem> getQuarterReport(String batchName,int period,int annual) {
//	
//		String ql = "select x from ModelAreaQuarterItem x where x.batch.batchName=? and x.batch.quarter.period=? and x.batch.quarter.annual=? order by x.modelArea.ordinalModel asc";//
////		List<ModelAreaQuarterItem> obj=__list(Object[].class,ql,batchName,period,annual);
//		return __list(ModelAreaQuarterItem.class,ql,batchName,period,annual);//,batch,period
//	}
//	@Override
//	public ModelAreaQuarterItem getItem(String batchName,int annual,int period) {
//		String ql = "select x" +
//				" from ModelAreaQuarterItem x " +
//				"where x.modelArea.cityId=? " +
//				"and x.batch.batchName=? " +
//				"and x.batch.quarter.period=? " +
//				"and x.batch.quarter.annual=?";//
//		return __first(ModelAreaQuarterItem.class, ql,batchName,annual,period);
//	}
//	@Override
//	public ModelAreaQuarterBatch getBatch(String batchName,int annual,int period) {
//		String ql = "select x " +
//				"from ModelAreaQuarterBatch x " +
//				"where x.batchName=? " +
//				"and x.quarter.period=? " +
//				"and x.quarter.annual=?";
//		return __first(ModelAreaQuarterBatch.class, ql,batchName,period,annual);
//	}
}
