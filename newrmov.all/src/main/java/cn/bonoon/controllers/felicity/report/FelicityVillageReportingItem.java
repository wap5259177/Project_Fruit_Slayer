package cn.bonoon.controllers.felicity.report;

import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.PaginationType;

@Transform
@AsDataGrid(pagination = PaginationType.NONE)
public class FelicityVillageReportingItem extends FelicityVillageReportBaseItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 349453988062057511L;

}
