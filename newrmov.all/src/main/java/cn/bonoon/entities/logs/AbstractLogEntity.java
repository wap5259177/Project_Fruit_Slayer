package cn.bonoon.entities.logs;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.entities.AbstractPersistable;

@MappedSuperclass
public abstract class AbstractLogEntity extends AbstractPersistable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3126169300386170590L;

	/**
	 * 记录下执行了该操作的用户的名（用户的登录名）。
	 */
	@Column(name = "C_USERNAME")
	private String userName;

	/**
	 * 记录下执行了该操作的用户的ID，操作日志里不要跟用户在实体上有任何的关联关系， 只里在这里存放一个用户的ID，可以指向某一个用户
	 */
	@Column(name = "C_CREATORID")
	private Long creatorId;

	/**
	 * 日志记录的时间，一定要有。
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "C_CREATEAT", nullable = false)
	private Date createAt;

	/**
	 * 记录下的操作内容
	 */
	@Column(name = "C_CONTENT")
	private String content;
	
	@Column(name = "C_OWNERID")
	private long ownerId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	/**
	 * 
	 * @param opt
	 * 对AbstractLogEntity 实体对象 4个属性进行赋值:
	 * 			ownerId
	 * 			creatorId
	 * 			userName				
	 * 赋值为操作者IOperator对应的属性
	 * 			createAt赋值为当前时间
	 * 
	 */
	public void currentUser(IOperator opt){
		ownerId = opt.toOwnerId();
		creatorId = opt.getId();
		userName = opt.getUsername();
		createAt = new Date();
	}
}
