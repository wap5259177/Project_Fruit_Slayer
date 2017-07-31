package cn.bonoon.core;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bonoon.core.felicity.AreaReportInfo;
import cn.bonoon.entities.felicityVillage.FVMACountyReportEntity;
import cn.bonoon.entities.felicityVillage.FVModelAreaReportEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.GenericService;

public interface FVCountyReportService extends GenericService<FVMACountyReportEntity>{


	/**
	 * 县下面所有的report,4条
	 */
	List<FVMACountyReportEntity> allCountyReport( LogonUser user);

	/**
	 * 创建这个县报下他的片区(应该要判断什么时候创建片区报,什么时候创建片区字典)
	 * @param user
	 * @param areas
	 */
	void saveModelArea(LogonUser user, List<FVModelAreaReportEntity> areas,Long id);

	/**
	 * 为了 实现表1 的层次关系,检查是否有辐射村,然后多生成出FVModelAreaReportEntity 记录,一行里面其他的字段设置为空,只要主体村名,辐射村名,和他们的类型
	 * @param id
	 * @return
	 */
	List<AreaReportInfo> checkRuralToAddMa(Long id);

	/**
	 * 修改片区
	 * @param id
	 * @param fvma
	 */
	void UpdateModelArea(Long id,String modelAreaName,Double constructionArea,Integer  households, Integer population);

	/**
	 * 保存县报的那三个填报字段:填报人,联系电话,填报时间
	 * @param reporter
	 * @param telephone
	 * @param reportTime
	 */
	void updateReportInfo(Long id,String reporter, String telephone, Date reportTime);

	/**
	 * 通过user 获取place
	 * @param user
	 * @return
	 */
	PlaceEntity getPlace(LogonUser user);
	
	/**
	 * 保存表三
	 * @param user
	 * @param cReport
	 * @param mas
	 */
	void update(IOperator user, FVMACountyReportEntity cReport, List<FVModelAreaReportEntity> mas);

	void updateReportInfo2(Long couId, String reporter2, String telephone2,
			Date reportTime2);

	/**
	 * 提交县报
	 * @param id
	 */
	void submitReport(Long id);

	/**
	 * 导出table
	 * @param id
	 */
	void exportTable1(HttpServletRequest request,HttpServletResponse response,Long id);
	void exportTable2(HttpServletRequest request,HttpServletResponse response,Long id);
	void exportTable3(HttpServletRequest request,HttpServletResponse response,Long id);

	
	
	//-------------------------------------------------------------------------------
	/**
	 * 
	 * @return
	 */
	List<FVMACountyReportEntity> allCountyReportByCityPid(LogonUser user);

	/**
	 * 取上一次县报的id
	 * @param copyId
	 * @param copyId2 
	 */
	void copy(Long id, Long copyid,LogonUser user);

	Long lastReport(Long id, LogonUser user);

	List<FVMACountyReportEntity> allCountyReportByReport(Long id);

	int checkWords(Long id);
}
