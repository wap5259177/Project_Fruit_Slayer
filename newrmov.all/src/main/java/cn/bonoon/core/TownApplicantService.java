package cn.bonoon.core;

import java.util.Date;
import java.util.List;

import cn.bonoon.entities.TownApplicantEntity;
import cn.bonoon.entities.TownEvaluatePointEntity;
import cn.bonoon.entities.TownPictureEntity;
import cn.bonoon.entities.TownProjectEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.GenericService;

public interface TownApplicantService extends GenericService<TownApplicantEntity>, ApplicantStatus{

	void auditPass(OperateEvent event, Long id, String content1, Date at1, String content2, Date at2);
	void auditReject(OperateEvent event, Long id, String content);
	List<TownEvaluatePointEntity> evaluatePoints(Long id);
	List<PlaceEntity> towns(IOperator event);
	List<TownProjectEntity> projects(Long id);
	List<TownPictureEntity> picutres(Long id);
	void deletePicture(IOperator user, Long tid, Long pid);
}
