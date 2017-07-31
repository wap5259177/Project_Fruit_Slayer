package cn.bonoon.services;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import cn.bonoon.core.SpecialFundsService;
import cn.bonoon.entities.SpecialFundsEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class SpecialFundsServiceImpl extends AbstractService<SpecialFundsEntity> implements SpecialFundsService {

	@Override
	protected SpecialFundsEntity __save(OperateEvent event, SpecialFundsEntity entity) {
		__edit(event, entity);
		return super.__save(event, entity);
	}
	
	@Override
	protected SpecialFundsEntity __update(OperateEvent event, SpecialFundsEntity entity) {
		__edit(event, entity);
		return super.__update(event, entity);
	}
	
	private void __edit(OperateEvent event, SpecialFundsEntity entity){
		
		if(event.is("applicant-submit")){
			
			PlaceEntity pl = entity.getPlace();
			entity.setStatus(FINISH);
			//检查完整性
			Assert.notNull(pl, "必须选择一个城市！");
			//2010-2020
			int annual = entity.getAnnual();
			if(annual<2010 || annual>2020) {
				throw new RuntimeException("年度要在2010-2020之间");
			}
			if(entity.getQuota() <= 0) {
				throw new RuntimeException("额度不能小于0");
			}
			Assert.hasText(entity.getContactName(), "联系人不允许为空！");
			Assert.hasText(entity.getContactPhone(), "联系电话不允许为空！");
			Date aat = entity.getRecordAt();
			Assert.notNull(aat, "下拨时间不允许为空！");
		}
	}
}
