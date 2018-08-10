package com.yls.projects.dialog.entity;

/**
 * @Author:Suchy
 * @Description:人机对话库实体
 * @Date: 11:10 2018/5/4
 */
public class DialogTypes {
    /**
     * 主键用户id
     */
    private String id;
    /**
     * 账户id
     */
    private String idAc;
    
    /**
     * 情景模块id
     */
    private String cidM;

    /**
     * 类别名
     */
    private String atname;

    /**
     * 被引用数
     */
    private Integer beQuoted;

    /**
     * 是否共享.0-默认不共享, 1-共享
     */
    private String isShare;

    /**
     * 状态。1-启用。2-禁用
     */
    private String state;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 修改者
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private String updateDate;

    /**
     * 删除标记。0-正常，1-已删除
     */
    private String delFlag;


    /**
     * 字段常量
     */
    public final  static String ID="id";
    public final  static String ID_AC="idAC";

    /**
     * 是否共享.0-不共享
     */
    public final  static String IS_SHARE_0="0";
    /**
     * 是否共享.1-共享
     */
    public final  static String IS_SHARE_1="1";
    /**
     * 状态  1-启用
     */
    public final  static String STATE_1="1";
    /**
     * 状态  2-禁用
     */
    public final  static String STATE_2="2";
    /**
     * 删除标记 0-正常
     */
    public final  static String  DEL_FLAG_0="0";
    /**
     * 删除标记 1-已删除
     */
    public final  static String  DEL_FLAG_1="1";

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
    
    public String getCidM() {
		return cidM;
	}

	public void setCidM(String cidM) {
		this.cidM = cidM;
	}

	public String getAtname() {
        return atname;
    }

    public void setAtname(String atname) {
        this.atname = atname;
    }

    public Integer getBeQuoted() {
        return beQuoted;
    }

    public void setBeQuoted(Integer beQuoted) {
        this.beQuoted = beQuoted;
    }

    public String getIsShare() {
        return isShare;
    }

    public void setIsShare(String isShare) {
        this.isShare = isShare;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
