package cn.bonoon.services;

import javax.persistence.EntityManager;

import cn.bonoon.entities.BaseRuralEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.util.StringHelper;

class InternalRuralHelper {
/**
 * 对村的地区进行查找并重新赋值 目前看到的是自然村的
 * @param entityManager
 * @param rural
 * @return
 */
	static PlaceEntity __findPlace(EntityManager entityManager, BaseRuralEntity rural){
		PlaceEntity pe = rural.getPlace();	
		Long pid = rural.getPlaceId();
		if(null == pe){
			//处理返回修改没有对地区进行赋值的情况
			if(pid > 0){
				pe = entityManager.find(PlaceEntity.class, pid);
			}else{
				//看镇是否还存在
				String tn = rural.getTown();
				if(StringHelper.isEmpty(tn)){
					throw new RuntimeException("数据异常1，请联系管理人员！");
				}
				//看该村村委会是否还存在
				String rn = rural.getName();
				if(StringHelper.isEmpty(rn)){
					throw new RuntimeException("数据异常2，请联系管理人员！");
				}
				//拿到rural自然村所在片区的所有地区实体PlaceEntity应该是镇一级别的吧 因为下面有判断片区下的每个地区实体名是否等于tn自然村的镇名
				for(PlaceEntity cs : rural.getModelArea().getPlace().getChildren()){
					if(tn.equals(cs.getName())){
						//获取镇下一级的所有地区实体吧PlaceEntity 那就是村了 
						for(PlaceEntity vs : cs.getChildren()){
							//判断当前自然村村名是否等于镇下一级的vs.getName()
							if(rn.equals(vs.getName())){
								if(pe != null){
								//	pe一定等于null..
									throw new RuntimeException("数据异常，有相同行政村名称，请联系管理人员！");
								}
								//如果等于就pe赋值为vs那不是镇下一级的咯?村地区实体
								pe = vs;
							}
						}
						//拿到镇名相同的旧退出循环?
						break;
					}
				}
			}
			if(null == pe){
				throw new RuntimeException("数据异常3，请联系管理人员！");
			}
			rural.setPlace(pe);
		}
		pid = pe.getId();
		rural.setPlaceId(pid);
		entityManager.merge(rural);
		return pe;
	}
}
