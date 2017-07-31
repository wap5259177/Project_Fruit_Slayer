package cn.bonoon.controllers.quarter;

import cn.bonoon.core.CityQuarterReportService;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.handler.impl.StandardAutoManager;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.handlers.AbstractOperateFormHandler;

public class CityQuarterAuditHandler extends AbstractOperateFormHandler<ModelAreaQuarterItem>{

	private final CityQuarterReportService cityQuarterReportService;
	
	public CityQuarterAuditHandler(int operand,
			CityQuarterReportService cityQuarterReportService,
			StandardAutoManager initializer, Class<?> editClass)
			throws Exception {
		super(operand, cityQuarterReportService, initializer, editClass);
		this.cityQuarterReportService = cityQuarterReportService;
	}

	@Override
	protected Object internalExecute(OperateEvent event, ObjectEditor editor) {
		ModelAreaQuarterReportAuditor fcra = (ModelAreaQuarterReportAuditor)editor;
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
