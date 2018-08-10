package com.yls.projects.robot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yls.frame.common.web.Result;
import com.yls.projects.common.controller.BaseController;
import com.yls.projects.robot.service.FixedDialogService;
import com.yls.projects.robot.vo.FixedDialogVo;

/**
 * 
 * 固定对话Controller
 * 
 * @author 陈华湛
 * @date 2018年5月7日上午11:01:11
 */
@RestController
public class FixedDialogController extends BaseController {

	@Autowired
	private FixedDialogService fixedDialogService;

	/**
	 * 添加固定对话
	 * 
	 * @param fixedDialogVo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/addFixedDialog")
	public Result addFixedDialog(FixedDialogVo fixedDialogVo) {
		return fixedDialogService.addFixedDialog(fixedDialogVo);
	}

	/**
	 * 修改固定对话
	 * 
	 * @param fixedDialogVo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/modifyFixedDialog")
	public Result modifyFixedDialog(FixedDialogVo fixedDialogVo) {
		return fixedDialogService.modifyFixedDialog(fixedDialogVo);
	}

	/**
	 * 根据人机对话ID查询详情
	 * 
	 * @param interfaceDefVo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getFixedDialogByIdD")
	public Result getFixedDialogByIdD(FixedDialogVo fixedDialogVo) {
		return fixedDialogService.getFixedDialogByIdD(fixedDialogVo);
	}

	/**
	 * 根据id删除对话（适用所有对话）
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/delFixedDialog")
	public Result delFixedDialog(@RequestParam("id") String id) {
		return fixedDialogService.delFixedDialog(id);
	}

	/**
	 * 根据组别id获取固定对话对应信息
	 * 
	 * @param teamId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getFixedDialogByTeamId")
	public Result getFixedDialogByTeamId(@RequestParam(value = "teamId", required = true) String teamId) {
		return fixedDialogService.getFixedDialogByTeamId(teamId);
	}

	/**
	 * 批量启用/禁用
	 * 
	 * @param fixedDialogVo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/modifyFixedDialogByState")
	public Result modifyFixedDialogByState(FixedDialogVo fixedDialogVo) {
		return fixedDialogService.modifyFixedDialogByState(fixedDialogVo);
	}

}
