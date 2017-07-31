package cn.bonoon.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.FileService;
import cn.bonoon.core.FileStatus;
import cn.bonoon.core.FileType;
import cn.bonoon.entities.DirectoryEntity;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.FileHistoryEntity;
import cn.bonoon.entities.FileShareEntity;
import cn.bonoon.entities.ShareUserGroupEntity;
import cn.bonoon.entities.plugins.AccountEntity;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.AbstractService;
import cn.bonoon.kernel.util.StringHelper;

@Service
@Transactional(readOnly = true)
public class FileServiceImpl extends AbstractService<FileEntity> implements FileService {
	
	
	private final static String HQL_DIR = "from DirectoryEntity where deleted=false and id=?";

	@Override
	public List<FileEntity> getFile(IOperator opt) {
		return __list(FileEntity.class, "from FileEntity x where x.deleted=false and x.creatorId=?", opt.getId());
	}

	@Override
	public List<FileEntity> getFile(Long directory_id) {
//		return __list(FileEntity.class, "select distinct x from FileEntity x left join x.directory y where x.deleted=false and y.id=?", directory_id); // ok
		return __list(FileEntity.class, "select distinct x from FileEntity x, DirectoryEntity y where x.deleted=false and x in elements(y.file) and y.id=?", directory_id);
	}

	@Override
	@Transactional
	public void delete(IOperator opt, Long[] ids, Long node_id) {
		if (null != ids && ids.length != 0) {
				for (Long id : ids) {
						FileEntity entity = __get(id);
						if(null != node_id && node_id > 0){
								List<DirectoryEntity> directory = new ArrayList<>(entity.getDirectory());
								for(DirectoryEntity dir : directory){
									if(dir.getId().equals(node_id)){
										entity.getDirectory().remove(dir);
									}
								}
								if (directory.size()==1) { 
									// 如果有多个关联，则去掉这个目录，如果只有一个，就放入回收站
									entity.setStatus(FileStatus.STATUS_DELETED);
								}
						} else {
							entity.getDirectory().clear();
							entity.setStatus(FileStatus.STATUS_DELETED);
						  }
						entity.setUpdateAt(new Date());
						entity.setUpdaterId(opt.getId());
						entity.setUpdaterName(opt.getUsername());
						entityManager.merge(entity);
				}
		}
	}

	@Override
	@Transactional
	public void completelyDelete(IOperator opt, Long[] ids) {
		if (null != ids && ids.length != 0) {
			for (Long id : ids) {
				FileEntity entity = __get(id);
				entity.setDeleted(true);
				entity.setShared(false);
				entity.setUpdateAt(new Date());
				entity.setUpdaterId(opt.getId());
				entity.setUpdaterName(opt.getUsername());
				entity.getDirectory().clear();
				entityManager.merge(entity);
//				List<FileShareEntity> fss = __list(FileShareEntity.class, "from FileShareEntity x where x.file.id=?", id);
//				if (null != fss && fss.size() > 0) {
//					for (FileShareEntity fs : fss) {
//						entityManager.remove(fs);
//					}
//				}
			}
		}
	}

	@Override
	public FileShareEntity fileShare(Long fid, Long uid) {
		return __first(FileShareEntity.class, "from FileShareEntity where file.id=? and account.id=?", fid, uid);
	}

	@Override
	public Long getActions(Long fid, Long uid) {
		return __first(Long.class, "select actions from FileShareEntity where file.id=? and account.id=?", fid, uid);
	}

	@Override
	@Transactional
	public void saveActions(IOperator opt, Long fid, String actions) {
		if (StringHelper.isEmpty(actions))
			return;

		for (String str : StringHelper.toList(actions)) {
			int index = str.indexOf("_");
			Long action = Long.parseLong(str.substring(0, index));
			Long uid = Long.parseLong(str.substring(index + 1));
			String hql = "from FileShareEntity x where x.file.id=? and x.account.id = ?";
			FileShareEntity entity = __first(FileShareEntity.class, hql, fid, uid);
			if (null != entity) {
				entity.setActions(action);
				entityManager.merge(entity);
			} else {
				entity = new FileShareEntity();
				entity.setCreateAt(new Date());
				entity.setCreatorId(opt.getId());
				entity.setFile(__get(fid));
				entity.setAccount(__first(AccountEntity.class, "from AccountEntity where id=?", uid));
				entity.setActions(action);
				entityManager.persist(entity);
			}
		}
		boolean share = __exsit(
				"select count(x.id) from FileShareEntity x where x.actions<>0 and x.file.id=? and x.file.deleted=0 and x.file.status<>0",
				fid);
		FileEntity file = __get(fid);
		file.setShared(share);
		entityManager.merge(file);
	}

