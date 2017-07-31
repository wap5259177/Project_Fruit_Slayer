package cn.bonoon.core.configs;

import java.util.List;

import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.annotations.form.FormEditor;
import cn.bonoon.kernel.web.annotations.form.PropertyEditor;
import cn.bonoon.kernel.web.annotations.form.PropertyHelper;
import cn.bonoon.kernel.web.annotations.form.PropertyHelper.HelperType;

@FormEditor(headWidth = 180, width = 650)
public class ProjectConfig {
	@PropertyEditor(name = "提交检查", value = 10)
	@PropertyHelper(value = "是否在项目提交的时候检查上传了项目<font color='blue'>建设前</font>的相片", type = HelperType.DIRECT)
	private boolean checkPhoneOnSubmit;
	@PropertyEditor(name = "例外账号", value = 11)
	@PropertyHelper(value = "对已经开启项目提交前检查<font color='blue'>建设前</font>相片的，则可以在这里直接填写该片区的<font color='red'>登录账号</font>；<br/>需要填写多个账号的，则以<font color='red'>半角的逗号','</font>分隔开来", 
		type = HelperType.WRAP)
	private String excludeOnSubmit;
	
	@PropertyEditor(name = "竣工检查(建设前相片)", value = 20)
	@PropertyHelper(value = "项目竣工时，是否需要检查该项目<font color='blue'>建设前</font>的相片", type = HelperType.DIRECT)
	private boolean checkBeforePhoneOnFinish;
	@PropertyEditor(name = "例外账号", value = 21)
	@PropertyHelper(value = "对于项目竣工时开启检查<font color='blue'>建设前</font>相片的，则可以在这里直接填写该片区的<font color='red'>登录账号</font>；<br/>需要填写多个账号的，则以<font color='red'>半角的逗号','</font>分隔开来", 
		type = HelperType.WRAP)
	private String excludeBeforePhone;
	
	@PropertyEditor(name = "竣工检查(建设后相片)", value = 30)
	@PropertyHelper(value = "项目竣工时，是否需要检查该项目<font color='blue'>建设后</font>的相片", type = HelperType.DIRECT)
	private boolean checkAfterPhoneOnFinish;
	@PropertyEditor(name = "例外账号", value = 31)
	@PropertyHelper(value = "对于项目竣工时开启检查<font color='blue'>建设后</font>相片的，则可以在这里直接填写该片区的<font color='red'>登录账号</font>；<br/>需要填写多个账号的，则以<font color='red'>半角的逗号','</font>分隔开来", 
		type = HelperType.WRAP)
	private String excludeAfterPhone;
	
	/**
	 * 如果需要检查相片，则返回true值
	 * @param ope
	 * @return
	 */
	public boolean checkOnSubmit(IOperator ope){
		return __check(ope.getUsername(), checkPhoneOnSubmit, excludeOnSubmit);
	}
	public boolean checkOnSubmit(String username){
		return __check(username, checkPhoneOnSubmit, excludeOnSubmit);
	}

	/**
	 * 如果需要检查相片，则返回true值
	 * @param ope
	 * @return
	 */
	public boolean checkBeforePhone(IOperator ope){
		return __check(ope.getUsername(), checkBeforePhoneOnFinish, excludeBeforePhone);
	}
	public boolean checkBeforePhone(String username){
		return __check(username, checkBeforePhoneOnFinish, excludeBeforePhone);
	}


	/**
	 * 如果需要检查相片，则返回true值
	 * @param ope
	 * @return
	 */
	public boolean checkAfterPhone(IOperator ope){
		return __check(ope.getUsername(), checkAfterPhoneOnFinish, excludeAfterPhone);
	}
	public boolean checkAfterPhone(String username){
		return __check(username, checkAfterPhoneOnFinish, excludeAfterPhone);
	}
	
	private boolean __check(String username, boolean needCheck, String accounts){
		if(!needCheck) return false;
		List<String> allows = StringHelper.toList(accounts);
		return !allows.contains(username);
	}
	
	public boolean isCheckPhoneOnSubmit() {
		return checkPhoneOnSubmit;
	}
	public void setCheckPhoneOnSubmit(boolean checkPhoneOnSubmit) {
		this.checkPhoneOnSubmit = checkPhoneOnSubmit;
	}
	public String getExcludeOnSubmit() {
		return excludeOnSubmit;
	}
	public void setExcludeOnSubmit(String excludeOnSubmit) {
		this.excludeOnSubmit = excludeOnSubmit;
	}
	public boolean isCheckBeforePhoneOnFinish() {
		return checkBeforePhoneOnFinish;
	}
	public void setCheckBeforePhoneOnFinish(boolean checkBeforePhoneOnFinish) {
		this.checkBeforePhoneOnFinish = checkBeforePhoneOnFinish;
	}
	public String getExcludeBeforePhone() {
		return excludeBeforePhone;
	}
	public void setExcludeBeforePhone(String excludeBeforePhone) {
		this.excludeBeforePhone = excludeBeforePhone;
	}
	public boolean isCheckAfterPhoneOnFinish() {
		return checkAfterPhoneOnFinish;
	}
	public void setCheckAfterPhoneOnFinish(boolean checkAfterPhoneOnFinish) {
		this.checkAfterPhoneOnFinish = checkAfterPhoneOnFinish;
	}
	public String getExcludeAfterPhone() {
		return excludeAfterPhone;
	}
	public void setExcludeAfterPhone(String excludeAfterPhone) {
		this.excludeAfterPhone = excludeAfterPhone;
	}
}
