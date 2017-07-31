package cn.bonoon.controllers.felicityvillage;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.core.FVCountyReportService;
import cn.bonoon.core.FVReportService;
import cn.bonoon.entities.felicityVillage.FVReportEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;

@Controller
@RequestMapping("s/ml/fv/city/statistics")
public class FVStatisticsCityFelicityController extends AbstractPanelController {
	
//	@Override
//	protected List<FelicityCountyEntity> counties(FelicityCountyReportService fcrService, LogonUser user) {
//		
//		
//		return fcrService.byCity(user);
//	}
	@Autowired
	protected FVCountyReportService fvCountyReportService;
	@Autowired
	protected FVReportService fvReportService;
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
//		StatisticsFelicityController s;
		
//		LogonUser user = getUser();
//		List<FVMACountyReportEntity> cReports = fvCountyReportService.allCountyReportByCityPid(user);
		PanelModel model = event.getModel();
		List<FVReportEntity> reports = fvReportService.allReport();
		model.addObject("reports", reports);
		event.setVmPath("felicityvillage/statistics/table");
	}
	
	
	@RequestMapping("!{key}/loadContent.do")
	public String loadContent(Model model,Long reportId,Integer tableType){//tableType 对应界面选择的项目库一,二,三(1,2,3)
		model.addAttribute("reportId", reportId);
		model.addAttribute("tableType", tableType);
		model.addAttribute("layout", "layout-empty.vm");
		return "felicityvillage/statistics/table"+tableType+"-content";
	}
	
	

	/**
	 * 统计表1的数据
	 * @param reportId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("!{key}/statis/table.json")
	public List<Object> Table1Json(Long reportId,Integer tableType){
		List<Object> infos = null;
		switch (tableType) {
			case 1:
				infos = fvReportService.table1Items(reportId,getUser());
				break;
			case 2:
				infos = fvReportService.table2Items(reportId,getUser());
				break;
			case 3:
				infos = fvReportService.table3Items(reportId,getUser());
				break;
		}
		return infos;
	}
	
	
	
	//导出table
	@RequestMapping(value="!{key}/table.excel",method = {POST,GET} )
	public void exportTable(HttpServletRequest request,HttpServletResponse response,Model model,Long reportId,Integer tableType){
		fvReportService.exportTable(request,response,reportId,tableType,getUser());
	}
}
