//package cn.bonoon.controllers.project.type;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import cn.bonoon.core.ProjectTypeService;
//import cn.bonoon.entities.ProjectTypeEntity;
//import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
//import cn.bonoon.kernel.web.controllers.AbstractGridController;
//
//@Controller
//@RequestMapping("s/data/project/type")
//public class ProjectTypeController extends AbstractGridController<ProjectTypeEntity, ProjectTypeItem>{
//
//	private ProjectTypeService projectTypeService;
//	@Autowired
//	protected ProjectTypeController(ProjectTypeService projectTypeService) {
//		super(projectTypeService);
//		this.projectTypeService = projectTypeService;
//	}
//
//	
//	@Override
//	@GridStandardDefinition(
//			insertClass = ProjectTypeEditor.class, 
//			updateClass = ProjectTypeEditor.class, 
//			detailClass = ProjectTypeDetail.class)
//	protected ProjectTypeService initLayoutGrid(LayoutGridRegister register) throws Exception {
//		return projectTypeService;
//	}
//
//	@Override
//	protected Class<ProjectTypeItem> itemClass() {
//		return ProjectTypeItem.class;
//	}
//	
//}
