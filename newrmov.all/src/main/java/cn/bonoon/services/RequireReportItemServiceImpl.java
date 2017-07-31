package cn.bonoon.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.FileStatus;
import cn.bonoon.core.FileType;
import cn.bonoon.core.RequireDetailStatus;
import cn.bonoon.core.RequireReportItemService;
import cn.bonoon.entities.DirectoryEntity;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.RequireReportDocumentEntity;
import cn.bonoon.entities.RequireReportEntity;
import cn.bonoon.entities.RequireReportItemEntity;
import cn.bonoon.entities.logs.HandleLogEntity;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class RequireReportItemServiceImpl extends AbstractService<RequireReportItemEntity> implements RequireReportItemService {

	private List<RequireReportDocumentEntity> _get_doc_by_item(Long item_id) {
		return __list(RequireReportDocumentEntity.class, "from RequireReportDocumentEntity where requireReportItem.id=? and document is not null", item_id);
	}
	
//	private List<RequireReportItemEntity> _getRequireReportItem(Long id) {
//		String ql = "from RequireReportItemEntity x where x.deleted=false and x.requireReport.deleted=false and x.requireReport.id=?";
//		return __list(RequireReportItemEntity.class, ql, id);
//	}
	
//	private void _statistics_finishCount(RequireReportEntity requireReport) {
//		String ql = "select count(x),sum(x.documentCount) from RequireReportItemEntity x where x.deleted=false and x.requireReport.deleted=false and x.documentCount>0 and x.requireReport.id=?";
//		Object[] cous = __single(Object[].class, ql, requireReport.getId());
//		requireReport.setFinishCount(((Number)cous[0]).intValue());
//		Object obj = cous[1];
//		if(obj instanceof Number){
//			requireReport.setDocumentCount(((Number)obj).intValue());
//		}else{
//			requireReport.setDocumentCount(0);
//		}
//		entityManager.merge(requireReport);
//	}
	
	@Override
	@Transactional
	public void signin(IOperator opt, Long id) {
		RequireReportItemEntity rrie =__get(id);
		if(null == rrie || rrie.getStatus() != 0){
			throw new RuntimeException("数据出错，请联系管理人员！");
		}
		rrie.setStatus(2);
		entityManager.merge(rrie);
	}
	
	@Override
	@Transactional
	public void sign(Long id,IOperator user) {
		RequireReportItemEntity entity = __get(id);
		entity.setStatus(RequireDetailStatus.FINISH);
		entityManager.merge(entity);
		
		/***********记录文档推送管理的查收的操作*********/
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(entity.getId());
		hlog.setTargetType(RequireReportItemEntity.class.getName());
		hlog.setContent("查收了"+entity.getUnit().getName()+"文档");
		entityManager.persist(hlog);
	}
	
	@Override
	@Transactional
	public void sendback(Long id,IOperator user) {
		RequireReportItemEntity entity = __get(id);
		entity.setStatus(RequireDetailStatus.SIGN_UNCOM);
		entityManager.merge(entity);
		
		RequireReportEntity requireReport = entity.getRequireReport();
		String ql = "select count(x),sum(x.documentCount) from RequireReportItemEntity x where x.deleted=false and x.requireReport.deleted=false and x.documentCount>0 and x.status in(1,3) and x.requireReport.id=?";
		Object[] cous = __single(Object[].class, ql, requireReport.getId());
		requireReport.setFinishCount(((Number)cous[0]).intValue());
		Object obj = cous[1];
		if(obj instanceof Number){
			requireReport.setDocumentCount(((Number)obj).intValue());
		}else{
			requireReport.setDocumentCount(0);
		}

		entityManager.merge(requireReport);
		
		/****记录文档推送管理的退回的操作*****/
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(requireReport.getId());
		hlog.setTargetType(RequireReportEntity.class.getName());
		hlog.setContent("退回"+entity.getUnit().getName()+"文档");
		entityManager.persist(hlog);
	}
	
	@Override
	@Transactional
	public void save_from_file(IOperator opt, Long item_id, Long[] file_ids) throws Exception {
		if (null == file_ids || file_ids.length < 1)
			return;
		// 检验时间
		RequireReportItemEntity item = __get(item_id);
//		RequireReportEntity requireReport = item.getRequireReport();
		Date current = new Date();
//		if (!(current.after(requireReport.getStartReportedAt()) && current.before(requireReport.getEndReportedAt())) && item.getStatus() == 0) {
//			throw new Exception("请在规定的时间内操作！");
//		}
		// 文件是否重复添加
		List<RequireReportDocumentEntity> docs = _get_doc_by_item(item_id);
		if (null != docs && docs.size() != 0) {
			String error = "";
			List<Long> exsit = Arrays.asList(file_ids);
			for (RequireReportDocumentEntity doc : docs) {
				FileEntity document = doc.getDocument();
				if (exsit.remove(document.getId())) {
					error += "<br/>" + document.getName();
				}
			}
			if (!error.isEmpty())
				throw new Exception("以下文件已添加，请匆重复添加！<br/>" + error);
		}
		// 保存
		for (Long file_id : file_ids) {
			FileEntity file = entityManager.find(FileEntity.class, file_id);
			RequireReportDocumentEntity doc = new RequireReportDocumentEntity();
			doc.setDocument(file);
			doc.setRequireReportItem(item);
			doc.setOperateAt(current);
			doc.setOperator(opt.getUsername());
			entityManager.persist(doc);

			item.setDocumentCount(item.getDocumentCount() + 1);
			entityManager.merge(item);
		}
//		_statistics_finishCount(requireReport);
	}

	@Override
	public List<RequireReportItemEntity> get(IOperator opt) {
		Date current = new Date();
		String hql = "select x from RequireReportItemEntity x where x.status in(0,2) and x.deleted=0 and x.unit.id=? and x.requireReport.deleted=0 "
				+ "and x.requireReport.startReportedAt<=? and x.requireReport.endReportedAt>=? and x.requireReport.status=0 and x.requireReport.statusIssue=1";
		return __list(RequireReportItemEntity.class, hql, opt.getOwnerId(), current, current);
	}
	
	@Override
	public List<RequireReportItemEntity> getAdditional(IOperator opt) {
		String hql = "select x from RequireReportItemEntity x where x.requireReport.deleted=0 and x.status=4 and x.deleted=0 and x.unit.id=?";
		return __list(RequireReportItemEntity.class, hql, opt.getOwnerId());
	}

	@Override
	@Transactional
	public void delete(IOperator opt, Long[] document_ids) {
		if (null == document_ids || document_ids.length < 1) {
			return;
		}
//		RequireReportEntity requireReport = null;
		for (Long id : document_ids) {
			RequireReportDocumentEntity document = entityManager.find(RequireReportDocumentEntity.class, id);//__first(RequireReportDocumentEntity.class, "from RequireReportDocumentEntity where id=?", id);
			if (null != document) {
				RequireReportItemEntity item = document.getRequireReportItem();
//				requireReport = item.getRequireReport();
				entityManager.remove(document);
				item.setDocumentCount(item.getDocumentCount() - 1);
				entityManager.merge(item);
			}
		}
//		_statistics_finishCount(requireReport);
	}

	@Override
	public List<RequireReportDocumentEntity> get_by_item(Long item_id) {
		return _get_doc_by_item(item_id);
	}

	@Override
	@Transactional
	public void save_upload(IOperator opt, Long item_id, FileEntity file) {
		Date current = new Date();
		RequireReportItemEntity item = __get(item_id);
		file.setCreateAt(current);
		file.setCreatorId(opt.getId());
		file.setCreatorName(opt.getUsername());
		file.setOwnerId(opt.getOwnerId());
		file.setStatus(FileStatus.STATUS_FORBID);
		file.setType(FileType.DOC);
		file.setVersion(1);
		DirectoryEntity dir = item.getRequireReport().getDirectory();
		if(null != dir){
			file.setDirectory(new ArrayList<DirectoryEntity>());
			file.getDirectory().add(dir);
		}
		entityManager.persist(file);

		RequireReportDocumentEntity document = new RequireReportDocumentEntity();
		document.setRequireReportItem(item);
		document.setDocument(file);
		document.setOperateAt(current);
		document.setOperator(opt.getUsername());
		entityManager.persist(document);
		item.setDocumentCount(item.getDocumentCount() + 1);
		entityManager.merge(item);
//		_statistics_finishCount(item.getRequireReport());
	}

	@Override
	public RequireReportDocumentEntity get_document(Long id) {
		return entityManager.find(RequireReportDocumentEntity.class, id);
	}

	@Override
	public List<RequireReportItemEntity> get_ended(Long id) {
		return __list(RequireReportItemEntity.class, "select x from RequireReportItemEntity where requireReport.deleted=0 and deleted=0 and status=0 and requireReport.id=?", id);
	}

	@Override
	public List<RequireReportItemEntity> getCreate(IOperator opt) {
		Date current = new Date();
		String hql = "select x from RequireReportItemEntity x where x.requireReport.deleted=0 and x.status=0 and x.deleted=0 and x.requireReport.creatorId=? " +
				"and x.requireReport.startReportedAt<=? and x.requireReport.endReportedAt>=?";
		return __list(RequireReportItemEntity.class, hql, opt.getId(), current, current);
	}

	@Override
	public List<RequireReportItemEntity> getAdditionalCreate(IOperator opt) {
		String hql="select x from RequireReportItemEntity x where x.requireReport.deleted=0 and and x.status=1 and x.deleted=0 and x.requireReport.creatorId=?  ";
		return __list(RequireReportItemEntity.class, hql, opt.getId());
	}

	@Override        //select x from RequireReportItemEntity x where x.unit.id=? and x.documentCount=0 and x.urge>0 and x.deleted=false and x.requireReport.status=0 and x.requireReport.deleted=false
	public List<RequireReportItemEntity> getUrges(IOperator user) {
		String ql = "select x from RequireReportItemEntity x where x.unit.id=? and  x.urge>0 and x.deleted=false and x.requireReport.status=0 and x.requireReport.deleted=false and x.requireReport.statusIssue=1";
		return __list(RequireReportItemEntity.class, ql, user.getOwnerId());
	}

	@Override
	@Transactional
	public void submit(Long id) {
		RequireReportItemEntity entity = __get(id);
		if(entity.getDocumentCount() <= 0){
			throw new RuntimeException("请先上传文档，才允许提交！");
		}
		entity.setStatus(1);
		entity.setUrge(0);
		entity.setSubmitAt(new Date());
		entityManager.merge(entity);
		
		RequireReportEntity requireReport = entity.getRequireReport();
		String ql = "select count(x),sum(x.documentCount) from RequireReportItemEntity x where x.deleted=false and x.requireReport.deleted=false and x.documentCount>0 and x.status in(1,3) and x.requireReport.id=?";
		Object[] cous = __single(Object[].class, ql, requireReport.getId());
		requireReport.setFinishCount(((Number)cous[0]).intValue());
		Object obj = cous[1];
		if(obj instanceof Number){
			requireReport.setDocumentCount(((Number)obj).intValue());
		}else{
			requireReport.setDocumentCount(0);
		}
		entityManager.merge(requireReport);
	}
}
