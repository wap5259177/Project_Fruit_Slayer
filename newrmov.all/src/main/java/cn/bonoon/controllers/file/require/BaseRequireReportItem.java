package cn.bonoon.controllers.file.require;

import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;

public class BaseRequireReportItem extends AbstractItem implements RequireReportDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5710275799404453351L;

	@AsColumn(width = 180, ordinal = 0)
	private String name;

	@AsColumn(width = 60, ordinal = 5)
	private int itemCount;

	@AsColumn(width = 60, ordinal = 6)
	private int finishCount;

	@AsColumn(width = 100, ordinal = 4)
	private String endReportedAt;

	@AsColumn(width = 160, ordinal = 1)
	private String offices;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public int getFinishCount() {
		return finishCount;
	}

	public void setFinishCount(int finishCount) {
		this.finishCount = finishCount;
	}

	public String getEndReportedAt() {
		return endReportedAt;
	}

	public void setEndReportedAt(String endReportedAt) {
		this.endReportedAt = endReportedAt;
	}

	public String getOffices() {
		return offices;
	}

	public void setOffices(String offices) {
		this.offices = offices;
	}

}
