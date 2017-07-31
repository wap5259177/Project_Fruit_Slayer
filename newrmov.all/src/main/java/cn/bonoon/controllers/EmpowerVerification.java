package cn.bonoon.controllers;

import org.springframework.security.authentication.encoding.PasswordEncoder;
import cn.bonoon.core.plugins.LoginService;
import cn.bonoon.entities.plugins.AccountEntity;
import cn.bonoon.entities.plugins.FlagType;
/**
 * 授权验证超级管理员
 * 
 * @author wsf
 * @creation 2016-12-20 
 *
 */
public class EmpowerVerification {

	/**
	 * 
	 * @param un 查询帐户信息要用到的帐户名
	 * @param up 页面输入的密码,验证输入密码是否与查询到的帐户密码一致
	 * @param loginService 
	 * @param passwordEncoder
	 * @return
	 */
	public static boolean empowerVerification(String un, String up,
			LoginService loginService, PasswordEncoder passwordEncoder) {
		
		AccountEntity user = loginService.loadByLoginName(un);
		if (user == null
				|| user.getFlag() != FlagType.SUPER
				|| !passwordEncoder.isPasswordValid(user.getLoginPwd(), up,
						null)) {
			return false;
		}
		return true;
	}
}