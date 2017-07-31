package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.bonoon.kernel.support.entities.AbstractEntity;

/**
 * 项目状态变动
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_prjstatuschange")
public class ProjectStatusChangeEntity extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8984824327662331644L;
	@ManyToOne
	@JoinColumn(name = "R_PROJECT_ID")
	private ProjectEntity project;
	
	@Column(name = "C_STATUS")
	private int status;
	
	@Column(name = "C_PRESTATUS")
	private int prestatus;

	public ProjectEntity getProject() {
		return project;
	}

	public void setProject(ProjectEntity project) {
		this.project = project;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPrestatus() {
		return prestatus;
	}

	public void setPrestatus(int prestatus) {
		this.prestatus = prestatus;
	}
	
	
	
	
	
	
	
}
