package com.yls.projects.robot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yls.projects.robot.dao.SMSVerifyDao;
import com.yls.projects.robot.entity.SMSVerify;
import com.yls.projects.robot.service.SMSVerifyService;

/**
 * 
 * 短信验证Service实现类
 * @author 陈华湛
 * @date 2018年4月28日下午5:08:13
 */
@Service("sMSVerifyService")
public class SMSVerifyServiceImpl implements SMSVerifyService{
	
	@Autowired
	private SMSVerifyDao sMSVerifyDao;

	/**
	 * 根据手机号查找短信验证信息
	 */
	@Override
	public SMSVerify getByTelPhone(SMSVerify sMSVerify) {
		return sMSVerifyDao.getByTelPhone(sMSVerify);
	}

	/**
	 * 保存短信验证信息
	 */
	@Override
	public Integer save(SMSVerify sMSVerify) {
		return sMSVerifyDao.save(sMSVerify);
	}

}
