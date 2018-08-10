package com.yls.projects.robot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yls.frame.common.web.Result;
import com.yls.projects.common.controller.BaseController;
import com.yls.projects.robot.service.DialogRobotInterService;
import com.yls.projects.robot.vo.InterfaceDefVo;

/**
 * 接口对话Controller
 * 
 * @author 陈俊
 * @date 2018年5月14日
 */
@RestController
public class InterfaceDefController extends BaseController{

	@Autowired
	private DialogRobotInterService dialogRobotInterService;

	/**
	 * 添加接口对话
	 * 
	 * @param interfaceDefVo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/addIDialog")
	public Result addIDialog(InterfaceDefVo interfaceDefVo) {
		return dialogRobotInterService.addIDialog(interfaceDefVo);
	}

	/**
	 * 修改接口对话
	 * 
	 * @param interfaceDefVo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/modifyIDialog")
	public Result modifyIDialog(InterfaceDefVo interfaceDefVo) {
		return dialogRobotInterService.modifyIDialog(interfaceDefVo);
	}

	/**
	 * 根据人机对话ID查询详情
	 * 
	 * @param interfaceDefVo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("getIDialogByIdD")
	public Result getIDialogByIdD(InterfaceDefVo interfaceDefVo) {
		return dialogRobotInterService.getIDialogByIdD(interfaceDefVo);
	}

}
