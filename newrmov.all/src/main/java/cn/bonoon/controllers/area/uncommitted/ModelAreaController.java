package cn.bonoon.controllers.area.uncommitted;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.area.ModelAreaDetailSimple;
import cn.bonoon.controllers.area.ModelAreaItem;
import cn.bonoon.controllers.area.ModelAreaUpdate;
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;

@Controller
@RequestMapping("s/cl/area/uncommitted")
public class ModelAreaController extends AbstractGridController<ModelAreaEntity, ModelAreaItem> {

	private ModelAreaService modelAreaService;
	@Autowired
	protected ModelAreaController(ModelAreaService modelAreaService) {
		super(modelAreaService);
		this.modelAreaService = modelAreaService;
	}
	
	@Override
	@GridStandardDefinition(
		deleteOperation=false,//去除删除按钮
	    //insertClass = ModelAreaEditor.class,//添加,修改按钮 
		detailClass = ModelAreaDetailSimple.class,
		updateClass = ModelAreaUpdate.class,
		editStatus = {0, 3}
	)
	@QueryExpression("x.status>=0")
	protected ModelAreaService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button("打印", "index.print", ButtonEventType.JUMP).ordinal(50);
		//这里+3个按钮：核心村、非主体村、产业发展
		return modelAreaService;
	}
	
	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.DOMAIN;
	}
	
	@RequestMapping("!{key}/index.print")
	public String print(HttpServletRequest request, Long id, String gridid, Model model) {
		
		ModelAreaEntity entity =modelAreaService.get(id);
		model.addAttribute("form",entity);
		model.addAttribute("title","示范片建设台账封面");
		model.addAttribute("readonly", "readonly='readonly'").addAttribute("disabled", "disabled='true'");
		return "applicant/modelarea-view-view";
	}
	
	
}
