package cn.bonoon.controllers.industry;

import cn.bonoon.kernel.query.PageCondition;
import cn.bonoon.kernel.web.annotations.condition.ConditionContent;

public class IndustryAreaCondition extends PageCondition implements IndustryAreaDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1252247940731199125L;
	@ConditionContent(headWidth=180)//headWidth:设置名字的显示宽度
	private String searchCoopName;

	public String getSearchCoopName() {
		return searchCoopName;
	}

	public void setSearchCoopName(String searchCoopName) {
		this.searchCoopName = searchCoopName;
	}
}
