package cn.bonoon.core;

import java.util.Collection;
import java.util.List;

import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.entities.AdministrationRuralEntity;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.RuralExpertGroupEntity;
import cn.bonoon.entities.RuralUnitEntity;
import cn.bonoon.entities.RuralWorkGroupEntity;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.GenericService;

/**
 * 
 * @author jackson
 *
 */
public interface AdministrationCoreRuralService extends GenericService<AdministrationCoreRuralEntity>{

	AdministrationCoreRuralEntity getAdministrationByAdminRuralId(Long id);
	
	List<RuralWorkGroupEntity> workGroupsByAdminRural(Long id);
	List<RuralUnitEntity> ruralUnitsByAdminRural(Long id);
	List<RuralExpertGroupEntity> experGroupByAdminRural(Long id);
	
	List<NewRuralEntity> getRuralByAdminRuralId(Long id);
	
	/**
	 * 通过admin_id 查出该行政村下的自然村列表。
	 * 结果：
	 * 		t.C_NATURALVILLAGE  自然村名
	 */
	List<String> getNaturalVillage(Long adminId);
	boolean check(Long id,IOperator opt);

	Collection<Object[]> statistics(String batch);

//	Collection<Object[]> advanStatistics(String batch);

	List<AdministrationRuralEntity> annualIncome(Long id);
//	public Collection<Object[]>  showItems(Collection<Object[]> items);
	Collection<Object[]> excelValue(String batch);
	Collection<Object[]> excelValue3(String batch) ;
}
