package com.yls.projects.robot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
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
import com.yls.frame.common.web.Result;
import com.yls.projects.common.cache.ActionEnum;
import com.yls.projects.common.cache.DialogCacheListenerService;
import com.yls.projects.robot.dao.DialogAnswerScriptDao;
import com.yls.projects.robot.dao.DialogCacheMapper;
import com.yls.projects.robot.dao.DialogDao;
import com.yls.projects.robot.dao.DialogManDao;
import com.yls.projects.robot.dao.DialogRobotStaticDao;
import com.yls.projects.robot.dao.WordsDao;
import com.yls.projects.robot.entity.Dialog;
import com.yls.projects.robot.entity.DialogAnswerScript;
import com.yls.projects.robot.entity.DialogMan;
import com.yls.projects.robot.entity.DialogMoreAndOne2;
import com.yls.projects.robot.entity.DialogRobotStatic;
import com.yls.projects.robot.entity.Words;
import com.yls.projects.robot.service.FixedDialogService;
import com.yls.projects.robot.utils.GsonAdatper;
import com.yls.projects.robot.vo.FixedDialogVo;

/**
 * 固定对话Service实现类
 * 
 * @author 陈华湛
 * @date 2018年5月8日上午10:16:12
 */
@Service("fixedDialogService")
@Transactional(readOnly = true)
public class FixedDialogServiceImpl implements FixedDialogService {

	@Autowired
	private DialogDao dialogDao;

	@Autowired
	private DialogManDao dialogManDao;

	@Autowired
	private DialogRobotStaticDao dialogRobotStaticDao;

	@Autowired
	private DialogAnswerScriptDao dialogAnswerScriptDao;

	@Autowired
	private DialogCacheListenerService dialogCacheListenerService;

	@Autowired
	private WordsDao wordsDao;

	@Autowired
	private DialogCacheMapper dialogCacheMapper;

