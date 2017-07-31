package cn.bonoon.controllers.newrural;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.core.AdministrationCoreRuralService;
import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.entities.RuralExpertGroupEntity;
import cn.bonoon.entities.RuralUnitEntity;
import cn.bonoon.entities.RuralWorkGroupEntity;
import cn.bonoon.entities.ruralBuild.ModelAreaCRBuildQuarterEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.models.DialogModel;

/**
 * 行政村表
 * @author xiaowu
 *
 */
@Controller
@RequestMapping("s/cl/area/administration/rural")
public class AdministrationRuralController extends AbstractGridController<AdministrationCoreRuralEntity,
		AdministrationRuralItem> {

	private final AdministrationCoreRuralService administrationRuralService;


	@Autowired
	public AdministrationRuralController(AdministrationCoreRuralService administrationRuralService) {
		super(administrationRuralService);
		this.administrationRuralService = administrationRuralService;
	}

//	private String dialog;
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		dialog = toUrl("/s/ma/media/0-manager.image");
//		super.afterPropertiesSet();
//	}


	
	@Override
	@GridStandardDefinition(
			detailClass = AdministrationRuralDetail.class,
			updateClass = AdministrationRuralEditor.class,
			autoOperation = false,
			deleteOperation=false,
			editStatus = {0, 3}
	)
	@QueryExpression("x.modelArea.status>=0 ")
	protected AdministrationCoreRuralService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button("打印", "index.print", ButtonEventType.JUMP).ordinal(50);
		return administrationRuralService;
	}

	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.DOMAIN;
	}
	
	@RequestMapping("!{key}/index.print")
	public String print(HttpServletRequest request, Long id, String gridid, Model model, Object form) {
//		AdministrationRuralDetail ard;
//		if(null == form){
//			ard = new AdministrationRuralDetail();
//		}else{
//			ard = (AdministrationRuralDetail)form;
//		}
//		AdministrationCoreRuralEntity are = administrationRuralService.getAdministrationByAdminRuralId(id);
		List<String> natureVillages = administrationRuralService.getNaturalVillage(id);
		if(natureVillages!=null&&natureVillages.size()>0){
			StringBuffer sb = new StringBuffer("");
			for(int i=0;i<natureVillages.size();i++){
				if(i==natureVillages.size()-1){
					sb.append(natureVillages.get(i)).append("");
				}else{
					sb.append(natureVillages.get(i)).append(",");
				}
			}
			model.addAttribute("natureList", sb);
			model.addAttribute("natralruralNum", natureVillages.size());
			model.addAttribute("natureVillages", natureVillages);
		}
		List<RuralWorkGroupEntity> wgs;
		List<RuralUnitEntity> rus;
		List<RuralExpertGroupEntity> egs;
//			ard.reset(are);
			wgs = administrationRuralService.workGroupsByAdminRural(id);
			rus = administrationRuralService.ruralUnitsByAdminRural(id);
			egs = administrationRuralService.experGroupByAdminRural(id);
		model.addAttribute("wgs", wgs).addAttribute("egs", egs).addAttribute("rus", rus);
		AdministrationCoreRuralEntity entity =administrationRuralService.get(id);
		model.addAttribute("form",entity);
		model.addAttribute("title","连片示范建设工程主体建设行政村情况表");
		model.addAttribute("readonly", "readonly='readonly'").addAttribute("disabled", "disabled='true'");
		return "applicant/administrationrural-view-view";
	}
	
}
