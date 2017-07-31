package cn.bonoon.controllers.felicityvillage.report;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.core.FVBaseRuralService;
import cn.bonoon.core.FVCoreRuralService;
import cn.bonoon.core.FVCountyReportService;
import cn.bonoon.core.FVMAReportService;
import cn.bonoon.core.FVModelAreaReportService;
import cn.bonoon.core.FVProjectRuralService;
import cn.bonoon.core.FVReportService;
import cn.bonoon.core.felicity.ProjectInfo;
import cn.bonoon.core.felicity.ReportInfo;
import cn.bonoon.core.felicity.RuralInfo;
import cn.bonoon.entities.felicityVillage.FVBaseRuralEntity;
import cn.bonoon.entities.felicityVillage.FVCoreRuralEntity;
import cn.bonoon.entities.felicityVillage.FVMACountyReportEntity;
import cn.bonoon.entities.felicityVillage.FVModelAreaReportEntity;
import cn.bonoon.entities.felicityVillage.FVPeripheralRuralEntity;
import cn.bonoon.entities.felicityVillage.FVProjectRuralEntity;
import cn.bonoon.entities.felicityVillage.FVReportEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.models.CommonItem;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.JsonResult;



@Controller
@RequestMapping("s/cl/fv/county/report")
public class LocalFVReportController extends AbstractPanelController{
	@Autowired
	private FVReportService fvReportService;
	
	@Autowired
	private FVCountyReportService fvCountyReportService;
	
	@Autowired
	private FVMAReportService fvmaReportService;
	
	@Autowired
	private FVCoreRuralService fvCoreRuralService;
	
	@Autowired
	private FVBaseRuralService baseRuralService;
	
	@Autowired
	private FVProjectRuralService fvProjectRuralService;
	
	@Autowired
	private FVModelAreaReportService areaReportService;
	
