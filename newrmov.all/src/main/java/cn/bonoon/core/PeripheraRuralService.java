package cn.bonoon.core;

import java.util.Collection;
import java.util.List;

import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.PeripheralRuralEntity;
import cn.bonoon.entities.RuralExpertGroupEntity;
import cn.bonoon.entities.RuralUnitEntity;
import cn.bonoon.entities.RuralWorkGroupEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.GenericService;

public interface PeripheraRuralService extends GenericService<PeripheralRuralEntity> {
	/**
	 * x.id,x.name,x.town,x.naturalVillage
	 * @param oid
	 * @param mid
	 * @return
	 */
//	List<NewRuralEntity> getRurals(Long newRural);
	List<PlaceEntity> getTowns(Long oid);
	List<Object[]> rurals(Long oid, Long mid);
//	DirectoryEntity dir_building(Long id, int extattr); // extattr 33：建设前，34：建设中，35，建设后
	
//	void createDirViedo(OperateEvent event, PeripheralRuralEntity peripheralRural);
//	void createDirImg(OperateEvent event, PeripheralRuralEntity peripheralRural);
	List<FileEntity> medias(Long id, int code, String buildType);
	public List<FileEntity> mediasTime(Long id);
	List<FileEntity> mediasTime1(Long id,int code);
	Collection<Object[]> statistics(String batch);
	Collection<Object[]> statisticsLocal(IOperator opt, String batch);
	Collection<Object[]> statisticsCity(IOperator opt, String batch);
	

	List<RuralWorkGroupEntity> workGroups(Long id);
	List<RuralExpertGroupEntity> expertGroups(Long id);
	List<RuralUnitEntity> ruralUnits(Long id);
//	DirectoryEntity dir_buildingOrCreate(IOperator opt,Long id, int intValue);
	/**
	 * 用于把非主体村的行政村提取出来
	 */
	void extract();
	
	boolean check(Long id, IOperator opt);
	
	
}
