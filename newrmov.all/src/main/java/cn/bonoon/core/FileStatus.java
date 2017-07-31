package cn.bonoon.core;

import cn.bonoon.kernel.support.entities.Persistable;

public interface FileStatus extends Persistable {

	/**
	 * 删除
	 */
	int STATUS_DELETED = 0;

	/**
	 * 正常状态
	 */
	int STATUS_NORMAL = 1;

	/**
	 * 禁止，不可修改
	 */
	int STATUS_FORBID = -1;
	
	int SHARE_DEFAULT = 0;
	int SHARE_SHARE = 1;
	int SHARE_PRIVATE = 2;

//	int getStatus();
//
//	void setStatus(int status);
}
