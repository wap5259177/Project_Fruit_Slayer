package cn.bonoon.core;

import cn.bonoon.entities.RuralWorkGroupEntity;
import cn.bonoon.kernel.support.services.GenericService;

public interface RuralWorkGroupService extends GenericService<RuralWorkGroupEntity> {

	void workGroups(Long newId, String[] arr_wn, String[] arr_uj, String[] arr_wp, String[] arr_wr);
	void workGroupsUpdate(Long newId, String[] arr_wgid, String[] arr_wn, String[] arr_uj, String[] arr_wp, String[] arr_wr);
}
