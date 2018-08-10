package com.yls.projects.robot.dao;

import java.util.List;

import com.yls.projects.robot.entity.RobotVocation;

public interface RobotVocationDao {
    int deleteByPrimaryKey(String id);

    int insert(RobotVocation record);

    int insertSelective(RobotVocation record);

    RobotVocation selectByPrimaryKey(String id);
    
    List<RobotVocation> findAllVocation();

    int updateByPrimaryKeySelective(RobotVocation record);

    int updateByPrimaryKey(RobotVocation record);
    
    
}