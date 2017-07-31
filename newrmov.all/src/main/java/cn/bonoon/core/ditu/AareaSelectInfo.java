package cn.bonoon.core.ditu;

public class AareaSelectInfo {

	private Long value;
	private String name;
	private boolean selected;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public AareaSelectInfo(String name,boolean selected){
		this.name  = name;
		this.selected = selected;
	}
	
	public AareaSelectInfo(Long id,String name,boolean selected){
		this.setValue(id);
		this.name  = name;
		this.selected = selected;
	}
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	
	
}
