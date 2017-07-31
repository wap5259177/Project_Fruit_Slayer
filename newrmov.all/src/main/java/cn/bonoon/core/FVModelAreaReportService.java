package cn.bonoon.core;

import java.util.List;

import cn.bonoon.entities.felicityVillage.FVModelAreaReportEntity;
import cn.bonoon.kernel.support.services.GenericService;

public interface FVModelAreaReportService extends GenericService<FVModelAreaReportEntity>{

	List<FVModelAreaReportEntity> allMasByCounty(Long id);



}
