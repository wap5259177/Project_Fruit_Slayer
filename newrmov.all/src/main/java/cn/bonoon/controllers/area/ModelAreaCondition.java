package cn.bonoon.controllers.area;

import cn.bonoon.controllers.area.ModelAreaDefine;
import cn.bonoon.kernel.query.PageCondition;
import cn.bonoon.kernel.web.annotations.condition.ConditionContent;


public class ModelAreaCondition extends PageCondition implements ModelAreaDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3827245924264531854L;
	
	@ConditionContent
	private String searchName;
	@ConditionContent
	private String searchContactName;
	@ConditionContent
	private String searchContactPhone;
	
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getSearchContactName() {
		return searchContactName;
	}
	public void setSearchContactName(String searchContactName) {
		this.searchContactName = searchContactName;
	}
	public String getSearchContactPhone() {
		return searchContactPhone;
	}
	public void setSearchContactPhone(String searchContactPhone) {
		this.searchContactPhone = searchContactPhone;
	}
	
	
}
