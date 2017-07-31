package cn.bonoon.core;

import java.util.List;

import cn.bonoon.entities.FelicityCountyEntity;
import cn.bonoon.entities.UnitEntity;
import cn.bonoon.kernel.support.services.GenericService;

public interface FelicityCountyService extends GenericService<FelicityCountyEntity>{

	List<String> summary(Long oid);

	UnitEntity getUnit(Long uid);

	FelicityCountyEntity getByOwner(Long uid);

	List<Object[]> getVillages(Long id);

}
