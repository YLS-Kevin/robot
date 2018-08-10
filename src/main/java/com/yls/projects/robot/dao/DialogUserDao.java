package com.yls.projects.robot.dao;

import java.util.List;
import java.util.Map;

import com.yls.projects.robot.entity.DialogUser;
import com.yls.projects.robot.vo.DialogUserVo;

/**
 * 
 * 用户Dao
 * 
 * @author 陈华湛
 * @date 2018年4月27日下午5:12:01
 */
public interface DialogUserDao {

	/**
	 * 根据id查询用户
	 * 
	 * @param id
	 * @return
	 */
	DialogUser getById(String id);

	/**
	 * 保存手机号注册用户信息
	 * 
	 * @param dialogUser
	 * @return
	 */
	Integer saveTelPhoneRegister(DialogUser dialogUser);

	/**
	 * 根据手机号码查询用户，如果找到用户，说明该手机号已经被注册了
	 * 
	 * @param dialogUser
	 * @return
	 */
	DialogUser getByTelPhone(DialogUser dialogUser);

	/**
	 * 保存用短信验证码登录的用户信息
	 * 
	 * @param dialogUser
	 * @return
	 */
	Integer saveVerifyRegister(DialogUser dialogUser);

	/**
	 * 根据userId 查询信息
	 * 
	 * @param dialogUser
	 * @return
	 */
	DialogUserVo getByUserId(DialogUser dialogUser);

	/**
	 * 修改数据
	 * 
	 * @param dialogUser
	 */
	void update(DialogUser dialogUser);

	/**
	 * 根据账号Id查询列表
	 * 
	 * @param dialogUserVo
	 * @return
	 */
	List<Map<String, Object>> getListByIdAc(DialogUserVo dialogUserVo);

	/**
	 * 获取总数
	 * 
	 * @param dialogUserVo
	 * @return
	 */
	Integer getCount(DialogUserVo dialogUserVo);

	/**
	 * 删除数据
	 * 
	 * @param dialogUser
	 */
	void delete(DialogUser dialogUser);

}
