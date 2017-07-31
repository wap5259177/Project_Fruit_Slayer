package cn.bonoon.entities.felicityVillage;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.support.entities.AbstractEntity;

@Entity
@Table(name = "t_fvrural")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class FVBaseRuralEntity extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2850496045461915149L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_MODELAREA_ID")
	protected FVModelAreaReportEntity modelArea;

	/**
	 * 示范片区，这里的地址是县区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_PLACE_ID")
	protected PlaceEntity place;
	
	@ManyToMany(mappedBy = "rurals")
	protected List<FVProjectRuralEntity> projects;
	
//	/**
//	 * 一个村有多个项目
//	 */
//	@OneToMany(mappedBy = "rural")
//	private List<FVProjectRuralEntity> projects;
	
	//-----------------------下面这几个字段有点理解模糊,是村子完成项目的个数,村子启动时间,村子预计完成时间??村子建设项目的进度??还是正对一个片区的啊?-------------------
	/**
	 * 项目个数
	 */
	@Column(name="C_PROJECTNUM")
	protected int projectNum;
	
	/**
	 * 项目完成个数
	 */
	@Column(name="C_PROJECTFINISHNUM")
	protected int projectFinishNum;
	
	
	/**
	 * 启动时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "C_STARTTIME")
	protected Date startTime;
	
	
	/**
	 * 预计完成时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "C_PREDICTFINISHTIME")
	protected Date predictFinishTime;
	
	/**
	 * 建设进度
	 */
	@Column(name = "C_CONSTRUCTPROGRESS")
	protected String constructProgress;
	
	
	/**
	 *	村子的类型:行政村还是自然村
	 */
	@Column(name = "C_TYPE")
	protected String type;


	public FVModelAreaReportEntity getModelArea() {
		return modelArea;
	}


	public void setModelArea(FVModelAreaReportEntity modelArea) {
		this.modelArea = modelArea;
	}


	public PlaceEntity getPlace() {
		return place;
	}


	public void setPlace(PlaceEntity place) {
		this.place = place;
	}


	public List<FVProjectRuralEntity> getProjects() {
		return projects;
	}


	public void setProjects(List<FVProjectRuralEntity> projects) {
		this.projects = projects;
	}


	public int getProjectNum() {
		return projectNum;
	}


	public void setProjectNum(int projectNum) {
		this.projectNum = projectNum;
	}


	public int getProjectFinishNum() {
		return projectFinishNum;
	}


	public void setProjectFinishNum(int projectFinishNum) {
		this.projectFinishNum = projectFinishNum;
	}


	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public Date getPredictFinishTime() {
		return predictFinishTime;
	}


	public void setPredictFinishTime(Date predictFinishTime) {
		this.predictFinishTime = predictFinishTime;
	}


	public String getConstructProgress() {
		return constructProgress;
	}


	public void setConstructProgress(String constructProgress) {
		this.constructProgress = constructProgress;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
