package cn.bonoon.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_townproject")
public class TownProjectEntity extends AbstractProjectEntity<TownApplicantEntity>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4578696913691137398L;

}
