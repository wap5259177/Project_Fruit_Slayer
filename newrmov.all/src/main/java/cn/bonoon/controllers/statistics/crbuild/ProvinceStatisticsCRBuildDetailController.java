package cn.bonoon.controllers.statistics.crbuild;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.core.CRBuildQuarterReportService;
import cn.bonoon.entities.ruralBuild.ModelAreaCRBuildQuarterItem;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.controllers.AbstractController;
import cn.bonoon.kernel.web.models.DialogModel;
import cn.bonoon.kernel.web.models.JsonResult;

@Controller
@RequestMapping("s/pls/sms/crb")
public class ProvinceStatisticsCRBuildDetailController  extends AbstractController{
	@Autowired
	private CRBuildQuarterReportService crBuildQuarterReportService;

	/**
	 * 显示弹窗
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "{id}-{readonly}!pushDialog", method = GET)
	public ModelAndView pushDialog(HttpServletRequest request,
			@PathVariable("id") Long id,@PathVariable("readonly") Integer readonly,Integer status) {
		
			DialogModel model = new DialogModel(request);
			ModelAreaCRBuildQuarterItem item = crBuildQuarterReportService.get(id);
			if(item==null)throw new RuntimeException("找不到"+ModelAreaCRBuildQuarterItem.class+id);
			String title = null;
			if(item.getStatus()==1||item.getStatus()==4){
				title = "提交审核内容";
			}else if(item.getStatus()==3){
				title = "查看驳回原因";
			}
			model.addObject("title", title);
			model.addObject("item",item);
			if(readonly!=null){
				model.addObject("readonly",readonly);
				model.addObject("height",readonly==1?200:280);
			}
			if(status!=null){
				model.addObject("status",status);
			}
			return model.execute("report/crbuild/province-report");
	
	}

	/****/
	//驳回操作
	@ResponseBody
	@RequestMapping(value = "!{key}/index.pass")
	public JsonResult indexPass(@RequestParam(value = "id") Long id,String content){
		try{
			crBuildQuarterReportService.toPass(getUser(), id, content);
			return JsonResult.result(0,"提交成功");
		}catch(Exception e){
			return JsonResult.error("提交失败");
		}	
	}
	/****/
	//审核通过的操作
	@ResponseBody
	@RequestMapping(value = "!{key}/index.success")
	public JsonResult indexSuccess(@RequestParam(value = "id") Long id,String content){
		try{
			crBuildQuarterReportService.toSuccess(getUser(), id, content);
			return JsonResult.result(0,"提交成功");
		}catch(Exception e){
			return JsonResult.error("提交失败");
		}	
	}

}


