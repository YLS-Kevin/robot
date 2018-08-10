package com.yls.projects.robot.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yls.frame.common.Exception.RobotException;
import com.yls.frame.common.enums.ResultEnum;
import com.yls.frame.common.utils.ResultUtil;
import com.yls.frame.common.web.Result;
import com.yls.projects.robot.dao.RobotVocationDao;
import com.yls.projects.robot.entity.RobotVocation;
import com.yls.projects.robot.service.RobotVocationService;

/**
 * 
 * 行业实现类
 * @author 陈华湛
 * @date 2018年5月23日下午6:25:31
 */
@Service("robotVocationService")
public class RobotVocationServiceImpl implements RobotVocationService{
	
	private static Logger logger = LoggerFactory.getLogger(RobotsServiceImpl.class);
	
	@Autowired
	private RobotVocationDao robotVocationDao;

	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	public Result findAllVocation() {
		
		List<RobotVocation> findAllVocation = null;
		try {
			findAllVocation = robotVocationDao.findAllVocation();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("执行--/configRobot请求--robotVocationDao.findAllVocation()--sql出错！");
			throw new RobotException(ResultEnum.FAIL);
		}
		
		return ResultUtil.success(findAllVocation);
	}

}
