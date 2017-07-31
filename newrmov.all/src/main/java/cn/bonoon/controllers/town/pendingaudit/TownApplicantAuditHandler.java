package cn.bonoon.controllers.town.pendingaudit;

import cn.bonoon.core.TownApplicantService;
import cn.bonoon.entities.TownApplicantEntity;
import cn.bonoon.handler.impl.StandardAutoManager;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.handlers.AbstractOperateFormHandler;

public class TownApplicantAuditHandler extends AbstractOperateFormHandler<TownApplicantEntity>{
	private final TownApplicantService applicantService;
	public TownApplicantAuditHandler(int operand,
			TownApplicantService applicantService,
			StandardAutoManager initializer, Class<?> editClass)
			throws Exception {
		super(operand, applicantService, initializer, editClass);
		this.applicantService = applicantService;
	}

	@Override
	protected Object internalExecute(OperateEvent event, ObjectEditor editor) {
		TownApplicantAuditor taa = (TownApplicantAuditor)editor;
		if(event.is("applicant-reject")){
			//驳回
			applicantService.auditReject(event, taa.getId(), taa.getRejectContent());
		}else{
			//通过审核
			applicantService.auditPass(event, taa.getId(), taa.getOpinion2(), taa.getOpinionAt2(), taa.getOpinion3(), taa.getOpinionAt3());
		}
		return null;
	}
}
