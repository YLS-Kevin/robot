package com.yls.projects.dialog.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yls.projects.dialog.entity.DialogMore;
import com.yls.projects.dialog.vo.DialogMoreVo;

public interface DialogMoreDao {
	int deleteByPrimaryKey(String id);

	int insert(DialogMore record);

	int insertSelective(DialogMore record);

	DialogMore selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(DialogMore record);

	int updateByPrimaryKey(DialogMore record);

	/**
	 * 获取总记录数
	 * 
	 * @param dialogMoreVo
	 * @return
	 */
	Integer getCount(DialogMoreVo dialogMoreVo);

	/**
	 * 分页获取信息
	 * 
	 * @param dialogMoreVo
	 * @return
	 */
	List<Map<String, Object>> dialogMoreList(DialogMoreVo dialogMoreVo);

	/**
	 * 批量更新数据
	 * 
	 * @param dialog
	 */
	void batchUpdate(@Param(value = "state") String state, @Param(value = "updateBy") String updateBy,
			@Param(value = "list") List<String> list);
	
	/**
	 * 获取总记录数
	 * 
	 * @param dialogMoreVo
	 * @return
	 */
	Integer getCountByThemeName(DialogMore record);

}