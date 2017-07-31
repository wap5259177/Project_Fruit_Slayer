package cn.bonoon.controllers.industry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.IndustryAreaService;
import cn.bonoon.entities.IndustryAreaEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;

@Controller
@RequestMapping("s/cl/area/industry")
public class IndustryAreaController extends AbstractGridController<IndustryAreaEntity, IndustryAreaItem>{
	private final IndustryAreaService industryAreaService;
	@Autowired
	protected IndustryAreaController(IndustryAreaService industryAreaService) {
		super(industryAreaService);
		this.industryAreaService = industryAreaService;
	}

	@Override
	@GridStandardDefinition(
			detailClass = IndustryAreaDetail.class,
			insertClass = IndustryAreaEditor.class, 
			updateClass = IndustryAreaEditor.class,
					editStatus = {0, 3}
			)
	//@QueryExpression("x.rural.modelArea.status=0")
	protected IndustryAreaService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button("打印", "index.print", ButtonEventType.JUMP).ordinal(50);
		return industryAreaService;
	}
	
	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.DOMAIN;
	}
	
	@RequestMapping("!{key}/index.print")
	public String print(HttpServletRequest request, Long id, String gridid, Model model) {
		
		IndustryAreaEntity entity =industryAreaService.get(id);
		model.addAttribute("form",entity);
		model.addAttribute("title","连片示范建设工程产业发展情况统计表");
		model.addAttribute("readonly", "readonly='readonly'").addAttribute("disabled", "disabled='true'");
		return "applicant/areaindustry-view-view";
	}
	
}
