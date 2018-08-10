package com.yls.projects.robot.entity;

import java.util.Date;

/**
 * 
 * 异常应答表
 * @author 陈华湛
 * @date 2018年5月23日下午3:56:37
 */
public class DialogAnswerExp {
    /** id。UUID */
    private String id;

    /** 种类。1-全局默认，2-账户默认，3-终端 */
    private Short skid;

    /** 账户id。UUID */
    private String idAc;

    /** 机器人id。UUID */
    private String cid;

    /** 类型。1-无答案时，2-接口异常时，3-系统出错时 */
    private Short stype;

    /** 机器应答内容 */
    private String answer;

    /** 状态。1-启用。2-禁用。 */
    private Short state;

    /** 排序 */
    private Integer sort;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    private Date createDate;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    private Date updateDate;
    
    //无答案
    public static String STYPE_1 = "1"; 
    //接口异常
    public static String STYPE_2 = "2"; 
    //系统出错
    public static String STYPE_3 = "3";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Short getSkid() {
        return skid;
    }

    public void setSkid(Short skid) {
        this.skid = skid;
    }

    public String getIdAc() {
        return idAc;
    }

    public void setIdAc(String idAc) {
        this.idAc = idAc == null ? null : idAc.trim();
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }

    public Short getStype() {
        return stype;
    }

    public void setStype(Short stype) {
        this.stype = stype;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
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