package com.yls.projects.dialog.service;

import com.yls.frame.common.web.JsonResult;
import com.yls.frame.common.web.Result;
import com.yls.projects.dialog.entity.DialogMore;
import com.yls.projects.dialog.vo.DialogMoreVo;
import com.yls.projects.robot.entity.DialogUser;

public interface DialogMoreService {
	/**
	 * 分页获取主题信息
	 * 
	 * @param dialogMoreVo
	 * @return
	 */
	JsonResult dialogMoreList(DialogMoreVo dialogMoreVo);

	/**
	 * 新增主题
	 * 
	 * @param dialogMore
	 */
	JsonResult addDialogMore(DialogMore dialogMore, DialogUser dialogUser);

	/**
	 * 逻辑删除主题
	 * 
	 * @param id
	 * @param dialogUser
	 * @return
	 */
	JsonResult delDialogMore(String id, DialogUser dialogUser);

	/**
	 * 修改主题
	 * 
	 * @param dialogMore
	 */
	JsonResult updateDialogMore(DialogMore dialogMore, DialogUser dialogUser);

	/**
	 * 批量启用/禁用
	 * 
	 * @param dialogMoreVo
	 * @return
	 */
	Result modifyMulDialogByState(DialogMoreVo dialogMoreVo);
}
