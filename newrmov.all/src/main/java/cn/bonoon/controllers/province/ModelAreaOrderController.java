package cn.bonoon.controllers.province;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.core.ModelAreaService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.JsonResult;

@Controller
@RequestMapping("s/pl/order/pmav")
public class ModelAreaOrderController extends AbstractPanelController{

	@Autowired
	protected ModelAreaService modelAreaService;
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		StringBuilder ma1 = new StringBuilder(), 
				ma2 = new StringBuilder(), 
				ma3 = new StringBuilder(), 
				ma4 = new StringBuilder();
		Map<Object, List<Object[]>> map = modelAreaService.items();
		StringBuilder maItems;
		for(Map.Entry<Object, List<Object[]>> ent : map.entrySet()){
			if("一".equals(ent.getKey())){
				maItems = ma1;
			}else if("二".equals(ent.getKey())){
				maItems = ma2;
			}else if("三".equals(ent.getKey())){
				maItems = ma3;
			}else if("珠三角".equals(ent.getKey())){
				maItems = ma4;
			}else{
				continue;
			}
			maItems.append("<p style='font-size:20px;font-weight:bold;height:25px;' class='item-open2 item-close2' onclick=\"jQuery(this).toggleClass('item-close2').next().slideToggle();\">批次：");
			maItems.append(ent.getKey()).append("</p><div style='padding-left:25px;'>");
			for(Object[] it : ent.getValue()){
				Object id = it[1];
				maItems.append("<div class='ordinal-item'><input type='text' class='num-ordinal' value='");
				if(null != it[3]){
					maItems.append(it[3]);
				}
				maItems.append("' name='val_").append(id).append("'/><input type='hidden' value='");
				maItems.append(id).append("' name='ids'/>").append(it[4]).append("(").append(it[2]).append(")");
				maItems.append("<a class='btn-change-batch' href='javascript:' onclick=\"return changeBatch(").append(id).append(",'一');\">第一批</a>");
				maItems.append("<a class='btn-change-batch' href='javascript:' onclick=\"return changeBatch(").append(id).append(",'二');\">第二批</a>");
				maItems.append("<a class='btn-change-batch' href='javascript:' onclick=\"return changeBatch(").append(id).append(",'三');\">第三批</a>");
				maItems.append("<a class='btn-change-batch' href='javascript:' onclick=\"return changeBatch(").append(id).append(",'珠三角');\">珠三角</a>");
				maItems.append("</div>");
			}
			maItems.append("</div>");
		}
		
		event.getModel().addObject("maItems", ma1.append(ma2).append(ma3).append(ma4));
		event.setVmPath("applicant/area/province-ordinal");
	}
	
	@ResponseBody
	@RequestMapping(value="!{key}/save.submit", method=RequestMethod.POST)
	public JsonResult saveOrdinal(Long[] ids, HttpServletRequest request){
		try{
			modelAreaService.ordinal(ids, request.getParameterMap(),getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="!{key}/batch.change", method=RequestMethod.POST)
	public JsonResult batchChange(
			@RequestParam("id") Long id, 
			@RequestParam("batch") String batch){
		try{
			modelAreaService.changeBacth(id, batch,getUser());
			//modelAreaService.ordinal(ids, request.getParameterMap());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
}
