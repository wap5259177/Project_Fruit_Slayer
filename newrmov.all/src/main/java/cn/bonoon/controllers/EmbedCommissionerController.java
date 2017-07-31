package cn.bonoon.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.core.CommissionerService;
import cn.bonoon.entities.CommissionerEntity;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.controllers.AbstractController;
import cn.bonoon.kernel.web.models.JsonResult;

@Controller
@RequestMapping("s")
public class EmbedCommissionerController extends AbstractController{
	
	@Autowired
	private CommissionerService commissionerService;

	//信息专员的处理
	@RequestMapping("cl/commissioner.embed")
	public String cl(Model model){
		LogonUser user = getUser();
		CommissionerEntity f = commissionerService.getByUser(user);
		model.addAttribute("form", f);
		return "cl-host-commissioner";
	}
	@ResponseBody
	@RequestMapping("cl/commissioner.submit")
	public JsonResult cl(String name, String job, String phone1, String phone2, String remark,String qqNum){
		try{
			LogonUser user = getUser();
			commissionerService.save(user, name, job, phone1, phone2, remark,qqNum);
			return JsonResult.result();
		}catch (Exception e) {
			return JsonResult.error(e);
		}
	}
	//信息专员的处理
	@RequestMapping("ml/commissioner.embed")
	public String ml(Model model){
		LogonUser user = getUser();
		CommissionerEntity f = commissionerService.getByUser(user);
		model.addAttribute("form", f);
		return "ml-host-commissioner";
	}
	@ResponseBody
	@RequestMapping("ml/commissioner.submit")
	public JsonResult ml(String name, String job, String phone1, String phone2, String remark,String qqNum){
		try{
			LogonUser user = getUser();
			commissionerService.save(user, name, job, phone1, phone2, remark,qqNum);
			return JsonResult.result();
		}catch (Exception e) {
			return JsonResult.error(e);
		}
	}
	//信息专员的处理
	@RequestMapping("pl/commissioner.embed")
	public String pl(Model model){
		List<CommissionerEntity> items = commissionerService.find(" and x.history=false");
		model.addAttribute("items", items);
		return "pl-host-commissioner";
	}
}
