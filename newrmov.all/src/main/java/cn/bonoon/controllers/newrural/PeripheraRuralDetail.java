package cn.bonoon.controllers.newrural;

import cn.bonoon.controllers.inofs.BasePeripheralRuralInfo;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormDetail;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@FormDetail(autoIgnore = true, width = 680)
@InsertCell(value = "applicant/peripheralrural-view.vm", type = EmbedType.PARSE)
@WithDialog(initializer = PeripheraRuralDetailInitializer.class)
public class PeripheraRuralDetail extends BasePeripheralRuralInfo implements RuralDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4085005359888779447L;
	
}
