package com.yls.projects.robot.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yls.projects.robot.vo.BaseEntity;

public class InterData extends BaseEntity{
    /** id。UUID */
    private String id;

    /** 账户id。UUID */
    private String idAc;

    /** url */
    //@JsonProperty("aaa")
    @NotNull(message = "url字段必传")
    @NotBlank(message = "url字段必传")
    private String url;

    /** urltest。测试url */
    private String urltest;

    /** 接口说明 */
    @NotNull(message = "explains字段必传")
    @NotBlank(message = "explains字段必传")
    private String explains;

    /** 接口调用方式。get，post */
    @NotNull(message = "icall字段必传")
    @NotBlank(message = "icall字段必传")
    private String icall;

    /** 接口参数名 */
    private String paramName;

    /** 接口参数值 */
    private String paramValue;

    /** 是否启用, 0启用,1禁用 */
    private String state;

    /** url参数编码。UTF-8(默认) */
    private String paracode;
    
    /** 是否删除,  0表示启用,1表示禁用 */
    private String delFlag;

    /** 接口需要登录。1-需要，2-不需要。 */
    @JsonIgnore
    private Short needlogin;

    /** 登录key */
    @JsonIgnore
    private String loginkey;

    /** 登录密钥 */
    @JsonIgnore
    private String loginsecret;

    /** 创建者 */
    @JsonIgnore
    private String createBy;

    /** 创建时间 */
    @JsonIgnore
    private Date createDate;

    /** 更新者 */
    @JsonIgnore
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateDate;
    
    /**
	 * 状态。1-启用。0-禁用。
	 */
	public static final String STATE_1 = "1";
	
	/**
	 * 状态。0表示启用,1表示禁用。
	 */
	public static final String delFlag_1 = "0";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getIdAc() {
        return idAc;
    }

    public void setIdAc(String idAc) {
        this.idAc = idAc == null ? null : idAc.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getUrltest() {
        return urltest;
    }

    public void setUrltest(String urltest) {
        this.urltest = urltest == null ? null : urltest.trim();
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains == null ? null : explains.trim();
    }

    public String getIcall() {
        return icall;
    }

    public void setIcall(String icall) {
        this.icall = icall == null ? null : icall.trim();
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName == null ? null : paramName.trim();
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue == null ? null : paramValue.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getParacode() {
        return paracode;
    }

    public void setParacode(String paracode) {
        this.paracode = paracode == null ? null : paracode.trim();
    }

    public Short getNeedlogin() {
        return needlogin;
    }

    public void setNeedlogin(Short needlogin) {
        this.needlogin = needlogin;
    }

    public String getLoginkey() {
        return loginkey;
    }

    public void setLoginkey(String loginkey) {
        this.loginkey = loginkey == null ? null : loginkey.trim();
    }

    public String getLoginsecret() {
        return loginsecret;
    }

    public void setLoginsecret(String loginsecret) {
        this.loginsecret = loginsecret == null ? null : loginsecret.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}