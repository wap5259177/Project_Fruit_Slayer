package cn.bonoon.core;

import java.util.Date;
import java.util.List;

import cn.bonoon.entities.DirectoryEntity;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.FileHistoryEntity;
import cn.bonoon.entities.FileShareEntity;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.GenericService;

public interface FileService extends GenericService<FileEntity> {

	List<FileEntity> getFile(IOperator opt);

	// 目录下的文件
	List<FileEntity> getFile(Long directory_id);

	void delete(IOperator opt, Long[] ids, Long node_id);
	
	void completelyDelete(IOperator opt, Long[] ids);

	FileShareEntity fileShare(Long fid, Long uid);

	Long getActions(Long fid, Long uid);

	void saveActions(IOperator opt, Long fid, String actions);

	List<FileHistoryEntity> getFileHistory(Long fid);

	void updateFileVersion(IOperator opt, Long id, String name, String extendedAttributes, 
			Integer status, String remark, FileType type, Date issueAt, 
			String ext, long length, String mapPath);

	void fileOperator(IOperator opt, Long[] ids, Long type, Long source_node_id, Long target_node_id);
	
	// 没什么用~
//	List<FileEntity> getAreaFile(Long doc_dir_id, Long img_dir_id);
	
	void delete(IOperator opt, Long[] ids); // 片区的文件上传功能使用这种删除方式

	List<FileShareEntity> getShareFiles(IOperator opt);
	List<FileEntity> getShareFiles(Long did,LogonUser user);

	List<DirectoryEntity> getShareDirectories(IOperator user);
	List<DirectoryEntity> getShareDirectories(Long did);

	void saveGroupActions(IOperator opt, Long fid, String actions);

	Long getGroupActions(Long fid, Long uid);

	
//	void save_area_file(IOperator opt, FileEntity file, Long area_child, Long id, String dir_flag);
}
