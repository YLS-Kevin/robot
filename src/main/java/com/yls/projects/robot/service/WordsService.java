package com.yls.projects.robot.service;

import com.yls.frame.common.web.Result;
import com.yls.projects.robot.entity.Words;
import com.yls.projects.robot.vo.WordsVo;

/**
 * 词库表Service
 * 
 * @author 陈俊
 * @date 2018年6月4日
 */
public interface WordsService {

	/**
	 * 添加动态词
	 * 
	 * @param words
	 * @return
	 */
	Result addDynaWord(Words words);

	/**
	 * 修改动态词
	 * 
	 * @param words
	 * @return
	 */
	Result modifyDynaWord(Words words);

	/**
	 * 动态词列表
	 * 
	 * @param wordsVo
	 * @return
	 */
	Result listDynaWord(WordsVo wordsVo);

	/**
	 * 删除动态词
	 * 
	 * @param words
	 * @return
	 */
	Result delDynaWord(Words words);

	/**
	 * 批量启用/禁用
	 * 
	 * @param words
	 * @return
	 */
	Result modifyDynaWordByState(WordsVo wordsVo);
	
	/**
	 * 添加动态词
	 * 
	 * @param words
	 * @return
	 */
	Result addReplaceDynaWord(Words words);

}
