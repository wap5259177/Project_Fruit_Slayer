package cn.bonoon.controllers.area.back;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import cn.bonoon.core.plugins.PlaceService;
import cn.bonoon.entities.IndustryAreaEntity;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.PeripheralRuralEntity;
import cn.bonoon.entities.ProjectEntity;
import cn.bonoon.entities.RuralExpertGroupEntity;
import cn.bonoon.entities.RuralUnitEntity;
import cn.bonoon.entities.RuralWorkGroupEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.events.ReadEvent;
import cn.bonoon.kernel.support.models.Page;
import cn.bonoon.kernel.support.searcher.Finder;
import cn.bonoon.kernel.web.controllers.AbstractController;
import cn.bonoon.kernel.web.models.DialogModel;
import cn.bonoon.kernel.web.models.JsonResult;

@Controller
@RequestMapping("s/cl/area/back")
public class ReporgtingHandleBackController extends AbstractController {

	private NewRuralService newRuralService;
	private ProjectService projectService;
	private ProjectTypeService projectTypeService;
	private ModelAreaService modelAreaService;
	private RuralWorkGroupService ruralWorkGroupService;
	private RuralExpertGroupService ruralExpertGroupService;
	private RuralUnitService ruralUnitService;
	private PeripheraRuralService peripheraRuralService;
	private IndustryAreaService industryAreaService;
	private PlaceService placeService;
	private Long newId = 0L;
	 
