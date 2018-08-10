package com.yls.projects.oauth.daodb;

import com.yls.projects.oauth.entity.OauthClientDetails;

/**
 * 终端细节表实体类
 * 
 * @author 陈俊
 * @date 2018年5月21日
 */
public interface OauthClientDetailsDao {

	/**
	 * 添加数据
	 * 
	 * @param oauthClientDetails
	 */
	void insert(OauthClientDetails oauthClientDetails);

	/**
	 * 更新数据
	 * 
	 * @param oauthClientDetails
	 */
	void update(OauthClientDetails oauthClientDetails);

}
