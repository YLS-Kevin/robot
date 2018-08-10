package com.yls.projects.robot.dao;

import java.util.List;
import java.util.Map;

import com.yls.projects.robot.entity.Dwords;
import com.yls.projects.robot.vo.DwordsVo;

/**
 * 动态词表 Dao
 * 
 * @author 陈俊
 * @date 2018年5月21日
 */
public interface DwordsDao {

	/**
	 * 添加数据
	 * 
	 * @param dwords
	 */
	Integer insert(Dwords dwords);

	/**
	 * 更新数据
	 * 
	 * @param dwords
	 */
	void update(Dwords dwords);

	/**
	 * 根据动态组id查询动态词
	 * 
	 * @param dwordsVo
	 * @return
	 */
	List<Map<String, Object>> getListByIdDwg(DwordsVo dwordsVo);

	/**
	 * 获取总数
	 * 
	 * @param dwordsVo
	 * @return
	 */
	Integer getCount(DwordsVo dwordsVo);
}
