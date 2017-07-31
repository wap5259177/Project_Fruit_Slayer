package cn.bonoon.controllers.information;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;

@ResetProperties({
	@ResetProperty(value = "name", name = "填报名称"),
	@ResetProperty(value = "annual", name = "年度"),
	@ResetProperty(value = "startTime", name = "统计起始时间"),
	@ResetProperty(value = "deadline", name = "统计截止时间"),
	@ResetProperty(value = "startAt", name = "填报起始时间"),
	@ResetProperty(value = "endAt", name = "填报截止时间"),
//	@ResetProperty(value = "month", name = "月份",
//			options = @OptionArray(value = {"1","2","3","4","5","6","7","8","9","10","11","12"})),
	@ResetProperty(value = "status",   name = "状态",    
	options = @OptionArray(value = {
			"草稿", 
			"<font color='green'>进行中</font>",
			"<font color='color'>待审核</font>",
			"<font color='color'>驳回</font>",
			"完成"
	})),
})
public interface ProvinceInformationDefine {
	
}
