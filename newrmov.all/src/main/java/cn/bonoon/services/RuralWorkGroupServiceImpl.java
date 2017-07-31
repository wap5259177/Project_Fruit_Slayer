package cn.bonoon.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.RuralWorkGroupService;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.RuralWorkGroupEntity;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class RuralWorkGroupServiceImpl extends AbstractService<RuralWorkGroupEntity> implements RuralWorkGroupService {
	
	@Override
	@Transactional
	public void workGroups(Long newId, String[] arr_wn, String[] arr_uj, String[] arr_wp, String[] arr_wr) {
		for(int i=0; i<arr_wn.length; i++) {
			//设置关联外键
			NewRuralEntity nre = __first(NewRuralEntity.class, "from NewRuralEntity where id=?", newId);
			RuralWorkGroupEntity wg = new RuralWorkGroupEntity();
			wg.setNewRural(nre);
			wg.setWorkName(arr_wn[i]);
			wg.setUnitJob(arr_uj[i]);
			wg.setWorkPhone(arr_wp[i]);
			wg.setWorkRemark(arr_wr[i]);
			entityManager.merge(wg);
		}
	}

	@Override
	@Transactional
	public void workGroupsUpdate(Long newId, String[] arr_wgid, String[] arr_wn, String[] arr_uj,
			String[] arr_wp, String[] arr_wr) {
//		List<RuralWorkGroupEntity> wgs = __list(RuralWorkGroupEntity.class,
//				"from RuralWorkGroupEntity where newRural.id=? order by id", newId);
		for (int i = 0; i < arr_wgid.length; i++) {
			RuralWorkGroupEntity rwge = __get(Long.parseLong(arr_wgid[i]));
			rwge.setWorkName(arr_wn[i]);
			rwge.setUnitJob(arr_uj[i]);
			rwge.setWorkPhone(arr_wp[i]);
			rwge.setWorkRemark(arr_wr[i]);
			entityManager.merge(rwge);
		}
		if(arr_wn.length > arr_wgid.length) {
			NewRuralEntity nre = __first(NewRuralEntity.class, "from NewRuralEntity where id=?", newId);
			for(int i=arr_wgid.length; i<arr_wn.length; i++) {
				RuralWorkGroupEntity wg = new RuralWorkGroupEntity();
				wg.setNewRural(nre);
				wg.setWorkName(arr_wn[i]);
				wg.setUnitJob(arr_uj[i]);
				wg.setWorkPhone(arr_wp[i]);
				wg.setWorkRemark(arr_wr[i]);
				entityManager.persist(wg);
			}
		}
	}
}

