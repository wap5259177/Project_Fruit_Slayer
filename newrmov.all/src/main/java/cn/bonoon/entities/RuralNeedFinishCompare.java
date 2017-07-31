package cn.bonoon.entities;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * 专门用来与{@link RuralNeedFinishInfo}进行对比的类
 * @author jackson
 *
 */
@Embeddable
public class RuralNeedFinishCompare {

	@Embedded
	@AttributeOverrides({
	       @AttributeOverride(name="needFinish1", column=@Column(name = "S_NF1")),
	       @AttributeOverride(name="needFinish2", column=@Column(name = "S_NF2")),
	       @AttributeOverride(name="needFinish3", column=@Column(name = "S_NF3")),
	       @AttributeOverride(name="needFinish4", column=@Column(name = "S_NF4")),
	       @AttributeOverride(name="needFinish5", column=@Column(name = "S_NF5")),
	       @AttributeOverride(name="needFinish6", column=@Column(name = "S_NF6")),
	       @AttributeOverride(name="needFinish7", column=@Column(name = "S_NF7")),
	       @AttributeOverride(name="needFinish8", column=@Column(name = "S_NF8")),
	       @AttributeOverride(name="needFinish9", column=@Column(name = "S_NF9"))
	   })
	private RuralNeedFinishInfo source = new RuralNeedFinishInfo();

	@Embedded
	@AttributeOverrides({
	       @AttributeOverride(name="needFinish1", column=@Column(name = "T_NF1")),
	       @AttributeOverride(name="needFinish2", column=@Column(name = "T_NF2")),
	       @AttributeOverride(name="needFinish3", column=@Column(name = "T_NF3")),
	       @AttributeOverride(name="needFinish4", column=@Column(name = "T_NF4")),
	       @AttributeOverride(name="needFinish5", column=@Column(name = "T_NF5")),
	       @AttributeOverride(name="needFinish6", column=@Column(name = "T_NF6")),
	       @AttributeOverride(name="needFinish7", column=@Column(name = "T_NF7")),
	       @AttributeOverride(name="needFinish8", column=@Column(name = "T_NF8")),
	       @AttributeOverride(name="needFinish9", column=@Column(name = "T_NF9"))
	   })
	private RuralNeedFinishInfo target = new RuralNeedFinishInfo();

	public RuralNeedFinishInfo getSource() {
		return source;
	}

	public void setSource(RuralNeedFinishInfo source) {
		this.source = source;
	}

	public RuralNeedFinishInfo getTarget() {
		return target;
	}

	public void setTarget(RuralNeedFinishInfo target) {
		this.target = target;
	}
	
	/**
	 * 如果有值不相等的，则表示需要调整
	 */
	public boolean needAdjust(){
		return !source.valueEquals(target);
	}
}
