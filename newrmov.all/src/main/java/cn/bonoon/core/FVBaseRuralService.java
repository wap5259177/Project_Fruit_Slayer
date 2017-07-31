package cn.bonoon.core;

import java.util.Date;
import java.util.List;

import cn.bonoon.entities.felicityVillage.FVBaseRuralEntity;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.services.GenericService;

public interface FVBaseRuralService extends GenericService<FVBaseRuralEntity>{

	void saveRural(LogonUser user,Long id, boolean ismaster, String crName, String crType,
			String prName, String prType, Long coreRural, Boolean finishedPlan,
			Boolean finishBid, Integer projectNum,Integer projectFinishNum,
			Date startTime, Date predictFinishTime,String constructProgress);

	//更新Prural
	void updatePRural(LogonUser user, Long id, String prName, String prType,
			Long coreRural, Integer projectNum, Integer projectFinishNum,
			Date startTime, Date predictFinishTime, String constructProgress);

	//更新crural
	void updateCRural(LogonUser user, Long id, String crName, String crType,
			Boolean finishedPlan, Boolean finishedBid, Integer projectNum,
			Integer projectFinishNum, Date startTime, Date predictFinishTime,
			String constructProgress);

	//所有村子
	List<FVBaseRuralEntity> allRuralByModelArea(Long id);//object[0]id   object[1]name  

	void deleteRural(Long id);

}
