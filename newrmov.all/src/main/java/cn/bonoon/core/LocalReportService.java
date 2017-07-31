package cn.bonoon.core;

import cn.bonoon.entities.felicityVillage.FVMACountyReportEntity;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.SearchService;

public interface LocalReportService extends SearchService<FVMACountyReportEntity>{

	void toReport(IOperator opt, Long id);

}
