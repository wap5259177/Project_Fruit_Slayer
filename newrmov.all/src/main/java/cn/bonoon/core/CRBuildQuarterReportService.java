package cn.bonoon.core;


import cn.bonoon.controllers.project.report.crbuild.CRBuildQuarterDetail;
import cn.bonoon.entities.ruralBuild.ModelAreaCRBuildQuarterItem;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.SearchService;

public interface CRBuildQuarterReportService extends SearchService<ModelAreaCRBuildQuarterItem>{
	
	
	/**
	 * 驳回操作
	 */
	void toPass(IOperator opt, Long id,String content);
	/**
	 * 审核通过的操作
	 */
	void toSuccess(IOperator opt, Long id,String content);
	
	
	void toReport(IOperator opt, Long id);

	void updateItem(CRBuildQuarterDetail detail);

	void toFinish(Long id);
	
    void updateCRBuildQuarterItemStatus(Long id,String returnResion);

//	void refreshRural(LogonUser user, Long id);
//	
//	ModelAreaQuarterNaturalRural getQnr(Long id);
//
//	void update(ModelAreaQuarterNaturalRural qnr);
//
//	void updateItem(ModelAreaQuarterItem item);
//
//	void toFinish(Long id);
//	
////	List<ModelAreaQuarterItem>getMaqitems(int count,Long id);
//	ModelAreaCRBuildQuarterItem getMaqitem(Long ownerId,int annual,int period);
//
//	
//	//以下两个是给首页使用的
//	List<ModelAreaQuarterItem> getUrges(IOperator user);
//	List<ModelAreaQuarterItem> getNeedReport(IOperator user);
}
