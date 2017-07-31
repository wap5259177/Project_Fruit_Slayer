package cn.bonoon.controllers.project.report.crbuild;

import cn.bonoon.core.CityCRBuildQuarterReportService;
import cn.bonoon.entities.ruralBuild.ModelAreaCRBuildQuarterItem;
import cn.bonoon.handler.impl.StandardAutoManager;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.handlers.AbstractOperateFormHandler;

public class CityCRBuildQuarterAuditHandler extends AbstractOperateFormHandler<ModelAreaCRBuildQuarterItem>{

	private final CityCRBuildQuarterReportService cityQuarterReportService;
	
	public CityCRBuildQuarterAuditHandler(int operand,
			CityCRBuildQuarterReportService cityQuarterReportService,
			StandardAutoManager initializer, Class<?> editClass)
			throws Exception {
		super(operand, cityQuarterReportService, initializer, editClass);
		this.cityQuarterReportService = cityQuarterReportService;
	}

	@Override
	protected Object internalExecute(OperateEvent event, ObjectEditor editor) {
		ModelAreaCRBuildQuarterReportAuditor fcra = (ModelAreaCRBuildQuarterReportAuditor)editor;
		if(event.is("applicant-reject")){
			//驳回
			cityQuarterReportService.auditReject(event, fcra.getId(), fcra.getAuditContent(),fcra.getAuditName(),fcra.getAuditAt());
			
		}else{
			//通过审核
			cityQuarterReportService.auditPass(event, fcra.getId(),fcra.getAuditContent(),fcra.getAuditName(),fcra.getAuditAt());
			
		}
		return null;
	}

	
}
