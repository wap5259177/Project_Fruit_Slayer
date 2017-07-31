package cn.bonoon.core;

import cn.bonoon.entities.RuralUnitEntity;
import cn.bonoon.kernel.support.services.GenericService;

public interface RuralUnitService extends GenericService<RuralUnitEntity> {

	void units(Long newId, String[] arr_un, String[] arr_ra, String[] arr_cn, String[] arr_up);
	void unitsUpdate(Long newId, String[] arr_unid, String[] arr_un, String[] arr_ra, String[] arr_cn, String[] arr_up);
}
