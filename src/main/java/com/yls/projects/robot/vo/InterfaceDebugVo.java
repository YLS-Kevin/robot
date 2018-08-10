package com.yls.projects.robot.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class InterfaceDebugVo {

	/** 秘钥**/
	private String token;
	/** 设备id**/
	private String sbid;
	/** 定位的城市名**/
	@NotNull(message = "scity字段必传")
    @NotBlank(message = "scity字段必传")
	private String scity;
	/** 定位的具体地址**/
	@NotNull(message = "saddr字段必传")
    @NotBlank(message = "saddr字段必传")
	private String saddr;
	/** 经度**/
	@NotNull(message = "lon字段必传")
    @NotBlank(message = "lon字段必传")
	private String lon;
	/** 纬度**/
	@NotNull(message = "lat字段必传")
    @NotBlank(message = "lat字段必传")
	private String lat;
	/** 操作系统版本**/
	@NotNull(message = "os字段必传")
    @NotBlank(message = "os字段必传")
	private String os;
	/** 人说的话**/
	@NotNull(message = "info字段必传")
    @NotBlank(message = "info字段必传")
	private String info;
	/** 账户id**/
	@NotNull(message = "acid字段必传")
    @NotBlank(message = "acid字段必传")
	private String acid;
	/** 定位的城市名**/
	@NotNull(message = "region字段必传")
    @NotBlank(message = "region字段必传")
	private String region;
	/** 机器人模块id**/
	@NotNull(message = "cid_m字段必传")
    @NotBlank(message = "cid_m字段必传")
	private String cid_m;
	/** 机器人id**/
	@NotNull(message = "cid字段必传")
    @NotBlank(message = "cid字段必传")
	private String cid;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSbid() {
		return sbid;
	}
	public void setSbid(String sbid) {
		this.sbid = sbid;
	}
	public String getScity() {
		return scity;
	}
	public void setScity(String scity) {
		this.scity = scity;
	}
	public String getSaddr() {
		return saddr;
	}
	public void setSaddr(String saddr) {
		this.saddr = saddr;
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
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getAcid() {
		return acid;
	}
	public void setAcid(String acid) {
		this.acid = acid;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCid_m() {
		return cid_m;
	}
	public void setCid_m(String cid_m) {
		this.cid_m = cid_m;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
					
						
				
				
				
				
			
			
			
			
			
			
}
