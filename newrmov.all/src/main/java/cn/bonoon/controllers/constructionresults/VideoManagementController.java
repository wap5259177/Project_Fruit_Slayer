package cn.bonoon.controllers.constructionresults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.BaseRuralService;
import cn.bonoon.entities.BaseRuralEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;


/**
 * 建设成效 -> 视频管理
 */
@Controller
@RequestMapping("s/cl/vm")
public class VideoManagementController extends AbstractGridController<BaseRuralEntity, BaseRuralItem>{
	
	private BaseRuralService baseRuralService;
//	@Autowired
//	private FileService fileService;
	@Autowired
	protected VideoManagementController(BaseRuralService baseRuralService) {
		super(baseRuralService);
		this.baseRuralService = baseRuralService;
	}
	private String dialog;
	@Override
	public void afterPropertiesSet() throws Exception {
		dialog = toUrl("/s/ma/media/0-manager.vedio");
		super.afterPropertiesSet();
	}
	@Override
	@GridStandardDefinition(deleteOperation = false, autoOperation = false)
	@QueryExpression("x.deleted=0")
	protected BaseRuralService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button("视频管理", dialog, ButtonEventType.DIALOG);
		return baseRuralService;
	}

	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.DOMAIN;
	}
//	@RequestMapping(value = "!{key}/file.detail", method = GET)
//	public ModelAndView detail(HttpServletRequest request, final Long id) {
//		return FileHelper.detail(request, fileService, id);
//	}
}
