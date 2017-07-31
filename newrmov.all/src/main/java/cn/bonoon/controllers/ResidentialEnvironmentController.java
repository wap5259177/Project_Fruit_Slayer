package cn.bonoon.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.core.ResidentialEnvironmentService;
import cn.bonoon.entities.BaseRuralEntity;
import cn.bonoon.entities.ResidentialEnvironmentEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.JsonResult;

/**
 * 人居环境整治
 * @author jackson
 *
 */
@Controller
@RequestMapping("s/cl/re")
public class ResidentialEnvironmentController extends AbstractPanelController{

	@Autowired
	private ResidentialEnvironmentService residentialEnvironmentService;
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		// TODO 根据ownerid加载相应的ResidentialEnvironmentEntity表的数据
		List<ResidentialEnvironmentEntity> listRes=residentialEnvironmentService.getResidentialEnvironment(event.getUser().getOwnerId());
		ModelAndView execute = event.execute();
		execute.addObject("listRes",listRes);
		event.setVmPath("residential");
	}
	
	@RequestMapping(value = "!{key}/index.load", method = GET)
	public String load(Model model){		
		List<ResidentialEnvironmentEntity> listRes=residentialEnvironmentService.getResidentialEnvironment(getUser().getOwnerId());
		model.addAttribute("listRes",listRes).addAttribute("layout", "layout-empty.vm");
		return "residential-items";
	}
	
	@RequestMapping(value = "!{key}/index.editor", method = GET)
	public String editor(Model model){
		//单位的ID
		Long uid = getUser().getOwnerId(); 
		
		List<BaseRuralEntity> items = residentialEnvironmentService.getRurals(uid);
		Map<String, List<BaseRuralEntity>> maps = new HashMap<>();
		for(BaseRuralEntity br : items){
			List<BaseRuralEntity> inim = maps.get(br.getTown());
			if(inim == null){
				inim = new ArrayList<>();
				maps.put(br.getTown(), inim);
			}
			inim.add(br);
		}
		StringBuilder coreRurals = new StringBuilder();
		for(Map.Entry<String, List<BaseRuralEntity>> ent : maps.entrySet()){
			String tna = ent.getKey();
			coreRurals.append("<div class='item-close item-open' onclick=\"jQuery(this).toggleClass('item-open').next().slideToggle();\"><b>")
				.append(tna).append("</b></div><div style='display:none;padding-left:20px;'>");
			for(BaseRuralEntity br : ent.getValue()){
				Long cId=br.getId();
				String vna = br.getName();
				String nvn = br.getNaturalVillage();
				if(nvn==null)
					nvn="";
				coreRurals.append("<a id='getTo' style='cursor:pointer;' onclick=\"getCore(this, '").append(cId)
					.append("', '").append(tna)
					.append("', '").append(vna)
					.append("', '").append(nvn)
					.append("')\" data='").append(cId)
					.append("' name='crids' id='crids_").append(cId)
					.append("'>").append(vna)
					.append(" : ").append(nvn).append("</a><br/>");
				 
			}
			coreRurals.append("</div>");
		}
		model.addAttribute("coreRurals", coreRurals);
		
		return "residential-editor";
	}

	@ResponseBody
	@RequestMapping(value = "!{key}/index.save", method = POST)
	public JsonResult save(Model model, HttpServletRequest request, int[] re_count){
		//if(re_count!=null){
			try{
				LogonUser user = getUser();
				Date now = new Date();
				List<ResidentialEnvironmentEntity> items = new ArrayList<>();
				for(int co : re_count){
					String id = request.getParameter("id_" + co);
					String annual = request.getParameter("annual_" + co);
					String ordinal = request.getParameter("ordinal_" + co);
					ResidentialEnvironmentEntity res=residentialEnvironmentService.findRes(StringHelper.toLong(id), StringHelper.toint(annual));
					if(res==null){
					//String nv = request.getParameter("nv_" + co);
						BaseRuralEntity br = residentialEnvironmentService.getRural(StringHelper.toLong(id));
						ResidentialEnvironmentEntity re = new ResidentialEnvironmentEntity();
						re.setRural(br);
						re.setAnnual(StringHelper.toint(annual));
						re.setOrdinal(StringHelper.toint(ordinal));
						re.setNaturalVillage(br.getNaturalVillage());
						re.setCreateAt(now);
						re.setCreatorId(user.getId());
						re.setCreatorName(user.getUsername());
						re.setOwnerId(br.getOwnerId());
						re.setTownName(br.getTown());
						re.setVillageName(br.getName());
						items.add(re);
					}else{
						throw new RuntimeException("该记录已存在！");
					}
				}
				residentialEnvironmentService.save(items);
			}catch(Exception ex){
				return JsonResult.error(ex);
			}
			
		//}
		return JsonResult.result();
	}
	
	
}
