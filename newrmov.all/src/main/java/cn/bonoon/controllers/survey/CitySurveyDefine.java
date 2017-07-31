package cn.bonoon.controllers.survey;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;

@ResetProperties({
	@ResetProperty(value = "annual", name = "年度"),
	@ResetProperty(value = "deadline", name = "截止时间"),
	@ResetProperty(value = "startAt",name="开始上报时间"),
	@ResetProperty(value = "endAt",name="结束上报时间"),
	@ResetProperty(value = "countyCount", name = "县个数"),
	@ResetProperty(value = "urge", name = "催报(次)"),
	@ResetProperty(value = "status", name = "状态", 
		options = @OptionArray(value = {"<font color='red'>未开始</font>", "<font color='green'>完成</font>", "<font color='blue'>正在填报</font>"}))
})
public interface CitySurveyDefine {

}
