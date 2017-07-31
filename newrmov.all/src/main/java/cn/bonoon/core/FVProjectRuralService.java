package cn.bonoon.core;

import java.util.Date;
import java.util.List;

import cn.bonoon.core.felicity.ProjectInfo;
import cn.bonoon.entities.felicityVillage.FVProjectRuralEntity;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.services.GenericService;

public interface FVProjectRuralService extends GenericService<FVProjectRuralEntity>{

	void saveProject(LogonUser user,Long id,Long areaId, String pjType, String pjName, Long[] rurals,
			Double budgetMoney, Double finishMoney, Date startTime,
			Date finishTime, Date checkTime, String checkUnit);

	List<ProjectInfo> makePjList(Long id);

	void deleteProject(Long id);

	List<ProjectInfo> makePjListByMaId(Long id);


}
