package cn.bonoon.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cn.bonoon.kernel.support.entities.EntityTreeMovable;

/**
 * <pre>
 * 目录，每个用户可以拥有自己的一个目录结构，如果不属于任何一个用户，则认为是系统目录。
 * 目录的状态有三种，共享、私有和好友共享，共享的目录其它人可以看到
 * </pre>
 * 
 * @author jackson
 * 
 */
@Entity
@Table(name = "t_directory")
public class DirectoryEntity extends AbstractFileEntity implements EntityTreeMovable<DirectoryEntity> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8628852581526364162L;

	@ManyToOne
	@JoinColumn(name = "R_PARENT_ID")
	private DirectoryEntity parent;

	@OneToMany(mappedBy = "parent")
	private List<DirectoryEntity> children;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "directory")
	private List<FileEntity> file;

	/**
	 * 子目录的数量
	 */
	@Column(name = "C_SIZE")
	private int size;

	/**
	 * 虚拟的目录结构的路径 不需要后缀的文件名（文件名或文件夹名）
	 */
	@Column(name = "C_FULLNAME")
	private String fullName;

	/**
	 * 允许使用的最大空间，如果为0则表示不限制
	 */
	@Column(name = "C_MAXLENGTH")
	private long maxLength;
	/**
	 * <pre>
	 * 限制目录的文件数
	 * </pre>
	 */
	@Column(name = "C_MAXCOUNT")
	private int maxCount;

	/**
	 * 目录下的文件数量
	 */
	@Column(name = "C_COUNT")
	private int count;

	/**
	 * 是否共享,对共享的定义 0.私有 1.共享
	 */
	@Column(name = "C_SHARE")
	private int share;

	/**
	 * <pre>
	 * 读取的方式,默认是只读取属于本目录下的所有FileEntity信息.
	 * 定义:
	 * 1.只读取FileEntity信息.
	 * 2.只读取path指定的目录下的文件及子目录下的文件,如果为null或""则什么都不读取.
	 * 3.以上两个都读取
	 * </pre>
	 */
	@Column(name = "C_READTYPE")
	private int readType = 0;

	@Column(name = "C_XPATH")
	private String xpath;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public DirectoryEntity getParent() {
		return parent;
	}

	public void setParent(DirectoryEntity parent) {
		this.parent = parent;
	}

	public List<DirectoryEntity> getChildren() {
		return children;
	}

	public void setChildren(List<DirectoryEntity> children) {
		this.children = children;
	}

	public void setShare(int share) {
		this.share = share;
	}

	public int getShare() {
		return share;
	}

	public void setReadType(int readType) {
		this.readType = readType;
	}

	public int getReadType() {
		return readType;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public List<FileEntity> getFile() {
		return file;
	}

	public void setFile(List<FileEntity> file) {
		this.file = file;
	}

	public long getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(long maxLength) {
		this.maxLength = maxLength;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
