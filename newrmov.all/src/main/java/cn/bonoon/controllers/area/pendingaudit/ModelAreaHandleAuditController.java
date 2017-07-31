package cn.bonoon.controllers.area.pendingaudit;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.controllers.industry.IndustryAreaEditor;
import cn.bonoon.controllers.industry.IndustryAreaItem;
import cn.bonoon.controllers.newrural.NewRuralEditor;
import cn.bonoon.controllers.newrural.NewRuralItem;
import cn.bonoon.controllers.newrural.PeripheraRuralEditor;
import cn.bonoon.controllers.newrural.PeripheraRuralItem;
import cn.bonoon.controllers.project.ProjectEditor;
import cn.bonoon.controllers.project.ProjectItem;
import cn.bonoon.core.IndustryAreaService;
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.core.NewRuralService;
import cn.bonoon.core.PeripheraRuralService;
import cn.bonoon.core.ProjectService;
import cn.bonoon.core.ProjectTypeService;
import cn.bonoon.core.RuralExpertGroupService;
import cn.bonoon.core.RuralUnitService;
import cn.bonoon.core.RuralWorkGroupService;
import cn.bonoon.entities.IndustryAreaEntity;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.PeripheralRuralEntity;
import cn.bonoon.entities.ProjectEntity;
import cn.bonoon.entities.RuralExpertGroupEntity;
import cn.bonoon.entities.RuralUnitEntity;
import cn.bonoon.entities.RuralWorkGroupEntity;
import cn.bonoon.kernel.events.ReadEvent;
import cn.bonoon.kernel.support.models.Page;
import cn.bonoon.kernel.support.searcher.Finder;
import cn.bonoon.kernel.web.controllers.AbstractController;
import cn.bonoon.kernel.web.models.DialogModel;

@Controller
@RequestMapping("s/ml/area/pendingaudit")
public class ModelAreaHandleAuditController extends AbstractController {

	private NewRuralService newRuralService;
	private ProjectService projectService;
	private ProjectTypeService projectTypeService;
	private ModelAreaService modelAreaService;
	private RuralWorkGroupService ruralWorkGroupService;
	private RuralExpertGroupService ruralExpertGroupService;
	private RuralUnitService ruralUnitService;
	private PeripheraRuralService peripheraRuralService;
	private IndustryAreaService industryAreaService;
	private Long newId = 0L;

	@Autowired
	public ModelAreaHandleAuditController(NewRuralService newRuralService,
			ProjectService projectService,
			ProjectTypeService projectTypeService,
			ModelAreaService modelAreaService,
			RuralWorkGroupService ruralWorkGroupService,
			RuralExpertGroupService ruralExpertGroupService,
			RuralUnitService ruralUnitService,
			PeripheraRuralService peripheraRuralService,
			IndustryAreaService industryAreaService) {
		this.newRuralService = newRuralService;
		this.projectService = projectService;
		this.projectTypeService = projectTypeService;
		this.modelAreaService = modelAreaService;
		this.ruralWorkGroupService = ruralWorkGroupService;
		this.ruralExpertGroupService = ruralExpertGroupService;
		this.ruralUnitService = ruralUnitService;
		this.peripheraRuralService = peripheraRuralService;
		this.industryAreaService = industryAreaService;
	}

