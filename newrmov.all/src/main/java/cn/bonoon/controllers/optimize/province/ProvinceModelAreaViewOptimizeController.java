//package cn.bonoon.controllers.optimize.province;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import cn.bonoon.controllers.AbstractModelAreaViewController;
//import cn.bonoon.controllers.inofs.BaseIndustryAreaInfo;
//import cn.bonoon.controllers.inofs.BaseModelAreaInfo;
//import cn.bonoon.controllers.inofs.BaseNewRuralInfo;
//import cn.bonoon.controllers.inofs.BaseProjectInfo;
//import cn.bonoon.core.AdministrationCoreRuralService;
//import cn.bonoon.core.AdministrationUncoreRuralService;
//import cn.bonoon.core.BaseRuralService;
//import cn.bonoon.core.ModelAreaService;
//import cn.bonoon.core.ditu.AareaSelectInfo;
//import cn.bonoon.entities.AdministrationCoreRuralEntity;
//import cn.bonoon.entities.AdministrationUncoreRuralEntity;
//import cn.bonoon.entities.BaseRuralEntity;
//import cn.bonoon.entities.ModelAreaEntity;
//import cn.bonoon.entities.NewRuralEntity;
//import cn.bonoon.entities.PeripheralRuralEntity;
//import cn.bonoon.entities.ProjectEntity;
//import cn.bonoon.entities.ProjectReportItem;
//import cn.bonoon.entities.RuralExpertGroupEntity;
//import cn.bonoon.entities.RuralUnitEntity;
//import cn.bonoon.entities.RuralWorkGroupEntity;
//import cn.bonoon.kernel.VisibleScope;
//import cn.bonoon.kernel.events.PanelEvent;
//import cn.bonoon.kernel.events.ReadEvent;
//import cn.bonoon.kernel.security.LogonUser;
//
//@Controller
//@RequestMapping("s/pl/optimize/pmav")
//public class ProvinceModelAreaViewOptimizeController extends AbstractModelAreaViewController{
//
//	
//	
//	@Autowired
//	private ModelAreaService modelAreaService;
//	
//	@Autowired
//	private BaseRuralService baseRuralService;
//	
//	@Autowired
//	private AdministrationCoreRuralService administrationCoreRuralService;
//	@Autowired
//	AdministrationUncoreRuralService administrationUncoreRuralService;
//	@Override
//	protected void onLoad(PanelEvent event) throws Exception {
//		event.getModel().addObject("layout", "layout-empty.vm");
//		event.setVmPath("optimize/province-view");
//	}
//	
//	/**
//	 * iframe  载入
//	 * 默认应该是显示第一批示范片显示
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value="!{key}/loadGDmap.do")
//	public String loadGDmap(Model model,String batch){
//		
//		List<Object[]> areaList = modelAreaService.getModelAreaByBatch(batch);
//		
//		
//		StringBuffer hasValue = new StringBuffer();//有片区的
//		StringBuffer noValue = new StringBuffer();
//		
//		List<String> ciList = new ArrayList<>();
//		List<String> allName = _getAllCityName(ciList);
//		for(int i=0;i<areaList.size();i++){
//			String cName = (String)areaList.get(i)[1];
//			Long id = (Long)areaList.get(i)[0];
//			if(allName.contains(cName)){
//				//不用对最末尾那个加判断,多加一个","没有影响
//				hasValue.append("{name:").append("'").append(cName).append("',").append("value:").append(id).append("},");
//			}
//		}
//		
//		
//		List<AareaSelectInfo> sList = __getNoValueCity(areaList);
//		for(int i=0;i<sList.size();i++){
//			String cName = sList.get(i).getName();
//			if(allName.contains(cName)){
//				noValue.append("{name:").append("'").append(cName).append("',").append("selected:").append(true).append("},");
//			}
//		}
//		model.addAttribute("layout", "layout-empty.vm");
//		model.addAttribute("cList", hasValue.toString());
//		model.addAttribute("sList", noValue.toString());
//		return "optimize/gd-map";
//	}
//
//	
//	
//	//getModelArea.do
//	/**
//	 * 点击某个批次按钮
//	 * @param batch
//	 * @return
//	 */
//	@RequestMapping(value="!{key}/getModelArea.do")
//	@ResponseBody
//	public List<AareaSelectInfo> getModelArea(String batch){
//		//object[0]:片区id    object[1]:cityName
//		List<Object[]> areaList = modelAreaService.getModelAreaByBatch(batch);
//		List<AareaSelectInfo> list = __add(areaList);
//		return list;
//	}
//	
//	/**
//	 * Load  示范片的概况显示
//	 * @param batch
//	 * @return
//	 */
//	@RequestMapping(value="!{key}/modelArea.detail")
//	public String modelAreaDetail(Model model,Long maid){
//		ModelAreaEntity ma = modelAreaService.get(maid);
//		Map<String,Object> map = modelAreaService.getMa_title_show(maid);//或许不用,用sql查出这些村个数,entity里面有相应的字段对应
//		model.addAttribute("ma", ma);
//		model.addAttribute("map", map);
//		model.addAttribute("layout", "layout-empty.vm");
//		return "optimize/modelArea-generation";
//	}
//	
//	
//	protected Class<? extends BaseModelAreaInfo> maType(){
//		return BaseModelAreaInfo.class;
//	}
//	
////	protected String __detail(HttpServletRequest request, ModelAndView model, LogonUser user, Long id){
////		model.addObject("readonly", "readonly='readonly'");
////		model.addObject("disabled", "disabled='true'");
////		try{
////			ReadEvent event = new ReadEvent(applicationContext, request, user);
////			event.setScope(VisibleScope.GLOBAL);
////			//1.台账信息
////			model.addObject("form", modelAreaService.get(event, id, maType()));
////			
////			//2.核心村
////			model.addObject("nrs", modelAreaService.getNewRurals(id));
////			
////			//3.非主体村
//////			List<Object[]> prs = modelAreaService.getPeripheralRurals(id);
////			model.addObject("prs", modelAreaService.getPeripheralRurals(id));
////			
////			//4.产业发展情况
////			List<Object[]> ias = modelAreaService.getIndustries(id);
////			model.addObject("ias", ias);
////			
////			//5.工程项目
////			List<Object[]> pjs = modelAreaService.getProjects(id);
////			model.addObject("pjs", pjs);
////			
////			//6.综合整治
////			List<ResidentialEnvironmentEntity> listRes = modelAreaService.getResidentialEnvironments(id);
////			model.addObject("listRes", listRes);
////		}catch (Exception e) {
////			e.printStackTrace();
////		}
////		
////		return "applicant/area/view-detail";
////	}
//	
//	/**
//	 * 示范片简介
//	 * @param batch
//	 * @return
//	 */
//	@RequestMapping(value="!{key}/ma.intro")
//	public String maIntro(HttpServletRequest request,Model model,Long id){
//		model.addAttribute("readonly", "readonly='readonly'");
//		model.addAttribute("disabled", "disabled='true'");
//		model.addAttribute("layout", "layout-empty.vm");
//		LogonUser user = getUser();
//		try{
//			ReadEvent event = new ReadEvent(applicationContext, request, user);
//			event.setScope(VisibleScope.GLOBAL);
//			//1.台账信息
//			model.addAttribute("form", modelAreaService.get(event, id, maType()));
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "optimize/area/modelarea-info";
//	}
//
//	
//	/**
//	 * 主体村,点击主体村,列出所有行政村
//	 */
//	@RequestMapping(value="!{key}/coreRural.intro")
//	public String allCR(HttpServletRequest request,Model model,Long id){
//		List<AdministrationCoreRuralEntity> acs = modelAreaService.getAdminRural(id);
//		model.addAttribute("acs", acs);
//		model.addAttribute("layout", "layout-empty.vm");
//		model.addAttribute("type", 1);
//		return "optimize/rural/coreRural-generation";
//	}
//	//非主体村
//	@RequestMapping(value="!{key}/uncoreRural.intro")
//	public String allUCR(HttpServletRequest request,Model model,Long id){
//		List<AdministrationUncoreRuralEntity> acs = modelAreaService.getUCAdminRural(id);
//		model.addAttribute("acs", acs);
//		model.addAttribute("layout", "layout-empty.vm");
//		model.addAttribute("type", 0);
//		return "optimize/rural/coreRural-generation";
//	}
//	
//	//点击行政村
//	@RequestMapping(value="!{key}/ac.generation")
//	public String acGeneration(HttpServletRequest request,Model model,Long id){
//		model.addAttribute("id", id);
//		model.addAttribute("layout", "layout-empty.vm");
//		return "optimize/rural/ac-generation";
//	}
//	
//	//点击行政村简介
//	@RequestMapping(value="!{key}/ac.intro")
//	public String acIntro(HttpServletRequest request,Model model,Long id,int type){//type 0:非主体村  1:主体村
//		//通过行政村id  拿到行政村
//		model.addAttribute("id", id);
//		model.addAttribute("layout", "layout-empty.vm");
//		model.addAttribute("readonly", "readonly='readonly'");
//		model.addAttribute("readonly", "disabled='disabled'");
//		if(type==1){
//			
//			AdministrationCoreRuralEntity acr = administrationCoreRuralService.get(id);
//			model.addAttribute("form", acr);
//			
//			List<RuralWorkGroupEntity> wgs = administrationCoreRuralService.workGroupsByAdminRural(id);//工作小组
//			List<RuralUnitEntity> rus = administrationCoreRuralService.ruralUnitsByAdminRural(id);//挂点县领导
//			List<RuralExpertGroupEntity> egs = administrationCoreRuralService.experGroupByAdminRural(id);//专家指导组
//			
//			model.addAttribute("wgs", wgs).addAttribute("egs", egs).addAttribute("rus", rus);
//			return "optimize/rural/adminrural/ar-info";
//		}else{
//			AdministrationUncoreRuralEntity aucr = administrationUncoreRuralService.get(id);
//			model.addAttribute("form", aucr);
//			return "optimize/rural/adminrural/aur-info";
//		}
//	}
//	
//	//ac_nr.generation
//	//点击主体自然村
//	@RequestMapping(value="!{key}/ac_nr.generation")
//	public String ac_nr(HttpServletRequest request,Model model,Long id,int type){
//		
//		model.addAttribute("id", id);
//		model.addAttribute("layout", "layout-empty.vm");
//		model.addAttribute("readonly", "readonly='readonly'");
//		model.addAttribute("readonly", "disabled='disabled'");
//		if(type==1){
//			List<NewRuralEntity> nrs = administrationCoreRuralService.getRuralByAdminRuralId(id);
//			model.addAttribute("nrs", nrs);
//		}else{
//			List<PeripheralRuralEntity> prs = administrationUncoreRuralService.getRuralByAdminRuralId(id);
//			model.addAttribute("nrs", prs);
//		}
//		
//		return "optimize/rural/adminrural/newrural/newrural-list";
//	}
//	
//	//nr.generation
//	//点击某个自然村名
//	@RequestMapping(value="!{key}/nr.generation")
//	public String nr_generation(HttpServletRequest request,Model model,Long nrid){
//		model.addAttribute("nrid", nrid);
//		model.addAttribute("layout", "layout-empty.vm");
//		return "optimize/rural/adminrural/newrural/nr-generation";
//	}
//	
//	//点击自然村简介
//	@RequestMapping(value="!{key}/nc.intro")
//	public String nc_intro(HttpServletRequest request,Model model,Long nrid){
//		model.addAttribute("nrid", nrid);
//		model.addAttribute("layout", "layout-empty.vm")
//		.addAttribute("readonly", "readonly='readonly'")
//		.addAttribute("disabled", "disabled='true'");
//		
//		try {
//			ReadEvent event = new ReadEvent(applicationContext, request, getUser());
//			event.setScope(VisibleScope.GLOBAL);
//			BaseNewRuralInfo bnr = newRuralService.get(event, nrid, BaseNewRuralInfo.class);
//			
//			model.addAttribute("form", bnr);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "optimize/rural/adminrural/newrural/nr-info";
//	}
//	
//	//点击项目
//	@RequestMapping(value="!{key}/nr.project")
//	public String nr_project(HttpServletRequest request,Model model,Long nrid){
//		model.addAttribute("nrid", nrid);
//		model.addAttribute("layout", "layout-empty.vm");
//		List<ProjectEntity> pros = baseRuralService.getProject(nrid);
//		int finish=0,end=0,running=0,noStart=0;
//		for(ProjectEntity p:pros){
//			switch (p.getStatus()) {
//			case 0://未开始
//				noStart++;
//				break;
//			case 1://进行中
//				running++;
//				break;
//			case 2://竣工
//				finish++;
//				break;
//			case 3://终止
//				end++;
//				break;
//			default:
//				break;
//			}
//		}
//		model.addAttribute("noStart", noStart).addAttribute("running", running)
//			.addAttribute("finish", finish).addAttribute("end", end);
//		return "optimize/project/project-sort";
//	}
//	
//	//getProject.do
//	//点击未开始/进行中/竣工/终止
//	@RequestMapping(value="!{key}/getProject.do")
//	public String getProject(HttpServletRequest request,Model model,Long ruralId,Integer status){
//		model.addAttribute("layout", "layout-empty.vm");
//		List<ProjectEntity> pros = baseRuralService.getProject(ruralId,status);
//		model.addAttribute("pros", pros);
//		return "optimize/project/project-list";
//	}
//	
//	//点击某个项目
//	@RequestMapping(value="!{key}/project.generation")
//	public String project_generation(HttpServletRequest request,Model model,Long proId){
//		model.addAttribute("layout", "layout-empty.vm").addAttribute("proId", proId);
//		return "optimize/project/project-generation";
//	}
//	
//	//TODO:
//	//点击项目详细信息
//	@RequestMapping(value="!{key}/project.info")
//	public String project_info(HttpServletRequest request,Model model,Long proId){
//		model.addAttribute("layout", "layout-empty.vm")
//			.addAttribute("readonly", "readonly='readonly'")
//			.addAttribute("disabled", "disabled='true'");
//		LogonUser user = getUser();
//		try{
//			ReadEvent event = new ReadEvent(applicationContext, request, user);
//			event.setScope(VisibleScope.GLOBAL);
//			model.addAttribute("form", projectService.get(event, proId, BaseProjectInfo.class));
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "optimize/project/areaproject-info";
//	}
//	
//	
//	//点击项目月报
//	@RequestMapping(value="!{key}/project.report")
//	public String project_report(HttpServletRequest request,Model model,Long proId){
//		List<ProjectReportItem> items = projectService.getProjectReport(proId);
////		ProjectReportItem item = projectReportManagerService.get(id);
//		ProjectEntity item = projectService.get(proId);
//		model.addAttribute("layout", "layout-empty.vm");
//		model.addAttribute("itemId", proId);
//		model.addAttribute("item", item);
//		model.addAttribute("items", items);
//		model.addAttribute("title", item.getName());
//		return "optimize/project/project-report-content";
//	}
//	
//	
//	//
//	//点击产业发展
//	@RequestMapping(value="!{key}/nr.inds")
//	public String nr_inds(HttpServletRequest request,Model model,Long nrid){
//		List<Object[]> ins = baseRuralService.getIndustries(nrid);
//		model.addAttribute("indstries", ins);
//		model.addAttribute("layout", "layout-empty.vm");
//		return "optimize/indstry/indstry-generation";
//	}
//	
//	//点击某个产业发展
//	@RequestMapping(value="!{key}/inds.info")
//	public String inds_info(HttpServletRequest request,Model model,Long indsId){
//		model.addAttribute("layout", "layout-empty.vm")
//			.addAttribute("readonly", "readonly='readonly'")
//			.addAttribute("disabled", "disabled='true'");
//		LogonUser user = getUser();
//		try{
//			ReadEvent event = new ReadEvent(applicationContext, request, user);
//			event.setScope(VisibleScope.GLOBAL);
//			model.addAttribute("form", industryAreaService.get(event, indsId, BaseIndustryAreaInfo.class));
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "optimize/indstry/areaindustry-info";
//	}
//	
//	//点击某个产业发展
//	@RequestMapping(value="!{key}/nr.plan")
//	public String nr_plan(HttpServletRequest request,Model model,Long nrid){
//		model.addAttribute("layout", "layout-empty.vm");
//		return "optimize/rural/rural-plan";
//	}
//	
//	//拿到没有不是片区的城市
//	private List<AareaSelectInfo> __getNoValueCity(List<Object[]> areaList) {
//		List<AareaSelectInfo> list = new ArrayList<>();
//		List<String> alList = new ArrayList<>();
//		List<String> ciList1 = _getAllCityName(alList);
//		for(Object[] i:areaList){
//			String city = (String) i[1];
//			if(ciList1.contains(city)){
//				ciList1.remove(city);
//			}
//		}
//		for(String str:ciList1){
//			list.add(new AareaSelectInfo(str, true));
//		}
//		return list;
//	}
//	
//	
//	//
//	private List<AareaSelectInfo> __add(List<Object[]> areaList) {
//		List<AareaSelectInfo> list = new ArrayList<>();
//		List<String> alList = new ArrayList<>();
//		List<String> ciList1 = _getAllCityName(alList);
//		for(Object[] i:areaList){
//			String city = (String) i[1];
//			list.add(new AareaSelectInfo((Long)i[0], city, false));
//			
//			if(ciList1.contains(city)){
//				ciList1.remove(city);
//			}
//		}
//		for(String str:ciList1){
//			list.add(new AareaSelectInfo(str, true));
//		}
//		return list;
//	}
//	
//	private List<String> _getAllCityName(List<String> ciList) {
//		ciList.add("清远市");
//		ciList.add("韶关市");
//		ciList.add("湛江市");
//		ciList.add("梅州市");
//		ciList.add("河源市");
//		ciList.add("肇庆市");
//		ciList.add("惠州市");
//		ciList.add("茂名市");
//		ciList.add("江门市");
//		ciList.add("阳江市");
//		ciList.add("云浮市");
//		ciList.add("广州市");
//		ciList.add("汕尾市");
//		ciList.add("揭阳市");
//		ciList.add("珠海市");
//		ciList.add("佛山市");
//		ciList.add("潮州市");
//		ciList.add("汕头市");
//		ciList.add("深圳市");
//		ciList.add("东莞市");
//		ciList.add("中山市");
//		return ciList;
//	}
//}
