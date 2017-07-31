package cn.bonoon.core;

import java.util.List;

import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.RequireReportDocumentEntity;
import cn.bonoon.entities.RequireReportItemEntity;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.GenericService;

public interface RequireReportItemService extends GenericService<RequireReportItemEntity> {

	List<RequireReportItemEntity> get(IOperator opt);
	List<RequireReportItemEntity> getAdditional(IOperator opt);
	
	List<RequireReportItemEntity> getCreate(IOperator opt);
	List<RequireReportItemEntity> getAdditionalCreate(IOperator opt);
	
	void save_from_file(IOperator opt, Long item_id, Long[] file_ids) throws Exception;
	
	// 删除文档（权删除RequireReportDocumentEntity，FileEntity仍然存在）
	void delete(IOperator opt, Long[] document_ids);
	
	List<RequireReportDocumentEntity> get_by_item(Long item_id);

	void save_upload(IOperator opt, Long item_id, FileEntity file);
	
	RequireReportDocumentEntity get_document(Long id);
	
	List<RequireReportItemEntity> get_ended(Long id); // 可补报的记录（已结束、正常状态）
	
	/**
	 * 所有还没有上报文档，并且省办设置了催报通知的
	 * @param user
	 * @return
	 */
	List<RequireReportItemEntity> getUrges(IOperator user);
	void submit(Long id);
	
	void signin(IOperator opt, Long id);
	void sign(Long id,IOperator user);
	void sendback(Long id,IOperator user);
}
