package cn.bonoon.services;

import cn.bonoon.core.configs.ModelAreaConfig;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.entities.Persistable;
import cn.bonoon.kernel.support.services.AbstractService;
import cn.bonoon.kernel.web.ConfigManager;

public abstract class ConfigurableModelAreaService<T extends Persistable> extends AbstractService<T>{
	
	/*--------------------------------------------------------------------
	 * 以下是关于片区台账基本信息的开放时间配置的
	 */

	private ConfigManager configManager;
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		configManager = ConfigManager.getManager();
	}
	
	protected ModelAreaConfig __config() {
		ModelAreaConfig config = new ModelAreaConfig();
		try{
			configManager.read(applicationContext, entityManager, config);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return config;
	}
	
	protected void checkAndThrowError(ModelAreaEntity entity, IOperator ope){
		checkAndThrowError(entity.getBatch(), ope);
	}
	
	protected void checkSubmitAndThrowError(ModelAreaEntity entity, IOperator ope){
		if(!__config().check(entity.getBatch(), ope, false)){
			throw new RuntimeException("第" + entity.getBatch() + "批的台账信息填报已经结束，不能再提交数据！");
		}
	}
	
	protected void checkAndThrowError(String batch, IOperator ope){
		if(!__config().check(batch, ope, true)){
			throw new RuntimeException("第" + batch + "批的台账信息填报已经结束，不能再保存数据！");
		}
	}
}
