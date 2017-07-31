package cn.bonoon.services.adjusts;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.adjusts.DelPorjectSourceInvComparerService;
import cn.bonoon.entities.logs.modelAreaManageModuleLogs.DelPorSouInvComparerEntity;
import cn.bonoon.kernel.support.services.AbstractSearchService;
@Service
@Transactional(readOnly = true)
public class DelPorjectSourceInvComparerServiceImpl extends AbstractSearchService<DelPorSouInvComparerEntity> implements DelPorjectSourceInvComparerService{
	
	@Override
	public void saveDelPorjectSourceInvComparer(
			DelPorSouInvComparerEntity entity) {
	entityManager.merge(entity);
		
	}
//public InvestmentInfo setInvestmentInfo(){
//		
//		
//	}

}
