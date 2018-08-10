package com.yls.projects.robot.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yls.frame.common.enums.ResultEnum;
import com.yls.frame.common.utils.ResultUtil;
import com.yls.frame.common.web.Result;
import com.yls.projects.common.controller.BaseController;
import com.yls.projects.robot.service.InterDataService;
import com.yls.projects.robot.vo.InterDataVo;


/**
 * @Author:Alex
 * @Description: 我的数据接口
 * @Date: 11:00 2018/5/31
 */
@RestController
public class InterDataController extends BaseController{

	private static Logger logger = LoggerFactory.getLogger(InterDataController.class);
	
	@Autowired
	private InterDataService interDataService;
	
	/**
	 * 分页获取数据接口列表
	 * 
	 * @param InterDataVo
	 * @return
	 */
	@GetMapping(value = "/listInterDataByPage")
	public Result listInterDataByPage(InterDataVo interDataVo) {
		return interDataService.listInterDataByPage(interDataVo);
	}
	
	/**
	 * 获取单个数据接口
	 * 
	 * @param InterDataVo
	 * @return
	 */
	@GetMapping(value = "/getInterfaceById")
	public Result getInterDataById(InterDataVo interDataVo) {
		return interDataService.getInterDataById(interDataVo);
	}
	
	/**
	 * 获取单个数据接口
	 * 
	 * @param InterDataVo
	 * @return
	 */
	@PostMapping(value = "/addInterface")
	public Result addInterface(@Valid InterDataVo interDataVo, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(ResultEnum.FAIL.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
		return interDataService.addInterface(interDataVo);
	}
	
	/**
	 * 获取单个数据接口
	 * 
	 * @param InterDataVo
	 * @return
	 */
	@PostMapping(value = "/modifyInterface")
	public Result modifyInterface(@Valid InterDataVo interDataVo, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(ResultEnum.FAIL.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
		return interDataService.modifyInterface(interDataVo);
	}
	
	/**
	 * 获取单个数据接口
	 * 
	 * @param InterDataVo
	 * @return
	 */
	@PostMapping(value = "/activeInterface")
	public Result activeInterface(InterDataVo interDataVo) {
		return interDataService.activeInterface(interDataVo);
	}
	
	/**
	 * 删除数据接口
	 * 
	 * @param InterDataVo
	 * @return
	 */
	@PostMapping(value = "/delInterface")
	public Result delInterface(InterDataVo interDataVo) {
		return interDataService.delInterface(interDataVo);
	}
}
