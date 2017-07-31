package cn.bonoon.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.FelicityVillageService;
import cn.bonoon.entities.FelicityVillageEntity;
import cn.bonoon.kernel.support.services.AbstractService;

/**
 * 幸福村居
 * @author jackson
 *
 */
@Service
@Transactional(readOnly = true)
public class FelicityVillageServiceImpl extends AbstractService<FelicityVillageEntity> implements FelicityVillageService{
	@Override
	public List<Object[]> getVillages(Long id) {
		String ql = "select x.id,x.townName,x.name,x.naturalVillage,x.villageType,x.constructionType from FelicityVillageEntity x where x.county.id=?";
		return __list(Object[].class, ql, id);
	}
}
