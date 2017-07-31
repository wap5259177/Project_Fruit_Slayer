package cn.bonoon.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.bonoon.core.FileStatus;
import cn.bonoon.core.FileType;
import cn.bonoon.core.IRequireReportEditor;
import cn.bonoon.core.RequireDetailStatus;
import cn.bonoon.core.RequireReportService;
import cn.bonoon.core.plugins.PlaceService;
import cn.bonoon.entities.DirectoryEntity;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.RequireReportDocumentEntity;
import cn.bonoon.entities.RequireReportEntity;
import cn.bonoon.entities.RequireReportItemEntity;
import cn.bonoon.entities.UnitEntity;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.io.DirectoryStrategy;
import cn.bonoon.kernel.io.FileInfo;
import cn.bonoon.kernel.io.FileManager;
import cn.bonoon.kernel.io.FilenameStrategy;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.AbstractService;
import cn.bonoon.kernel.util.StringHelper;

@Service
@Transactional(readOnly = true)
public class RequireReportServiceImpl extends AbstractService<RequireReportEntity> implements RequireReportService {

	
	private UnitEntity _getUnit(Long id) {
		return __first(UnitEntity.class, "from UnitEntity where id=?", id);
	}

	/*
	 * 查询requireReportItem  通过requireReport id
	 */
	private List<RequireReportItemEntity> _getRequireReportItem(Long id) {
		String ql = "from RequireReportItemEntity x where x.deleted=false and x.requireReport.deleted=false and x.requireReport.id=?";
		return __list(RequireReportItemEntity.class, ql, id);
	}

	/*
	 * 查询要求上报记录,统计类型,开始上投时间,结束上投时间
	 * 
	 */
	private List<Object[]> _statistics(Long time_type, Date start, Date end) {
		//名称/级别/文档数
		String ql = "select  x.requireReport.name, x.unit.place.level, x.documentCount from RequireReportItemEntity x where x.requireReport.";
		if(null != time_type && time_type == 0){
			ql += "startReportedAt";
		}else{
			ql += "endReportedAt";
		}
		ql += " between ? and ? and x.deleted=false and x.requireReport.deleted=false and x.unit.place.level in(" + 
				PlaceService.LEVEL_CITY   + "," + 
				PlaceService.LEVEL_COUNTY + "," + 
				PlaceService.LEVEL_TOWN   + ")";
		return __list(Object[].class, ql, start, end);
	}
	
	@Override
	public List<FileEntity> itemFiles(Long iid) {
		String ql = "select x.document from RequireReportDocumentEntity x where x.requireReportItem.id=?";
		return __list(FileEntity.class, ql, iid);
	}

	@Override
	protected RequireReportEntity __save(OperateEvent event, RequireReportEntity entity) {
		super.__save(event, entity);
		String[] arr_unit = event.getStringArray("unit");
		if (null != arr_unit && arr_unit.length != 0) {
			for (int i = 0, len = arr_unit.length; i < len; i++) {
				RequireReportItemEntity item = new RequireReportItemEntity();
				item.setRequireReport(entity);
				item.setUnit(_getUnit(Long.parseLong(arr_unit[i])));
				entityManager.persist(item);
			}
			entity.setItemCount(arr_unit.length);
		}
		saveFile(event, entity);
		entityManager.merge(entity);
		return entity;
	}

