package com.yls.projects.robot.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yls.frame.common.Exception.RobotException;
import com.yls.frame.common.enums.ResultEnum;
import com.yls.frame.common.utils.IdGen;
import com.yls.frame.common.utils.ResultUtil;
import com.yls.frame.common.utils.StringUtils;
import com.yls.frame.common.web.Result;
import com.yls.projects.common.Page.PageBaen;
import com.yls.projects.common.cache.ActionEnum;
import com.yls.projects.common.cache.DialogCacheListenerService;
import com.yls.projects.robot.dao.DialogCacheMapper;
import com.yls.projects.robot.dao.InterDataDao;
import com.yls.projects.robot.entity.DialogMoreAndOne2;
import com.yls.projects.robot.entity.InterData;
import com.yls.projects.robot.service.InterDataService;
import com.yls.projects.robot.utils.GsonAdatper;
import com.yls.projects.robot.vo.InterDataVo;

/**
 * @Author:Alex
 * @Description: 我的数据接口
 * @Date: 11:00 2018/5/31
 */
@Service("interDataService")
@Transactional(value = "robotTransactionManager", readOnly = true, rollbackFor = Exception.class)
public class InterDataServiceImpl implements InterDataService {

	@Autowired
	private InterDataDao interDataDao;
	
	@Autowired
	private DialogCacheMapper dialogCacheMapper;

	@Autowired
	private DialogCacheListenerService dialogCacheListenerService;

	@Override
	public Result listInterDataByPage(InterDataVo interDataVo) {

		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(interDataVo.getIdAc()) || interDataVo.getSize() == null
				|| interDataVo.getPage() == null) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		// 分页参数必须大于等于0
		if (interDataVo.getSize() <= 0 || interDataVo.getPage() <= 0) {
			throw new RobotException(ResultEnum.PAGING_SIZE_IS_LESSTHAN0_ERROR);
		}

		// 获取起始条数
		Integer startIndex = 0;
		if (interDataVo.getPage() > 1) {
			startIndex = interDataVo.getSize() * (interDataVo.getPage() - 1);
		}
		interDataVo.setStartIndex(startIndex);

		List<InterData> list = interDataDao.listInterDataByPage(interDataVo);
		Integer total = interDataDao.getCount(interDataVo);
		PageBaen pageBaen = new PageBaen();
		pageBaen.setSize(interDataVo.getSize());
		pageBaen.setPage(interDataVo.getPage());
		pageBaen.setTotal(total);
		pageBaen.setList(list);
		return ResultUtil.success(pageBaen);
	}

	@Override
	public Result getInterDataById(InterDataVo interDataVo) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(interDataVo.getId())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		InterData entity = interDataDao.getInterDataById(interDataVo);
		return ResultUtil.success(entity);
	}

	@Transactional(value = "robotTransactionManager", readOnly = false, rollbackFor = Exception.class)
	@Override
	public Result addInterface(InterDataVo interDataVo) {
		if (StringUtils.isBlank(interDataVo.getIdAc())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		
		if(interDataDao.getCountByInterName(interDataVo) > 0) {
			throw new RobotException(ResultEnum.INTERDATA_ALREADY_EXISTS);
		}
		
		interDataVo.setId(IdGen.uuid());
		interDataVo.setParacode("UTF-8");
		interDataVo.setState(InterDataVo.STATE_1);
		interDataVo.setCreateBy("1");
		interDataVo.setUpdateBy("1");
		interDataVo.setDelFlag(interDataVo.delFlag_1);
		// 插入数据
		try {
			interDataDao.insert(interDataVo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		return ResultUtil.success(null);
	}

	@Transactional(value = "robotTransactionManager", readOnly = false, rollbackFor = Exception.class)
	@Override
	public Result modifyInterface(InterDataVo interDataVo) {
		if (StringUtils.isBlank(interDataVo.getId())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		
		if(interDataDao.getCountByInterName(interDataVo) > 0) {
			throw new RobotException(ResultEnum.INTERDATA_ALREADY_EXISTS);
		}
		// 获取修改前对话数据
		List<DialogMoreAndOne2> updateDataLists = dialogCacheMapper.findDialogByInterDataId(interDataVo.getId());
		GsonBuilder gsonBuilder = new GsonBuilder();
		// 注册自定义String的适配器
		gsonBuilder.registerTypeAdapter(String.class, GsonAdatper.STRING);
		Gson gsonUpdate = gsonBuilder.create();
		String updateData = gsonUpdate.toJson(updateDataLists);
		try {
			interDataDao.updateByPrimaryKey(interDataVo);
			dialogCacheListenerService.ActionDialogCacheEvent(interDataVo.getId(),
					ActionEnum.UPDATE_INTERFACE.getCode(), updateData, "", "");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		return ResultUtil.success(null);
	}

	@Transactional(value = "robotTransactionManager", readOnly = false, rollbackFor = Exception.class)
	@Override
	public Result activeInterface(InterDataVo interDataVo) {
		if (StringUtils.isBlank(interDataVo.getIds()) || StringUtils.isBlank(interDataVo.getState())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		String[] ids = interDataVo.getIds().split(",");
		List<String> idsList = Arrays.asList(ids);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", interDataVo.getState());
		map.put("ids", idsList);
		try {
			interDataDao.batchActiveInter(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		return ResultUtil.success(null);
	}

	@Transactional(value = "robotTransactionManager", readOnly = false, rollbackFor = Exception.class)
	@Override
	public Result delInterface(InterDataVo interDataVo) {
		if (StringUtils.isBlank(interDataVo.getIds())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		String[] ids = interDataVo.getIds().split(",");
		List<String> idsList = Arrays.asList(ids);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", idsList);
		try {
			interDataDao.delInterface(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		return ResultUtil.success(null);
	}

}
