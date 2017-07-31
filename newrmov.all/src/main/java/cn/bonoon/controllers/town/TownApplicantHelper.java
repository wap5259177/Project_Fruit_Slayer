package cn.bonoon.controllers.town;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.controllers.ApplicantHelper;
import cn.bonoon.controllers.town.back.BackTownApplicantEditor;
import cn.bonoon.core.TownApplicantService;
import cn.bonoon.entities.TownEvaluatePointEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.models.DialogModel;

public class TownApplicantHelper extends ApplicantHelper{
	private static final Map<String, Integer> codes;
	static{ 
		codes = new HashMap<>();
		codes.put("AA1", 3);
		codes.put("AA2", 3);
		codes.put("AA3", 3);
		codes.put("AA4", 3); 
		codes.put("AA5", 2); 
		codes.put("AA6", 2);
		codes.put("AA7", 2); 
		codes.put("AB1", 4);
		codes.put("AB2", 4);
		codes.put("AB3", 4); 
		codes.put("AB4", 3);
		codes.put("AB5", 3);
		codes.put("AB6", 2);
		codes.put("AC1", 3);
		codes.put("AC2", 4);
		codes.put("AC3", 3);
		codes.put("AD1", 4);
		codes.put("AD2", 4);
		codes.put("AD3", 2);
		codes.put("BA1", 4);
		codes.put("BB1", 4);
		codes.put("BB2", 4);
		codes.put("C1", 5);
		codes.put("C2", 5);
		codes.put("C3", 5);
		codes.put("C4", 5);
		codes.put("C5", 10);
		codes.put("D", 100);
	}
	public static void view(ModelAndView model, List<TownEvaluatePointEntity> eps){
		Map<String, Integer> cs = new HashMap<>(codes);
		if(null != eps){
			for(TownEvaluatePointEntity tep : eps){
				String code = tep.getCode();
				Integer val = cs.remove(code);
				if(null == val){ continue; }
				label(model, tep, code);
			}
		}
		for(Entry<String, Integer> it : cs.entrySet()){
			model.addObject(it.getKey(), "<td></td><td></td>");
		}
	}

//	private static void label(ModelAndView model, TownEvaluatePointEntity tep, String code) {
//		String ipt = "<td style='text-align:center;' valign='middle'>"
//				+ DECIMAL_FORMAT.format(tep.getPointSelf())+"</td><td style='text-align:center;' valign='middle'>"
//				+ DECIMAL_FORMAT.format(tep.getPointAudit())+"</td>";
//		model.addObject(code, ipt);
//	}

