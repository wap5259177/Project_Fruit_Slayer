package cn.bonoon.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.LocalReportService;
import cn.bonoon.core.QuarterReportStatus;
import cn.bonoon.entities.felicityVillage.FVMACountyReportEntity;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.AbstractSearchService;

@Service
@Transactional(readOnly = true)
public class LocalReportServiceImpl extends AbstractSearchService<FVMACountyReportEntity>implements LocalReportService{
	/*
	 * 开始上报
	 */
	@Override
	@Transactional
	public void toReport(IOperator opt, Long id) {
		FVMACountyReportEntity entity = __get(id);
		entity.setStatus(QuarterReportStatus.EDIT);
		entityManager.merge(entity);
		
	}

}
