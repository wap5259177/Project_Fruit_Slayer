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
 * 建设成效 -> 照片管理
 * @author ocean~
 */
@Controller
@RequestMapping("s/cl/pm")
public class PhotoManagementController extends AbstractGridController<BaseRuralEntity, BaseRuralItem> {

	private BaseRuralService baseRuralService;

	private String dialog;
	@Override
	public void afterPropertiesSet() throws Exception {
		dialog = toUrl("/s/ma/media/0-manager.image");
		super.afterPropertiesSet();
	}

//	@Autowired
//	private FileService fileService;
	@Autowired
	protected PhotoManagementController(BaseRuralService baseRuralService) {
		super(baseRuralService);
		this.baseRuralService = baseRuralService;
	}

	@Override
	@GridStandardDefinition(deleteOperation = false, autoOperation = false)
	@QueryExpression("x.deleted=0")
	protected BaseRuralService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button("图片管理", dialog, ButtonEventType.DIALOG);
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
