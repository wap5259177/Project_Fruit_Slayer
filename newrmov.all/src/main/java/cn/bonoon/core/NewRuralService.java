package cn.bonoon.core;

import java.util.Collection;
import java.util.List;

import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.RuralExpertGroupEntity;
import cn.bonoon.entities.RuralUnitEntity;
import cn.bonoon.entities.RuralWorkGroupEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.GenericService;

public interface NewRuralService extends GenericService<NewRuralEntity> {

	List<PlaceEntity> towns(Long oId);
	List<PlaceEntity> village(Long oId);
//	DirectoryEntity dir_building(Long id, int extattr); // extattr 33：建设前，34：建设中，35，建设后
//	void createDirViedo(OperateEvent event, NewRuralEntity newRural);
//	void createDirImg(OperateEvent event, NewRuralEntity newRural);
	
    List<FileEntity> medias(Long id, int code, String buildType);
    List<FileEntity> mediasTime(Long id);
    List<FileEntity> mediasTime1(Long id,int code);
	Collection<Object[]> statistics(String batch);
	Collection<Object[]> statisticsLocal(IOperator opt, String batch);
	Collection<Object[]> statisticsCity(IOperator opt, String batch);
	
	List<RuralWorkGroupEntity> workGroups(Long id);
	List<RuralExpertGroupEntity> expertGroups(Long id);
	List<RuralUnitEntity> ruralUnits(Long id);
//	DirectoryEntity dir_buildingOrCreate(IOperator opt,Long id, int intValue);
	List<RuralWorkGroupEntity> workGroupsByAdminRural(Long id);
	List<RuralUnitEntity> ruralUnitsByAdminRural(Long id);
	List<RuralExpertGroupEntity> experGroupByAdminRural(Long id);
	AdministrationCoreRuralEntity getAdministrationRural(IOperator opt, Long id);
	AdministrationCoreRuralEntity getAdministrationRural(Long id);
	
	boolean check(Long id,IOperator opt);
}
