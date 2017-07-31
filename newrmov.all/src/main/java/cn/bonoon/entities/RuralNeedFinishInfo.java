package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 一个自然村需要完成的指标项
 * @author jackson
 *
 */
@Embeddable
public class RuralNeedFinishInfo {

	//以下是必须完成的10项指标与情况统计表上对应
	//
	@Column(name = "C_NF1")
	private int needFinish1;
	@Column(name = "C_NF2")
	private int needFinish2;
	@Column(name = "C_NF3")
	private int needFinish3;
	@Column(name = "C_NF4")
	private int needFinish4;
	@Column(name = "C_NF5")
	private int needFinish5;
	@Column(name = "C_NF6")
	private int needFinish6;
	@Column(name = "C_NF7")
	private int needFinish7;
	@Column(name = "C_NF8")
	private int needFinish8;
	@Column(name = "C_NF9")
	private int needFinish9;

	public int getNeedFinish1() {
		return needFinish1;
	}

	public void setNeedFinish1(int needFinish1) {
		this.needFinish1 = needFinish1;
	}

	public int getNeedFinish2() {
		return needFinish2;
	}

	public void setNeedFinish2(int needFinish2) {
		this.needFinish2 = needFinish2;
	}

	public int getNeedFinish3() {
		return needFinish3;
	}

	public void setNeedFinish3(int needFinish3) {
		this.needFinish3 = needFinish3;
	}

	public int getNeedFinish4() {
		return needFinish4;
	}

	public void setNeedFinish4(int needFinish4) {
		this.needFinish4 = needFinish4;
	}

	public int getNeedFinish5() {
		return needFinish5;
	}

	public void setNeedFinish5(int needFinish5) {
		this.needFinish5 = needFinish5;
	}

	public int getNeedFinish6() {
		return needFinish6;
	}

	public void setNeedFinish6(int needFinish6) {
		this.needFinish6 = needFinish6;
	}

	public int getNeedFinish7() {
		return needFinish7;
	}

	public void setNeedFinish7(int needFinish7) {
		this.needFinish7 = needFinish7;
	}

	public int getNeedFinish8() {
		return needFinish8;
	}

	public void setNeedFinish8(int needFinish8) {
		this.needFinish8 = needFinish8;
	}

	public int getNeedFinish9() {
		return needFinish9;
	}

	public void setNeedFinish9(int needFinish9) {
		this.needFinish9 = needFinish9;
	}

	public void copyTo(RuralNeedFinishInfo rnf) {
		rnf.needFinish1 = needFinish1;
		rnf.needFinish2 = needFinish2;
		rnf.needFinish3 = needFinish3;
		rnf.needFinish4 = needFinish4;
		rnf.needFinish5 = needFinish5;
		rnf.needFinish6 = needFinish6;
		rnf.needFinish7 = needFinish7;
		rnf.needFinish8 = needFinish8;
		rnf.needFinish9 = needFinish9;
	}

	public void clear() {
		needFinish1 = 0;
		needFinish2 = 0;
		needFinish3 = 0;
		needFinish4 = 0;
		needFinish5 = 0;
		needFinish6 = 0;
		needFinish7 = 0;
		needFinish8 = 0;
		needFinish9 = 0;
		
	}

	/**
	 * 累计，把数值进行累计
	 * 注意与{@link #stat(RuralNeedFinishInfo)}的区别
	 */
	public void sum(RuralNeedFinishInfo nf) {
		needFinish1 += nf.needFinish1;
		needFinish2 += nf.needFinish2;
		needFinish3 += nf.needFinish3;
		needFinish4 += nf.needFinish4;
		needFinish5 += nf.needFinish5;
		needFinish6 += nf.needFinish6;
		needFinish7 += nf.needFinish7;
		needFinish8 += nf.needFinish8;
		needFinish9 += nf.needFinish9;
	}

