package com.yls.projects.robot.service;

import com.yls.frame.common.web.Result;
import com.yls.projects.robot.entity.DwordGroup;
import com.yls.projects.robot.vo.DwordGroupVo;

/**
 * 动态词组表 Service
 * 
 * @author 陈俊
 * @date 2018年5月21日
 */
public interface DwordGroupService {

	/**
	 * 添加动态词组
	 * 
	 * @param dwordGroup
	 * @return
	 */
	Result addDynaWordType(DwordGroup dwordGroup);

	/**
	 * 更新动态词组
	 * 
	 * @param dwordGroup
	 * @return
	 */
	Result modifyDynaWordType(DwordGroup dwordGroup);

	/**
	 * 动态词组列表
	 * 
	 * @param dwordGroup
	 * @return
	 */
	Result listDynaWordType(DwordGroupVo dwordGroupVo);

	/**
	 * 删除动态词组
	 * 
	 * @param dwordGroupVo
	 * @return
	 */
	Result delDynaWordType(DwordGroup dwordGroup);
}
