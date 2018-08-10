package com.yls.projects.robot.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RobotsAndDialogTypes {
    /** id。UUID */
    private String id;

    /** 机器人id。UUID */
    private String cid;

    /** 机器人模块id。UUID。 通用模块时，该字段为空 */
    private String cidM;

    /** 类型。1-通用模块，2-自定义模块 */
    private Short ctype;

    /** 人机对话库id。UUID */
    private String idDt;

    /** 排序 */
    private Integer sort;

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
    private Date updateDate;
    
    /** 类型。1-通用模块，2-自定义模块 */
    private static Short CTYPE_1 = 1;
    
    /** 类型。1-通用模块，2-自定义模块 */
    private static Short CTYPE_2 = 2;
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }

    public String getCidM() {
        return cidM;
    }

    public void setCidM(String cidM) {
        this.cidM = cidM == null ? null : cidM.trim();
    }

    public Short getCtype() {
        return ctype;
    }

    public void setCtype(Short ctype) {
        this.ctype = ctype;
    }

    public String getIdDt() {
        return idDt;
    }

    public void setIdDt(String idDt) {
        this.idDt = idDt == null ? null : idDt.trim();
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