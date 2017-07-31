package cn.bonoon.controllers.project.report;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;


@ResetProperties({
	@ResetProperty(value = "annual", name = "年度"),
	@ResetProperty(value = "period", name = "季度",options = @OptionArray(value = {"第一季度", "第二季度","第三季度","第四季度"}, offset = 0)),
	@ResetProperty(value = "deadline", name = "截止时间"),
	@ResetProperty(value = "startAt",name="开始上报时间"),
	@ResetProperty(value = "endAt",name="结束上报时间"),
	@ResetProperty(value = "urge", name = "催报(次)"),
	@ResetProperty(value = "status", name = "状态", 
		options = @OptionArray(value = {
				"<font color='red'>未开始</font>", //0
				"<font color='green'>完成</font>", //1
				"<font color='blue'>正在填报</font>",//2
				"<font color='blue'>驳回</font>",//3
				"<font color='green'>待审核</font>"//4
			})),
	@ResetProperty(value = "isLock", name = "锁定", 
	options = @OptionArray(value = {"<font color='blue'>否</font>","<font color='red'>是</font>"}))
})
public interface LocalQuarterDefine {

}
