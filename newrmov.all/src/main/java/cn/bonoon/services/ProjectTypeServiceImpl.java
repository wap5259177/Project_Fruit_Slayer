package cn.bonoon.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.ProjectTypeService;
import cn.bonoon.entities.ProjectTypeEntity;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class ProjectTypeServiceImpl extends AbstractService<ProjectTypeEntity> implements ProjectTypeService {

}
