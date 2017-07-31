package cn.bonoon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.core.TownApplicantService;
import cn.bonoon.core.VillageApplicantService;
import cn.bonoon.kernel.web.controllers.AbstractController;
import cn.bonoon.kernel.web.models.JsonResult;

@Controller
@RequestMapping("s/tvmis/picture")
public class PictureController extends AbstractController{
	@Autowired
	private VillageApplicantService vaService;
	@Autowired
	private TownApplicantService taService;
	
	@ResponseBody
	@RequestMapping(value = "village/{vid}!{pid}!delete.do", method = RequestMethod.GET)
	public JsonResult deleteVAP(@PathVariable("vid")Long vid, @PathVariable("pid")Long pid){
		try{
			vaService.deletePicture(getUser(), vid, pid);
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	@ResponseBody
	@RequestMapping(value = "town/{tid}!{pid}!delete.do", method = RequestMethod.GET)
	public JsonResult deleteTAP(@PathVariable("tid")Long tid, @PathVariable("pid")Long pid){
		try{
			taService.deletePicture(getUser(), tid, pid);
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
}
