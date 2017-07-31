package cn.bonoon.controllers.felicity.countyreport;

import cn.bonoon.core.FelicityCountyReportService;
import cn.bonoon.entities.FelicityCountyReportEntity;
import cn.bonoon.handler.impl.StandardAutoManager;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.handlers.AbstractOperateFormHandler;

public class FelicityCountyReportAuditHandler extends AbstractOperateFormHandler<FelicityCountyReportEntity>{

	private final FelicityCountyReportService felicityCountyReportService;
	
	public FelicityCountyReportAuditHandler(int operand,
			FelicityCountyReportService felicityCountyReportService,
			StandardAutoManager initializer, Class<?> editClass)
			throws Exception {
		super(operand, felicityCountyReportService, initializer, editClass);
		this.felicityCountyReportService = felicityCountyReportService;
	}

	@Override
	protected Object internalExecute(OperateEvent event, ObjectEditor editor) {
		FelicityCountyReportAuditor fcra = (FelicityCountyReportAuditor)editor;
		if(event.is("applicant-reject")){
			//驳回
			felicityCountyReportService.auditReject(event, fcra.getId(), fcra.getAuditContent(),fcra.getAuditName(),fcra.getAuditAt());
			
		}else{
			//通过审核
			felicityCountyReportService.auditPass(event, fcra.getId(),fcra.getAuditContent(),fcra.getAuditName(),fcra.getAuditAt());
			
		}
		return null;
	}

	
}
