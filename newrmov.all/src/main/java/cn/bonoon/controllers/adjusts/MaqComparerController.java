package cn.bonoon.controllers.adjusts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.core.adjusts.ModelAreaQuarterComparerAdjustor;
import cn.bonoon.entities.logs.ModelAreaQuarterComparer;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.models.JsonResult;

@Controller
@RequestMapping("s/pl/adjust/maq")
public class MaqComparerController extends AbstractGridController<ModelAreaQuarterComparer, MaqComparerItem>{

	private ModelAreaQuarterComparerAdjustor maqcAdjustor;
	
	@Autowired
	public MaqComparerController(ModelAreaQuarterComparerAdjustor maqcAdjustor) {
		super(maqcAdjustor);
		this.maqcAdjustor = maqcAdjustor;
	}

	@GridStandardDefinition()
	@QueryExpression("x.status=0")
	@Override
	protected ModelAreaQuarterComparerAdjustor initLayoutGrid(LayoutGridRegister register) throws Exception {
		
		register.toolbar("手动调整", "adjust.do", ButtonEventType.GET);
		return maqcAdjustor;
	}
	
	@ResponseBody
	@RequestMapping("!{key}/adjust.do")
	public JsonResult adjust(){
		try{
			maqcAdjustor.adjustAll(getUser());
			return JsonResult.result();
		}catch(Exception ex){
			ex.printStackTrace();
			return JsonResult.error(ex);
		}
	}
}
