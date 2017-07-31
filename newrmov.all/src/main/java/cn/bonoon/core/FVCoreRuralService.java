package cn.bonoon.core;

import java.util.List;

import cn.bonoon.entities.felicityVillage.FVCoreRuralEntity;
import cn.bonoon.kernel.support.services.GenericService;

public interface FVCoreRuralService extends GenericService<FVCoreRuralEntity>{
	List<FVCoreRuralEntity> allCoreRural(Long id);

}