	@Override
	public List<FileShareEntity> getShareFiles(IOperator opt) {
		Long id = opt.getId();
		String ql = "select x from FileShareEntity x where x.actions>0 and x.account.id=? and x.file.deleted=0 and x.file.status<>0 and x.file.shared=1";
		return __list(FileShareEntity.class, ql, id);
	}
	
	@Override
	public List<DirectoryEntity> getShareDirectories(IOperator user) {
		String ql = "select x from DirectoryEntity x where x.deleted=false and x.share=1";
		return __list(DirectoryEntity.class, ql);
	}
//	@Override
//	public List<FileEntity> getShareFiles(Long did) {
//		String sl = "select x from FileEntity x left join x.directory y where x.deleted=false and x.status<>0 and y.id=?";
//		return __list(FileEntity.class, sl, did);
//	}
	
	
	@Override
	public List<FileEntity> getShareFiles(Long did,LogonUser user) {
		String sl = "select x from FileEntity x left join x.directory y where x.deleted=false  and x.status<>0 and y.id=?";
		if(user.getOwnerId()!=0){
			sl = "select x from FileEntity x left join x.directory y where x.deleted=false and x.ownerId in(0,?) and x.status<>0 and y.id=?";
			return __list(FileEntity.class, sl,user.getOwnerId(), did);
		}
		return __list(FileEntity.class, sl, did);
	}
	@Override
	public List<DirectoryEntity> getShareDirectories(Long did) {
		String ql = "select x from DirectoryEntity x where x.deleted=false and x.share=0 and x.parent.id=?";
		return __list(DirectoryEntity.class, ql, did);
	}

	@Override
	public List<FileHistoryEntity> getFileHistory(Long fid) {
		return __list(FileHistoryEntity.class, "from FileHistoryEntity x where x.file.id=? order by x.version desc", fid);
	}

	@Override
	@Transactional
	public void updateFileVersion(IOperator opt, Long id, String name, String extendedAttributes, Integer status, String remark, FileType type, Date issueAt
			, String ext, long length, String mapPath) {
		if (null != id && id > 0) { // update
			FileEntity entity = __get(id);
			if (null != entity) {
				Date date = new Date();
				Long uid = opt.getId();
				String uname = opt.getUsername();
				long version = entity.getVersion();
				// 上传新的附件，保存历史版本
				FileHistoryEntity fileHistory = new FileHistoryEntity();
				fileHistory.setCreateAt(date);
				fileHistory.setCreatorId(uid);
				fileHistory.setCreatorName(uname);
				fileHistory.setExt(entity.getExt());
				fileHistory.setExtendedAttributes(entity.getExtendedAttributes());
				fileHistory.setFile(entity);
				fileHistory.setLength(entity.getLength());
				fileHistory.setMapPath(entity.getMapPath());
				fileHistory.setName(entity.getName());
				fileHistory.setVersion(version);
				entityManager.persist(fileHistory);

				entity.setVersion(version + 1);
				entity.setName(name);
				entity.setExt(ext);
				entity.setExtendedAttributes(extendedAttributes);
				entity.setLength(length);
				entity.setMapPath(mapPath);
				entity.setStatus(status);
				entity.setRemark(remark);
				entity.setType(type);
				entity.setIssueAt(issueAt);
				entity.setUpdateAt(date);
				entity.setUpdaterId(uid);
				entity.setUpdaterName(uname);
				entityManager.merge(entity);
			}
		}
	}

