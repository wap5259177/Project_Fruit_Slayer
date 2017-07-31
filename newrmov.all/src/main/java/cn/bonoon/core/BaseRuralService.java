package cn.bonoon.core;

import java.util.List;

import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.entities.BaseRuralEntity;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.ProjectEntity;
import cn.bonoon.kernel.support.services.SearchService;

public interface BaseRuralService extends SearchService<BaseRuralEntity> {
	List<FileEntity> annexes(Long id, long arid, String code);
	List<FileEntity> medias1(Long id);
	List<?> findAllMedias(Long id);
	List<FileEntity> medias(Long id, int code, String buildType);
	AdministrationCoreRuralEntity getAdminRural(long arid);
	void saveMedia(BaseRuralEntity re, FileEntity _file);
	void saveAnnex(BaseRuralEntity rural, FileEntity _file);
	void saveAnnex(AdministrationCoreRuralEntity are, FileEntity _file);
	
	
	/**
	 * 自然村下面的项目
	 * @param nrid
	 * @return
	 */
	List<ProjectEntity> getProject(Long nrid);
	List<ProjectEntity> getProject(Long ruralId, Integer status);
	/**
	 * 自然村下面的产业发展
	 * @param id
	 * @return
	 */
	List<Object[]> getIndustries(Long id);
}
