package cn.bonoon.controllers.project;

import cn.bonoon.core.ProjectService;
import cn.bonoon.entities.ProjectEntity;
import cn.bonoon.kernel.web.controllers.AbstractGridController;

public abstract class AbstractProjectController extends AbstractGridController<ProjectEntity, ProjectItem>{

	protected ProjectService projectService;

	protected String dialog, dialogName = "项目图片";
	@Override
	public void afterPropertiesSet() throws Exception {
		dialog = toUrl("/s/ma/media/1-manager.image");
		super.afterPropertiesSet();
	}
	
	protected AbstractProjectController(ProjectService projectService) {
		super(projectService);
		this.projectService = projectService;
	} 

//	@Override
//	@QueryExpression("x.status=1")
//	@GridStandardDefinition(
//			detailClass = ProjectDetail.class,
//			deleteOperation = false, 
//			autoOperation = false)
//	protected ProjectService initLayoutGrid(LayoutGridRegister register) throws Exception {
//		register.button("项目图片", dialog, ButtonEventType.DIALOG);
//		//register.button("竣工", "finish.do", ButtonEventType.DIALOG);
//		return projectService;
//	}
	
//	@RequestMapping("finish.do")
//	public String finish(Long id){
//		return "";
//	}
}
