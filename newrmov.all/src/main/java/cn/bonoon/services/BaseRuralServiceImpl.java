package cn.bonoon.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.BaseRuralService;
import cn.bonoon.core.DirectoryExtattr;
import cn.bonoon.core.FileStatus;
import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.entities.BaseRuralEntity;
import cn.bonoon.entities.DirectoryEntity;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.ProjectEntity;
import cn.bonoon.kernel.support.services.AbstractSearchService;

@Service
@Transactional(readOnly = true)
public class BaseRuralServiceImpl extends AbstractSearchService<BaseRuralEntity> implements BaseRuralService {

	@Override
	public List<FileEntity> annexes(Long id, long arid, String code) {
		Long did;
		String ql;
		if(arid > 0){
			ql = "select x.directory.id from AdministrationRuralEntity x where x.id=?";
			did = __first(Long.class, ql, arid);
		}else{
			ql = "select x.directory.id from BaseRuralEntity x where x.id=?";
			did = __first(Long.class, ql, id);
		}
		if(null != did){
			ql = "select x from FileEntity x left join x.directory y where x.deleted=false and x.extattr6=? and y.id=?";
			return __list(FileEntity.class, ql, code, did);
		}
		return Collections.emptyList();
	}
	
	@Override
	public List<FileEntity> medias(Long id, int code, String buildType) {
		String ql = "select x.directoryMedia.id from BaseRuralEntity x where x.id=?";
		Long did = __first(Long.class, ql, id);
		if(null != did){
			ql = "select x from FileEntity x left join x.directory y where x.deleted=false and x.extattr1=? and x.extattr7=? and y.id=?";
			return __list(FileEntity.class, ql, code, buildType, did);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public void saveAnnex(BaseRuralEntity rural, FileEntity _file) {
		DirectoryEntity dir = rural.getDirectory();
		if(null == dir){
			dir = new DirectoryEntity();
			dir.setCreateAt(_file.getCreateAt());
			dir.setCreatorId(_file.getCreatorId());
			dir.setCreatorName(_file.getCreatorName());
			dir.setOwnerId(_file.getOwnerId());
			dir.setStatus(FileStatus.STATUS_FORBID);
			dir.setShare(FileStatus.SHARE_PRIVATE);
			String name = rural.getName();
			dir.setParent(rural.getModelArea().getDirectory());
			dir.setExtattr3(rural.getId());
			
			dir.setExtattr5(name);
			dir.setName(name);
			dir.setExtattr6(DirectoryExtattr.ROOT_RURAL);

			entityManager.persist(dir);
			
			rural.setDirectory(dir);
			entityManager.merge(rural);
		}
		_file.setDirectory(new ArrayList<DirectoryEntity>());
		_file.getDirectory().add(dir);
		_file.setStatus(FileStatus.STATUS_FORBID);
		_file.setVersion(1);
		entityManager.persist(_file);
	}
	
	@Override
	@Transactional
	public void saveAnnex(AdministrationCoreRuralEntity are, FileEntity _file) {
		DirectoryEntity dir = are.getDirectory();
		if(null == dir){
			dir = new DirectoryEntity();
			dir.setCreateAt(_file.getCreateAt());
			dir.setCreatorId(_file.getCreatorId());
			dir.setCreatorName(_file.getCreatorName());
			dir.setOwnerId(_file.getOwnerId());
			dir.setStatus(FileStatus.STATUS_FORBID);
			dir.setShare(FileStatus.SHARE_PRIVATE);
			String name = are.getName();
			dir.setParent(are.getModelArea().getDirectory());
			dir.setExtattr3(are.getId());
			
			dir.setExtattr5(name);
			dir.setName(name);
			dir.setExtattr6(DirectoryExtattr.ROOT_ADMIN_RURAL);

			entityManager.persist(dir);
			
			are.setDirectory(dir);
			entityManager.merge(are);
		}
		_file.setDirectory(new ArrayList<DirectoryEntity>());
		_file.getDirectory().add(dir);
		_file.setStatus(FileStatus.STATUS_FORBID);
		_file.setVersion(1);
		entityManager.persist(_file);
	}
	
	@Override
	@Transactional
	public void saveMedia(BaseRuralEntity re, FileEntity _file) {
		DirectoryEntity dm = re.getDirectoryMedia();
		if(null == dm){
			dm = new DirectoryEntity();
			dm.setCreateAt(_file.getCreateAt());
			dm.setCreatorId(_file.getCreatorId());
			dm.setCreatorName(_file.getCreatorName());
			dm.setOwnerId(_file.getOwnerId());
			dm.setParent(re.getDirectory());
			dm.setShare(FileStatus.SHARE_PRIVATE);
			dm.setStatus(FileStatus.STATUS_FORBID);
			dm.setName("多媒体文档");
			entityManager.persist(dm);
			re.setDirectoryMedia(dm);
			entityManager.merge(re);
		}
		_file.setDirectory(new ArrayList<DirectoryEntity>());
		_file.getDirectory().add(dm);
		_file.setStatus(FileStatus.STATUS_FORBID);
		_file.setVersion(1);
		entityManager.persist(_file);
	}

	@Override
	public List<FileEntity> medias1(Long id) {
//		String ql = "select x from BaseRuralEntity x where x.id=? order by x.id desc";
//		List<BaseRuralEntity> bs=__list(BaseRuralEntity.class, ql, id);
//		Long did=null;
//		for(BaseRuralEntity br : bs){
//			did=br.getDirectoryMedia().getId();
//	        if(null != did){
//			  ql = "select x from FileEntity x left join x.directory y where x.deleted=false and y.id=? and x.issueAt is not null order by x.issueAt desc";
//			
//		    }
//		}
		BaseRuralEntity bre = entityManager.find(BaseRuralEntity.class, id);
		if(null != bre){
			String ql = "select x from FileEntity x left join x.directory y where x.deleted=false and y.id=? and x.issueAt is not null order by x.issueAt desc";
			return __list(FileEntity.class, ql, bre.getId());
		}
		return Collections.emptyList();
	}

	@Override
	public List<?> findAllMedias(Long id) {
		String sql="SELECT C_ISSUEAT,r.C_ID,r.C_NAME name,r.C_TYPE,f.C_MAPPATH,f.C_NAME name1,f.C_REMARK,f.C_EXTATTR1,f.C_CREATEAT from t_rural r, t_file f, rt_directory2file df where r.R_DIRMEDIA_ID = df.R_DIRECTORY_ID and f.C_ID = df.R_FILE_ID and f.C_DELETED=0 and r.R_MODELAREA_ID = ? order by r.C_ID";
		return entityManager.createNativeQuery(sql).setParameter(1, id).getResultList();
	}
	@Override
		public AdministrationCoreRuralEntity getAdminRural(long arid) {
			return entityManager.find(AdministrationCoreRuralEntity.class, arid);
		}

	@Override
	public List<ProjectEntity> getProject(Long nrid) {
		String ql = "select x from ProjectEntity x where x.deleted=false and x.rural.id=? and x.modelArea.deleted=false";
		return __list(ProjectEntity.class, ql, nrid);
	}

	@Override
	public List<ProjectEntity> getProject(Long ruralId, Integer status) {
		String ql = "select x from ProjectEntity x where x.deleted=false and x.rural.id=? and x.modelArea.deleted=false and x.status=?";
		return __list(ProjectEntity.class, ql, ruralId,status);
	}

	@Override
	public List<Object[]> getIndustries(Long id) {
		String ql = "select x.id, x.coopName from IndustryAreaEntity x where x.deleted=false and x.rural.id=? and x.modelArea.deleted=false";
		return __list(Object[].class, ql, id);
	}
}
