package cn.bonoon.entities.logs.modelAreaManageModuleLogs;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;


import cn.bonoon.entities.logs.InvestmentComparer;
import cn.bonoon.entities.logs.SuperComparer;

/**
 *  对片区资金改变作记录的一个实体
 * @author wsf
 * @creation 2016-12-12 
 *
 */
@Entity
@Table(name = "cp_maic", catalog = "db_newrmov_log")
public class ModelAreaInvComparerEntity extends SuperComparer{
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7625609394856539972L;
	@Embedded
	InvestmentComparer invest=new InvestmentComparer();

	public InvestmentComparer getInvest() {
		return invest;
	}

	public void setInvest(InvestmentComparer invest) {
		this.invest = invest;
	}
}
