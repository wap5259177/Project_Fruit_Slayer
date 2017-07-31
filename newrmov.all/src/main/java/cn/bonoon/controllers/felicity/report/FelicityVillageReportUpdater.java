package cn.bonoon.controllers.felicity.report;

import cn.bonoon.controllers.felicity.FelicityVillageInfo;
import cn.bonoon.controllers.felicity.FelicityVillageReportDefine;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.DialogDefaultButton;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormUpdate;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@Transform
@InsertCell(value = "felicity/report-village-editor.vm", type = EmbedType.PARSE)
@FormUpdate(width = 620)
@WithDialog(title = "上报村月度报表")
@DialogDefaultButton(buttonName = "上报", functionBody = "_checkNumber();")
public class FelicityVillageReportUpdater extends FelicityVillageInfo implements FelicityVillageReportDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7165987307300637740L;

	@TransformField(value = "village.name", writable = WriteModel.NONE)
//	@PropertyUpdate(readonly = BoolType.TRUE, value = 1)
	private String name;
	@TransformField(value = "village.townName", writable = WriteModel.NONE)
//	@PropertyUpdate(readonly = BoolType.TRUE, value = 0)
	private String townName;
	@TransformField(value = "village.naturalVillage", writable = WriteModel.NONE)
//	@PropertyUpdate(readonly = BoolType.TRUE, value = 3)
	private String naturalVillage;
	@TransformField(value = "village.constructionType", writable = WriteModel.NONE)
//	@PropertyUpdate(readonly = BoolType.TRUE, value = 4)
	private String constructionType;//村庄建设类型
	@TransformField(value = "village.villageType", writable = WriteModel.NONE)
//	@PropertyUpdate(readonly = BoolType.TRUE, value = 5)
	private String villageType;//A为行政村 B为自然村

	@TransformField(value = "countyReport.annual", writable = WriteModel.NONE)
	private int annual;
	@TransformField(value = "countyReport.month", writable = WriteModel.NONE)
	private String month;
	//以下是需要填写的内容
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//	public String getTown() {
//		return town;
//	}
//	public void setTown(String town) {
//		this.town = town;
//	}
	public String getNaturalVillage() {
		return naturalVillage;
	}
	public void setNaturalVillage(String naturalVillage) {
		this.naturalVillage = naturalVillage;
	}
	public String getConstructionType() {
		return constructionType;
	}
	public void setConstructionType(String constructionType) {
		this.constructionType = constructionType;
	}
	public String getVillageType() {
		return villageType;
	}
	public void setVillageType(String villageType) {
		this.villageType = villageType;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getAnnual() {
		return annual;
	}
	public void setAnnual(int annual) {
		this.annual = annual;
	}
}
