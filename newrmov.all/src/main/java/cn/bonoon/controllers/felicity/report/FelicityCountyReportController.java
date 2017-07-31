package cn.bonoon.controllers.felicity.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.felicity.countyreport.FelicityCountyReportDetail;
import cn.bonoon.controllers.felicity.countyreport.FelicityCountyReportItem;
import cn.bonoon.core.FelicityCountyReportService;
import cn.bonoon.core.FelicityVillageReportService;
import cn.bonoon.entities.FelicityCountyReportEntity;
import cn.bonoon.entities.FelicityVillageReportEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.controllers.BaseButtonResolver;
import cn.bonoon.kernel.web.controllers.GridButtonResolver;
import cn.bonoon.kernel.web.handlers.DialogGridHandler;
import cn.bonoon.kernel.web.handlers.DialogUpdateHandler;

@Controller
@RequestMapping("s/cl/fv/report")
public class FelicityCountyReportController extends AbstractGridController<FelicityCountyReportEntity,FelicityCountyReportItem>{

	private FelicityCountyReportService felicityCountyReportService;
	@Autowired
	private FelicityVillageReportService felicityVillageReportService;
	@Autowired
	protected FelicityCountyReportController(FelicityCountyReportService felicityCountyReportService) {
		super(felicityCountyReportService);
		this.felicityCountyReportService = felicityCountyReportService;
	}
	@Override
	@GridStandardDefinition(
			autoOperation = false,
			detailClass = FelicityCountyReportDetail.class,
			insertClass = FelicityCountyReportInserter.class,
			updateClass = FelicityCountyReportUpdater.class,
			editStatus = {0, 3}
			)
	//@QueryExpression("x.status in(0, 3)")
	protected FelicityCountyReportService initLayoutGrid(LayoutGridRegister register) throws Exception {
//		HandlerRegister inRegister = new HandlerRegister() {
//			
//			@Override
//			public GridToolbarResolver toolbar() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//			@Override
//			public String refreshParentComponenet() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//			@Override
//			public StandardAutoManager getManager() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//			@Override
//			public GridButtonResolver button() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//		};
		DialogGridHandler<FelicityVillageReportEntity> fvdgHandler = new DialogGridHandler<>(register, felicityVillageReportService, FelicityVillageReportingItem.class);
		fvdgHandler.setRelatedKey("countyReport.id");
		fvdgHandler.size(700, 450);
		fvdgHandler.set("上报村月度数据", true);
		GridButtonResolver gbtn = fvdgHandler.gridButton();//.setName("测试");
		
		DialogUpdateHandler<FelicityVillageReportEntity> fvUpdate = new DialogUpdateHandler<>(register, felicityVillageReportService, FelicityVillageReportUpdater.class);
		fvUpdate.register(gbtn);
		gbtn.setName("上报");
		
		BaseButtonResolver br = fvdgHandler.register(register.button().status(0, 3));
		br.setName("上报数据");
		
		
		return felicityCountyReportService;
	}
	
	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.DOMAIN;
	}
}
