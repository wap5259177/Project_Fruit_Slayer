package cn.bonoon.controllers.file.management;

import java.util.Date;

import cn.bonoon.controllers.file.FileDefine;
import cn.bonoon.kernel.annotations.Ignore;
import cn.bonoon.kernel.annotations.QueryJoin;
import cn.bonoon.kernel.annotations.condition.ConditionField;
import cn.bonoon.kernel.annotations.condition.ConditionOpt;
import cn.bonoon.kernel.query.PageCondition;
import cn.bonoon.kernel.util.Opt;

public class FileCondition extends PageCondition implements FileDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -353067162781653705L;
	private String searchName;
	// private String searchExt;

	@ConditionField(value = "createAt")
	@ConditionOpt(Opt.GE)
	// @ConditionContent(value = "时间  从", ordinal = 20)
	private Date searchStartAt;

	@ConditionField(value = "createAt")
	@ConditionOpt(Opt.LE)
	// @ConditionContent(value = "到", ordinal = 21)
	private Date searchEndAt;

	@ConditionField(value = "id", joins = @QueryJoin("directory"))
	private Long searchDirectory;

	@Ignore
	private Integer searchType;

	public Long getSearchDirectory() {
		return searchDirectory;
	}

	public void setSearchDirectory(Long searchDirectory) {
		this.searchDirectory = searchDirectory;
	}

	public Date getSearchStartAt() {
		return searchStartAt;
	}

	public void setSearchStartAt(Date searchStartAt) {
		this.searchStartAt = searchStartAt;
	}

	public Date getSearchEndAt() {
		return searchEndAt;
	}

	public void setSearchEndAt(Date searchEndAt) {
		this.searchEndAt = searchEndAt;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public Integer getSearchType() {
		return searchType;
	}

	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}

}
