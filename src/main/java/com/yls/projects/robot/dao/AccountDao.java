package com.yls.projects.robot.dao;

import org.apache.ibatis.annotations.Param;

import com.yls.projects.robot.entity.Account;

/**
 * 
 * 账户Dao
 * 
 * @author 陈华湛
 * @date 2018年5月3日上午10:01:50
 */
public interface AccountDao {

	/**
	 * 保存注册账户信息
	 * 
	 * @param account
	 * @return Integer
	 */
	Integer saveAccount(Account account);

	/**
	 * 修改数据
	 * 
	 * @param account
	 */
	void update(Account account);

	/**
	 * 根据超级用户id获取信息
	 * 
	 * @param superUserId
	 * @return
	 */
	Account getBySuperUserId(@Param(value = "superUserId") String superUserId);

}
