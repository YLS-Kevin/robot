package com.yls.projects.robot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.yls.frame.common.Exception.RobotException;
import com.yls.frame.common.enums.ResultEnum;
import com.yls.frame.common.utils.IdGen;
import com.yls.frame.common.utils.ResultUtil;
import com.yls.frame.common.utils.StringUtils;
import com.yls.frame.common.web.Result;
import com.yls.projects.common.cache.ActionEnum;
import com.yls.projects.common.cache.DialogCacheListenerService;
import com.yls.projects.robot.dao.DialogAnswerScriptDao;
import com.yls.projects.robot.dao.DialogCacheMapper;
import com.yls.projects.robot.dao.DialogDao;
import com.yls.projects.robot.dao.DialogManDao;
import com.yls.projects.robot.dao.DialogRobotInterDao;
import com.yls.projects.robot.dao.DialogRobotStaticDao;
import com.yls.projects.robot.dao.WordsDao;
import com.yls.projects.robot.entity.Dialog;
import com.yls.projects.robot.entity.DialogAnswerScript;
import com.yls.projects.robot.entity.DialogMan;
import com.yls.projects.robot.entity.DialogMoreAndOne2;
import com.yls.projects.robot.entity.DialogRobotInter;
import com.yls.projects.robot.entity.DialogRobotStatic;
import com.yls.projects.robot.entity.Words;
import com.yls.projects.robot.service.MulDialogService;
import com.yls.projects.robot.utils.GsonAdatper;
import com.yls.projects.robot.vo.MulDialogVo;

/**
 * 多轮对话Service实现类
 * 
 * @author 陈俊
 * @date 2018年5月17日
 */
@Service("mulDialogService")
public class MulDialogSericeImpl implements MulDialogService {

	@Autowired
	private DialogDao dialogDao;

	@Autowired
	private DialogManDao dialogManDao;

	@Autowired
	private DialogRobotStaticDao dialogRobotStaticDao;

	@Autowired
	private DialogAnswerScriptDao dialogAnswerScriptDao;

	@Autowired
	private DialogRobotInterDao dialogRobotInterDao;

	@Autowired
	private WordsDao wordsDao;

	@Autowired
	private DialogCacheListenerService dialogCacheListenerService;

	@Autowired
	private DialogCacheMapper dialogCacheMapper;

