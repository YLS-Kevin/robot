package com.yls.projects.robot.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.yls.projects.robot.entity.SMSVerify;
import com.yls.projects.robot.service.DialogUserService;
import com.yls.projects.robot.service.SMSVerifyService;

/**
 * 自定义ShiroRealm
 * 
 * @author chz
 *
 */
public class VerifyCodeRealm extends AuthorizingRealm {

	@Autowired
	@Lazy
	private DialogUserService dialogUserService;

	@Autowired
	@Lazy
	private SMSVerifyService sMSVerifyService;

	/**
	 * 授权
	 * 
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
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
		// System.out.println(" VerifyCodeRealm __从数据库中获取 username: " + username
		// + " 所对应的用户信息.");

		SMSVerify sMSVerify = new SMSVerify();
		sMSVerify.setTelphone(username);
		SMSVerify entity = sMSVerifyService.getByTelPhone(sMSVerify);

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
		Object credentials = entity.getCode();

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, "");
		// info = new SimpleAuthenticationInfo(principal, credentials,
		// credentialsSalt, realmName);
		return info;
	}

}
