package cn.bonoon.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.CityQuarterReportService;
import cn.bonoon.core.QuarterReportStatus;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.ModelAreaQuarterBatch;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.services.AbstractService;
import cn.bonoon.kernel.util.StringHelper;

@Service
@Transactional(readOnly = true)
public class CityQuarterReportServiceImpl extends
		AbstractService<ModelAreaQuarterItem> implements
		CityQuarterReportService {

	@Override
	@Transactional
	public void auditPass(OperateEvent event, Long id, String auditContent,
			String auditName, Date auditAt) {
		if (StringHelper.isEmpty(auditName)) {
			throw new RuntimeException("审核人名称不能为空!");
		}
		if (null == auditAt) {
			throw new RuntimeException("审核时间不能为空!");
		}
		ModelAreaQuarterItem maqe = __get(id);
		maqe.setStatus(QuarterReportStatus.FINISH);
		maqe.setAuditAt(auditAt);
		maqe.setAuditName(auditName);
		maqe.setAuditContent(auditContent);

		entityManager.merge(maqe);
	}

	@Override
	@Transactional
	public void auditReject(OperateEvent event, Long id, String auditContent,
			String auditName, Date auditAt) {
		if (StringHelper.isEmpty(auditName)) {
			throw new RuntimeException("审核人名称不能为空!");
		}
		if (null == auditAt) {
			throw new RuntimeException("审核时间不能为空!");
		}
		if (StringHelper.isEmpty(auditContent)) {
			throw new RuntimeException("请输入驳回原因进行说明!");
		}
		ModelAreaQuarterItem maqe = __get(id);
		maqe.setStatus(QuarterReportStatus.REJECT);
		maqe.setAuditAt(auditAt);
		maqe.setAuditName(auditName);
		maqe.setAuditContent(auditContent);
		entityManager.merge(maqe);
	}

	@Override
	public ModelAreaQuarterItem getItem(Long ownerId, int annual,
			String batchName, int period) {
		String ql1 = "select x.place.id from UnitEntity x where x.id=?";
		Long pid = __first(Long.class, ql1, ownerId);
		String ql = "select x" + " from ModelAreaQuarterItem x "
				+ "where x.modelArea.cityId=? " + "and x.batch.batchName=? "
				+ "and x.batch.quarter.period=? "
				+ "and x.batch.quarter.annual=?";//
		return __first(ModelAreaQuarterItem.class, ql, pid, batchName, period,
				annual);
	}

	@Override
	public List<ModelAreaQuarterItem> getQuarterReport(String batchName,
			int period, int annual) {

		String ql = "select x from ModelAreaQuarterItem x where x.batch.batchName=? and x.batch.quarter.period=? and x.batch.quarter.annual=? order by x.modelArea.ordinalModel asc";//
		// List<ModelAreaQuarterItem>
		// obj=__list(Object[].class,ql,batchName,period,annual);
		return __list(ModelAreaQuarterItem.class, ql, batchName, period, annual);// ,batch,period
	}

	@Override
	public ModelAreaQuarterItem getItem(String batchName, int annual, int period) {
		String ql = "select x" + " from ModelAreaQuarterItem x "
				+ "where x.modelArea.cityId=? " + "and x.batch.batchName=? "
				+ "and x.batch.quarter.period=? "
				+ "and x.batch.quarter.annual=?";//
		return __first(ModelAreaQuarterItem.class, ql, batchName, annual,
				period);
	}

	@Override
	public ModelAreaQuarterBatch getBatch(String batchName, int annual,
			int period) {
		String ql = "select x " + "from ModelAreaQuarterBatch x "
				+ "where x.batchName=? " + "and x.quarter.period=? "
				+ "and x.quarter.annual=?";
		return __first(ModelAreaQuarterBatch.class, ql, batchName, period,
				annual);
	}

	public void updateItem4ParamToLast(List<ModelAreaQuarterItem> items) {
		for (ModelAreaQuarterItem item : items) {
			if (item.getArCount() == 0 && item.getNrCount() == 0
					&& item.getHouseholdCount() == 0
					&& item.getPopulationCount() == 0) {
				List<ModelAreaQuarterItem> lastItem = getItems(item
						.getModelArea().getId(), item.getBatch().getQuarter()
						.getPeriod(), item.getBatch().getQuarter().getAnnual());
				if (lastItem != null && lastItem.size() > 1) {
					ModelAreaQuarterItem first = lastItem.get(0);
					if (first.getId() == item.getId()) {
						System.out.println(true);
					}

					for (ModelAreaQuarterItem maqi : lastItem) {
						System.out.println(maqi.getBatch().getQuarter()
								.getAnnual()
								+ "年"
								+ maqi.getBatch().getQuarter().getPeriod());
						if (maqi.getStatus() == 0)
							continue;
						if (maqi.getArCount() != 0 || maqi.getNrCount() != 0
								|| maqi.getHouseholdCount() != 0
								|| maqi.getPopulationCount() != 0) {
							System.out
									.println("--------------------------------");

							System.out.println(item.getModelArea().getName());
							System.out.println(item.getArCount());
							System.out.println(item.getNrCount());
							System.out.println(item.getHouseholdCount());
							System.out.println(item.getPopulationCount());
							System.out.println("-----");

							item.setArCount(maqi.getArCount());
							item.setNrCount(maqi.getNrCount());
							item.setHouseholdCount(maqi.getHouseholdCount());
							item.setPopulationCount(maqi.getPopulationCount());
							entityManager.flush();
							entityManager.merge(item);

							System.out.println(item.getArCount());
							System.out.println(item.getNrCount());
							System.out.println(item.getHouseholdCount());
							System.out.println(item.getPopulationCount());
							System.out
									.println("--------------------------------");
							break;
						}
					}
				}
			}
		}
	}

	public ModelAreaQuarterItem getItem(String batchName, int annual,
			int period, Long modelAreaId) {
		String ql = "select x" + " from ModelAreaQuarterItem x "
				+ "where x.modelArea.id=? " + "x.batch.batchName=? "
				+ "and x.batch.quarter.period=? "
				+ "and x.batch.quarter.annual=?";//
		return __first(ModelAreaQuarterItem.class, ql, modelAreaId, batchName,
				period, annual);
	}

	public List<ModelAreaQuarterItem> getItems(Long modelAreaId, int period,
			int annual) {
		String ql = "select x from ModelAreaQuarterItem x where x.disabled=false and x.modelArea.id=? and  x.batch.quarter.annual*10+x.batch.quarter.period<=?  order by x.batch.quarter.annual*10+x.batch.quarter.period desc";//

		return __list(ModelAreaQuarterItem.class, ql, modelAreaId, annual * 10
				+ period);
	}

}
