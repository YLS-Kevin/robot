package com.yls.projects.robot.service;

import com.yls.frame.common.web.Result;
import com.yls.projects.robot.entity.Dwords;
import com.yls.projects.robot.vo.DwordsVo;

/**
 * 动态词表 Service
 * 
 * @author 陈俊
 * @date 2018年5月22日
 */
public interface DwordsService {

	/**
	 * 添加动态词
	 * 
	 * @param dwords
	 * @return
	 */
	Result addDynaWord(Dwords dwords);

	/**
	 * 修改动态词
	 * 
	 * @param dwords
	 * @return
	 */
	Result modifyDynaWord(Dwords dwords);

	/**
	 * 动态词列表
	 * 
	 * @param dwords
	 * @return
	 */
	Result listDynaWord(DwordsVo dwordsVo);
}
