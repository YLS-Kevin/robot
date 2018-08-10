package com.yls.projects.robot.service;

import com.yls.frame.common.web.Result;
import com.yls.projects.robot.entity.DialogUser;
import com.yls.projects.robot.vo.DialogUserVo;

/**
 * 
 * 用户Service接口
 * 
 * @author 陈华湛
 * @date 2018年4月27日下午5:06:21
 */
public interface DialogUserService {

	/**
	 * 根据id查询用户
	 * 
	 * @param id
	 * @return DialogUser
	 */
	public DialogUser getById(String id);

	/**
	 * 根据手机号码查询用户，如果找到用户，说明该手机号已经被注册了
	 * 
	 * @param dialogUser
	 * @return DialogUser
	 */
	public DialogUser getByTelPhone(DialogUser dialogUser);

	/**
	 * 保存手机号注册用户信息
	 * 
	 * @param dialogUser
	 * @return Integer
	 */
	public Integer saveTelPhoneRegister(DialogUser dialogUser);

	/**
	 * 保存用短信验证码登录的用户信息
	 * 
	 * @param dialogUser
	 * @return
	 */
	public Integer saveVerifyRegister(DialogUser dialogUser);

	/**
	 * 根据user_id查询用户
	 * 
	 * @param dialogUser
	 * @return
	 */
	Result getUserHomeById(DialogUser dialogUser);

	/**
	 * 修改用户信息
	 * 
	 * @param dialogUser
	 * @return
	 */
	Result updateUserById(DialogUser dialogUser);

	/**
	 * 修改密码
	 * 
	 * @param dialogUser
	 * @return
	 */
	Result updateUserPwd(DialogUser dialogUser);

	/**
	 * 对比旧密码
	 * 
	 * @param dialogUser
	 * @return
	 */
	Result getUserPwd(DialogUser dialogUser);

	/**
	 * 获取用户列表
	 * 
	 * @param dialogUser
	 * @return
	 */
	Result getUserList(DialogUserVo dialogUserVo);

	/**
	 * 新增用户
	 * 
	 * @param dialogUserVo
	 * @return
	 */
	Result addUser(DialogUser dialogUser);

	/**
	 * 删除用户
	 * 
	 * @param dialogUserVo
	 * @return
	 */
	Result delUser(DialogUser dialogUser);

}
