package cn.bonoon.controllers.area;

import cn.bonoon.controllers.inofs.BaseModelAreaInfo;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormDetail;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@FormDetail(autoIgnore = true, width = 700)
@InsertCell(value = "applicant/modelarea-view.vm", type = EmbedType.PARSE)
@WithDialog(initializer = ModelAreaDetailSimpleInitializer.class, height = 500)
public class ModelAreaDetailSimple extends BaseModelAreaInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8463501164233155359L;
	
}
