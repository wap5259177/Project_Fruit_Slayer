package cn.bonoon.core;

public interface RequireReportStatus {

	String[] item_options = { "未提交", "已提交", "补报" };
	
	int item_not_sign = 0;//未签收状态
	
	int item_draft = 2;//草稿状态，即已签收，但未上报和提交
	
	int item_submit = 3;//已上报完，提交了
	
	int item_supplement = 4;//补报，即省办退回市或县重新补充材料
	
	int item_accept = 1;//已查收，即省办已经查收市、县上报上来的材料，这个时候，市、县办的上报工作完成
}
