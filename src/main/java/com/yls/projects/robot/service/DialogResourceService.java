package com.yls.projects.robot.service;

import java.util.List;

import com.yls.projects.robot.entity.DialogResource;

/**
 * 资源service
 * 
 * @author Alex Lee 李璐
 * @date 2018年5月6日下午4:49:00
 */
public interface DialogResourceService {

	/**
     * 根据用户id获取权限信息
     * @param userId
     * @return DialogResource
     */
   public List<DialogResource> getByUserId(String userId);
}
