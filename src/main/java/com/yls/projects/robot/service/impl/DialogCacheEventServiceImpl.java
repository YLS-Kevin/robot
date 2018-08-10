package com.yls.projects.robot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yls.frame.common.Exception.RobotException;
import com.yls.frame.common.enums.ResultEnum;
import com.yls.projects.robot.dao.DialogCacheEventDao;
import com.yls.projects.robot.entity.DialogCacheEventWithBLOBs;
import com.yls.projects.robot.service.DialogCacheEventService;

/**
 * 对话数据缓存事件服务
 *
 * @author Alex
 * @create 2018年6月11日
 */
@Service("dialogCacheEventService")
@Transactional(value = "robotTransactionManager", readOnly = true, rollbackFor = Exception.class)
public class DialogCacheEventServiceImpl implements DialogCacheEventService {

	@Autowired
	private DialogCacheEventDao dialogCacheEventDao;
	
	@Transactional(value = "robotTransactionManager", readOnly = false, rollbackFor = Exception.class)
	@Override
	public int insert(DialogCacheEventWithBLOBs dialogCacheEventWithBloBs) {
		Integer integer = null;
		try {
			integer = dialogCacheEventDao.insert(dialogCacheEventWithBloBs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		return integer;
	}

	@Transactional(value = "robotTransactionManager", readOnly = false, rollbackFor = Exception.class)
	@Override
	public int delete(long id) {
		Integer integer = null;
		try {
			integer = dialogCacheEventDao.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		return integer;
	}

	@Override
	public List<DialogCacheEventWithBLOBs> list() {
		List<DialogCacheEventWithBLOBs> lists = null;
		try {
			lists = dialogCacheEventDao.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		return lists;
	}
	
	

}
