package com.yls.projects.robot.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * 机器人功能模块
 * @author 陈华湛
 * @date 2018年5月23日下午2:51:55
 */
public class RobotsModule {
	
	public static String delFlag_normal = "0";
	public static String delFlag_fail = "1";
	public static String state_active = "1";
	public static String state_disable = "2";
	public static Integer iscommon_yes = 1;
	public static Integer iscommon_no = 2;
	
    /** 机器人模块id。UUID */
    private String cidM;

    /** 机器人id。UUID */
    private String cid;

    /** 通用模块。1-是。2-否。 */
    private Integer iscommon;

    /** 模块名 */
    private String mname;

    /** 触发关键词。说该关键词时，进入或退出该模块。该模块关键词，多个用空格分开。如：打开主页，回到主页，我要去主页 */
    private String dokey;

    /** 状态。1-启用。2-禁用。 */
    private String state;
    
    /** 删除标记。0-正常。1-删除。 */
    private String delFlag;

    /** 排序 */
    private Integer sort;

    /** 功能描述 */
    private String remarks;

    /** 创建者 */
    @JsonIgnore
    private String createBy;

    /** 创建时间 */
    @JsonIgnore
    private Date createDate;

    /** 更新者 */
    @JsonIgnore
    private String updateBy;
    
    /** 对话库id*/
    private String idDts;
    
    /** 对话问答id */
    private String dialogId;

    /** 更新时间 */
    private Date updateDate;
    
    public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getCidM() {
        return cidM;
    }

    public void setCidM(String cidM) {
        this.cidM = cidM == null ? null : cidM.trim();
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }


	public Integer getIscommon() {
		return iscommon;
	}

	public void setIscommon(Integer iscommon) {
		this.iscommon = iscommon;
	}

	public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname == null ? null : mname.trim();
    }

    public String getDokey() {
        return dokey;
    }

    public void setDokey(String dokey) {
        this.dokey = dokey == null ? null : dokey.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
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
        this.remarks = remarks == null ? null : remarks.trim();
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

	public String getIdDts() {
		return idDts;
	}

	public void setIdDts(String idDts) {
		this.idDts = idDts;
	}

	public String getDialogId() {
		return dialogId;
	}

	public void setDialogId(String dialogId) {
		this.dialogId = dialogId;
	}
}