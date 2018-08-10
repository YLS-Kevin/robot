package com.yls.projects.common.cache;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yls.frame.common.utils.StringUtils;
import com.yls.projects.robot.dao.DialogCacheEventDao;
import com.yls.projects.robot.utils.CacheUtil;

/**
 * 自定义对话缓存事件监听器
 *
 * @author Alex
 * @create 2018年6月10日
 */
@Component
public class DialogCacheListener implements ApplicationListener<DialogCacheEvent> {

	private static Logger logger = LoggerFactory.getLogger(DialogCacheListener.class);

	@Autowired
	private DialogCacheEventDao dialogCacheEventDao;

	@Value("${accessToken_url}")
	private String tokenUrl;

	@Value("${redis.url}")
	private String cacheUrl;

	/**
	 * 对监听到的事件进行处理
	 * 
	 * @param DialogCacheEvent
	 */
	@Async("taskCacheExecutor")
	@Override
	public void onApplicationEvent(DialogCacheEvent dialogCacheEvent) {
		// 这里进行逻辑处理
		// spring boot 自定义异步事件
		// 直接触发调用接口
		String result = null; 
		String cid = "cd49317c121b4a7c981d5d3720508b81";
		String secretKey = "8382d723946c0b789ee32711afb79b3f";
		if (!StringUtils.isBlank(dialogCacheEvent.getCid())) {
			cid = dialogCacheEvent.getCid();
		}
		if (!StringUtils.isBlank(dialogCacheEvent.getSecretKey())) {
			secretKey = dialogCacheEvent.getSecretKey();
		}

		String token = CacheUtil.getToken(cid, secretKey, tokenUrl);
		if (!StringUtils.isBlank(token)) {
			result = CacheUtil.postResult(dialogCacheEvent.getAction(), token, this.cacheUrl, dialogCacheEvent.getMsg(),
					cid, dialogCacheEvent.getDialogId());
			logger.info(result);
			Map<String, Object> mapResult = JSON.parseObject(result, Map.class);
			String ret = mapResult.get("ret") == null ? "" : mapResult.get("ret").toString();
			if ("1".equals(ret)) {
				// 删除实体
				dialogCacheEventDao.deleteByPrimaryKey(dialogCacheEvent.getEventId());
			}
		}
	}
}
