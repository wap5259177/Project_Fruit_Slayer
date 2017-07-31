package cn.bonoon.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_townpicture")
public class TownPictureEntity extends PictureEntity<TownApplicantEntity>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3016684593053607309L;

}
