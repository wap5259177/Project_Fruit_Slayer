package cn.bonoon.controllers.city;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.controllers.AbstractModelAreaViewController;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.models.JsonResult;
import cn.bonoon.kernel.web.models.PanelModel;

@Controller
@RequestMapping("s/ml/cmaa")
public class CityModelAreaAuditController extends AbstractModelAreaViewController{

	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		LogonUser user = getUser();
		Long owner = user.getOwnerId();
		ModelAreaEntity ma = modelAreaService.getNeedAudit(owner);
		if(null != ma){
			PanelModel model = event.getModel();
			model.addObject("aid", ma.getId());
			model.addObject("name", ma.getName());
			model.addObject("contactName", ma.getContactName());
			model.addObject("contactJob", ma.getContactJob());
			model.addObject("contactPhone", ma.getContactPhone());
			model.addObject("contactPhone2", ma.getContactPhone2());
			model.addObject("applicatAt", StringHelper.date2String(ma.getApplicatAt()));
			model.addObject("remark", ma.getRemark());
			
			Date aat = ma.getAuditAt();
			if(null == aat){
				model.addObject("auditAt", StringHelper.date2String(new Date()));
			}else{
				model.addObject("auditAt", StringHelper.date2String(aat));
			}
			model.addObject("auditName", ma.getAuditName());
			
			model.addObject("auditContent", ma.getAuditContent());
			model.addObject("auditResult", ma.getAuditResult());
		}
		event.setVmPath("applicant/area/city-audit");
	}

	@ResponseBody
	@RequestMapping(value = "!{key}/{id}!pass.do", method = POST)
	public JsonResult pass(@PathVariable("id") Long id, String content, String name, Date date){
		try{
			modelAreaService.pass(id, content, name, date);
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	@ResponseBody
	@RequestMapping(value = "!{key}/{id}!reject.do", method = POST)
	public JsonResult reject(@PathVariable("id") Long id, String content, String name, Date date){
		try{
			modelAreaService.reject(id, content, name, date);
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
}