	public static void view(TownApplicantService taService, DialogModel model, DialogEvent event, Object form, boolean audit) {
		if(null != form){
			BaseTownApplicantDetail tae = (BaseTownApplicantDetail)form;
			model.addObject("area", tae.getArea());
			model.addObject("cityPopulation", tae.getCityPopulation());
			model.addObject("downtownArea", tae.getDowntownArea());
			model.addObject("farmerPopulation", tae.getFarmerPopulation());
			model.addObject("financialIncome", tae.getFinancialIncome());
			model.addObject("gdp", tae.getGdp());
			model.addObject("tempPopulation", tae.getTempPopulation());
			model.addObject("famousBrandNation", tae.getFamousBrandNation());
			model.addObject("famousBrandProvince", tae.getFamousBrandProvince());
			model.addObject("honorCity", tae.getHonorCity());
			model.addObject("honorNation", tae.getHonorNation());
			model.addObject("honorProvince", tae.getHonorProvince());
			model.addObject("themeDirection", tae.getThemeDirection());
			model.addObject("personIncome", tae.getPersonIncome());
			model.addObject("population", tae.getPopulation());
			
			model.addObject("place", tae.getName());
			model.addObject("countyName", tae.getCountyName());

			model.addObject("investmentOther", tae.getInvestmentOther());
			model.addObject("investmentNation", tae.getInvestmentNation());
			model.addObject("investmentProvince", tae.getInvestmentProvince());
			model.addObject("investmentLocal", tae.getInvestmentLocal());
			model.addObject("investmentSocial", tae.getInvestmentSocial());
			model.addObject("investmentSelf", tae.getInvestmentSelf());
			model.addObject("totalInvestment", tae.getTotalInvestment());
			
			if(null != tae.getAat()){
				model.addObject("aat", sdf2.format(tae.getAat()));
			}
			
			model.addObject("opinion1", tae.getOpinion1());
			if(null != tae.getOpinionAt1()){
				model.addObject("opinionAt1", sdf2.format(tae.getOpinionAt1()));
			}else{
				model.addObject("opinionAt1", "2014 年  月  日");
			}
			if(audit){
				textarea(model, "opinion2", tae.getOpinion2());
				if(null != tae.getOpinionAt2()){
					input(model, "opinionAt2", sdf1.format(tae.getOpinionAt2()));
				}else{
					input(model, "opinionAt2", sdf1.format(new Date()));
				}
				textarea(model, "opinion3", tae.getOpinion3());
				if(null != tae.getOpinionAt3()){
					input(model, "opinionAt3", sdf1.format(tae.getOpinionAt3()));
				}else{
					input(model, "opinionAt3", sdf1.format(new Date()));
				}
			}else{
				model.addObject("opinion2", tae.getOpinion2());
				if(null != tae.getOpinionAt2()){
					model.addObject("opinionAt2", sdf2.format(tae.getOpinionAt2()));
				}else{
					model.addObject("opinionAt2", "2014 年  月  日");
				}
				model.addObject("opinion3", tae.getOpinion3());
				if(null != tae.getOpinionAt3()){
					model.addObject("opinionAt3", sdf2.format(tae.getOpinionAt3()));
				}else{
					model.addObject("opinionAt3", "2014 年  月  日");
				}
			}
			model.addObject("opinion4", tae.getOpinion4());
			if(null != tae.getOpinionAt4()){
				model.addObject("opinionAt4", sdf2.format(tae.getOpinionAt4()));
			}else{
				model.addObject("opinionAt4", "2014 年  月  日");
			}
			model.addObject("opinion5", tae.getOpinion5());
			if(null != tae.getOpinionAt5()){
				model.addObject("opinionAt5", sdf2.format(tae.getOpinionAt5()));
			}else{
				model.addObject("opinionAt5", "2014 年  月  日");
			}
		}
	}
	
	public static void edit(ModelAndView model, List<TownEvaluatePointEntity> eps){
		Map<String, Integer> cs = new HashMap<>(codes);
		if(null != eps){
			for(TownEvaluatePointEntity tep : eps){
				String code = tep.getCode();
				Integer val = cs.remove(code);
				if(null == val){ continue; }
				if("D".equals(code)){
					label(model, tep, code);
					continue;
				}
				String ipt = "<td valign='middle'><input type='hidden' name='codes' value='"
						+ code+"'/><input type='hidden' name='"
						+ code+"-value' value='"
						+ val+"'/><input class='num-input-class' type='text' name='"
						+ code+"-self' value='"
						+ DECIMAL_FORMAT.format(tep.getPointSelf())+"'/></td><td valign='middle'><input type='text' name='"
						+ code+"-audit' value='"
						+ DECIMAL_FORMAT.format(tep.getPointAudit())+"' class='num-input-class'/></td>";
				model.addObject(code, ipt);
			}
		}
		for(Entry<String, Integer> it : cs.entrySet()){
			String code = it.getKey();
			if("D".equals(code)){
				model.addObject(code, "<td></td><td></td>");
				continue;
			}
			Integer val = it.getValue();
			String ipt = "<td><input type='hidden' name='codes' value='"
					+ code+"'/><input type='hidden' name='"
					+ code+"-value' value='"
					+ val+"'/><input class='num-input-class' type='text' name='"
					+ code+"-self'/></td><td><input type='text' name='"
					+ code+"-audit' class='num-input-class'/></td>";
			model.addObject(code, ipt);
		}
	}


