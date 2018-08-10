package com.yls.projects.robot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yls.projects.dialog.vo.DialogTypesVo;
import com.yls.projects.robot.entity.Dialog;

/**
 * 
 * 人机对话Dao
 * 
 * @author 陈华湛
 * @date 2018年5月7日下午5:02:37
 */
public interface DialogDao {

	/**
	 * 添加人机对话表
	 * 
	 * @param dialog
	 */
	void insert(Dialog dialog);

	/**
	 * 根据id删除固定对话,软删除
	 * 
	 * @param id
	 * @return
	 */
	Integer delById(String id);

	/**
	 * 根据组别id获取人机对话信息
	 * 
	 * @param teamId
	 * @return
	 */
	List<Dialog> getDialogByTeamId(String teamId);

	/**
	 * 根据条件查询列表(固定应答)
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	List<Map<String, Object>> getDialogByIdDt(DialogTypesVo dialogTypesVo);

	/**
	 * 获取总数(固定应答)
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	Integer getCount(DialogTypesVo dialogTypesVo);

	/**
	 * 根据条件查询列表(接口对话)
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	List<Map<String, Object>> getInterfaceByIdDt(DialogTypesVo dialogTypesVo);

	/**
	 * 获取总数(接口对话)
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	Integer getInterfaceCount(DialogTypesVo dialogTypesVo);

	/**
	 * 根据条件查询列表(多轮应答)
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	List<Map<String, Object>> getMulDialogByIdDt(DialogTypesVo dialogTypesVo);

	/**
	 * 获取总数(多轮应答)
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	Integer getCountMul(DialogTypesVo dialogTypesVo);

	/**
	 * 根据条件查询列表(多轮应答里面的固定应答)
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	List<Map<String, Object>> getMulDialogByIdAc(DialogTypesVo dialogTypesVo);

	/**
	 * 获取总数(多轮应答里面的固定应答)
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	Integer getCountByIdAc(DialogTypesVo dialogTypesVo);

	/**
	 * 根据条件查询列表(多轮应答里面的接口应答)
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	List<Map<String, Object>> getMulDialogByIdAc2(DialogTypesVo dialogTypesVo);

	/**
	 * 获取总数(多轮应答里面的接口应答)
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	Integer getCountByIdAc2(DialogTypesVo dialogTypesVo);

	/**
	 * 情景模块 根据条件查询列表(固定应答)
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	List<Map<String, Object>> getModelDialogByIdDt(DialogTypesVo dialogTypesVo);

	/**
	 * 情景模块 获取总数(固定应答)
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	Integer getModelCount(DialogTypesVo dialogTypesVo);

	/**
	 * 情景模块 根据条件查询列表(接口对话)
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	List<Map<String, Object>> getModelInterfaceByIdDt(DialogTypesVo dialogTypesVo);

	/**
	 * 情景模块 获取总数(接口对话)
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	Integer getModelInterfaceCount(DialogTypesVo dialogTypesVo);

	/**
	 * 获取总数(aLL应答)
	 * 
	 * @param dialogTypesVo
	 * @return
	 */
	Integer getAllCount(DialogTypesVo dialogTypesVo);

	/**
	 * 批量更新数据
	 * 
	 * @param dialog
	 */
	void batchUpdate(@Param(value = "state") String state, @Param(value = "updateBy") String updateBy,
			@Param(value = "list") List<String> list);
	
	/**
	 * 更新数据时间
	 * 
	 * @param dialog
	 */
	void updateDialog(@Param(value = "id") String id);

}