	@Override
	@Transactional
	public void fileOperator(IOperator opt, Long[] ids, Long type, Long source_node_id, Long target_node_id) {
		if (null == ids || ids.length == 0)
			return;

		DirectoryEntity source_dir = __first(DirectoryEntity.class, HQL_DIR, source_node_id);
		DirectoryEntity target_dir = __first(DirectoryEntity.class, HQL_DIR, target_node_id);
		if (type == 0) { // 移动
			for (Long id : ids) {
				FileEntity file = __get(id);
				List<DirectoryEntity> directory = file.getDirectory();
				directory.remove(source_dir);
				directory.add(target_dir);
				entityManager.merge(file);
			}
		} else if (type == 1) { // 复制
			for (Long id : ids) {
				FileEntity file = __get(id);
				List<DirectoryEntity> directory = file.getDirectory();
				directory.add(target_dir);
				entityManager.merge(file);
			}
		}
	}

//	@Override
//	public List<FileEntity> getAreaFile(Long doc_dir_id, Long img_dir_id) {
//		return __list(FileEntity.class, "select distinct x from FileEntity x, DirectoryEntity y where x.deleted=false and x in elements(y.file) and (y.id=? or y.id=?) and x.status<>0 order by x.createAt desc", doc_dir_id, img_dir_id);
//	}

	@Override
	@Transactional
	public void delete(IOperator opt, Long[] ids) {
		if (null == ids || ids.length == 0)
			return;
		
		for (Long id : ids) {
			FileEntity file = __get(id);
			file.setDeleted(true);
			file.setUpdateAt(new Date());
			file.setUpdaterId(opt.getId());
			file.setUpdaterName(opt.getUsername());
			entityManager.merge(file);
		}
	}

	@Override
	@Transactional
	public void saveGroupActions(IOperator opt, Long fid, String actions) {
		if (StringHelper.isEmpty(actions))
			return;
		//11_4
		for (String str : StringHelper.toList(actions)) {
			int index = str.indexOf("_");
			Long action = Long.parseLong(str.substring(0, index));
			Long uid = Long.parseLong(str.substring(index + 1));
			
			ShareUserGroupEntity sg = entityManager.find(ShareUserGroupEntity.class, uid);
			List<AccountEntity> accounts = sg.getAccounts();
			for(AccountEntity acc:accounts){
				
				Long accId  = acc.getId();
				String ql = "select x from FileShareEntity x where x.account.id=? and x.file.id=?";
//				FileShareEntity f = __get(FileShareEntity.class,a.getId(),fid);
				FileShareEntity entity = __first(FileShareEntity.class,ql,accId,fid);
//				entity.setActions(action);
				
				if (null != entity) {
					entity.setActions(action);				
					entityManager.merge(entity);
				} else {
					entity = new FileShareEntity();
					entity.setCreateAt(new Date());
					entity.setCreatorId(opt.getId());
					entity.setFile(__get(fid));
					entity.setAccount(acc);
					entity.setActions(action);
					entityManager.persist(entity);
				}
			}
			
		}
		boolean share = __exsit(
				"select count(x.id) from FileShareEntity x where x.actions<>0 and x.file.id=? and x.file.deleted=0 and x.file.status<>0",
				fid);
		FileEntity file = __get(fid);
		file.setShared(share);
		entityManager.merge(file);
		
	}

