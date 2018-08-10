package com.yls.projects.robot.dao;

import java.util.List;

import com.yls.projects.robot.entity.DialogAnswerExp;
import com.yls.projects.robot.vo.RobotsVo;

public interface DialogAnswerExpDao {
    int deleteByPrimaryKey(String id);

    int insert(DialogAnswerExp record);

    int insertSelective(DialogAnswerExp record);

    DialogAnswerExp selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DialogAnswerExp record);

    int updateByPrimaryKey(DialogAnswerExp record);
    
    int updateByCidAndStype(DialogAnswerExp record);
    
    List<DialogAnswerExp> getAnswerExpByRobotId(RobotsVo robotsVo);
    
    /**
     * 根据机器人id与异常类型查找list
     * */
    List<DialogAnswerExp> listAnswerExpByRobotIdAndStype(RobotsVo robotsVo);
}