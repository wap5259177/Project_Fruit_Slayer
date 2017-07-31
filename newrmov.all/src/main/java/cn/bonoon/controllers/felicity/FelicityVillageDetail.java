package cn.bonoon.controllers.felicity;

import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.DialogModelValue;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormDetail;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@FormDetail(width = 620, autoIgnore = true)
@InsertCell(value = "felicity/village-view.vm", type = EmbedType.PARSE)
@WithDialog(title = "村信息", closeButton = false, models = {
	@DialogModelValue(name = "readonly", value = "readonly='readonly'"),
	@DialogModelValue(name = "disabled", value = "disabled='true'")
}, height = 500)
public class FelicityVillageDetail extends FelicityVillageInfo implements FelicityVillageDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7100595389493340285L;

	private String name;//村名
	private String townName;//镇名
	private String naturalVillage;//自然村名
	private String constructionType;//村庄建设类型
	private String villageType;//A为行政村 B为自然村
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
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
	
}