	/**
	 * 统计，把选中的统计成数值
	 */
	public void stat(RuralNeedFinishInfo nf) {
		if(nf.needFinish1 != 0) needFinish1++;
		if(nf.needFinish2 != 0) needFinish2++;
		if(nf.needFinish3 != 0) needFinish3++;
		if(nf.needFinish4 != 0) needFinish4++;
		if(nf.needFinish5 != 0) needFinish5++;
		if(nf.needFinish6 != 0) needFinish6++;
		if(nf.needFinish7 != 0) needFinish7++;
		if(nf.needFinish8 != 0) needFinish8++;
		if(nf.needFinish9 != 0) needFinish9++;
	}

	public boolean valueEquals(RuralNeedFinishInfo other) {
		if(other.needFinish1 != needFinish1) return false;
		if(other.needFinish2 != needFinish2) return false;
		if(other.needFinish3 != needFinish3) return false;
		if(other.needFinish4 != needFinish4) return false;
		if(other.needFinish5 != needFinish5) return false;
		if(other.needFinish6 != needFinish6) return false;
		if(other.needFinish7 != needFinish7) return false;
		if(other.needFinish8 != needFinish8) return false;
		if(other.needFinish9 != needFinish9) return false;
		return true;
	}
	
	/**
	 * 选中的值的解析，如果已经选择，则为1，没选中，则为0
	 * @param rnf
	 */
	public void parseFinish(RuralNeedFinishInfo rnf){
		needFinish1 = __checkSetNf(rnf.needFinish1, needFinish1);
		needFinish2 = __checkSetNf(rnf.needFinish2, needFinish2);
		needFinish3 = __checkSetNf(rnf.needFinish3, needFinish3);
		needFinish4 = __checkSetNf(rnf.needFinish4, needFinish4);
		needFinish5 = __checkSetNf(rnf.needFinish5, needFinish5);
		needFinish6 = __checkSetNf(rnf.needFinish6, needFinish6);
		needFinish7 = __checkSetNf(rnf.needFinish7, needFinish7);
		needFinish8 = __checkSetNf(rnf.needFinish8, needFinish8);
		needFinish9 = __checkSetNf(rnf.needFinish9, needFinish9);
	}

	public static int __checkSetNf(int value, int oldValue){
		int result = oldValue;
		if(value==1)result = 1;
		if(value==2)result = 1;
		return result;
	}
	
	/**
	 * 这个是反过来解析，即解析哪些值是之前已经选择了的。
	 * @param rnf
	 */
	public void parseFinish2(RuralNeedFinishInfo rnf){
		needFinish1 = __checkSetNf(rnf.needFinish1, needFinish1);
		needFinish2 = __checkSetNf(rnf.needFinish2, needFinish2);
		needFinish3 = __checkSetNf(rnf.needFinish3, needFinish3);
		needFinish4 = __checkSetNf(rnf.needFinish4, needFinish4);
		needFinish5 = __checkSetNf(rnf.needFinish5, needFinish5);
		needFinish6 = __checkSetNf(rnf.needFinish6, needFinish6);
		needFinish7 = __checkSetNf(rnf.needFinish7, needFinish7);
		needFinish8 = __checkSetNf(rnf.needFinish8, needFinish8);
		needFinish9 = __checkSetNf(rnf.needFinish9, needFinish9);
	}

	public static int __checkSetNf2(int value, int oldValue){
		
		//如果当前是选中的情况，不管之前是否已经选中，这里也应该仍然是选中的状态；选中这个操作是用户自己操作的
		if(oldValue == 2) return oldValue;
		if(oldValue == 0 && value != 0){
			return 1;
		}
		return oldValue;
	}
	/**
	 *  主要是当季度自然村有误时,行政村用来减去自然村的
	 * @param rnf
	 */
	public void subtract(RuralNeedFinishInfo rnf){
		if(rnf.needFinish1==1)
		needFinish1--;
		if(rnf.needFinish2==1)
		needFinish2--;
		if(rnf.needFinish3==1)
		needFinish3--;
		if(rnf.needFinish4==1)
		needFinish4--;
		if(rnf.needFinish5==1)
		needFinish5--;
		if(rnf.needFinish6==1)
		needFinish6--;
		if(rnf.needFinish7==1)
		needFinish7--;
		if(rnf.needFinish8==1)
		needFinish8--;
		if(rnf.needFinish9==1)
		needFinish9--;
		
	}

}
