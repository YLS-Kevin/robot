package com.yls.projects.robot.service;

import com.yls.frame.common.web.Result;
import com.yls.projects.robot.vo.FixedDialogVo;

/**
 * 
 * 固定对话Service
 * 
 * @author 陈华湛
 * @date 2018年5月7日上午11:28:07
 */
public interface FixedDialogService {

	/**
	 * 添加固定对话
	 * 
	 * @param fixedDialogVo
	 * @return
	 */
	Result addFixedDialog(FixedDialogVo fixedDialogVo);

	/**
	 * 根据id删除固定对话
	 * 
	 * @param id
	 * @return Result
	 */
	Result delFixedDialog(String id);

	/**
	 * 根据组别id获取固定对话对应信息
	 * 
	 * @param teamId
	 * @return Result
	 */
	Result getFixedDialogByTeamId(String teamId);

	/**
	 * 修改固定对话
	 * 
	 * @param fixedDialogVo
	 * @return
	 */
	Result modifyFixedDialog(FixedDialogVo fixedDialogVo);

	/**
	 * 根据人机对话ID查询详情
	 * 
	 * @param fixedDialogVo
	 * @return
	 */
	Result getFixedDialogByIdD(FixedDialogVo fixedDialogVo);

	/**
	 * 批量启用/禁用单轮对话
	 * 
	 * @param fixedDialogVo
	 * @return
	 */
	Result modifyFixedDialogByState(FixedDialogVo fixedDialogVo);
	
	/**
	 * 删除人说表的记录通过对话id
	 * 
	 * @param fixedDialogVo
	 * @return
	 */
	void deleteDialogManByDialogId(FixedDialogVo fixedDialogVo);
}
