package cn.bonoon.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.RuralUnitService;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.RuralUnitEntity;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class RuralUnitServiceImpl extends AbstractService<RuralUnitEntity> implements RuralUnitService {

	@Override
	@Transactional
	public void units(Long newId, String[] arr_un, String[] arr_ra, String[] arr_cn, String[] arr_up) {
		for(int i=0; i<arr_un.length; i++) {
			//设置关联外键
			NewRuralEntity nre = __first(NewRuralEntity.class, "from NewRuralEntity where id=?", newId);
			RuralUnitEntity rue = new RuralUnitEntity();
			rue.setNewRural(nre);
			rue.setUnitName(arr_un[i]);
			rue.setRegisteredAddress(arr_ra[i]);
			rue.setContactName(arr_cn[i]);
			rue.setUnitPhone(arr_up[i]);
			entityManager.merge(rue);
		}
	}

	@Override
	@Transactional
	public void unitsUpdate(Long newId, String[] arr_unid, String[] arr_un,
			String[] arr_ra, String[] arr_cn, String[] arr_up) {
//		List<RuralUnitEntity> wgs = __list(RuralUnitEntity.class,
//				"from RuralUnitEntity where newRural.id=? order by id", newId);
		for (int i = 0; i < arr_unid.length; i++) {
			RuralUnitEntity rwge = __get(Long.parseLong(arr_unid[i]));
			rwge.setUnitName(arr_un[i]);
			rwge.setRegisteredAddress(arr_ra[i]);
			rwge.setContactName(arr_cn[i]);
			rwge.setUnitPhone(arr_up[i]);
			entityManager.merge(rwge);
		}
		if(arr_un.length > arr_unid.length) {
			NewRuralEntity nre = __first(NewRuralEntity.class, "from NewRuralEntity where id=?", newId);
			for(int i=arr_unid.length; i<arr_un.length; i++) {
				RuralUnitEntity wg = new RuralUnitEntity();
				wg.setNewRural(nre);
				wg.setUnitName(arr_un[i]);
				wg.setRegisteredAddress(arr_ra[i]);
				wg.setContactName(arr_cn[i]);
				wg.setUnitPhone(arr_up[i]);
				entityManager.persist(wg);
			}
		}
	}
}