	protected void onLoad(PanelEvent event) throws Exception {
//		PanelModel model = event.getModel();
		event.setVmPath("felicityvillage/felicity-view");
	}
	
	
	//列出各个上报的年度list  数据表格
	@ResponseBody
	@RequestMapping("!{key}/report.json")
	public List<ReportInfo> report(){
		List<FVReportEntity> reports = fvReportService.allReport();
		//这里过滤点delete=true的
		List<FVMACountyReportEntity> cReports = fvCountyReportService.allCountyReport(getUser());
		
		List<FVMACountyReportEntity> hasR =  new ArrayList<>();//已经点了开始上报的
		Map<Long,FVReportEntity> notR = new HashMap<>();//没有电开始上报的
		List<FVReportEntity> maybeR =  new ArrayList<>();//可能已经开始上报
		
		for(FVReportEntity fvr:reports){
			if(cReports.size()==0){
				notR.put(fvr.getId(), fvr);
			}else{
				for(FVMACountyReportEntity fvcr:cReports){
					FVReportEntity r = fvcr.getReport();
					if(r!=null&&r.getId().equals(fvr.getId())){
						hasR.add(fvcr);
					}else{
						notR.put(fvr.getId(), fvr);//第一次可能在里面没有这个id,被你放进了map ,但是第二遍的时候有了,所有不应该放进去
						maybeR.add(fvr);//可能已经上报了,被你放到了没有上报里面
					}
				}
			}
		}
		
		//可能已经上报了,被你放到了没有上报里面,去掉不必添加进来的
		for(FVMACountyReportEntity fvcr:hasR){
			for(FVReportEntity mr:maybeR){
				if(fvcr.getReport().getId().equals(mr.getId())){
					notR.remove(mr.getId());
				}
			}
		}
		
		List<ReportInfo> infoData = new ArrayList<>();
		for(Map.Entry<Long, FVReportEntity> entry:notR.entrySet()){
			FVReportEntity r = entry.getValue();
			infoData.add(new ReportInfo(r));
		}
		for(FVMACountyReportEntity fvcr:hasR){
			infoData.add(new ReportInfo(fvcr));
		}
		return infoData;
	}

	
	
	
	/**
	 * [xxx]开始填报
	 * 	创建县报
	 * 	创建片区
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("!{key}/toReport.do")
	public String addArea(Model model,Long id){//id为report 的id
		
		//FVMACountyReportEntity fvcr = fvReportService.toReport(id,getUser());
		
		//model.addAttribute("id", fvcr.getId());//注意要放县报的id到界面
		LogonUser user = getUser();
		PlaceEntity place = fvCountyReportService.getPlace(user);
//		Long lastReportId = fvCountyReportService.lastReport(id, user);
//		model.addAttribute("lastReportId", lastReportId);
		
		List<FVMACountyReportEntity> crs = fvCountyReportService.allCountyReport(user);
		model.addAttribute("count", crs.size());//如果之前有填报的,那么size 一定大于0,那么下一次填报的时候就可以拷贝上一次的数据
		model.addAttribute("crs", crs);
		
		String cityName = place.getParent().getName();
		String countyName = place.getName();
		String cityCountyName = cityName+countyName;
		model.addAttribute("cityCountyName", cityCountyName);
		model.addAttribute("id", id);
		model.addAttribute("title", "填写示范片");
		model.addAttribute("layout", "layout-empty.vm");
		return "felicityvillage/area-view";
	}
	
	/**
	 * 保存示范片
	 * @param request
	 * @param codes
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value ="!{key}/area.save", method = POST)
	public JsonResult AreaSave(HttpServletRequest request,
			Long[] codes,
			Long id,//县报的id   -->report  id
			/*Long copyId*/
			Long copyReportId){
		try{
		if(null != copyReportId){
			fvCountyReportService.copy(id, copyReportId,getUser());
		}else{
			List<FVModelAreaReportEntity> areas = new ArrayList<>();
//			try {
//				throw new RuntimeException();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			
			for(Long code:codes){
//				String _cityCountyName = "cityCountyName_"+code;   
				String _modelAreaName = "modelAreaName_"+code;
				String _constructionArea = "constructionArea_"+code;
				String _households = "households_"+code;
				String _population = "population_"+code;
				
//				String cityCountyName = request.getParameter(_cityCountyName); 
				String modelAreaName = request.getParameter(_modelAreaName); 
				if(modelAreaName==null||"".equals(modelAreaName)){
					throw new RuntimeException("请填写示范片名称!");
				}
				
//				//TODO:界面要对  输入的内容做判断,是否可空字符串,人口数不能输入中文等....
//				double constructionArea = StringHelper.toDouble(request.getParameter(_constructionArea)); 
//				int households = StringHelper.toint(request.getParameter(_households)); 
//				int population = StringHelper.toint(request.getParameter(_population)); 
				
				String constructionArea = request.getParameter(_constructionArea);
				String households = request.getParameter(_households); 
				String population = request.getParameter(_population);
				
				FVModelAreaReportEntity area = new FVModelAreaReportEntity();
				area.setName(modelAreaName);
				area.setConstructionArea(StringHelper.toDouble(constructionArea));
				area.setHouseholds(StringHelper.toint(households));
				area.setPopulation(StringHelper.toint(population));
				areas.add(area);
			}
			FVMACountyReportEntity fvcr = fvReportService.toReport(id,getUser());
			fvCountyReportService.saveModelArea(getUser(), areas,fvcr.getId());
//			fvCountyReportService.saveModelArea(getUser(), areas,id);
		}
			return JsonResult.result();
		}catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error(e);
		}
	}
	
	
	/**
	 * 点击表1填报
	 * 		弹出框最大化(框里面用tab)
	 * @param model
	 * @param id
	 * @return
	 */
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	@RequestMapping("!{key}/report.table1")
	public String report1(Model model,Long id){//id为县报的id
		FVMACountyReportEntity cReport = fvCountyReportService.get(id);
		String startTime = sdf.format(cReport.getReport().getStartTime());
		String deadline = sdf.format(cReport.getReport().getDeadline());
		
		
		
		List<FVModelAreaReportEntity> mas = cReport.getModelAreas();
		model.addAttribute("mas", mas);
		model.addAttribute("id", id);
		model.addAttribute("cReport", cReport);
		model.addAttribute("title", "11个原中央苏区县幸福村居示范片项目库(一)");
		model.addAttribute("startTime", startTime);
		model.addAttribute("deadline", deadline);
		model.addAttribute("layout", "layout-empty.vm");
		
		if(cReport.getStatus()==1||cReport.getStatus()==4){//完成和待审核都不能进去修改了
			model.addAttribute("submit", true);
			model.addAttribute("readonly", "readonly='readonly'");
		}
		return "felicityvillage/table1-view";
	}
	
	
	//处理tab对应iframe的src 
	@RequestMapping("!{key}/felicityvillage/table1.htm")
	public String tab(Model model,Long id){//片区id
		FVModelAreaReportEntity ma = fvmaReportService.get(id);
		model.addAttribute("ma", ma);
		model.addAttribute("id", id);
		if(ma.getCountyReport().getStatus()==1 ||ma.getCountyReport().getStatus()==4){
			model.addAttribute("submit", true);
			model.addAttribute("readonly", "readonly='readonly'");
		}
		model.addAttribute("layout", "layout-empty.vm");
		return "felicityvillage/table1-info";
	}
	
	//处理tab对应iframe的src 
	@RequestMapping("!{key}/table2.htm")
	public String tab2(Model model,Long id){//片区id
		FVModelAreaReportEntity ma = fvmaReportService.get(id);
		model.addAttribute("ma", ma);
		model.addAttribute("id", id);
		if(ma.getCountyReport().getStatus()==1){
			model.addAttribute("submit", true);
		}
		model.addAttribute("layout", "layout-empty.vm");
		return "felicityvillage/table2/table2-info";
	}
	
	
	
	//列出table1  数据表格
	@ResponseBody
	@RequestMapping("!{key}/felicityvillage/table1.json")
	public List<RuralInfo> Table1Json(Long id){//片区id
		List<RuralInfo> rinfos = fvmaReportService.checkRuralMakeList(id);
		return rinfos;
	}
	
	
	//点击添加村按钮
	@RequestMapping("!{key}/felicityvillage/addRural.do")
	public String addRural(Model model,Long id){//id为片区id
		
		List<FVCoreRuralEntity> crs = fvCoreRuralService.allCoreRural(id);
		FVModelAreaReportEntity ma = areaReportService.get(id);
		model.addAttribute("ma", ma);
		model.addAttribute("id", id);
		model.addAttribute("title", "添加村指标");
		model.addAttribute("crs", crs);
		model.addAttribute("layout", "layout-empty.vm");
		return "felicityvillage/rural-view";
	}
	
	//点击编辑按钮,编辑村子
	@RequestMapping("!{key}/felicityvillage/editorRural.do")
	public String editorRural(Model model,Long id){//id为村子
		model.addAttribute("title", "编辑");
		model.addAttribute("layout", "layout-empty.vm");
		FVBaseRuralEntity rural = baseRuralService.get(id);
		
		if(rural instanceof FVPeripheralRuralEntity){
			List<FVCoreRuralEntity> crs = fvCoreRuralService.allCoreRural(rural.getModelArea().getId());
			model.addAttribute("crs", crs);
			FVPeripheralRuralEntity prural = (FVPeripheralRuralEntity) rural;
			model.addAttribute("prural", prural);
			return "felicityvillage/p-rural-view";
		}else if (rural instanceof FVCoreRuralEntity){
			FVCoreRuralEntity crural = (FVCoreRuralEntity) rural;
			model.addAttribute("crural", crural);
			return "felicityvillage/c-rural-view";
		}else{
			return "";
		}
	}
	
	//修改示范片
	@ResponseBody
	@RequestMapping(value ="!{key}/felicityvillage/area.update", method = POST)
	public JsonResult AreaUpdate(HttpServletRequest request,Long id,String modelAreaName,Double constructionArea,Integer  households, Integer population){//片区报的id//
		try{
			fvCountyReportService.UpdateModelArea(id,modelAreaName,constructionArea,households,population);
			return JsonResult.result();
		}catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error(e);
		}
	}
	
	
	
	
	
	
	//保存村子::辐射村保存不了,包装类型转给基本类型 出的空指针
	@ResponseBody
	@RequestMapping(value ="!{key}/felicityvillage/rural.save", method = POST)
	public JsonResult ruralSave(Long id,Boolean ismaster,
			String crName,String crType,
			String prName,String prType,Long coreRural ,
			Boolean finishedPlan,Boolean finishedBid,
			
			Integer projectNum,Integer projectFinishNum,
			
			Date startTime,Date predictFinishTime,
			String constructProgress){
		if(startTime!=null && predictFinishTime!=null){
			long st = startTime.getTime();
			long pft = predictFinishTime.getTime();
			if(st>pft){
				Exception e = new RuntimeException("启动时间必须早于预计完成时间！");
				try {
					throw e ;
				} catch (Exception e1) {
					e1.printStackTrace();
					return JsonResult.error(e1);
				}
			}
		}
		
		try{
			LogonUser user = getUser();
			if(null == ismaster) ismaster=true;
			baseRuralService.saveRural(user,id,ismaster,crName,crType,prName,prType,coreRural,finishedPlan,finishedBid,
					projectNum,projectFinishNum,startTime,predictFinishTime,constructProgress);
			return JsonResult.result();
		}catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error(e);
		}
	}
	
	
	
	//保存主体村子:
	@ResponseBody
	@RequestMapping(value ="!{key}/felicityvillage/crural.save", method = POST)
	public JsonResult cruralSave(Long id,
			String crName,String crType,
			Boolean finishedPlan,Boolean finishedBid,
			Integer projectNum,Integer projectFinishNum,
			Date startTime,Date predictFinishTime,
			String constructProgress){
		if(startTime!=null && predictFinishTime!=null){
			long st = startTime.getTime();
			long pft = predictFinishTime.getTime();
			if(st>pft){
				Exception e = new RuntimeException("启动时间必须早于预计完成时间！");
				try {
					throw e ;
				} catch (Exception e1) {
					e1.printStackTrace();
					return JsonResult.error(e1);
				}
			}
		}
		try{
			LogonUser user = getUser();
			baseRuralService.updateCRural(user,id,crName,crType,finishedPlan,finishedBid,
					projectNum,projectFinishNum,startTime,predictFinishTime,constructProgress);
			return JsonResult.result();
		}catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error(e);
		}
	}
	
	
	//保存辐射村子:--->
	@ResponseBody
	@RequestMapping(value ="!{key}/felicityvillage/prural.save", method = POST)
	public JsonResult pruralSave(Long id,
			String prName,String prType,Long coreRural ,
			Integer projectNum,Integer projectFinishNum,
			Date startTime,Date predictFinishTime,
			String constructProgress){
		if(startTime!=null && predictFinishTime!=null){
			long st = startTime.getTime();
			long pft = predictFinishTime.getTime();
			if(st>pft){
				Exception e = new RuntimeException("启动时间必须早于预计完成时间！");
				try {
					throw e ;
				} catch (Exception e1) {
					e1.printStackTrace();
					return JsonResult.error(e1);
				}
			}
		}
		try{
			LogonUser user = getUser();
			baseRuralService.updatePRural(user,id,prName,prType,coreRural,
					projectNum,projectFinishNum,startTime,predictFinishTime,constructProgress);
			return JsonResult.result();
		}catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error(e);
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value ="!{key}/felicityvillage/deleteRural.do", method = POST)
	public JsonResult deleteRural(Long id){
		try{
			baseRuralService.deleteRural(id);
			return JsonResult.result();
		}catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error(e);
		}
	}
	
	
	//保存填报的那三个字段
	@ResponseBody
	@RequestMapping(value ="!{key}/table1.save", method = POST)
	public JsonResult table1Save(HttpServletRequest request,Long couId,String reporter1,String telephone1,Date reportTime1){//片区报的id//
		try{
			fvCountyReportService.updateReportInfo(couId,reporter1,telephone1,reportTime1);
			return JsonResult.result();
		}catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error(e);
		}
	}

	
	//-------------------------------------------------------------------------------------------
	
	
	/**
	 * 点击表2填报
	 * 		弹出框最大化(框里面用tab)
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("!{key}/report.table2")
	public String report2(Model model,Long id){//id为县报的id
		FVMACountyReportEntity cReport = fvCountyReportService.get(id);
		String startTime = sdf.format(cReport.getReport().getStartTime());
		String deadline = sdf.format(cReport.getReport().getDeadline());
		List<FVModelAreaReportEntity> mas = cReport.getModelAreas();
		model.addAttribute("mas", mas);
		model.addAttribute("id", id);
		model.addAttribute("cReport", cReport);
		model.addAttribute("title", "11个原中央苏区县幸福村居示范片项目库(二)");
		model.addAttribute("startTime", startTime);
		model.addAttribute("deadline", deadline);
		
		if(cReport.getStatus()==1){
			model.addAttribute("submit", true);
		}
		model.addAttribute("layout", "layout-empty.vm");
		return "felicityvillage/table2/table2-view";
	}
	
	
	//列出table2  数据表格
	@ResponseBody
	@RequestMapping("!{key}/table2.json")
	public List<ProjectInfo> Table2Json(Long id){//片区id
//		List<ProjectInfo> prinfos = fvProjectRuralService.makePjList(id);
		List<ProjectInfo> prinfos = fvProjectRuralService.makePjListByMaId(id);
		return prinfos;
	}
	
	
	
	//点击添加村项目按钮,,1.给示范片 2.,,,村  联动  选择片区的时候 load 村进来给选择 
	@RequestMapping("!{key}/addProject.do")
	public String addProject(Model model,Long id){//id为县报Id
//		FVMACountyReportEntity cReport = fvCountyReportService.get(id);
//		List<FVModelAreaReportEntity> mas = cReport.getModelAreas();
//		model.addAttribute("mas", mas);
		FVModelAreaReportEntity ma = areaReportService.get(id);
		
		model.addAttribute("ma", ma);
		model.addAttribute("title", "添加项目");
		model.addAttribute("layout", "layout-empty.vm");
		return "felicityvillage/table2/project-view";
		
		
	}
	
	//多选
	@ResponseBody
	@RequestMapping(value ="!{key}/serural.json", method = POST)
	public List<CommonItem>seRural(Long maId){
		List<CommonItem> items = new ArrayList<>(); 
		List<FVBaseRuralEntity>rurals  = baseRuralService.allRuralByModelArea(maId);
		for(FVBaseRuralEntity r:rurals){
			String text = r.getName()+"("+r.getType()+")";
			items.add(new CommonItem(r.getId()+"",text));
		}
		return items;
	}
	

	
	//保存项目
	@ResponseBody
	@RequestMapping(value ="!{key}/project.save", method = POST)
	public JsonResult projectSave(Long id,Long areaId,
			String pjType,String pjName,
			Long[]rurals,
			Double budgetMoney,Double finishMoney,
			Date startTime,Date finishTime,Date checkTime,
			String checkUnit){
		try{
			LogonUser user = getUser();
			fvProjectRuralService.saveProject(user,id,areaId,pjType,pjName,rurals,budgetMoney,finishMoney,
					startTime,finishTime,checkTime,checkUnit);
			return JsonResult.result();
		}catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error(e);
		}
	}
	
	
	//点击编辑按钮,编辑项目
	@RequestMapping("!{key}/editorProject.do")
	public String editorProject(Model model,Long id){//
		FVProjectRuralEntity pr = fvProjectRuralService.get(id);
		//List<FVBaseRuralEntity> rurals = pr.getRurals();
		
		List<FVModelAreaReportEntity> mas = pr.getModelArea().getCountyReport().getModelAreas();
		model.addAttribute("mas", mas);
		
		model.addAttribute("pr",pr);
		model.addAttribute("title", "编辑");
		FVModelAreaReportEntity ma = pr.getModelArea();
		model.addAttribute("ma", ma);
		model.addAttribute("layout", "layout-empty.vm");
		return "felicityvillage/table2/project-view";
	}
	
	
	@ResponseBody
	@RequestMapping(value ="!{key}/deleteProject.do", method = POST)
	public JsonResult deleteProject(Long id){
		try{
			fvProjectRuralService.deleteProject(id);
			return JsonResult.result();
		}catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error(e);
		}
	}
	
	
	
	//保存表2填报的那三个字段
	@ResponseBody
	@RequestMapping(value ="!{key}/table2.save", method = POST)
	public JsonResult table2Save(HttpServletRequest request,Long couId,String reporter2,String telephone2,Date reportTime2){//片区报的id//
		try{
			fvCountyReportService.updateReportInfo2(couId,reporter2,telephone2,reportTime2);
			return JsonResult.result();
		}catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error(e);
		}
	}
	
	//提交
//	@ResponseBody
//	@RequestMapping(value ="!{key}/report.submit", method = POST)
//	public JsonResult reportSubmit(HttpServletRequest request,Long[] codes,Long id){//县报的id  
//		try{
//			fvCountyReportService.submitReport(id);
//			return JsonResult.result();
//		}catch (Exception e) {
//			e.printStackTrace();
//			return JsonResult.error(e);
//		}
//	}
	
	@ResponseBody
	@RequestMapping(value ="!{key}/report.submit", method = POST)
	public int reportSubmit(HttpServletRequest request,Long[] codes,Long id){//县报的id
		/*
		 * status
		 * 0:可以提交
		 *	不能提交:
		 *   	1:建设    		字数<500
		 * 		2:存在问题  	字数>200
		 * 		3:备注 		字数>200
		 */
		int status  = fvCountyReportService.checkWords(id);
		if(status==0){
			fvCountyReportService.submitReport(id);
		}
		return status;
	}
	
	
	//---------------------导出excel------------------------------------------------------------------
	
	//导出table1
	@RequestMapping(value="!{key}/table1.excel",method = {POST,GET} )
	public void exportTable1(HttpServletRequest request,HttpServletResponse response,Model model,Long id){
		fvCountyReportService.exportTable1(request,response,id);
	}
	
	//导出table2
	@RequestMapping(value="!{key}/table2.excel",method = {POST,GET} )
	public void exportTable2(HttpServletRequest request,HttpServletResponse response,Model model,Long id){
		fvCountyReportService.exportTable2(request,response,id);
	}
	
	//导出table3
	@RequestMapping(value="!{key}/table3.excel",method = {POST,GET} )
	public void exportTable3(HttpServletRequest request,HttpServletResponse response,Model model,Long id){
		fvCountyReportService.exportTable3(request,response,id);
	}
}


