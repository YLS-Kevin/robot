package com.yls.projects.robot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yls.frame.common.web.Result;
import com.yls.projects.common.controller.BaseController;
import com.yls.projects.robot.entity.DwordGroup;
import com.yls.projects.robot.service.DwordGroupService;
import com.yls.projects.robot.vo.DwordGroupVo;

/**
 * 动态词组Controller
 * 
 * @author 陈俊
 * @date 2018年5月21日
 */
@RestController
public class DwordGroupController extends BaseController {

	@Autowired
	private DwordGroupService dwordGroupService;

	/**
	 * 添加动态词组
	 * 
	 * @param dwordGroup
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/addDynaWordType")
	public Result addDynaWordType(DwordGroup dwordGroup) {
		return dwordGroupService.addDynaWordType(dwordGroup);
	}

	/**
	 * 更新动态词组
	 * 
	 * @param dwordGroup
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/modifyDynaWordType")
	public Result modifyDynaWordType(DwordGroup dwordGroup) {
		return dwordGroupService.modifyDynaWordType(dwordGroup);
	}

	/**
	 * 动态词组列表
	 * 
	 * @param dwordGroup
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/listDynaWordType")
	public Result listDynaWordType(DwordGroupVo dwordGroupVo) {
		return dwordGroupService.listDynaWordType(dwordGroupVo);
	}

	/**
	 * 删除动态词组
	 * 
	 * @param dwordGroupVo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/delDynaWordType")
	public Result delDynaWordType(DwordGroup dwordGroup) {
		return dwordGroupService.delDynaWordType(dwordGroup);
	}

}
