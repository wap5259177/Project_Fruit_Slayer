package cn.bonoon.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.UserGroupService;
import cn.bonoon.entities.ShareUserGroupEntity;
import cn.bonoon.entities.plugins.AccountEntity;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class UserGroupServiceImpl extends AbstractService<ShareUserGroupEntity> implements UserGroupService{
	
	@Override
	protected ShareUserGroupEntity __save(OperateEvent event, ShareUserGroupEntity entity) {
//		UnitEntity unit = entityManager.find(UnitEntity.class, entity.getOwnerId());
		
		return super.__save(event, entity);
	}

	@Override
	@Transactional
	public void saveActions(LogonUser user, Long id, String actions) {
		String[] ids = actions.split(",");
		ShareUserGroupEntity sug = __get(id);
		
//		System.out.println(ids);
		sug.getAccounts().clear();
//		String ql = "select x AccountEntity x where x.id=?";
		for(String i : ids){
			AccountEntity ae = null;
			if(!"".equals(i)){
				ae = entityManager.find(AccountEntity.class, Long.valueOf(i));
				 sug = __get(id);
			}
			if(null != ae){
				sug.getAccounts().add(ae);
			}
		}
//		System.out.println(sug);
		entityManager.merge(sug);
	}

}