	/**
	 * 添加固定对话
	 */
	@Override
	@SuppressWarnings("rawtypes")
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class, readOnly = false)
	public Result addFixedDialog(FixedDialogVo fixedDialogVo) throws RobotException {
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
		dialogCacheListenerService.ActionDialogCacheEvent(dialogId, ActionEnum.ADD_DIALOG.getCode(), dialogId, "", "");
		map.put("dialogId", dialogId);
		return ResultUtil.success(map);
	}

	/**
	 * 根据id删除固定对话
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class, readOnly = false)
	public Result delFixedDialog(String id) {
		Map<String, Object> map = new HashMap<>(16);
		map.put("id", id);

		Integer integer = 0;
		try {
			integer = dialogDao.delById(id);
			// 插入缓存事件记录表,保持事务一致性
			dialogCacheListenerService.ActionDialogCacheEvent(id, ActionEnum.DELETE_DIALOG.getCode(), id, "", "");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		return ResultUtil.success(map);
	}

	/**
	 * 根据组别id获取固定对话对应信息
	 */
	@Override
	public Result getFixedDialogByTeamId(String teamId) {
		Map<String, Object> map = new HashMap<>(16);

		// 匹配类型
		String aptype = null;

		// 构造人awords
		List<Map<String, Object>> awordsList = new ArrayList<>();

		// 构造keywords
		List<Map<String, Object>> keywordsList = new ArrayList<>();

		// 构造anwers
		List<Map<String, Object>> anwersList = new ArrayList<>();

		// 构造script
		Map<String, Object> scriptMap = new HashMap<>(16);

		// =========获取人机对话表数据===============
		List<Dialog> dialogList = dialogDao.getDialogByTeamId(teamId);

		for (Dialog dialog : dialogList) {
			// 人机对话表主键id
			String dialogId = dialog.getId();

			// =========获取人说表数据===============
			// 根据人机对话id获取对应数据
			DialogMan dialogMan = dialogManDao.getDialogManByIdD(dialogId);

			if (dialogMan.getAptype().equals(DialogMan.APTYPE_1)) {
				// 模糊匹配
				map.put("aptype", DialogMan.APTYPE_1);
				Map<String, Object> awordsMap = new HashMap<>(16);
				awordsMap.put("question", dialogMan.getAword());
				awordsMap.put("id", dialogMan.getId());
				awordsList.add(awordsMap);

			} else if (dialogMan.getAptype().equals(DialogMan.APTYPE_2)) {
				// 关键词匹配
				map.put("aptype", DialogMan.APTYPE_2);

				if (StringUtils.isNotBlank(dialogMan.getAword1())) {
					Map<String, Object> key1Map = new HashMap<>(16);
					key1Map.put("id", dialogMan.getId());
					key1Map.put("key", dialogMan.getAword1());
					key1Map.put("near", dialogMan.getAword1near());
					keywordsList.add(key1Map);
				}
				if (StringUtils.isNotBlank(dialogMan.getAword2())) {
					Map<String, Object> key2Map = new HashMap<>(16);
					key2Map.put("id", dialogMan.getId());
					key2Map.put("key", dialogMan.getAword2());
					key2Map.put("near", dialogMan.getAword2near());
					keywordsList.add(key2Map);
				}
				if (StringUtils.isNotBlank(dialogMan.getAword3())) {
					Map<String, Object> key3Map = new HashMap<>(16);
					key3Map.put("id", dialogMan.getId());
					key3Map.put("key", dialogMan.getAword3());
					key3Map.put("near", dialogMan.getAword3near());
					keywordsList.add(key3Map);
				}
				if (StringUtils.isNotBlank(dialogMan.getAword4())) {
					Map<String, Object> key4Map = new HashMap<>(16);
					key4Map.put("id", dialogMan.getId());
					key4Map.put("key", dialogMan.getAword4());
					key4Map.put("near", dialogMan.getAword4near());
					keywordsList.add(key4Map);
				}
				if (StringUtils.isNotBlank(dialogMan.getAword5())) {
					Map<String, Object> key5Map = new HashMap<>(16);
					key5Map.put("id", dialogMan.getId());
					key5Map.put("key", dialogMan.getAword5());
					key5Map.put("near", dialogMan.getAword5near());
					keywordsList.add(key5Map);
				}
			}

			// =========获取机器固定应答表数据===============
			List<DialogRobotStatic> dialogRobotStaticList = dialogRobotStaticDao.getDialogRobotStaticByIdD(dialogId);
			;
			for (DialogRobotStatic dialogRobotStatic : dialogRobotStaticList) {
				Map<String, Object> answerMap = new HashMap<>(16);
				answerMap.put("id", dialogRobotStatic.getId());
				answerMap.put("answer", dialogRobotStatic.getAnswer());
				anwersList.add(answerMap);
			}

			// =========获取答案返回脚本表数据===============
			// 根据对话id获取对应脚本数据
			DialogAnswerScript dialogAnswerScript = dialogAnswerScriptDao.getDialogAnswerScriptByIdD(dialogId);
			if (dialogAnswerScript != null) {
				scriptMap.put("script", dialogAnswerScript.getScripts());
			}

		}
		map.put("awords", awordsList);
		map.put("keywords", keywordsList);
		map.put("anwers", anwersList);
		map.put("script", scriptMap);
		map.put("teamId", teamId);

		return ResultUtil.success(map);
	}

	/**
	 * 修改固定对话
	 */
	@Override
	@SuppressWarnings("rawtypes")
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class, readOnly = false)
	public Result modifyFixedDialog(FixedDialogVo fixedDialogVo) {
		// 如果脚本内容,机器回答,匹配类型,账号id,人机对话id为空，则抛出参数不完整异常
		if (StringUtils.isBlank(fixedDialogVo.getAnwers()) || StringUtils.isBlank(fixedDialogVo.getAptype())
				|| StringUtils.isBlank(fixedDialogVo.getIdAc()) || StringUtils.isBlank(fixedDialogVo.getIdD())
				|| StringUtils.isBlank(fixedDialogVo.getScriptId())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}

		// 获取修改前对话数据
		List<DialogMoreAndOne2> updateDataLists = dialogCacheMapper.findDialogById(fixedDialogVo.getIdD());
		GsonBuilder gsonBuilder = new GsonBuilder();
		// 注册自定义String的适配器
		gsonBuilder.registerTypeAdapter(String.class, GsonAdatper.STRING);
		Gson gsonUpdate = gsonBuilder.create();
		String updateData = gsonUpdate.toJson(updateDataLists);

		// 当前登录的用户
		// DialogUser dialogUser = (DialogUser)
		// SecurityUtils.getSubject().getSession().getAttribute("dialogUser");
		// String dialogUserId = dialogUser.getUserId();
		String dialogUserId = "1";

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
		Set<String> wordsSet = new HashSet<>();
		
		// ============更新dialog表的数据============
		dialogDao.updateDialog(fixedDialogVo.getIdD());
		
		// ============更新人说表的数据============
		// 先删除数据，在插入数据
		try {
			dialogManDao.deleteDialogManByDialogId(fixedDialogVo.getIdD());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		if (DialogMan.APTYPE_1.equals(fixedDialogVo.getAptype())) {
			// 模糊匹配，不需要关键词字段

			// ============更新人说表的数据============
			for (Map<String, Object> awordsMap : awordsList) {
				DialogMan dialogMan = new DialogMan();
				String question = awordsMap.get("question").toString();
				// 主键id
				dialogMan.setId(IdGen.uuid());
				// 账户id
				dialogMan.setIdAc(fixedDialogVo.getIdAc());
				// 人机对话id
				dialogMan.setIdD(fixedDialogVo.getIdD());
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
		} else if (DialogMan.APTYPE_2.equals(fixedDialogVo.getAptype())) {
			// 关键词匹配，不需要人说的话字段

			// ============更新人说表的数据=============
			for (Map<String, Object> keywordsMap : keywordsList) {
				DialogMan dialogMan = new DialogMan();
				// 人说表主键id
				dialogMan.setId(IdGen.uuid());
				// 账户id
				dialogMan.setIdAc(fixedDialogVo.getIdAc());
				// 人机对话id
				dialogMan.setIdD(fixedDialogVo.getIdD());
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
		}

		// =============更新机器固定应答表的数据===============
		// 先删除数据,再插入数据
		try {
			dialogRobotStaticDao.deleteByDialogId(fixedDialogVo.getIdD());
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
			dialogRobotStatic.setIdAc(fixedDialogVo.getIdAc());
			// 人机对话id
			dialogRobotStatic.setIdD(fixedDialogVo.getIdD());
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
		DialogAnswerScript dialogAnswerScript = new DialogAnswerScript();
		dialogAnswerScript.setId(fixedDialogVo.getScriptId());
		dialogAnswerScript.setScripts(fixedDialogVo.getScript());
		dialogAnswerScript.setUpdateBy(dialogUserId);
		// 更新数据
		try {
			dialogAnswerScriptDao.update(dialogAnswerScript);
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
				words.setAutoin("1");
				words.setWften(0);
				words.setWx("n");
				words.setState("1");
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

		dialogCacheListenerService.ActionDialogCacheEvent(fixedDialogVo.getIdD(), ActionEnum.UPDATE_DIALOG.getCode(),
				updateData, "", "");
		return ResultUtil.success(map);
	}

	/**
	 * 根据人机对话ID查询详情
	 */
	@Override
	@SuppressWarnings("rawtypes")
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	public Result getFixedDialogByIdD(FixedDialogVo fixedDialogVo) {
		Map<String, Object> map = new HashMap<>();
		// 人机对话ID
		String dialogId = fixedDialogVo.getIdD();
		// 人说表List
		List<DialogMan> dialogManList = dialogManDao.getListByIdD(dialogId);
		// 机器人固定应答表List
		List<DialogRobotStatic> dialogRobotStaticList = dialogRobotStaticDao.getDialogRobotStaticByIdD(dialogId);
		// 答案返回脚本表List
		List<DialogAnswerScript> dialogAnswerScriptList = dialogAnswerScriptDao.getListByIdD(dialogId);
		map.put("dialogMan", dialogManList);
		map.put("dialogRobotStatic", dialogRobotStaticList);
		map.put("dialogAnswerScript", dialogAnswerScriptList);
		return ResultUtil.success(map);
	}

	/**
	 * 批量启用/禁用
	 */
	@Override
	@SuppressWarnings("rawtypes")
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class, readOnly = false)
	public Result modifyFixedDialogByState(FixedDialogVo fixedDialogVo) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(fixedDialogVo.getIds()) || StringUtils.isBlank(fixedDialogVo.getState())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}

		// 当前登录的用户
		// DialogUser dialogUser = (DialogUser)
		// SecurityUtils.getSubject().getSession().getAttribute("dialogUser");
		// String dialogUserId = dialogUser.getUserId();
		String dialogUserId = "1";

		Gson gson = new Gson();
		List<Map<String, Object>> idsList = gson.fromJson(fixedDialogVo.getIds(),
				new TypeToken<ArrayList<Map<String, Object>>>() {
				}.getType());
		// 人机对话idList
		List<String> idList = new ArrayList<>();
		for (Map<String, Object> idsMap : idsList) {
			idList.add(idsMap.get("id").toString());
		}
		// 获取修改前对话数据
		List<DialogMoreAndOne2> updateDataLists = dialogCacheMapper.findDialogById(idList.get(0));
		GsonBuilder gsonBuilder = new GsonBuilder();
		// 注册自定义String的适配器
		gsonBuilder.registerTypeAdapter(String.class, GsonAdatper.STRING);
		Gson gsonUpdate = gsonBuilder.create();
		String updateData = gsonUpdate.toJson(updateDataLists);
		// 批量更新数据
		try {
			dialogDao.batchUpdate(fixedDialogVo.getState(), dialogUserId, idList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		dialogCacheListenerService.ActionDialogCacheEvent(idList.get(0), ActionEnum.UPDATE_DIALOG.getCode(), updateData,
				"", "");
		return ResultUtil.success();
	}

	@Override
	public void deleteDialogManByDialogId(FixedDialogVo fixedDialogVo) {
		dialogManDao.deleteDialogManByDialogId(fixedDialogVo.getCidMIdDt());
	}
}
