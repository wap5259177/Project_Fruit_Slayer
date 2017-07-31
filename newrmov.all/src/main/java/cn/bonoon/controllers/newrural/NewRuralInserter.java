package cn.bonoon.controllers.newrural;

import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.annotations.AutoDataLoader;
import cn.bonoon.kernel.web.annotations.components.AsComboBox;
import cn.bonoon.kernel.web.annotations.form.FormInsert;
import cn.bonoon.kernel.web.annotations.form.PropertyEditor;

@Transform
//@WithDialog(width = 900,height = 500)
@FormInsert(headWidth = 150,width = 200, value=2)
public class NewRuralInserter extends ObjectEditor implements RuralDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5107382376704760408L;

	

	//这里就是nr 的name
	@PropertyEditor(value=1,required=true)
	private String naturalVillage;
	
	@TransformField(writable= WriteModel.INSERTONLY)
	@PropertyEditor(value=20,required=true)
	@AsComboBox()
	@AutoDataLoader(value=AdministrationCoreRuralEntity.class,
		queries= @QueryExpression("x.modelArea.ownerId={USER owner} and x.deleted=false"))
	private Long adminRural;
	
	public Long getAdminRural() {
		return adminRural;
	}
	public void setAdminRural(Long adminRural) {
		this.adminRural = adminRural;
	}
	public String getNaturalVillage() {
		return naturalVillage;
	}
	public void setNaturalVillage(String naturalVillage) {
		this.naturalVillage = naturalVillage;
	}
	
	
	
}
