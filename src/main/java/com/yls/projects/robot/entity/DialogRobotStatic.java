package com.yls.projects.robot.entity;

/**
 * 
 * 机器固定应答实体类
 * @author 陈华湛
 * @date 2018年5月5日下午5:52:12
 */
public class DialogRobotStatic {
	
	/**
	 * id。UUID
	 */
	private String id;
	
	/**
	 * 账户id。UUID
	 */
	private String idAc;
	
	/**
	 * 人机对话id
	 */
	private String idD;
	
	/**
	 * 机器应答内容
	 */
	private String answer;
	
	/**
	 * 状态。1-启用。2-禁用。
	 */
	private Integer state;
	
	/**
	 * 排序
	 */
	private Integer sort;
	
	/**
	 * 创建者
	 */
	private String createBy;
	
	/**
	 * 创建时间
	 */
	private String createDate;
	
	/**
	 * 更新者
	 */
	private String updateBy;
	
	/**
	 * 更新时间
	 */
	private String updateDate;
	
	
	/**
	 * 状态。1-启用。2-禁用。
	 */
	public static final String STATE_1 = "1";
	
	/**
	 * 状态。1-启用。2-禁用。
	 */
	public static final String STATE_2 = "2";
	
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdAc() {
		return idAc;
	}

	public void setIdAc(String idAc) {
		this.idAc = idAc;
	}

	public String getIdD() {
		return idD;
	}

	public void setIdD(String idD) {
		this.idD = idD;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
	
	

}
