package cn.bonoon.core;

import java.util.List;

import cn.bonoon.entities.AdministrationUncoreRuralEntity;
import cn.bonoon.entities.PeripheralRuralEntity;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.GenericService;

public interface AdministrationUncoreRuralService extends GenericService<AdministrationUncoreRuralEntity>{

	AdministrationUncoreRuralEntity getAdministrationByAdminRuralId(Long id);
	
	List<String> getNaturalVillage(Long adminId);
	
	 boolean check(Long id, IOperator opt);

	 //非主体行政村下的自然村
	List<PeripheralRuralEntity> getRuralByAdminRuralId(Long id);
}
