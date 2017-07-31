package cn.bonoon.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.BatchInvestService;
import cn.bonoon.entities.informatioin.BatchCapitalInvestmentInformationEntity;
import cn.bonoon.entities.informatioin.CityCapitalInvestmentInformationEntity;
import cn.bonoon.kernel.support.services.AbstractService;
import cn.bonoon.util.DoubleHelper;

@Service
@Transactional(readOnly = true)
public class BatchInvestServiceImpl extends AbstractService<BatchCapitalInvestmentInformationEntity> implements BatchInvestService{

	@Override
	@Transactional
	public void saveBatchs(List<BatchCapitalInvestmentInformationEntity> batchs,CityCapitalInvestmentInformationEntity cityReport) {
		
//		int i[] = new int[2];
		double d[] = new double[8];
		for(BatchCapitalInvestmentInformationEntity batch:batchs){
			entityManager.merge(batch);
//			i[0] += batch.getProjectStartCount();
//			i[1] += batch.getProjectFinishCount();
			
			d[6] = DoubleHelper.add(d[6], batch.getTotalFunds());
//			d[6] += batch.getTotalFunds();
			
			d[0] = DoubleHelper.add(d[0], batch.getFunds0());
			d[1] = DoubleHelper.add(d[1], batch.getFunds1());
			d[2] = DoubleHelper.add(d[2], batch.getFunds2());
			d[3] = DoubleHelper.add(d[3], batch.getFunds3());
			d[4] = DoubleHelper.add(d[4], batch.getFunds4());
			d[5] = DoubleHelper.add(d[5], batch.getFunds5());
//			d[0] += batch.getFunds0();
//			d[1] += batch.getFunds1();
//			d[2] += batch.getFunds2();
//			d[3] += batch.getFunds3();
//			d[4] += batch.getFunds4();
//			d[5] += batch.getFunds5();
		}
//		cityReport.setProjectStartCount(i[0]);
//		cityReport.setProjectFinishCount(i[1]);
		
		
		cityReport.setTotalFunds(d[6]);
		cityReport.setFunds0(d[0]);
		cityReport.setFunds1(d[1]);
		cityReport.setFunds2(d[2]);
		cityReport.setFunds3(d[3]);
		cityReport.setFunds4(d[4]);
		cityReport.setFunds5(d[5]);
		entityManager.merge(cityReport);
	}

	@Override
	public List<BatchCapitalInvestmentInformationEntity> allBatchInvestByCReportId(
			Long id) {
		String ql = "select x from BatchCapitalInvestmentInformationEntity x where x.cciInformation.id=? and x.deleted=false";
		return __list(BatchCapitalInvestmentInformationEntity.class, ql, id);
	}


	
	
	

}
