package cn.bonoon.controllers.information.report;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.core.CityVillageService;
import cn.bonoon.core.CountyVillageService;
import cn.bonoon.core.PCVillageService;
import cn.bonoon.core.information.ProReportInfo;
import cn.bonoon.entities.informatioin.CityNaturalVillageInformationEntity;
import cn.bonoon.entities.informatioin.CountyNaturalVillageInformationEntity;
import cn.bonoon.entities.informatioin.ProvinceNaturalVillageInformationEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.JsonResult;
import cn.bonoon.kernel.web.models.PanelModel;

@Controller
@RequestMapping("s/ml/information/city/village")
public class CityVillageReportController extends AbstractPanelController{

	@Autowired
	private PCVillageService pcVillageService;
	
	@Autowired
	private CityVillageService cityVillageService;
	
	@Autowired
	private CountyVillageService countyVillageService;
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		Long oid = event.getUser().getOwnerId();
		model.addObject("oid", oid);
		model.addObject("title", "自然村情况表填报:");
		event.setVmPath("information/report-view");
	}
	
	
	
	//列出各个上报的年度list  数据表格
	@ResponseBody
	@RequestMapping("!{key}/report.json")
	public List<ProReportInfo> report(){
		
		List<ProvinceNaturalVillageInformationEntity> reports = pcVillageService.allReport();
		 List<CityNaturalVillageInformationEntity> cityReports = cityVillageService.allCountyReport(getUser());
		
		List<CityNaturalVillageInformationEntity> hasR =  new ArrayList<>();//已经点了开始上报的
		Map<Long,ProvinceNaturalVillageInformationEntity> notR = new HashMap<>();//没有电开始上报的
		List<ProvinceNaturalVillageInformationEntity> maybeR =  new ArrayList<>();//可能已经开始上报
		
		for(ProvinceNaturalVillageInformationEntity fvr:reports){
			if(cityReports.size()==0){
				notR.put(fvr.getId(), fvr);
			}else{
				for(CityNaturalVillageInformationEntity fvcr:cityReports){
					ProvinceNaturalVillageInformationEntity r = fvcr.getPnvInformation();
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
		for(CityNaturalVillageInformationEntity fvcr:hasR){
			for(ProvinceNaturalVillageInformationEntity mr:maybeR){
				if(fvcr.getPnvInformation().getId().equals(mr.getId())){
					notR.remove(mr.getId());
				}
			}
		}
		
		List<ProReportInfo> infoData = new ArrayList<>();
		for(Map.Entry<Long, ProvinceNaturalVillageInformationEntity> entry:notR.entrySet()){
			ProvinceNaturalVillageInformationEntity r = entry.getValue();
			infoData.add(new ProReportInfo(r));
		}
		for(CityNaturalVillageInformationEntity fvcr:hasR){
			infoData.add(new ProReportInfo(fvcr));
		}
		return infoData;
	}
	
	
	/**
	 * 点击开始填报
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value ="!{key}/toReport.do", method = POST)
	public JsonResult toReport(HttpServletRequest request,Long reportId){
		try{
			/*
			 * 点击开始上报的时候,顺便这个市下面有多少个县就创建多少条
			 * CountyNaturalVillageInformationEntity
			 */
			pcVillageService.toReport(getUser(),reportId);
			return JsonResult.result();
		}catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error(e);
		}
	}
	
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	/**
	 * 点击填报按钮
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("!{key}/report.inves")
	public String report1(Model model,Long cityRportId,Boolean print){//id为市报的id
		CityNaturalVillageInformationEntity cityReport = cityVillageService.get(cityRportId);
		String cityName = cityReport.getPlace().getName();
		ProvinceNaturalVillageInformationEntity report = cityReport.getPnvInformation();
		model.addAttribute("cityName", cityName);
		model.addAttribute("deadline", sdf.format(report.getDeadline()));
		
		
		List<CountyNaturalVillageInformationEntity> counties = cityReport.getItems();
		model.addAttribute("counties", counties);
		
		model.addAttribute("cityReportId", cityReport.getId());
		model.addAttribute("cityReport", cityReport);
		
		if(cityReport.getStatus()==1 || cityReport.getStatus()==4){
			model.addAttribute("readonly", "readonly='readonly'");
			model.addAttribute("submit", true);
		}
		
		if(print!=null&&print==true){
			model.addAttribute("title", "广东省"+cityName+"自然村户数情况表");
			return "information/city-report/village-view-view";
		}
		model.addAttribute("layout", "layout-empty.vm");
		return "information/city-report/village-view";
	}
	
	
	/***
	 * 保存invests  其实只是做更新
	 * @param request
	 * @param cityReportId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value ="!{key}/village.save", method = POST)
	public JsonResult saveInvest(HttpServletRequest request,Long cityReportId){//片区报的id//
		try{
			CityNaturalVillageInformationEntity cityReport = cityVillageService.get(cityReportId);
			List<CountyNaturalVillageInformationEntity> counties = cityReport.getItems();
			
			for(CountyNaturalVillageInformationEntity bc:counties){
				String householdCount_ = request.getParameter("householdCount_"+bc.getId());
				String peopleCount_ = request.getParameter("peopleCount_"+bc.getId());
				String avCount_ = request.getParameter("avCount_"+bc.getId());
				String household0_ = request.getParameter("household0_"+bc.getId());
				String household1_ = request.getParameter("household1_"+bc.getId());
				String household2_ = request.getParameter("household2_"+bc.getId());
				String household3_ = request.getParameter("household3_"+bc.getId());
				String household4_ = request.getParameter("household4_"+bc.getId());
				
				bc.setHouseholdCount(StringHelper.toint(householdCount_));
				bc.setPeopleCount(StringHelper.toint(peopleCount_));
				bc.setAvCount(StringHelper.toint(avCount_));
				bc.setHousehold0(StringHelper.toint(household0_));
				bc.setHousehold1(StringHelper.toint(household1_));
				bc.setHousehold2(StringHelper.toint(household2_));
				bc.setHousehold3(StringHelper.toint(household3_));
				bc.setHousehold4(StringHelper.toint(household4_));
			}
			countyVillageService.saveCountys(counties,cityReport);
			return JsonResult.result();
		}catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error(e);
		}
	}
	
	
	//提交
	@ResponseBody
	@RequestMapping(value ="!{key}/report.submit", method = POST)
	public JsonResult reportSubmit(HttpServletRequest request,Long[] codes,Long id){//县报的id  
		try{
			cityVillageService.submitReport(id);
			return JsonResult.result();
		}catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error(e);
		}
	}

	
	
}
