package cn.bonoon.controllers.newrural;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.AdministrationUncoreRuralService;
import cn.bonoon.entities.AdministrationUncoreRuralEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
/**
 * 非主体村下的行政村
 * @author Administrator
 *
 */
@Controller
@RequestMapping("s/cl/area/nocore/administration/rural")
public class AdministrationRuralNoCoreController extends AbstractGridController<AdministrationUncoreRuralEntity, AdministrationRuralItem> {

	private final AdministrationUncoreRuralService administrationUncoreRuralService;


	@Autowired
	public AdministrationRuralNoCoreController(AdministrationUncoreRuralService administrationUncoreRuralService) {
		super(administrationUncoreRuralService);
		this.administrationUncoreRuralService = administrationUncoreRuralService;
	}


	@Override
	@GridStandardDefinition(
			detailClass = AdministrationRuralDetailNoCore.class,
			updateClass = AdministrationRuralEditorNoCore.class,
			autoOperation = false,
			deleteOperation=false,
			editStatus = {0, 3}
	)
	
	
	@QueryExpression("x.modelArea.status>=0")
	protected AdministrationUncoreRuralService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button("打印", "index.print", ButtonEventType.JUMP).ordinal(50);
		return administrationUncoreRuralService;
	}

	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.DOMAIN;
	}
	
	@RequestMapping("!{key}/index.print")
	public String print(HttpServletRequest request, Long id, String gridid, Model model) {
		List<String> natureVillages = administrationUncoreRuralService.getNaturalVillage(id);
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
		AdministrationUncoreRuralEntity entity =administrationUncoreRuralService.get(id);
		model.addAttribute("form",entity);
		model.addAttribute("title","连片示范建设工程非主体建设行政村情况表");
		model.addAttribute("readonly", "readonly='readonly'").addAttribute("disabled", "disabled='true'");
		return "applicant/administrationrural-uncore-view-view";
	}
	
}




