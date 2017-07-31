package cn.bonoon.core.adjusts;

import cn.bonoon.entities.logs.ModelAreaQuarterComparer;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.SearchService;

public interface ModelAreaQuarterComparerAdjustor extends SearchService<ModelAreaQuarterComparer>{

	/**
	 * 调整所有可能会出现异常的数据
	 * @param opt 
	 */
	void adjustAll(IOperator opt);

}
