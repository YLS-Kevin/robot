package com.yls.projects.dialog.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yls.frame.common.Exception.RobotException;
import com.yls.frame.common.enums.ResultEnum;
import com.yls.frame.common.utils.IdGen;
import com.yls.frame.common.utils.ResultUtil;
import com.yls.frame.common.utils.StringUtils;
import com.yls.frame.common.utils.Utils;
import com.yls.frame.common.web.JsonResult;
import com.yls.frame.common.web.Result;
import com.yls.projects.common.Page.PageBaen;
import com.yls.projects.dialog.dao.DialogTypesDao;
import com.yls.projects.dialog.entity.DialogTypes;
import com.yls.projects.dialog.service.DialogTypesService;
import com.yls.projects.dialog.vo.DialogTypesVo;
import com.yls.projects.robot.dao.DialogDao;
import com.yls.projects.robot.dao.RobotsAndDialogTypesDao;
import com.yls.projects.robot.entity.DialogUser;

/**
 * 对话库实现类
 * 
 * @Author:Suchy
 * @Description:
 * @Date: 18:25 2018/5/7
 */
@Service("dialogTypesService")
public class DialogTypesServiceImpl implements DialogTypesService {

	private static Logger logger = LoggerFactory.getLogger(DialogTypesServiceImpl.class);

	@Autowired
	private DialogTypesDao dialogTypesDao;

	@Autowired
	private DialogDao dialogDao;
	
	@Autowired
	private RobotsAndDialogTypesDao robotsAndDialogTypesDao;

	/**
	 * 分页获取信息
	 *
	 * @param dialogTypesVo
	 * @return
	 */
	@Cacheable(value = "dialogType", key = "#root.targetClass.name")
	@Transactional(value = "robotTransactionManager", readOnly = true)
	@Override
	public JsonResult dialogTypesList(DialogTypesVo dialogTypesVo) throws RobotException {

		// 统一使用com.yls.frame.common.web.Result.java, 并统一使用
		// com.yls.frame.common.utils.ResultUtil.java工具类 进行返回
		JsonResult result = new JsonResult();

		if (dialogTypesVo == null) {
			// edit by Alex for 统一异常处理,已下请自行修改 2018-05-05
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		if (dialogTypesVo.getSize() == null) {
			throw new RobotException(ResultEnum.PAGING_SIZE_IS_NULL_ERROR);
			// end by Alex for 2018-05-05
		}
		if (dialogTypesVo.getSize() <= 0) {
			throw new RobotException(ResultEnum.PAGING_SIZE_IS_LESSTHAN0_ERROR);
		}
		if (dialogTypesVo.getPage() == null) {
			throw new RobotException(ResultEnum.PAGING_SIZE_IS_NULL_ERROR);
		}
		if (dialogTypesVo.getPage() <= 0) {
			throw new RobotException(ResultEnum.PAGING_SIZE_IS_LESSTHAN0_ERROR);
		}
		if (StringUtils.isBlank(dialogTypesVo.getIsShare())) {
			dialogTypesVo.setIsShare(DialogTypes.IS_SHARE_0);
		}
		// 获取起始条数
		Integer startIndex = 0;
		if (dialogTypesVo.getPage() > 1) {
			startIndex = dialogTypesVo.getSize() * (dialogTypesVo.getPage() - 1);
		}
		dialogTypesVo.setStartIndex(startIndex);

		List<Map<String, Object>> dataList = this.dialogTypesDao.dialogTypesList(dialogTypesVo);

		Integer total = this.dialogTypesDao.getCount(dialogTypesVo);

		PageBaen pageBaen = new PageBaen();
		pageBaen.setSize(dialogTypesVo.getSize());
		pageBaen.setPage(dialogTypesVo.getPage());
		pageBaen.setTotal(total);
		pageBaen.setList(dataList);

		result.setSuccess(pageBaen);
		return result;

	}

	/**
	 * 新增对话
	 * 
	 * @param dialogTypes
	 * @param dialogUser
	 * @return
	 * @throws RobotException
	 */
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	@Override
	public JsonResult addDialogTypes(DialogTypes dialogTypes, DialogUser dialogUser) throws RobotException {
		JsonResult result = new JsonResult();
		if (dialogUser == null) {
			throw new RobotException(ResultEnum.SESSION_INVALIDATION_ERROR);
		}
		if (dialogUser.getUserId() == null) {
			throw new RobotException(ResultEnum.SESSION_INVALIDATION_ERROR);
		}
		if (dialogTypes == null) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}

		if (dialogTypes.getIdAc() == null) {
			throw new RobotException(ResultEnum.PARAMETER_IS_NULL_ERROR);
		}
		if (dialogTypes.getAtname() == null) {
			throw new RobotException(ResultEnum.PARAMETER_IS_NULL_ERROR);
		}

		if(dialogTypesDao.getCountByDialogLibraryName(dialogTypes) > 0) {
			throw new RobotException(ResultEnum.ABILITY_ALREADY_EXISTS);
		}
		
		dialogTypes.setId(IdGen.uuid());
		dialogTypes.setIsShare(DialogTypes.IS_SHARE_0);
		dialogTypes.setDelFlag(DialogTypes.DEL_FLAG_0);
		dialogTypes.setCreateBy(dialogUser.getUserId());
		dialogTypes.setUpdateBy(dialogUser.getUserId());

		this.dialogTypesDao.addDialogTypes(dialogTypes);
		// edit by Alex for 2018-05-05 应该给默认初始大小
		Map<String, Object> resultMap = new HashMap<>(16);
		// end by Alex for 2018-05-05
		resultMap.put(DialogTypes.ID, dialogTypes.getId());
		result.setSuccess(resultMap);
		return result;
	}

