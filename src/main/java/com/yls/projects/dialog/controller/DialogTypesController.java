package com.yls.projects.dialog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yls.frame.common.web.Result;
import com.yls.projects.dialog.service.DialogTypesService;
import com.yls.projects.dialog.vo.DialogTypesVo;

/**
 * 人机对话库表Controller
 * 
 * @author 陈俊
 * @date 2018年5月29日
 */
@RestController
public class DialogTypesController {

	@Autowired
	private DialogTypesService dialogTypesService;

	/**
	 * 根据搜索条件查询对话列表
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getDialogById")
	public Result getDialogById(DialogTypesVo dialogTypesVo) {
		return dialogTypesService.getDialogById(dialogTypesVo);

	}

	/**
	 * 根据账户ID查询信息(多论对话)
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getDialogByIdAc")
	public Result getDialogByIdAc(DialogTypesVo dialogTypesVo) {
		return dialogTypesService.getDialogByIdAc(dialogTypesVo);
	}

}
