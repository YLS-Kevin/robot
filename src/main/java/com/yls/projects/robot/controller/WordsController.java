package com.yls.projects.robot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yls.frame.common.web.Result;
import com.yls.projects.common.controller.BaseController;
import com.yls.projects.robot.entity.Words;
import com.yls.projects.robot.service.WordsService;
import com.yls.projects.robot.vo.WordsVo;

/**
 * 动态词 Controller
 * 
 * @author 陈俊
 * @date 2018年5月21日
 */
@RestController
public class WordsController extends BaseController {

	@Autowired
	private WordsService wordsService;

	/**
	 * 添加动态词
	 * 
	 * @param dwords
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/addDynaWord")
	public Result addDynaWord(Words words) {
		return wordsService.addDynaWord(words);
	}

	/**
	 * 更新动态词
	 * 
	 * @param dwords
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/modifyDynaWord")
	public Result modifyDynaWord(Words words) {
		return wordsService.modifyDynaWord(words);
	}

	/**
	 * 动态词列表
	 * 
	 * @param dwords
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/listDynaWord")
	public Result listDynaWordType(WordsVo wordsVo) {
		return wordsService.listDynaWord(wordsVo);
	}

	/**
	 * 删除动态词
	 * 
	 * @param words
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/delDynaWord")
	public Result delDynaWord(Words words) {
		return wordsService.delDynaWord(words);
	}

	/**
	 * 批量更新数据(启用/禁用)
	 * 
	 * @param wordsVo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/modifyDynaWordByState")
	public Result modifyDynaWordByState(WordsVo wordsVo) {
		return wordsService.modifyDynaWordByState(wordsVo);
	}
	
	/**
	 * 添加动态词
	 * 
	 * @param dwords
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/addReplaceDynaWord")
	public Result addReplaceDynaWord(Words words) {
		return wordsService.addReplaceDynaWord(words);
	}
}
