package com.yls.projects.robot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yls.projects.robot.entity.DialogMan;
import com.yls.projects.robot.vo.FixedDialogVo;

/**
 * 
 * 对话人说Dao
 * 
 * @author 陈华湛
 * @date 2018年5月7日下午5:22:45
 */
public interface DialogManDao {

	/**
	 * 添加人说表
	 * 
	 * @param dialogMan
	 */
	void insert(DialogMan dialogMan);

	/**
	 * 更新人说表
	 * 
	 * @param dialogMan
	 */
	void update(DialogMan dialogMan);

	/**
	 * 根据人机对话id获取对应数据
	 * 
	 * @param dialogId
	 * @return
	 */
	DialogMan getDialogManByIdD(String dialogId);

	/**
	 * 根据人机对话id获取对应数据List
	 * 
	 * @param dialogId
	 * @return
	 */
	List<DialogMan> getListByIdD(@Param(value = "dialogId") String dialogId);
	
	/**
	 * 删除人说表的记录通过对话id
	 * 
	 * @param dialogId
	 * @return
	 */
	void deleteDialogManByDialogId(@Param(value = "dialogId") String dialogId);
}
