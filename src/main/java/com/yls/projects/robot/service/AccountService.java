package com.yls.projects.robot.service;

import com.yls.projects.robot.entity.Account;

/**
 * 
 * 账户Service接口
 * @author 陈华湛
 * @date 2018年5月3日上午9:58:00
 */
public interface AccountService {

	/**
	 * 保存注册账户信息
	 * @param account
	 * @return Integer
	 */
	Integer saveAccount(Account account);
}
