package cn.bonoon.controllers.file.reportitem;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;

@ResetProperties({
	@ResetProperty(value = "name", name = "送报通知"),
	@ResetProperty(value = "rname", name = "名称"),
	@ResetProperty(value = "offices", name = "业务处室"),
	@ResetProperty(value = "startReportedAt", name = "开始时间"),
	@ResetProperty(value = "endReportedAt", name = "结束时间"),
	@ResetProperty(value = "submitAt", name = "提交时间"),
	@ResetProperty(value = "unit", name = "上报单位"),
	@ResetProperty(value = "urge", name = "催报次数"),
	@ResetProperty(value = "status", name = "状态", 
	                         options = @OptionArray(value ={"<font color='red'>未签收</font>","<font color='red'>未查收</font>","未提交",  "已查收","补报"})),
	@ResetProperty(value = "documentCount", name = "文档数量")
})
public interface RequireReportItemDefine {

}
