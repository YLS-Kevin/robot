package com.yls.projects.dialog.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yls.projects.dialog.entity.DialogTypes;
import com.yls.projects.dialog.vo.DialogTypesVo;
import com.yls.projects.robot.vo.RobotsVo;

/**
 * @Author:Suchy
 * @Description: 人机对话库Dao
 * @Date: 11:18 2018/5/4
 */
public interface DialogTypesDao {
	/**
	 * 获取总记录数
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	Integer getCount(DialogTypesVo dialogTypesVo);

	/**
	 * 分页获取信息
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	List<Map<String, Object>> dialogTypesList(DialogTypesVo dialogTypesVo);

	/**
	 * 新增对话
	 * 
	 * @param dialogTypes
	 */
	void addDialogTypes(DialogTypes dialogTypes);

	/**
	 * 逻辑删除对话
	 * 
	 * @param id
	 */
	void delDialogTypes(DialogTypes dialogTypes);

	/**
	 * 修改对话
	 * 
	 * @param dialogTypes
	 */
	void updateDialogTypes(DialogTypes dialogTypes);

	/**
	 * 根据对话库id查询对话库
	 * 
	 * @param Id
	 * @return
	 */
	DialogTypes getById(@Param(value = "id") String id);
	
	/**
	 * 根据情景模块id查询对话库
	 * 
	 * @param Id
	 * @return
	 */
	DialogTypes getByCidM(@Param(value = "cidM") String cidM);
	
	/**
	 * 查出共享的能力并且自己账户的全部能力
	 * @param robotsVo
	 * @return
	 */
	List<DialogTypes> listShareAbility(RobotsVo robotsVo);
	
	/**
	 * 根据对话库多个id查询对话库列表
	 * 
	 * @param ids
	 * @return
	 */
	List<DialogTypes> listDialogTypeByIds(@Param(value = "ids") List<String> ids);
	
	/**
	 * 共享对话
	 * 
	 * @param dialogTypes
	 */
	void shareDialogLibrary(DialogTypes dialogTypes);
	
	/**
	 * 根据对话库名获取记录数
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	Integer getCountByDialogLibraryName(DialogTypes dialogTypes);
}
