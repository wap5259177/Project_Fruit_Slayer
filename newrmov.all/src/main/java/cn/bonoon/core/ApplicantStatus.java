package cn.bonoon.core;

/**
 * <pre>
 * 申请状态说明：
 * 0 - 未提交，草稿状态
 * 1 - 已经完成
 * 2 - 已经关闭，即退出整个流程，该申请已经不能再使用
 * 3 - 审核不通过时退回修改，这时是允许修改，但不允许删除
 * 4 - 各种审核的中间状态，如：4为等待县审核、5为县审核通过，等待市审核
 * </pre>
 * @author jackson
 *
 */
public interface ApplicantStatus {
	int UNSTART = -1;
	int DRAFT = 0;
	int FINISH = 1;
	int CLOSED = 2;
	int REJECT = 3;
	int WAIT_AUDIT = 4;
}
