package cn.bonoon.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_villageevaluate")
public class VillageEvaluatePointEntity extends EvaluatePointEntity<VillageApplicantEntity>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8411912988205464516L;

}
