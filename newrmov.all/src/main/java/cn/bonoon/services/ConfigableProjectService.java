package cn.bonoon.services;

import cn.bonoon.core.configs.ProjectConfig;
import cn.bonoon.kernel.support.entities.Persistable;
import cn.bonoon.kernel.support.services.AbstractService;
import cn.bonoon.kernel.web.ConfigManager;

public abstract class ConfigableProjectService<T extends Persistable> extends AbstractService<T>{

	private ConfigManager configManager;
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		configManager = ConfigManager.getManager();
	}
	
	protected ProjectConfig __config() {
		ProjectConfig config = new ProjectConfig();
		try{
			configManager.read(applicationContext, entityManager, config);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return config;
	}
}
