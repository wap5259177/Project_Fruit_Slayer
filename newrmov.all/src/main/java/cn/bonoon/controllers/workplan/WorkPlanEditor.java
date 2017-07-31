package cn.bonoon.controllers.workplan;

import java.util.Calendar;

import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.components.AsEditor;
import cn.bonoon.kernel.web.annotations.components.AsEditor.EditorToolbar;
import cn.bonoon.kernel.web.annotations.components.AsFile;
import cn.bonoon.kernel.web.annotations.components.AsSelector;
import cn.bonoon.kernel.web.annotations.components.SelectorOption;
import cn.bonoon.kernel.web.annotations.form.FormEditor;
import cn.bonoon.kernel.web.annotations.form.PropertyEditor;

@FormEditor(value = 2,headWidth=120,width=250)
@Transform
@WithDialog(initFormClass = true)
public class WorkPlanEditor extends ObjectEditor implements WorkPlanDefine{

	private static final long serialVersionUID = 2608232307955351132L;
	
	@PropertyEditor(value = 0, colspan = 1)
	private String name;
	
	@PropertyEditor(1)
	@AsSelector({
		@SelectorOption(name = "2014", value = "2014"),
		@SelectorOption(name = "2015", value = "2015"),
		@SelectorOption(name = "2016", value = "2016"),
		@SelectorOption(name = "2017", value = "2017")
	})
	private int annual = Calendar.getInstance().get(Calendar.YEAR);
	
	@PropertyEditor(2)
	@AsSelector
	private int monthly;
	
	//----------------2016-6-24
	@PropertyEditor(value = 5, colspan = 1)
	@AsFile
	private String enclosure;//附件上传
	
	@PropertyEditor(value = 12, colspan = 1)
	@AsEditor(toolbar=EditorToolbar.SIMPLE)
	private String content;
	
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public int getMonthly() {
		return monthly;
	}

	public void setMonthly(int monthly) {
		this.monthly = monthly;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
//	
//	public WorkPlanEntity toEntity(WorkPlanEntity wpe,
//			ModelAreaEntity mae) throws ParseException {
//				return wpe;
//		
//	}
//	public void set(WorkPlanEntity wpe) {
//		
//	
//	}

	public int getAnnual() {
		return annual;
	}

	public void setAnnual(int annual) {
		this.annual = annual;
	}

	public String getEnclosure() {
		return enclosure;
	}

	public void setEnclosure(String enclosure) {
		this.enclosure = enclosure;
	}
}
