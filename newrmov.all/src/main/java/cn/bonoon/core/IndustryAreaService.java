package cn.bonoon.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import cn.bonoon.entities.IndustryAreaEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.GenericService;

public interface IndustryAreaService extends GenericService<IndustryAreaEntity>, ApplicantStatus {

	List<PlaceEntity> getTowns(Long oid);

	Map<Long, String> rurals(Long oid);

	/**
	 * x.id,x.name,x.modelArea.name,x.naturalVillage
	 * @param opt
	 * @return
	 */
	List<Object[]> rurals(IOperator opt);
	/**
	 * x.id,x.name,x.modelArea.name,x.naturalVillage
	 * @param opt
	 * @return
	 */
	List<Object[]> rurals2(IOperator opt);

	Collection<Object[]> statistics(String batch);
	Collection<Object[]> statisticsLocal(IOperator opt, String batch);
	Collection<Object[]> statisticsCity(IOperator opt, String batch);

	boolean check(IOperator opt);
}
