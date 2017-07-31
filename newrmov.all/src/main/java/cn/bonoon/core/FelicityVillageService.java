package cn.bonoon.core;

import java.util.List;

import cn.bonoon.entities.FelicityVillageEntity;
import cn.bonoon.kernel.support.services.GenericService;

public interface FelicityVillageService extends GenericService<FelicityVillageEntity>{
	List<Object[]> getVillages(Long id);
}
