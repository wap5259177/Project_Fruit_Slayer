package cn.bonoon.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import cn.bonoon.entities.plugins.AccountEntity;
import cn.bonoon.kernel.support.entities.AbstractEntity;


@Entity
@Table(name = "t_sugroup")
public class ShareUserGroupEntity extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -135213895933333956L;

	@ManyToMany
	@JoinTable(name="rt_sug_account",
		joinColumns= @JoinColumn(name="SUG_ID", referencedColumnName="C_ID"),//joinColums指定中间表与city表的外键关系
        inverseJoinColumns= @JoinColumn(name="ACC_ID", referencedColumnName="C_ID")//inverseJoinColumns定义了中间表与另一端（province）的外键关系
	)
	private List<AccountEntity> accounts;

	
	public List<AccountEntity> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountEntity> accounts) {
		this.accounts = accounts;
	}
}
