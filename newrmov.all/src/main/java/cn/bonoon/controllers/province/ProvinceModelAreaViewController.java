package cn.bonoon.controllers.province;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.AbstractModelAreaViewController;
import cn.bonoon.kernel.events.PanelEvent;

@Controller
@RequestMapping("s/pl/pmav")
public class ProvinceModelAreaViewController extends AbstractModelAreaViewController{

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
				maItems.append("<div><a href='view.detail?id=").append(it[1]).append("' onclick=\"jQuery('#ma-view-detail-content').load(this.href);return false;\" title='");
				maItems.append(it[2]).append("'>");
				maItems.append(it[4]).append("</a></div>");
			}
			maItems.append("</div>");
		}
		
		event.getModel().addObject("maItems", ma1.append(ma2).append(ma3).append(ma4));
		event.setVmPath("applicant/area/province-view");
	}
}
