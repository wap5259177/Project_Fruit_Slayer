package cn.bonoon.core;

import cn.bonoon.entities.UnitEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.services.GenericService;

public interface UnitService extends GenericService<UnitEntity> {

	/**
	 * 通过登录用户获取他所在的place
	 * @param user
	 * @return
	 */
	PlaceEntity getPlace(LogonUser user);

}