	@Override
	protected RequireReportEntity __update(OperateEvent event, RequireReportEntity entity) {
		String[] arr_unit = event.getStringArray("unit");
		List<Long> unit_ids = new ArrayList<Long>();
		if (null != arr_unit && arr_unit.length != 0) {
			for (String unit_id : arr_unit) {
				unit_ids.add(Long.parseLong(unit_id));
			}
			entity.setItemCount(arr_unit.length);
			entityManager.merge(entity);
		}
		List<RequireReportItemEntity> items = __list(RequireReportItemEntity.class, "from RequireReportItemEntity where requireReport.id=?", entity.getId());
		for (RequireReportItemEntity item : items) {
			Long unit_id = item.getUnit().getId();
			if (unit_ids.contains(unit_id)) {
				if (item.isDeleted()) {
					item.setDeleted(false);
					entityManager.merge(item);
				}
				unit_ids.remove(unit_id);
			} else {
				if (!item.isDeleted()) {
					item.setDeleted(true);
					entityManager.merge(item);
				}
			}
		}
		for (Long unit_id : unit_ids) {
			RequireReportItemEntity item = new RequireReportItemEntity();
			item.setUnit(_getUnit(unit_id));
			item.setRequireReport(entity);
			entityManager.persist(item);
		}
		saveFile(event, entity);
		return super.__update(event, entity);
	}
	
//	@Override
//	protected boolean onSave(OperateEvent event, RequireReportEntity entity) {
//		saveFile(event, entity);
//		return super.onSave(event, entity);
//	}
//	
//	@Override
//	protected boolean onUpdate(OperateEvent event, RequireReportEntity entity) {
//		saveFile(event, entity);
//		return super.onUpdate(event, entity);
//	}
	
//	private DirectoryEntity __directory(RequireReportEntity entity){
//		DirectoryEntity dir = entity.getDirectory();
//		if(null == dir){
//			
//		}
//		return dir;
//	}
	
