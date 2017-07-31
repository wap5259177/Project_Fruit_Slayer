package cn.bonoon.controllers.felicity;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;

@ResetProperties({
	@ResetProperty(value = "townName", name = "县名"),
	@ResetProperty(value = "name", name = "行政村名"),
	@ResetProperty(value = "annual", name = "年度"),
	@ResetProperty(value = "month", name = "月份",
			options = @OptionArray({"1","2","3","4","5","6","7","8","9","10","11","12"})),
	@ResetProperty(value = "naturalVillage", name = "自然村名"),
	@ResetProperty(value = "constructionType", name = "村庄建设类型"),
	@ResetProperty(value = "villageType", name = "村庄类型"),
	@ResetProperty(value = "constructionCharacteristic", name = "村庄建设特色"),
	@ResetProperty(value = "planningCompleted", name = "是否完成<br/>规划设计", options = @OptionArray({"否","是"})),
	@ResetProperty(value = "biddingCompleted", name = "是否完成<br/>项目招<br/>投标", options = @OptionArray({"否","是"})),
	@ResetProperty(value = "effectRemediation", name = "是否完成<br/>环境卫生<br/>整治", options = @OptionArray({"否","是"})),
	@ResetProperty(value = "effectUniformStyle", name = "是否统一<br/>民居外立<br/>面风貌", options = @OptionArray({"否","是"})),
	@ResetProperty(value = "effectSolveGarbage", name = "是否解决<br/>垃圾问题", options = @OptionArray({"否","是"})),
	@ResetProperty(value = "effectSewageTreatment", name = "是否建立<br/>污水处理<br/>设施", options = @OptionArray({"否","是"})),
	@ResetProperty(value = "effectCleaningTeam", name = "是否建立<br/>村保洁<br/>队伍", options = @OptionArray({"否","是"})),
	@ResetProperty(value = "effectCouncil", name = "是否成立<br/>村民理事会", options = @OptionArray({"否","是"})),
	@ResetProperty(value = "effectHardBottom", name = "是否完成<br/>村巷道硬<br/>底化建设", options = @OptionArray({"否","是"})),
	@ResetProperty(value = "effectThroughWater", name = "是否解决<br/>通自来水", options = @OptionArray({"否","是"})),
	@ResetProperty(value = "effectOther", name = "其它"),
	@ResetProperty(value = "constructionArea", name = "村庄建设<br/>覆盖面积"),
	@ResetProperty(value = "householdCount", name = "户数"),
	@ResetProperty(value = "population", name = "人口数"),
	@ResetProperty(value = "projectCount", name = "确定的建<br/>设项目数<br/>（个）"),
	@ResetProperty(value = "projectProgress", name = "项目建设<br/>进度（%）"),
	@ResetProperty(value = "investmentBudget", name = "投入预算<br/>（万元）"),
	@ResetProperty(value = "investmentCompleted", name = "目前已完<br/>成投入<br/>（万元）"),
	@ResetProperty(value = "fundsTotal", name = "资金来源<br/>（万元）<br/>小计"),
	@ResetProperty(value = "fundsProvince", name = "省"),
	@ResetProperty(value = "fundsCity", name = "市"),
	@ResetProperty(value = "fundsCounty", name = "县"),
	@ResetProperty(value = "fundsTown", name = "镇"),
	@ResetProperty(value = "fundsVillage", name = "村"),
	@ResetProperty(value = "fundsMasses", name = "群众"),
	@ResetProperty(value = "fundsSociety", name = "社会"),
	@ResetProperty(value = "fundsOther", name = "其它"),
	@ResetProperty(value = "planningProgress", name = "规划进度<br/>（%）"),
	@ResetProperty(value = "biddingProgress", name = "完成招投<br/>标比例<br/>（%）"),
	@ResetProperty(value = "nextStagePlanning", name = "下阶段打算"),
	@ResetProperty(value="status",name="状态",
		options = @OptionArray(value = {"草稿","通过","<font color='blue'>待审核</font>","<font color='red'>不通过</font>"}))
})
public interface FelicityVillageReportDefine {

}
