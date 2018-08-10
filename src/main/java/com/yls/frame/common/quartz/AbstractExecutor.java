package com.yls.frame.common.quartz;

import org.apache.log4j.Logger;


/**
 * 抽象任务调度类，配置通用的日志输出
 * @author lijieran
 *
 */
public abstract  class AbstractExecutor implements Executor{
	
	protected String taskname;
	
	private static Logger logger = Logger.getLogger(AbstractExecutor.class);
	
	public AbstractExecutor() {
		this.setTaskname();
	}
	
	
	public void execute() {
		logger.info("执行"+this.getTaskname()+"任务====");
		long  startTime =System.currentTimeMillis();
		
		this.hanldeExecute();
		
		logger.info("执行"+this.getTaskname()+"任务====结束"+ ((System.currentTimeMillis() - startTime) * 0.001) + "s ");
	}
	
	


	@Override
	public void fill(String[] params) {
		logger.info("执行"+this.getTaskname()+"补数据任务====");
		
		long  startTime =System.currentTimeMillis();
		
		if(params!=null) {
			for(String param:params) {
				logger.info("参数为：" + param);
			}
		}
		
		this.hanldeFill(params);
		
		logger.info("执行"+this.getTaskname()+"补数据任务====结束"+ ((System.currentTimeMillis() - startTime) * 0.001) + "s ");
		
	}
	

	public abstract void hanldeExecute();
	public abstract void hanldeFill(String params[]);
	public abstract void setTaskname();


	public String getTaskname() {
		return taskname;
	}
	
	

}
