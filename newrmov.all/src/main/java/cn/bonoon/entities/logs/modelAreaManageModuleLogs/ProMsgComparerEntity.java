package cn.bonoon.entities.logs.modelAreaManageModuleLogs;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.bonoon.entities.logs.InvestmentComparer;
import cn.bonoon.entities.logs.SuperComparer;
/**
 * 
 * 
 * @author wsf
 * @creation 2016-12-7 
 *
 */
@Entity
@Table(name = "cp_pmc", catalog = "db_newrmov_log")
public class ProMsgComparerEntity extends SuperComparer{

	/**
	 * 
	 */
	private static final long serialVersionUID = -637012672834406892L;
	
	
	//投工数Old
		@Column(name = "S_LC")
		private int labourCountOld;
		//投工数Now
		@Column(name = "T_LC")
		private int labourCountNow;
		
		//折资额Old
		@Column(name = "S_S")
		private double spendOld;
		//折资额Now
			@Column(name = "T_S")
			private double spendNow;
		@Embedded
		InvestmentComparer invest=new InvestmentComparer();
		public int getLabourCountOld() {
			return labourCountOld;
		}
		public void setLabourCountOld(int labourCountOld) {
			this.labourCountOld = labourCountOld;
		}
	
		public double getSpendOld() {
			return spendOld;
		}
		public void setSpendOld(double spendOld) {
			this.spendOld = spendOld;
		}
		public int getLabourCountNow() {
			return labourCountNow;
		}
		public void setLabourCountNow(int labourCountNow) {
			this.labourCountNow = labourCountNow;
		}
		public double getSpendNow() {
			return spendNow;
		}
		public void setSpendNow(double spendNow) {
			this.spendNow = spendNow;
		}
		public InvestmentComparer getInvest() {
			return invest;
		}
		public void setInvest(InvestmentComparer invest) {
			this.invest = invest;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		
		
	

}
