package cn.bonoon.controllers.project;

import cn.bonoon.controllers.inofs.BaseProjectInfo;
import cn.bonoon.entities.ProjectEntity;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.DialogDefaultButton;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormEditor;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@Transform
@FormEditor(width = 620)
@InsertCell(value = "applicant/areaproject-editor.vm", type = EmbedType.PARSE)
@DialogDefaultButton(
		buttonName = "暂存",
		functionBody = "_checkNumber();",
		otherButtonName = "提交",
		otherParameterName = "applicant-submit",
		otherParameterValue = "true",
		otherFunctionBody = "if(!confirm('提交后不可能再修改数据，是否确定提交？')){return false;}_checkNumber();"
	)
@WithDialog(initializer = ProjectEditorInitializer.class)
public class ProjectEditor extends BaseProjectInfo implements ProjectDefine{

	private static final long serialVersionUID = -9176142379702216697L;

	@TransformField(value="status",writable=WriteModel.NONE)
	private int status;
	
//	private Integer submit; // 0:暂存 1:提交
	public ProjectEntity toEntity(ProjectEntity entity) {
//		entity.setName(this.name);
//		InvestmentInfo i = entity.getInvestment();
//		i.setTotalFunds(this.totalFunds);
//		i.setStateFunds(this.stateFunds);
//		i.setProvinceFunds(this.provinceFunds);
//		i.setLocalFunds(this.localFunds);
//		i.setSocialFunds(this.socialFunds);
//		i.setOtherFunds(this.otherFunds);
//		//entity.setModelarea(this.modelarea);
////		entity.setBuildPosition(this.buildPosition);
////		entity.setBuildUnit(this.buildUnit);
////		entity.setBuildUnitLeader(this.buildUnitLeader);
////		entity.setLeaderTel(this.leaderTel);
////		entity.setConstructionScale(this.constructionScale);
////		entity.setPlanStartWorkingAt(this.planStartWorkingAt);
////		entity.setPlanCompletedWorkingAt(this.planCompletedWorkingAt);
////		entity.setBenefitUnits(this.benefitUnits);
////		entity.setCurrentProblem(this.currentProblem);
////		entity.setConstructionContent(this.constructionContent);
////		entity.setRealStartWorkingAt(this.realStartWorkingAt);
////		entity.setRealScale(this.realScale);
////		entity.setConstructionStatus(this.constructionStatus);
////		entity.setRealCompletedWorkingAt(this.realCompletedWorkingAt);
////		entity.setCheckAt(this.checkAt);
////		entity.setAuditor(this.auditor);
////		entity.setAuditAt(this.auditAt);
////		entity.setLandArea(this.landArea);
////		entity.setPrincipalTel(this.principalTel);
////		entity.setLocalePrincipal(this.localePrincipal);
////		entity.setConstructionUnit(this.constructionUnit);
//		entity.setStatus(1);
////		entity.setCreateAt(this.createAt);
//		
//		i.setSpecialFunds(this.specialFunds);
//		entity.setProProperty(this.proProperty);
//		entity.setProjectType(this.projectType);
//		entity.setExactKind(this.exactKind);
//		entity.setLabourCount(this.labourCount);
//		entity.setSpend(this.spend);
////		entity.setStartAtY(this.startAtY);
////		entity.setStartAtM(this.startAtM);
////		entity.setEndAtY(this.endAtY);
////		entity.setEndAtM(this.endAtM);
////		entity.setFinishAtY(this.finishAtY);
////		entity.setFinishAtM(this.finishAtM);
////		entity.setAreaName(this.areaName);
////		entity.setRemark(this.remark);
		return entity;
	}

