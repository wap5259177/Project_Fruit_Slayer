package cn.bonoon.controllers.workplan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.WorkPlanService;
import cn.bonoon.entities.WorkPlanEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;

@Controller
@RequestMapping("s/work/plan")
public class WorkPlanController extends AbstractGridController<WorkPlanEntity, WorkPlanItem>{
	
	private  WorkPlanService workPlanService;
	
	@Autowired
	protected WorkPlanController(WorkPlanService workPlanService) {
		super(workPlanService);
		this.workPlanService = workPlanService;
	}

	@Override
	@GridStandardDefinition(
			detailClass = WorkPlanDetail.class,
			insertClass = WorkPlanEditor.class, 
			updateClass = WorkPlanEditor.class
			)
	protected WorkPlanService initLayoutGrid(LayoutGridRegister register) throws Exception {
		return workPlanService;
	}
	
	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.DOMAIN;
	}

}
