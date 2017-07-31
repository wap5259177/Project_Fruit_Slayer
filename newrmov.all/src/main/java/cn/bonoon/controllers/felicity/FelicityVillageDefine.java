package cn.bonoon.controllers.felicity;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;

@ResetProperties({
	@ResetProperty(value = "name", name = "村名"),
	@ResetProperty(value = "townName", name = "镇名"),
	@ResetProperty(value = "naturalVillage", name = "自然村名"),
	@ResetProperty(value="constructionType",name="村庄建设类型"),
	@ResetProperty(value="villageType",name="村庄类型",
		options = @OptionArray(value = {"行政村","自然村"}))
})
public interface FelicityVillageDefine {

}
