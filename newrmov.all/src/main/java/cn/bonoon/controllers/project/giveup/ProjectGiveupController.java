package cn.bonoon.controllers.project.giveup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.project.ProjectItem;
import cn.bonoon.core.ProjectService;
import cn.bonoon.entities.ProjectEntity;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.web.controllers.AbstractGridController;

@Controller
@RequestMapping("s/cl/project/giveup")
public class ProjectGiveupController extends AbstractGridController<ProjectEntity, ProjectItem>{

	private ProjectService projectService;
	@Autowired
	protected ProjectGiveupController(ProjectService projectService) {
		super(projectService);
		this.projectService = projectService;
	}

	@Override
	@QueryExpression("x.status=-1")
	protected ProjectService initLayoutGrid(LayoutGridRegister register) throws Exception {
		return projectService;
	}

}
