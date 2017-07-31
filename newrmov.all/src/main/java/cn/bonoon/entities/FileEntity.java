package cn.bonoon.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.bonoon.core.FileType;

/**
 * <pre>
 * 文件实体。
 * 可以用来保存上传到系统的文件信息，一般只有比较重要的文件才使用。
 * 
 * <font color='red'>
 * 注意:其它实体如果需要使用文件的,可以使用一对多或多对多的关联关系单向关联，
 * 不管是一对多还是多对多，都需要生成中间的关系表。这样如果关联多的话，才不会
 * 在文件表里添加过多的字段。
 * </font>
 * </pre>
 * 
 * @author jackson
 * 
 */
@Entity
@Table(name = "t_file")
public class FileEntity extends AbstractFileEntity implements Comparable<FileEntity> {

	private static final long serialVersionUID = -1976902043525620843L;

	/**
	 * 文件的扩展名
	 */
	@Column(name = "C_EXT")
	private String ext;

	/**
	 * 所在的目录的ID
	 */
	// @ManyToOne(fetch = LAZY)
	// @JoinColumn(name = "R_DIRECTORY_ID")
	// private DirectoryEntity directory;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "RT_DIRECTORY2FILE", 
	joinColumns = @JoinColumn(name = "R_FILE_ID", referencedColumnName = "C_ID"), 
	inverseJoinColumns = @JoinColumn(name = "R_DIRECTORY_ID", referencedColumnName = "C_ID"))
	private List<DirectoryEntity> directory;

	/**
	 * 扩展属性
	 */
	@Column(name = "C_EXTATTR")
	private String extendedAttributes;

	/**
	 * <pre>
	 * 附件属于哪一个级别的,用户提交上来的附件应该属于默认级别,
	 * 如果是系统添加的,应该属于其它级别,具体级别的意义由实现的子类来定义
	 * </pre>
	 */
	@Column(name = "C_LEVEL")
	private int level = 0;

	/**
	 * 是否分享
	 */
	@Column(name = "C_SHARED")
	private boolean shared;

	@Column(name = "C_VERSION")
	private long version;

	/**
	 * 类型 0:文档 1:图片 2:音频 3:视频 4:其它
	 */
	@Enumerated
	@Column(name = "C_TYPE")
	private FileType type;

	/**
	 * 发布时间、录制时间等
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "C_ISSUEAT")
	private Date issueAt;

	@Column(name = "C_EXTATTR7")
	private String extattr7;
	@Column(name = "C_EXTATTR8")
	private String extattr8;
	@Column(name = "C_EXTATTR9")
	private String extattr9;
	@Column(name = "C_EXTATTR10")
	private String extattr10;

	@Column(name = "C_EXTERNALLINK")
	private boolean externalLink;
	
	public String getExtattr7() {
		return extattr7;
	}

	public void setExtattr7(String extattr7) {
		this.extattr7 = extattr7;
	}

	public String getExtattr8() {
		return extattr8;
	}

	public void setExtattr8(String extattr8) {
		this.extattr8 = extattr8;
	}

	public String getExtattr9() {
		return extattr9;
	}

	public void setExtattr9(String extattr9) {
		this.extattr9 = extattr9;
	}

	public String getExtattr10() {
		return extattr10;
	}

	public void setExtattr10(String extattr10) {
		this.extattr10 = extattr10;
	}

	public void setExtendedAttributes(String extendedAttributes) {
		this.extendedAttributes = extendedAttributes;
	}

	public String getExtendedAttributes() {
		return extendedAttributes;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public boolean isShared() {
		return shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public List<DirectoryEntity> getDirectory() {
		return directory;
	}

	public void setDirectory(List<DirectoryEntity> directory) {
		this.directory = directory;
	}

	public FileType getType() {
		return type;
	}

	public void setType(FileType type) {
		this.type = type;
	}

	public Date getIssueAt() {
		return issueAt;
	}

	public void setIssueAt(Date issueAt) {
		this.issueAt = issueAt;
	}

	public boolean isExternalLink() {
		return externalLink;
	}

	public void setExternalLink(boolean externalLink) {
		this.externalLink = externalLink;
	}

	@Override
	public int compareTo(FileEntity o) {
		if(this.getExtattr1() > o.getExtattr1())
			return 1;
		else if(this.getExtattr1() < o.getExtattr1())
			return -1;
		return 0;
	}

}
