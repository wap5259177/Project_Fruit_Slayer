package cn.bonoon.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.RuralExpertGroupService;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.RuralExpertGroupEntity;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class RuralExpertGroupServiceImpl extends AbstractService<RuralExpertGroupEntity> implements RuralExpertGroupService {

	@Override
	@Transactional
	public void expertGroups(Long newId, String[] arr_en, String[] arr_ej, String[] arr_ep, String[] arr_er) {
		NewRuralEntity nre = __first(NewRuralEntity.class, "from NewRuralEntity where id=?", newId);
		for(int i=0; i<arr_en.length; i++) {
			//设置关联外键
			RuralExpertGroupEntity reg = new RuralExpertGroupEntity();
			reg.setNewRural(nre);
			reg.setExpertName(arr_en[i]);
			reg.setExpertJob(arr_ej[i]);
			reg.setExpertPhone(arr_ep[i]);
			reg.setExpertRemark(arr_er[i]);
			entityManager.merge(reg);
		}

	}

	@Override
	@Transactional
	public void expertGroupsUpdate(Long newId, String[] arr_egid,
			String[] arr_en, String[] arr_ej, String[] arr_ep, String[] arr_er) {
//		List<RuralExpertGroupEntity> wgs = __list(RuralExpertGroupEntity.class,
//				"from RuralExpertGroupEntity where newRural.id=? order by id", newId);
		for (int i = 0; i < arr_egid.length; i++) {
			RuralExpertGroupEntity rwge = __get(Long.parseLong(arr_egid[i]));
			rwge.setExpertName(arr_en[i]);
			rwge.setExpertJob(arr_ej[i]);
			rwge.setExpertPhone(arr_ep[i]);
			rwge.setExpertRemark(arr_er[i]);
			entityManager.merge(rwge);
		}
		if(arr_en.length > arr_egid.length) {
			NewRuralEntity nre = entityManager.find(NewRuralEntity.class, newId);
			for(int i=arr_egid.length; i<arr_en.length; i++) {
				RuralExpertGroupEntity wg = new RuralExpertGroupEntity();
				wg.setNewRural(nre);
				wg.setExpertName(arr_en[i]);
				wg.setExpertJob(arr_ej[i]);
				wg.setExpertPhone(arr_ep[i]);
				wg.setExpertRemark(arr_er[i]);
				entityManager.persist(wg);
			}
		}
	}
}
