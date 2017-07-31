package cn.bonoon.controllers.statistics;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.ModelAreaService;
import cn.bonoon.core.ModelAreaStatisInfo;
import cn.bonoon.core.StatisticsScheduleService;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;
/**
 * 省项目资金投入情况
 * @author Administrator
 *
 */
@Controller
@RequestMapping("s/pls/sms/invest")
public class ProvinceStatisticsProjectInvestController extends AbstractPanelController{

	@Autowired
	private StatisticsScheduleService statisticsScheduleService;
	
	@Autowired
	protected ModelAreaService modelAreaService;
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		
		Calendar date = GregorianCalendar.getInstance();
		int nowyear = date.get(Calendar.YEAR);
		StringBuffer selectYear = new StringBuffer();
		selectYear.append("<select id='year'   onchange='' name='year' >");
		selectYear.append("<optgroup label='年度'><option value ='-1'>--请选择--</option>");
		for(int i=nowyear-4;i<=nowyear;i++){
			selectYear.append("<option value ='").append(i).append("'>").append(i).append("</option>");
		}
		selectYear.append("</select>");
		PanelModel model = event.getModel();
		model.addObject("title", "项目资金投入情况");
		model.addObject("selectYear", selectYear);
		event.setVmPath("statistics/project-invest-view");
	}
	
	
	
	//导出主体村项目资金excel
	@RequestMapping(value="!{key}/epPjInvest.excel",method = {POST,GET} )
	public void exportTable1(HttpServletRequest request,HttpServletResponse response,Model model,String batch,int ruralType,int year,int period,int status){
		List<ModelAreaStatisInfo> items = new ArrayList<>();
		List<ModelAreaEntity> models = modelAreaService.getModelArea(batch);
		for(ModelAreaEntity ma:models){
			ModelAreaStatisInfo mInfo = statisticsScheduleService.statisProInvest(ma,batch,ruralType,year,period,status);
			items.add(mInfo);
		}
		statisticsScheduleService.exportRuralPjInvest(request,response,items,ruralType);
//		if("0".equals(rural)){
//			statisticsScheduleService.exportCoreRuralPjInvest(request,response,items);
//		}else{
//			statisticsScheduleService.exportUnCoreRuralPjInvest(request,response,items);
//		}
	}
	
	@RequestMapping(value="!{key}/loadContent.do",method = {POST,GET} )
	public String loadContent(Model model,String batch,int ruralType,int year,int period,int status){
		List<ModelAreaStatisInfo> items = new ArrayList<>();
		List<ModelAreaEntity> models = modelAreaService.getModelArea(batch);
		for(ModelAreaEntity ma:models){
			ModelAreaStatisInfo mInfo = statisticsScheduleService.statisProInvest(ma,batch,ruralType,year,period,status);
			items.add(mInfo);
		}
		model.addAttribute("items", items);
		model.addAttribute("layout", "layout-empty.vm");
		return "statistics/project-invest-content";
	}
	
	
}