	@Autowired
	public ReporgtingHandleBackController(NewRuralService newRuralService,
			ProjectService projectService,
			ProjectTypeService projectTypeService,
			ModelAreaService modelAreaService,
			RuralWorkGroupService ruralWorkGroupService,
			RuralExpertGroupService ruralExpertGroupService,
			RuralUnitService ruralUnitService,
			PeripheraRuralService peripheraRuralService,
			IndustryAreaService industryAreaService,
			PlaceService placeService) {
		this.newRuralService = newRuralService;
		this.projectService = projectService;
		this.projectTypeService = projectTypeService;
		this.modelAreaService = modelAreaService;
		this.ruralWorkGroupService = ruralWorkGroupService;
		this.ruralExpertGroupService = ruralExpertGroupService;
		this.ruralUnitService = ruralUnitService;
		this.peripheraRuralService = peripheraRuralService;
		this.placeService = placeService;
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
			fr.and("x.modelArea.id=?", id);
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
			fr.and("x.modelArea.id=?", id);
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

	/**
	 * 核心村添加修改
	 * 
	 * @param request
	 * @param mid
	 * @param areaId
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{mid}/base_village/editor", method = { POST, GET })
	public ModelAndView base_village_editor(HttpServletRequest request,
			@PathVariable("mid") String mid, Long areaId, Long ruralId) {
		newId = ruralId;
		DialogModel model = new DialogModel(mid + "editor", mid, request);
		addVillages(model);
		if (null != areaId) {
			model.addObject("areaId", areaId);
		}
		if (null != ruralId) {
			NewRuralEntity entity = newRuralService.get(ruralId);
			NewRuralEditor form = new NewRuralEditor();
//			form.set(entity);
			model.addObject("id", ruralId);
			model.addObject("baseVillageInfo", form);
			
			StringBuilder sb = new StringBuilder();
			List<RuralWorkGroupEntity> l = ruralWorkGroupService.find(" and x.newRural.id=" + ruralId);
			int i = 0;
			for(RuralWorkGroupEntity rwge : l) {
				sb.append("<tr><td><input type='hidden' name='workGroupID' value='"+ rwge.getId() +"' />"+ (++i) +"</td>")
					.append("<td><input style='width:80px;border:#ffffff;border-bottom: #000000 1px solid;text-align:center;' name='workName' type='text' value='"+ rwge.getWorkName() +"' /></td>")
					.append("<td colspan='2'><input name='unitJob' style='width:180px' type='text' value='"+ rwge.getUnitJob() +"' /></td>")
					.append("<td><input style='width:80px;border:#ffffff;border-bottom: #000000 1px solid;text-align:center;' type='text' name='workPhone' value='"+ rwge.getWorkPhone() +"' /></td>")
					.append("<td><input style='width:80px;border:#ffffff;border-bottom: #000000 1px solid;text-align:center;' type='text' name='workRemark' value='"+ rwge.getWorkRemark() +"' /></td></tr>");
			}
			model.addObject("sb", sb);
			
			StringBuilder sb1 = new StringBuilder();
			List<RuralExpertGroupEntity> l1 = ruralExpertGroupService.find(" and x.newRural.id=" + ruralId);
			int j = 0;
			for(RuralExpertGroupEntity rege : l1) {
				sb1.append("<tr><td><input type='hidden' name='expertGroupID' value='"+ rege.getId() +"' />"+ (++j) +"</td>")
				.append("<td><input style='width:80px;border:#ffffff;border-bottom: #000000 1px solid;text-align:center;' name='expertName' type='text' value='"+ rege.getExpertName() +"' /></td>")
				.append("<td colspan='2'><input name='expertJob' style='width:180px' type='text' value='"+ rege.getExpertJob() +"' /></td>")
				.append("<td><input style='width:80px;border:#ffffff;border-bottom: #000000 1px solid;text-align:center;' type='text' name='expertPhone' value='"+ rege.getExpertPhone() +"' /></td>")
				.append("<td><input style='width:80px;border:#ffffff;border-bottom: #000000 1px solid;text-align:center;' type='text' name='expertRemark' value='"+ rege.getExpertRemark() +"' /></td></tr>");
			}
			model.addObject("sb1", sb1);
			
			StringBuilder sb2 = new StringBuilder();
			List<RuralUnitEntity> l2 = ruralUnitService.find(" and x.newRural.id=" + ruralId);
			int k = 0;
			for(RuralUnitEntity rue : l2) {
				sb2.append("<tr><td><input type='hidden' name='unitID' value='"+ rue.getId() +"' />"+ (++k) +"</td>")
				.append("<td><input style='width:80px;border:#ffffff;border-bottom: #000000 1px solid;text-align:center;' name='unitName' type='text' value='"+ rue.getUnitName() +"' /></td>")
				.append("<td colspan='2'><input name='registeredAddress' style='width:180px' type='text' value='"+ rue.getRegisteredAddress() +"' /></td>")
				.append("<td><input style='width:80px;border:#ffffff;border-bottom: #000000 1px solid;text-align:center;' type='text' name='ucName' value='"+ rue.getContactName() +"' /></td>")
				.append("<td><input style='width:80px;border:#ffffff;border-bottom: #000000 1px solid;text-align:center;' type='text' name='unitPhone' value='"+ rue.getUnitPhone() +"' /></td></tr>");
			}
			model.addObject("sb2", sb2);
		}
		return model.execute("applicant/tab-base-village-editor");
	}

	/**
	 * 非主体村添加修改
	 * 
	 * @param request
	 * @param mid
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{mid}/village/editor", method = { POST, GET })
	public ModelAndView village_editor(HttpServletRequest request, @PathVariable("mid") String mid, Long id, Long areaId) {
		DialogModel model = new DialogModel(mid + "editor", mid, request);
		addVillages(model);
		if(null != areaId) {
			model.addObject("areaId", areaId);
		}
		if (null != id) {
			PeripheralRuralEntity entity = peripheraRuralService.get(id);
			PeripheraRuralEditor form = new PeripheraRuralEditor();
//			form.set(entity);
			model.addObject("id", id);
			model.addObject("villageInfo", form);
		}
		return model.execute("applicant/tab-village-editor");

	}

	/**
	 * 项目添加与修改
	 * 
	 * @param request
	 * @param mid
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{mid}/project/editor", method = { POST, GET })
	public ModelAndView project_editor(HttpServletRequest request,
			@PathVariable("mid") String mid, Long id, Long area_id) {
		DialogModel model = new DialogModel(mid + "editor", mid, request);
		addVillages(model);
		if (null != area_id) {
			ModelAreaEntity e = modelAreaService.get(area_id);
			model.addObject("area_name", e.getName());
			model.addObject("area_id", area_id);
		}
		if (null != id) {
			ProjectEntity entity = projectService.get(id);
			ProjectEditor form = new ProjectEditor();
			form.set(entity);
			model.addObject("id", id);
			model.addObject("form", form);
		}
		return model.execute("applicant/tab-project-editor");
	}
	/**
	 * 核心村删除
	 * 
	 * @param request
	 * @param mid
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{mid}/base_village/delete", method = {
			RequestMethod.GET, RequestMethod.POST })
	public JsonResult base_village_delete(HttpServletRequest request,
			@PathVariable("mid") String mid, Long id) {
		try {
			NewRuralEntity e = newRuralService.get(id);
			e.setDeleted(true);
			newRuralService.update(e);
			return JsonResult.result();
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error(e);
		}
	}

	/**
	 * 项目删除
	 * 
	 * @param request
	 * @param mid
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{mid}/project/del", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult project_del(HttpServletRequest request,
			@PathVariable("mid") String mid, Long id) {
		try {
			ProjectEntity e = projectService.get(id);
			e.setDeleted(true);
			// e.setUpdateAt(new Date());
			projectService.update(e);
			return JsonResult.result();
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error(e);
		}
	}
	
	/**
	 * 非主体村删除
	 * 
	 * @param request
	 * @param mid
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{mid}/village/delete", method = {
			RequestMethod.GET, RequestMethod.POST })
	public JsonResult village_delete(HttpServletRequest request,
			@PathVariable("mid") String mid, Long id) {
		try {
			PeripheralRuralEntity e = peripheraRuralService.get(id);
			e.setDeleted(true);
			peripheraRuralService.update(e);
			return JsonResult.result();
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error(e);
		}
	}
	
	//非主体村批量删除
		@ResponseBody
		@RequestMapping(value = "!{mid}/village/batchDel", method = { RequestMethod.GET,
				RequestMethod.POST })
		public JsonResult village_batchDel(HttpServletRequest request, @PathVariable("mid") String mid, String ids) {
			String[] ids2 = ids.split(",");
			try {
				if(ids2.length != 0) {
					for(int i=0; i<ids2.length; i++) {
						PeripheralRuralEntity e = peripheraRuralService.get(Long.parseLong(ids2[i]));
						e.setDeleted(true);
						peripheraRuralService.update(e);
					}
				}
					return JsonResult.result();
			} catch (Exception e) {
				e.printStackTrace();
				return JsonResult.error(e);
			}
		}
		
		//核心村批量删除
		@ResponseBody
		@RequestMapping(value = "!{mid}/basevillage/batchDel", method = { RequestMethod.GET,
				RequestMethod.POST })
		public JsonResult basevillage_batchDel(HttpServletRequest request, @PathVariable("mid") String mid, String ids) {
			String[] ids2 = ids.split(",");
			try {
				if(ids2.length != 0) {
					for(int i=0; i<ids2.length; i++) {
						NewRuralEntity e = newRuralService.get(Long.parseLong(ids2[i]));
						e.setDeleted(true);
						newRuralService.update(e);
					}
				}
					return JsonResult.result();
			} catch (Exception e) {
				e.printStackTrace();
				return JsonResult.error(e);
			}
		}
		
	//核心村批量删除
	@ResponseBody
	@RequestMapping(value = "!{mid}/project/batchDel", method = { RequestMethod.GET,
			RequestMethod.POST })
		public JsonResult project_batchDel(HttpServletRequest request, @PathVariable("mid") String mid, String ids) {
			String[] ids2 = ids.split(",");
			try {
				if(ids2.length != 0) {
					for(int i=0; i<ids2.length; i++) {
						ProjectEntity e = projectService.get(Long.parseLong(ids2[i]));
						e.setDeleted(true);
						projectService.update(e);
					}
				}
					return JsonResult.result();
			} catch (Exception e) {
				e.printStackTrace();
				return JsonResult.error(e);
			}
		}
	
	/**
	 * 核心村保存
	 * 
	 * @param mid
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{mid}/base_village/save", method = {
			RequestMethod.GET, RequestMethod.POST })
	public JsonResult base_village_save(@PathVariable("mid") String mid,	NewRuralEditor form, HttpServletRequest request) {
		/*
		/********** workGroups **********/
		String workGroupIDs = request.getParameter("workGroupIDs");
		String workNames = request.getParameter("workNames");
		String unitJobs = request.getParameter("unitJobs");
		String workPhones = request.getParameter("workPhones");
		String workRemarks = request.getParameter("workRemarks");
		String[] arr_wgid = workGroupIDs.split(",");
		String[] arr_wn = workNames.split(",");
		String[] arr_uj = unitJobs.split(",");
		String[] arr_wp = workPhones.split(",");
		String[] arr_wr = workRemarks.split(",");
		/********** expertGroups **********/
		String expertGroupIDs = request.getParameter("expertGroupIDs");
		String expertNames = request.getParameter("expertNames");
		String expertJobs = request.getParameter("expertJobs");
		String expertPhones = request.getParameter("expertPhones");
		String expertRemarks = request.getParameter("expertRemarks");
		String[] arr_egid = expertGroupIDs.split(",");
		String[] arr_en = expertNames.split(",");
		String[] arr_ej = expertJobs.split(",");
		String[] arr_ep = expertPhones.split(",");
		String[] arr_er = expertRemarks.split(",");
		/********** units **********/
		String unitIDs = request.getParameter("unitIDs");
		String unitNames = request.getParameter("unitNames");
		String registeredAddresses = request.getParameter("registeredAddresses");
		String ucNames = request.getParameter("ucNames");
		String unitPhones = request.getParameter("unitPhones");
		String[] arr_unid = unitIDs.split(",");
		String[] arr_un = unitNames.split(",");
		String[] arr_ra = registeredAddresses.split(",");
		String[] arr_cn = ucNames.split(",");
		String[] arr_up = unitPhones.split(",");
		
		try {
			NewRuralEntity entity;
			if (form.isNew()) {
				entity = new NewRuralEntity();
//				form.toEntity(entity, modelAreaService.get(form.getModelareaId()));
				entity.setOwnerId(getUser().getOwnerId());
				entity.setCreateAt(new Date());
				entity.setCreatorName(getUsername());
				entity.setCreatorId(getUser().getId());
				newRuralService.save(entity);
				
				newId = entity.getId();
				if(workNames.length() != 0) {
					ruralWorkGroupService.workGroups(newId, arr_wn, arr_uj, arr_wp, arr_wr);
				}
				if(expertNames.length() != 0) {
					ruralExpertGroupService.expertGroups(newId, arr_en, arr_ej, arr_ep, arr_er);
				}
				if(ucNames.length() != 0) {
					ruralUnitService.units(newId, arr_un, arr_ra, arr_cn, arr_up);
				}
			} else {
				entity = newRuralService.get(form.getId());
//				form.toEntity(entity, modelAreaService.get(form.getModelareaId()));
				newRuralService.update(entity);
				
				if(workGroupIDs.length() == 0 && workNames.length() != 0) {
					ruralWorkGroupService.workGroups(newId, arr_wn, arr_uj, arr_wp, arr_wr);
				} else if(workGroupIDs.length() != 0){
					ruralWorkGroupService.workGroupsUpdate(newId, arr_wgid, arr_wn, arr_uj, arr_wp, arr_wr);
				}
				if(expertGroupIDs.length() == 0 && expertNames.length() != 0) {
					ruralExpertGroupService.expertGroups(newId, arr_en, arr_ej, arr_ep, arr_er);
				} else if(expertGroupIDs.length() != 0) {
					ruralExpertGroupService.expertGroupsUpdate(newId, arr_egid, arr_en, arr_ej, arr_ep, arr_er);
				}
				if(unitIDs.length() == 0 && unitNames.length() != 0) {
					ruralUnitService.units(newId, arr_un, arr_ra, arr_cn, arr_up);
				} else if(unitIDs.length() != 0){
					ruralUnitService.unitsUpdate(newId, arr_unid, arr_un, arr_ra, arr_cn, arr_up);
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); JsonResult.result(e.toString());
		}
		return JsonResult.result();
	}

	/**
	 * 非主体村保存
	 * 
	 * @param mid
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{mid}/village/save", method = {
			RequestMethod.GET, RequestMethod.POST })
	public JsonResult village_save(@PathVariable("mid") String mid,PeripheraRuralEditor form) {
		try {
			PeripheralRuralEntity entity;
			if (form.isNew()) {
				entity = new PeripheralRuralEntity();
//				form.toEntity(entity, modelAreaService.get(form.getModelareaId()));
				entity.setOwnerId(getUser().getOwnerId());
				entity.setCreatorId(getUser().getId());
				entity.setCreatorName(getUsername());
				peripheraRuralService.save(entity);
			} else {
				entity = peripheraRuralService.get(form.getId());
//				form.toEntity(entity, modelAreaService.get(form.getModelareaId()));
				peripheraRuralService.update(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.result(e.toString());
		}
		return JsonResult.result();
	}

	/**
	 * 保存项目信息
	 * 
	 * @param mid
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{mid}/project/save", method = {
			RequestMethod.GET, RequestMethod.POST })
	public JsonResult project_save(@PathVariable("mid") String mid,
			ProjectEditor form) {

		try {
			ProjectEntity entity;
			if (form.isNew()) {
				entity = new ProjectEntity();
				form.toEntity(entity);
				entity.setOwnerId(getUser().getOwnerId());
				entity.setCreatorName(getUsername());
				entity.setCreatorId(getUser().getId());
				entity.setCreateAt(new Date());
				projectService.save(entity);
			} else {
				entity = projectService.get(form.getId());
				form.toEntity(entity);
				entity.setCreateAt(new Date());
				projectService.update(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.result(e.toString());
		}
		return JsonResult.result();
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
	
	//产业发展
	@ResponseBody
	@RequestMapping(value = "!{mid}/indDev/editor", method = { POST, GET })
	public ModelAndView indDev_editor(HttpServletRequest request,
			@PathVariable("mid") String mid, Long id, Long area_id) {
		DialogModel model = new DialogModel(mid + "editor", mid, request);
		addVillages(model);
		if (null != area_id) {
			ModelAreaEntity e = modelAreaService.get(area_id);
			model.addObject("area_name", e.getName());
			model.addObject("area_id", area_id);
		}
		if (null != id) {
			IndustryAreaEntity entity = industryAreaService.get(id);
			IndustryAreaEditor form = new IndustryAreaEditor();
//			form.set(entity);
			model.addObject("id", id);
			model.addObject("form", form);
//			model.addObject("vId", form.getPlaceId());
		}
		return model.execute("applicant/tab-industry-editor");
	}
	
	//产业发展保存
	@ResponseBody
	@RequestMapping(value = "!{mid}/industry/save", method = {
			RequestMethod.GET, RequestMethod.POST })
	public JsonResult industry_save(@PathVariable("mid") String mid,IndustryAreaEditor form, HttpServletRequest request) {
		
		Long placeId = Long.parseLong(request.getParameter("place"));
		
		try {
			IndustryAreaEntity entity;
			if (form.isNew()) {
				entity = new IndustryAreaEntity();
//				form.toEntity(entity, modelAreaService.get(form.getModelarea()));
				entity.setOwnerId(getUser().getOwnerId());
				entity.setCreatorId(getUser().getId());
				entity.setCreatorName(getUsername());
				entity.setCreateAt(new Date());
				entity.setTown(placeService.get(placeId).getParent().getName());
				entity.setName(placeService.get(placeId).getName());
				entity.setVillageName(placeService.get(placeId).getName());
				entity.setPlaceId(placeId);
				industryAreaService.save(entity);
			} else {
				entity = industryAreaService.get(form.getId());
//				form.toEntity(entity, modelAreaService.get(form.getModelarea()));
				entity.setTown(placeService.get(placeId).getParent().getName());
				entity.setName(placeService.get(placeId).getName());
				entity.setVillageName(placeService.get(placeId).getName());
				entity.setPlaceId(placeId);
				entity.setCreateAt(new Date());
				
				industryAreaService.update(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.result(e.toString());
		}
		return JsonResult.result();
	}
	
	public void addVillages(DialogModel model) {
		List<PlaceEntity> places = newRuralService.towns(getUser().getOwnerId());
		StringBuilder selPlace = new StringBuilder();
		selPlace.append("<select id='place' style='width:220px;' name='place'>");
		for(PlaceEntity place : places){
			if(place.getChildren().isEmpty()){ continue; }
			selPlace.append("<optgroup label='").append(place.getName()).append("'>");
			for(PlaceEntity pe : place.getChildren()){
				Long pid = pe.getId();
				selPlace.append("<option value='").append(pid);
				selPlace.append("'>").append(pe.getName());
				selPlace.append("</option>");
			}
			selPlace.append("</optgroup>");
		}
		selPlace.append("</select>");
		model.addObject("place", selPlace);
	}
}
