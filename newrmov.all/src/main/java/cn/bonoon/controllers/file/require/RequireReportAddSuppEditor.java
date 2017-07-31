package cn.bonoon.controllers.file.require;

import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@Transform
@InsertCell(value = "report/add_supp.vm", type = EmbedType.PARSE)
@WithDialog(initializer = RequireReportDetailInitializer.class, width = 750, height = 550)
public class RequireReportAddSuppEditor extends ObjectEditor {

	private static final long serialVersionUID = 7149787322335338133L;

}
