package com.yls.projects.robot.dao;

import java.util.List;

import com.yls.projects.robot.entity.RobotsModule;
import com.yls.projects.robot.vo.RobotsVo;

public interface RobotsModuleDao {
    int deleteByPrimaryKey(String cidM);

    int insert(RobotsModule record);

    int insertSelective(RobotsModule record);

    RobotsModule selectByPrimaryKey(String cidM);

    int updateByPrimaryKeySelective(RobotsModule record);

    int updateByPrimaryKey(RobotsModule record);
    
    List<RobotsModule> getRobotInfoByIdAndMid(RobotsVo robotsVo);
    
    RobotsModule getDialogIdByIdAndMid(RobotsVo robotsVo);
    
    RobotsModule getRobotModelById(RobotsVo robotsVo);
    
    int getCountByCidMname(RobotsVo robotsVo);
}