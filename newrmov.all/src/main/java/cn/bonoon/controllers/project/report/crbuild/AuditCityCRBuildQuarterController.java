package cn.bonoon.controllers.project.report.crbuild;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.core.CityCRBuildQuarterReportService;
import cn.bonoon.core.QuarterReportStatus;
import cn.bonoon.entities.ruralBuild.ModelAreaCRBuildQuarterItem;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.controllers.BaseButtonResolver;
import cn.bonoon.kernel.web.handlers.DialogUpdateHandler;
import cn.bonoon.kernel.web.models.DialogModel;
@Controller
@RequestMapping("s/cls/macqa/crbuild")
public class AuditCityCRBuildQuarterController extends AbstractGridController<ModelAreaCRBuildQuarterItem,ModelAreaCRBuildQuarterAuditItem>{

	private CityCRBuildQuarterReportService cityCRBuildQuarterReportService;

	@Autowired
	protected AuditCityCRBuildQuarterController(CityCRBuildQuarterReportService cityCRBuildQuarterReportService ) {
		super(cityCRBuildQuarterReportService);
		this.cityCRBuildQuarterReportService = cityCRBuildQuarterReportService;
		
	}

	@Override
	@GridStandardDefinition(
//			detailClass = CityQuarterReportDetail.class, 
			autoOperation = false, 
			deleteOperation = false
	)
	@QueryExpression("x.status>=0 and x.modelArea.cityId=(select y.place.id from UnitEntity y where y.id={USER owner})")
	protected CityCRBuildQuarterReportService initLayoutGrid(LayoutGridRegister register) throws Exception {
		CityCRBuildQuarterAuditHandler cqahandler = new CityCRBuildQuarterAuditHandler(0, cityCRBuildQuarterReportService, autoManager, ModelAreaCRBuildQuarterReportAuditor.class);
		DialogUpdateHandler<ModelAreaCRBuildQuarterItem> dh = new DialogUpdateHandler<>(register, cqahandler);
		BaseButtonResolver br = dh.register(register.button("审核","", ButtonEventType.DIALOG).status(QuarterReportStatus.AUDIT));
		br.setName("审核");
		
		register.button("查看", "index.report", ButtonEventType.DIALOG);
		
//		register.button("打印", "index.print", ButtonEventType.JUMP).ordinal(50);
		return cityCRBuildQuarterReportService;
	}
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	@RequestMapping("!{key}/index.report")
	public ModelAndView report(HttpServletRequest request, Long id, String gridid, String v) {
		DialogModel model = new DialogModel("c_s_" + id, request);
		model.addForm(id);
		ModelAreaCRBuildQuarterItem item = cityCRBuildQuarterReportService.get(id);
		if(null!=item){
			
		}
		model.addObject("title", "省级新农村示范片主体村建设情况统计表");
		model.addObject("deadline", sdf.format(item.getBatch().getQuarter().getDeadline()));
		model.addObject("id", id);
		model.addObject("item", item);
		model.addObject("status", item.getStatus());
		model.addObject("auditat", item.getAuditAt());
		model.addObject("auditName", item.getAuditName());
		model.addObject("auditContent", item.getAuditContent());
		return model.execute("report/crbuild/crbuild-quarter-report");
	}
	
	
	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.GLOBAL;
	}
	
	
	
//	//打印20160413
//	@RequestMapping("!{key}/index.print")
//	public String print(HttpServletRequest request, Long id, String gridid, Model model) {
//		ModelAreaCRBuildQuarterItem item = cityCRBuildQuarterReportService.get(id);
//		if(null!=item){
//			
//		}
//		model.addAttribute("title", "广东省" + item.getCityName()+item.getModelArea().getName() + "连片示范建设工程进展情况统计表");
//		model.addAttribute("deadline", sdf.format(item.getBatch().getQuarter().getDeadline()));
//		model.addAttribute("itemId", id);
//		model.addAttribute("item", item);
//		model.addAttribute("status", item.getStatus());
//		model.addAttribute("auditat", item.getAuditAt());
//		model.addAttribute("auditName", item.getAuditName());
//		model.addAttribute("auditContent", item.getAuditContent());
//		return "report/quarter-report-view";
//	}
}
