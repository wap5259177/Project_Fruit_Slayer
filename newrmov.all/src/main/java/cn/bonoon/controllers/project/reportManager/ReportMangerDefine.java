package cn.bonoon.controllers.project.reportManager;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;

@ResetProperties({
	@ResetProperty(value = "county", name = "县名"),
	@ResetProperty(value = "batch", name = "批次"),
	@ResetProperty(value = "name", name = "示范片区"),
	@ResetProperty(value = "annual", name = "年份"),
	@ResetProperty(value = "period", name = "月份", 
		options = @OptionArray(value = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"})),
	@ResetProperty(value = "specialFunds", name = "省级专项资金"),
	@ResetProperty(value = "totalFunds", name = "合计"),
	@ResetProperty(value = "status", name = "状态", 
	options = @OptionArray(value = { "<font color='red'>未提交</font>","<font color='blue'>已提交</font>"})),
})
public interface ReportMangerDefine {

}
