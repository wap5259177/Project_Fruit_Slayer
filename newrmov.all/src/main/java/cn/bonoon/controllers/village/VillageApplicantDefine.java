package cn.bonoon.controllers.village;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;


@ResetProperties({
	@ResetProperty(value = "countyName", name = "县(市、区)"),
	@ResetProperty(value = "townName", name = "镇"),
	@ResetProperty(value = "name", name = "村"),
	@ResetProperty(value = "contactName", name = "联系人"),
	@ResetProperty(value = "contactPhone", name = "联系电话"),
	@ResetProperty(value = "applicantAt", name = "申报日期"),
	@ResetProperty(value = "status", name = "申请状态", 
		options = @OptionArray(value = {"暂存", "通过审核", "取消申请", "驳回", "等待审核"}))
})
public interface VillageApplicantDefine {

}
