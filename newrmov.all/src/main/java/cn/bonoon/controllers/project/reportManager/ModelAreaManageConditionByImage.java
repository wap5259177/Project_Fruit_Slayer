package cn.bonoon.controllers.project.reportManager;

import cn.bonoon.kernel.query.PageCondition;
import cn.bonoon.kernel.web.annotations.condition.ConditionContent;

public class ModelAreaManageConditionByImage extends PageCondition implements
		ProjectImageDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2732880769234091797L;


	//定义界面搜索框search+需要查询的字段该字段由注解了本类的那个类提供的字段如下面的name其实也是数据库实体某个字段属性名
	@ConditionContent(ordinal = 3)
	private String searchName;

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

}
