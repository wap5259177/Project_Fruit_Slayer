package cn.bonoon.controllers.file.require;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;

@ResetProperties({
	@ResetProperty(value = "name", name = "名称"),
	@ResetProperty(value = "remark", name = "备注"),
	@ResetProperty(value = "other", name = "其他"),
	@ResetProperty(value = "offices", name = "业务处室"),
	@ResetProperty(value = "draftAt", name = "草拟时间"),
	@ResetProperty(value = "issueAt", name = "发布时间"),
	@ResetProperty(value = "startReportedAt", name = "开始时间"),
	@ResetProperty(value = "endReportedAt", name = "结束时间"),
	@ResetProperty(value = "itemCount", name = "总数量"),
	@ResetProperty(value = "finishCount", name = "完成数"),
	@ResetProperty(value = "finishAt", name = "归档时间"),
	@ResetProperty(value = "documentCount", name = "文档数"),
	@ResetProperty(value = "statusIssue", name = "发布状态", options = @OptionArray({"未发布", "<font color='green'>发布</font>"})),
	@ResetProperty(value = "status", name = "状态", options = @OptionArray({"正常", "已归档"})),
	@ResetProperty(value = "remark", name = "备注")
})
public interface RequireReportDefine {

}
