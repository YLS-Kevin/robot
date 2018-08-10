package com.yls.projects.robot.dao;

import java.util.List;

import com.yls.projects.robot.entity.DialogCacheEvent;
import com.yls.projects.robot.entity.DialogCacheEventWithBLOBs;

public interface DialogCacheEventDao {
    int deleteByPrimaryKey(long id);

    int insert(DialogCacheEventWithBLOBs record);

    int insertSelective(DialogCacheEventWithBLOBs record);

    DialogCacheEventWithBLOBs selectByPrimaryKey(long id);

    int updateByPrimaryKeySelective(DialogCacheEventWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(DialogCacheEventWithBLOBs record);

    int updateByPrimaryKey(DialogCacheEvent record);
    
    List<DialogCacheEventWithBLOBs> list();
}