package com.yls.projects.robot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yls.projects.robot.entity.DialogAnswerScript;

/**
 * 
 * 答案返回脚本Dao
 * 
 * @author 陈华湛
 * @date 2018年5月7日下午5:50:27
 */
public interface DialogAnswerScriptDao {

	/**
	 * 根据对话id获取对应脚本数据
	 * 
	 * @return
	 */
	DialogAnswerScript getDialogAnswerScriptByIdD(String dialogId);

	/**
	 * 插入数据
	 * 
	 * @param dialogAnswerScript
	 */
	void insert(DialogAnswerScript dialogAnswerScript);

	/**
	 * 更新数据
	 * 
	 * @param dialogAnswerScript
	 */
	void update(DialogAnswerScript dialogAnswerScript);

	/**
	 * 删除数据
	 * 
	 * @param dialogAnswerScript
	 */
	void delete(@Param(value = "dialogId") String dialogId);

	/**
	 * 根据人机对话ID查询详情List
	 * 
	 * @param dialogId
	 * @return
	 */
	List<DialogAnswerScript> getListByIdD(@Param(value = "dialogId") String dialogId);
}