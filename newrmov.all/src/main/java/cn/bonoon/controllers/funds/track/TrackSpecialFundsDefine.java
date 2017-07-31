package cn.bonoon.controllers.funds.track;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;

@ResetProperties({
	@ResetProperty(value = "annual", name = "年度"),
	@ResetProperty(value = "quota", name = "额度"),
	@ResetProperty(value = "contactName", name = "联系人"),
	@ResetProperty(value = "contactPhone", name = "联系电话"),
	@ResetProperty(value = "recordAt", name = "下拨时间"),
	@ResetProperty(value = "place", name = "地区"),
	@ResetProperty(value = "creatorId", name = "分类名（ID）"),
	@ResetProperty(value = "createAt", name = "创建时间"),
	@ResetProperty(value = "status", name = "状态", options = @OptionArray(value = {"禁用", "未启用", "正常", "其它"}, offset = -1)),
	@ResetProperty(value = "remark", name = "备注")
})
public interface TrackSpecialFundsDefine {

}
