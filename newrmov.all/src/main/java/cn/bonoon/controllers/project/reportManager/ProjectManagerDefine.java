package cn.bonoon.controllers.project.reportManager;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;

@ResetProperties({
	@ResetProperty(value = "cityName", name = "市名"),
	@ResetProperty(value = "county", name = "县名"),
	@ResetProperty(value = "batch", name = "批次"),
	@ResetProperty(value = "areaName", name = "示范片区"),
	@ResetProperty(value="town",name="乡、镇名"),
	@ResetProperty(value="villageName",name="行政村名"),
	@ResetProperty(value = "naturalVillage", name = "自然村名"),
	@ResetProperty(value = "code", name = "项目编号"),
	@ResetProperty(value = "name", name = "项目名称"),
	@ResetProperty(value = "proProperty", name = "项目属性"),
	@ResetProperty(value = "projectType", name = "项目类型"),
	@ResetProperty(value = "specialFunds", name = "预计专项资金"),
	@ResetProperty(value = "totalFunds", name = "预计投入"),
	@ResetProperty(value = "status", name = "状态", 
	options = @OptionArray(value = {
				"未提交", 
				"<font color='blue'>进行中</font>", 
				"<font color='green'>竣工</font>", 
				"<font color='red'>终止</font>",
				"<font color='red'>驳回</font>"
			}))
})
public interface ProjectManagerDefine {

}
