package cn.bonoon.entities.felicityVillage;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 辐射村
 * @author xiaowu
 *
 */
@Entity
public class FVPeripheralRuralEntity extends FVBaseRuralEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5016771326372799025L;

	
	/**
	 * 这一个辐射村是由那个主体村村带动起来的
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_CORERURAL_ID")
	private FVCoreRuralEntity coreRural;

	public FVPeripheralRuralEntity(){}
	public FVPeripheralRuralEntity(FVPeripheralRuralEntity pr){
		
	}
	

	public FVCoreRuralEntity getCoreRural() {
		return coreRural;
	}


	public void setCoreRural(FVCoreRuralEntity coreRural) {
		this.coreRural = coreRural;
	}
	
	
}
