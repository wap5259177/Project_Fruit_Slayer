package cn.bonoon.entities.informatioin;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import cn.bonoon.kernel.support.entities.AbstractPersistable;
import cn.bonoon.kernel.support.entities.EntityDeletable;

/**
 * 自然村情况表，统计县的所有县的情况
 * 
 * @author jackson
 *
 */
@MappedSuperclass
public class NaturalVillageInformationEntity extends AbstractPersistable implements EntityDeletable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7344860313786762578L;
	/**
	 * 如果是省级的，则是省级填空的内容，如果是市或县的，这里是市或县的名字
	 */
	@Column(name = "C_NAME")
	private String name;
	/**
	 * 农业户数
	 */
	@Column(name = "C_HOUSEHOLDCOUNT")
	private int householdCount;
	/**
	 * 农业人口数
	 */
	@Column(name = "C_PEOPLECOUNT")
	private int peopleCount;
	
	/**
	 * 建制行政村或居委会的个数
	 */
	@Column(name = "C_AVCOUNT")
	private int avCount;
	/**
	 * 20户以上自然村
	 */
	@Column(name = "C_HOUSEHOLD0")
	private int household0;
	/**
	 * 20-100户自然村
	 */
	@Column(name = "C_HOUSEHOLD1")
	private int household1;
	/**
	 * 100-300户自然村
	 */
	@Column(name = "C_HOUSEHOLD2")
	private int household2;
	/**
	 * 300-500户自然村
	 */
	@Column(name = "C_HOUSEHOLD3")
	private int household3;
	/**
	 * 500户以上
	 */
	@Column(name = "C_HOUSEHOLD4")
	private int household4;

	@Column(name = "C_DELETED")
	private boolean deleted;

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHouseholdCount() {
		return householdCount;
	}

	public void setHouseholdCount(int householdCount) {
		this.householdCount = householdCount;
	}

	public int getPeopleCount() {
		return peopleCount;
	}

	public void setPeopleCount(int peopleCount) {
		this.peopleCount = peopleCount;
	}

	public int getAvCount() {
		return avCount;
	}

	public void setAvCount(int avCount) {
		this.avCount = avCount;
	}

	public int getHousehold0() {
		return household0;
	}

	public void setHousehold0(int household0) {
		this.household0 = household0;
	}

	public int getHousehold1() {
		return household1;
	}

	public void setHousehold1(int household1) {
		this.household1 = household1;
	}

	public int getHousehold2() {
		return household2;
	}

	public void setHousehold2(int household2) {
		this.household2 = household2;
	}

	public int getHousehold3() {
		return household3;
	}

	public void setHousehold3(int household3) {
		this.household3 = household3;
	}

	public int getHousehold4() {
		return household4;	
	}

	public void setHousehold4(int household4) {
		this.household4 = household4;
	}

}
