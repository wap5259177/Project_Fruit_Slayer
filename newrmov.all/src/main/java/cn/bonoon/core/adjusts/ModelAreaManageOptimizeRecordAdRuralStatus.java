package cn.bonoon.core.adjusts;

import cn.bonoon.entities.logs.modelAreaManageModuleLogs.AdRuralStatusComparerEntity;
import cn.bonoon.kernel.support.services.SearchService;


public interface ModelAreaManageOptimizeRecordAdRuralStatus  extends SearchService<AdRuralStatusComparerEntity>{
public void saveRuralStatusComparer(AdRuralStatusComparerEntity entity);
}
