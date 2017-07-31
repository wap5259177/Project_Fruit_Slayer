package cn.bonoon.controllers.industry;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;

@ResetProperties({
	@ResetProperty(value="name",name="产业名称"),
	@ResetProperty(value="coopName",name="专业合作社名称"),
	@ResetProperty(value = "modelArea", name = "示范片区"),
	@ResetProperty(value="town",name="乡、镇名"),
	@ResetProperty(value="villageName",name="行政村名"),
	@ResetProperty(value = "naturalVillage", name = "自然村名"),
	@ResetProperty(value="villageFea",name="村属性"),
	@ResetProperty(value="reportDate",name="填报日期"),
	@ResetProperty(value = "status", name = "状态", 
	options = @OptionArray(value = {"<font color='red'>未确认</font>", "已确认", "关闭", "放弃"}))
})
public interface IndustryAreaDefine {

}
