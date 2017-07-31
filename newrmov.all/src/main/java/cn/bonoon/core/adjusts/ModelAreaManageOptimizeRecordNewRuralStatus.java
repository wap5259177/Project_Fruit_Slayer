package cn.bonoon.core.adjusts;

import cn.bonoon.entities.logs.modelAreaManageModuleLogs.NewRuralStatusComparerEntity;
import cn.bonoon.kernel.support.services.SearchService;

public interface ModelAreaManageOptimizeRecordNewRuralStatus extends SearchService<NewRuralStatusComparerEntity>{
	public void saveRuralStatusComparer(NewRuralStatusComparerEntity entity);
	}
