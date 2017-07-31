package cn.bonoon.services;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.controllers.project.report.crbuild.CRBuildQuarterDetail;
import cn.bonoon.core.CRBuildQuarterReportService;
import cn.bonoon.core.QuarterReportStatus;
import cn.bonoon.entities.ruralBuild.ModelAreaCRBuildQuarterItem;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.AbstractSearchService;

@Service
@Transactional(readOnly = true)
public class CRBuildQuarterReportServiceImpl extends AbstractSearchService<ModelAreaCRBuildQuarterItem>implements CRBuildQuarterReportService{
	//驳回
	@Override
	public void toPass(IOperator opt, Long id,String content) {
		
		ModelAreaCRBuildQuarterItem item = __get(id);
		item.setStatus(QuarterReportStatus.REJECT);
		if(content==null)content="";
		item.setAuditContent(content);
		item.setAuditName(opt.getDisplayName());
		item.setAuditAt(new Date());
		 //开始上报这里，先不做处理先。之后填报以自然村为单位时可能会有所修改
		entityManager.flush();
		entityManager.merge(item);
		
	}
	//审核成功的操作
	@Override
	public void toSuccess(IOperator opt, Long id, String content) {
		ModelAreaCRBuildQuarterItem item = __get(id);
		item.setStatus(QuarterReportStatus.FINISH);
		if(content==null)content="";
		item.setAuditContent(content);
		item.setAuditName(opt.getDisplayName());
		item.setAuditAt(new Date());
		 //开始上报这里，先不做处理先。之后填报以自然村为单位时可能会有所修改
		entityManager.flush();
		entityManager.merge(item);	
	}
	
	/*
	 * 开始上报
	 */
	@Override
	@Transactional
	public void toReport(IOperator opt, Long id) {
		ModelAreaCRBuildQuarterItem item = __get(id);
		item.setStatus(QuarterReportStatus.EDIT);
		 //开始上报这里，先不做处理先。之后填报以自然村为单位时可能会有所修改
		entityManager.merge(item);
	}
	
	
	@Override
	@Transactional
	public void updateItem(CRBuildQuarterDetail detail) {
		ModelAreaCRBuildQuarterItem item = __get(detail.getId());
		item = item.copy(detail);
		entityManager.merge(item);
		//这里不要统计先了
//		ModelAreaCRBuildQuarterBatch batch = item.getBatch();
//		ModelAreaCRBuildQuarterEntity quarter = batch.getQuarter();
//		quarter = quarter.subtract(batch);
//		batch = batch.subtract(item);
//		entityManager.merge(item);
//		batch = batch.add(item);
//		entityManager.merge(batch);
//		quarter = quarter.add(batch);
//		entityManager.merge(quarter);
		
	}

	

	@Override
	@Transactional
	public void toFinish(Long id) {
		ModelAreaCRBuildQuarterItem item = __get(id);
		item.setStatus(QuarterReportStatus.AUDIT);
		entityManager.merge(item);
		
		
	}

	    @Override
	public void updateCRBuildQuarterItemStatus(Long id,String returnResion) {
			__exec("update ModelAreaCRBuildQuarterItem set status=3,auditContent='"+returnResion+"' where id=?",id);
		}

		


		
	
	//通过ownerId  annual  period  拿到一个 item
//	@Override
//	public ModelAreaQuarterItem getMaqitem(Long ownerId, int annual, int period) {
//		String ql = "select x from ModelAreaQuarterItem x where x.status=1 and x.modelArea.ownerId=? and x.batch.quarter.annual=? and x.batch.quarter.period=?";
//		return __first(ModelAreaQuarterItem.class, ql, ownerId,annual,period);
//	}

	


//	@Override
//	public List<ModelAreaQuarterItem> getUrges(IOperator user) {
//		//片区未上报备案 (x.modelArea.status:-1),季报已经完成(x.status=1),季报在审核(x.status=4)  
//		String ql = "select x from ModelAreaQuarterItem x where x.modelArea.ownerId=? and  x.urge>0 and x.status not in(1,4) and x.modelArea.status<>-1 and x.modelArea.deleted=false";
//		return __list(ModelAreaQuarterItem.class, ql, user.getOwnerId());
//	}
//
//	@Override
//	public List<ModelAreaQuarterItem> getNeedReport(IOperator user) {
//		String ql = "select x from ModelAreaQuarterItem x where x.modelArea.ownerId=? and x.batch.quarter.startAt<=? and x.modelArea.status<>-1 and x.modelArea.deleted=false";
//		return __list(ModelAreaQuarterItem.class, ql, user.getOwnerId(), new Date());
//	}

}
