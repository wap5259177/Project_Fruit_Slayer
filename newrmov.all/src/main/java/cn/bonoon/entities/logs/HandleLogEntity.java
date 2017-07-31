package cn.bonoon.entities.logs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 处理日志，对数据的每一次处理，都需要记录下来详细的处理情况
 * 
 * @author jackson
 *
 */
@Entity
@Table(name = "log_hl", catalog = "db_newrmov_log")
public class HandleLogEntity extends AbstractLogEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 28731764850253869L;

	@Column(name = "C_TARGETTYPE")
	private String targetType;
	
	//如果值为0，则表示针对这一类型的所有记录；不针对具体一个类型
	@Column(name = "C_TARGETID")
	private long targetId;

	public long getTargetId() {
		return targetId;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

}
