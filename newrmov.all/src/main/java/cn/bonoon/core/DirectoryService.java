package cn.bonoon.core;

import java.util.List;

import cn.bonoon.entities.DirectoryEntity;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.GenericService;

public interface DirectoryService extends GenericService<DirectoryEntity> {

	//void recalculate(IOperator opt);

//	/**
//	 * 取得所有公开的目录,如果该目录公开,则表示该目录下的所有子目录及文件都公开
//	 * 
//	 * @return
//	 */
//	List<DirectoryEntity> share();

	/**
	 * 个人目录
	 * 
	 * @param id
	 *            用户的ID
	 * @param readShare
	 *            是否读取共享的或公开的文件夹
	 * @return
	 */
	//List<DirectoryEntity> personal(Long id);

	List<DirectoryEntity> personalRoot(IOperator opt);

	void append(IOperator opt, Long parent_id, String name, int maxCount, long maxLength, int share, int status, String remark);
	
	void update(IOperator opt, Long id, String name, int maxCount, long maxLength, int share, int status, String remark);
	
	void move(IOperator opt, Long node_id, Long pnode_id);
	
	void rename(IOperator opt, Long id, String name);
	
	boolean isLeaf(DirectoryEntity entity);
	
	void delete(Long id);
	


}
