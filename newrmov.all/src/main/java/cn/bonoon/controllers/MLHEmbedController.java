package cn.bonoon.controllers;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.SurveySummaryCityService;
import cn.bonoon.entities.SurveySummaryCityEntity;
import cn.bonoon.entities.SurveySummaryProvinceEntity;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.util.StringHelper;

@Controller
@RequestMapping("s/ml")
public class MLHEmbedController extends BaseHEmbedController{

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	@Autowired
	private SurveySummaryCityService surveySummaryCityService;
	@RequestMapping("host.embed")
	public String get(Model model){
		LogonUser user = getUser();
		__embedReport(user, model);
		try{
			String ssmsg = "";
			List<SurveySummaryCityEntity> items = surveySummaryCityService.get(user);
			List<SurveySummaryCityEntity> urges = surveySummaryCityService.getUrges(user);
			ItemInfo ii = moduleManager.find("ML_SYSTEM", "35", "2");
			if(!urges.isEmpty()){
				ssmsg = "<div class='embed-content-title' style='color:red;'>省办催报[进入功能：<a href='"
						+ ii.getHref() + "' title='进入"
						+ ii.getName() + "' style='color:blue;'>"
						+ ii.getName() + "</a>]:</div>";
				int i;
				if(urges.size() > 1){
					i = 1;
				}else{
					i = 0;
				}
				for(SurveySummaryCityEntity rri : urges){
					SurveySummaryProvinceEntity province = rri.getProvince();
					ssmsg += "<div class='embed-content-item'> ";
					if(i > 0){
						ssmsg += i++ + ". ";
					}
					ssmsg += province.getAnnual() + "年度(截止时间：" + sdf.format(province.getDeadline()) + ")";
					if(rri.getUrge() > 1){
						ssmsg += "[已催报<b style='color:red;'>" + rri.getUrge() + "次</b>]";
					}
					ssmsg += "</div>";
				}
				ssmsg += "<div style='padding-top:10px;'></div>";
			}
			if(!items.isEmpty()){
				ssmsg += "<div class='embed-content-title'>您需要上报以下摸底调查汇总表：[进入功能：<a href='"
					+ ii.getHref() + "' title='进入"
					+ ii.getName() + "'>"
					+ ii.getName() + "</a>]</div>";
				int i;
				if(items.size() > 1){
					i = 1;
				}else{
					i = 0;
				}
				for(SurveySummaryCityEntity rri : items){
					SurveySummaryProvinceEntity province = rri.getProvince();
					ssmsg += "<div class='embed-content-item'> ";
					if(i > 0){
						ssmsg += i++ + ". ";
					}					
					ssmsg += province.getAnnual() + "年度(截止时间：" + sdf.format(province.getDeadline()) + ")";

					ssmsg += "；上报时间：" + StringHelper.date2String(province.getStartAt()) 
							+ " 至 " + StringHelper.date2String(province.getEndAt())
							+ "</div>";
				}
				ssmsg += "<div style='padding-top:10px;'></div>";
			}

			if(ssmsg.isEmpty()){
				ssmsg = "<div class='embed-content-message'>当前没有要求上报文档！</div>";
			}	
			model.addAttribute("ssmsg", ssmsg);
		}catch(Exception ex){
			log.error(ex.getMessage());
			model.addAttribute("ssmsg", "<div class='embed-content-message'>无法读取幸福村居的填报情况！</div>");
		}
		return "ml-host";
	}

	@Override
	protected ItemInfo __menuReport() {
		return moduleManager.find("ML_SYSTEM", "5", "1", "1");
	}

	@Override
	protected ItemInfo __menuSupplement() {
//		return moduleManager.find("CL_SYSTEM", "5", "1", "5");
		return moduleManager.find("ML_SYSTEM", "5", "1", "1");
	}

	@Override
	protected ItemInfo __menuQuarterReport() {
		//TODO:季度上报 催报
		return null;
	}
}
