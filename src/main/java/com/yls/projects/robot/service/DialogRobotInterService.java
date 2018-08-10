package com.yls.projects.robot.service;

import com.yls.frame.common.web.Result;
import com.yls.projects.robot.vo.InterfaceDefVo;

/**
 * 机器接口应答 Service
 * 
 * @author 陈俊
 * @date 2018年5月14日
 */
public interface DialogRobotInterService {

	/**
	 * 添加接口对话
	 * 
	 * @param interfaceDefVo
	 * @return
	 */
	Result addIDialog(InterfaceDefVo interfaceDefVo);

	/**
	 * 修改接口对话
	 * 
	 * @param interfaceDefVo
	 * @return
	 */
	Result modifyIDialog(InterfaceDefVo interfaceDefVo);

	/**
	 * 根据人机对话ID查询详情
	 * 
	 * @param interfaceDefVo
	 * @return
	 */
	Result getIDialogByIdD(InterfaceDefVo interfaceDefVo);
}
