package com.yls.projects.robot.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yls.frame.common.enums.ResultEnum;
import com.yls.frame.common.utils.ResultUtil;
import com.yls.frame.common.web.Result;
import com.yls.projects.common.controller.BaseController;
import com.yls.projects.robot.entity.DialogUser;
import com.yls.projects.robot.entity.TestFunction;
import com.yls.projects.robot.utils.HttpUtils;

/**
 * 
 * 测试Controller
 * 
 * @author 李璐
 * @date 2018年6月25日下午5:04:34
 */
@RestController
public class TestFunctionController extends BaseController{

	/**
	 * 
	 * 
	 * @param dialogUser
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/testFunction")
	public Result testFunction(@Valid TestFunction testFunction, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(ResultEnum.FAIL.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
		String result = null;
		if ("post".equals(testFunction.getSendType().toLowerCase())) {
			result = HttpUtils.post(testFunction.getUrl(), null);
		} else {
			result = HttpUtils.get(testFunction.getUrl());
		}
		return ResultUtil.success(result);
	}
}
