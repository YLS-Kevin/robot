package com.yls.projects.robot.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yls.projects.robot.entity.DialogCacheEventWithBLOBs;

/**
 * 对话数据缓存事件服务
 *
 * @author Alex
 * @create 2018年6月11日
 */
public interface DialogCacheEventService {

	int insert(DialogCacheEventWithBLOBs dialogCacheEventWithBloBs);
	
	int delete(@Param(value = "id") long id);
	
	List<DialogCacheEventWithBLOBs> list();
}
