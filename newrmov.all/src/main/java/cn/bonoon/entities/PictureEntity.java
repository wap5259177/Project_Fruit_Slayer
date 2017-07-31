package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import cn.bonoon.kernel.support.entities.AbstractPersistable;

@MappedSuperclass
public abstract class PictureEntity<E extends ApplicantEntity> extends AbstractPersistable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5931090070427844286L;

	@ManyToOne
	@JoinColumn(name = "R_APPLICANT_ID")
	private E applicant;

	@Column(name = "C_PATH")
	private String path;

	@Column(name = "C_NAME")
	private String name;

	public E getApplicant() {
		return applicant;
	}

	public void setApplicant(E applicant) {
		this.applicant = applicant;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
