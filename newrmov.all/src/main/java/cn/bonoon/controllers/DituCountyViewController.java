package cn.bonoon.controllers;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.LocationPointService;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.ModelAreaPointEntity;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.PeripheralRuralEntity;
import cn.bonoon.entities.RuralPointEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;

/**
 * 示范片区地图查看
 *
 */
@Controller
@RequestMapping("s/cl/ditu/view")
public class DituCountyViewController extends AbstractPanelController {
	private final StringBuilder inc;

	public DituCountyViewController() {
		inc = new StringBuilder();
		inc.append("<script charset=\"utf-8\" src=\"http://map.qq.com/api/js?v=2.exp&libraries=drawing\"></script>");//加载腾讯地图api
	}

	@Autowired
	private LocationPointService locationPointService;

	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		model.addIncludes(inc);
		LogonUser user = getUser();
		Long oid = user.getOwnerId();// unit
		boolean isModelArea = true;
		
		
		ModelAreaPointEntity modelAreapoint = locationPointService
				.findModelAreaPointByCurrUser(oid);// 根据当前县取出示范片区
		
		model.addObject("modelAreapoint", modelAreapoint);
		if (modelAreapoint != null) {//1.如果已经编辑过的，则加载地图的编辑信息
			model.addObject("modelAreaPoint", modelAreapoint);
			
//			//只有示范片区编辑过,才能编辑村子
//			List<NewRuralEntity> nrurals = locationPointService.findAllNewRuralBymodelAreaId(modelAreapoint.getModelArea().getId());
//			List<PeripheralRuralEntity> prurals = locationPointService.findAllPeripheralRuralBymodelAreaId(modelAreapoint.getModelArea().getId());
//			model.addObject("nrurals", nrurals);
//			model.addObject("prurals", prurals);
			
			//这个片区下的所有编辑过的村子点
			List<RuralPointEntity> rps = locationPointService.findRuralPointByMapointId(modelAreapoint.getId());
			
//			List<PointEmbeddable> pPoints = new ArrayList<PointEmbeddable>();
//			List<PointEmbeddable> nPoints = new ArrayList<PointEmbeddable>();
			
			List<RuralPointEntity> nPoints = new ArrayList<RuralPointEntity>();
			List<RuralPointEntity> pPoints = new ArrayList<RuralPointEntity>();
//			RuralPointEntity p ;
			if(rps!=null){//片区下面创建有村子
				for(RuralPointEntity r:rps){
					if(r.getRural() instanceof NewRuralEntity){
						nPoints.add(r);
						model.addObject("nPoints", nPoints);
					}else if(r.getRural() instanceof PeripheralRuralEntity){
						pPoints.add(r);
						model.addObject("pPoints", pPoints);
					}
				}
			}else{//片区下面没有创建村子
				//System.out.println("片区点下面没有创建任何村子点!");
			}
		} else {
			// 2.未编辑过,根据县的名称,在地图上定位到该县.这是需要保存县的搜索名称和搜索后返回的坐标
			ModelAreaEntity modelArea = locationPointService
					.findModelAreaByCurrUser(oid);
			if (modelArea != null) {
				model.addObject("modelArea", modelArea);
			}else{//3.这个县不属于示范片区,让他定位到广东省
				isModelArea = false;
			}
			
		}
		model.addObject("isModelArea", isModelArea);
		event.setVmPath("ditu/cl-view");
	}
	
	
}
