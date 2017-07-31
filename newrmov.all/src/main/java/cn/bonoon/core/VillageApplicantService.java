package cn.bonoon.core;

import java.util.Date;
import java.util.List;

import cn.bonoon.entities.VillageApplicantEntity;
import cn.bonoon.entities.VillageEvaluatePointEntity;
import cn.bonoon.entities.VillagePictureEntity;
import cn.bonoon.entities.VillageProjectEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.GenericService;

public interface VillageApplicantService extends GenericService<VillageApplicantEntity>, ApplicantStatus{

	void auditPass(OperateEvent event, Long id, String content1, Date at1, String content2, Date at2);
	void auditReject(OperateEvent event, Long id, String content);
	List<VillageEvaluatePointEntity> evaluatePoints(Long id);
	List<PlaceEntity> towns(IOperator event);
	List<VillageProjectEntity> projects(Long id);
	List<VillagePictureEntity> picutres(Long id);
	void deletePicture(IOperator user, Long vid, Long pid);
}
