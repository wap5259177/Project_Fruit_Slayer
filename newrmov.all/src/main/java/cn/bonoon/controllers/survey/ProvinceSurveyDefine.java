package cn.bonoon.controllers.survey;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;

@ResetProperties({
	@ResetProperty(value = "annual", name = "年度"),
	@ResetProperty(value = "period", name = "季度"),
	@ResetProperty(value = "deadline", name = "截止时间"),
	@ResetProperty(value = "startAt",name="开始上报时间"),
	@ResetProperty(value = "endAt",name="结束上报时间"),
	@ResetProperty(value = "needReport", name = "需上报(市)"),
	@ResetProperty(value = "startReport", name = "开始上报(市)"),
	@ResetProperty(value = "finishReport", name = "完成上报(市)"),
	@ResetProperty(value = "status", name = "状态", 
		options = @OptionArray(value = {"<font color='blue'>进行中</font>", "<font color='green'>完成</font>"}))
})
public interface ProvinceSurveyDefine {

}
