package com.yls.projects.robot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yls.frame.common.web.Result;
import com.yls.projects.common.controller.BaseController;
import com.yls.projects.robot.entity.DialogUser;
import com.yls.projects.robot.service.DialogUserService;
import com.yls.projects.robot.vo.DialogUserVo;

/**
 * 
 * 用户Controller
 * 
 * @author 陈华湛
 * @date 2018年4月27日下午5:04:34
 */
@RestController
public class DialogUserController extends BaseController {

	@Autowired
	private DialogUserService dialogUserService;

	/**
	 * 用户主页显示
	 * 
	 * @param dialogUser
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/getUserHomeById")
	public Result getUserHomeById(DialogUser dialogUser) {
		return dialogUserService.getUserHomeById(dialogUser);
	}

	/**
	 * 用户信息修改接口
	 * 
	 * @param dialogUser
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/updateUserById")
	public Result updateUserById(DialogUser dialogUser) {
		return dialogUserService.updateUserById(dialogUser);
	}

	/**
	 * 修改密码
	 * 
	 * @param dialogUser
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/updateUserPwd")
	public Result updateUserPwd(DialogUser dialogUser) {
		return dialogUserService.updateUserPwd(dialogUser);
	}

	/**
	 * 对比旧密码
	 * 
	 * @param dialogUser
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getUserPwd")
	public Result getUserPwd(DialogUser dialogUser) {
		return dialogUserService.getUserPwd(dialogUser);
	}

	/**
	 * 获取用户列表
	 * 
	 * @param dialogUserVo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/getUserList")
	public Result getUserList(DialogUserVo dialogUserVo) {
		return dialogUserService.getUserList(dialogUserVo);
	}

	/**
	 * 新增用户
	 * 
	 * @param dialogUserVo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/addUser")
	public Result addUser(DialogUser dialogUser) {
		return dialogUserService.addUser(dialogUser);
	}

	/**
	 * 删除用户
	 * 
	 * @param dialogUser
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/delUser")
	public Result delUser(DialogUser dialogUser) {
		return dialogUserService.delUser(dialogUser);
	}

}
