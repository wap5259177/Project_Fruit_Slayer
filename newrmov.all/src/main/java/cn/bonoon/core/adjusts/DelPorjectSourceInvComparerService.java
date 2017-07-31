package cn.bonoon.core.adjusts;

import cn.bonoon.entities.logs.modelAreaManageModuleLogs.DelPorSouInvComparerEntity;
import cn.bonoon.kernel.support.services.SearchService;

public interface DelPorjectSourceInvComparerService  extends SearchService<DelPorSouInvComparerEntity>{
	public void saveDelPorjectSourceInvComparer(DelPorSouInvComparerEntity entity);
}
