package com.yls.projects.robot.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yls.frame.common.Exception.RobotException;
import com.yls.frame.common.enums.ResultEnum;
import com.yls.frame.common.utils.ResultUtil;
import com.yls.frame.common.utils.StringUtils;
import com.yls.frame.common.web.Result;
import com.yls.projects.common.Page.PageBaen;
import com.yls.projects.robot.dao.RobotClientUseLogDao;
import com.yls.projects.robot.entity.RobotClientUseLog;
import com.yls.projects.robot.service.RobotClientUserLogService;

/**
 * 机器人终端使用日志表 Service 实现类
 * 
 * @author 陈俊
 * @date 2018年6月9日
 */
@Service("robotClientUserLogService")
@Transactional(readOnly = true)
public class RobotClientUserLogServiceImpl implements RobotClientUserLogService {

	@Autowired
	private RobotClientUseLogDao robotClientUseLogDao;

	/**
	 * 日志列表
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	public Result listChatLog(RobotClientUseLog robotClientUseLog) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(robotClientUseLog.getIdCu()) || robotClientUseLog.getSize() == null
				|| robotClientUseLog.getPage() == null) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		// 分页参数必须大于等于0
		if (robotClientUseLog.getSize() <= 0 || robotClientUseLog.getPage() <= 0) {
			throw new RobotException(ResultEnum.PAGING_SIZE_IS_LESSTHAN0_ERROR);
		}

		// 获取起始条数
		Integer startIndex = 0;
		if (robotClientUseLog.getPage() > 1) {
			startIndex = robotClientUseLog.getSize() * (robotClientUseLog.getPage() - 1);
		}
		robotClientUseLog.setStartIndex(startIndex);

		List<Map<String, Object>> dataList = robotClientUseLogDao.getListByIdCu(robotClientUseLog);
		Integer total = robotClientUseLogDao.getCount(robotClientUseLog);

		PageBaen pageBaen = new PageBaen();
		pageBaen.setSize(robotClientUseLog.getSize());
		pageBaen.setPage(robotClientUseLog.getPage());
		pageBaen.setTotal(total);
		pageBaen.setList(dataList);
		return ResultUtil.success(pageBaen);

	}

}
