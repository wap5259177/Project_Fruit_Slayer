package cn.bonoon.core;

import cn.bonoon.entities.ShareUserGroupEntity;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.services.GenericService;

public interface UserGroupService extends GenericService<ShareUserGroupEntity>{

	void saveActions(LogonUser user, Long id, String actions);

}
