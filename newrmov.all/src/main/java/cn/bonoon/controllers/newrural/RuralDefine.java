package cn.bonoon.controllers.newrural;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;

@ResetProperties({
	@ResetProperty(value = "name", name = "行政村名"),
	@ResetProperty(value = "naturalVillage", name = "自然村名"),
	@ResetProperty(value="town",name="乡、镇名"),
	@ResetProperty(value="type",name="村庄类型"),
	@ResetProperty(value = "adminRural", name = "所属行政村"),
	@ResetProperty(value = "status", name = "状态", 
	options = @OptionArray(value = {"<font color='red'>未确认</font>", "已确认", "关闭", "放弃"}))
})
public interface RuralDefine {

}
