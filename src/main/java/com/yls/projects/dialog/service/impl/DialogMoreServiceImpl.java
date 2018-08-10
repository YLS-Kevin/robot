package com.yls.projects.dialog.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.yls.frame.common.web.JsonResult;
import com.yls.frame.common.web.Result;
import com.yls.projects.common.Page.PageBaen;
import com.yls.projects.dialog.dao.DialogMoreDao;
import com.yls.projects.dialog.entity.DialogMore;
import com.yls.projects.dialog.entity.DialogTypes;
import com.yls.projects.dialog.service.DialogMoreService;
import com.yls.projects.dialog.vo.DialogMoreVo;
import com.yls.projects.dialog.vo.DialogTypesVo;
import com.yls.projects.robot.dao.DialogDao;
import com.yls.projects.robot.entity.DialogUser;

@Service("dialogMoreService")
public class DialogMoreServiceImpl implements DialogMoreService {

	@Autowired
	private DialogMoreDao dialogMoreDao;

	@Autowired
	private DialogDao dialogDao;

	/**
	 * 分页获取主题信息
	 * 
	 * @param dialogMoreVo
	 * @return
	 */
	@Transactional(value = "robotTransactionManager", readOnly = true)
	@Override
	public JsonResult dialogMoreList(DialogMoreVo dialogMoreVo) throws RobotException {
		JsonResult result = new JsonResult();
		if (dialogMoreVo == null) {
			// edit by Alex for 统一异常处理,已下请自行修改 2018-05-05
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		if (dialogMoreVo.getSize() == null) {
			throw new RobotException(ResultEnum.PAGING_SIZE_IS_NULL_ERROR);
			// end by Alex for 2018-05-05
		}
		if (dialogMoreVo.getSize() <= 0) {
			throw new RobotException(ResultEnum.PAGING_SIZE_IS_LESSTHAN0_ERROR);
		}
		if (dialogMoreVo.getPage() == null) {
			throw new RobotException(ResultEnum.PAGING_SIZE_IS_NULL_ERROR);
		}
		if (dialogMoreVo.getPage() <= 0) {
			throw new RobotException(ResultEnum.PAGING_SIZE_IS_LESSTHAN0_ERROR);
		}
		if (dialogMoreVo.getIdAc() == null) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		if (dialogMoreVo.getIdDt() == null) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}

		// 获取起始条数
		Integer startIndex = 0;
		if (dialogMoreVo.getPage() > 1) {
			startIndex = dialogMoreVo.getSize() * (dialogMoreVo.getPage() - 1);
		}
		dialogMoreVo.setStartIndex(startIndex);

		List<Map<String, Object>> dataList = this.dialogMoreDao.dialogMoreList(dialogMoreVo);
		Integer total = this.dialogMoreDao.getCount(dialogMoreVo);

		PageBaen pageBaen = new PageBaen();
		pageBaen.setSize(dialogMoreVo.getSize());
		pageBaen.setPage(dialogMoreVo.getPage());
		pageBaen.setTotal(total);
		pageBaen.setList(dataList);

		result.setSuccess(pageBaen);

		return result;
	}

	/**
	 * 新增主题
	 *
	 * @param dialogMore
	 * @param dialogUser
	 * @return
	 * @throws RobotException
	 */
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	@Override
	public JsonResult addDialogMore(DialogMore dialogMore, DialogUser dialogUser) throws RobotException {
		JsonResult result = new JsonResult();
		if (dialogUser == null) {
			throw new RobotException(ResultEnum.SESSION_INVALIDATION_ERROR);
		}
		if (dialogUser.getUserId() == null) {
			throw new RobotException(ResultEnum.SESSION_INVALIDATION_ERROR);
		}
		if (dialogMore.getTname() == null) {
			throw new RobotException(ResultEnum.PARAMETER_IS_NULL_ERROR);
		}
		if (dialogMore.getIdAc() == null) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		if (dialogMore.getIdDt() == null) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		
		if(dialogMoreDao.getCountByThemeName(dialogMore)> 0) {
			throw new RobotException(ResultEnum.THEME_ALREADY_EXISTS);
		}

		dialogMore.setId(IdGen.uuid());
		dialogMore.setState(DialogMore.STATE_1);
		dialogMore.setDelFlag(DialogMore.DEL_FLAG_0);
		dialogMore.setCreateBy(dialogUser.getUserId());
		dialogMore.setUpdateBy(dialogUser.getUserId());

		this.dialogMoreDao.insertSelective(dialogMore);

		Map<String, Object> resultMap = new HashMap<>(16);
		resultMap.put(DialogMore.ID, dialogMore.getId());
		result.setSuccess(resultMap);
		return result;
	}

