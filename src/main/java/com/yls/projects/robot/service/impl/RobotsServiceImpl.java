package com.yls.projects.robot.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.yls.frame.common.Exception.RobotException;
import com.yls.frame.common.enums.ResultEnum;
import com.yls.frame.common.utils.IdGen;
import com.yls.frame.common.utils.ResultUtil;
import com.yls.frame.common.utils.StringUtils;
import com.yls.frame.common.web.Result;
import com.yls.projects.common.Page.PageBaen;
import com.yls.projects.common.cache.ActionEnum;
import com.yls.projects.common.cache.DialogCacheListenerService;
import com.yls.projects.dialog.dao.DialogTypesDao;
import com.yls.projects.dialog.entity.DialogTypes;
import com.yls.projects.dialog.vo.DialogTypesVo;
import com.yls.projects.oauth.daodb.OauthClientDetailsDao;
import com.yls.projects.oauth.entity.OauthClientDetails;
import com.yls.projects.oauth.utils.MD5Util;
import com.yls.projects.robot.dao.ClientCacheMapper;
import com.yls.projects.robot.dao.DialogAnswerExpDao;
import com.yls.projects.robot.dao.DialogAnswerScriptDao;
import com.yls.projects.robot.dao.DialogCacheMapper;
import com.yls.projects.robot.dao.DialogDao;
import com.yls.projects.robot.dao.DialogManDao;
import com.yls.projects.robot.dao.DialogRobotStaticDao;
import com.yls.projects.robot.dao.RobotsAndDialogTypesDao;
import com.yls.projects.robot.dao.RobotsDao;
import com.yls.projects.robot.dao.RobotsModuleDao;
import com.yls.projects.robot.dao.WordsDao;
import com.yls.projects.robot.entity.Dialog;
import com.yls.projects.robot.entity.DialogAnswerExp;
import com.yls.projects.robot.entity.DialogAnswerScript;
import com.yls.projects.robot.entity.DialogExp;
import com.yls.projects.robot.entity.DialogMan;
import com.yls.projects.robot.entity.DialogMoreAndOne2;
import com.yls.projects.robot.entity.DialogRobotStatic;
import com.yls.projects.robot.entity.DialogType2;
import com.yls.projects.robot.entity.Robots;
import com.yls.projects.robot.entity.RobotsAndDialogTypes;
import com.yls.projects.robot.entity.RobotsModule;
import com.yls.projects.robot.entity.Words;
import com.yls.projects.robot.service.FixedDialogService;
import com.yls.projects.robot.service.RobotsService;
import com.yls.projects.robot.utils.GsonAdatper;
import com.yls.projects.robot.vo.FixedDialogVo;
import com.yls.projects.robot.vo.RobotsVo;

/**
 * 机器人Service 实现类
 * 
 * @author 陈俊
 * @date 2018年5月17日
 */
@Service("robotsService")
public class RobotsServiceImpl implements RobotsService {

	private static Logger logger = LoggerFactory.getLogger(RobotsServiceImpl.class);

	@Autowired
	private RobotsDao robotsDao;

	@Autowired
	private OauthClientDetailsDao oauthClientDetailsDao;

	@Autowired
	private RobotsModuleDao robotsModuleDao;

	@Autowired
	private DialogAnswerExpDao dialogAnswerExpDao;

	@Autowired
	private RobotsAndDialogTypesDao robotsAndDialogTypesDao;

	@Autowired
	private DialogCacheListenerService dialogCacheListenerService;

	@Autowired
	private DialogDao dialogDao;

	@Autowired
	private DialogTypesDao dialogTypesDao;

	@Autowired
	private ClientCacheMapper clientCacheMapper;

	@Autowired
	private DialogCacheMapper dialogCacheMapper;

	@Autowired
	private FixedDialogService fixedDialogService;

	@Autowired
	private DialogManDao dialogManDao;

	@Autowired
	private DialogRobotStaticDao dialogRobotStaticDao;

	@Autowired
	private DialogAnswerScriptDao dialogAnswerScriptDao;

	@Autowired
	private WordsDao wordsDao;

