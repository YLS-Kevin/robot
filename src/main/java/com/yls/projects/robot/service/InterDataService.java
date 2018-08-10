package com.yls.projects.robot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yls.frame.common.web.Result;
import com.yls.projects.robot.vo.InterDataVo;

/**
 * @Author:Alex
 * @Description: 我的数据接口
 * @Date: 11:00 2018/5/31
 */
public interface InterDataService {
	
	/**
     * 分页获取数据接口信息
     * @param dialogTypesVo
     * @return
     */
	Result listInterDataByPage(InterDataVo interDataVo);
	
	/**
     * 获取单个数据接口信息
     * @param dialogTypesVo
     * @return
     */
	Result getInterDataById(InterDataVo interDataVo);
	
	/**
     * 添加单个数据接口信息
     * @param dialogTypesVo
     * @return
     */
	Result addInterface(InterDataVo interDataVo);
	
	/**
     * 修改单个数据接口信息
     * @param dialogTypesVo
     * @return
     */
	Result modifyInterface(InterDataVo interDataVo);
	
	/**
     * 激活单个数据接口信息
     * @param dialogTypesVo
     * @return
     */
	Result activeInterface(InterDataVo interDataVo);
	
	/**
     * 删除数据接口信息
     * @param dialogTypesVo
     * @return
     */
	Result delInterface(InterDataVo interDataVo);
}
