package com.yls.projects.robot.dao;

import java.util.List;
import java.util.Map;

import com.yls.frame.common.web.Result;
import com.yls.projects.robot.entity.InterData;
import com.yls.projects.robot.vo.InterDataVo;
import com.yls.projects.robot.vo.RobotsVo;

public interface InterDataDao {
    int deleteByPrimaryKey(String id);

    int insert(InterData record);

    int insertSelective(InterData record);

    InterData selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InterData record);

    int updateByPrimaryKey(InterData record);
    
    /**
	 * 根据账号ID查询列表
	 * 
	 * @param idAc
	 * @return
	 */
    List<InterData> listInterDataByPage(InterDataVo interDataVo);
    
    /**
	 * 获取总数
	 * 
	 * @param robotsVo
	 * @return
	 */
	Integer getCount(InterDataVo interDataVo);
	
	/**
	 * 根据数据接口ID查询实体
	 * 
	 * @param id
	 * @return
	 */
    InterData getInterDataById(InterDataVo interDataVo);
    
    /**
	 * 批量启用或禁用接口
	 * 
	 * @param id
	 * @return
	 */
    int batchActiveInter(Map<String, Object> map);
    
    /**
	 * 批量删除接口
	 * 
	 * @param id
	 * @return
	 */
    int delInterface(Map<String, Object> map);
    
    /**
   	 * 获取总数
   	 * 
   	 * @param robotsVo
   	 * @return
   	 */
   	Integer getCountByInterName(InterDataVo interDataVo);
    
}