	public void set(ProjectEntity entity) {
//		
//		this.name = entity.getName();
////		this.type = entity.getType().getId();
////		this.newRuralId = entity.getNewRural().getId();
//		this.totalFunds = entity.getInvestment().getTotalFunds();
//		this.stateFunds = entity.getInvestment().getStateFunds();
//		this.provinceFunds = entity.getInvestment().getProvinceFunds();
//		this.localFunds = entity.getInvestment().getLocalFunds();
//		this.socialFunds = entity.getInvestment().getSocialFunds();
//		this.otherFunds = entity.getInvestment().getOtherFunds();
//	//	this.modelarea = entity.getModelarea();
////		this.buildPosition = entity.getBuildPosition();
////		this.buildUnit = entity.getBuildUnit();
////		this.buildUnitLeader = entity.getBuildUnitLeader();
////		this.leaderTel = entity.getLeaderTel();
////		this.constructionUnit = entity.getConstructionUnit();
////		this.localePrincipal = entity.getLocalePrincipal();
////		this.principalTel = entity.getPrincipalTel();
////		this.landArea = entity.getLandArea();
////		this.constructionScale = entity.getConstructionScale();
////		this.planStartWorkingAt = entity.getPlanStartWorkingAt();
////		this.planCompletedWorkingAt = entity.getPlanCompletedWorkingAt();
////		this.benefitUnits = entity.getBenefitUnits();
////		this.currentProblem = entity.getCurrentProblem();
////		this.constructionContent = entity.getConstructionContent();
////		this.realStartWorkingAt = entity.getRealStartWorkingAt();
////		this.realScale = entity.getRealScale();
////		this.constructionStatus = entity.getConstructionStatus();
////		this.realCompletedWorkingAt = entity.getRealCompletedWorkingAt();
////		this.checkAt = entity.getCheckAt();
////		this.auditor = entity.getAuditor();
////		this.auditAt = entity.getAuditAt();
//		
//		this.specialFunds = entity.getInvestment().getSpecialFunds();
//		this.proProperty = entity.getProProperty();
//		this.projectType = entity.getProjectType();
//		this.exactKind = entity.getExactKind();
//		this.labourCount = entity.getLabourCount();
//		this.spend = entity.getSpend();
////		this.startAtY = entity.getStartAtY();
////		this.startAtM = entity.getStartAtM();
////		this.endAtY = entity.getEndAtY();
////		this.endAtM = entity.getEndAtM();
////		this.finishAtY = entity.getFinishAtY();
////		this.finishAtM = entity.getFinishAtM();
////		this.areaName = entity.getAreaName();
////		this.remark = entity.getRemark();
////		this.createAt = entity.getCreateAt();
////		this.creatorName = entity.getCreatorName();
//		
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
//	@TransformField("modelArea.id")
//	@PropertyEditor
//	@AsComboBox
//	@AutoDataLoader(ModelAreaEntity.class)
//	private Long modelArea;

	/**
	 * 以下某些属性是关联实体类的属性
	 */

	
//	private String areaName;
//	//项目类型
//	@TransformField("type.id")
//	private Long type;
//	//村
//	private Long newRuralId;
//	
//	@TransformField("rural.id")
//	private Long newRural;
//	
//	private String code;
//	
//	//资金
//	@TransformField("investment.")
//	private double totalFunds;
//	@TransformField("investment.")
//	private double stateFunds;
//	@TransformField("investment.")
//	private double provinceFunds;
//	@TransformField("investment.")
//	private double localFunds;
//	@TransformField("investment.")
//	private double socialFunds;
//	@TransformField("investment.")
//	private double specialFunds;
//	@TransformField("investment.")
//	private double otherFunds;
//	//累计投入
//	@TransformField("totalInvestment.totalFunds")
//	private double ttotalFunds;
//	@TransformField("totalInvestment.stateFunds")
//	private double tstateFunds;
//	@TransformField("totalInvestment.provinceFunds")
//	private double tprovinceFunds;
//	@TransformField("totalInvestment.localFunds")
//	private double tlocalFunds;
//	@TransformField("totalInvestment.socialFunds")
//	private double tsocialFunds;
//	@TransformField("totalInvestment.specialFunds")
//	private double tspecialFunds;
//	@TransformField("totalInvestment.otherFunds")
//	private double totherFunds;
//	
//	private String proProperty;
//	private String name;
//	private String projectType;
//	private String exactKind;
//	private int labourCount;
//	private double spend;
//	
//	private String startAtY;
//	private String startAtM;
//	private String endAtY;
//	private String endAtM;
//	private String finishAtY;
//	private String finishAtM;
//	private String remark;

//	private String creatorName;
//	private Date createAt;
//	public double getTotalFunds() {
//		return totalFunds;
//	}
//
//	public double getTtotalFunds() {
//		return ttotalFunds;
//	}
//
//	public void setTtotalFunds(double ttotalFunds) {
//		this.ttotalFunds = ttotalFunds;
//	}
//
//	public double getTstateFunds() {
//		return tstateFunds;
//	}
//
//	public void setTstateFunds(double tstateFunds) {
//		this.tstateFunds = tstateFunds;
//	}
//
//	public double getTprovinceFunds() {
//		return tprovinceFunds;
//	}
//
//	public void setTprovinceFunds(double tprovinceFunds) {
//		this.tprovinceFunds = tprovinceFunds;
//	}
//
//	public double getTlocalFunds() {
//		return tlocalFunds;
//	}
//
//	public void setTlocalFunds(double tlocalFunds) {
//		this.tlocalFunds = tlocalFunds;
//	}
//
//	public double getTsocialFunds() {
//		return tsocialFunds;
//	}
//
//	public void setTsocialFunds(double tsocialFunds) {
//		this.tsocialFunds = tsocialFunds;
//	}
//
//	public double getTspecialFunds() {
//		return tspecialFunds;
//	}
//
//	public void setTspecialFunds(double tspecialFunds) {
//		this.tspecialFunds = tspecialFunds;
//	}
//
//	public double getTotherFunds() {
//		return totherFunds;
//	}
//
//	public void setTotherFunds(double totherFunds) {
//		this.totherFunds = totherFunds;
//	}
//
//	public void setTotalFunds(double totalFunds) {
//		this.totalFunds = totalFunds;
//	}
//
//	public double getStateFunds() {
//		return stateFunds;
//	}
//
//	public void setStateFunds(double stateFunds) {
//		this.stateFunds = stateFunds;
//	}
//
//	public double getProvinceFunds() {
//		return provinceFunds;
//	}
//
//	public void setProvinceFunds(double provinceFunds) {
//		this.provinceFunds = provinceFunds;
//	}
//
//	public double getLocalFunds() {
//		return localFunds;
//	}
//
//	public void setLocalFunds(double localFunds) {
//		this.localFunds = localFunds;
//	}
//
//	public double getSocialFunds() {
//		return socialFunds;
//	}
//
//	public void setSocialFunds(double socialFunds) {
//		this.socialFunds = socialFunds;
//	}
//
//	public double getSpecialFunds() {
//		return specialFunds;
//	}
//
//	public void setSpecialFunds(double specialFunds) {
//		this.specialFunds = specialFunds;
//	}
//
//	public double getOtherFunds() {
//		return otherFunds;
//	}
//
//	public void setOtherFunds(double otherFunds) {
//		this.otherFunds = otherFunds;
//	}
//
//	public String getProProperty() {
//		return proProperty;
//	}
//
//	public void setProProperty(String proProperty) {
//		this.proProperty = proProperty;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getProjectType() {
//		return projectType;
//	}
//
//	public void setProjectType(String projectType) {
//		this.projectType = projectType;
//	}
//
//	public String getExactKind() {
//		return exactKind;
//	}
//
//	public void setExactKind(String exactKind) {
//		this.exactKind = exactKind;
//	}
//
//	public int getLabourCount() {
//		return labourCount;
//	}
//
//	public void setLabourCount(int labourCount) {
//		this.labourCount = labourCount;
//	}
//
//	public double getSpend() {
//		return spend;
//	}
//
//	public void setSpend(double spend) {
//		this.spend = spend;
//	}
//
//	public String getStartAtY() {
//		return startAtY;
//	}
//
//	public void setStartAtY(String startAtY) {
//		this.startAtY = startAtY;
//	}
//
//	public String getStartAtM() {
//		return startAtM;
//	}
//
//	public void setStartAtM(String startAtM) {
//		this.startAtM = startAtM;
//	}
//
//	public String getEndAtY() {
//		return endAtY;
//	}
//
//	public void setEndAtY(String endAtY) {
//		this.endAtY = endAtY;
//	}
//
//	public String getEndAtM() {
//		return endAtM;
//	}
//
//	public void setEndAtM(String endAtM) {
//		this.endAtM = endAtM;
//	}
//
//	public String getFinishAtY() {
//		return finishAtY;
//	}
//
//	public void setFinishAtY(String finishAtY) {
//		this.finishAtY = finishAtY;
//	}
//
//	public String getFinishAtM() {
//		return finishAtM;
//	}
//
//	public void setFinishAtM(String finishAtM) {
//		this.finishAtM = finishAtM;
//	}
//
//	public String getRemark() {
//		return remark;
//	}
//
//	public void setRemark(String remark) {
//		this.remark = remark;
//	}
//
//	public String getCode() {
//		return code;
//	}
//
//	public void setCode(String code) {
//		this.code = code;
//	}
//
//	public Long getNewRural() {
//		return newRural;
//	}
//
//	public void setNewRural(Long newRural) {
//		this.newRural = newRural;
//	}
	
}
