package cn.bonoon.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.FelicityVillageReportService;
import cn.bonoon.entities.FelicityCountyReportEntity;
import cn.bonoon.entities.FelicityVillageReportEntity;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.services.AbstractService;

/**
 * 幸福村居
 * @author jackson
 *
 */
@Service
@Transactional(readOnly = true)
public class FelicityVillageReportServiceImpl extends AbstractService<FelicityVillageReportEntity> implements FelicityVillageReportService {

	@Override
	protected FelicityVillageReportEntity __update(OperateEvent event, FelicityVillageReportEntity entity) {
		if(entity.isBiddingCompleted()){
			//已经完成的进度调整为100%
			entity.setBiddingProgress(100);
		}else{
			double bp = entity.getBiddingProgress();
			if(bp < 0 || bp > 100){
				throw new RuntimeException("完成招投标比例的值应该在0%~100%之间!");
			}
		}
		if(entity.isPlanningCompleted()){
			entity.setPlanningProgress(100);
		}else{
			double pp = entity.getPlanningProgress();
			if(pp < 0 || pp > 100){
				throw new RuntimeException("规划进度的值应该在0%~100%之间!");
			}
		}
		double pr = entity.getProjectProgress();
		if(pr < 0 || pr > 100){
			throw new RuntimeException("项目建设进度的值应该在0%~100%之间!");
		}
		super.__update(event, entity);
		FelicityCountyReportEntity report = entity.getCountyReport();
		report.clear();
		for(FelicityVillageReportEntity fvr : report.getItems()){
			report.sum(fvr);
		}
		entityManager.merge(report);
		return entity;
	}
}
