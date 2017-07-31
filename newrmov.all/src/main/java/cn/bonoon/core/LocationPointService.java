package cn.bonoon.core;

import java.util.List;

import cn.bonoon.entities.BaseRuralEntity;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.ModelAreaPointEntity;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.PeripheralRuralEntity;
import cn.bonoon.entities.RuralPanoramaEntity;
import cn.bonoon.entities.RuralPointEntity;

public interface LocationPointService {

	// 根据当前县，取出示范片区信息
	ModelAreaPointEntity findModelAreaPointByCurrUser(Long oid);
	ModelAreaEntity findModelAreaByCurrUser(Long oid);
	
	//通过id查找
	ModelAreaEntity findModelAreaById(Long id);
	ModelAreaPointEntity findModelAreaPointById(Long id);
	
	//提交,创建并保存片区地理位置信息
	void commitModelAreaPoint(ModelAreaPointEntity modelAreaPoint);
	//片区编辑
	void updateModelAreaPoint(ModelAreaPointEntity modelAreaPoint);
	
	//通过片区的Id查找这个片区下的核心村子,非主体村
	List<NewRuralEntity> findAllNewRuralBymodelAreaId(Long id);
	List<PeripheralRuralEntity> findAllPeripheralRuralBymodelAreaId(Long id);
	
	//村子的创建.更新,查找
	BaseRuralEntity findRuralById(Long id);
	RuralPointEntity findRuralPointByRuralId(Long id);
	void commitRuralPoint(RuralPointEntity ruralPoint);
	void updateRuralPoint(RuralPointEntity ruralPoint);
	
	//查看片区下的所有村位置
	List<RuralPointEntity> findRuralPointByMapointId(Long id);
	
	List<ModelAreaPointEntity> getAll();
	
	//查看所有村子
	List<BaseRuralEntity>findAllRural();
	//所有村子点
	List<RuralPointEntity>findAllRuralPoint();
	

	//保存全景点
	void savePanoramaPoint(RuralPanoramaEntity ruralPano);
	RuralPanoramaEntity findRuralPanoById(Long id);
	List<RuralPanoramaEntity> findAllRuralPano();
	List<RuralPanoramaEntity> findRuralPanoByRuralId(Long id);
	
}
