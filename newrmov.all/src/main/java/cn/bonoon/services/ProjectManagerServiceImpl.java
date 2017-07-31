//package cn.bonoon.services;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import cn.bonoon.core.ProjectManagerService;
//import cn.bonoon.entities.ProjectEntity;
//import cn.bonoon.kernel.support.services.AbstractService;
//@Service
//@Transactional(readOnly = true)
//public class ProjectManagerServiceImpl extends AbstractService<ProjectEntity> implements ProjectManagerService{
//
//	@Override
//	@Transactional
//	public void back(Long id) {
//		ProjectEntity pro = __get(id);
//		pro.setStatus(0);
//		entityManager.merge(pro);
//	}
//
//}
