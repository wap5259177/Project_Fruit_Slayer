package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.bonoon.kernel.support.entities.AbstractEntity;

@Entity
@Table(name = "t_projecttype")
public class ProjectTypeEntity extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1385336553373795717L;
	
	/**
	 * 1:正常   -1:禁止  0:草稿
	 */
	@Column(name = "C_STATUS")
	private int status;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
