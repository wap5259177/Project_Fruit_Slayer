package cn.bonoon.controllers.adjusts;

import cn.bonoon.entities.RuralNeedFinishInfo;
import cn.bonoon.entities.logs.ModelAreaQuarterComparer;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;

@AsDataGrid
@Transform(write = WriteModel.NONE)
public class MaqComparerItem extends AbstractItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8762522931434495246L;
	
	private RuralNeedFinishInfo source;
	
	private RuralNeedFinishInfo target;

	@AsColumn(width = 100, ordinal = 0)
	private String content;
	
	public void initReader(ModelAreaQuarterComparer mqc){
		
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
}
