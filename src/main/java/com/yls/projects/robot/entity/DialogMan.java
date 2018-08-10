package com.yls.projects.robot.entity;

/**
 * 
 * 对话人说实体类
 * 
 * @author 陈华湛
 * @date 2018年5月5日下午4:42:04
 */
public class DialogMan {

	/**
	 * 主键id
	 */
	private String id;

	/**
	 * 账户id
	 */
	private String idAc;

	/**
	 * 人机对话id
	 */
	private String idD;

	/**
	 * 匹配类型。1-模糊匹配，2-关键词匹配
	 */
	private String aptype;

	/**
	 * 人说的话
	 */
	private String aword;

	/**
	 * 关键词个数。最多5个。指一定得有才能形成这个问题的关键词
	 */
	private Integer awordnum;

	/**
	 * 关键词1类型。1-固定，2-变化。
	 */
	private Integer aword1type;

	/**
	 * 关键词1。如果为变化型，写入时用[地名]。不用默认了，因为默认可定义近义问题解决
	 */
	private String aword1;

	/**
	 * 关键词1同义词。多个用 | 分开
	 */
	private String aword1near;

	/**
	 * 关键词1动态类型
	 */
	private String aword1dyna;

	/**
	 * 关键词1url接口参数。当对话的类型为：接口时需用到
	 */
	private String aword1para;

	/**
	 * 关键词2类型。1-固定，2-变化。
	 */
	private Integer aword2type;

	/**
	 * 关键词1。如果为变化型，写入时用[地名]。不用默认了，因为默认可定义近义问题解决
	 */
	private String aword2;

	/**
	 * 关键词2同义词。多个用 | 分开
	 */
	private String aword2near;

	/**
	 * 关键词2动态类型
	 */
	private String aword2dyna;

	/**
	 * 关键词1url接口参数。当对话的类型为：接口时需用到
	 */
	private String aword2para;

	/**
	 * 关键词3类型。1-固定，2-变化。
	 */
	private Integer aword3type;

	/**
	 * 关键词3。如果为变化型，写入时用[地名]。不用默认了，因为默认可定义近义问题解决
	 */
	private String aword3;

	/**
	 * 关键词3同义词。多个用 | 分开
	 */
	private String aword3near;

	/**
	 * 关键词3动态类型
	 */
	private String aword3dyna;

	/**
	 * 关键词3url接口参数。当对话的类型为：接口时需用到
	 */
	private String aword3para;

	/**
	 * 关键词4类型。1-固定，2-变化。
	 */
	private Integer aword4type;

	/**
	 * 关键词4。如果为变化型，写入时用[地名]。不用默认了，因为默认可定义近义问题解决
	 */
	private String aword4;

	/**
	 * 关键词4同义词。多个用 | 分开
	 */
	private String aword4near;

	/**
	 * 关键词4动态类型
	 */
	private String aword4dyna;

	/**
	 * 关键词4url接口参数。当对话的类型为：接口时需用到
	 */
	private String aword4para;

	/**
	 * 关键词5类型。1-固定，2-变化。
	 */
	private Integer aword5type;

	/**
	 * 关键词5。如果为变化型，写入时用[地名]。不用默认了，因为默认可定义近义问题解决
	 */
	private String aword5;

	/**
	 * 关键词5url接口参数。当对话的类型为：接口时需用到
	 */
	private String aword5near;

	/**
	 * 关键词5动态类型
	 */
	private String aword5dyna;

	/**
	 * 关键词5url接口参数。当对话的类型为：接口时需用到
	 */
	private String aword5para;

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
	 * 匹配类型。1-模糊匹配，2-关键词匹配
	 */
	public static final String APTYPE_1 = "1";

	/**
	 * 匹配类型。1-模糊匹配，2-关键词匹配
	 */
	public static final String APTYPE_2 = "2";

	/**
	 * 关键词1类型。1-固定，2-变化。
	 */
	public static final String AWORD1_TYPE_1 = "1";

	/**
	 * 关键词1类型。1-固定，2-变化。
	 */
	public static final String AWORD1_TYPE_2 = "2";

	/**
	 * 关键词2类型。1-固定，2-变化。
	 */
	public static final String AWORD2_TYPE_1 = "1";

	/**
	 * 关键词2类型。1-固定，2-变化。
	 */
	public static final String AWORD2_TYPE_2 = "2";

	/**
	 * 关键词3类型。1-固定，2-变化。
	 */
	public static final String AWORD3_TYPE_1 = "1";

	/**
	 * 关键词3类型。1-固定，2-变化。
	 */
	public static final String AWORD3_TYPE_2 = "2";

	/**
	 * 关键词4类型。1-固定，2-变化。
	 */
	public static final String AWORD4_TYPE_1 = "1";

	/**
	 * 关键词4类型。1-固定，2-变化。
	 */
	public static final String AWORD4_TYPE_2 = "2";

	/**
	 * 关键词5类型。1-固定，2-变化。
	 */
	public static final String AWORD5_TYPE_1 = "1";

	/**
	 * 关键词5类型。1-固定，2-变化。
	 */
	public static final String AWORD5_TYPE_2 = "2";

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

	public String getAptype() {
		return aptype;
	}

	public void setAptype(String aptype) {
		this.aptype = aptype;
	}

