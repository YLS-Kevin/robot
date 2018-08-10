package com.yls.projects.common.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.yls.frame.common.Exception.RobotException;
import com.yls.frame.common.enums.ResultEnum;
import com.yls.projects.robot.dao.DialogCacheEventDao;
import com.yls.projects.robot.entity.DialogCacheEventWithBLOBs;

/**
 * 自定义对话缓存事件监听发布service
 *
 * @author Alex
 * @create 2018年6月10日
 */
@Service("dialogCacheListenerService")
public class DialogCacheListenerService  {

	/**
	 * 上下文对象
	 */
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private DialogCacheEventDao dialogCacheEventDao;
	
	public void publish(String action, String msg, long eventId){
		//通过上下文对象发布监听
		applicationContext.publishEvent(new DialogCacheEvent(this, msg, action, eventId, "", "", ""));
	}
	
	public void ActionDialogCacheEvent(String dialogId, String action, String updateData, String cid, String secretKey) {
		// 插入缓存事件记录表,保持事务一致性
		DialogCacheEventWithBLOBs entity = new DialogCacheEventWithBLOBs();
		try {
			entity.setAction(action);
			entity.setCid(cid);
			entity.setSecretKey(secretKey);
			entity.setUpdateBeforeData(updateData);
			entity.setUpdateData(dialogId);
			entity.setCreateBy("1");
			entity.setUpdateBy("1");
			dialogCacheEventDao.insert(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		applicationContext.publishEvent(new DialogCacheEvent(this, updateData, action, entity.getId(), dialogId, cid, secretKey));
	}
}