	@Override
	public Long getGroupActions(Long fid, Long uid) {
		
//		FileShareEntity fse = entityManager.find(FileShareEntity.class, fid);
		
//		Long action = null;
//		
//		ShareUserGroupEntity sg = entityManager.find(ShareUserGroupEntity.class, fid);
//		List<AccountEntity> accounts = sg.getAccounts();
//		for(AccountEntity acc:accounts){
//			Long accId  = acc.getId();
//			String ql = "select x.actions from FileShareEntity x where file.id=? and account.id=?";
//			FileShareEntity entity = __first(FileShareEntity.class,ql,accId,fid);
//			action = entity.getActions();
//		}
//		return fid;
//		return action;
		return null;
		
	}

	
//	private AbstractEntity _init_entity(IOperator opt, AbstractEntity entity) {
//		entity.setCreateAt(new Date());
//		entity.setCreatorId(opt.getId());
//		entity.setCreatorName(opt.getUsername());
//		entity.setOwnerId(opt.getOwnerId());
//		return entity;
//	}
	
//	private DirectoryEntity __create(IOperator opt, int extattr, AbstractEntity entity, DirectoryEntity parent){
//		Long id = entity.getId();
//		String name = entity.getName();
//		DirectoryEntity dir = new DirectoryEntity();
//		dir.setParent(parent);
//		dir.setExtattr2(extattr);
//		dir.setExtattr3(id);
//		dir.setExtattr5(name);
//		dir.setName(name);
//		entityManager.persist(_init_entity(opt, dir));
//		return dir;
//	}
//
//	@Override
//	@Transactional
//	public void save_area_file(IOperator opt, FileEntity file, Long area_child, Long id, String dir_flag) {
//		DirectoryEntity directory = null;
//		if (area_child == Area.RURAL.getId()) { // 核心村
//			NewRuralEntity rural = __first(NewRuralEntity.class, "from NewRuralEntity where id=?", id);
//			DirectoryEntity parent = rural.getDirectory();
//			if (dir_flag.equals("B27")) {
//				directory = rural.getDirectoryB27();
//				if (null == directory) {
//					directory = __create(opt, DirectoryExtattr.RURAL, rural, parent);
//					rural.setDirectoryB27(directory);
//					entityManager.merge(rural);
//				}
//			} else if (dir_flag.equals("B28")) {
//				directory = rural.getDirectoryB28();
//				if (null == directory) {
//					directory = __create(opt, DirectoryExtattr.RURAL, rural, parent);
//					rural.setDirectoryB28(directory);
//					entityManager.merge(rural);
//				}
//			} else if (dir_flag.equals("B49")) {
//				directory = rural.getDirectoryB49();
//				if (null == directory) {
//					directory = __create(opt, DirectoryExtattr.RURAL, rural, parent);
//					rural.setDirectoryB49(directory);
//					entityManager.merge(rural);
//				}
//			} else if (dir_flag.equals("B50")) {
//				directory = rural.getDirectoryB50();
//				if (null == directory) {
//					directory = __create(opt, DirectoryExtattr.RURAL, rural, parent);
//					rural.setDirectoryB50(directory);
//					entityManager.merge(rural);
//				}
//			} else if (dir_flag.equals("B51")) {
//				directory = rural.getDirectoryB51();
//				if (null == directory) {
//					directory = __create(opt, DirectoryExtattr.RURAL, rural, parent);
//					rural.setDirectoryB51(directory);
//					entityManager.merge(rural);
//				}
//			}
//		} else if (area_child == Area.Peripheral.getId()) { // 非主体村
//			PeripheralRuralEntity per = __first(PeripheralRuralEntity.class, "from PeripheralRuralEntity where id=?", id);
//			DirectoryEntity parent = per.getDirectory();
//			if (dir_flag.equals("C27")) {
//				directory = per.getDirectoryC27();
//				if (null == directory) {
//					directory = __create(opt, DirectoryExtattr.RURAL, per, parent);
//					per.setDirectoryC27(directory);
//					entityManager.merge(per);
//				}
//			} else if (dir_flag.equals("C28")) {
//				directory = per.getDirectoryC28();
//				if (null == directory) {
//					directory = __create(opt, DirectoryExtattr.RURAL, per, parent);
//					per.setDirectoryC28(directory);
//					entityManager.merge(per);
//				}
//			} else if (dir_flag.equals("C36")) {
//				directory = per.getDirectoryC36();
//				if (null == directory) {
//					directory = __create(opt, DirectoryExtattr.RURAL, per, parent);
//					per.setDirectoryC36(directory);
//					entityManager.merge(per);
//				}
//			}
//		} else if (area_child == Area.PROJECT.getId()) { // 项目
//			ProjectEntity project = __first(ProjectEntity.class, "from ProjectEntity where id=?", id);
//			if (dir_flag.equals("")) {
//				project.getDirectory();
//			}
//		}
//		List<DirectoryEntity> dir = new ArrayList<DirectoryEntity>();
//		dir.add(directory);
//		file.setDirectory(dir);
//		entityManager.persist(file);
//	}
}
