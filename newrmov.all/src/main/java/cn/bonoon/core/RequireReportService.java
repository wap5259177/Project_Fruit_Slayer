package cn.bonoon.core;

import java.util.List;

import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.RequireReportDocumentEntity;
import cn.bonoon.entities.RequireReportEntity;
import cn.bonoon.entities.RequireReportItemEntity;
import cn.bonoon.entities.UnitEntity;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.GenericService;

public interface RequireReportService extends GenericService<RequireReportEntity> {

	String ROOT_PATH = "require/report/r_";
	
	
	List<UnitEntity> findCity();
	List<UnitEntity> findCounty();
	List<RequireReportItemEntity> findItem(Long id);
	List<Long> findItemPlaceIds(Long id);

	/*
	 * 通过时间类型,统计类型,年份,月份,季度查询要求上报的记录
	 */
	List<Object[]> statistics_year(Long time_type, int year);
	List<Object[]> statistics_season(Long time_type, int year, int season);
	List<Object[]> statistics_month(Long time_type, int year, int month);
	
	void issue(IOperator opt, Long id);
	void finish(Long id);
	List<FileEntity> itemFiles(Long iid);
	void urge(Long[] ids);
	String getName(Long id);
	List<RequireReportDocumentEntity> getDocuments(Long id, Long[] fids);
	void toback(Long id);
	

}