	/**
	 * 添加多轮对话
	 */
	@Override
	@SuppressWarnings("rawtypes")
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	public Result addMulDialog(MulDialogVo mulDialogVo) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(mulDialogVo.getStype()) || StringUtils.isBlank(mulDialogVo.getScript())
				|| StringUtils.isBlank(mulDialogVo.getAptype()) || StringUtils.isBlank(mulDialogVo.getAtype())
				|| StringUtils.isBlank(mulDialogVo.getIdAp()) || StringUtils.isBlank(mulDialogVo.getIdAc())
				|| StringUtils.isBlank(mulDialogVo.getIdDt()) || StringUtils.isBlank(mulDialogVo.getType())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}

		Map<String, Object> map = new HashMap<>(16);
		Gson gson = new Gson();
		// 参数List
		List<Map<String, Object>> keywordsList = gson.fromJson(mulDialogVo.getKeywords(),
				new TypeToken<ArrayList<Map<String, Object>>>() {
				}.getType());
		// 固定答案List
		List<Map<String, Object>> awordsList = gson.fromJson(mulDialogVo.getAwords(),
				new TypeToken<ArrayList<Map<String, Object>>>() {
				}.getType());
		// 脚本List
		List<Map<String, Object>> scriptList = gson.fromJson(mulDialogVo.getScript(),
				new TypeToken<ArrayList<Map<String, Object>>>() {
				}.getType());
		// 人说的话List
		List<Map<String, Object>> anwersList = gson.fromJson(mulDialogVo.getAnwers(),
				new TypeToken<ArrayList<Map<String, Object>>>() {
				}.getType());

		// 当前登录的用户
		// DialogUser dialogUser = (DialogUser)
		// SecurityUtils.getSubject().getSession().getAttribute("dialogUser");
		// String dialogUserId = dialogUser.getUserId();
		String dialogUserId = "1";

		// 组别id
		String teamId = IdGen.uuid();

		if (DialogMan.APTYPE_1.equals(mulDialogVo.getAptype())) {
			// 模糊匹配,不需要关键词字段
			if (Dialog.ATYPE_2.equals(mulDialogVo.getType())) {
				// 固定应答
				this.addFixedDialogByAptype1(mulDialogVo, dialogUserId, awordsList, anwersList, scriptList, teamId);
			} else if (Dialog.ATYPE_3.equals(mulDialogVo.getType())) {
				// 接口应答
				this.addInterfaceDefByAptype1(mulDialogVo, dialogUserId, awordsList, anwersList, scriptList, teamId);
			}
		} else if (DialogMan.APTYPE_2.equals(mulDialogVo.getAptype())) {
			// 关键词匹配,不需要人说的话字段
			if (Dialog.ATYPE_2.equals(mulDialogVo.getType())) {
				// 固定应答
				this.addFixedDialogByAptype2(mulDialogVo, dialogUserId, keywordsList, anwersList, scriptList, teamId);
			} else if (Dialog.ATYPE_3.equals(mulDialogVo.getType())) {
				// 接口应答
				this.addInterfaceDefByAptype2(mulDialogVo, dialogUserId, keywordsList, anwersList, scriptList, teamId);
			}
		}
		return ResultUtil.success(map);
	}

	/**
	 * 修改多轮对话
	 */
	@Override
	@SuppressWarnings("rawtypes")
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	public Result updateMulDialog(MulDialogVo mulDialogVo) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(mulDialogVo.getStype()) || StringUtils.isBlank(mulDialogVo.getScript())
				|| StringUtils.isBlank(mulDialogVo.getAptype()) || StringUtils.isBlank(mulDialogVo.getAtype())
				|| StringUtils.isBlank(mulDialogVo.getIdAp()) || StringUtils.isBlank(mulDialogVo.getIdAc())
				|| StringUtils.isBlank(mulDialogVo.getIdDt()) || StringUtils.isBlank(mulDialogVo.getType())
				|| StringUtils.isBlank(mulDialogVo.getIdD())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}

		// 获取修改前对话数据
		List<DialogMoreAndOne2> updateDataLists = dialogCacheMapper.findDialogById(mulDialogVo.getIdD());
		GsonBuilder gsonBuilder = new GsonBuilder();
		// 注册自定义String的适配器
		gsonBuilder.registerTypeAdapter(String.class, GsonAdatper.STRING);
		Gson gsonUpdate = gsonBuilder.create();
		String updateData = gsonUpdate.toJson(updateDataLists);

		Map<String, Object> map = new HashMap<>(16);
		Gson gson = new Gson();
		// 参数List
		List<Map<String, Object>> keywordsList = gson.fromJson(mulDialogVo.getKeywords(),
				new TypeToken<ArrayList<Map<String, Object>>>() {
				}.getType());
		// 固定答案List
		List<Map<String, Object>> awordsList = gson.fromJson(mulDialogVo.getAwords(),
				new TypeToken<ArrayList<Map<String, Object>>>() {
				}.getType());
		// 脚本List
		List<Map<String, Object>> scriptList = gson.fromJson(mulDialogVo.getScript(),
				new TypeToken<ArrayList<Map<String, Object>>>() {
				}.getType());
		// 人说的话List
		List<Map<String, Object>> anwersList = gson.fromJson(mulDialogVo.getAnwers(),
				new TypeToken<ArrayList<Map<String, Object>>>() {
				}.getType());

		// 当前登录的用户
		// DialogUser dialogUser = (DialogUser)
		// SecurityUtils.getSubject().getSession().getAttribute("dialogUser");
		// String dialogUserId = dialogUser.getUserId();
		String dialogUserId = "1";
		// ============更新dialog表的数据============
		dialogDao.updateDialog(mulDialogVo.getIdD());

		if (DialogMan.APTYPE_1.equals(mulDialogVo.getAptype())) {
			// 模糊匹配,不需要关键词字段
			if (Dialog.ATYPE_2.equals(mulDialogVo.getType())) {
				// 固定应答
				this.updateFixedDialogByAptype1(mulDialogVo, dialogUserId, awordsList, anwersList, scriptList);
			} else if (Dialog.ATYPE_3.equals(mulDialogVo.getType())) {
				// 接口应答
				this.updateInterfaceDefByAptype1(mulDialogVo, dialogUserId, awordsList, anwersList, scriptList);
			}
		} else if (DialogMan.APTYPE_2.equals(mulDialogVo.getAptype())) {
			// 关键词匹配,不需要人说的话字段
			if (Dialog.ATYPE_2.equals(mulDialogVo.getType())) {
				// 固定应答
				this.updateFixedDialogByAptype2(mulDialogVo, dialogUserId, keywordsList, anwersList, scriptList);
			} else if (Dialog.ATYPE_3.equals(mulDialogVo.getType())) {
				// 接口应答
				this.updateInterfaceDefByAptype2(mulDialogVo, dialogUserId, keywordsList, anwersList, scriptList);
			}
		}

		dialogCacheListenerService.ActionDialogCacheEvent(mulDialogVo.getIdD(), ActionEnum.UPDATE_DIALOG.getCode(),
				updateData, "", "");

		return ResultUtil.success(map);

	}

	/**
	 * 模糊匹配,固定应答
	 */
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	public void addFixedDialogByAptype1(MulDialogVo mulDialogVo, String dialogUserId,
			List<Map<String, Object>> awordsList, List<Map<String, Object>> anwersList,
			List<Map<String, Object>> scriptList, String teamId) {
		// =========在人机对话表（DIALOG）中插入数据==================
		Dialog dialog = new Dialog();
		// 人机对话主键id
		String dialogId = IdGen.uuid();
		dialog.setId(dialogId);
		// 账户id
		dialog.setIdAc(mulDialogVo.getIdAc());
		// 类别id
		dialog.setIdDt(mulDialogVo.getIdDt());
		// 对话类别。1-未知（暂不用），2-固定应答，3-接口应答
		dialog.setAtype(mulDialogVo.getType());
		// 多轮对话主题类 : 4-多轮对话主题入口 5-多轮对话中
		dialog.setMulDialogType(mulDialogVo.getAtype());
		// 组别id
		dialog.setTeamId(teamId);
		// 主题id
		dialog.setIdAp(mulDialogVo.getIdAp());
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

		for (Map<String, Object> awordsMap : awordsList) {
			// =========在人说表（DIALOG_MAN）中插入数据===================
			DialogMan dialogMan = new DialogMan();
			// 人说表主键id
			String id = IdGen.uuid();
			dialogMan.setId(id);
			// 账户id
			dialogMan.setIdAc(mulDialogVo.getIdAc());
			// 人机对话id
			dialogMan.setIdD(dialogId);
			// 匹配类型。1-模糊匹配，2-关键词匹配
			dialogMan.setAptype(DialogMan.APTYPE_1);
			// 人说的话
			dialogMan.setAword(awordsMap.get("question").toString());
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
			dialogRobotStatic.setIdAc(mulDialogVo.getIdAc());
			// 人机对话id
			dialogRobotStatic.setIdD(dialogId);
			// 机器应答内容
			dialogRobotStatic.setAnswer(anwersMap.get("answer").toString());
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

		for (Map<String, Object> scriptMap : scriptList) {
			// =========在答案返回脚本表（DIALOG_ANSWER_SCRIPT）中插入数据==============
			DialogAnswerScript dialogAnswerScript = new DialogAnswerScript();
			// 答案返回脚本表主键id
			String dialogAnswerScriptId = IdGen.uuid();
			dialogAnswerScript.setId(dialogAnswerScriptId);
			// 账户id
			dialogAnswerScript.setIdAc(mulDialogVo.getIdAc());
			// 人机对话id
			dialogAnswerScript.setIdD(dialogId);
			// 对话类别。1-多轮对话，2-固定应答，3-接口应答。
			dialogAnswerScript.setAtype(Integer.parseInt(DialogAnswerScript.ATYPE_2));
			// 脚本类型。 1-无，2-接口中返回，3-自定义返回。 当为接口应答时，该字段才有意义
			dialogAnswerScript.setStype(Integer.parseInt(DialogAnswerScript.STYPE_1));
			// 返回脚本
			dialogAnswerScript.setScripts(scriptMap.get("scripts").toString());
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
		}

		dialogCacheListenerService.ActionDialogCacheEvent(dialogId, ActionEnum.ADD_DIALOG.getCode(), dialogId, "", "");
	}

	/**
	 * 关键词匹配,固定应答
	 */
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	public void addFixedDialogByAptype2(MulDialogVo mulDialogVo, String dialogUserId,
			List<Map<String, Object>> keywordsList, List<Map<String, Object>> anwersList,
			List<Map<String, Object>> scriptList, String teamId) {
		// =========在人机对话表（DIALOG）中插入数据==================
		Dialog dialog = new Dialog();
		// 人机对话主键id
		String dialogId = IdGen.uuid();
		dialog.setId(dialogId);
		// 账户id
		dialog.setIdAc(mulDialogVo.getIdAc());
		// 类别id
		dialog.setIdDt(mulDialogVo.getIdDt());
		// 对话类别。1-未知（暂不用），2-固定应答，3-接口应答
		dialog.setAtype(mulDialogVo.getType());
		// 多轮对话主题类 : 4-多轮对话主题入口 5-多轮对话中
		dialog.setMulDialogType(mulDialogVo.getAtype());
		// 组别id
		dialog.setTeamId(teamId);
		// 主题id
		dialog.setIdAp(mulDialogVo.getIdAp());
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
			dialogMan.setIdAc(mulDialogVo.getIdAc());
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
				String wtype = valueList.get(i).get("wtype") == null ? "" : valueList.get(i).get("wtype").toString();
				// 关键词
				String wkey = valueList.get(i).get("wkey") == null ? "" : valueList.get(i).get("wkey").toString();
				// 同义词
				String near = valueList.get(i).get("near") == null ? "" : valueList.get(i).get("near").toString();
				// 动态词类型
				String wdyna = valueList.get(i).get("wdyna") == null ? "" : valueList.get(i).get("wdyna").toString();
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
			// 关键词个数
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
			dialogRobotStatic.setIdAc(mulDialogVo.getIdAc());
			// 人机对话id
			dialogRobotStatic.setIdD(dialogId);
			// 机器应答内容
			dialogRobotStatic.setAnswer(anwersMap.get("answer").toString());
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

		for (Map<String, Object> scriptMap : scriptList) {
			// =========在答案返回脚本表（DIALOG_ANSWER_SCRIPT）中插入数据==============
			DialogAnswerScript dialogAnswerScript = new DialogAnswerScript();
			// 答案返回脚本表主键id
			String dialogAnswerScriptId = IdGen.uuid();
			dialogAnswerScript.setId(dialogAnswerScriptId);
			// 账户id
			dialogAnswerScript.setIdAc(mulDialogVo.getIdAc());
			// 人机对话id
			dialogAnswerScript.setIdD(dialogId);
			// 对话类别。1-多轮对话，2-固定应答，3-接口应答。
			dialogAnswerScript.setAtype(Integer.parseInt(DialogAnswerScript.ATYPE_2));
			// 脚本类型。 1-无，2-接口中返回，3-自定义返回。 当为接口应答时，该字段才有意义
			dialogAnswerScript.setStype(Integer.parseInt(DialogAnswerScript.STYPE_1));
			// 返回脚本
			dialogAnswerScript.setScripts(scriptMap.get("scripts").toString());
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
		}

		// =========在词库表（WORDS）中插入数据==============
		List<Words> wordsList = new ArrayList<>();
		if (wordsSet.size() > 0) {
			for (String wname : wordsSet) {
				Words words = new Words();
				words.setId(IdGen.uuid());
				words.setIdAc(mulDialogVo.getIdAc());// 账户ID
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

		dialogCacheListenerService.ActionDialogCacheEvent(dialogId, ActionEnum.ADD_DIALOG.getCode(), dialogId, "", "");
	}

	/**
	 * 模糊匹配,接口对话
	 */
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	public void addInterfaceDefByAptype1(MulDialogVo mulDialogVo, String dialogUserId,
			List<Map<String, Object>> awordsList, List<Map<String, Object>> anwersList,
			List<Map<String, Object>> scriptList, String teamId) {
		// =========在人机对话表（DIALOG）中插入数据==================
		Dialog dialog = new Dialog();
		// 人机对话主键id
		String dialogId = IdGen.uuid();
		dialog.setId(dialogId);
		// 账户id
		dialog.setIdAc(mulDialogVo.getIdAc());
		// 类别id
		dialog.setIdDt(mulDialogVo.getIdDt());
		// 对话类别。1-未知（暂不用），2-固定应答，3-接口应答
		dialog.setAtype(mulDialogVo.getType());
		// 多轮对话主题类 : 4-多轮对话主题入口 5-多轮对话中
		dialog.setMulDialogType(mulDialogVo.getAtype());
		// 组别id
		dialog.setTeamId(teamId);
		// 主题id
		dialog.setIdAp(mulDialogVo.getIdAp());
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

		for (Map<String, Object> awordsMap : awordsList) {
			// =========在人说表（DIALOG_MAN）中插入数据===================
			DialogMan dialogMan = new DialogMan();
			// 人说表主键id
			String id = IdGen.uuid();
			dialogMan.setId(id);
			// 账户id
			dialogMan.setIdAc(mulDialogVo.getIdAc());
			// 人机对话id
			dialogMan.setIdD(dialogId);
			// 匹配类型。1-模糊匹配，2-关键词匹配
			dialogMan.setAptype(DialogMan.APTYPE_1);
			// 人说的话
			dialogMan.setAword(awordsMap.get("question").toString());
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

		// =========机器接口应答表（DIALOG_ROBOT_INTER）中插入数据==============
		DialogRobotInter dialogRobotInter = new DialogRobotInter();
		// 机器接口应答表主键id
		String dialogRobotInterId = IdGen.uuid();
		dialogRobotInter.setId(dialogRobotInterId);
		// 账户id
		dialogRobotInter.setIdAc(mulDialogVo.getIdAc());
		// 人机对话id
		dialogRobotInter.setIdD(dialogId);
		// 数据接口id
		dialogRobotInter.setIdDi(mulDialogVo.getIdDi());
		// 创建者
		dialogRobotInter.setCreateBy(dialogUserId);
		// 更新者
		dialogRobotInter.setUpdateBy(dialogUserId);
		// 插入数据
		try {
			dialogRobotInterDao.insert(dialogRobotInter);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}

		for (Map<String, Object> scriptMap : scriptList) {
			// =========在答案返回脚本表（DIALOG_ANSWER_SCRIPT）中插入数据==============
			DialogAnswerScript dialogAnswerScript = new DialogAnswerScript();
			// 答案返回脚本表主键id
			String dialogAnswerScriptId = IdGen.uuid();
			dialogAnswerScript.setId(dialogAnswerScriptId);
			// 账户id
			dialogAnswerScript.setIdAc(mulDialogVo.getIdAc());
			// 人机对话id
			dialogAnswerScript.setIdD(dialogId);
			// 对话类别。1-多轮对话，2-固定应答，3-接口应答。
			dialogAnswerScript.setAtype(Integer.parseInt(DialogAnswerScript.ATYPE_3));
			// 脚本类型。 1-无，2-接口中返回，3-自定义返回。 当为接口应答时，该字段才有意义
			dialogAnswerScript.setStype(Integer.parseInt(mulDialogVo.getStype()));
			if (DialogAnswerScript.STYPE_3.equals(mulDialogVo.getStype())) {
				// 返回参数
				dialogAnswerScript.setRepara(scriptMap.get("repara").toString());
				// 包含
				dialogAnswerScript.setSin(Short.parseShort(scriptMap.get("sin").toString()));
				// 包含关键词
				dialogAnswerScript.setSinword(scriptMap.get("sinword").toString());
			}
			// 返回脚本
			dialogAnswerScript.setScripts(scriptMap.get("scripts").toString());
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
		}
		dialogCacheListenerService.ActionDialogCacheEvent(dialogId, ActionEnum.ADD_DIALOG.getCode(), dialogId, "", "");
	}

	/**
	 * 关键词匹配,接口对话
	 */
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	public void addInterfaceDefByAptype2(MulDialogVo mulDialogVo, String dialogUserId,
			List<Map<String, Object>> keywordsList, List<Map<String, Object>> anwersList,
			List<Map<String, Object>> scriptList, String teamId) {
		// =========在人机对话表（DIALOG）中插入数据==================
		Dialog dialog = new Dialog();
		// 人机对话主键id
		String dialogId = IdGen.uuid();
		dialog.setId(dialogId);
		// 账户id
		dialog.setIdAc(mulDialogVo.getIdAc());
		// 类别id
		dialog.setIdDt(mulDialogVo.getIdDt());
		// 对话类别。1-未知（暂不用），2-固定应答，3-接口应答
		dialog.setAtype(mulDialogVo.getType());
		// 多轮对话主题类 : 4-多轮对话主题入口 5-多轮对话中
		dialog.setMulDialogType(mulDialogVo.getAtype());
		// 组别id
		dialog.setTeamId(teamId);
		// 主题id
		dialog.setIdAp(mulDialogVo.getIdAp());
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
			dialogMan.setIdAc(mulDialogVo.getIdAc());
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
				// 关键词
				String wkey = valueList.get(i).get("wkey") == null ? "" : valueList.get(i).get("wkey").toString();
				// url参数
				String wpara = valueList.get(i).get("wpara") == null ? "" : valueList.get(i).get("wpara").toString();
				// 动态词类型
				String wdyna = valueList.get(i).get("wdyna") == null ? "" : valueList.get(i).get("wdyna").toString();
				// 关键词类型
				String wtype = valueList.get(i).get("wtype") == null ? "" : valueList.get(i).get("wtype").toString();
				// 同义词
				String near = valueList.get(i).get("near") == null ? "" : valueList.get(i).get("near").toString();
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
					// 关键词
					if (StringUtils.isNotBlank(wkey)) {
						dialogMan.setAword1(wkey);
					}
					// url参数
					if (StringUtils.isNotBlank(wpara)) {
						dialogMan.setAword1para(wpara);
					}
					// 动态词类型
					if (StringUtils.isNotBlank(wdyna)) {
						dialogMan.setAword1dyna("(&" + wdyna + "&)");
					}
					// 关键词类型
					if (StringUtils.isNotBlank(wtype)) {
						dialogMan.setAword1type(Integer.parseInt(wtype));
					}
					// 同义词
					if (StringUtils.isNotBlank(near)) {
						dialogMan.setAword1near(near);
					}
				} else if (i == 1) {
					awordnum++;
					// 关键词
					if (StringUtils.isNotBlank(wkey)) {
						dialogMan.setAword2(wkey);
					}
					// url参数
					if (StringUtils.isNotBlank(wpara)) {
						dialogMan.setAword2para(wpara);
					}
					// 动态词类型
					if (StringUtils.isNotBlank(wdyna)) {
						dialogMan.setAword2dyna("(&" + wdyna + "&)");
					}
					// 关键词类型
					if (StringUtils.isNotBlank(wtype)) {
						dialogMan.setAword2type(Integer.parseInt(wtype));
					}
					// 同义词
					if (StringUtils.isNotBlank(near)) {
						dialogMan.setAword2near(near);
					}
				} else if (i == 2) {
					awordnum++;
					// 关键词
					if (StringUtils.isNotBlank(wkey)) {
						dialogMan.setAword3(wkey);
					}
					// url参数
					if (StringUtils.isNotBlank(wpara)) {
						dialogMan.setAword3para(wpara);
					}
					// 动态词类型
					if (StringUtils.isNotBlank(wdyna)) {
						dialogMan.setAword3dyna("(&" + wdyna + "&)");
					}
					// 关键词类型
					if (StringUtils.isNotBlank(wtype)) {
						dialogMan.setAword3type(Integer.parseInt(wtype));
					}
					// 同义词
					if (StringUtils.isNotBlank(near)) {
						dialogMan.setAword3near(near);
					}
				} else if (i == 3) {
					awordnum++;
					// 关键词
					if (StringUtils.isNotBlank(wkey)) {
						dialogMan.setAword4(wkey);
					}
					// url参数
					if (StringUtils.isNotBlank(wpara)) {
						dialogMan.setAword4para(wpara);
					}
					// 动态词类型
					if (StringUtils.isNotBlank(wdyna)) {
						dialogMan.setAword4dyna("(&" + wdyna + "&)");
					}
					// 关键词类型
					if (StringUtils.isNotBlank(wtype)) {
						dialogMan.setAword4type(Integer.parseInt(wtype));
					}
					// 同义词
					if (StringUtils.isNotBlank(near)) {
						dialogMan.setAword4near(near);
					}
				} else if (i == 4) {
					awordnum++;
					// 关键词
					if (StringUtils.isNotBlank(wkey)) {
						dialogMan.setAword5(wkey);
					}
					// url参数
					if (StringUtils.isNotBlank(wpara)) {
						dialogMan.setAword5para(wpara);
					}
					// 动态词类型
					if (StringUtils.isNotBlank(wdyna)) {
						dialogMan.setAword5dyna("(&" + wdyna + "&)");
					}
					// 关键词类型
					if (StringUtils.isNotBlank(wtype)) {
						dialogMan.setAword5type(Integer.parseInt(wtype));
					}
					// 同义词
					if (StringUtils.isNotBlank(near)) {
						dialogMan.setAword5near(near);
					}
				}
			}
			// 关键词个数
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

		// =========机器接口应答表（DIALOG_ROBOT_INTER）中插入数据==============
		DialogRobotInter dialogRobotInter = new DialogRobotInter();
		// 机器接口应答表主键id
		String dialogRobotInterId = IdGen.uuid();
		dialogRobotInter.setId(dialogRobotInterId);
		// 账户id
		dialogRobotInter.setIdAc(mulDialogVo.getIdAc());
		// 人机对话id
		dialogRobotInter.setIdD(dialogId);
		// 数据接口id
		dialogRobotInter.setIdDi(mulDialogVo.getIdDi());
		// 创建者
		dialogRobotInter.setCreateBy(dialogUserId);
		// 更新者
		dialogRobotInter.setUpdateBy(dialogUserId);
		// 插入数据
		try {
			dialogRobotInterDao.insert(dialogRobotInter);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}

		for (Map<String, Object> scriptMap : scriptList) {
			// =========在答案返回脚本表（DIALOG_ANSWER_SCRIPT）中插入数据==============
			DialogAnswerScript dialogAnswerScript = new DialogAnswerScript();
			// 答案返回脚本表主键id
			String dialogAnswerScriptId = IdGen.uuid();
			dialogAnswerScript.setId(dialogAnswerScriptId);
			// 账户id
			dialogAnswerScript.setIdAc(mulDialogVo.getIdAc());
			// 人机对话id
			dialogAnswerScript.setIdD(dialogId);
			// 对话类别。1-多轮对话，2-固定应答，3-接口应答。
			dialogAnswerScript.setAtype(Integer.parseInt(DialogAnswerScript.ATYPE_3));
			// 脚本类型。 1-无，2-接口中返回，3-自定义返回。 当为接口应答时，该字段才有意义
			dialogAnswerScript.setStype(Integer.parseInt(mulDialogVo.getStype()));
			if (DialogAnswerScript.STYPE_3.equals(mulDialogVo.getStype())) {
				// 返回参数
				dialogAnswerScript.setRepara(scriptMap.get("repara").toString());
				// 包含
				dialogAnswerScript.setSin(Short.parseShort(scriptMap.get("sin").toString()));
				// 包含关键词
				dialogAnswerScript.setSinword(scriptMap.get("sinword").toString());
			}
			// 返回脚本
			dialogAnswerScript.setScripts(scriptMap.get("scripts").toString());
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
		}

		// =========在词库表（WORDS）中插入数据==============
		List<Words> wordsList = new ArrayList<>();
		if (wordsSet.size() > 0) {
			for (String wname : wordsSet) {
				Words words = new Words();
				words.setId(IdGen.uuid());
				words.setIdAc(mulDialogVo.getIdAc());// 账户ID
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
		dialogCacheListenerService.ActionDialogCacheEvent(dialogId, ActionEnum.ADD_DIALOG.getCode(), dialogId, "", "");
	}

	/**
	 * 模糊匹配,修改固定应答
	 */
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	public void updateFixedDialogByAptype1(MulDialogVo mulDialogVo, String dialogUserId,
			List<Map<String, Object>> awordsList, List<Map<String, Object>> anwersList,
			List<Map<String, Object>> scriptList) {
		// ============更新人说表的数据============
		// 先删除数据，在插入数据
		try {
			dialogManDao.deleteDialogManByDialogId(mulDialogVo.getIdD());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		for (Map<String, Object> awordsMap : awordsList) {
			DialogMan dialogMan = new DialogMan();
			String question = awordsMap.get("question").toString();
			// 主键id
			dialogMan.setId(IdGen.uuid());
			// 账户id
			dialogMan.setIdAc(mulDialogVo.getIdAc());
			// 人机对话id
			dialogMan.setIdD(mulDialogVo.getIdD());
			// 匹配类型。1-模糊匹配，2-关键词匹配
			dialogMan.setAptype(DialogMan.APTYPE_1);
			// 人说的话
			dialogMan.setAword(question);
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

		// =============更新机器固定应答表的数据===============
		// 先删除数据,再插入数据
		try {
			dialogRobotStaticDao.deleteByDialogId(mulDialogVo.getIdD());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		for (Map<String, Object> anwersMap : anwersList) {
			DialogRobotStatic dialogRobotStatic = new DialogRobotStatic();
			String answer = anwersMap.get("answer").toString();
			// 机器固定应答主键id
			dialogRobotStatic.setId(IdGen.uuid());
			// 账户id
			dialogRobotStatic.setIdAc(mulDialogVo.getIdAc());
			// 人机对话id
			dialogRobotStatic.setIdD(mulDialogVo.getIdD());
			// 机器应答内容
			dialogRobotStatic.setAnswer(answer);
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

		// ===========更新答案返回脚本表的数据==================
		for (Map<String, Object> scriptMap : scriptList) {
			DialogAnswerScript dialogAnswerScript = new DialogAnswerScript();
			dialogAnswerScript.setId(scriptMap.get("id").toString());
			dialogAnswerScript.setScripts(scriptMap.get("scripts").toString());
			dialogAnswerScript.setUpdateBy(dialogUserId);
			// 更新数据
			try {
				dialogAnswerScriptDao.update(dialogAnswerScript);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RobotException(ResultEnum.FAIL);
			}
		}
	}

	/**
	 * 关键词匹配,修改固定应答
	 */
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	public void updateFixedDialogByAptype2(MulDialogVo mulDialogVo, String dialogUserId,
			List<Map<String, Object>> keywordsList, List<Map<String, Object>> anwersList,
			List<Map<String, Object>> scriptList) {
		Set<String> wordsSet = new HashSet<>();
		// ============更新人说表的数据=============
		// 先删除数据，在插入数据
		try {
			dialogManDao.deleteDialogManByDialogId(mulDialogVo.getIdD());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		for (Map<String, Object> keywordsMap : keywordsList) {
			DialogMan dialogMan = new DialogMan();
			// 人说表主键id
			dialogMan.setId(IdGen.uuid());
			// 账户id
			dialogMan.setIdAc(mulDialogVo.getIdAc());
			// 人机对话id
			dialogMan.setIdD(mulDialogVo.getIdD());
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
				String wtype = valueList.get(i).get("wtype") == null ? "" : valueList.get(i).get("wtype").toString();
				// 关键词
				String wkey = valueList.get(i).get("wkey") == null ? "" : valueList.get(i).get("wkey").toString();
				// 同义词
				String near = valueList.get(i).get("near") == null ? "" : valueList.get(i).get("near").toString();
				// 动态词类型
				String wdyna = valueList.get(i).get("wdyna") == null ? "" : valueList.get(i).get("wdyna").toString();
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

		// =============更新机器固定应答表的数据===============
		// 先删除数据,再插入数据
		try {
			dialogRobotStaticDao.deleteByDialogId(mulDialogVo.getIdD());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		for (Map<String, Object> anwersMap : anwersList) {
			DialogRobotStatic dialogRobotStatic = new DialogRobotStatic();
			String answer = anwersMap.get("answer").toString();
			// 机器固定应答主键id
			dialogRobotStatic.setId(IdGen.uuid());
			// 账户id
			dialogRobotStatic.setIdAc(mulDialogVo.getIdAc());
			// 人机对话id
			dialogRobotStatic.setIdD(mulDialogVo.getIdD());
			// 机器应答内容
			dialogRobotStatic.setAnswer(answer);
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

		// ===========更新答案返回脚本表的数据==================
		for (Map<String, Object> scriptMap : scriptList) {
			DialogAnswerScript dialogAnswerScript = new DialogAnswerScript();
			dialogAnswerScript.setId(scriptMap.get("id").toString());
			dialogAnswerScript.setScripts(scriptMap.get("scripts").toString());
			dialogAnswerScript.setUpdateBy(dialogUserId);
			// 更新数据
			try {
				dialogAnswerScriptDao.update(dialogAnswerScript);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RobotException(ResultEnum.FAIL);
			}
		}

		// =========在词库表（WORDS）中插入数据==============
		List<Words> wordsList = new ArrayList<>();
		if (wordsSet.size() > 0) {
			for (String wname : wordsSet) {
				Words words = new Words();
				words.setId(IdGen.uuid());
				words.setIdAc(mulDialogVo.getIdAc());// 账户ID
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

	/**
	 * 模糊匹配,修改接口对话
	 */
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	public void updateInterfaceDefByAptype1(MulDialogVo mulDialogVo, String dialogUserId,
			List<Map<String, Object>> awordsList, List<Map<String, Object>> anwersList,
			List<Map<String, Object>> scriptList) {
		// ============更新人说表的数据============
		// 先删除数据，在插入数据
		try {
			dialogManDao.deleteDialogManByDialogId(mulDialogVo.getIdD());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		for (Map<String, Object> awordsMap : awordsList) {
			DialogMan dialogMan = new DialogMan();
			String question = awordsMap.get("question").toString();
			// 主键id
			dialogMan.setId(IdGen.uuid());
			// 账户id
			dialogMan.setIdAc(mulDialogVo.getIdAc());
			// 人机对话id
			dialogMan.setIdD(mulDialogVo.getIdD());
			// 匹配类型。1-模糊匹配，2-关键词匹配
			dialogMan.setAptype(DialogMan.APTYPE_1);
			// 人说的话
			dialogMan.setAword(question);
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

		// =========更新机器接口应答表的数据==============
		DialogRobotInter dialogRobotInter = new DialogRobotInter();
		dialogRobotInter.setId(mulDialogVo.getId());
		dialogRobotInter.setIdDi(mulDialogVo.getIdDi());
		dialogRobotInter.setUpdateBy(dialogUserId);
		// 更新数据
		try {
			dialogRobotInterDao.update(dialogRobotInter);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}

		// ===========更新答案返回脚本表的数据==================
		// 更新脚本表先全部删除，再新增新的数据
		try {
			dialogAnswerScriptDao.delete(mulDialogVo.getIdD());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		for (Map<String, Object> scriptMap : scriptList) {
			DialogAnswerScript dialogAnswerScript = new DialogAnswerScript();
			// 答案返回脚本表主键id
			dialogAnswerScript.setId(IdGen.uuid());
			// 账户id
			dialogAnswerScript.setIdAc(mulDialogVo.getIdAc());
			// 人机对话id
			dialogAnswerScript.setIdD(mulDialogVo.getIdD());
			// 对话类别。1-多轮对话，2-固定应答，3-接口应答。
			dialogAnswerScript.setAtype(Integer.parseInt(DialogAnswerScript.ATYPE_3));
			// 脚本类型。 1-无，2-接口中返回，3-自定义返回。 当为接口应答时，该字段才有意义
			String stype = mulDialogVo.getStype();
			dialogAnswerScript.setStype(Integer.parseInt(stype));
			// 只有自定义返回才有下面4个参数
			if (DialogAnswerScript.STYPE_3.equals(stype)) {
				// 返回参数
				dialogAnswerScript.setRepara(scriptMap.get("repara").toString());
				// 包含
				dialogAnswerScript.setSin(Short.parseShort(scriptMap.get("sin").toString()));
				// 包含关键词
				dialogAnswerScript.setSinword(scriptMap.get("sinword").toString());
			}
			// 返回脚本
			dialogAnswerScript.setScripts(scriptMap.get("scripts").toString());
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
		}
	}

	/**
	 * 关键词匹配,修改接口对话
	 */
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	public void updateInterfaceDefByAptype2(MulDialogVo mulDialogVo, String dialogUserId,
			List<Map<String, Object>> keywordsList, List<Map<String, Object>> anwersList,
			List<Map<String, Object>> scriptList) {
		Set<String> wordsSet = new HashSet<>();
		// ============更新人说表的数据=============
		// 先删除数据，在插入数据
		try {
			dialogManDao.deleteDialogManByDialogId(mulDialogVo.getIdD());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		for (Map<String, Object> keywordsMap : keywordsList) {
			DialogMan dialogMan = new DialogMan();
			// 人说表主键id
			dialogMan.setId(IdGen.uuid());
			// 账户id
			dialogMan.setIdAc(mulDialogVo.getIdAc());
			// 人机对话id
			dialogMan.setIdD(mulDialogVo.getIdD());
			// 匹配类型。1-模糊匹配，2-关键词匹配
			dialogMan.setAptype(DialogMan.APTYPE_2);
			// 关键词个数
			Integer awordnum = 0;
			// valueList
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> valueList = (List<Map<String, Object>>) (keywordsMap.get("value") != null
					? keywordsMap.get("value") : "");
			for (int i = 0; i < valueList.size(); i++) {
				// 关键词
				String wkey = valueList.get(i).get("wkey") == null ? "" : valueList.get(i).get("wkey").toString();
				// url参数
				String wpara = valueList.get(i).get("wpara") == null ? "" : valueList.get(i).get("wpara").toString();
				// 动态词类型
				String wdyna = valueList.get(i).get("wdyna") == null ? "" : valueList.get(i).get("wdyna").toString();
				// 关键词类型
				String wtype = valueList.get(i).get("wtype") == null ? "" : valueList.get(i).get("wtype").toString();
				// 同义词
				String near = valueList.get(i).get("near") == null ? "" : valueList.get(i).get("near").toString();
				if (i == 0) {
					awordnum++;
					// 关键词
					if (StringUtils.isNotBlank(wkey)) {
						dialogMan.setAword1(wkey);
					}
					// url参数
					if (StringUtils.isNotBlank(wpara)) {
						dialogMan.setAword1para(wpara);
					}
					// 动态词类型
					if (StringUtils.isNotBlank(wdyna)) {
						dialogMan.setAword1dyna("(&" + wdyna + "&)");
					}
					// 关键词类型
					if (StringUtils.isNotBlank(wtype)) {
						dialogMan.setAword1type(Integer.parseInt(wtype));
					}
					// 同义词
					if (StringUtils.isNotBlank(near)) {
						dialogMan.setAword1near(near);
					}
				} else if (i == 1) {
					awordnum++;
					// 关键词
					if (StringUtils.isNotBlank(wkey)) {
						dialogMan.setAword2(wkey);
					}
					// url参数
					if (StringUtils.isNotBlank(wpara)) {
						dialogMan.setAword2para(wpara);
					}
					// 动态词类型
					if (StringUtils.isNotBlank(wdyna)) {
						dialogMan.setAword2dyna("(&" + wdyna + "&)");
					}
					// 关键词类型
					if (StringUtils.isNotBlank(wtype)) {
						dialogMan.setAword2type(Integer.parseInt(wtype));
					}
					// 同义词
					if (StringUtils.isNotBlank(near)) {
						dialogMan.setAword2near(near);
					}
				} else if (i == 2) {
					awordnum++;
					// 关键词
					if (StringUtils.isNotBlank(wkey)) {
						dialogMan.setAword3(wkey);
					}
					// url参数
					if (StringUtils.isNotBlank(wpara)) {
						dialogMan.setAword3para(wpara);
					}
					// 动态词类型
					if (StringUtils.isNotBlank(wdyna)) {
						dialogMan.setAword3dyna("(&" + wdyna + "&)");
					}
					// 关键词类型
					if (StringUtils.isNotBlank(wtype)) {
						dialogMan.setAword3type(Integer.parseInt(wtype));
					}
					// 同义词
					if (StringUtils.isNotBlank(near)) {
						dialogMan.setAword3near(near);
					}
				} else if (i == 3) {
					awordnum++;
					// 关键词
					if (StringUtils.isNotBlank(wkey)) {
						dialogMan.setAword4(wkey);
					}
					// url参数
					if (StringUtils.isNotBlank(wpara)) {
						dialogMan.setAword4para(wpara);
					}
					// 动态词类型
					if (StringUtils.isNotBlank(wdyna)) {
						dialogMan.setAword4dyna("(&" + wdyna + "&)");
					}
					// 关键词类型
					if (StringUtils.isNotBlank(wtype)) {
						dialogMan.setAword4type(Integer.parseInt(wtype));
					}
					// 同义词
					if (StringUtils.isNotBlank(near)) {
						dialogMan.setAword4near(near);
					}
				} else if (i == 4) {
					awordnum++;
					// 关键词
					if (StringUtils.isNotBlank(wkey)) {
						dialogMan.setAword5(wkey);
					}
					// url参数
					if (StringUtils.isNotBlank(wpara)) {
						dialogMan.setAword5para(wpara);
					}
					// 动态词类型
					if (StringUtils.isNotBlank(wdyna)) {
						dialogMan.setAword5dyna("(&" + wdyna + "&)");
					}
					// 关键词类型
					if (StringUtils.isNotBlank(wtype)) {
						dialogMan.setAword5type(Integer.parseInt(wtype));
					}
					// 同义词
					if (StringUtils.isNotBlank(near)) {
						dialogMan.setAword5near(near);
					}
				}
			}
			// 关键词个数
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

		// =========更新机器接口应答表的数据==============
		DialogRobotInter dialogRobotInter = new DialogRobotInter();
		dialogRobotInter.setId(mulDialogVo.getId());
		dialogRobotInter.setIdDi(mulDialogVo.getIdDi());
		dialogRobotInter.setUpdateBy(dialogUserId);
		// 更新数据
		try {
			dialogRobotInterDao.update(dialogRobotInter);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}

		// ===========更新答案返回脚本表的数据==================
		// 更新脚本表先全部删除，再新增新的数据
		try {
			dialogAnswerScriptDao.delete(mulDialogVo.getIdD());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		for (Map<String, Object> scriptMap : scriptList) {
			DialogAnswerScript dialogAnswerScript = new DialogAnswerScript();
			// 答案返回脚本表主键id
			dialogAnswerScript.setId(IdGen.uuid());
			// 账户id
			dialogAnswerScript.setIdAc(mulDialogVo.getIdAc());
			// 人机对话id
			dialogAnswerScript.setIdD(mulDialogVo.getIdD());
			// 对话类别。1-多轮对话，2-固定应答，3-接口应答。
			dialogAnswerScript.setAtype(Integer.parseInt(DialogAnswerScript.ATYPE_3));
			// 脚本类型。 1-无，2-接口中返回，3-自定义返回。 当为接口应答时，该字段才有意义
			String stype = mulDialogVo.getStype();
			dialogAnswerScript.setStype(Integer.parseInt(stype));
			// 只有自定义返回才有下面4个参数
			if (DialogAnswerScript.STYPE_3.equals(stype)) {
				// 返回参数
				dialogAnswerScript.setRepara(scriptMap.get("repara").toString());
				// 包含
				dialogAnswerScript.setSin(Short.parseShort(scriptMap.get("sin").toString()));
				// 包含关键词
				dialogAnswerScript.setSinword(scriptMap.get("sinword").toString());
			}
			// 返回脚本
			dialogAnswerScript.setScripts(scriptMap.get("scripts").toString());
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
		}

		// =========在词库表（WORDS）中插入数据==============
		List<Words> wordsList = new ArrayList<>();
		if (wordsSet.size() > 0) {
			for (String wname : wordsSet) {
				Words words = new Words();
				words.setId(IdGen.uuid());
				words.setIdAc(mulDialogVo.getIdAc());// 账户ID
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

	/**
	 * 根据人机对话id查询多轮对话详情
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Result getMulDialogByIdD(MulDialogVo mulDialogVo) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(mulDialogVo.getIdD()) || StringUtils.isBlank(mulDialogVo.getType())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		Map<String, Object> map = new HashMap<>();
		// 人机对话ID
		String dialogId = mulDialogVo.getIdD();
		// 人说表List
		List<DialogMan> dialogManList = dialogManDao.getListByIdD(dialogId);
		// 答案返回脚本表List
		List<DialogAnswerScript> dialogAnswerScriptList = dialogAnswerScriptDao.getListByIdD(dialogId);
		map.put("dialogMan", dialogManList);
		map.put("dialogAnswerScript", dialogAnswerScriptList);
		if (Dialog.ATYPE_2.equals(mulDialogVo.getType())) {
			// 固定应答
			// 机器人固定应答表List
			List<DialogRobotStatic> dialogRobotStaticList = dialogRobotStaticDao.getDialogRobotStaticByIdD(dialogId);
			map.put("dialogRobotStatic", dialogRobotStaticList);
		} else if (Dialog.ATYPE_3.equals(mulDialogVo.getType())) {
			// 接口应答
			// 机器人接口应答表List
			List<DialogRobotInter> dialogRobotInterList = dialogRobotInterDao.getListByIdD(dialogId);
			map.put("dialogRobotInter", dialogRobotInterList);
		}
		return ResultUtil.success(map);
	}
}
