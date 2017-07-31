package cn.bonoon.controllers.file.requirereportapply;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;


	@ResetProperties({
		@ResetProperty(value = "name", name = "名称"),
		@ResetProperty(value = "unit", name = "上报单位"),
		@ResetProperty(value = "createAt", name = "创建时间"),
		@ResetProperty(value = "startReportedAt", name = "开始时间"),
		@ResetProperty(value = "endReportedAt", name = "结束时间"),
		@ResetProperty(value = "documentCount",name = "文档数量"),
		@ResetProperty(value = "reason",name = "原因"),
		@ResetProperty(value = "remark",name = "备注"),
		@ResetProperty(value = "report",name = "上报"),
		@ResetProperty(value = "status", name = "状态", options = @OptionArray(value = {"未提交", "等待审核", "审核通过", "审核不通过"}))
	})
	public interface RequireReportApplyDefine {
	
	}