	/**
	 * 逻辑删除对话
	 * 
	 * @param id
	 * @param dialogUser
	 * @return
	 * @throws RobotException
	 */
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	@Override
	public JsonResult delDialogTypes(String id, DialogUser dialogUser) throws RobotException {
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
		dialogTypesVo.setIdDt(id);
		Integer count = dialogDao.getAllCount(dialogTypesVo);
		if (count > 0) {
			throw new RobotException(ResultEnum.EXISTS_DIALOG_ERROR);
		}
		//如果该能力已经被机器人所引用不让删除
		Integer abilityCount = robotsAndDialogTypesDao.getCountByDialogId(id);
		if (abilityCount > 0) {
			throw new RobotException(ResultEnum.ENTITY_ALREADY_USE);
		}
		DialogTypes dialogTypes = new DialogTypes();
		dialogTypes.setId(id);
		dialogTypes.setUpdateBy(dialogUser.getUserId());
		dialogTypes.setUpdateDate(Utils.getFormatDate("yyyy-MM-dd hh:mm:ss"));
		dialogTypes.setDelFlag(DialogTypes.DEL_FLAG_1);
		this.dialogTypesDao.delDialogTypes(dialogTypes);
		Map<String, Object> resultMap = new HashMap<>(16);
		resultMap.put(DialogTypes.ID, id);
		result.setSuccess(resultMap);

		return result;
	}

	/**
	 * 修改对话
	 * 
	 * @param dialogTypes
	 * @return
	 * @throws RobotException
	 */
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	@Override
	public JsonResult updateDialogTypes(DialogTypes dialogTypes, DialogUser dialogUser) throws RobotException {
		JsonResult result = new JsonResult();
		if (dialogUser == null) {
			throw new RobotException(ResultEnum.SESSION_INVALIDATION_ERROR);
		}
		if (dialogUser.getUserId() == null) {
			throw new RobotException(ResultEnum.SESSION_INVALIDATION_ERROR);
		}
		if (dialogTypes == null) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		if (dialogTypes.getId() == null) {
			throw new RobotException(ResultEnum.PARAMETER_IS_NULL_ERROR);
		}
		if (dialogTypes.getAtname() == null) {
			throw new RobotException(ResultEnum.PARAMETER_IS_NULL_ERROR);
		}
		
		if(dialogTypesDao.getCountByDialogLibraryName(dialogTypes) > 0) {
			throw new RobotException(ResultEnum.ABILITY_ALREADY_EXISTS);
		}
		
		dialogTypes.setUpdateBy(dialogUser.getUserId());
		dialogTypes.setUpdateDate(Utils.getFormatDate("yyyy-MM-dd hh:mm:ss"));
		this.dialogTypesDao.updateDialogTypes(dialogTypes);
		Map<String, Object> resultMap = new HashMap<>(16);
		resultMap.put(DialogTypes.ID, dialogTypes.getId());
		result.setSuccess(resultMap);
		return result;
	}

