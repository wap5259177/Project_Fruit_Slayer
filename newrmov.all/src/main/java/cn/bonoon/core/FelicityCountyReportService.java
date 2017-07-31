package cn.bonoon.core;

import java.util.Date;
import java.util.List;

import cn.bonoon.entities.FelicityCountyEntity;
import cn.bonoon.entities.FelicityCountyReportEntity;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.GenericService;

public interface FelicityCountyReportService extends GenericService<FelicityCountyReportEntity>{

	void auditPass(OperateEvent event, Long id,String auditContent,String auditName,Date auditAt);
	void auditReject(OperateEvent event, Long id, String auditContent,String auditName,Date auditAt);
	
	List<FelicityCountyReportEntity> reports(IOperator user, int selectedYear);

	FelicityCountyReportEntity report(IOperator user, Long id);

	FelicityCountyReportEntity start(IOperator user, int year, int month);

	List<FelicityCountyReportEntity> statistics(int startAt, int endAt, String inids);

	List<FelicityCountyEntity> byCity(IOperator user);

	List<FelicityCountyEntity> byCounty(IOperator user);

	List<FelicityCountyEntity> byProvince(IOperator user);
	
	//FelicityCountyReportEntity __parseProjectReport(IOperator user, FelicityCountyReportEntity pre);
	
	void save(Long id, ParamenterValue pv, boolean submit); 
}
