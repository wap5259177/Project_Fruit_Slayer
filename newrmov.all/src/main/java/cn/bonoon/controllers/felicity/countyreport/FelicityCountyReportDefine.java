package cn.bonoon.controllers.felicity.countyreport;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;

@ResetProperties({
	@ResetProperty(value = "name", name = "县名"),//
	@ResetProperty(value = "annual", name = "年度"),
	@ResetProperty(value = "month", name = "月份",
			options = @OptionArray(value = {"1","2","3","4","5","6","7","8","9","10","11","12"})),
	@ResetProperty(value = "informant", name = "填报人"),
	@ResetProperty(value = "contact", name = "联系电话"),
	@ResetProperty(value = "reportingAt", name = "上报时间"),
	@ResetProperty(value = "constructionCharacteristic0", name = "人文历史"),
	@ResetProperty(value = "constructionCharacteristic1", name = "乡村旅游"),
	@ResetProperty(value = "constructionCharacteristic2", name = "自然生态"),
	@ResetProperty(value = "constructionCharacteristic3", name = "特色产业"),
	@ResetProperty(value = "constructionCharacteristic4", name = "民居风貌"),
	@ResetProperty(value = "constructionCharacteristic5", name = "渔业渔港"),
	@ResetProperty(value = "constructionCharacteristic6", name = "其它"),
	@ResetProperty(value = "planningCompletedTrue", name = "完成规划设计"),
	@ResetProperty(value = "planningCompletedFalse", name = "未完成规划设计"),
	@ResetProperty(value = "biddingCompletedTrue", name = "完成项目招投标"),
	@ResetProperty(value = "biddingCompletedFalse", name = "未完成项目招投标"),
	@ResetProperty(value = "effectRemediationTrue", name = "完成环境卫生整治"),
	@ResetProperty(value = "effectRemediationFalse", name = "未完成环境卫生整治"),
	@ResetProperty(value = "effectUniformStyleTrue", name = "已统一民居外立面风貌"),
	@ResetProperty(value = "effectUniformStyleFalse", name = "未统一民居外立面风貌"),
	@ResetProperty(value = "effectSolveGarbageTrue", name = "已解决垃圾问题"),
	@ResetProperty(value = "effectSolveGarbageFalse", name = "未解决垃圾问题"),
	@ResetProperty(value = "effectSewageTreatmentTrue", name = "已建立污水处理设施"),
	@ResetProperty(value = "effectSewageTreatmentFalse", name = "未建立污水处理设施"),
	@ResetProperty(value = "effectCleaningTeamTrue", name = "已建立村保洁队伍"),
	@ResetProperty(value = "effectCleaningTeamFalse", name = "未建立村保洁队伍"),
	@ResetProperty(value = "effectCouncilTrue", name = "已成立村民理事会"),
	@ResetProperty(value = "effectCouncilFalse", name = "未成立村民理事会"),
	@ResetProperty(value = "effectHardBottomTrue", name = "已完成村巷道硬底化建设"),
	@ResetProperty(value = "effectHardBottomFalse", name = "未完成村巷道硬底化建设"),
	@ResetProperty(value = "effectThroughWaterTrue", name = "已通自来水"),
	@ResetProperty(value = "effectThroughWaterFalse", name = "未通自来水"),
	@ResetProperty(value = "constructionArea", name = "村庄建设覆盖面积"),
	@ResetProperty(value = "householdCount", name = "户数"),
	@ResetProperty(value = "population", name = "人口数"),
	@ResetProperty(value = "projectCount", name = "确定的建设项目数（个）"),
	@ResetProperty(value = "projectProgress", name = "项目建设进度（%）"),
	@ResetProperty(value = "investmentBudget", name = "投入预算（万元）"),
	@ResetProperty(value = "investmentCompleted", name = "目前已完成投入（万元）"),
	@ResetProperty(value = "fundsTotal", name = "资金来源（万元）小计"),
	@ResetProperty(value = "fundsProvince", name = "省"),
	@ResetProperty(value = "fundsCity", name = "市"),
	@ResetProperty(value = "fundsCounty", name = "县"),
	@ResetProperty(value = "fundsTown", name = "镇"),
	@ResetProperty(value = "fundsVillage", name = "村"),
	@ResetProperty(value = "fundsMasses", name = "群众"),
	@ResetProperty(value = "fundsSociety", name = "社会"),
	@ResetProperty(value = "fundsOther", name = "其它"),
	@ResetProperty(value = "planningProgress", name = "规划进度（%）"),
	@ResetProperty(value = "biddingProgress", name = "完成招投标比例（%）"),
	@ResetProperty(value = "auditContent", name = "审核建议"),
	
	
	@ResetProperty(value="status",name="状态",
		options = @OptionArray(value = {"草稿","通过","<font color='blue'>待审核</font>","<font color='red'>不通过</font>"}))
})
public interface FelicityCountyReportDefine {
	
}
