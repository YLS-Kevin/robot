package com.yls.projects.robot.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yls.projects.robot.vo.BaseEntity;

/**
 * 机器人终端使用日志表 实体类
 * 
 * @author 陈俊
 * @date 2018年6月9日
 */
public class RobotClientUseLog extends BaseEntity {

	/** id。UUID */
	private String id;

	/** 机器人终端id */
	private String idCu;

	/** 终端IP */
	private String cip;

	/** 经度 */
	private String lon;

	/** 纬度 */
	private String lat;

	/** 城市 */
	private String scity;

	/** 地址 */
	private String saddr;

	/** 访问时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date vdate;

	/** 人说 */
	private String mansay;

	/** 机器说 */
	private String robotsay;

	/** 分词结果 */
	private String participle;

	/** 机器人名称 */
	private String cname;

	/** 访问开始时间 */
	private String bDate;

	/** 访问结束时间 */
	private String eDate;
	
	private String isfind;

	public String getbDate() {
		return bDate;
	}

	public void setbDate(String bDate) {
		this.bDate = bDate;
	}

	public String geteDate() {
		return eDate;
	}

	public void seteDate(String eDate) {
		this.eDate = eDate;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getIdCu() {
		return idCu;
	}

	public void setIdCu(String idCu) {
		this.idCu = idCu == null ? null : idCu.trim();
	}

	public String getCip() {
		return cip;
	}

	public void setCip(String cip) {
		this.cip = cip == null ? null : cip.trim();
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getScity() {
		return scity;
	}

	public void setScity(String scity) {
		this.scity = scity == null ? null : scity.trim();
	}

	public String getSaddr() {
		return saddr;
	}

	public void setSaddr(String saddr) {
		this.saddr = saddr == null ? null : saddr.trim();
	}

	public Date getVdate() {
		return vdate;
	}

	public void setVdate(Date vdate) {
		this.vdate = vdate;
	}

	public String getMansay() {
		return mansay;
	}

	public void setMansay(String mansay) {
		this.mansay = mansay == null ? null : mansay.trim();
	}

	public String getRobotsay() {
		return robotsay;
	}

	public void setRobotsay(String robotsay) {
		this.robotsay = robotsay == null ? null : robotsay.trim();
	}

	public String getParticiple() {
		return participle;
	}

	public void setParticiple(String participle) {
		this.participle = participle == null ? null : participle.trim();
	}

	public String getIsfind() {
		return isfind;
	}

	public void setIsfind(String isfind) {
		this.isfind = isfind;
	}
}