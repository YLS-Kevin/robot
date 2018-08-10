package com.yls.projects.robot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yls.projects.robot.entity.Words;
import com.yls.projects.robot.vo.WordsVo;

/**
 * 词库表Dao
 * 
 * @author 陈俊
 * @date 2018年5月31日
 */
public interface WordsDao {

	/**
	 * 添加数据
	 * 
	 * @param words
	 * @return
	 */
	void insert(Words words);

	/**
	 * 修改数据
	 * 
	 * @param words
	 * @return
	 */
	void update(Words words);

	/**
	 * 批量添加数据 
	 * 如果有重复数据则忽略
	 * 
	 * @param words
	 */
	void batchInserts(List<Words> list);

	/**
	 * 根据动态组id查询动态词
	 * 
	 * @param dwordsVo
	 * @return
	 */
	List<Map<String, Object>> getListByIdDwg(WordsVo wordsVo);

	/**
	 * 获取总数
	 * 
	 * @param dwordsVo
	 * @return
	 */
	Integer getCount(WordsVo wordsVo);

	/**
	 * 删除数据
	 * 
	 * @param words
	 */
	void delete(Words words);

	/**
	 * 批量更新数据
	 * 
	 * @param state
	 * @param updateBy
	 * @param list
	 */
	void batchUpdate(@Param(value = "state") String state, @Param(value = "updateBy") String updateBy,
			@Param(value = "list") List<String> list);
	
	/**
	 * 添加数据 
	 * 有重名的则替换
	 * 专为动态词插入调用
	 * 
	 * @param words
	 * @return
	 */
	void insertReplace(Words words);
	
	/**
	 * 根据动态组id查询动态词
	 * 
	 * @param words
	 * @return
	 */
	Integer getListByName(Words words);
	
	/**
	 * 根据查询动态词
	 * 
	 * @param words
	 * @return
	 */
	List<Words> getEntityByName(Words words);
	
	/**
	 * 常规添加数据
	 * 
	 * @param words
	 * @return
	 */
	void commonInsert(Words words);
}
