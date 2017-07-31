package cn.bonoon.core;
/**
 * <pre>
 * 季度报表状态说明：
 * 0 - 未开始 
 * 1 - 已经完成
 * 2 - 正在填报
 * 3 - 驳回
 * 4 - 待审核
 * </pre>
 * @author xiaowu
 *
 */
public interface QuarterReportStatus {

	int NOTSTART  = 0;
	int FINISH = 1;
	int EDIT = 2;
	int REJECT = 3;
	int AUDIT = 4;
}
