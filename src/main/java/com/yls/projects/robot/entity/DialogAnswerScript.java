package com.yls.projects.robot.entity;

import java.util.Date;

public class DialogAnswerScript {
    /** id。UUID */
    private String id;

    /** 账户id。UUID */
    private String idAc;

    /** 人机对话id */
    private String idD;

    /** 对话类别。1-多轮对话，2-固定应答，3-接口应答。 */
    private Integer atype;

    /** 脚本类型。 1-无，2-接口中返回，3-自定义返回。 当为接口应答时，该字段才有意义 */
    private Integer stype;

    /** 返回参数。接口中返回的其中一个参数名。当为接口应答时，该字段才有意义 */
    private String repara;

    /** 包含或等于。 1-包含，2-不包含，3-等于，4-不等于。当为接口应答时，该字段才有意义 */
    private Short sin;

    /** 包含关键词。多个用 | 分开。当为接口应答时，该字段才有意义 */
    private String sinword;

    /** 返回脚本 */
    private String scripts;

    /** 状态。1-启用。2-禁用。 */
    private Integer state;

    /** 排序 */
    private Integer sort;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    private String createDate;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    private String updateDate;
    
    /** 对话类别。1-多轮对话，2-固定应答，3-接口应答。 */
    public static String ATYPE_1 = "1";
    
    /** 对话类别。1-多轮对话，2-固定应答，3-接口应答。 */
    public static String ATYPE_2 = "2";
    
    /** 对话类别。1-多轮对话，2-固定应答，3-接口应答。 */
    public static String ATYPE_3 = "3";
    
    /** 脚本类型。 1-无，2-接口中返回，3-自定义返回。 当为接口应答时，该字段才有意义 */
    public static String STYPE_1 = "1";
    
    /** 脚本类型。 1-无，2-接口中返回，3-自定义返回。 当为接口应答时，该字段才有意义 */
    public static String STYPE_2 = "2";
    
    /** 脚本类型。 1-无，2-接口中返回，3-自定义返回。 当为接口应答时，该字段才有意义 */
    public static String STYPE_3 = "3";
    
    /** 包含或等于。 1-包含，2-不包含，3-等于，4-不等于。当为接口应答时，该字段才有意义 */
    public static String SIN_1 = "1";
    /** 包含或等于。 1-包含，2-不包含，3-等于，4-不等于。当为接口应答时，该字段才有意义 */
    public static String SIN_2 = "2";
    /** 包含或等于。 1-包含，2-不包含，3-等于，4-不等于。当为接口应答时，该字段才有意义 */
    public static String SIN_3 = "3";
    /** 包含或等于。 1-包含，2-不包含，3-等于，4-不等于。当为接口应答时，该字段才有意义 */
    public static String SIN_4 = "4";

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

	public Integer getAtype() {
		return atype;
	}

	public void setAtype(Integer atype) {
		this.atype = atype;
	}

	public Integer getStype() {
		return stype;
	}

	public void setStype(Integer stype) {
		this.stype = stype;
	}

	public String getRepara() {
		return repara;
	}

	public void setRepara(String repara) {
		this.repara = repara;
	}

	public Short getSin() {
		return sin;
	}

	public void setSin(Short sin) {
		this.sin = sin;
	}

	public String getSinword() {
		return sinword;
	}

	public void setSinword(String sinword) {
		this.sinword = sinword;
	}

	public String getScripts() {
		return scripts;
	}

	public void setScripts(String scripts) {
		this.scripts = scripts;
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