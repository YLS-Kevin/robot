package com.yls.projects.robot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yls.projects.robot.dao.AccountDao;
import com.yls.projects.robot.entity.Account;
import com.yls.projects.robot.service.AccountService;

/**
 * 
 * 账户Service实现类
 * @author 陈华湛
 * @date 2018年5月3日上午10:00:45
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	private AccountDao accountDao;

	/**
	 * 保存注册账户信息
	 */
	@Override
	public Integer saveAccount(Account account) {
		return accountDao.saveAccount(account);
	}

}
