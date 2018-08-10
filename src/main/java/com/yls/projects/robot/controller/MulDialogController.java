package com.yls.projects.robot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yls.frame.common.web.Result;
import com.yls.projects.common.controller.BaseController;
import com.yls.projects.robot.service.MulDialogService;
import com.yls.projects.robot.vo.MulDialogVo;

/**
 * 多轮对话Controller
 * 
 * @author 陈俊
 * @date 2018年5月17日
 */
@RestController
public class MulDialogController extends BaseController{

	@Autowired
	private MulDialogService mulDialogService;

	/**
	 * 添加多轮对话
	 * 
	 * @param interfaceDefVo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/addMulDialog")
	public Result addMulDialog(MulDialogVo mulDialogVo) {
		return mulDialogService.addMulDialog(mulDialogVo);
	}

	/**
	 * 修改多轮对话
	 * 
	 * @param mulDialogVo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/updateMulDialog")
	public Result updateMulDialog(MulDialogVo mulDialogVo) {
		return mulDialogService.updateMulDialog(mulDialogVo);
	}

	/**
	 * 根据人机对话id查询多轮对话详情
	 * 
	 * @param mulDialogVo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getMulDialogByIdD")
	public Result getMulDialogByIdD(MulDialogVo mulDialogVo) {
		return mulDialogService.getMulDialogByIdD(mulDialogVo);
	}

}
