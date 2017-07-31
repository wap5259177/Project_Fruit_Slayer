package cn.bonoon.controllers.area.pendingaudit;

import cn.bonoon.core.ModelAreaService;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.handler.impl.StandardAutoManager;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.handlers.AbstractOperateFormHandler;

public class ModelAreaAuditHandler extends AbstractOperateFormHandler<ModelAreaEntity>{

	private final ModelAreaService modelAreaService;

	public ModelAreaAuditHandler(int operand,
			ModelAreaService modelAreaService,
			StandardAutoManager initializer, Class<?> editClass)
			throws Exception {
		super(operand, modelAreaService, initializer, editClass);
		this.modelAreaService = modelAreaService;
	}


	@Override
	protected Object internalExecute(OperateEvent event, ObjectEditor editor) {
		ModelAreaAuditor maa = (ModelAreaAuditor) editor;
		if(event.is("applicant-reject")){
			//驳回
			modelAreaService.auditReject(event, maa.getId());
		} else {
			//通过审核
			modelAreaService.auditPass(event, maa.getId());
		}
		return null;
	}
}
