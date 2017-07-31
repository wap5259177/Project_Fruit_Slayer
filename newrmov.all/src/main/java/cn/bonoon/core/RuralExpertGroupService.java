package cn.bonoon.core;

import cn.bonoon.entities.RuralExpertGroupEntity;
import cn.bonoon.kernel.support.services.GenericService;

public interface RuralExpertGroupService extends GenericService<RuralExpertGroupEntity> {

	void expertGroups(Long newId, String[] arr_en, String[] arr_ej, String[] arr_ep, String[] arr_er);
	void expertGroupsUpdate(Long newId, String[] arr_egid, String[] arr_en, String[] arr_ej, String[] arr_ep, String[] arr_er);
}