	/**
	 * 展示某个对话库详情
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class, readOnly = true)
	public Result getDialogById(DialogTypesVo dialogTypesVo) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(dialogTypesVo.getIdDt()) || dialogTypesVo.getPageSingle() == null
				|| dialogTypesVo.getSizeSingle() == null || dialogTypesVo.getPageMul() == null
				|| dialogTypesVo.getSizeMul() == null || StringUtils.isBlank(dialogTypesVo.getIsI())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}

		// 分页参数必须大于等于0
		if (dialogTypesVo.getPageSingle() <= 0 || dialogTypesVo.getSizeSingle() <= 0 || dialogTypesVo.getPageMul() <= 0
				|| dialogTypesVo.getSizeMul() <= 0) {
			throw new RobotException(ResultEnum.PAGING_SIZE_IS_LESSTHAN0_ERROR);
		}

		Map<String, Object> map = new HashMap<>(16);
		
		//如果该能力已经被机器人所引用不让删除
		Integer abilityCount = robotsAndDialogTypesDao.getCountByDialogId(dialogTypesVo.getIdDt());

		DialogTypes dialogTypes = dialogTypesDao.getById(dialogTypesVo.getIdDt());
		map.put("id", dialogTypes.getId());
		map.put("atname", dialogTypes.getAtname());
		map.put("idAc", dialogTypes.getIdAc());
		map.put("be_quoted", abilityCount);
		map.put("isShare", dialogTypes.getIsShare());
		map.put("state", dialogTypes.getState());

		// 获取起始条数
		Integer startIndexSingle = 0;
		if (dialogTypesVo.getPageSingle() > 1) {
			startIndexSingle = dialogTypesVo.getSizeSingle() * (dialogTypesVo.getPageSingle() - 1);
		}
		dialogTypesVo.setStartIndexSingle(startIndexSingle);

		// 单轮对话
		List<Map<String, Object>> singleList = null;
		Integer singleCount = 0;
		if (DialogTypesVo.isI_2.equals(dialogTypesVo.getIsI())) {
			// 固定应答
			singleList = dialogDao.getDialogByIdDt(dialogTypesVo);
			singleCount = dialogDao.getCount(dialogTypesVo);
		} else if (DialogTypesVo.isI_3.equals(dialogTypesVo.getIsI())) {
			// 接口应答
			singleList = dialogDao.getInterfaceByIdDt(dialogTypesVo);
			singleCount = dialogDao.getInterfaceCount(dialogTypesVo);
		}

		PageBaen pageBaen = new PageBaen();
		pageBaen.setSize(dialogTypesVo.getSizeSingle());
		pageBaen.setPage(dialogTypesVo.getPageSingle());
		pageBaen.setTotal(singleCount);
		pageBaen.setList(singleList);
		map.put("singleDialog", pageBaen);

		// 多轮问答
		// 获取起始条数
		Integer startIndexMul = 0;
		if (dialogTypesVo.getPageMul() > 1) {
			startIndexMul = dialogTypesVo.getSizeMul() * (dialogTypesVo.getPageMul() - 1);
		}
		dialogTypesVo.setStartIndexMul(startIndexMul);
		List<Map<String, Object>> mulList = dialogDao.getMulDialogByIdDt(dialogTypesVo);
		Integer mulCount = dialogDao.getCountMul(dialogTypesVo);
		PageBaen pageBaen2 = new PageBaen();
		pageBaen2.setSize(dialogTypesVo.getSizeMul());
		pageBaen2.setPage(dialogTypesVo.getPageMul());
		pageBaen2.setTotal(mulCount);
		pageBaen2.setList(mulList);
		map.put("mulDialog", pageBaen2);

		return ResultUtil.success(map);
	}

	/**
	 * 根据账户ID查询信息(多论对话)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class, readOnly = true)
	public Result getDialogByIdAc(DialogTypesVo dialogTypesVo) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(dialogTypesVo.getIdAc()) || dialogTypesVo.getPageMul() == null
				|| dialogTypesVo.getSizeMul() == null || dialogTypesVo.getPageMul2() == null
				|| dialogTypesVo.getSizeMul2() == null || StringUtils.isBlank(dialogTypesVo.getIsI())
				|| StringUtils.isBlank(dialogTypesVo.getIdAp())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}

		// 分页参数必须大于等于0
		if (dialogTypesVo.getPageMul() <= 0 || dialogTypesVo.getSizeMul() <= 0 || dialogTypesVo.getPageMul2() <= 0
				|| dialogTypesVo.getSizeMul2() <= 0) {
			throw new RobotException(ResultEnum.PAGING_SIZE_IS_LESSTHAN0_ERROR);
		}

		Map<String, Object> map = new HashMap<>(16);

		// 获取起始条数
		Integer startIndexMul = 0;
		Integer startIndexMul2 = 0;
		if (dialogTypesVo.getPageMul() > 1) {
			startIndexMul = dialogTypesVo.getSizeMul() * (dialogTypesVo.getPageMul() - 1);
		}
		dialogTypesVo.setStartIndexMul(startIndexMul);
		if (dialogTypesVo.getPageMul2() > 1) {
			startIndexMul2 = dialogTypesVo.getSizeMul2() * (dialogTypesVo.getPageMul2() - 1);
		}
		dialogTypesVo.setStartIndexMul2(startIndexMul2);

		List<Map<String, Object>> mulList = null;
		Integer mulCount = 0;
		PageBaen pageBaen = null;
		if (DialogTypesVo.isI_2.equals(dialogTypesVo.getIsI())) {
			// 固定应答
			dialogTypesVo.setAtype(dialogTypesVo.getIsI());
			// 多轮对话主题入口
			dialogTypesVo.setMulDialogType(DialogTypesVo.atype_4);
			mulList = dialogDao.getMulDialogByIdAc(dialogTypesVo);
			mulCount = dialogDao.getCountByIdAc(dialogTypesVo);
			pageBaen = new PageBaen();
			pageBaen.setSize(dialogTypesVo.getSizeMul());
			pageBaen.setPage(dialogTypesVo.getPageMul());
			pageBaen.setTotal(mulCount);
			pageBaen.setList(mulList);
			map.put("mulDialog", pageBaen);
			// 多轮对话中
			dialogTypesVo.setMulDialogType(DialogTypesVo.atype_5);
			mulList = dialogDao.getMulDialogByIdAc(dialogTypesVo);
			mulCount = dialogDao.getCountByIdAc(dialogTypesVo);
			pageBaen = new PageBaen();
			pageBaen.setSize(dialogTypesVo.getSizeMul2());
			pageBaen.setPage(dialogTypesVo.getPageMul2());
			pageBaen.setTotal(mulCount);
			pageBaen.setList(mulList);
			map.put("mulDialog2", pageBaen);
		} else if (DialogTypesVo.isI_3.equals(dialogTypesVo.getIsI())) {
			// 接口应答
			dialogTypesVo.setAtype(dialogTypesVo.getIsI());
			// 多轮对话主题入口
			dialogTypesVo.setMulDialogType(DialogTypesVo.atype_4);
			mulList = dialogDao.getMulDialogByIdAc2(dialogTypesVo);
			mulCount = dialogDao.getCountByIdAc2(dialogTypesVo);
			pageBaen = new PageBaen();
			pageBaen.setSize(dialogTypesVo.getSizeMul());
			pageBaen.setPage(dialogTypesVo.getPageMul());
			pageBaen.setTotal(mulCount);
			pageBaen.setList(mulList);
			map.put("mulDialog", pageBaen);
			// 多轮对话中
			dialogTypesVo.setMulDialogType(DialogTypesVo.atype_5);
			mulList = dialogDao.getMulDialogByIdAc2(dialogTypesVo);
			mulCount = dialogDao.getCountByIdAc2(dialogTypesVo);
			pageBaen = new PageBaen();
			pageBaen.setSize(dialogTypesVo.getSizeMul2());
			pageBaen.setPage(dialogTypesVo.getPageMul2());
			pageBaen.setTotal(mulCount);
			pageBaen.setList(mulList);
			map.put("mulDialog2", pageBaen);
		}

		return ResultUtil.success(map);
	}

	@Override
	public Result shareDialogLibrary(DialogTypes dialogTypes) {
		if (StringUtils.isBlank(dialogTypes.getId()) || StringUtils.isBlank(dialogTypes.getIsShare())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		//如果该能力已经被机器人所引用不让删除
		Integer abilityCount = robotsAndDialogTypesDao.getCountByDialogId(dialogTypes.getId());
		if (abilityCount > 0 && "0".equals(dialogTypes.getIsShare())) {
			throw new RobotException(ResultEnum.ENTITY_ALREADY_USE);
		}
		try {
			dialogTypesDao.shareDialogLibrary(dialogTypes);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		return ResultUtil.success();
	}
}
