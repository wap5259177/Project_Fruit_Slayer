package cn.bonoon.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.UnitService;
import cn.bonoon.entities.UnitEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class UnitServiceImpl extends AbstractService<UnitEntity> implements UnitService {

	@Override
	public PlaceEntity getPlace(LogonUser user) {
		String ql = "select u.place from UnitEntity u where u.id=?";
		return __first(PlaceEntity.class, ql,user.getOwnerId());
	}

}
