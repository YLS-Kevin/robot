package com.yls.projects.robot.dao;

import java.util.List;

import com.yls.projects.robot.entity.DialogResource;

/**
 * 资源dao
 * 
 * @author Alex Lee 李璐
 * @date 2018年5月6日下午4:49:00
 */
public interface DialogResourcDao {
	
	/**
     * 根据用户id获取权限信息
     * @param userId
     * @return DialogResource
     */
    List<DialogResource> getByUserId(String userId);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dialog_resource
     *
     * @mbggenerated Sun May 06 11:40:42 CST 2018
     */
    int deleteByPrimaryKey(String resourceId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dialog_resource
     *
     * @mbggenerated Sun May 06 11:40:42 CST 2018
     */
    int insert(DialogResource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dialog_resource
     *
     * @mbggenerated Sun May 06 11:40:42 CST 2018
     */
    int insertSelective(DialogResource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dialog_resource
     *
     * @mbggenerated Sun May 06 11:40:42 CST 2018
     */
    DialogResource selectByPrimaryKey(String resourceId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dialog_resource
     *
     * @mbggenerated Sun May 06 11:40:42 CST 2018
     */
    int updateByPrimaryKeySelective(DialogResource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dialog_resource
     *
     * @mbggenerated Sun May 06 11:40:42 CST 2018
     */
    int updateByPrimaryKey(DialogResource record);
}