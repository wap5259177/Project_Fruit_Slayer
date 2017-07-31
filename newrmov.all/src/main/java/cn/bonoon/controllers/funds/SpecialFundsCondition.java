package cn.bonoon.controllers.funds;

import cn.bonoon.controllers.funds.allocated.SpecialFundsDefine;
import cn.bonoon.kernel.query.PageCondition;
import cn.bonoon.kernel.web.annotations.condition.ConditionContent;

public class SpecialFundsCondition extends PageCondition implements SpecialFundsDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7236339625548122084L;

	//查询条件加search
	@ConditionContent
	private Integer searchAnnual;

	public Integer getSearchAnnual() {
		return searchAnnual;
	}

	public void setSearchAnnual(Integer searchAnnual) {
		this.searchAnnual = searchAnnual;
	}

}
