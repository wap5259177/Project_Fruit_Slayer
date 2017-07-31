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

import cn.bonoon.core.BatchInvestService;
import cn.bonoon.core.CityInvestService;
import cn.bonoon.core.PCInvestService;
import cn.bonoon.core.information.ProReportInfo;
import cn.bonoon.entities.informatioin.BatchCapitalInvestmentInformationEntity;
import cn.bonoon.entities.informatioin.CityCapitalInvestmentInformationEntity;
import cn.bonoon.entities.informatioin.ProvinceCapitalInvestmentInformationEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.JsonResult;
import cn.bonoon.kernel.web.models.PanelModel;

@Controller
@RequestMapping("s/ml/information/city/invest")
public class CityInvestReportController extends AbstractPanelController{

	@Autowired
	private PCInvestService pcInvestService;
	
	@Autowired
	private CityInvestService cityInvestService;
	
	@Autowired
	private BatchInvestService batchInvestService;
	
	
	
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		Long oid = event.getUser().getOwnerId();
		model.addObject("oid", oid);
		model.addObject("title", "资金投入情况填报:");
		event.setVmPath("information/report-view");
	}
	
	
	
	//列出各个上报的年度list  数据表格
	@ResponseBody
	@RequestMapping("!{key}/report.json")
	public List<ProReportInfo> report(){
		List<ProvinceCapitalInvestmentInformationEntity> reports = pcInvestService.allReport();
		 List<CityCapitalInvestmentInformationEntity> cityReports = cityInvestService.allCountyReport(getUser());
		
		List<CityCapitalInvestmentInformationEntity> hasR =  new ArrayList<>();//已经点了开始上报的
		Map<Long,ProvinceCapitalInvestmentInformationEntity> notR = new HashMap<>();//没有电开始上报的
		List<ProvinceCapitalInvestmentInformationEntity> maybeR =  new ArrayList<>();//可能已经开始上报
		
		for(ProvinceCapitalInvestmentInformationEntity fvr:reports){
			if(cityReports.size()==0){
				notR.put(fvr.getId(), fvr);
			}else{
				for(CityCapitalInvestmentInformationEntity fvcr:cityReports){
					ProvinceCapitalInvestmentInformationEntity r = fvcr.getPciInformation();
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
		for(CityCapitalInvestmentInformationEntity fvcr:hasR){
			for(ProvinceCapitalInvestmentInformationEntity mr:maybeR){
				if(fvcr.getPciInformation().getId().equals(mr.getId())){
					notR.remove(mr.getId());
				}
			}
		}
		
		List<ProReportInfo> infoData = new ArrayList<>();
		for(Map.Entry<Long, ProvinceCapitalInvestmentInformationEntity> entry:notR.entrySet()){
			ProvinceCapitalInvestmentInformationEntity r = entry.getValue();
			infoData.add(new ProReportInfo(r));
		}
		for(CityCapitalInvestmentInformationEntity fvcr:hasR){
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
			 * 点击开始上报的时候,顺便创建那三条固定的batchcapi...
			 */
			pcInvestService.toReport(getUser(),reportId);
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
		CityCapitalInvestmentInformationEntity cityReport = cityInvestService.get(cityRportId);
		String cityName = cityReport.getPlace().getName();
		ProvinceCapitalInvestmentInformationEntity report = cityReport.getPciInformation();
		model.addAttribute("cityName", cityName);
		model.addAttribute("deadline", sdf.format(report.getDeadline()));
		
		
		List<BatchCapitalInvestmentInformationEntity> batchs = cityReport.getItems();
		model.addAttribute("batchs", batchs);
		
		model.addAttribute("cityReport", cityReport);
		model.addAttribute("cityReportId", cityReport.getId());
		if(cityReport.getStatus()==1 || cityReport.getStatus()==4){//提交和待审核状态下的这条记录,进去都不能修改
			model.addAttribute("readonly", "readonly='readonly'");
			model.addAttribute("submit", true);
		}
		model.addAttribute("layout", "layout-empty.vm");
		//打印的情况
		if(print!=null&&print==true){
			model.addAttribute("title", "广东省"+cityName+"新农村建设资金投入情况表");
			return "information/city-report/money-view-view";
		}
		return "information/city-report/money-view";
	}
	
	
	/***
	 * 保存invests  其实只是做更新
	 * @param request
	 * @param cityReportId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value ="!{key}/invest.save", method = POST)
	public JsonResult saveInvest(HttpServletRequest request,Long cityReportId){//片区报的id//
		try{
			CityCapitalInvestmentInformationEntity cityReport = cityInvestService.get(cityReportId);
			List<BatchCapitalInvestmentInformationEntity> batchs = cityReport.getItems();
			
			for(BatchCapitalInvestmentInformationEntity bc:batchs){
				String projectStartCount_ = request.getParameter("projectStartCount_"+bc.getId());
				String projectFinishCount_ = request.getParameter("projectFinishCount_"+bc.getId());
				String totalFunds_ = request.getParameter("totalFunds_"+bc.getId());
				String funds0_ = request.getParameter("funds0_"+bc.getId());
				String funds1_ = request.getParameter("funds1_"+bc.getId());
				String funds2_ = request.getParameter("funds2_"+bc.getId());
				String funds3_ = request.getParameter("funds3_"+bc.getId());
				String funds4_ = request.getParameter("funds4_"+bc.getId());
				String funds5_ = request.getParameter("funds5_"+bc.getId());
				
				bc.setProjectStartCount(StringHelper.toint(projectStartCount_));
				bc.setProjectFinishCount(StringHelper.toint(projectFinishCount_));
				bc.setTotalFunds(StringHelper.todouble(totalFunds_));
				bc.setFunds0(StringHelper.todouble(funds0_));
				bc.setFunds1(StringHelper.todouble(funds1_));
				bc.setFunds2(StringHelper.todouble(funds2_));
				bc.setFunds3(StringHelper.todouble(funds3_));
				bc.setFunds4(StringHelper.todouble(funds4_));
				bc.setFunds5(StringHelper.todouble(funds5_));
			}
			batchInvestService.saveBatchs(batchs,cityReport);
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
			cityInvestService.submitReport(id);
			return JsonResult.result();
		}catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error(e);
		}
	}
	
	
}
