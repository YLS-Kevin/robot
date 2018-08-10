package com.yls.projects.robot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yls.projects.dialog.vo.DialogTypesVo;
import com.yls.projects.robot.entity.RobotsAndDialogTypes;
import com.yls.projects.robot.vo.RobotsVo;

public interface RobotsAndDialogTypesDao {
    int deleteByPrimaryKey(String id);

    int insert(RobotsAndDialogTypes record);

    int insertSelective(RobotsAndDialogTypes record);

    RobotsAndDialogTypes selectByPrimaryKey(String id);

    RobotsAndDialogTypes selectByCidAndIdM(RobotsAndDialogTypes robotsAndDialogTypes);
    
    int updateByPrimaryKeySelective(RobotsAndDialogTypes record);
    
    int updateByCidAndIdM(RobotsAndDialogTypes record);

    int updateByPrimaryKey(RobotsAndDialogTypes record);
    
    List<RobotsAndDialogTypes> getAbilitys(RobotsVo robotsVo);
    
    /**
	 * 获取是否机器人应用了该能力模块id
	 * 
	 * @param 
	 * @return
	 */
	Integer getCountByDialogId(@Param(value = "dialogId")String dialogId);
}