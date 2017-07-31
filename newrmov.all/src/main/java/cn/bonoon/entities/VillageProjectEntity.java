package cn.bonoon.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_villageproject")
public class VillageProjectEntity extends AbstractProjectEntity<VillageApplicantEntity>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5007050056436440485L;

}
