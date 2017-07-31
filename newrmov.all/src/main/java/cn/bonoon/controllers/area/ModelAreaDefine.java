package cn.bonoon.controllers.area;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;

@ResetProperties({
	@ResetProperty(value = "name", name = "片区名"),
	@ResetProperty(value = "applicatAt", name = "申报时间"),
	@ResetProperty(value = "cityName", name = "市名"),
	@ResetProperty(value = "contactName", name = "联系人"),
	@ResetProperty(value = "contactPhone", name = "固定电话"),
	@ResetProperty(value = "contactJob", name = "职务"),
	@ResetProperty(value = "contactPhone2", name = "手机号码"),
	@ResetProperty(value = "remark", name = "备注"),
	@ResetProperty(value = "county", name = "县名"),
	@ResetProperty(value = "batch", name = "批次"),
	@ResetProperty(value = "status", name = "状态", 
		options = @OptionArray(value = {"草稿", "<font color='blue'>暂存</font>", "正常", "终止", "<font color='red'>驳回</font>", "待审核"}, offset = -1))
})
public interface ModelAreaDefine {
	
}
