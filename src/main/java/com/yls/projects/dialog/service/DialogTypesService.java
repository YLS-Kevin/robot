package com.yls.projects.dialog.service;

import com.yls.frame.common.web.JsonResult;
import com.yls.frame.common.web.Result;
import com.yls.projects.dialog.entity.DialogTypes;
import com.yls.projects.dialog.vo.DialogTypesVo;
import com.yls.projects.robot.entity.DialogUser;

/**
 * @Author:Suchy
 * @Description: 人机对话库Service接口
 * @Date: 11:19 2018/5/4
 */
public interface DialogTypesService {
	/**
	 * 分页获取对话库信息
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	JsonResult dialogTypesList(DialogTypesVo dialogTypesVo);

	/**
	 * 新增对话
	 * 
	 * @param dialogTypes
	 */
	JsonResult addDialogTypes(DialogTypes dialogTypes, DialogUser dialogUser);

	/**
	 * 逻辑删除对话
	 * 
	 * @param id
	 * @param dialogUser
	 * @return
	 */
	JsonResult delDialogTypes(String id, DialogUser dialogUser);

	/**
	 * 修改对话
	 * 
	 * @param dialogTypes
	 */
	JsonResult updateDialogTypes(DialogTypes dialogTypes, DialogUser dialogUser);

	/**
	 * 展示某个对话库详情
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	Result getDialogById(DialogTypesVo dialogTypesVo);

	/**
	 * 根据账户ID查询信息
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	Result getDialogByIdAc(DialogTypesVo dialogTypesVo);
	
	/**
	 * 共享对话
	 * 
	 * @param dialogTypes
	 */
	Result shareDialogLibrary(DialogTypes dialogTypes);
	
	
}
