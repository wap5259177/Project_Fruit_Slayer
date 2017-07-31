package cn.bonoon.controllers.file.share;

import cn.bonoon.controllers.account.AccountCondition;

public class FileShareCondition extends AccountCondition {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2483840116793177589L;

	/**
	 * 已经获得文件权限的用户
	 */
	private Boolean searchHasPrivileges;

	public Boolean getSearchHasPrivileges() {
		return searchHasPrivileges;
	}

	public void setSearchHasPrivileges(Boolean searchHasPrivileges) {
		this.searchHasPrivileges = searchHasPrivileges;
	}

}
