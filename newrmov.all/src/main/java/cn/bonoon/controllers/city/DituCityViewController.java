package cn.bonoon.controllers.city;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.AbstractModelAreaViewController;
import cn.bonoon.core.LocationPointService;
import cn.bonoon.entities.ModelAreaPointEntity;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.RuralPointEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.models.PanelModel;

/**
 * 市级下地图功能
 * @author Administrator
 *
 */
@Controller
@RequestMapping("s/ml/ct/ditu")
public class DituCityViewController extends AbstractModelAreaViewController{
	private final StringBuilder inc;

	@Autowired
	private LocationPointService locationPointService;
	public DituCityViewController() {
		inc = new StringBuilder();
		inc.append("<script charset=\"utf-8\" src=\"http://map.qq.com/api/js?v=2.exp&libraries=drawing\"></script>");//加载腾讯地图api
	}

	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		model.addIncludes(inc);
		LogonUser user = getUser();
		Long owner = user.getOwnerId();
		
		//市下面的所有片区点
		List<ModelAreaPointEntity> mps = modelAreaService.cityModelAreaPoint(owner);
		//市下面的所有村子点
		List<RuralPointEntity> rurals = modelAreaService.cityRuralPoint(owner);
		List<RuralPointEntity> n_rurals = new ArrayList<RuralPointEntity>();
		List<RuralPointEntity> p_rurals = new ArrayList<RuralPointEntity>();
		
		if(rurals!=null && rurals.size()>0){
			for(RuralPointEntity r:rurals){
				if(r.getRural() instanceof NewRuralEntity){
					//核心村
					n_rurals.add(r);
				}else{
					//非主体村
					p_rurals.add(r);
				}
			}
		}
		
		model.addObject("mps", mps);
		model.addObject("n_rurals", n_rurals);
		model.addObject("p_rurals", p_rurals);
		event.setVmPath("ditu/ct-view");
	}
	
	@RequestMapping(value = "!{mid}/loadMapInfo.do")
	public String loadMapInfo(Long id ,Model model){
		ModelAreaPointEntity map = locationPointService.findModelAreaPointById(id);
		String pic = map.getPicture();
		model.addAttribute("pic", pic);
		model.addAttribute("map",map);
		return "ditu/city-ma-windowinfo";
	}
	
	@RequestMapping(value = "!{mid}/loadRuralInfo.do")
	public String loadRuralInfo(Long id ,Model model){
		RuralPointEntity rp = locationPointService.findRuralPointByRuralId(id);
		String pic = rp.getPicture();
		model.addAttribute("pic", pic);
		model.addAttribute("ruralPoint",rp);
		return "ditu/city-rural-windowinfo";
	}
	
	
}
