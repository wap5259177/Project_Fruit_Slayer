package cn.bonoon.core;

import java.util.List;

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.entities.ModelAreaQuarterBatch;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.entities.ruralBuild.ModelAreaCRBuildQuarterBatch;
import cn.bonoon.entities.ruralBuild.ModelAreaCRBuildQuarterEntity;
import cn.bonoon.entities.ruralBuild.ModelAreaCRBuildQuarterItem;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.services.GenericService;

public interface StatisticsCRBuildService extends GenericService<ModelAreaCRBuildQuarterEntity> {

	//结束
	void refreshItem(LogonUser user, Long id);

	List<ModelAreaCRBuildQuarterItem> itemSurveies(Long pid, Long id);

	List<ModelAreaCRBuildQuarterBatch> batchSurveies(Long pid);
/**
 * 分页查询
 * 省季报的每个批次下所有的县季报
 * @param pid
 * @param id
 * @return
 */
	List<ModelAreaCRBuildQuarterItem> getAllItemFromProvince(Long id,String batchName);
	List<ModelAreaCRBuildQuarterItem> getAllItemFromProvince(Long id);
	
    void upadateBatch(Long itemId, int batch, Long quarterId) ;
   
}
