package cn.bonoon.entities.logs;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import cn.bonoon.entities.logs.HandleLogEntity;
import cn.bonoon.kernel.support.entities.AbstractPersistable;
/**
 *  处理以片区为单位的可继承本类
 * 
 * @author wsf
 * @creation 2016-12-7 
 *
 */
@MappedSuperclass
public class SuperModelAreaComparer extends SuperComparer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3554697918932144732L;
	//表示某一次的对比处理过程
	
		//当前对比的片区的Id，所有对比都是以一个片区为单位进行的，一个片区下有多个村，每个村下面有多个季度的报表
		@Column(name = "C_MAID")
		private long maid;
		
		public void setMSG(HandleLogEntity log,long maid,String name,String targetType,long targetId){
			this.setLog(log);
			this.setMaid(maid);
			this.setName(name);
			this.setTargetType(targetType);
			this.setTargetId(targetId);			
			
		}
		
		

		public long getMaid() {
			return maid;
		}

		public void setMaid(long maid) {
			this.maid = maid;
		}

	
	


		public static long getSerialversionuid() {
			return serialVersionUID;
		}

	
}
