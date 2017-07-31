package cn.bonoon.controllers.project;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;

@ResetProperties({
	@ResetProperty(value = "modelArea", name = "示范片区"),
	@ResetProperty(value = "name", name = "项目名称"),
	@ResetProperty(value = "code", name = "项目编号"),
	@ResetProperty(value="town",name="乡、镇名"),
	@ResetProperty(value="villageName",name="行政村名"),
	@ResetProperty(value = "naturalVillage", name = "自然村名"),
	@ResetProperty(value = "proProperty", name = "项目属性"),
	@ResetProperty(value = "projectType", name = "项目类型"),
	@ResetProperty(value = "spend", name = "投资额"),
	@ResetProperty(value = "labourCount", name = "劳动人口"),
	@ResetProperty(value = "cityName", name = "市名"),
	@ResetProperty(value = "areaName", name = "示范片区"),
	@ResetProperty(value = "specialFunds", name = "省级新农村连片示范工程建设补助资金"),
	@ResetProperty(value = "totalFunds", name = "合计"),
	@ResetProperty(value = "status", name = "状态", 
		options = @OptionArray(value = {
					"未提交", 
					"<font color='blue'>进行中</font>", 
					"<font color='green'>竣工</font>", 
					"<font color='red'>终止</font>",
					"<font color='red'>驳回</font>"
				}))
})
public interface ProjectDefine {

}
