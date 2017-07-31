package cn.bonoon.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.DirectoryService;
import cn.bonoon.entities.DirectoryEntity;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class DirectoryServiceImpl extends AbstractService<DirectoryEntity> implements DirectoryService {

//	@Override
//	@Transactional
//	public void recalculate(IOperator opt) {
//		if (!(opt.isSuper() || opt.isAdmin()))
//			throw new RuntimeException("只有管理员才能进行对所有目录重新计算的功能!");
//
//		String ql = "update DirectoryEntity x set x.length=(select sum(length) from FileEntity where directory.id=x.id), "
//				+ "x.count=(select count(*) from FileEntity where directory.id=x.id), "
//				+ "x.size=(select count(*) from DirectoryEntity where parent.id=x.id)";
//		entityManager.createQuery(ql).executeUpdate();
//	}

//	@Override
//	public List<DirectoryEntity> share() {
//		return __list(DirectoryEntity.class, "from DirectoryEntity x where x.deleted=false and x.share=1");
//	}

//	@Override
//	public List<DirectoryEntity> personal(Long id) {
//		return __list(DirectoryEntity.class, "from DirectoryEntity x where x.deleted=false and x.share=0 and x.creatorId=?", id);
//	}

	@Override
	public List<DirectoryEntity> personalRoot(IOperator opt) {
		String ql = "select x from DirectoryEntity x where x.deleted=false and x.parent is null and x.ownerId=?";
		return __list(DirectoryEntity.class, ql, opt.getOwnerId());
	}

	@Override
	@Transactional
	public void move(IOperator opt, Long node_id, Long pnode_id) {
		DirectoryEntity entity = __get(node_id);
		DirectoryEntity parent = __get(pnode_id);
		entity.setParent(parent);
		entity.setUpdateAt(new Date());
		entity.setUpdaterId(opt.getId());
		entity.setUpdaterName(opt.getUsername());
		entityManager.merge(entity);
	}

	@Override
	@Transactional
	public void rename(IOperator opt, Long id, String name) {
		DirectoryEntity entity = __get(id);
		entity.setName(name);
		entity.setUpdateAt(new Date());
		entity.setUpdaterId(opt.getId());
		entity.setUpdaterName(opt.getUsername());
		entityManager.merge(entity);
	}

	@Override
	@Transactional
	public void append(IOperator opt, Long parent_id, String name, int maxCount, long maxLength, int share, int status, String remark) {
		DirectoryEntity parent = __get(parent_id);
		DirectoryEntity entity = new DirectoryEntity();
		entity.setParent(parent);
		entity.setName(name);
		entity.setMaxCount(maxCount);
		entity.setMaxLength(maxLength);
		entity.setShare(share);
		entity.setStatus(status);
		entity.setRemark(remark);
		entity.setCreateAt(new Date());
		entity.setCreatorId(opt.getId());
		entity.setCreatorName(opt.getUsername());
		entity.setFile(new ArrayList<FileEntity>());
		entityManager.persist(entity);
	}

	@Override
	@Transactional
	public void update(IOperator opt, Long id, String name, int maxCount, long maxLength, int share, int status, String remark) {
		DirectoryEntity entity = __get(id);
		entity.setName(name);
		entity.setMaxCount(maxCount);
		entity.setMaxLength(maxLength);
		entity.setShare(share);
		entity.setStatus(status);
		entity.setRemark(remark);
		entity.setUpdateAt(new Date());
		entity.setUpdaterId(opt.getId());
		entity.setUpdaterName(opt.getUsername());
		entityManager.merge(entity);
	}

	@Override
	public boolean isLeaf(DirectoryEntity entity) {
		List<DirectoryEntity> children = entity.getChildren();
		if (null == children || children.isEmpty()) {
			return true;
		}
		for (DirectoryEntity dir : children) {
			if (!dir.isDeleted()) {
				return false;
			}
		}
		return true;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		DirectoryEntity directory = __get(id);
		directory.setDeleted(true);
		entityManager.merge(directory);
	}

	

}
