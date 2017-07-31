package cn.bonoon.core;

import java.util.List;

import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.entities.ModelAreaQuarterNaturalRural;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.RuralNeedFinishInfo;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.SearchService;

public interface LocalQuarterReportService extends
		SearchService<ModelAreaQuarterItem> {
	/**
	 * <strong> 市级别季度的上报服务接口 使用说明:每次点击开始上报按钮都获取当时自然村{@link NewRuralEntity}
	 * 的RuralNeedFinishInfo needFinish字段 作为本季度{@link ModelAreaQuarterItem}的9项指标
	 * </strong>
	 * 
	 * @param opt
	 * @param id
	 */
	void toReport(IOperator opt, Long id);

	void refreshRural(LogonUser user, Long id);

	ModelAreaQuarterNaturalRural getQnr(Long id);

	void update(ModelAreaQuarterNaturalRural qnr);

	void updateItem(ModelAreaQuarterItem item);

	void toFinish(Long id);

	// List<ModelAreaQuarterItem>getMaqitems(int count,Long id);
	/**
	 * 根据modelArea片区的ownerId 年，季度获取一个已经完成填报的市级别的建设工程进展季度报表
	 * 
	 * @param ownerId
	 * @param annual
	 * @param period
	 * @return
	 */
	ModelAreaQuarterItem getMaqitem(Long ownerId, int annual, int period);

	/**
	 * 同步资金功能
	 * 
	 * @param id
	 */
	void synchronousMoney(Long id);

	// 以下两个是给首页使用的
	List<ModelAreaQuarterItem> getUrges(IOperator user);

	List<ModelAreaQuarterItem> getNeedReport(IOperator user);

	//
	/**
	 * 根据modelArea片区的ownerId 年，季度获取一个任意状态的市级别的建设工程进展季度报表
	 * 
	 * @param ownerId
	 * @param annual
	 * @param period
	 * @return
	 */
	ModelAreaQuarterItem getOneMaqitem(Long modelAreaID, int annual, int period);

	/**
	 * 本方法主要是重新统计：季度表9项指标以及行政村有编制规划设计村数（个）</br> 本季度{@link ModelAreaQuarterItem}
	 * 9项指标{@link RuralNeedFinishInfo}以及行政村有编制规划设计村数（个）统计方式为：</br>
	 * 获取本季度上报时的所有季度自然村的RuralNeedFinishInfo以及每个行政村的行政村有编制规划设计村数（个）对应的数值的对应累加
	 * 
	 */
	String updateQuarter9Parm(Long id) throws Exception;

	/**
	 * 对9项指标,行政村有编制规划设计村数（个）统计给季报
	 */
	void set9Param(ModelAreaQuarterItem item);

	/**
	 * 本季度之前本片区所有工程进展季报（不包含本季度季报）
	 * 
	 */
	List<ModelAreaQuarterItem> beforeQuarter(int nowAnnual, int nowPeriod,
			Long maId) throws Exception;

	/**
	 * 刷新工程进展季度表的资金
	 * 
	 * @param item
	 */
	void __synchronousMoney(ModelAreaQuarterItem item);
	
	/**
	 * bug
	 * 
	 * 
	 * 对未开始上报的季报不管现在的指标的值是什么，9項指標及其他三項指標统统赋值为上季度不是未开始的季报的指标
	 * 
	 * @param items
	 * @throws Exception
	 */
	void show9ParamByNoStart(List<ModelAreaQuarterItem> items);
}
