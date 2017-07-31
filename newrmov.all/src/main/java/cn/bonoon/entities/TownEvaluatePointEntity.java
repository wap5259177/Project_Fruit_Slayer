package cn.bonoon.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_townevaluate")
public class TownEvaluatePointEntity extends EvaluatePointEntity<TownApplicantEntity>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2261606054788493443L;

}
