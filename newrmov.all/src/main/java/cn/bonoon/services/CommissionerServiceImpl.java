package cn.bonoon.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.CommissionerService;
import cn.bonoon.entities.CommissionerEntity;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.UnitEntity;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.AbstractSearchService;
import cn.bonoon.kernel.util.StringHelper;

@Service
@Transactional(readOnly = true)
public class CommissionerServiceImpl extends AbstractSearchService<CommissionerEntity> implements CommissionerService {

	@Override
	public CommissionerEntity getByUser(IOperator user) {
		String ql = "select x from CommissionerEntity x where x.history=false and x.deleted=false and x.ownerId=?";
		return __first(CommissionerEntity.class, ql, user.getOwnerId());
	}

	@Override
	@Transactional
	public void save(IOperator user, String name, String job, String phone1,
			String phone2, String remark,String qqNum) {
		String emsg = "";
		if(StringHelper.isEmpty(name)){
			emsg += "信息专员名称不能为空！<br/>";
		}
		if(StringHelper.isEmpty(job)){
			emsg += "信息专员职位不能为空！<br/>";
		}
		if(StringHelper.isEmpty(phone1)){
			emsg += "信息专员的固定电话不能为空！<br/>";
		}
		if(StringHelper.isEmpty(phone2)){
			emsg += "信息专员的手机号码不能为空！<br/>";
		}
		if(StringHelper.isEmpty(qqNum)){
			emsg += "信息专员的QQ号码不能为空！<br/>";
		}
		if(!emsg.isEmpty()){
			throw new RuntimeException(emsg);
		}
		//处理信息专员的信息
		String ql = "select x from CommissionerEntity x where x.deleted=false and x.history=false and x.ownerId=?";
		List<CommissionerEntity> comms = __list(CommissionerEntity.class, ql, user.getOwnerId());
		Date now = new Date();
		UnitEntity unit;
		if(!comms.isEmpty()){
			//String cn = entity.getContactName();
			CommissionerEntity oce = null;
			for(CommissionerEntity co : comms){
				if(name.equals(co.getName())){
					oce = co;
					break;
				}
			}
			if(null == oce){
				oce = comms.get(0);
			}
			//如果所有信息都相同，则不创建新的记录
			//if(job.equals(oce.getJob()))
			oce.setHistory(true);
			oce.setUpdateAt(now);
			oce.setUpdaterId(user.getId());
			oce.setUpdaterName(user.getUsername());
			entityManager.merge(oce);
			unit = oce.getUnit();
		}else{
			unit = entityManager.find(UnitEntity.class, user.getOwnerId());
			if(null == unit){
				throw new RuntimeException("非市/县级单位！");
			}
		}
		CommissionerEntity ce = new CommissionerEntity();
		ce.setCreateAt(now);
		ce.setCreatorId(user.getId());
		ce.setCreatorName(user.getUsername());
		ce.setOwnerId(user.getOwnerId());
		
		ce.setJob(job);
		ce.setName(name);
		ce.setPhone1(phone1);
		ce.setPhone2(phone2);
		ce.setRemark(remark);
		ce.setQqNum(qqNum);
		ce.setUnit(unit);
		entityManager.persist(ce);
		String qlma = "select x from ModelAreaEntity x where x.deleted=false and x.ownerId=?";
		ModelAreaEntity ma = __first(ModelAreaEntity.class, qlma, user.getOwnerId());
		if(null != ma){
			ma.setContactJob(job);
			ma.setContactName(name);
			ma.setContactPhone(phone1);
			ma.setContactPhone2(phone2);
			ma.setContactQQNum(qqNum);
			entityManager.merge(ma);
		}
	}

}
