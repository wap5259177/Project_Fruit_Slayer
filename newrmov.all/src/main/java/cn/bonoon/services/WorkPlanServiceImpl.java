package cn.bonoon.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.controllers.SaveMultipartFile;
import cn.bonoon.core.WorkPlanService;
import cn.bonoon.entities.UnitEntity;
import cn.bonoon.entities.WorkPlanEntity;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class WorkPlanServiceImpl extends AbstractService<WorkPlanEntity> implements WorkPlanService {
	@Override
	protected WorkPlanEntity __save(OperateEvent event, WorkPlanEntity entity) {
		UnitEntity unit = entityManager.find(UnitEntity.class, entity.getOwnerId());
		entity.setUnitName(unit.getName());
		entity.setPlace(unit.getPlace());
		
		
		
		String newFileName = SaveMultipartFile.saveFile(event);
		if(newFileName != null){
			entity.setEnclosure(newFileName);
		}
		return super.__save(event, entity);
	}
	
	@Override
	protected WorkPlanEntity __update(OperateEvent event, WorkPlanEntity entity) {
		String newFileName = SaveMultipartFile.saveFile(event);
		if(newFileName != null){
			entity.setEnclosure(newFileName);
		}
		return super.__update(event, entity);
	}
}