	@Autowired
	private FileManager fileManager;
	private void saveFile(OperateEvent event, RequireReportEntity entity){
		IRequireReportEditor rre = (IRequireReportEditor)event.getSource();
		DirectoryEntity dir = __syncDirectory(entity, event, event.now());
		if(null != rre.getDeleteOldAnnex() && rre.getDeleteOldAnnex()){
			entity.setAnnex("");
			entity.setAnnexName("");
			FileEntity fe = entity.getAnnexFile();
			if(null != fe){
				fe.setDeleted(true);
				fe.setStatus(FileStatus.STATUS_DELETED);
				entityManager.merge(fe);
				entity.setAnnexFile(null);
			}
		}else{ 
			MultipartFile ra = rre.getReportAnnex();
			if(null != ra && !ra.isEmpty()){
				try {
					FileInfo fi = fileManager.save(ROOT_PATH + entity.getId(), DirectoryStrategy.YEAR_MONTH, FilenameStrategy.MD5, rre.getReportAnnex());
					entity.setAnnex(fi.getRelativePath());
					entity.setAnnexName(fi.getOriginalFilename());

					FileEntity fe = entity.getAnnexFile();
					if(null == fe){
						fe = new FileEntity();
						fe.setCreateAt(event.now());
						fe.setCreatorId(event.getId());
						fe.setCreatorName(event.getUsername());
						fe.setOwnerId(event.getOwnerId());
						fe.setStatus(FileStatus.STATUS_FORBID);
						fe.setVersion(1);
						fe.setDirectory(new ArrayList<DirectoryEntity>());
						fe.getDirectory().add(dir);
						fe.setType(FileType.DOC);
						
						fe.setName(fi.getOriginalFilename());
						fe.setMapPath(fi.getRelativePath());
						fe.setLength(fi.getSize());
						fe.setExt(fi.getFilesuffix());
						entityManager.persist(fe);
					}else{
						fe.setName(fi.getOriginalFilename());
						fe.setMapPath(fi.getRelativePath());
						fe.setLength(fi.getSize());
						fe.setExt(fi.getFilesuffix());
						entityManager.merge(fe);
					}
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void __delete(OperateEvent event, RequireReportEntity entity) {
		List<RequireReportItemEntity> items = _getRequireReportItem(entity.getId());
		for (RequireReportItemEntity item : items) {
			item.setDeleted(true);
			entityManager.merge(item);
		}
		DirectoryEntity dir = entity.getDirectory();
		if(null != dir){
			dir.setStatus(FileStatus.STATUS_DELETED);
			dir.setDeleted(true);
			for(FileEntity fe : dir.getFile()){
				fe.setStatus(FileStatus.STATUS_DELETED);
				fe.setDeleted(true);
				entityManager.merge(fe);
			}
			entityManager.merge(dir);
		}
		super.__delete(event, entity);
	}

	@Override
	public List<UnitEntity> findCity() {
		return __list(UnitEntity.class, "from UnitEntity x where x.deleted=0 and x.place.level=2");
	}

	@Override
	public List<UnitEntity> findCounty() {
		return __list(UnitEntity.class, "from UnitEntity x where x.deleted=0 and x.place.level=3");
	}

	@Override
	public List<RequireReportItemEntity> findItem(Long id) {
		return _getRequireReportItem(id);
	}

	@Override
	public List<Long> findItemPlaceIds(Long id) {
		return __list(Long.class, "select x.unit.id from RequireReportItemEntity x where x.deleted=false and x.requireReport.deleted=false and x.requireReport.id=?", id);
	}

	/*
	 *通过时间类型,和年份查询出要求上报的记录
	 */
	@Override
	public List<Object[]> statistics_year(Long time_type, int year) {
		Calendar start_cal = Calendar.getInstance();
		Calendar end_cal = Calendar.getInstance();
		start_cal.set(year, 0, 1);
		end_cal.set(year, 11, end_cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return _statistics(time_type, start_cal.getTime(), end_cal.getTime());
	}

	@Override
	public List<Object[]> statistics_season(Long time_type, int year, int season) {
		Calendar start_cal = Calendar.getInstance();
		Calendar end_cal = Calendar.getInstance();
		if (season == 1) {
			start_cal.set(year, 0, 1);
			end_cal.set(year, 2, end_cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		} else if (season == 2) {
			start_cal.set(year, 3, 1);
			end_cal.set(year, 5, end_cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		} else if (season == 3) {
			start_cal.set(year, 6, 1);
			end_cal.set(year, 8, end_cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		} else if (season == 4) {
			start_cal.set(year, 9, 1);
			end_cal.set(year, 11, end_cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		}
		return _statistics(time_type, start_cal.getTime(), end_cal.getTime());
	}

	@Override
	public List<Object[]> statistics_month(Long time_type, int year, int month) {
		Calendar start_cal = Calendar.getInstance();
		Calendar end_cal = Calendar.getInstance();
		start_cal.set(year, month - 1, 1);
		end_cal.set(year, month - 1, end_cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return _statistics(time_type, start_cal.getTime(), end_cal.getTime());
	}
	
	@Override
	@Transactional
	public void issue(IOperator opt, Long id) {
		RequireReportEntity entity = __get(id);
		if(entity.getStatusIssue() == 1){
			//取消发布
			entity.setStatusIssue(0);
			entity.setIssueAt(null);
		}else{
			if(StringHelper.isEmpty(entity.getName())){
				throw new RuntimeException("请填写“报送通知”的名称");
			}
			if(StringHelper.isEmpty(entity.getOffices())){
				throw new RuntimeException("请填写“报送通知”的业务处室");
			}
			if(entity.getStartReportedAt() == null){
				throw new RuntimeException("请定义开始上报的时间");
			}
			if(entity.getEndReportedAt() == null){
				throw new RuntimeException("请定义结束上报的时间");
			}
			if(entity.getStartReportedAt().after(entity.getEndReportedAt())){
				throw new RuntimeException("开始上报的时间 '大于' 结束上报的时间");
			}
			
			String ql = "select count(x) from RequireReportItemEntity x where x.deleted=0 and x.requireReport.id=?";
			if(!__exsit(ql, entity.getId())){
				throw new RuntimeException("必须指定送报的单位部门！");
			}
			
			entity.setStatusIssue(1);
			Date now = new Date();
			entity.setIssueAt(now);
			__syncDirectory(entity, opt, now);
		}
		entityManager.merge(entity);
	}
	private final String ROOT_DIR_CODE = "ROOT_DIR_CODE";
	private final String ROOT_DIR_NAME = "报送通知";
	private DirectoryEntity __syncDirectory(RequireReportEntity entity, IOperator opt, Date now){
		DirectoryEntity dir = entity.getDirectory();
		if(null == dir){
			String ql = "select x from DirectoryEntity x where x.ownerId=? and x.deleted=false and x.parent is null and x.extattr1=1 and x.extattr2=1 and x.extattr5=?";
			
			DirectoryEntity parent = __first(DirectoryEntity.class, ql, opt.getOwnerId(), ROOT_DIR_CODE);
			if(null == parent){
				parent = new DirectoryEntity();
				parent.setCreateAt(now);
				parent.setCreatorId(opt.getId());
				parent.setCreatorName(opt.getUsername());
				parent.setOwnerId(opt.getOwnerId());
				parent.setShare(FileStatus.SHARE_PRIVATE);
				parent.setStatus(FileStatus.STATUS_FORBID);
				parent.setExtattr1(1);
				parent.setExtattr2(1);
				parent.setExtattr5(ROOT_DIR_CODE);
				parent.setName(ROOT_DIR_NAME);
				entityManager.persist(parent);
			}
			dir = new DirectoryEntity();
			dir.setCreateAt(now);
			dir.setCreatorId(opt.getId());
			dir.setCreatorName(opt.getUsername());
			dir.setOwnerId(opt.getOwnerId());
			dir.setShare(FileStatus.SHARE_PRIVATE);
			dir.setStatus(FileStatus.STATUS_FORBID);
			dir.setParent(parent);
			
			dir.setName(entity.getName());
			entityManager.persist(dir);
			
			entity.setDirectory(dir);
			entityManager.merge(entity);
		}else if(null != entity.getName() && !entity.getName().equals(dir.getName())){
			dir.setName(entity.getName());
			entityManager.merge(dir);//保存名称与通知名称一致
		}
		return dir;
	}
	
	@Override
	@Transactional
	public void finish(Long id) {
		String ql = "select x from RequireReportItemEntity x where x.deleted=false and x.requireReport.id=? ";
		List<RequireReportItemEntity> items = __list(RequireReportItemEntity.class, ql, id);
		RequireReportEntity entity = __get(id);
		if(entity.getStatusIssue() != 1){
			//非发布状态，不允许归档
			throw new RuntimeException("此“报送通知”未发布！");
		}
		Date now = new Date();
		if(entity.getEndReportedAt().after(now)){
			throw new RuntimeException("报送时间未结束，不允许归档！");
		}
		for(RequireReportItemEntity rit : items){
			if(rit.getStatus()==RequireDetailStatus.FINISH || rit.getStatus()==RequireDetailStatus.NOTFING){
				if(rit.getStatus() != RequireDetailStatus.FINISH){
					throw new RuntimeException("此“报送通知”有未查收，不允许归档！");
				}
			}
		}
		entity.setStatus(1);
		entity.setFinishAt(now);
		entityManager.merge(entity);
	}
	
	@Override
	@Transactional
	public void toback(Long id) {
		RequireReportEntity entity = __get(id);
		entity.setStatus(0);
		entityManager.merge(entity);
	}

	@Override
	@Transactional
	public void urge(Long[] ids) {
		String ql = "update RequireReportItemEntity set urge=urge+1 where id";
		if(ids.length == 1){
			ql += "=" + ids[0];
		}else{
			ql += " in(" + ids[0];
			for(int i = 1; i < ids.length; i++){
				ql += "," + ids[i];
			}
			ql += ")";
		}
		entityManager.createQuery(ql).executeUpdate();
	}

	@Override
	public String getName(Long id) {
		String ql = "select x.name from RequireReportEntity x where x.id=?";
		return __first(String.class, ql, id);
	}

	@Override
	public List<RequireReportDocumentEntity> getDocuments(Long id, Long[] fids) {
		if(fids == null || fids.length == 0){
			return Collections.emptyList();
		}
		String ql = "select x from RequireReportDocumentEntity x where x.requireReportItem.requireReport.id=? and x.document.id";
		if(fids.length == 1){
			ql += "=" + fids[0];
		}else{
			ql += " in(" + fids[0];
			for(int i = 1; i < fids.length; i++){
				ql += "," + fids[i];
			}
			ql += ")";
		}
		return __list(RequireReportDocumentEntity.class, ql, id);
	}
}
