/**
 * 
 */
package com.yls.projects.robot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yls.projects.robot.entity.DialogType2;

/**
 * @author huangsy
 * @date 2018年3月7日上午9:10:37
 */
public interface ClientCacheMapper {

	List<DialogType2> findDialogTypeById(@Param(value = "cid") String cid, @Param(value = "idM") String idM);
	
}
