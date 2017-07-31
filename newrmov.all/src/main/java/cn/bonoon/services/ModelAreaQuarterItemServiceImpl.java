/*package cn.bonoon.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.ModelAreaQuarterItemService;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.ServiceSupport;
@Service
@Transactional(readOnly = true)
public class ModelAreaQuarterItemServiceImpl extends ServiceSupport implements ModelAreaQuarterItemService{

	@Override
	public List<ModelAreaQuarterItem> getUrges(IOperator user) {
		//片区未上报备案 (x.modelArea.status:-1),季报已经完成(x.status=1),季报在审核(x.status=4)  
		String ql = "select x from ModelAreaQuarterItem x where x.modelArea.ownerId=? and  x.urge>0 and x.status not in(1,4) and x.modelArea.status<>-1 and x.modelArea.deleted=false";
		return __list(ModelAreaQuarterItem.class, ql, user.getOwnerId());
	}

	@Override
	public List<ModelAreaQuarterItem> getNeedReport(IOperator user) {
		String ql = "select x from ModelAreaQuarterItem x where x.modelArea.ownerId=? and x.batch.quarter.startAt<=? and x.modelArea.status<>-1 and x.modelArea.deleted=false";
		return __list(ModelAreaQuarterItem.class, ql, user.getOwnerId(),new Date());
	}

}
*/