package cn.bonoon.controllers.newrural;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.NewRuralService;
import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.RuralExpertGroupEntity;
import cn.bonoon.entities.RuralUnitEntity;
import cn.bonoon.entities.RuralWorkGroupEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;

@Controller
@RequestMapping("s/cl/area/core/rural")
public class NewRuralController extends AbstractGridController<NewRuralEntity, NewRuralItem> {

	private final NewRuralService newRuralService;

//	@Autowired
//	private FileService fileService;
	@Autowired
	public NewRuralController(NewRuralService newRuralService) {
		super(newRuralService);
		this.newRuralService = newRuralService;
	}

	private String dialog;
	@Override
	public void afterPropertiesSet() throws Exception {
		dialog = toUrl("/s/ma/media/0-manager.image");
		super.afterPropertiesSet();
	}
//	@RequestMapping(value = "!{key}/file.detail")
//	public ModelAndView detail(HttpServletRequest request, final Long id) {
//		return FileHelper.detail(request, fileService, id);
//	}

//	@ResponseBody
//	@RequestMapping(value = "!{key}/document/delete")
//	public JsonResult file_delete(HttpServletRequest request, String ids) {
//		try {
//			fileService.delete(getUser(), StringHelper.toLongArray(ids));
//		} catch (Exception e) {
//			e.printStackTrace();
//			return JsonResult.result(e);
//		}
//		return JsonResult.result();
//	}

	@Override
	@GridStandardDefinition(
			insertClass = NewRuralInserter.class,
			//insertClass = NewRuralEditor.class, 
			detailClass = NewRuralDetail.class,
			updateClass = NewRuralEditor.class,
			autoOperation = false,
			deleteOperation=false,
			editStatus = {0, 3}
	)
	@QueryExpression("x.modelArea.status>=0")
	protected NewRuralService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button("图片管理", dialog, ButtonEventType.DIALOG);
		register.button("打印", "index.print", ButtonEventType.JUMP).ordinal(50);
		return newRuralService;
	}

	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.DOMAIN;
	}
	
	@RequestMapping("!{key}/index.print")
	public String print(HttpServletRequest request, Long id, String gridid, Model model) {
        AdministrationCoreRuralEntity are = newRuralService.getAdministrationRural(id);
		List<RuralWorkGroupEntity> wgs;
		List<RuralUnitEntity> rus;
		List<RuralExpertGroupEntity> egs;
		if(are !=null){
			wgs = newRuralService.workGroupsByAdminRural(are.getId());
			rus = newRuralService.ruralUnitsByAdminRural(are.getId());
			egs = newRuralService.experGroupByAdminRural(are.getId());
		}else{
			wgs = newRuralService.workGroups(id);
			rus = newRuralService.ruralUnits(id);
			egs = newRuralService.expertGroups(id);
		}
		model.addAttribute("wgs", wgs).addAttribute("egs", egs).addAttribute("rus", rus);
		NewRuralEntity entity =newRuralService.get(id);
		model.addAttribute("form",entity);
		model.addAttribute("title","连片示范建设工程主体建设自然村情况表");
		model.addAttribute("readonly", "readonly='readonly'").addAttribute("disabled", "disabled='true'");
		return "applicant/newrural-view-view";
	}
	
}
