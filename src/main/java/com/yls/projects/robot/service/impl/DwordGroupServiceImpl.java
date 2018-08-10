package com.yls.projects.robot.service.impl;

import java.util.HashMap;
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
import com.yls.projects.robot.dao.DwordGroupDao;
import com.yls.projects.robot.dao.WordsDao;
import com.yls.projects.robot.entity.DwordGroup;
import com.yls.projects.robot.service.DwordGroupService;
import com.yls.projects.robot.vo.DwordGroupVo;
import com.yls.projects.robot.vo.WordsVo;

/**
 * 动态词组表 Service 实现类
 * 
 * @author 陈俊
 * @date 2018年5月21日
 */
@Service("dwordGroupService")
@Transactional(readOnly = true)
public class DwordGroupServiceImpl implements DwordGroupService {

	@Autowired
	private DwordGroupDao dwordGroupDao;

	@Autowired
	private WordsDao wordsDao;

	/**
	 * 添加数据
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class, readOnly = false)
	public Result addDynaWordType(DwordGroup dwordGroup) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(dwordGroup.getGroupName()) || StringUtils.isBlank(dwordGroup.getGroupCnName())
				|| StringUtils.isBlank(dwordGroup.getIsShare()) || StringUtils.isBlank(dwordGroup.getIdAc())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		
		//判断是否有重名的
		if(dwordGroupDao.getCountByName(dwordGroup) > 0) {
			throw new RobotException(ResultEnum.EXISTS_SAME_DWORD);
		}
		
		if(dwordGroupDao.getCountByCnName(dwordGroup) > 0) {
			throw new RobotException(ResultEnum.EXISTS_SAME_DCN_WORD);
		}
		
		Map<String, Object> map = new HashMap<>(16);
		// 当前登录的用户
		// DialogUser dialogUser = (DialogUser)
		// SecurityUtils.getSubject().getSession().getAttribute("dialogUser");
		// String dialogUserId = dialogUser.getUserId();
		String dialogUserId = "1";

		String Id = IdGen.uuid();
		map.put("Id", Id);

		DwordGroup entity = new DwordGroup();
		entity.setId(Id);
		entity.setIdAc(dwordGroup.getIdAc());
		entity.setGroupName(dwordGroup.getGroupName());
		entity.setGroupCnName(dwordGroup.getGroupCnName());
		entity.setIsShare(dwordGroup.getIsShare());
		entity.setIsDefault(DwordGroup.DEFAULT_NO);
		entity.setState(DwordGroup.STATE_1);
		entity.setCreateBy(dialogUserId);
		entity.setUpdateBy(dialogUserId);
		// 插入数据
		try {
			dwordGroupDao.insert(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}

		return ResultUtil.success(map);
	}

	/**
	 * 更新数据
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class, readOnly = false)
	public Result modifyDynaWordType(DwordGroup dwordGroup) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(dwordGroup.getGroupName()) || StringUtils.isBlank(dwordGroup.getGroupCnName())
				|| StringUtils.isBlank(dwordGroup.getIsShare()) || StringUtils.isBlank(dwordGroup.getId())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		
		//判断是否有重名的
		if(dwordGroupDao.getCountByName(dwordGroup) > 0) {
			throw new RobotException(ResultEnum.EXISTS_SAME_DWORD);
		}
		
		if(dwordGroupDao.getCountByCnName(dwordGroup) > 0) {
			throw new RobotException(ResultEnum.EXISTS_SAME_DCN_WORD);
		}
		

		// 当前登录的用户
		// DialogUser dialogUser = (DialogUser)
		// SecurityUtils.getSubject().getSession().getAttribute("dialogUser");
		// String dialogUserId = dialogUser.getUserId();
		String dialogUserId = "1";

		DwordGroup entity = new DwordGroup();
		entity.setId(dwordGroup.getId());
		entity.setGroupName(dwordGroup.getGroupName());
		entity.setGroupCnName(dwordGroup.getGroupCnName());
		entity.setIsShare(dwordGroup.getIsShare());
		entity.setUpdateBy(dialogUserId);
		// 插入数据
		try {
			dwordGroupDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}

		return ResultUtil.success(null);
	}

	/**
	 * 动态词组列表
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	public Result listDynaWordType(DwordGroupVo dwordGroupVo) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(dwordGroupVo.getIdAc()) || dwordGroupVo.getSize() == null
				|| dwordGroupVo.getPage() == null) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		// 分页参数必须大于等于0
		if (dwordGroupVo.getSize() <= 0 || dwordGroupVo.getPage() <= 0) {
			throw new RobotException(ResultEnum.PAGING_SIZE_IS_LESSTHAN0_ERROR);
		}

		// 获取起始条数
		Integer startIndex = 0;
		if (dwordGroupVo.getPage() > 1) {
			startIndex = dwordGroupVo.getSize() * (dwordGroupVo.getPage() - 1);
		}
		dwordGroupVo.setStartIndex(startIndex);

		List<Map<String, Object>> dataList = dwordGroupDao.getListByIdAc(dwordGroupVo);
		Integer total = dwordGroupDao.getCount(dwordGroupVo);

		PageBaen pageBaen = new PageBaen();
		pageBaen.setSize(dwordGroupVo.getSize());
		pageBaen.setPage(dwordGroupVo.getPage());
		pageBaen.setTotal(total);
		pageBaen.setList(dataList);
		return ResultUtil.success(pageBaen);
	}

	/**
	 * 删除动态词组
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class, readOnly = false)
	public Result delDynaWordType(DwordGroup dwordGroup) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(dwordGroup.getId())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		WordsVo wordsVo = new WordsVo();
		wordsVo.setIdDwg(dwordGroup.getId());
		Integer count = wordsDao.getCount(wordsVo);
		if (count <= 0) {
			// 删除数据
			try {
				dwordGroupDao.delete(dwordGroup);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RobotException(ResultEnum.FAIL);
			}
		} else {
			throw new RobotException(ResultEnum.WORDS_GOUNP_FAIL);
		}

		return ResultUtil.success();
	}

}
