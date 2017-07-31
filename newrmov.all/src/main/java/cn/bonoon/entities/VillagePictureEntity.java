package cn.bonoon.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_villagepicture")
public class VillagePictureEntity extends PictureEntity<VillageApplicantEntity>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4615727855618936974L;

}
