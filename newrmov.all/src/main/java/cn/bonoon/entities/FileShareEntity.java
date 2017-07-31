package cn.bonoon.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.bonoon.entities.plugins.AccountEntity;
import cn.bonoon.kernel.support.entities.AbstractPersistable;

/**
 * 文件分享
 * 
 * @author ocean~
 */
@Entity
@Table(name = "t_fileshare")
public class FileShareEntity extends AbstractPersistable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3821538704167216053L;

	@ManyToOne
	@JoinColumn(name = "R_FILE_ID")
	private FileEntity file;

	@ManyToOne
	@JoinColumn(name = "R_ACCOUNT_ID")
	private AccountEntity account;

	/**
	 * 操作权限：1查看、2下载、4修改、8删除
	 */
	@Column(name = "C_ACTIONS")
	private long actions;

	@Column(name = "C_CREATORID")
	private Long creatorId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "C_CREATEAT", nullable = false)
	private Date createAt;//共享时间

	public FileEntity getFile() {
		return file;
	}

	public void setFile(FileEntity file) {
		this.file = file;
	}

	public AccountEntity getAccount() {
		return account;
	}

	public void setAccount(AccountEntity account) {
		this.account = account;
	}

	public long getActions() {
		return actions;
	}

	public void setActions(long actions) {
		this.actions = actions;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

}
