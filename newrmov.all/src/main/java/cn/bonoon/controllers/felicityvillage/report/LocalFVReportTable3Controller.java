package cn.bonoon.controllers.felicityvillage.report;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.core.FVCountyReportService;
import cn.bonoon.entities.felicityVillage.FVInvestmentInfo;
import cn.bonoon.entities.felicityVillage.FVMACountyReportEntity;
import cn.bonoon.entities.felicityVillage.FVModelAreaReportEntity;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.controllers.AbstractController;
import cn.bonoon.kernel.web.models.JsonResult;
import cn.bonoon.util.DoubleHelper;

@Controller
@RequestMapping("s/cl/fv/county/report")
public class LocalFVReportTable3Controller extends AbstractController{
	@Autowired
	private FVCountyReportService fvCountyReportService;

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	@RequestMapping("!{key}/report.table3")
	public String report1(Model model,Long id){//id为县报的id
		FVMACountyReportEntity cReport = fvCountyReportService.get(id);
		String startTime = sdf.format(cReport.getReport().getStartTime());
		String deadline = sdf.format(cReport.getReport().getDeadline());
		List<FVModelAreaReportEntity> mas = cReport.getModelAreas();
		model.addAttribute("mas", mas);
		model.addAttribute("id", id);
		model.addAttribute("cReport", cReport);
		model.addAttribute("startTime", startTime);
		model.addAttribute("deadline", deadline);
		
		if(cReport.getStatus()==1||cReport.getStatus()==4){//完成和待审核都不能进去修改了
			model.addAttribute("submit", true);
			model.addAttribute("readonly", "readonly='readonly'");
		}
		model.addAttribute("layout", "layout-empty.vm");
		return "felicityvillage/table3-view";
	}
	@ResponseBody
	@RequestMapping(value ="!{key}/{id}!table3.save", method = POST)
	public JsonResult table1Save(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam(value = "reporter3", required = false) String reporter3,
			@RequestParam(value = "telephone3", required = false) String telephone3,
			@RequestParam(value = "reportTime3", required = false) Date reportTime3){//片区报的id//
		try{
			//fvCountyReportService.updateReportInfo(couId,reporter1,telephone1,reportTime1);
			FVMACountyReportEntity cReport = fvCountyReportService.get(id);
			List<FVModelAreaReportEntity> mas = new ArrayList<>();
			FVInvestmentInfo aii = new FVInvestmentInfo();
			double ait = 0;
			for(FVModelAreaReportEntity ar : cReport.getModelAreas()){
				Long iid = ar.getId();
				ar.setFeature(request.getParameter("fea_" + iid));
				ar.setProblem(request.getParameter("que_" + iid));
				ar.setRemark(request.getParameter("rem_" + iid));
				
				FVInvestmentInfo ii = new FVInvestmentInfo();
				ii.setProvinceFunds(parse(request, "pro_", iid));
				ii.setCityFunds(parse(request, "cit_", iid));
				ii.setCountyFunds(parse(request, "cou_", iid));
				ii.setSocialFunds(parse(request, "soc_", iid));
				ii.setMassFunds(parse(request, "mas_", iid));
				ii.setOtherFunds(parse(request, "oth_", iid));
				
				ii.sumSelf();
				ar.setInvestment(ii);
				ar.setInvested(parse(request, "hbf_", iid));
				mas.add(ar);
				
				aii.sum(ii);
				
				ait = DoubleHelper.add(ait, ar.getInvested());
			}
			cReport.setInvested(ait);
			cReport.setInvestment(aii);
			cReport.setReporter3(reporter3);
			cReport.setTelephone3(telephone3);
			cReport.setReportTime3(reportTime3);
			fvCountyReportService.update(getUser(), cReport, mas);
			return JsonResult.result();
		}catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error(e);
		}
	}
	private double parse(HttpServletRequest request, String pre, Long id){
		String str = request.getParameter(pre + id);
		return StringHelper.todouble(str);
	}
}
