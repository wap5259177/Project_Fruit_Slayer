package cn.bonoon.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.FVModelAreaReportService;
import cn.bonoon.entities.felicityVillage.FVModelAreaReportEntity;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class FVModelAreaReportServiceImpl extends AbstractService<FVModelAreaReportEntity> implements FVModelAreaReportService{

	@Override
	public List<FVModelAreaReportEntity> allMasByCounty(Long id) {
		String ql = "select x from FVModelAreaReportEntity x where x.countyReport.id=? and x.deleted=false";
		return __list(FVModelAreaReportEntity.class, ql, id);
	}


}
