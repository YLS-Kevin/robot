package com.yls.projects.robot.quartz;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yls.frame.common.quartz.AbstractExecutor;
import com.yls.frame.common.utils.StringUtils;
import com.yls.projects.robot.dao.DialogCacheEventDao;
import com.yls.projects.robot.entity.DialogCacheEventWithBLOBs;
import com.yls.projects.robot.utils.CacheUtil;

@Service
public class CacheExecutorJob extends AbstractExecutor {

	private static Logger logger = Logger.getLogger(CacheExecutorJob.class);

	@Value("${accessToken_url}")
	private String tokenUrl;

	@Value("${redis.url}")
	private String cacheUrl;

	@Autowired
	private DialogCacheEventDao dialogCacheEventDao;

	@Override
	public void hanldeExecute() {
		String cid = "cd49317c121b4a7c981d5d3720508b81";
		String secretKey = "8382d723946c0b789ee32711afb79b3f";
		String token = "";
		List<DialogCacheEventWithBLOBs> list = dialogCacheEventDao.list();
		for (DialogCacheEventWithBLOBs entity : list) {
			if (!StringUtils.isBlank(entity.getCid())) {
				cid = entity.getCid();
			}
			if (!StringUtils.isBlank(entity.getSecretKey())) {
				secretKey = entity.getSecretKey();
			}
			token = CacheUtil.getToken(cid, secretKey, tokenUrl);
			if (!StringUtils.isBlank(token)) {
				String result = CacheUtil.postResult(entity.getAction(), token, this.cacheUrl,
						entity.getUpdateBeforeData(), cid, entity.getUpdateData());
				Map<String, Object> mapResult = JSON.parseObject(result, Map.class);
				String ret = mapResult.get("ret") == null ? "" : mapResult.get("ret").toString();
				if ("1".equals(ret)) {
					// 删除实体
					dialogCacheEventDao.deleteByPrimaryKey(entity.getId());
				} else {
					break;
				}
			}
		}

	}

	@Override
	public void hanldeFill(String[] params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTaskname() {
		taskname = "定期检查-保证数据一致性";

	}

}
