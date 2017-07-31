package cn.bonoon.controllers.village.pendingaudit;

import cn.bonoon.core.VillageApplicantService;
import cn.bonoon.entities.VillageApplicantEntity;
import cn.bonoon.handler.impl.StandardAutoManager;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.handlers.AbstractOperateFormHandler;

public class VillageApplicantAuditHandler extends AbstractOperateFormHandler<VillageApplicantEntity>{
	private final VillageApplicantService applicantService;
	public VillageApplicantAuditHandler(int operand,
			VillageApplicantService applicantService,
			StandardAutoManager initializer, Class<?> editClass)
			throws Exception {
		super(operand, applicantService, initializer, editClass);
		this.applicantService = applicantService;
	}

	@Override
	protected Object internalExecute(OperateEvent event, ObjectEditor editor) {
		VillageApplicantAuditor taa = (VillageApplicantAuditor)editor;
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
