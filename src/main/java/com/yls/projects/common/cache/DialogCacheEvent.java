package com.yls.projects.common.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;

/**
 * 自定义对话缓存事件
 *
 * @author Alex
 * @create 2018年6月10日
 */
public class DialogCacheEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(DialogCacheEvent.class);
	
	private String msg;
	
	private String action;
	
	private long eventId;
	
	private String dialogId;
	
	private String cid;
	
	private String secretKey;

	/**
     * 在自定义事件的构造方法中除了第一个source参数，其他参数都可以去自定义，
     * 可以根据项目实际情况进行监听传参
     * @param source
     * @param msg
     */
	public DialogCacheEvent(Object source, String msg, String action, long eventId, String dialogId, String cid, String secretKey) {
		super(source);
		this.msg = msg;
		this.action = action;
		this.dialogId = dialogId;
		this.eventId = eventId;
		this.cid = cid;
		this.secretKey = secretKey;
	}
	
	/**
     * 自定义监听器触发的透传打印方法
     * @param msg
     */
    public void printMsg(String msg)
    {
    	logger.debug("对话缓存事件监听:{}", msg);
    }

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public String getDialogId() {
		return dialogId;
	}

	public void setDialogId(String dialogId) {
		this.dialogId = dialogId;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
}
