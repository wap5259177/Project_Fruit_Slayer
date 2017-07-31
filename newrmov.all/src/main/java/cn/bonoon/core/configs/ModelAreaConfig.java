package cn.bonoon.core.configs;

import java.util.List;

import cn.bonoon.kernel.annotations.Ignore;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.annotations.components.AsSelector;
import cn.bonoon.kernel.web.annotations.components.SelectorOption;
import cn.bonoon.kernel.web.annotations.form.FormEditor;
import cn.bonoon.kernel.web.annotations.form.PropertyEditor;
import cn.bonoon.kernel.web.annotations.form.PropertyHelper;
import cn.bonoon.kernel.web.annotations.form.PropertyHelper.HelperType;

@FormEditor(headWidth = 180, width = 650)
public class ModelAreaConfig {

	//@AsCheckbox("是")
	@AsSelector({
		@SelectorOption(value = "0", name = "开放填报"),
		@SelectorOption(value = "1", name = "仅关闭提示"),
		@SelectorOption(value = "2", name = "完全关闭填报")
	})
	@PropertyEditor(name = "关闭第一批", value = 0, width = 200)
	@PropertyHelper(value = "只关闭基本台账的填报部分", type = HelperType.DIRECT)
	private int closeOne;

	//@AsCheckbox("是")
	@AsSelector({
		@SelectorOption(value = "0", name = "开放填报"),
		@SelectorOption(value = "1", name = "仅关闭提示"),
		@SelectorOption(value = "2", name = "完全关闭填报")
	})
	@PropertyEditor(name = "关闭第二批", value = 10, width = 200)
	@PropertyHelper(value = "只关闭基本台账的填报部分", type = HelperType.DIRECT)
	private int closeTwo;
	
	//@AsCheckbox("是")
	@AsSelector({
		@SelectorOption(value = "0", name = "开放填报"),
		@SelectorOption(value = "1", name = "仅关闭提示"),
		@SelectorOption(value = "2", name = "完全关闭填报")
	})
	@PropertyEditor(name = "关闭第三批", value = 20, width = 200)
	@PropertyHelper(value = "只关闭基本台账的填报部分", type = HelperType.DIRECT)
	private int closeThree;

	@AsSelector({
		@SelectorOption(value = "0", name = "开放填报"),
		@SelectorOption(value = "1", name = "仅关闭提示"),
		@SelectorOption(value = "2", name = "完全关闭填报")
	})
	@PropertyEditor(name = "关闭珠三角", value = 30, width = 200)
	@PropertyHelper(value = "只关闭基本台账的填报部分", type = HelperType.DIRECT)
	private int closeFour;
	
	//@AsCheckbox("是")
	@AsSelector({
		@SelectorOption(value = "0", name = "开放填报"),
		@SelectorOption(value = "1", name = "仅关闭提示"),
		@SelectorOption(value = "2", name = "完全关闭填报")
	})
	@PropertyEditor(name = "关闭未明确批次", value = 90, width = 200)
	@PropertyHelper(value = "处理对于某些片区还没有选择是第几批的情况", type = HelperType.DIRECT)
	private int closeOther;

	@PropertyEditor(name = "例外账号", value = 100)
	@PropertyHelper(value = "如果已经关闭填报的，确实需要开放修改的，则可以在这里直接填写该片区的<font color='red'>登录账号</font>；<br/>需要填写多个账号的，则以<font color='red'>半角的逗号','</font>分隔开来", 
		type = HelperType.WRAP)
	private String allowAccount;
	
	/**
	 * 检查是否能继续操作，如果返回true，则可以继续执行下面代码
	 * @param entity
	 * @param ope
	 * @return
	 */
	public boolean check(String batch, IOperator ope, boolean onSave){
		//取得批次
		if(null != batch){
			switch (batch) {
			case "一":
				return __check(closeOne, ope, onSave);
			case "二":
				return __check(closeTwo, ope, onSave);
			case "三":
				return __check(closeThree, ope, onSave);
			case "珠三角":
				return __check(closeFour, ope, onSave);
			}
		}
		return __check(closeOther, ope, onSave);
	}
	@Ignore
	private List<String> allows;
	private boolean __check(int close, IOperator ope, boolean onSave){
		if(0 == close) return true;
		if(allows == null){
			allows = StringHelper.toList(allowAccount);
		}
		if(allows.contains(ope.getUsername())) return true;
		return onSave && 1 == close;
	}
	
//
//	public boolean isCloseOne() {
//		return closeOne;
//	}
//
//	public void setCloseOne(boolean closeOne) {
//		this.closeOne = closeOne;
//	}
//
//	public boolean isCloseTwo() {
//		return closeTwo;
//	}
//
//	public void setCloseTwo(boolean closeTwo) {
//		this.closeTwo = closeTwo;
//	}
//
//	public boolean isCloseThree() {
//		return closeThree;
//	}
//
//	public void setCloseThree(boolean closeThree) {
//		this.closeThree = closeThree;
//	}

	public int getCloseOne() {
		return closeOne;
	}

	public void setCloseOne(int closeOne) {
		this.closeOne = closeOne;
	}

	public int getCloseTwo() {
		return closeTwo;
	}

	public void setCloseTwo(int closeTwo) {
		this.closeTwo = closeTwo;
	}

	public int getCloseThree() {
		return closeThree;
	}

	public void setCloseThree(int closeThree) {
		this.closeThree = closeThree;
	}

	public String getAllowAccount() {
		return allowAccount;
	}

	public void setAllowAccount(String allowAccount) {
		this.allowAccount = allowAccount;
	}

	public int getCloseOther() {
		return closeOther;
	}

	public void setCloseOther(int closeOther) {
		this.closeOther = closeOther;
	}

	public int getCloseFour() {
		return closeFour;
	}

	public void setCloseFour(int closeFour) {
		this.closeFour = closeFour;
	}
}
