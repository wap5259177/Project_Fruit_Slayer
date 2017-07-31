package cn.bonoon.controllers.newrural;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.PeripheraRuralService;
import cn.bonoon.entities.PeripheralRuralEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;

@Controller
@RequestMapping("s/cl/area/peripheral/rural")
public class PeripheraRuralController extends AbstractGridController<PeripheralRuralEntity, PeripheraRuralItem>{

	private final PeripheraRuralService peripheraRuralService;

	private String dialog;
	@Override
	public void afterPropertiesSet() throws Exception {
		dialog = toUrl("/s/ma/media/0-manager.image");
		super.afterPropertiesSet();
	}
//	@Autowired
//	private FileService fileService;
	
	@Autowired
	protected PeripheraRuralController( PeripheraRuralService peripheraRuralService) {
		super(peripheraRuralService);
		this.peripheraRuralService = peripheraRuralService;
	}

//	@RequestMapping(value = "!{key}/file.detail")
//	public ModelAndView detail(HttpServletRequest request, final Long id) {
//		return FileHelper.detail(request, fileService, id);
//	}
//
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
//			insertClass = PeripheraRuralInserter.class, 
			detailClass = PeripheraRuralDetail.class,
			updateClass = PeripheraRuralEditor.class,
			autoOperation = false,
			deleteOperation=false,
			editStatus = {0, 3}
			)
	@QueryExpression("x.modelArea.status>=0")
	protected PeripheraRuralService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button("图片管理", dialog, ButtonEventType.DIALOG);
		register.button("打印", "index.print", ButtonEventType.JUMP).ordinal(50);
		return peripheraRuralService;
	}
	
	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.DOMAIN;
	}
	
	@RequestMapping("!{key}/index.print")
	public String print(HttpServletRequest request, Long id, String gridid, Model model) {
		
		PeripheralRuralEntity entity =peripheraRuralService.get(id);
		model.addAttribute("form",entity);
		model.addAttribute("title","连片示范建设工程非主体建设自然村情况表");
		model.addAttribute("readonly", "readonly='readonly'").addAttribute("disabled", "disabled='true'");
		return "applicant/peripheralrural-view-view";
	}
	
}
