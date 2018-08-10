package com.yls.projects.robot.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yls.frame.common.Exception.RobotException;
import com.yls.frame.common.enums.ResultEnum;
import com.yls.frame.common.utils.IdGen;
import com.yls.frame.common.utils.ResultUtil;
import com.yls.frame.common.utils.StringUtils;
import com.yls.frame.common.web.Result;
import com.yls.projects.common.Page.PageBaen;
import com.yls.projects.robot.dao.DwordsDao;
import com.yls.projects.robot.entity.Dwords;
import com.yls.projects.robot.service.DwordsService;
import com.yls.projects.robot.vo.DwordsVo;

/**
 * 动态词表 Service 实现类
 * 
 * @author 陈俊
 * @date 2018年5月22日
 */
@Service("dwordsService")
public class DwordsServiceImpl implements DwordsService {

	@Autowired
	private DwordsDao dwordsDao;

	/**
	 * 添加数据
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	public Result addDynaWord(Dwords dwords) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(dwords.getIdDwg()) || StringUtils.isBlank(dwords.getDwname())
				|| StringUtils.isBlank(dwords.getIdAc())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}

		Dwords entity = new Dwords();
		entity.setId(IdGen.uuid());
		entity.setIdAc(dwords.getIdAc());
		entity.setIdDwg(dwords.getIdDwg());
		entity.setDwname(dwords.getDwname());
		entity.setState(Dwords.STATE_1);

		// 插入数据
		try {
			dwordsDao.insert(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}

		return ResultUtil.success(null);
	}

	/**
	 * 修改数据
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	public Result modifyDynaWord(Dwords dwords) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(dwords.getId()) || StringUtils.isBlank(dwords.getDwname())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}

		Dwords entity = new Dwords();
		entity.setId(dwords.getId());
		entity.setDwname(dwords.getDwname());

		// 更新数据
		try {
			dwordsDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}

		return ResultUtil.success(null);
	}

	/**
	 * 动态词列表
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	public Result listDynaWord(DwordsVo dwordsVo) {

		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(dwordsVo.getIdDwg()) || dwordsVo.getSize() == null || dwordsVo.getPage() == null) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		// 分页参数必须大于等于0
		if (dwordsVo.getSize() <= 0 || dwordsVo.getPage() <= 0) {
			throw new RobotException(ResultEnum.PAGING_SIZE_IS_LESSTHAN0_ERROR);
		}

		// 获取起始条数
		Integer startIndex = 0;
		if (dwordsVo.getPage() > 1) {
			startIndex = dwordsVo.getSize() * (dwordsVo.getPage() - 1);
		}
		dwordsVo.setStartIndex(startIndex);

		List<Map<String, Object>> dataList = dwordsDao.getListByIdDwg(dwordsVo);
		Integer total = dwordsDao.getCount(dwordsVo);

		PageBaen pageBaen = new PageBaen();
		pageBaen.setSize(dwordsVo.getSize());
		pageBaen.setPage(dwordsVo.getPage());
		pageBaen.setTotal(total);
		pageBaen.setList(dataList);
		return ResultUtil.success(pageBaen);
	}

}
