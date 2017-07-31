package cn.bonoon.services.adjusts;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.adjusts.ModelAreaManageOptimizeRecordAdRuralStatus;
import cn.bonoon.entities.logs.modelAreaManageModuleLogs.AdRuralStatusComparerEntity;
import cn.bonoon.kernel.support.services.AbstractSearchService;
@Service
@Transactional(readOnly = true)
public class ModelAreaManageOptimizeRecordAdRuralStatusImpl extends AbstractSearchService<AdRuralStatusComparerEntity> implements ModelAreaManageOptimizeRecordAdRuralStatus{

	@Override
	public void saveRuralStatusComparer(AdRuralStatusComparerEntity entity) {
		entityManager.merge(entity);
		
	}

	

}
