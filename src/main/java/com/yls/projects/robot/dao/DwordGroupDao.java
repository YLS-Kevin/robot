package com.yls.projects.robot.dao;

import java.util.List;
import java.util.Map;

import com.yls.projects.robot.entity.DwordGroup;
import com.yls.projects.robot.vo.DwordGroupVo;

/**
 * 动态词组表 Dao
 * 
 * @author 陈俊
 * @date 2018年5月21日
 */
public interface DwordGroupDao {

	/**
	 * 添加数据
	 * 
	 * @param dwordGroup
	 */
	void insert(DwordGroup dwordGroup);

	/**
	 * 更新数据
	 * 
	 * @param dwordGroup
	 */
	void update(DwordGroup dwordGroup);

	/**
	 * 根据账号Id查询列表
	 * 
	 * @param dwordGroupVo
	 * @return
	 */
	List<Map<String, Object>> getListByIdAc(DwordGroupVo dwordGroupVo);

	/**
	 * 获取总数
	 * 
	 * @param dwordGroupVo
	 * @return
	 */
	Integer getCount(DwordGroupVo dwordGroupVo);

	/**
	 * 删除数据
	 * 
	 * @param dwordGroup
	 */
	void delete(DwordGroup dwordGroup);  
	
	/**
	 * 获取总数去重复添加
	 * 
	 * @param dwordGroupVo
	 * @return
	 */
	Integer getCountByName(DwordGroup dwordGroup);
	
	/**
	 * 获取总数去重复添加
	 * 
	 * @param dwordGroupVo
	 * @return
	 */
	Integer getCountByCnName(DwordGroup dwordGroup);
	

}
