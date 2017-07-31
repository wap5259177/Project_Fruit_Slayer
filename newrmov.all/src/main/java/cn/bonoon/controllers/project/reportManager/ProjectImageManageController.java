package cn.bonoon.controllers.project.reportManager;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.core.ModelAreaService;
import cn.bonoon.core.ProjectService;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.models.DialogModel;
import cn.bonoon.util.ControllerUtil;
import cn.bonoon.util.FrontStyleCodeUtil;

/**
 * 省察看项目图片 分地区,按最近时间产生的项目
 * 
 * @creation 2017-6-2
 * 
 */
@Controller
@RequestMapping("s/pl/ma/project/projectImageManage")
public class ProjectImageManageController extends
		AbstractGridController<ModelAreaEntity, ModelAreaItemByImage> {
	//图片管理弹出框的默认宽高
	private int w = 1150;
	private int h = 600;
	private ModelAreaService modelAreaService;
	@Autowired
	private ProjectService projectService;

	@Autowired
	protected ProjectImageManageController(ModelAreaService modelAreaService) {
		super(modelAreaService);
		this.modelAreaService = modelAreaService;
	}

	@Override
	@GridStandardDefinition(deleteOperation = false)
	protected ModelAreaService initLayoutGrid(LayoutGridRegister register)
			throws Exception {
		register.button("项目图片管理", "view.projectImage", ButtonEventType.DIALOG);
		return modelAreaService;
	}

	/**
	 * 先显示某个片区最近增加的项目按时间[月,月+1]
	 */
	@RequestMapping(value = "!{key}/view.projectImage", method = GET)
	public ModelAndView projectImage_view(@PathVariable("key") String key,
			HttpServletRequest request, @RequestParam(value = "id") Long id,
			@RequestParam(value = "gridid", required = false) String gridid) {
		DialogModel model = new DialogModel("t_a_" + id, request);

		model.setTitle(modelAreaService.get(id).getName() + "项目图片管理");
		w = 1150;
		h = 600;
		model.addObject("mid", id);
		model.setSize(w, h);
		model.addObject("w", w);
		model.addObject("h", h);
		model.addObject("imageViewW", 80);
		model.addObject("imageViewH", "80");

		// FrontStyleCodeUtil
		// fscu=FrontStyleCodeUtil.getFrontStyleCodeUtilEntity();
		// model.addObject("FrontStyle",
		// FrontStyleCodeUtil.quoteStyle(fscu.getBootstrap_min_css()
		// + fscu.getBootstrap_theme_min_css()
		// + fscu.getJquery_min_js() + fscu.getBootstrap_min_js()));

		List<int[]> proMinMaxTime = projectService.getProjectTime(id);

		if (proMinMaxTime != null && proMinMaxTime.size() > 0) {
			model.addObject("projectMinMaxTime", proMinMaxTime);
			// 显示片区最近月增加的项目
			if (proMinMaxTime.size() == 2) {

				model.addObject("projectList", modelAreaService
						.recentAddProjectByModelArea(id,
								proMinMaxTime.get(1)[0],
								proMinMaxTime.get(1)[1], 0, w, h, null));
			} else {
				// 只有一个min月份则是只有该月有建项目
				model.addObject("projectList", modelAreaService
						.recentAddProjectByModelArea(id,
								proMinMaxTime.get(0)[0],
								proMinMaxTime.get(0)[1], 0, w, h, null));
			}

			return model.execute("project/projectImage-view");
		}
		model.addObject("error", "无项目");
		return model.execute("project/projectImage-view");
	}

	@RequestMapping("{key}/projectImageAjax")
	@ResponseBody
	public String projectImageAjax(Long id, int selectYear, int selectMonth,
			int selectMonthInterval, String projectName) {
		List<ProjectImageShowEntity> res = modelAreaService
				.recentAddProjectByModelArea(id, selectYear, selectMonth,
						selectMonthInterval, w, h, projectName);
		String result = ControllerUtil.getJSONString(res);
		return result;
	}

	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.GLOBAL;
	}
}
