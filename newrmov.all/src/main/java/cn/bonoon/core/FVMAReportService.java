package cn.bonoon.core;

import java.util.List;

import cn.bonoon.core.felicity.RuralInfo;
import cn.bonoon.entities.felicityVillage.FVModelAreaReportEntity;
import cn.bonoon.kernel.support.services.GenericService;

public interface FVMAReportService extends GenericService<FVModelAreaReportEntity>{

	List<RuralInfo> checkRuralMakeList(Long id);



}
