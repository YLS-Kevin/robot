package com.yls.projects.robot.service;

import java.util.List;
import java.util.Map;

import com.yls.frame.common.web.Result;
import com.yls.projects.robot.vo.MulDialogVo;

/**
 * 多轮对话Service
 * 
 * @author 陈俊
 * @date 2018年5月17日
 */
public interface MulDialogService {

	/**
	 * 添加多轮对话
	 * 
	 * @param interfaceDefVo
	 * @return
	 */
	Result addMulDialog(MulDialogVo mulDialogVo);

	/**
	 * 模糊匹配,固定应答
	 * 
	 * @param mulDialogVo
	 * @param dialogUserId
	 * @param awordsList
	 * @param anwersList
	 * @param scriptList
	 */
	void addFixedDialogByAptype1(MulDialogVo mulDialogVo, String dialogUserId, List<Map<String, Object>> awordsList,
			List<Map<String, Object>> anwersList, List<Map<String, Object>> scriptList, String teamId);

	/**
	 * 关键词匹配,固定应答
	 * 
	 * @param mulDialogVo
	 * @param dialogUserId
	 * @param keywordsList
	 * @param anwersList
	 * @param scriptList
	 */
	void addFixedDialogByAptype2(MulDialogVo mulDialogVo, String dialogUserId, List<Map<String, Object>> keywordsList,
			List<Map<String, Object>> anwersList, List<Map<String, Object>> scriptList, String teamId);

	/**
	 * 模糊匹配,接口对话
	 * 
	 * @param mulDialogVo
	 * @param dialogUserId
	 * @param awordsList
	 * @param anwersList
	 * @param scriptList
	 */
	void addInterfaceDefByAptype1(MulDialogVo mulDialogVo, String dialogUserId, List<Map<String, Object>> awordsList,
			List<Map<String, Object>> anwersList, List<Map<String, Object>> scriptList, String teamId);

	/**
	 * 关键词匹配,接口对话
	 * 
	 * @param mulDialogVo
	 * @param dialogUserId
	 * @param keywordsList
	 * @param anwersList
	 * @param scriptList
	 */
	void addInterfaceDefByAptype2(MulDialogVo mulDialogVo, String dialogUserId, List<Map<String, Object>> keywordsList,
			List<Map<String, Object>> anwersList, List<Map<String, Object>> scriptList, String teamId);

	/**
	 * 修改多轮对话
	 * 
	 * @param mulDialogVo
	 * @return
	 */
	Result updateMulDialog(MulDialogVo mulDialogVo);

	/**
	 * 模糊匹配,修改固定应答
	 * 
	 * @param mulDialogVo
	 * @param dialogUserId
	 * @param awordsList
	 * @param anwersList
	 * @param scriptList
	 */
	void updateFixedDialogByAptype1(MulDialogVo mulDialogVo, String dialogUserId, List<Map<String, Object>> awordsList,
			List<Map<String, Object>> anwersList, List<Map<String, Object>> scriptList);

	/**
	 * 关键词匹配,修改固定应答
	 * 
	 * @param mulDialogVo
	 * @param dialogUserId
	 * @param awordsList
	 * @param keywordsList
	 * @param scriptList
	 */
	void updateFixedDialogByAptype2(MulDialogVo mulDialogVo, String dialogUserId, List<Map<String, Object>> awordsList,
			List<Map<String, Object>> keywordsList, List<Map<String, Object>> scriptList);

	/**
	 * 模糊匹配,修改接口应答
	 * 
	 * @param mulDialogVo
	 * @param dialogUserId
	 * @param awordsList
	 * @param anwersList
	 * @param scriptList
	 */
	void updateInterfaceDefByAptype1(MulDialogVo mulDialogVo, String dialogUserId, List<Map<String, Object>> awordsList,
			List<Map<String, Object>> anwersList, List<Map<String, Object>> scriptList);

	/**
	 * 关键词匹配,修改接口应答
	 * 
	 * @param mulDialogVo
	 * @param dialogUserId
	 * @param awordsList
	 * @param anwersList
	 * @param scriptList
	 */
	void updateInterfaceDefByAptype2(MulDialogVo mulDialogVo, String dialogUserId, List<Map<String, Object>> awordsList,
			List<Map<String, Object>> keywordsList, List<Map<String, Object>> scriptList);

	/**
	 * 根据人机对话id查询多轮对话详情
	 * 
	 * @param mulDialogVo
	 * @return
	 */
	Result getMulDialogByIdD(MulDialogVo mulDialogVo);

}
