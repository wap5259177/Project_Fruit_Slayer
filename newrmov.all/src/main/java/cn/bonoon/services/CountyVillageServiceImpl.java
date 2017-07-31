package cn.bonoon.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.CountyVillageService;
import cn.bonoon.entities.informatioin.CityNaturalVillageInformationEntity;
import cn.bonoon.entities.informatioin.CountyNaturalVillageInformationEntity;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class CountyVillageServiceImpl extends AbstractService<CountyNaturalVillageInformationEntity> implements CountyVillageService{

	@Override
	@Transactional
	public void saveCountys(List<CountyNaturalVillageInformationEntity> counties,CityNaturalVillageInformationEntity cityReport) {
		
		int ct[] = new int[4];
		int hd[] = new int[6];
		for(CountyNaturalVillageInformationEntity entity:counties){
			entityManager.merge(entity);
//			ct[0] += entity.getHouseholdCount();
//			ct[1] += entity.getPeopleCount();
			ct[2] += entity.getAvCount();
			
			hd[0] += entity.getHousehold0();
			hd[1] += entity.getHousehold1();
			hd[2] += entity.getHousehold2();
			hd[3] += entity.getHousehold3();
			hd[4] += entity.getHousehold4();
		}
//		cityReport.setHouseholdCount(ct[0]);
//		cityReport.setPeopleCount(ct[1]);
		cityReport.setAvCount(ct[2]);
		cityReport.setHousehold0(hd[0]);
		cityReport.setHousehold1(hd[1]);
		cityReport.setHousehold2(hd[2]);
		cityReport.setHousehold3(hd[3]);
		cityReport.setHousehold4(hd[4]);
		entityManager.merge(cityReport);
	}

	@Override
	public List<CountyNaturalVillageInformationEntity> allVillagestByCReportId(
			Long id) {
		String ql = "select x from CountyNaturalVillageInformationEntity x where x.cnvInformation.id=? and x.deleted=false";
		return __list(CountyNaturalVillageInformationEntity.class, ql, id);
	}


	
	
	

}