	/**
	 * 创建机器人
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(value = "robotTransactionManager", readOnly = false, rollbackFor = Exception.class)
	public Result createdRobot(Robots robots) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(robots.getCname()) || StringUtils.isBlank(robots.getIntrade())
				|| StringUtils.isBlank(robots.getRemarks()) || StringUtils.isBlank(robots.getIdAc())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		
		if(robotsDao.getCountByRobotName(robots) > 0) {
			throw new RobotException(ResultEnum.ROBOT_ALREADY_EXISTS);
		}

		// 当前登录的用户
		// DialogUser dialogUser = (DialogUser)
		// SecurityUtils.getSubject().getSession().getAttribute("dialogUser");
		// String dialogUserId = dialogUser.getUserId();
		String dialogUserId = "1";

		String appId = IdGen.uuid();
		String secret = MD5Util.stringMD5(IdGen.uuid());
		// 机器人(应用)表
		String robotId = appId;
		Robots entity = new Robots();
		entity.setId(robotId);
		entity.setIdAc(robots.getIdAc());
		entity.setCname(robots.getCname());
		entity.setAppid(appId);
		entity.setSecret(secret);
		entity.setAccessWay("WEBAPI");
		entity.setRemarks(robots.getRemarks());
		entity.setIntrade(robots.getIntrade());
		entity.setState(Robots.STATE_1);
		entity.setCreateBy(dialogUserId);
		entity.setUpdateBy(dialogUserId);
		entity.setDelFlag(Robots.DEL_FLAG_0);

		// 终端细节表
		OauthClientDetails oauthClientDetails = new OauthClientDetails();
		oauthClientDetails.setClientId(appId);
		oauthClientDetails.setResourceIds(OauthClientDetails.RESOURCEIDS_1);
		oauthClientDetails.setClientSecret(secret);
		oauthClientDetails.setScope(OauthClientDetails.SCOPE_1);
		oauthClientDetails.setAuthorizedGrantTypes(OauthClientDetails.AUTHORIZEDGRANTTYPES_1);
		oauthClientDetails.setCreateTime(new Date());
		oauthClientDetails.setArchived(OauthClientDetails.ARCHIVED_0);
		oauthClientDetails.setTrusted(OauthClientDetails.TRUSTED_0);

		// 插入数据
		try {
			robotsDao.insert(entity);
			oauthClientDetailsDao.insert(oauthClientDetails);
			// 插入通用模块
			RobotsVo robotsVo = new RobotsVo();
			robotsVo.setCid(robotId);
			robotsVo.setMname("通用");
			robotsVo.setDokey("通用");
			robotsVo.setIscommon(RobotsModule.iscommon_yes + "");
			robotsVo.setIdAc(robots.getIdAc());
			Map<String, String> map = new HashMap<String, String>();
			insertRobotModel(robotsVo, map, true);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		return ResultUtil.success();
	}

	/**
	 * 修改机器人
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(value = "robotTransactionManager", readOnly = false, rollbackFor = Exception.class)
	public Result modifyRobot(Robots robots) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(robots.getCname()) || StringUtils.isBlank(robots.getIntrade())
				|| StringUtils.isBlank(robots.getRemarks()) || StringUtils.isBlank(robots.getAccessWay())
				|| StringUtils.isBlank(robots.getId())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		
		if(robotsDao.getCountByRobotName(robots) > 0) {
			throw new RobotException(ResultEnum.ROBOT_ALREADY_EXISTS);
		}

		// 当前登录的用户
		// DialogUser dialogUser = (DialogUser)
		// SecurityUtils.getSubject().getSession().getAttribute("dialogUser");
		// String dialogUserId = dialogUser.getUserId();
		String dialogUserId = "1";

		Robots entity = new Robots();
		entity.setId(robots.getId());
		entity.setCname(robots.getCname());
		entity.setIntrade(robots.getIntrade());
		entity.setRemarks(robots.getRemarks());
		entity.setAccessWay(robots.getAccessWay());
		entity.setUpdateBy(dialogUserId);

		// 更新数据
		try {
			robotsDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		return ResultUtil.success(null);
	}

	/**
	 * 获取机器人列表
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(value = "robotTransactionManager", readOnly = true, rollbackFor = Exception.class)
	public Result listRobot(RobotsVo robotsVo) {

		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(robotsVo.getIdAc()) || robotsVo.getSize() == null || robotsVo.getPage() == null) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		// 分页参数必须大于等于0
		if (robotsVo.getSize() <= 0 || robotsVo.getPage() <= 0) {
			throw new RobotException(ResultEnum.PAGING_SIZE_IS_LESSTHAN0_ERROR);
		}

		// 获取起始条数
		Integer startIndex = 0;
		if (robotsVo.getPage() > 1) {
			startIndex = robotsVo.getSize() * (robotsVo.getPage() - 1);
		}
		robotsVo.setStartIndex(startIndex);

		List<Map<String, Object>> dataList = robotsDao.getListByIdAc(robotsVo);
		Integer total = robotsDao.getCount(robotsVo);

		PageBaen pageBaen = new PageBaen();
		pageBaen.setSize(robotsVo.getSize());
		pageBaen.setPage(robotsVo.getPage());
		pageBaen.setTotal(total);
		pageBaen.setList(dataList);
		return ResultUtil.success(pageBaen);
	}

	/**
	 * 配置机器人
	 */
	@Override
	@Transactional(value = "robotTransactionManager", readOnly = false, rollbackFor = Exception.class)
	public Result configRobot(RobotsVo robotsVo) {

		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(robotsVo.getCid()) || StringUtils.isBlank(robotsVo.getIdAc())
				|| StringUtils.isBlank(robotsVo.getIdM()) || StringUtils.isBlank(robotsVo.getIdM())
				|| StringUtils.isBlank(robotsVo.getIsModifyDoKey())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}

		/*
		 * // 当前登录的用户 DialogUser dialogUser = (DialogUser)
		 * SecurityUtils.getSubject().getSession().getAttribute("dialogUser");
		 * //用户id String dialogUserId = dialogUser.getUserId(); //账号id String
		 * idAc = dialogUser.getIdAc();
		 */

		String dialogUserId = "1";

		// 查询robots appid 和secrets
		Robots robotsEntity = robotsDao.getEntityById(robotsVo);

		// 如果机器应答内容不为空，并且异常类型不为空 操作该记录
		if (StringUtils.isNotBlank(robotsVo.getAnswer()) && StringUtils.isNotBlank(robotsVo.getStype())) {
			// 判断该异常回答是否已存在数据库,如果存在则更新,否则插入
			List<DialogAnswerExp> listExp = dialogAnswerExpDao.listAnswerExpByRobotIdAndStype(robotsVo);
			if (listExp != null && listExp.size() > 0) {
				DialogAnswerExp dialogAnswerExp = new DialogAnswerExp();
				// 账号id
				dialogAnswerExp.setIdAc(robotsVo.getIdAc());
				// 机器人id
				dialogAnswerExp.setCid(robotsVo.getCid());
				// 类型。1-无答案时，2-接口异常时，3-系统出错时
				dialogAnswerExp.setStype(Short.parseShort(robotsVo.getStype()));
				// 机器应答内容
				dialogAnswerExp.setAnswer(robotsVo.getAnswer());
				// 创建者
				dialogAnswerExp.setCreateBy(dialogUserId);
				// 更新者
				dialogAnswerExp.setUpdateBy(dialogUserId);

				GsonBuilder gsonBuilder = new GsonBuilder();
				// 注册自定义String的适配器
				gsonBuilder.registerTypeAdapter(String.class, GsonAdatper.STRING);
				Gson gsonUpdate = gsonBuilder.create();
				// 及时缓存data
				List<DialogExp> updateDataList = dialogCacheMapper.findDialogExpById(listExp.get(0).getId());
				String updateData = gsonUpdate.toJson(updateDataList);
				try {
					dialogAnswerExpDao.updateByCidAndStype(dialogAnswerExp);
					dialogCacheListenerService.ActionDialogCacheEvent(listExp.get(0).getId(),
							ActionEnum.UPDATE_DIALOG_EXP.getCode(), updateData, robotsEntity.getId(),
							robotsEntity.getSecret());
				} catch (Exception e) {
					logger.info("执行--/configRobot请求--dialogAnswerExpDao.updateByCidAndStype(dialogAnswerExp)--sql出错！");
					e.printStackTrace();
					throw new RobotException(ResultEnum.FAIL);
				}
			} else {
				DialogAnswerExp dialogAnswerExp = new DialogAnswerExp();
				// 异常应答表主键id
				String idString = IdGen.uuid();
				dialogAnswerExp.setId(idString);
				// 账号id
				dialogAnswerExp.setIdAc(robotsVo.getIdAc());
				// 终端id
				dialogAnswerExp.setCid(robotsVo.getCid());
				// 类型。1-无答案时，2-接口异常时，3-系统出错时
				dialogAnswerExp.setStype(Short.parseShort(robotsVo.getStype()));
				// 机器应答内容
				dialogAnswerExp.setAnswer(robotsVo.getAnswer());
				// 创建者
				dialogAnswerExp.setCreateBy(dialogUserId);
				// 更新者
				dialogAnswerExp.setUpdateBy(dialogUserId);

				try {
					dialogAnswerExpDao.insertSelective(dialogAnswerExp);
					dialogCacheListenerService.ActionDialogCacheEvent(idString, ActionEnum.ADD_DIALOG_EXP.getCode(),
							idString, robotsEntity.getId(), robotsEntity.getSecret());
				} catch (Exception e) {
					logger.info("执行--/configRobot请求--dialogAnswerExpDao.insertSelective(dialogAnswerExp)--sql出错！");
					e.printStackTrace();
					throw new RobotException(ResultEnum.FAIL);
				}
			}
		}

