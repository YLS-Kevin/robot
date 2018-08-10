package com.yls.projects.robot.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.yls.frame.common.utils.StringUtils;
import com.yls.projects.robot.entity.DialogResource;
import com.yls.projects.robot.entity.DialogUser;
import com.yls.projects.robot.service.DialogResourceService;
import com.yls.projects.robot.service.DialogUserService;

/**
 * 自定义ShiroRealm
 * 
 * @author chz
 *
 */
public class ShiroRealm extends AuthorizingRealm {

	private static Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

	@Autowired
	@Lazy
	private DialogUserService dialogUserService;

	@Autowired
	@Lazy
	private DialogResourceService dialogResourceService;

	/**
	 * 授权
	 * 
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		DialogUser dialogUser = new DialogUser();
		dialogUser.setTelphone((String) principals.getPrimaryPrincipal());
		DialogUser user = dialogUserService.getByTelPhone(dialogUser);
		if (user != null) {
			logger.info("读取权限资源开始--控制台中假如没打印sql,说明从redis中读取");
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			List<DialogResource> list = dialogResourceService.getByUserId(user.getUserId());
			for (DialogResource menu : list) {
				if (StringUtils.isNotBlank(menu.getPermission())) {
					// 添加基于Permission的权限信息
					for (String permission : StringUtils.split(menu.getPermission(), ",")) {
						info.addStringPermission(permission);
					}
				}
			}
			logger.info("读取权限资源结束--控制台中假如没打印sql,说明从redis中读取");
			return info;
		} else {
			return null;
		}

	}

	/**
	 * 权限认证
	 * 
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		// 1. 把 AuthenticationToken 转换为 UsernamePasswordToken
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;

		// 2. 从 UsernamePasswordToken 中来获取 username
		String username = upToken.getUsername();

		// 3. 调用数据库的方法, 从数据库中查询 username 对应的用户记录
		//System.out.println("shiroRealm-->>从数据库中获取 username: " + username + " 所对应的用户信息.");

		DialogUser dialogUser = new DialogUser();
		dialogUser.setTelphone(username);
		DialogUser entity = dialogUserService.getByTelPhone(dialogUser);

		//System.out.println("entity:" + entity);

		// 4. 若用户不存在, 则可以抛出 UnknownAccountException 异常
		if ("unknown".equals(username)) {
			throw new UnknownAccountException("用户不存在!");
		}

		// 5. 根据用户信息的情况, 决定是否需要抛出其他的 AuthenticationException 异常.
		if ("monster".equals(username)) {
			throw new LockedAccountException("用户被锁定");
		}

		// 6. 根据用户的情况, 来构建 AuthenticationInfo 对象并返回. 通常使用的实现类为:
		// SimpleAuthenticationInfo
		// 以下信息是从数据库中获取的.
		// 1). principal: 认证的实体信息. 可以是 username, 也可以是数据表对应的用户的实体类对象.
		Object principal = username;
		// 2). credentials: 密码.
		Object credentials = entity.getPwd();

		// 3). realmName: 当前 realm 对象的 name. 调用父类的 getName() 方法即可
		String realmName = getName();
		// 4). 盐值.
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);

		SimpleAuthenticationInfo info = null; // new
												// SimpleAuthenticationInfo(principal,
												// credentials, realmName);
		info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);

		return info;
	}

}
