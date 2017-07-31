package cn.bonoon.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.RequireReportApplyService;
import cn.bonoon.entities.RequireReportApplyEntity;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class RequireReportApplyServiceImpl extends AbstractService<RequireReportApplyEntity> implements RequireReportApplyService {
	@Override
	protected RequireReportApplyEntity __save(OperateEvent event, RequireReportApplyEntity entity) {
		if ("true".equalsIgnoreCase(event.getString("applicant-submit"))) {
			entity.setStatus(1);
		}
		return super.__save(event, entity);
	}
	
	@Override
	protected RequireReportApplyEntity __update(OperateEvent event, RequireReportApplyEntity entity) {
		if ("true".equalsIgnoreCase(event.getString("applicant-submit"))) {
			entity.setStatus(1);
		}
		return super.__update(event, entity);
	}
}
