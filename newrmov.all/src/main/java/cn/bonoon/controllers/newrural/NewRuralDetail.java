package cn.bonoon.controllers.newrural;

import cn.bonoon.controllers.inofs.BaseNewRuralInfo;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormDetail;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@FormDetail(autoIgnore = true, width = 620)
@InsertCell(value = "applicant/newrural-view.vm", type = EmbedType.PARSE)
@WithDialog(initializer = NewRuralDetailInitializer.class)
public class NewRuralDetail extends BaseNewRuralInfo implements RuralDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4085005359888779447L;
	
}
