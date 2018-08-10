package com.yls.projects.robot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yls.projects.robot.dao.DialogResourcDao;
import com.yls.projects.robot.entity.DialogResource;
import com.yls.projects.robot.service.DialogResourceService;

/**
 * 资源service impl
 * 
 * @author Alex Lee 李璐
 * @date 2018年5月6日下午4:49:00
 */
@Service("dialogResourceService")
@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
public class DialogResourceServiceImpl implements DialogResourceService{
	
	@Autowired
	private DialogResourcDao dialogResourcDao;
	
	//keyGenerator="wiselyKeyGenerator"

	@Cacheable(value = "ida",key="#root.targetClass.name")
	@Transactional(value = "robotTransactionManager", readOnly = true)
	@Override
	public List<DialogResource> getByUserId(String userId) {
		return dialogResourcDao.getByUserId(userId);
	}

}
