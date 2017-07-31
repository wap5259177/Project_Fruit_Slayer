package cn.bonoon.controllers.felicityvillage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.core.FVMAReportService;
import cn.bonoon.core.FVProjectRuralService;
import cn.bonoon.core.felicity.ProjectInfo;
import cn.bonoon.core.felicity.RuralInfo;
import cn.bonoon.entities.felicityVillage.FVModelAreaReportEntity;


/**
 * 
 * @author Administrator
 *	省办，点击表1,2,3 查看
 */
@Controller
@RequestMapping("s/base/fv")
public class BaseFVContrller {

	@Autowired
	private FVMAReportService fvmaReportService;
	@Autowired
	private FVProjectRuralService fvProjectRuralService;
//	@Autowired
//	private FVCountyReportService fvCountyReportService;
	
	//处理tab对应iframe的src 
	@RequestMapping("felicityvillage/table1.htm")
	public String tab(Model model,Long id){//片区id
		FVModelAreaReportEntity ma = fvmaReportService.get(id);
		model.addAttribute("ma", ma);
		model.addAttribute("id", id);
		if(ma.getCountyReport().getStatus()==1){
			model.addAttribute("submit", true);
		}
		model.addAttribute("layout", "layout-empty.vm");
		model.addAttribute("title", ma.getName());
		return "felicityvillage/base/table1-info";
	}
	
	//列出table1  数据表格
	@ResponseBody
	@RequestMapping("felicityvillage/table1.json")
	public List<RuralInfo> Table1Json(Long id){//片区id
		List<RuralInfo> rinfos = fvmaReportService.checkRuralMakeList(id);
		return rinfos;
	}
	
	
	//处理tab对应iframe的src 
	@RequestMapping("table2.htm")
	public String tab2(Model model,Long id){//片区id
		FVModelAreaReportEntity ma = fvmaReportService.get(id);
		model.addAttribute("ma", ma);
		model.addAttribute("id", id);
		if(ma.getCountyReport().getStatus()==1){
			model.addAttribute("submit", true);
		}
		model.addAttribute("title", ma.getName());
		model.addAttribute("layout", "layout-empty.vm");
		return "felicityvillage/base/table2-info";
	}
	
	//列出table2  数据表格
	@ResponseBody
	@RequestMapping("table2.json")
	public List<ProjectInfo> Table2Json(Long id){//片区id
		List<ProjectInfo> prinfos = fvProjectRuralService.makePjListByMaId(id);
		return prinfos;
	}
	
	
	@RequestMapping("table3.htm")
	public String report1(Model model,Long id){//id为县报的id
		FVModelAreaReportEntity ma = fvmaReportService.get(id);
		model.addAttribute("it", ma);
		model.addAttribute("id", id);
		if(ma.getCountyReport().getStatus()==1){
			model.addAttribute("submit", true);
		}
		model.addAttribute("title", ma.getName());
		model.addAttribute("layout", "layout-empty.vm");
		return "felicityvillage/base/table3-info";
	}
}