		// 如果机器人图标不为空则更新
		if (StringUtils.isNotBlank(robotsVo.getIconurl())) {
			Robots robots = new Robots();
			// 主键id
			robots.setId(robotsVo.getCid());
			// 机器人图标
			robots.setIconurl(robotsVo.getIconurl());
			try {
				robotsDao.update(robots);
			} catch (Exception e) {
				logger.info("执行--/configRobot请求--robotsDao.update(robots)--sql出错！");
				e.printStackTrace();
				throw new RobotException(ResultEnum.FAIL);
			}
		}
		// 更新非通用模块的触发关键词

		if ("1".equals(robotsVo.getIsModifyDoKey()) && StringUtils.isNotBlank(robotsVo.getDokey())
				&& StringUtils.isNotBlank(robotsVo.getCidMIdDt()) && StringUtils.isNotBlank(robotsVo.getDialogId())) {
			RobotsModule robotsModule = new RobotsModule();
			robotsModule.setCidM(robotsVo.getIdM());
			robotsModule.setDokey(robotsVo.getDokey());

			// 获取修改前对话数据
			List<DialogMoreAndOne2> updateDataLists = dialogCacheMapper.findDialogById(robotsVo.getDialogId());
			GsonBuilder gsonBuilder = new GsonBuilder();
			// 注册自定义String的适配器
			gsonBuilder.registerTypeAdapter(String.class, GsonAdatper.STRING);
			Gson gsonUpdate = gsonBuilder.create();
			String updateData = gsonUpdate.toJson(updateDataLists);
			try {
				// 1.更新通用模块表的记录
				robotsModuleDao.updateByPrimaryKeySelective(robotsModule);
				// ============更新dialog表的数据============
				dialogDao.updateDialog(robotsVo.getDialogId());
				// 2.根据对话id删除人说表的记录
				dialogManDao.deleteDialogManByDialogId(robotsVo.getDialogId());
				// 3.根据对话id添加人说表的记录
				String[] dokeys = robotsVo.getDokey().split("\\|");
				JSONArray jsonArray = new JSONArray();
				DialogMan dialogMan = new DialogMan();
				for (int i = 0; i < dokeys.length; i++) {
					// 主键id
					dialogMan.setId(IdGen.uuid());
					// 账户id
					dialogMan.setIdAc(robotsVo.getIdAc());
					// 人机对话id
					dialogMan.setIdD(robotsVo.getDialogId());
					// 匹配类型。1-模糊匹配，2-关键词匹配
					dialogMan.setAptype(DialogMan.APTYPE_1);
					// 人说的话
					dialogMan.setAword(dokeys[i]);
					// 创建者
					dialogMan.setCreateBy(dialogUserId);
					// 更新者
					dialogMan.setUpdateBy(dialogUserId);
					// 插入数据
					dialogManDao.insert(dialogMan);
				}
				// 4.触发对话更新及时缓存事件
				// 查询机器人id与秘钥
				Robots robots = robotsDao.getEntityById(robotsVo);
				dialogCacheListenerService.ActionDialogCacheEvent(robotsVo.getDialogId(),
						ActionEnum.UPDATE_DIALOG.getCode(), updateData, robotsVo.getCid(), robots.getSecret());
			} catch (Exception e) {
				logger.info("执行--/configRobot请求--robotsModuleDao.updateByPrimaryKeySelective(robotsModule)--sql出错！");
				e.printStackTrace();
				throw new RobotException(ResultEnum.FAIL);
			}
		} else if ("1".equals(robotsVo.getIsModifyDoKey()) && (StringUtils.isNotBlank(robotsVo.getDokey())
				|| StringUtils.isNotBlank(robotsVo.getCidMIdDt()) || StringUtils.isNotBlank(robotsVo.getDialogId()))) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		return ResultUtil.success();
	}

	/**
	 * 为机器人选择技能
	 */
	@Transactional(value = "robotTransactionManager", readOnly = false, rollbackFor = Exception.class)
	@Override
	public Result selectMulSkill(RobotsVo robotsVo) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(robotsVo.getCid()) || StringUtils.isBlank(robotsVo.getIdM())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}

		/*
		 * // 当前登录的用户 DialogUser dialogUser = (DialogUser)
		 * SecurityUtils.getSubject().getSession().getAttribute("dialogUser");
		 * //用户id String dialogUserId = dialogUser.getUserId(); //账号id String
		 * idAc = dialogUser.getIdAc();
		 */
		String dialogUserId = "1";
		// 账号id
		RobotsAndDialogTypes robotsAndDialogTypes = new RobotsAndDialogTypes();
		// 机器人id
		robotsAndDialogTypes.setCid(robotsVo.getCid());
		// 模块id
		robotsAndDialogTypes.setCidM(robotsVo.getIdM());
		// 技能id(人机对话库id)
		robotsAndDialogTypes.setIdDt(robotsVo.getIdDt());
		// 创建者
		robotsAndDialogTypes.setCreateBy(dialogUserId);
		// 更新者
		robotsAndDialogTypes.setUpdateBy(dialogUserId);

		// 获取修改前对话数据
		List<DialogType2> updateDataLists = clientCacheMapper.findDialogTypeById(robotsVo.getCid(), robotsVo.getIdM());
		GsonBuilder gsonBuilder = new GsonBuilder();
		// 注册自定义String的适配器
		gsonBuilder.registerTypeAdapter(String.class, GsonAdatper.STRING);
		Gson gsonUpdate = gsonBuilder.create();
		String updateData = gsonUpdate.toJson(updateDataLists);

		// 根据cid和idM判断是否已经存在该记录
		RobotsAndDialogTypes entity = robotsAndDialogTypesDao.selectByCidAndIdM(robotsAndDialogTypes);

		// 查询机器人id与秘钥
		Robots robots = robotsDao.getEntityById(robotsVo);
		if (entity != null) {
			// 更新
			try {
				robotsAndDialogTypesDao.updateByCidAndIdM(robotsAndDialogTypes);
				dialogCacheListenerService.ActionDialogCacheEvent(entity.getId(),
						ActionEnum.UPDATE_DIALOG_TYPE.getCode(), updateData, robots.getAppid(), robots.getSecret());
			} catch (Exception e) {
				logger.info(
						"执行--/selectMulSkill请求--robotsAndDialogTypesDao.updateByCidAndIdM(robotsAndDialogTypes)--sql出错！");
				e.printStackTrace();
				throw new RobotException(ResultEnum.FAIL);
			}
		} else {
			// 新增
			// 主键id
			String id = IdGen.uuid();
			robotsAndDialogTypes.setId(id);
			try {
				robotsAndDialogTypesDao.insertSelective(robotsAndDialogTypes);
				dialogCacheListenerService.ActionDialogCacheEvent(id, ActionEnum.ADD_DIALOG_TYPE.getCode(), id,
						robots.getAppid(), robots.getSecret());
			} catch (Exception e) {
				logger.info(
						"执行--/selectMulSkill请求--robotsAndDialogTypesDao.insertSelective(robotsAndDialogTypes)--sql出错！");
				e.printStackTrace();
				throw new RobotException(ResultEnum.FAIL);
			}
		}
		return ResultUtil.success();
	}

	/**
	 * 查询机器人模块信息
	 */
	@Transactional(value = "robotTransactionManager", readOnly = true, rollbackFor = Exception.class)
	@Override
	public Result getRobotInfoByIdAndMid(RobotsVo robotsVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<RobotsModule> mnameList = robotsModuleDao.getRobotInfoByIdAndMid(robotsVo);
		List<DialogAnswerExp> answerExpList = dialogAnswerExpDao.getAnswerExpByRobotId(robotsVo);
		map.put("mnameList", mnameList);
		// 放入DialogId 对应的触发问题的dialogId
		RobotsModule robotsModule = robotsModuleDao.getDialogIdByIdAndMid(robotsVo);
		if (robotsModule != null) {
			map.put("dialogId", robotsModule.getDialogId());
		} else {
			map.put("dialogId", "");
		}
		// 放入异常数据
		if (answerExpList != null && answerExpList.size() > 0) {
			for (DialogAnswerExp dialogAnswerExp : answerExpList) {
				if (dialogAnswerExp.getStype().toString().equals(DialogAnswerExp.STYPE_1)) {
					map.put("noAnswer", dialogAnswerExp.getAnswer());
					break;
				} else {
					map.put("noAnswer", "");
				}
			}

			for (DialogAnswerExp dialogAnswerExp : answerExpList) {
				if (dialogAnswerExp.getStype().toString().equals(DialogAnswerExp.STYPE_2)) {
					map.put("expAnswer", dialogAnswerExp.getAnswer());
					break;
				} else {
					map.put("expAnswer", "");
				}
			}
		} else {
			map.put("noAnswer", "");
			map.put("expAnswer", "");
		}
		// 获取机器人图标
		Robots robot = robotsDao.getEntityById(robotsVo);
		map.put("iconurl", robot.getIconurl());
		// 获取能力列表
		List<RobotsAndDialogTypes> robotsAndDialogTypes = robotsAndDialogTypesDao.getAbilitys(robotsVo);
		List<DialogTypes> abilitys = null;
		if (robotsAndDialogTypes != null && robotsAndDialogTypes.size() > 0) {
			String[] ids = robotsAndDialogTypes.get(0).getIdDt().split(",");
			List<String> idsList = Arrays.asList(ids);
			abilitys = dialogTypesDao.listDialogTypeByIds(idsList);
		}
		map.put("abalityList", abilitys);

		// 根据情景模块id获取某个对话库类型
		DialogTypes dialogTypes = dialogTypesDao.getByCidM(robotsVo.getIdM());
		if (dialogTypes != null) {
			robotsVo.setIdDt(dialogTypes.getId());
		}
		// 获取对话列表
		PageBaen singleList = getSingleList(robotsVo);
		map.put("singleList", singleList);
		return ResultUtil.success(map);
	}

	private PageBaen getSingleList(RobotsVo robotsVo) {
		// 获取对话列表
		DialogTypesVo dialogTypesVo = new DialogTypesVo();
		dialogTypesVo.setPageSingle(robotsVo.getPageSingle());
		dialogTypesVo.setSizeSingle(robotsVo.getSizeSingle());
		dialogTypesVo.setState(robotsVo.getStateDialog());
		dialogTypesVo.setDescSingle(robotsVo.getDescSingle());
		dialogTypesVo.setDescSingleCol(robotsVo.getDescSingleCol());
		dialogTypesVo.setKeyword(robotsVo.getKeyword());
		dialogTypesVo.setIsI(robotsVo.getIsI());
		dialogTypesVo.setIdDt(robotsVo.getIdDt());

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
		return pageBaen;
	}

	@Transactional(value = "robotTransactionManager", readOnly = false, rollbackFor = Exception.class)
	@Override
	public Result delRobotModelById(RobotsVo robotsVo) {
		if (StringUtils.isBlank(robotsVo.getIdM())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}

		DialogTypes dialogTypes = dialogTypesDao.getByCidM(robotsVo.getIdM());

		// 注释删除限制
		// DialogTypesVo dialogTypesVo = new DialogTypesVo();
		// if(dialogTypes != null) {
		// dialogTypesVo.setIdDt(dialogTypes.getId());
		// Integer count = dialogDao.getAllCount(dialogTypesVo);
		// if (count > 0) {
		// throw new RobotException(ResultEnum.EXISTS_DIALOG_ERROR);
		// }
		// }

		RobotsModule robotsModule = new RobotsModule();
		robotsModule.setCidM(robotsVo.getIdM());
		robotsModule.setDelFlag(RobotsModule.delFlag_fail);

		// 查询robots appid 和secrets
		Robots robotsEntity = robotsDao.getEntityByIdM(robotsVo);
		try {
			robotsModuleDao.updateByPrimaryKeySelective(robotsModule);
			dialogCacheListenerService.ActionDialogCacheEvent(robotsVo.getIdM(),
					ActionEnum.DELETE_DIALOG_MODEL.getCode(), robotsVo.getIdM(), robotsEntity.getAppid(),
					robotsEntity.getSecret());
		} catch (Exception e) {
			logger.error("删除情景模块失败");
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		return ResultUtil.success(null);
	}

	@Transactional(value = "robotTransactionManager", readOnly = false, rollbackFor = Exception.class)
	@Override
	public Result addRobotModel(RobotsVo robotsVo) {
		if (StringUtils.isBlank(robotsVo.getCid()) || StringUtils.isBlank(robotsVo.getMname())
				|| StringUtils.isBlank(robotsVo.getIdAc()) || StringUtils.isBlank(robotsVo.getDokey())
				|| StringUtils.isBlank(robotsVo.getCidMIdDt()) || StringUtils.isBlank(robotsVo.getCidM())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		// 判断是否存在同名
		if (robotsModuleDao.getCountByCidMname(robotsVo) > 0) {
			throw new RobotException(ResultEnum.EXISTS_SAME_DIALOG_MODEL);
		}
		Map<String, String> map = new HashMap<String, String>();
		insertRobotModel(robotsVo, map, false);
		return ResultUtil.success(map);
	}

	private String insertRobotModel(RobotsVo robotsVo, Map<String, String> map, boolean isCommon) {
		String dokeyDialogId = "";
		String homePageDialogId = "";
		String uuid = IdGen.uuid();
		// 插入情景模块
		RobotsModule robotsModule = new RobotsModule();
		robotsModule.setCidM(uuid);
		robotsModule.setMname(robotsVo.getMname());
		robotsModule.setCid(robotsVo.getCid());
		robotsModule.setIscommon(RobotsModule.iscommon_no);
		if (StringUtils.isNotBlank(robotsVo.getIscommon())
				&& RobotsModule.iscommon_yes.toString().equals(robotsVo.getIscommon())) {
			robotsModule.setIscommon(RobotsModule.iscommon_yes);
		}
		robotsModule.setDokey(robotsVo.getDokey());
		robotsModule.setState(RobotsModule.state_active);
		robotsModule.setDelFlag(RobotsModule.delFlag_normal);
		robotsModule.setCreateBy("1");
		robotsModule.setUpdateBy("1");

		// 插入对话库
		DialogTypes dialogTypes = new DialogTypes();
		String idDt = IdGen.uuid();
		dialogTypes.setId(idDt);
		dialogTypes.setIdAc(robotsVo.getIdAc());
		dialogTypes.setAtname(uuid);
		dialogTypes.setCidM(uuid);
		dialogTypes.setIsShare(DialogTypes.IS_SHARE_0);
		dialogTypes.setState(DialogTypes.STATE_1);
		dialogTypes.setDelFlag(DialogTypes.DEL_FLAG_0);
		dialogTypes.setCreateBy("1");
		dialogTypes.setUpdateBy("1");

		if (!isCommon) {
			// 插入固定问答
			FixedDialogVo fixedDialogVo = new FixedDialogVo();
			fixedDialogVo.setIdAc(robotsVo.getIdAc());
			fixedDialogVo.setIdDt(idDt);
			// 通用模块对应的对话库id
			fixedDialogVo.setCidMIdDt(robotsVo.getCidMIdDt());
			String[] dokeys = robotsVo.getDokey().split("\\|");
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < dokeys.length; i++) {
				Map<String, Object> mapi = new HashMap<String, Object>();
				mapi.put("question", dokeys[i]);
				jsonArray.add(mapi);
			}
			fixedDialogVo.setAwords(JSON.toJSONString(jsonArray));
			fixedDialogVo.setAnwers("[{\"answer\":\"好的\"}]");
			Map<String, Object> mapScript = new HashMap<String, Object>();
			mapScript.put("cidM", uuid);
			mapScript.put("stype", "openModel");
			fixedDialogVo.setScript(JSON.toJSONString(mapScript));
			fixedDialogVo.setAptype("1");
			// Result result = fixedDialogService.addFixedDialog(fixedDialogVo);

			dokeyDialogId = addFixedDialog4Model(fixedDialogVo);

			// 返回首页 问答插入
			fixedDialogVo.setCidMIdDt("");
			fixedDialogVo.setAwords("[{\"question\":\"返回首页\"}]");
			mapScript.put("cidM", robotsVo.getCidM());
			fixedDialogVo.setScript(JSON.toJSONString(mapScript));
			homePageDialogId = addFixedDialog4Model(fixedDialogVo);

			// Map<String, Object> mapReturnData = (Map<String, Object>)
			// result.getReturnData();
			// map.put("dialogId", mapReturnData.get("dialogId").toString());
			map.put("dialogId", dokeyDialogId);
		}

		// 查询robots appid 和secrets
		Robots robotsEntity = robotsDao.getEntityById(robotsVo);
		try {
			robotsModuleDao.insert(robotsModule);
			dialogTypesDao.addDialogTypes(dialogTypes);
			map.put("cidM", uuid);
			map.put("idDt", idDt);
			dialogCacheListenerService.ActionDialogCacheEvent(uuid, ActionEnum.ADD_DIALOG_MODEL.getCode(), uuid,
					robotsEntity.getAppid(), robotsEntity.getSecret());
			if (!isCommon) {
				dialogCacheListenerService.ActionDialogCacheEvent(dokeyDialogId, ActionEnum.ADD_DIALOG.getCode(),
						dokeyDialogId, robotsEntity.getAppid(), robotsEntity.getSecret());
				dialogCacheListenerService.ActionDialogCacheEvent(homePageDialogId, ActionEnum.ADD_DIALOG.getCode(),
						homePageDialogId, robotsEntity.getAppid(), robotsEntity.getSecret());
			}
			return uuid;
		} catch (Exception e) {
			logger.error("新增情景模块失败");
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
	}

	@Transactional(value = "robotTransactionManager", readOnly = true, rollbackFor = Exception.class)
	@Override
	public Result listShareAbility(RobotsVo robotsVo) {
		if (StringUtils.isBlank(robotsVo.getIdAc())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		List<DialogTypes> list = dialogTypesDao.listShareAbility(robotsVo);
		return ResultUtil.success(list);
	}

	@Transactional(value = "robotTransactionManager", readOnly = false, rollbackFor = Exception.class)
	@Override
	public Result modifyRobotModelById(RobotsVo robotsVo) {
		if (StringUtils.isBlank(robotsVo.getIdM()) || StringUtils.isBlank(robotsVo.getMname())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}

		// 判断是否存在同名
		if (robotsModuleDao.getCountByCidMname(robotsVo) > 0) {
			throw new RobotException(ResultEnum.EXISTS_SAME_DIALOG_MODEL);
		}

		RobotsModule robotsModule = new RobotsModule();
		robotsModule.setCidM(robotsVo.getIdM());
		robotsModule.setMname(robotsVo.getMname());
		// robotsModule.setDokey(robotsVo.getDokey());
		try {
			robotsModuleDao.updateByPrimaryKeySelective(robotsModule);
		} catch (Exception e) {
			logger.error("更新情景模块失败");
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		return ResultUtil.success();
	}

	@Transactional(value = "robotTransactionManager", readOnly = true, rollbackFor = Exception.class)
	@Override
	public Result getRobotModelById(RobotsVo robotsVo) {
		if (StringUtils.isBlank(robotsVo.getIdM())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		RobotsModule robotsModule = robotsModuleDao.getRobotModelById(robotsVo);
		return ResultUtil.success(robotsModule);
	}

	@Transactional(value = "robotTransactionManager", readOnly = false, rollbackFor = Exception.class)
	@Override
	public Result delRobotById(Robots robots) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(robots.getId())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		try {
			robotsDao.delRobotById(robots);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		return ResultUtil.success();
	}

	/**
	 * 添加固定对话
	 */
	private String addFixedDialog4Model(FixedDialogVo fixedDialogVo) throws RobotException {
		// 如果账户id,对话类别id,脚本内容，机器回答或者匹配类型为空，则抛出参数不完整异常
		if (StringUtils.isBlank(fixedDialogVo.getAnwers()) || StringUtils.isBlank(fixedDialogVo.getAptype())
				|| StringUtils.isBlank(fixedDialogVo.getIdAc()) || StringUtils.isBlank(fixedDialogVo.getIdDt())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}

		Map<String, Object> map = new HashMap<>(16);
		Gson gson = new Gson();
		// 人说的话List
		List<Map<String, Object>> awordsList = gson.fromJson(fixedDialogVo.getAwords(),
				new TypeToken<ArrayList<Map<String, Object>>>() {
				}.getType());
		// 参数List
		List<Map<String, Object>> keywordsList = gson.fromJson(fixedDialogVo.getKeywords(),
				new TypeToken<ArrayList<Map<String, Object>>>() {
				}.getType());
		// 机器人回答List
		List<Map<String, Object>> anwersList = gson.fromJson(fixedDialogVo.getAnwers(),
				new TypeToken<ArrayList<Map<String, Object>>>() {
				}.getType());

		// 当前登录的用户
		// DialogUser dialogUser = (DialogUser)
		// SecurityUtils.getSubject().getSession().getAttribute("dialogUser");
		// String dialogUserId = dialogUser.getUserId();
		String dialogUserId = "1";

		// 组别id
		String teamId = IdGen.uuid();
		// map.put("id", teamId);
		// 人机对话主键id
		String dialogId = IdGen.uuid();

		if (DialogMan.APTYPE_1.equals(fixedDialogVo.getAptype())) {
			// 模糊匹配,不需要关键词字段

			// =========在人机对话表（DIALOG）中插入数据==================
			Dialog dialog = new Dialog();

			dialog.setId(dialogId);
			// 账户id
			dialog.setIdAc(fixedDialogVo.getIdAc());
			// 类别id
			dialog.setIdDt(fixedDialogVo.getIdDt());
			// 对话类别。1-未知（暂不用），2-固定应答，3-接口应答，4-多轮对话主题入口，5-多轮对话中。
			dialog.setAtype(Dialog.ATYPE_2);
			// 组别id
			dialog.setTeamId(teamId);
			// 状态。1-启用。2-禁用。
			dialog.setState(Dialog.STATE_1);
			// 创建者
			dialog.setCreateBy(dialogUserId);
			// 更新者
			dialog.setUpdateBy(dialogUserId);
			// 删除标记。0-正常，1-已删除
			dialog.setDelFlag(Dialog.DEL_FLAG_0);
			// 通用情景模块id-对应的对话库id
			dialog.setCidMIdDt(fixedDialogVo.getCidMIdDt());

			// 插入数据
			try {
				dialogDao.insert(dialog);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RobotException(ResultEnum.FAIL);
			}

			for (Map<String, Object> awordsMap : awordsList) {
				// =========在人说表（DIALOG_MAN）中插入数据===================
				DialogMan dialogMan = new DialogMan();
				// 人说表主键id
				String id = IdGen.uuid();
				dialogMan.setId(id);
				// 账户id
				dialogMan.setIdAc(fixedDialogVo.getIdAc());
				// 人机对话id
				dialogMan.setIdD(dialogId);
				// 匹配类型。1-模糊匹配，2-关键词匹配
				dialogMan.setAptype(DialogMan.APTYPE_1);
				// 人说的话
				String awordsStr = awordsMap.get("question").toString();
				dialogMan.setAword(awordsStr);
				// 创建者
				dialogMan.setCreateBy(dialogUserId);
				// 更新者
				dialogMan.setUpdateBy(dialogUserId);

				// 插入数据
				try {
					dialogManDao.insert(dialogMan);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RobotException(ResultEnum.FAIL);
				}

			}

			// =========在机器固定应答表（DIALOG_ROBOT_STATIC）中插入数据==============
			for (Map<String, Object> anwersMap : anwersList) {
				DialogRobotStatic dialogRobotStatic = new DialogRobotStatic();
				// 机器固定应答主键id
				String dialogRobotStaticId = IdGen.uuid();
				dialogRobotStatic.setId(dialogRobotStaticId);
				// 账户id
				dialogRobotStatic.setIdAc(fixedDialogVo.getIdAc());
				// 人机对话id
				dialogRobotStatic.setIdD(dialogId);
				// 机器应答内容
				String answerStr = anwersMap.get("answer").toString();
				dialogRobotStatic.setAnswer(answerStr);
				// 状态。1-启用。2-禁用。
				dialogRobotStatic.setState(Integer.parseInt(DialogRobotStatic.STATE_1));
				// 创建者
				dialogRobotStatic.setCreateBy(dialogUserId);
				// 更新者
				dialogRobotStatic.setUpdateBy(dialogUserId);

				// 插入数据
				try {
					dialogRobotStaticDao.insert(dialogRobotStatic);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RobotException(ResultEnum.FAIL);
				}

			}
			// =========在答案返回脚本表（DIALOG_ANSWER_SCRIPT）中插入数据==============
			DialogAnswerScript dialogAnswerScript = new DialogAnswerScript();
			// 答案返回脚本表主键id
			String dialogAnswerScriptId = IdGen.uuid();
			dialogAnswerScript.setId(dialogAnswerScriptId);
			// 账户id
			dialogAnswerScript.setIdAc(fixedDialogVo.getIdAc());
			// 人机对话id
			dialogAnswerScript.setIdD(dialogId);
			// 对话类别。1-多轮对话，2-固定应答，3-接口应答。
			dialogAnswerScript.setAtype(Integer.parseInt(DialogAnswerScript.ATYPE_2));
			// 脚本类型。 1-无，2-接口中返回，3-自定义返回。 当为接口应答时，该字段才有意义
			dialogAnswerScript.setStype(Integer.parseInt(DialogAnswerScript.STYPE_1));
			// 返回脚本
			if (StringUtils.isNotBlank(fixedDialogVo.getScript())) {
				dialogAnswerScript.setScripts(fixedDialogVo.getScript());
			} else {
				dialogAnswerScript.setScripts("");
			}
			// 状态。1-启用。2-禁用。
			dialogAnswerScript.setState(Integer.parseInt(DialogAnswerScript.STATE_1));
			// 创建者
			dialogAnswerScript.setCreateBy(dialogUserId);
			// 更新者
			dialogAnswerScript.setUpdateBy(dialogUserId);

			// 插入数据
			try {
				dialogAnswerScriptDao.insert(dialogAnswerScript);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RobotException(ResultEnum.FAIL);
			}
		} else if (DialogMan.APTYPE_2.equals(fixedDialogVo.getAptype())) {
			// 关键词匹配，不需要人说的话字段

			// =========在人机对话表（DIALOG）中插入数据==================
			Dialog dialog = new Dialog();

			dialog.setId(dialogId);
			// 账户id
			dialog.setIdAc(fixedDialogVo.getIdAc());
			// 类别id
			dialog.setIdDt(fixedDialogVo.getIdDt());
			// 对话类别。1-未知（暂不用），2-固定应答，3-接口应答，4-多轮对话主题入口，5-多轮对话中。
			dialog.setAtype(Dialog.ATYPE_2);
			// 组别id
			dialog.setTeamId(teamId);
			// 状态。1-启用。2-禁用。
			dialog.setState(Dialog.STATE_1);
			// 创建者
			dialog.setCreateBy(dialogUserId);
			// 更新者
			dialog.setUpdateBy(dialogUserId);
			// 删除标记。0-正常，1-已删除
			dialog.setDelFlag(Dialog.DEL_FLAG_0);

			// 插入数据
			try {
				dialogDao.insert(dialog);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RobotException(ResultEnum.FAIL);
			}

			Set<String> wordsSet = new HashSet<>();
			for (Map<String, Object> keywordsMap : keywordsList) {
				// =========在人说表（DIALOG_MAN）中插入数据===================
				DialogMan dialogMan = new DialogMan();
				// 人说表主键id
				String id = IdGen.uuid();
				dialogMan.setId(id);
				// 账户id
				dialogMan.setIdAc(fixedDialogVo.getIdAc());
				// 人机对话id
				dialogMan.setIdD(dialogId);
				// 匹配类型。1-模糊匹配，2-关键词匹配
				dialogMan.setAptype(DialogMan.APTYPE_2);
				// 关键词个数
				Integer awordnum = 0;
				// valueList
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> valueList = (List<Map<String, Object>>) (keywordsMap.get("value") != null
						? keywordsMap.get("value") : "");
				for (int i = 0; i < valueList.size(); i++) {
					// 关键词类型
					String wtype = valueList.get(i).get("wtype") == null ? ""
							: valueList.get(i).get("wtype").toString();
					// 关键词
					String wkey = valueList.get(i).get("wkey") == null ? "" : valueList.get(i).get("wkey").toString();
					// 同义词
					String near = valueList.get(i).get("near") == null ? "" : valueList.get(i).get("near").toString();
					// 动态词类型
					String wdyna = valueList.get(i).get("wdyna") == null ? ""
							: valueList.get(i).get("wdyna").toString();
					// 处理关键词、同义词
					if (StringUtils.isNotBlank(wkey)) {
						wordsSet.add(wkey);
					}
					if (StringUtils.isNotBlank(near)) {
						if (near.contains("|")) {
							String nearArr[] = near.split("\\|");
							for (int n = 0; n < nearArr.length; n++) {
								wordsSet.add(nearArr[n]);
							}
						} else {
							wordsSet.add(near);
						}
					}
					if (i == 0) {
						awordnum++;
						// 关键词1类型。1-固定，2-变化。
						if (StringUtils.isNotBlank(wtype)) {
							dialogMan.setAword1type(Integer.parseInt(wtype));
							// 关键词1类型为变化时，存在动态词类型
							if (DialogMan.AWORD1_TYPE_2.equals(wtype)) {
								if (StringUtils.isNotBlank(wdyna)) {
									dialogMan.setAword1dyna("(&" + wdyna + "&)");
								}
							}
						}
						// 关键词
						if (StringUtils.isNotBlank(wkey)) {
							dialogMan.setAword1(wkey);
						}
						// 关键词1同义词。多个用 | 分开
						if (StringUtils.isNotBlank(near)) {
							dialogMan.setAword1near(near);
						}
					} else if (i == 1) {
						awordnum++;
						// 关键词2类型。1-固定，2-变化。
						if (StringUtils.isNotBlank(wtype)) {
							dialogMan.setAword2type(Integer.parseInt(wtype));
							// 关键词1类型为变化时，存在动态词类型
							if (DialogMan.AWORD1_TYPE_2.equals(wtype)) {
								if (StringUtils.isNotBlank(wdyna)) {
									dialogMan.setAword2dyna("(&" + wdyna + "&)");
								}
							}
						}
						// 关键词
						if (StringUtils.isNotBlank(wkey)) {
							dialogMan.setAword2(wkey);
						}
						// 关键词1同义词。多个用 | 分开
						if (StringUtils.isNotBlank(near)) {
							dialogMan.setAword2near(near);
						}
					} else if (i == 2) {
						awordnum++;
						// 关键词3类型。1-固定，2-变化。
						if (StringUtils.isNotBlank(wtype)) {
							dialogMan.setAword3type(Integer.parseInt(wtype));
							// 关键词1类型为变化时，存在动态词类型
							if (DialogMan.AWORD1_TYPE_2.equals(wtype)) {
								if (StringUtils.isNotBlank(wdyna)) {
									dialogMan.setAword3dyna("(&" + wdyna + "&)");
								}
							}
						}
						// 关键词
						if (StringUtils.isNotBlank(wkey)) {
							dialogMan.setAword3(wkey);
						}
						// 关键词1同义词。多个用 | 分开
						if (StringUtils.isNotBlank(near)) {
							dialogMan.setAword3near(near);
						}
					} else if (i == 3) {
						awordnum++;
						// 关键词4类型。1-固定，2-变化。
						if (StringUtils.isNotBlank(wtype)) {
							dialogMan.setAword4type(Integer.parseInt(wtype));
							// 关键词1类型为变化时，存在动态词类型
							if (DialogMan.AWORD1_TYPE_2.equals(wtype)) {
								if (StringUtils.isNotBlank(wdyna)) {
									dialogMan.setAword4dyna("(&" + wdyna + "&)");
								}
							}
						}
						// 关键词
						if (StringUtils.isNotBlank(wkey)) {
							dialogMan.setAword4(wkey);
						}
						// 关键词1同义词。多个用 | 分开
						if (StringUtils.isNotBlank(near)) {
							dialogMan.setAword4near(near);
						}
					} else if (i == 4) {
						awordnum++;
						// 关键词5类型。1-固定，2-变化。
						if (StringUtils.isNotBlank(wtype)) {
							dialogMan.setAword5type(Integer.parseInt(wtype));
							// 关键词1类型为变化时，存在动态词类型
							if (DialogMan.AWORD1_TYPE_2.equals(wtype)) {
								if (StringUtils.isNotBlank(wdyna)) {
									dialogMan.setAword5dyna("(&" + wdyna + "&)");
								}
							}
						}
						// 关键词
						if (StringUtils.isNotBlank(wkey)) {
							dialogMan.setAword5(wkey);
						}
						// 关键词1同义词。多个用 | 分开
						if (StringUtils.isNotBlank(near)) {
							dialogMan.setAword5near(near);
						}
					}
				}

				dialogMan.setAwordnum(awordnum);
				// 创建者
				dialogMan.setCreateBy(dialogUserId);
				// 更新者
				dialogMan.setUpdateBy(dialogUserId);

				// 插入数据
				try {
					dialogManDao.insert(dialogMan);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RobotException(ResultEnum.FAIL);
				}
			}

			// =========在机器固定应答表（DIALOG_ROBOT_STATIC）中插入数据==============
			DialogRobotStatic dialogRobotStatic = new DialogRobotStatic();
			for (Map<String, Object> anwersMap : anwersList) {
				// 机器固定应答主键id
				String dialogRobotStaticId = IdGen.uuid();
				dialogRobotStatic.setId(dialogRobotStaticId);
				// 账户id
				dialogRobotStatic.setIdAc(fixedDialogVo.getIdAc());
				// 人机对话id
				dialogRobotStatic.setIdD(dialogId);
				// 机器应答内容
				String answerStr = anwersMap.get("answer").toString();
				dialogRobotStatic.setAnswer(answerStr);
				// 状态。1-启用。2-禁用。
				dialogRobotStatic.setState(Integer.parseInt(DialogRobotStatic.STATE_1));
				// 创建者
				dialogRobotStatic.setCreateBy(dialogUserId);
				// 更新者
				dialogRobotStatic.setUpdateBy(dialogUserId);

				// 插入数据
				try {
					dialogRobotStaticDao.insert(dialogRobotStatic);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RobotException(ResultEnum.FAIL);
				}
			}

			// =========在答案返回脚本表（DIALOG_ANSWER_SCRIPT）中插入数据==============
			DialogAnswerScript dialogAnswerScript = new DialogAnswerScript();
			// 答案返回脚本表主键id
			String dialogAnswerScriptId = IdGen.uuid();
			dialogAnswerScript.setId(dialogAnswerScriptId);
			// 账户id
			dialogAnswerScript.setIdAc(fixedDialogVo.getIdAc());
			// 人机对话id
			dialogAnswerScript.setIdD(dialogId);
			// 对话类别。1-多轮对话，2-固定应答，3-接口应答。
			dialogAnswerScript.setAtype(Integer.parseInt(DialogAnswerScript.ATYPE_2));
			// 脚本类型。 1-无，2-接口中返回，3-自定义返回。 当为接口应答时，该字段才有意义
			dialogAnswerScript.setStype(Integer.parseInt(DialogAnswerScript.STYPE_1));
			// 返回脚本
			if (StringUtils.isNotBlank(fixedDialogVo.getScript())) {
				dialogAnswerScript.setScripts(fixedDialogVo.getScript());
			} else {
				dialogAnswerScript.setScripts("");
			}
			// 状态。1-启用。2-禁用。
			dialogAnswerScript.setState(Integer.parseInt(DialogAnswerScript.STATE_1));
			// 创建者
			dialogAnswerScript.setCreateBy(dialogUserId);
			// 更新者
			dialogAnswerScript.setUpdateBy(dialogUserId);

			// 插入数据
			try {
				dialogAnswerScriptDao.insert(dialogAnswerScript);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RobotException(ResultEnum.FAIL);
			}

			// =========在词库表（WORDS）中插入数据==============
			List<Words> wordsList = new ArrayList<>();
			if (wordsSet.size() > 0) {
				for (String wname : wordsSet) {
					Words words = new Words();
					words.setId(IdGen.uuid());
					words.setIdAc(fixedDialogVo.getIdAc());// 账户ID
					words.setWname(wname);
					words.setAutoin(Words.AUTOIN_1);
					words.setWften(0);
					words.setWx(Words.WX_n);
					words.setState(Words.STATE_1);
					words.setCreateBy(dialogUserId);
					words.setUpdateBy(dialogUserId);
					wordsList.add(words);
				}
			}
			for (Words words : wordsList) {
				// 插入数据
				try {
					if (wordsList.size() > 0) {
						wordsDao.insert(words);
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new RobotException(ResultEnum.FAIL);
				}
			}
		}
		// 插入缓存事件记录表,保持事务一致性
		// dialogCacheListenerService.ActionDialogCacheEvent(dialogId,
		// ActionEnum.ADD_DIALOG.getCode(), dialogId, "", "");
		map.put("dialogId", dialogId);
		return dialogId;
	}
}
