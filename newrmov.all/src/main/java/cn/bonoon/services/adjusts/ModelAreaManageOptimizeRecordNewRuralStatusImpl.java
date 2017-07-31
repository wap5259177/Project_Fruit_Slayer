package cn.bonoon.services.adjusts;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.adjusts.ModelAreaManageOptimizeRecordNewRuralStatus;
import cn.bonoon.entities.logs.modelAreaManageModuleLogs.NewRuralStatusComparerEntity;
import cn.bonoon.kernel.events.ReadEvent;
import cn.bonoon.kernel.query.Pageable;
import cn.bonoon.kernel.support.models.Page;
import cn.bonoon.kernel.support.searcher.Finder;
import cn.bonoon.kernel.support.searcher.ISearcher;
import cn.bonoon.kernel.support.services.AbstractSearchService;
@Service
@Transactional(readOnly = true)
public class ModelAreaManageOptimizeRecordNewRuralStatusImpl extends AbstractSearchService<NewRuralStatusComparerEntity> implements ModelAreaManageOptimizeRecordNewRuralStatus{

	@Override
	public void saveRuralStatusComparer(NewRuralStatusComparerEntity entity) {
		entityManager.merge(entity);
	}

	

}