	public String getAword() {
		return aword;
	}

	public void setAword(String aword) {
		this.aword = aword;
	}

	public String getAword1() {
		return aword1;
	}

	public void setAword1(String aword1) {
		this.aword1 = aword1;
	}

	public String getAword1near() {
		return aword1near;
	}

	public void setAword1near(String aword1near) {
		this.aword1near = aword1near;
	}

	public String getAword1dyna() {
		return aword1dyna;
	}

	public void setAword1dyna(String aword1dyna) {
		this.aword1dyna = aword1dyna;
	}

	public String getAword1para() {
		return aword1para;
	}

	public void setAword1para(String aword1para) {
		this.aword1para = aword1para;
	}

	public String getAword2() {
		return aword2;
	}

	public void setAword2(String aword2) {
		this.aword2 = aword2;
	}

	public String getAword2near() {
		return aword2near;
	}

	public void setAword2near(String aword2near) {
		this.aword2near = aword2near;
	}

	public String getAword2dyna() {
		return aword2dyna;
	}

	public void setAword2dyna(String aword2dyna) {
		this.aword2dyna = aword2dyna;
	}

	public String getAword2para() {
		return aword2para;
	}

	public void setAword2para(String aword2para) {
		this.aword2para = aword2para;
	}

	public String getAword3() {
		return aword3;
	}

	public void setAword3(String aword3) {
		this.aword3 = aword3;
	}

	public String getAword3near() {
		return aword3near;
	}

	public void setAword3near(String aword3near) {
		this.aword3near = aword3near;
	}

	public String getAword3dyna() {
		return aword3dyna;
	}

	public void setAword3dyna(String aword3dyna) {
		this.aword3dyna = aword3dyna;
	}

	public String getAword3para() {
		return aword3para;
	}

	public void setAword3para(String aword3para) {
		this.aword3para = aword3para;
	}

	public String getAword4() {
		return aword4;
	}

	public void setAword4(String aword4) {
		this.aword4 = aword4;
	}

	public String getAword4near() {
		return aword4near;
	}

	public void setAword4near(String aword4near) {
		this.aword4near = aword4near;
	}

	public String getAword4dyna() {
		return aword4dyna;
	}

	public void setAword4dyna(String aword4dyna) {
		this.aword4dyna = aword4dyna;
	}

	public String getAword4para() {
		return aword4para;
	}

	public void setAword4para(String aword4para) {
		this.aword4para = aword4para;
	}

	public String getAword5() {
		return aword5;
	}

	public void setAword5(String aword5) {
		this.aword5 = aword5;
	}

	public String getAword5near() {
		return aword5near;
	}

	public void setAword5near(String aword5near) {
		this.aword5near = aword5near;
	}

	public String getAword5dyna() {
		return aword5dyna;
	}

	public void setAword5dyna(String aword5dyna) {
		this.aword5dyna = aword5dyna;
	}

	public String getAword5para() {
		return aword5para;
	}

	public void setAword5para(String aword5para) {
		this.aword5para = aword5para;
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

	public Integer getAwordnum() {
		return awordnum;
	}

	public void setAwordnum(Integer awordnum) {
		this.awordnum = awordnum;
	}

	public Integer getAword1type() {
		return aword1type;
	}

	public void setAword1type(Integer aword1type) {
		this.aword1type = aword1type;
	}

	public Integer getAword2type() {
		return aword2type;
	}

	public void setAword2type(Integer aword2type) {
		this.aword2type = aword2type;
	}

	public Integer getAword3type() {
		return aword3type;
	}

	public void setAword3type(Integer aword3type) {
		this.aword3type = aword3type;
	}

	public Integer getAword4type() {
		return aword4type;
	}

	public void setAword4type(Integer aword4type) {
		this.aword4type = aword4type;
	}

	public Integer getAword5type() {
		return aword5type;
	}

	public void setAword5type(Integer aword5type) {
		this.aword5type = aword5type;
	}

	@Override
	public String toString() {
		return "DialogMan [id=" + id + ", idAc=" + idAc + ", idD=" + idD + ", aptype=" + aptype + ", aword=" + aword
				+ ", awordnum=" + awordnum + ", aword1type=" + aword1type + ", aword1=" + aword1 + ", aword1near="
				+ aword1near + ", aword1dyna=" + aword1dyna + ", aword1para=" + aword1para + ", aword2type="
				+ aword2type + ", aword2=" + aword2 + ", aword2near=" + aword2near + ", aword2dyna=" + aword2dyna
				+ ", aword2para=" + aword2para + ", aword3type=" + aword3type + ", aword3=" + aword3 + ", aword3near="
				+ aword3near + ", aword3dyna=" + aword3dyna + ", aword3para=" + aword3para + ", aword4type="
				+ aword4type + ", aword4=" + aword4 + ", aword4near=" + aword4near + ", aword4dyna=" + aword4dyna
				+ ", aword4para=" + aword4para + ", aword5type=" + aword5type + ", aword5=" + aword5 + ", aword5near="
				+ aword5near + ", aword5dyna=" + aword5dyna + ", aword5para=" + aword5para + ", sort=" + sort
				+ ", createBy=" + createBy + ", createDate=" + createDate + ", updateBy=" + updateBy + ", updateDate="
				+ updateDate + "]";
	}

}