	public static void reject(TownApplicantService taService, DialogModel model, DialogEvent event, Object form) {
		// rejectAt
		model.addObject("reject-view", true);
		edit(taService, model, event, form);
		BackTownApplicantEditor bae = (BackTownApplicantEditor)form;
		if(null != bae.getRejectAt()){
			model.addObject("rejectAt", sdf2.format(bae.getRejectAt()));
		}
		if(StringHelper.isEmpty(bae.getRejectContent())){
			model.addObject("rejectContent", "<span style='color:red'>无</span>");
		}else{
			model.addObject("rejectContent", bae.getRejectContent());
		}
	}
	
	public static void edit(TownApplicantService taService, DialogModel model, DialogEvent event, Object form) {
		Long selectedPlace = null;
		if(null != form){
			BaseTownApplicantEditor tae = (BaseTownApplicantEditor)form;
			selectedPlace = tae.getPlace();
			number(model, "area", tae.getArea());
			number(model, "cityPopulation", tae.getCityPopulation());
			number(model, "downtownArea", tae.getDowntownArea());
			number(model, "farmerPopulation", tae.getFarmerPopulation());
			number(model, "financialIncome", tae.getFinancialIncome());
			number(model, "gdp", tae.getGdp());
			number(model, "tempPopulation", tae.getTempPopulation());
			number(model, "personIncome", tae.getPersonIncome());
			number(model, "population", tae.getPopulation());
			
			input(model, "famousBrandNation", tae.getFamousBrandNation());
			input(model, "famousBrandProvince", tae.getFamousBrandProvince());
			input(model, "honorCity", tae.getHonorCity());
			input(model, "honorNation", tae.getHonorNation());
			input(model, "honorProvince", tae.getHonorProvince());
			input(model, "themeDirection", tae.getThemeDirection());
			
			number(model, "investmentOther", tae.getInvestmentOther());
			number(model, "investmentNation", tae.getInvestmentNation());
			number(model, "investmentProvince", tae.getInvestmentProvince());
			number(model, "investmentLocal", tae.getInvestmentLocal());
			number(model, "investmentSocial", tae.getInvestmentSocial());
			number(model, "investmentSelf", tae.getInvestmentSelf());
			model.addObject("totalInvestment", tae.getTotalInvestment());
			
			textarea(model, "opinion1", tae.getOpinion1());
			if(null != tae.getOpinionAt1()){
				input(model, "opinionAt1", sdf1.format(tae.getOpinionAt1()));
			}else{
				input(model, "opinionAt1", sdf1.format(new Date()));
			}
		}else{
			number(model, "area", "");
			number(model, "cityPopulation", "");
			number(model, "downtownArea", "");
			number(model, "farmerPopulation", "");
			number(model, "financialIncome", "");
			number(model, "gdp", "");
			number(model, "tempPopulation", "");
			number(model, "personIncome", "");
			number(model, "population", "");
			
			input(model, "famousBrandNation", "");
			input(model, "famousBrandProvince", "");
			input(model, "honorCity", "");
			input(model, "honorNation", "");
			input(model, "honorProvince", "");
			input(model, "themeDirection", "");

			number(model, "investmentNation", "");
			number(model, "investmentProvince", "");
			number(model, "investmentLocal", "");
			number(model, "investmentSocial", "");
			number(model, "investmentSelf", "");
			number(model, "investmentOther", "");
			
			textarea(model, "opinion1", "");
			input(model, "opinionAt1", sdf1.format(new Date()));
		}
		model.addObject("opinion2", "");
		model.addObject("opinionAt2", "2014 年  月  日");
		model.addObject("opinion3", "");
		model.addObject("opinionAt3", "2014 年  月  日");
		model.addObject("opinion4", "");
		model.addObject("opinionAt4", "2014 年  月  日");
		model.addObject("opinion5", "");
		model.addObject("opinionAt5", "2014 年  月  日");
		
		//取出县下的所有镇
		List<PlaceEntity> places = taService.towns(event);
		StringBuilder selPlace = new StringBuilder();
		selPlace.append("<select name='place'>");
		for(PlaceEntity place : places){
			Long pid = place.getId();
			selPlace.append("<option value='").append(pid);
			if(pid.equals(selectedPlace)){
				selPlace.append("' selected='selected");
			}
			selPlace.append("'>").append(place.getName());
			selPlace.append("</option>");
		}
		selPlace.append("</select>");
		model.addObject("place", selPlace);
	}
}
