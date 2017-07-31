package cn.bonoon.controllers.area.tocommit;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.controllers.area.ModelAreaDetail;
import cn.bonoon.controllers.area.ModelAreaItem;
import cn.bonoon.controllers.inofs.BaseIndustryAreaInfo;
import cn.bonoon.controllers.inofs.BaseNewRuralInfo;
import cn.bonoon.controllers.inofs.BasePeripheralRuralInfo;
import cn.bonoon.controllers.inofs.BaseProjectInfo;
import cn.bonoon.core.IndustryAreaService;
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.core.NewRuralService;
import cn.bonoon.core.PeripheraRuralService;
import cn.bonoon.core.ProjectService;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.RuralExpertGroupEntity;
import cn.bonoon.entities.RuralUnitEntity;
import cn.bonoon.entities.RuralWorkGroupEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.events.ReadEvent;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.ButtonRefreshType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.models.JsonResult;

@Controller
@RequestMapping("s/cl/area/tocommit")
public class ModelAreaTocommitController extends AbstractGridController<ModelAreaEntity, ModelAreaItem>{
	private ModelAreaService modelAreaService;
	@Autowired
	protected NewRuralService newRuralService;
	@Autowired
	protected PeripheraRuralService peripheraRuralService;
	@Autowired
	protected ProjectService projectService;
	@Autowired
	protected IndustryAreaService industryAreaService;

	@Autowired
	protected ModelAreaTocommitController(ModelAreaService modelAreaService) {
		super(modelAreaService);
		this.modelAreaService = modelAreaService;
	}

	@Override
	@GridStandardDefinition(
		detailClass = ModelAreaDetail.class, 
		autoOperation = false, 
		deleteOperation = false
	)
	@QueryExpression("x.status in(0, 3)")
	protected ModelAreaService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button("提交", "index.submit", ButtonEventType.GET, ButtonRefreshType.FINISH);
		return modelAreaService;
	}
	
	@ResponseBody
	@RequestMapping(value = "!{key}/index.submit", method = GET)
	public JsonResult submit(Long id){
		try{
			modelAreaService.submit(id, getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.DOMAIN;
	}
	@RequestMapping(value = "!{key}/{id}!pr.detail", method = GET)
	public String pr(HttpServletRequest request, Model model, @PathVariable("id") Long id, Integer o){
		model.addAttribute("layout", "layout-empty.vm")
			.addAttribute("readonly", "readonly='readonly'")
			.addAttribute("disabled", "disabled='true'")
			.addAttribute("o", o);
		LogonUser user = getUser();
		try{
			ReadEvent event = new ReadEvent(applicationContext, request, user);
			event.setScope(VisibleScope.GLOBAL);
			model.addAttribute("form", peripheraRuralService.get(event, id, BasePeripheralRuralInfo.class));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "applicant/area/peripheralrural-detail";
	}

	@RequestMapping(value = "!{key}/{id}!nr.detail", method = GET)
	public String nr(HttpServletRequest request, Model model, @PathVariable("id") Long id, Integer o){
		model.addAttribute("layout", "layout-empty.vm")
			.addAttribute("readonly", "readonly='readonly'")
			.addAttribute("disabled", "disabled='true'")
			.addAttribute("o", o);
		LogonUser user = getUser();
		try{
			ReadEvent event = new ReadEvent(applicationContext, request, user);
			event.setScope(VisibleScope.GLOBAL);
			model.addAttribute("form", newRuralService.get(event, id, BaseNewRuralInfo.class));
			
			List<RuralWorkGroupEntity> wgs = newRuralService.workGroups(id);
			List<RuralExpertGroupEntity> egs = newRuralService.expertGroups(id);
			List<RuralUnitEntity> rus = newRuralService.ruralUnits(id);
			model.addAttribute("wgs", wgs).addAttribute("egs", egs).addAttribute("rus", rus);

		}catch (Exception e) {
			e.printStackTrace();
		}
		return "applicant/area/newrural-detail";
	}

	@RequestMapping(value = "!{key}/{id}!ia.detail", method = GET)
	public String ia(HttpServletRequest request, Model model, @PathVariable("id") Long id, Integer o){
		model.addAttribute("layout", "layout-empty.vm")
			.addAttribute("readonly", "readonly='readonly'")
			.addAttribute("disabled", "disabled='true'")
			.addAttribute("o", o);
		LogonUser user = getUser();
		try{
			ReadEvent event = new ReadEvent(applicationContext, request, user);
			event.setScope(VisibleScope.GLOBAL);
			model.addAttribute("form", industryAreaService.get(event, id, BaseIndustryAreaInfo.class));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "applicant/area/areaindustry-detail";
	}

	@RequestMapping(value = "!{key}/{id}!pj.detail", method = GET)
	public String pj(HttpServletRequest request, Model model, @PathVariable("id") Long id, Integer o){
		model.addAttribute("layout", "layout-empty.vm")
			.addAttribute("readonly", "readonly='readonly'")
			.addAttribute("disabled", "disabled='true'")
			.addAttribute("o", o);
		LogonUser user = getUser();
		try{
			ReadEvent event = new ReadEvent(applicationContext, request, user);
			event.setScope(VisibleScope.GLOBAL);
			model.addAttribute("form", projectService.get(event, id, BaseProjectInfo.class));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "applicant/area/areaproject-detail";
	}
}
