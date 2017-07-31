package cn.bonoon.entities.felicityVillage;

import javax.persistence.MappedSuperclass;

/**
 * 放小计的字段
 * 
 * 表1,2的小计字段
 * a.表1:一个县季下面要统计一下,他这个县下面的所有片区覆盖面积,户数,人口数,主体村数,辐射村数,完成规划设计数...项目个数,启动项目数
 * b.表2:项目数量,项目预算投入数,完成投入数(万元)
 *
 * @author Administrator
 *
 */
@MappedSuperclass
public abstract class AbstractFVFelicityEntity extends AbstractFVModelAreaEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4659798051495908280L;

	
//	//主体村数
//	@Column(name="C_CRNUM")
//	private int crNum;
//	
//	//辐射村数
//	@Column(name="C_PRNUM")
//	private int prNum;
//	
	
	
//	//项目个数
//	@Column(name="C_PROJECTNUM")
//	private int projectNum;
//	
//	//项目完成个数
//	@Column(name="C_PROJECTFINISHNUM")
//	private int projectFinishNum;

	

//	public int getProjectNum() {
//		return projectNum;
//	}
//
//	public void setProjectNum(int projectNum) {
//		this.projectNum = projectNum;
//	}
//
//	public int getProjectFinishNum() {
//		return projectFinishNum;
//	}
//
//	public void setProjectFinishNum(int projectFinishNum) {
//		this.projectFinishNum = projectFinishNum;
//	}
	

	
	

}
