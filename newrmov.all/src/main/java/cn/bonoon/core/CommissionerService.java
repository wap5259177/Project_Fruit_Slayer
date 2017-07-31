package cn.bonoon.core;

import cn.bonoon.entities.CommissionerEntity;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.SearchService;

public interface CommissionerService extends SearchService<CommissionerEntity> {

	CommissionerEntity getByUser(IOperator user);

	void save(IOperator user, String name, String job, String phone1,
			String phone2, String remark,String qqNum);

}
