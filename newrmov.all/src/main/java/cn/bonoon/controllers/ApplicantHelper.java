package cn.bonoon.controllers;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.entities.EvaluatePointEntity;
import cn.bonoon.kernel.web.models.DialogModel;


public class ApplicantHelper {
	protected static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("##.##");
	protected static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	protected static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy 年 MM 月 dd 日");


	protected static void label(ModelAndView model, EvaluatePointEntity<?> tep, String code) {
		String ipt = "<td style='text-align:center;' valign='middle'>"
				+ DECIMAL_FORMAT.format(tep.getPointSelf())+"</td><td style='text-align:center;' valign='middle'>"
				+ DECIMAL_FORMAT.format(tep.getPointAudit())+"</td>";
		model.addObject(code, ipt);
	}
	protected static void input(DialogModel model, String name, Object value){
		String ipt = "<input type='text' name='" + name;
		if(null != value){
			ipt += "' value='" + value;
		}
		ipt += "'/>";
		model.addObject(name, ipt);
	}
	protected static void textarea(DialogModel model, String name, Object value){
		String ipt = "<textarea name='" + name + "'>";
		if(null != value){
			ipt += value;
		}
		ipt += "</textarea>";
		model.addObject(name, ipt);
	}
	
	protected static void number(DialogModel model, String name, Object value){
		String ipt = "<input type='text' name='" + name;
		if(null != value){
			ipt += "' value='" + value;
		}
		ipt += "' class='num-input-class'/>";
		model.addObject(name, ipt);
	}

}
