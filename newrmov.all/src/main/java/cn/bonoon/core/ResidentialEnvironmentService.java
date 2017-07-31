package cn.bonoon.core;

import java.util.List;

import cn.bonoon.entities.BaseRuralEntity;
import cn.bonoon.entities.ResidentialEnvironmentEntity;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.GenericService;

public interface ResidentialEnvironmentService extends GenericService<ResidentialEnvironmentEntity>{
	List<ResidentialEnvironmentEntity> getResidentialEnvironment(Long oId);
	//void saveResidentialEnvironment(OperateEvent event,Long[] arr_annual,Long[] arr_ordinal,List<String> rr_townName,List<String> rr_villageName,List<String> rr_naturalVillage);
	//void deleteResidentialEnvironment(OperateEvent event,ResidentialEnvironmentEntity entity);
	//ResidentialEnvironmentEntity getById(Long id);
	//UnitEntity getUnit(Long uid);
	BaseRuralEntity getRural(Long rid);
	void save(List<ResidentialEnvironmentEntity> items);
	List<BaseRuralEntity> getRurals(Long uid);
	
	/**
	 * <pre>
	 * 统计某一批次，方法里已经对批次进行处理，所以外面不需处理，直接把值传进来即可
	 * batch  = BatchHelper.get(batch);
	 * </pre>
	 * @param batch
	 * @return
	 */
	List<Object[]> statistics(String batch);
	List<Object[]> statisticsLocal(IOperator opt, String batch);
	List<Object[]> statisticsCity(IOperator opt, String batch);
	ResidentialEnvironmentEntity findRes(Long id,int annual);
}
