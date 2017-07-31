package cn.bonoon.core.echart;


/**
 * 饼图
 * @author Administrator
 *
 */
public class PjTypeEchartPie {
	private String name;//对应echart 里面的name
	private int value;//对应echart 里面的value
	public PjTypeEchartPie(){}
	public PjTypeEchartPie(String name,int value){
		this.name = name;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	@Override
	public String toString() {
		String jsonStr = "{name:"+name+","+"value:"+value+"}";
		return jsonStr;
	}
}
