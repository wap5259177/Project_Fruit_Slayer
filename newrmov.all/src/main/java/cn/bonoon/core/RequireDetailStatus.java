package cn.bonoon.core;

/**
 * <pre>
 * 文档管理状态说明：
 * 0 - 未签收 
 * 1 - 未查收
 * 2 - 已签收-未提交
 * 3 - 已查收
 * 4 - 补报
 * </pre>
 * @author 
 *
 */

public interface RequireDetailStatus {

	int NOTSIGN  = 0;
	int NOTFING = 1;
	int SIGN_UNCOM = 2;
	int FINISH = 3;
	int REPAY = 4;
	
}
