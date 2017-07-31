package cn.bonoon.controllers.project;

import cn.bonoon.controllers.inofs.BaseProjectInfo;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormDetail;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@FormDetail(autoIgnore = true, width = 620)
@InsertCell(value = "applicant/areaproject-view.vm", type = EmbedType.PARSE)
@WithDialog(initializer = ProjectDetailInitializer.class)
public class ProjectDetail extends BaseProjectInfo implements ProjectDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4085005359888779447L;
	
}
