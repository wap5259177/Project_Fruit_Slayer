package cn.bonoon.controllers.industry;

import cn.bonoon.controllers.inofs.BaseIndustryAreaInfo;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormDetail;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@FormDetail(autoIgnore = true, width = 620)
@InsertCell(value = "applicant/areaindustry-view.vm", type = EmbedType.PARSE)
@WithDialog(initializer = IndustryAreaDetailInitializer.class)
@Transform(write = WriteModel.NONE)
public class IndustryAreaDetail extends BaseIndustryAreaInfo implements IndustryAreaDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4085005359888779447L;
	
}