	/**
	 * 核心村datagrid
	 * 
	 * @param mid
	 * @param area_id
	 * @param request
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{mid}/base_village/search", method = {
			RequestMethod.GET, RequestMethod.POST })
	public Page base_village_search(@PathVariable("mid") String mid,
			Long id, HttpServletRequest request) {
		try {
			
			ReadEvent event = new ReadEvent(applicationContext, request, getUser());
			Finder fr = newRuralService.finder(event, NewRuralItem.class);
			 
			// 只选片区下的核心村
			fr.and("modelArea.id=?", id);
			return fr.getPage();
		} catch (Exception e) {
			e.printStackTrace();
			return Page.EMPTY;
		}
	}

	/**
	 * 非主体村datagrid
	 * 
	 * @param mid
	 * @param request
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{mid}/village/search", method = {
			RequestMethod.GET, RequestMethod.POST })
	public Page village_search(@PathVariable("mid") String mid,
			HttpServletRequest request, Long id) {
		try {
			ReadEvent event = new ReadEvent(applicationContext, request,
					getUser());
			Finder fr = peripheraRuralService.finder(event, PeripheraRuralItem.class);
			fr.and("modelArea.id=?", id);
			return fr.getPage();
		} catch (Exception e) {
			e.printStackTrace();
			return Page.EMPTY;
		}
	}

	/**
	 * 项目datagrid
	 * 
	 * @param mid
	 * @param request
	 * @param form
	 * @param area_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{mid}/project/search", method = {
			RequestMethod.GET, RequestMethod.POST })
	public Page project_search(@PathVariable("mid") String mid,
			HttpServletRequest request, Long id) {
		try {
			ReadEvent event = new ReadEvent(applicationContext, request,
					getUser());
			Finder fr = projectService.finder(event, ProjectItem.class);
			ModelAreaEntity e = modelAreaService.get(id);
			fr.and("x.areaName=? and x.status=?", e.getName(), 1);
			return fr.getPage();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Page.EMPTY;
		}
	}
	
	/**
	 * 核心村信息
	 * 
	 * @param request
	 * @param mid
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "!{mid}/base_village/detial", method = { POST, GET })
	public ModelAndView base_village_detial(HttpServletRequest request,
			@PathVariable("mid") String mid, Long id) {
		DialogModel model = new DialogModel(mid + "detial", mid, request);
		if (null != id) {
			NewRuralEntity entity = newRuralService.get(id);
			NewRuralEditor form = new NewRuralEditor();
//			form.set(entity);
			model.addObject("id", id);
			model.addObject("baseVillageInfo", form);
			
			StringBuilder sb = new StringBuilder();
			List<RuralWorkGroupEntity> l = ruralWorkGroupService.find(" and x.newRural.id=" + id);
			int i = 0;
			for(RuralWorkGroupEntity rwge : l) {
				sb.append("<tr><td><input disabled='disabled' type='hidden' name='workGroupID' value='"+ rwge.getId() +"' />"+ (++i) +"</td>")
					.append("<td><input disabled='disabled' style='width:80px;border:#ffffff;border-bottom: #000000 1px solid;text-align:center;' name='workName' type='text' value='"+ rwge.getWorkName() +"' /></td>")
					.append("<td colspan='2'><input disabled='disabled' name='unitJob' style='width:180px' type='text' value='"+ rwge.getUnitJob() +"' /></td>")
					.append("<td><input disabled='disabled' style='width:80px;border:#ffffff;border-bottom: #000000 1px solid;text-align:center;' type='text' name='workPhone' value='"+ rwge.getWorkPhone() +"' /></td>")
					.append("<td><input disabled='disabled' style='width:80px;border:#ffffff;border-bottom: #000000 1px solid;text-align:center;' type='text' name='workRemark' value='"+ rwge.getWorkRemark() +"' /></td></tr>");
			}
			model.addObject("sb", sb);
			
			StringBuilder sb1 = new StringBuilder();
			List<RuralExpertGroupEntity> l1 = ruralExpertGroupService.find(" and x.newRural.id=" + id);
			int j = 0;
			for(RuralExpertGroupEntity rege : l1) {
				sb1.append("<tr><td><input type='hidden' name='expertGroupID' value='"+ rege.getId() +"' />"+ (++j) +"</td>")
				.append("<td><input disabled='disabled' style='width:80px;border:#ffffff;border-bottom: #000000 1px solid;text-align:center;' name='expertName' type='text' value='"+ rege.getExpertName() +"' /></td>")
				.append("<td colspan='2'><input disabled='disabled' name='expertJob' style='width:180px' type='text' value='"+ rege.getExpertJob() +"' /></td>")
				.append("<td><input disabled='disabled' style='width:80px;border:#ffffff;border-bottom: #000000 1px solid;text-align:center;' type='text' name='expertPhone' value='"+ rege.getExpertPhone() +"' /></td>")
				.append("<td><input disabled='disabled' style='width:80px;border:#ffffff;border-bottom: #000000 1px solid;text-align:center;' type='text' name='expertRemark' value='"+ rege.getExpertRemark() +"' /></td></tr>");
			}
			model.addObject("sb1", sb1);
			
			StringBuilder sb2 = new StringBuilder();
			List<RuralUnitEntity> l2 = ruralUnitService.find(" and x.newRural.id=" + id);
			int k = 0;
			for(RuralUnitEntity rue : l2) {
				sb2.append("<tr><td><input type='hidden' name='unitID' value='"+ rue.getId() +"' />"+ (++k) +"</td>")
				.append("<td><input disabled='disabled' style='width:80px;border:#ffffff;border-bottom: #000000 1px solid;text-align:center;' name='unitName' type='text' value='"+ rue.getUnitName() +"' /></td>")
				.append("<td colspan='2'><input disabled='disabled' name='registeredAddress' style='width:180px' type='text' value='"+ rue.getRegisteredAddress() +"' /></td>")
				.append("<td><input disabled='disabled' style='width:80px;border:#ffffff;border-bottom: #000000 1px solid;text-align:center;' type='text' name='ucName' value='"+ rue.getContactName() +"' /></td>")
				.append("<td><input disabled='disabled' style='width:80px;border:#ffffff;border-bottom: #000000 1px solid;text-align:center;' type='text' name='unitPhone' value='"+ rue.getUnitPhone() +"' /></td></tr>");
			}
			model.addObject("sb2", sb2);
			
		}
		return model.execute("applicant/tab-base-village-detial");
	}
	
	/**
	 * 非主体村信息
	 * 
	 * @param request
	 * @param mid
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "!{mid}/village/detial", method = { POST, GET })
	public ModelAndView village_detial(HttpServletRequest request,
			@PathVariable("mid") String mid, Long id) {
		DialogModel model = new DialogModel(mid + "detial", mid, request);
		if (null != id) {
			PeripheralRuralEntity e = peripheraRuralService.get(id);
			PeripheraRuralEditor form = new PeripheraRuralEditor();
//			form.set(e);
			model.addObject("id", id);
			model.addObject("villageInfo", form);
		}
		return model.execute("applicant/tab-village-detial");
	}

	/**
	 * 项目信息
	 * 
	 * @param request
	 * @param mid
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "!{mid}/project/detial", method = { POST, GET })
	public ModelAndView project_detial(HttpServletRequest request, @PathVariable("mid") String mid, Long id) {
		DialogModel model = new DialogModel(mid + "detial", mid, request);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (null != id) {
			ProjectEntity entity = projectService.get(id);
			ProjectEditor form = new ProjectEditor();
			form.set(entity);
//			model.addObject("rural_name", entity.getNewRural().getName());
//			model.addObject("type_name", entity.getType().getName());
//			model.addObject("createAt", sdf.format(form.getCreateAt()));
			model.addObject("id", id);
			model.addObject("form", form);
		}
		return model.execute("applicant/tab-project-detial");
	}
	
	@RequestMapping(value = "!{mid}/industry/detial", method = { POST, GET })
	public ModelAndView industry_detial(HttpServletRequest request,
			@PathVariable("mid") String mid, Long id) {
		DialogModel model = new DialogModel(mid + "detial", mid, request);
		if (null != id) {
			IndustryAreaEntity e = industryAreaService.get(id);
			IndustryAreaEditor form = new IndustryAreaEditor();
			//form.set(e);
			model.addObject("id", id);
			model.addObject("form", form);
		}
		return model.execute("applicant/tab-industry-detial");
	}
	
	@ResponseBody
	@RequestMapping(value = "!{mid}/indDev/search", method = {
			RequestMethod.GET, RequestMethod.POST })
	public Page indDev_search(@PathVariable("mid") String mid, Long id, HttpServletRequest request) {
		try {
			ReadEvent event = new ReadEvent(applicationContext, request,
					getUser());
			Finder fr = industryAreaService.finder(event, IndustryAreaItem.class);
			fr.and("modelArea.id=?", id);
			return fr.getPage();
		} catch (Exception e) {
			e.printStackTrace();
			return Page.EMPTY;
		}
	}
}
