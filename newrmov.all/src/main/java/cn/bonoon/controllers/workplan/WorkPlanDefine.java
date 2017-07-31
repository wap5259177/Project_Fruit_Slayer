package cn.bonoon.controllers.workplan;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;

@ResetProperties({
	@ResetProperty(value = "name", name = "名称"),
	@ResetProperty(value = "annual", name = "年度"),
	@ResetProperty(value = "monthly", name = "月度", 
		options = @OptionArray(value = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"})),
	@ResetProperty(value = "unitName", name = "单位"),
	@ResetProperty(value = "createAt", name = "创建时间"),
	@ResetProperty(value = "enclosure", name = "附件上传"),
	@ResetProperty(value = "content", name = "内容")
})
public interface WorkPlanDefine {

}
