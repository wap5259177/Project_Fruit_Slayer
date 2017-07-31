package cn.bonoon.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.FVCoreRuralService;
import cn.bonoon.entities.felicityVillage.FVCoreRuralEntity;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class FVCoreRuralServiceImpl extends AbstractService<FVCoreRuralEntity> implements FVCoreRuralService{

	@Override
	public List<FVCoreRuralEntity> allCoreRural(Long id) {
		String ql = "select x from FVCoreRuralEntity x where x.deleted=false and x.modelArea.id=?";
		return __list(FVCoreRuralEntity.class, ql,id);
	}

	
	

}
