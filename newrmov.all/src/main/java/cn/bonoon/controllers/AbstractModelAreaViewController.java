package cn.bonoon.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.controllers.inofs.BaseIndustryAreaInfo;
import cn.bonoon.controllers.inofs.BaseModelAreaInfo;
import cn.bonoon.controllers.inofs.BaseNewRuralInfo;
import cn.bonoon.controllers.inofs.BasePeripheralRuralInfo;
import cn.bonoon.controllers.inofs.BaseProjectInfo;
import cn.bonoon.core.IndustryAreaService;
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.core.NewRuralService;
import cn.bonoon.core.PeripheraRuralService;
import cn.bonoon.core.ProjectService;
import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.entities.ResidentialEnvironmentEntity;
import cn.bonoon.entities.RuralExpertGroupEntity;
import cn.bonoon.entities.RuralUnitEntity;
import cn.bonoon.entities.RuralWorkGroupEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.events.ReadEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;

public abstract class AbstractModelAreaViewController extends AbstractPanelController{

	@Autowired
	protected ModelAreaService modelAreaService;
	@Autowired
	protected NewRuralService newRuralService;
	@Autowired
	protected PeripheraRuralService peripheraRuralService;
	@Autowired
	protected ProjectService projectService;
	@Autowired
	protected IndustryAreaService industryAreaService;

	protected Class<? extends BaseModelAreaInfo> maType(){
		return BaseModelAreaInfo.class;
	}
	
	protected String __detail(HttpServletRequest request, ModelAndView model, LogonUser user, Long id){
		model.addObject("readonly", "readonly='readonly'");
		model.addObject("disabled", "disabled='true'");
		
		try{
			ReadEvent event = new ReadEvent(applicationContext, request, user);
			event.setScope(VisibleScope.GLOBAL);
			//1.台账信息
			model.addObject("form", modelAreaService.get(event, id, maType()));
			
			//2.核心村
			//TODO 这里需要再处理
//			Map<Object, Object[]> maps = new HashMap<>();
//			for(Object[] it : modelAreaService.getNewRurals(id)){
//				Object vid = it[3];
//				Object[] vs = maps.get(vid);
//				if(null == vs){
//					vs = new Object[3];
//					vs[0] = vid;
//					vs[1] = it[2];
//					vs[2] = new ArrayList<Object>();
//					maps.put(vid, vs);
//				}
//				((List<Object>)vs[2]).add(it);
//			}
			//List<Object[]> nrs = modelAreaService.getNewRurals(id);
			model.addObject("nrs", modelAreaService.getNewRurals(id));
			
			//3.非主体村
//			List<Object[]> prs = modelAreaService.getPeripheralRurals(id);
			model.addObject("prs", modelAreaService.getPeripheralRurals(id));
			
			//4.产业发展情况
			List<Object[]> ias = modelAreaService.getIndustries(id);
			model.addObject("ias", ias);
			
			//5.工程项目
			List<Object[]> pjs = modelAreaService.getProjects(id);
			model.addObject("pjs", pjs);
			
			//6.综合整治
			List<ResidentialEnvironmentEntity> listRes = modelAreaService.getResidentialEnvironments(id);
			model.addObject("listRes", listRes);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "applicant/area/view-detail";
	}

	@RequestMapping(value = "!{key}/view.detail", method = GET)
	public ModelAndView detail(HttpServletRequest request, Long id){
		ModelAndView model = new ModelAndView();
		model.addObject("layout", "layout-empty.vm");
		LogonUser user = getUser();
		model.setViewName(__detail(request, model, user, id));
		return model;
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
			BaseNewRuralInfo bnr = newRuralService.get(event, id, BaseNewRuralInfo.class);

//			NewRuralService newRuralService = (NewRuralService) service;
			AdministrationCoreRuralEntity are = newRuralService.getAdministrationRural(user, id);
			
			List<RuralWorkGroupEntity> wgs;
			List<RuralUnitEntity> rus;
			List<RuralExpertGroupEntity> egs;
			if(are !=null){
				bnr.reset(are);
				wgs = newRuralService.workGroupsByAdminRural(are.getId());
				rus = newRuralService.ruralUnitsByAdminRural(are.getId());
				egs = newRuralService.experGroupByAdminRural(are.getId());
			}else{
				wgs = newRuralService.workGroups(id);
				rus = newRuralService.ruralUnits(id);
				egs = newRuralService.expertGroups(id);
			}
			model.addAttribute("form", bnr);
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
