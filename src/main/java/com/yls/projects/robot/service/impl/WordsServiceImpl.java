package com.yls.projects.robot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yls.frame.common.Exception.RobotException;
import com.yls.frame.common.enums.ResultEnum;
import com.yls.frame.common.utils.IdGen;
import com.yls.frame.common.utils.ResultUtil;
import com.yls.frame.common.utils.StringUtils;
import com.yls.frame.common.web.Result;
import com.yls.projects.common.Page.PageBaen;
import com.yls.projects.robot.dao.WordsDao;
import com.yls.projects.robot.entity.Words;
import com.yls.projects.robot.service.WordsService;
import com.yls.projects.robot.vo.WordsVo;

/**
 * 动态词表 Service 实现类
 * 
 * @author 陈俊
 * @date 2018年5月22日
 */
@Service("wordsService")
@Transactional(readOnly = true)
public class WordsServiceImpl implements WordsService {

	@Autowired
	private WordsDao wordsDao;

	/**
	 * 添加动态词
	 * 
	 * @param words
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class, readOnly = false)
	public Result addDynaWord(Words words) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(words.getWname()) || StringUtils.isBlank(words.getIdAc()) || words.getWften() == null
				|| StringUtils.isBlank(words.getState())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}

		Map<String, Object> map = new HashMap<>(16);
		// 当前登录的用户
		// DialogUser dialogUser = (DialogUser)
		// SecurityUtils.getSubject().getSession().getAttribute("dialogUser");
		// String dialogUserId = dialogUser.getUserId();
		String dialogUserId = "1";

		String Id = IdGen.uuid();
		map.put("Id", Id);

		Words entity = new Words();
		entity.setId(Id);
		if (StringUtils.isNotBlank(words.getIdDwg())) {
			entity.setIdDwg(words.getIdDwg());
		}
		entity.setIdAc(words.getIdAc());
		entity.setWname(words.getWname());
		entity.setWx(Words.WX_n);
		entity.setWften(words.getWften());
		entity.setAutoin(Words.AUTOIN_1);
		entity.setState(words.getState());
		entity.setRemarks(words.getRemarks());
		entity.setCreateBy(dialogUserId);
		entity.setUpdateBy(dialogUserId);

		// 查询该词是否已经存在
		Integer count = wordsDao.getListByName(words);
		if (count == 0) {
			// 插入数据
			try {
				wordsDao.insert(entity);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RobotException(ResultEnum.WORDS_FAIL);
			}
		} else {
			throw new RobotException(ResultEnum.WORDS_FAIL);
		}
		return ResultUtil.success(map);
	}

	/**
	 * 修改动态词
	 * 
	 * @param words
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class, readOnly = false)
	public Result modifyDynaWord(Words words) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(words.getId()) || StringUtils.isBlank(words.getWname()) || words.getWften() == null
				|| StringUtils.isBlank(words.getState())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}

		// 当前登录的用户
		// DialogUser dialogUser = (DialogUser)
		// SecurityUtils.getSubject().getSession().getAttribute("dialogUser");
		// String dialogUserId = dialogUser.getUserId();
		String dialogUserId = "1";

		Words entity = new Words();
		entity.setId(words.getId());
		entity.setWname(words.getWname());
		entity.setWften(words.getWften());
		entity.setState(words.getState());
		entity.setRemarks(words.getRemarks());
		entity.setUpdateBy(dialogUserId);

		// 更新数据
		try {
			wordsDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.WORDS_FAIL);
		}

		return ResultUtil.success(null);
	}

	/**
	 * 动态词列表
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	public Result listDynaWord(WordsVo wordsVo) {
		// 如果参数为空，则抛出参数不完整异常
		if (wordsVo.getSize() == null || wordsVo.getPage() == null) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		// 分页参数必须大于等于0
		if (wordsVo.getSize() <= 0 || wordsVo.getPage() <= 0) {
			throw new RobotException(ResultEnum.PAGING_SIZE_IS_LESSTHAN0_ERROR);
		}
//		if (StringUtils.isBlank(wordsVo.getIdAc())) {
//			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
//		}

		// 获取起始条数
		Integer startIndex = 0;
		if (wordsVo.getPage() > 1) {
			startIndex = wordsVo.getSize() * (wordsVo.getPage() - 1);
		}
		wordsVo.setStartIndex(startIndex);

		List<Map<String, Object>> dataList = wordsDao.getListByIdDwg(wordsVo);
		Integer total = wordsDao.getCount(wordsVo);

		PageBaen pageBaen = new PageBaen();
		pageBaen.setSize(wordsVo.getSize());
		pageBaen.setPage(wordsVo.getPage());
		pageBaen.setTotal(total);
		pageBaen.setList(dataList);

		return ResultUtil.success(pageBaen);
	}

	/**
	 * 删除动态词
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class, readOnly = false)
	public Result delDynaWord(Words words) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(words.getId())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		// 删除数据
		try {
			wordsDao.delete(words);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		return ResultUtil.success();
	}

	/**
	 * 批量更新数据
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class, readOnly = false)
	public Result modifyDynaWordByState(WordsVo wordsVo) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(wordsVo.getIds()) || StringUtils.isBlank(wordsVo.getState())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}

		// 当前登录的用户
		// DialogUser dialogUser = (DialogUser)
		// SecurityUtils.getSubject().getSession().getAttribute("dialogUser");
		// String dialogUserId = dialogUser.getUserId();
		String dialogUserId = "1";

		Gson gson = new Gson();
		List<Map<String, Object>> idsList = gson.fromJson(wordsVo.getIds(),
				new TypeToken<ArrayList<Map<String, Object>>>() {
				}.getType());
		// 人机对话idList
		List<String> idList = new ArrayList<>();
		for (Map<String, Object> idsMap : idsList) {
			idList.add(idsMap.get("id").toString());
		}
		// 批量更新数据
		try {
			wordsDao.batchUpdate(wordsVo.getState(), dialogUserId, idList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		return ResultUtil.success();
	}

	/**
	 * 添加动态词
	 * 专为动态词添加 ,如果有就替换
	 * 
	 * @param words
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class, readOnly = false)
	public Result addReplaceDynaWord(Words words) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(words.getWname()) || StringUtils.isBlank(words.getIdAc()) || words.getWften() == null
				|| StringUtils.isBlank(words.getState())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}

		Map<String, Object> map = new HashMap<>(16);
		// 当前登录的用户
		// DialogUser dialogUser = (DialogUser)
		// SecurityUtils.getSubject().getSession().getAttribute("dialogUser");
		// String dialogUserId = dialogUser.getUserId();
		String dialogUserId = "1";

		String Id = IdGen.uuid();
		map.put("Id", Id);

		Words entity = new Words();
		entity.setId(Id);
		if (StringUtils.isNotBlank(words.getIdDwg())) {
			entity.setIdDwg(words.getIdDwg());
		}
		entity.setIdAc(words.getIdAc());
		entity.setWname(words.getWname());
		entity.setWx(Words.WX_n);
		entity.setWften(words.getWften());
		entity.setAutoin(Words.AUTOIN_1);
		entity.setState(words.getState());
		entity.setRemarks(words.getRemarks());
		entity.setCreateBy(dialogUserId);
		entity.setUpdateBy(dialogUserId);

		List<Words> listWord = wordsDao.getEntityByName(words);
		
		if (listWord != null && listWord.size() > 0) {
			Words words2 = listWord.get(0);
			if (StringUtils.isBlank(words2.getIdDwg())) {
				// 插入数据
				try {
					wordsDao.insertReplace(entity);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RobotException(ResultEnum.WORDS_FAIL);
				}
			} else {
				throw new RobotException(ResultEnum.WORD_ALREADY_EXISTS);
			}
		} else {
			try {
				wordsDao.commonInsert(entity);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RobotException(ResultEnum.WORDS_FAIL);
			}
		}
		return ResultUtil.success(map);
	}

}
