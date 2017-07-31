package cn.bonoon.controllers.project.reportManager;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;

@ResetProperties({
		@ResetProperty(value = "name", name = "片区名"),
		@ResetProperty(value = "batch", name = "批次"),
		@ResetProperty(value = "status", name = "状态", options = @OptionArray(value = {
				"草稿", "<font color='blue'>暂存</font>", "正常", "终止",
				"<font color='red'>驳回</font>", "待审核" }, offset = -1)) })
public interface ProjectImageDefine {

}