	/**
	 * 逻辑删除主题
	 *
	 * @param id
	 * @param dialogUser
	 * @return
	 * @throws RobotException
	 */
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	@Override
	public JsonResult delDialogMore(String id, DialogUser dialogUser) throws RobotException {
		JsonResult result = new JsonResult();
		if (dialogUser == null) {
			throw new RobotException(ResultEnum.SESSION_INVALIDATION_ERROR);
		}
		if (dialogUser.getUserId() == null) {
			throw new RobotException(ResultEnum.SESSION_INVALIDATION_ERROR);
		}
		if (id == null) {
			throw new RobotException(ResultEnum.PARAMETER_IS_NULL_ERROR);
		}

		DialogTypesVo dialogTypesVo = new DialogTypesVo();
		dialogTypesVo.setIdAp(id);
		Integer count = dialogDao.getAllCount(dialogTypesVo);
		if (count > 0) {
			throw new RobotException(ResultEnum.EXISTS_DIALOG_ERROR);
		}

		DialogMore dialogMore = new DialogMore();
		dialogMore.setId(id);
		dialogMore.setUpdateBy(dialogUser.getUserId());
		dialogMore.setUpdateDate(new Date());
		dialogMore.setDelFlag(DialogTypes.DEL_FLAG_1);
		this.dialogMoreDao.updateByPrimaryKeySelective(dialogMore);
		Map<String, Object> resultMap = new HashMap<>(16);
		resultMap.put(DialogMore.ID, id);
		result.setSuccess(resultMap);

		return result;
	}

	/**
	 * 修改主题
	 *
	 * @param dialogMore
	 * @return
	 * @throws RobotException
	 */
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	@Override
	public JsonResult updateDialogMore(DialogMore dialogMore, DialogUser dialogUser) throws RobotException {
		JsonResult result = new JsonResult();
		if (dialogUser == null) {
			throw new RobotException(ResultEnum.SESSION_INVALIDATION_ERROR);
		}
		if (dialogUser.getUserId() == null) {
			throw new RobotException(ResultEnum.SESSION_INVALIDATION_ERROR);
		}
		if (dialogMore == null) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		if (dialogMore.getId() == null) {
			throw new RobotException(ResultEnum.PARAMETER_IS_NULL_ERROR);
		}
		if (dialogMore.getTname() == null) {
			throw new RobotException(ResultEnum.PARAMETER_IS_NULL_ERROR);
		}
		
		if(dialogMoreDao.getCountByThemeName(dialogMore)> 0) {
			throw new RobotException(ResultEnum.THEME_ALREADY_EXISTS);
		}
		
		dialogMore.setUpdateBy(dialogUser.getUserId());
		dialogMore.setUpdateDate(new Date());
		this.dialogMoreDao.updateByPrimaryKeySelective(dialogMore);
		Map<String, Object> resultMap = new HashMap<>(16);
		resultMap.put(DialogMore.ID, dialogMore.getId());
		result.setSuccess(resultMap);
		return result;
	}

	/**
	 * 批量启用/禁用
	 */
	@Override
	@SuppressWarnings("rawtypes")
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	public Result modifyMulDialogByState(DialogMoreVo dialogMoreVo) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(dialogMoreVo.getIdAps()) || StringUtils.isBlank(dialogMoreVo.getState())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}

		// 当前登录的用户
		// DialogUser dialogUser = (DialogUser)
		// SecurityUtils.getSubject().getSession().getAttribute("dialogUser");
		// String dialogUserId = dialogUser.getUserId();
		String dialogUserId = "1";

		Gson gson = new Gson();
		List<Map<String, Object>> idApsList = gson.fromJson(dialogMoreVo.getIdAps(),
				new TypeToken<ArrayList<Map<String, Object>>>() {
				}.getType());
		// 主题idList
		List<String> idApList = new ArrayList<>();
		for (Map<String, Object> idApsMap : idApsList) {
			idApList.add(idApsMap.get("idAp").toString());
		}
		// 批量更新数据
		try {
			dialogMoreDao.batchUpdate(dialogMoreVo.getState(), dialogUserId, idApList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		return ResultUtil.success();
	}
}
