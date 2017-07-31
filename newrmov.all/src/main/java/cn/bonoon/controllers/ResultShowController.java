package cn.bonoon.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.DialogModel;
import cn.bonoon.kernel.web.models.JsonResult;
import cn.bonoon.kernel.web.models.PanelModel;

@Controller
@RequestMapping("s/pl/result/show")
public class ResultShowController extends AbstractPanelController {

	private final String img_path = "/r/images/example/7village_planning_diagram/";
	private final String[] img_href = { "03西群村空间设计导引图.jpg", "4 功能结构规划图 10.05.13g-tk副本.jpg", "5景区协调规划图.jpg", "6.景观资源评价图.jpg", "新作塘村初步设想.jpg", 
			 "东莞茶山镇南社村(人文历史名村).jpg", "东莞茶山镇南社村2(人文历史名村).jpg", "东莞茶山镇南社村3（人文历史名村).jpg", "东莞南城街道周溪社区（社会管理名村）.jpg", "东莞南城街道周溪社区2（社会管理名村）.jpg", 
			 "东莞中堂镇潢涌村（人文历史名村）.jpg", "佛山高明塘伙新村风景（生态、乡村旅游名村）.jpg", "佛山南海西樵镇松塘村（人文历史名村）.jpg", "佛山三水大旗头3（人文历史名村）.jpg", "佛山三水大旗头村2（人文历史名村）.jpg"};
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		StringBuilder sb = new StringBuilder("<table style='padding:12px;'><tr>");
		final int col = 5;
		for (int i = 0; i < img_href.length; i++) {
			String img_id = String.valueOf(i);
			String _img_href = img_href[i];
			String img_name = _img_href.substring(0, _img_href.length() - 4);
			String img_src = img_path + _img_href;
			String onclick_js = "javascript: jQuery('#div-kernel-dialog-loader').load('image/dialog?id=" + img_id + "');return false;";
			if (i % col == 0) {
				sb.append("</tr><tr>");
			}
			sb.append("<td valign='middle' align='center' style='width:207px;height:180px;'><img style='cursor:pointer;' width='180px' height='130px' src='");
			sb.append(img_src).append("' onclick=\"").append(onclick_js).append("\"/><p style='text-align:center;'><a href='javascript:void(0)' onclick=\"");
			sb.append(onclick_js).append("\" >").append(img_name).append("</a></p></td>");
		}
		sb.append("</tr></table>");
		model.addObject("photo_show", sb);
		model.addObject("mid", model.getMid());
//		event.setVmPath("result-show");
		
		event.setVmPath("result-show-timeline");
	}

	@RequestMapping(value = "!{mid}/image/dialog", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView img_dlg(HttpServletRequest request, @PathVariable("mid") String mid, Long id) {
		DialogModel model = new DialogModel(mid, request);
		model.addObject("img_id", id);
		model.addObject("img_page", id + 1);
		String _img_href = img_href[id.intValue()];
		model.addObject("img_name", _img_href.substring(0, _img_href.length() - 4));
		model.addObject("img_src", img_path + _img_href);
		model.addObject("img_count", img_href.length);
		return model.execute("photo_show_dlg");
	}
	
	@ResponseBody
	@RequestMapping(value = "!{mid}/image/navigate", method = { RequestMethod.GET, RequestMethod.POST })
	public final JsonResult img_navigate(HttpServletRequest request, @PathVariable("mid") String mid, final Long id, final Long direction) {
		if (id == 0 && direction == 0)
			return JsonResult.result(JsonResult.ERROR, "已是第一页");
		else if (id == img_href.length - 1 && direction == 1)
			return JsonResult.result(JsonResult.ERROR, "已是最后一页");

		Integer _id = direction == 1 ? id.intValue() + 1 : id.intValue() - 1;
		String _img_href = img_href[_id];
		String img_name = _img_href.substring(0, _img_href.length() - 4);
		String img_src = img_path + _img_href;
		Object[] obj = { _id, _id + 1, img_name, img_src };
		return JsonResult.result((Object) obj);
	}

//	@ResponseBody
//	@RequestMapping(value = "!{mid}/image/play", method = { RequestMethod.GET, RequestMethod.POST })
//	public final JsonResult img_play(HttpServletRequest request, @PathVariable("mid") String mid, final Long id, final Long type) {
//		return JsonResult.result();
//	}
	
	@RequestMapping(value = "!{mid}/project/statistics", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView project_statistics(HttpServletRequest request, @PathVariable("mid") String mid, Long id) {
		DialogModel model = new DialogModel(mid, request);
		return model.execute("result-show-project-statistics");
	}
	
	@RequestMapping(value = "!{mid}/photo/show", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView photo_show(HttpServletRequest request, @PathVariable("mid") String mid, Long id) {
		DialogModel model = new DialogModel(mid + "_dlg_2", mid, request);
		StringBuilder sb = new StringBuilder("<table style='padding:12px;'><tr>");
		final int col = 5;
		for (int i = 0; i < img_href.length; i++) {
			String img_id = String.valueOf(i);
			String _img_href = img_href[i];
			String img_name = _img_href.substring(0, _img_href.length() - 4);
			String img_src = img_path + _img_href;
			String onclick_js = "javascript: jQuery('#div-kernel-dialog-loader').load('image/dialog?id=" + img_id + "');return false;";
			if (i % col == 0) {
				sb.append("</tr><tr>");
			}
			sb.append("<td valign='middle' align='center' style='width:207px;height:180px;'><img style='cursor:pointer;' width='160px' height='110px' src='");
			sb.append(img_src).append("' onclick=\"").append(onclick_js).append("\"/><p style='text-align:center;'><a href='javascript:void(0)' onclick=\"");
			sb.append(onclick_js).append("\" >").append(img_name).append("</a></p></td>");
		}
		sb.append("</tr></table>");
		model.addObject("_photo_show", sb);
		return model.execute("result-show-photo");
	}
	
	@RequestMapping(value = "!{mid}/monitor", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView monitor(HttpServletRequest request, @PathVariable("mid") String mid, Long id) {
		DialogModel model = new DialogModel(mid, request);
		return model.execute("result-show-monitor");
	}
	
	@RequestMapping(value = "!{mid}/monitor2", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView monitor2(HttpServletRequest request, @PathVariable("mid") String mid, Long id) {
		DialogModel model = new DialogModel(mid, request);
		return model.execute("result-show-monitor2");
	}
	
	@RequestMapping(value = "!{mid}/map_view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView map_view(HttpServletRequest request, @PathVariable("mid") String mid, Long id) {
		DialogModel model = new DialogModel(mid, request);
		return model.execute("result-show-mapview");
	}
	
	@RequestMapping(value = "!{mid}/piechart/dialog", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView piechart(HttpServletRequest request, @PathVariable("mid") String mid, Long id) {
		DialogModel model = new DialogModel(mid + "pie", request);
		return model.execute("schedule-job-piechart-dlg");
	}
	
	@RequestMapping(value = "!{mid}/city_area", method = { RequestMethod.GET, RequestMethod.POST })
	public String city_area(Model model) {
		return "city_area";
	}

}
