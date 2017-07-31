package cn.bonoon.controllers.project.report.crbuild;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;


@ResetProperties({
	@ResetProperty(value = "name", name = "县名"),//
	@ResetProperty(value = "batchName", name = "批次"),//
	@ResetProperty(value = "period", name = "季度", options = @OptionArray(value = {"第一季度", "第二季度","第三季度","第四季度","自示范片启动建设以来至2016年6月30日"}, offset = 0)  ),
	@ResetProperty(value = "annual", name = "年度"),
	@ResetProperty(value = "period", name = "季度"),
	@ResetProperty(value = "startAt", name = "开始填报时间"),
	@ResetProperty(value = "endAt", name = "结束填报时间"),
	@ResetProperty(value = "deadline", name = "截止时间"),
	@ResetProperty(value="status",name="状态",
	options = @OptionArray(value = {
			"<font color='red'>草稿</font>", //0
			"<font color='green'>通过</font>", //1
			"<font color='blue'>正在填报</font>",//2
			"<font color='blue'>驳回</font>",//3
			"<font color='green'>待审核</font>"//4
	}))
})
public interface ModelAreaCRBuildQuarterAuditDefine {
	
}
