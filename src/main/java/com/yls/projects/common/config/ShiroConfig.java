package com.yls.projects.common.config;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.yls.projects.common.config.filter.ShiroPermsFilter;
import com.yls.projects.common.config.filter.ShiroRolesFilter;
import com.yls.projects.common.config.filter.ShiroExpiredFilter;
import com.yls.projects.common.config.filter.ShiroLogoutFilter;
import com.yls.projects.robot.realm.ShiroRealm;
import com.yls.projects.robot.realm.VerifyCodeRealm;

/**
 * Shiro配置类
 * 
 * @author chz
 *
 */
@Configuration
public class ShiroConfig {

	

	/**
	 * ShiroFilterFactoryBean 处理拦截资源文件问题。
	 * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
	 * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
	 *
	 * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
	 * 3、部分过滤器可指定参数，如perms，roles
	 *
	 */
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		// shiroFilterFactoryBean.setLoginUrl("/login.html");
		shiroFilterFactoryBean.setLoginUrl("/loginPage");
		shiroFilterFactoryBean.setUnauthorizedUrl("/loginPage");

		Map<String, Filter> filterMap = new LinkedHashMap<String, Filter>();
		filterMap.put("authc", shiroExpiredFilter());
		//filterMap.put("authc", new ShiroLoginFilter());
		filterMap.put("perms", shiroPermsFilter());
		filterMap.put("roles", shiroRolesFilter());
		filterMap.put("logout", shiroLogoutFilter());
		shiroFilterFactoryBean.setFilters(filterMap);

		// 拦截器.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/layui/**", "anon");
		filterChainDefinitionMap.put("/js/*", "anon");
		filterChainDefinitionMap.put("/css/*", "anon");
		filterChainDefinitionMap.put("/images/*", "anon");

		filterChainDefinitionMap.put("/fogetPwd", "anon");
		filterChainDefinitionMap.put("/register", "anon");
		filterChainDefinitionMap.put("/loginByPhone", "anon");
		filterChainDefinitionMap.put("/loginBySms", "anon");
		filterChainDefinitionMap.put("/registerPage", "anon");
		filterChainDefinitionMap.put("/sendRegisterCode", "anon");
		filterChainDefinitionMap.put("/loginPage", "anon");
		filterChainDefinitionMap.put("/verifyLoginPage", "anon");
		filterChainDefinitionMap.put("/sendLoginCode", "anon");
		//filterChainDefinitionMap.put("/upload", "anon");
		filterChainDefinitionMap.put("/girls/getAge/**", "authc,perms[user:select,user:update]");

		// 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");

		// <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		// <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		filterChainDefinitionMap.put("/**", "authc"); // ("/**", "authc,perms[user:add]")

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean(name = "securityManager")
	public SecurityManager securityManager(@Qualifier("shiroRealm") ShiroRealm shiroRealm,
			@Qualifier("verifyCodeRealm") VerifyCodeRealm verifyCodeRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		// securityManager.setRealm(shiroRealm);
		securityManager.setAuthenticator(modularRealmAuthenticator());
		List<Realm> realms = new ArrayList<>();
		realms.add(shiroRealm);
		realms.add(verifyCodeRealm);
		securityManager.setRealms(realms);
		// 自定义session管理
		// securityManager.setSessionManager(sessionManager());
		return securityManager;
	}

	// 自定义sessionManager
	// @Bean
	// public SessionManager sessionManager() {
	// MyShrioSessionManager myShrioSessionManager = new
	// MyShrioSessionManager();
	// return myShrioSessionManager;
	// }

	/**
	 * 系统自带的Realm管理，主要针对多realm
	 */
	@Bean
	public ModularRealmAuthenticator modularRealmAuthenticator() {
		ModularRealmAuthenticator modularRealmAuthenticator = new ModularRealmAuthenticator();
		modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
		return modularRealmAuthenticator;
	}

	/**
	 * 密码匹配凭证管理器
	 * 
	 * @return
	 */
	@Bean(name = "hashedCredentialsMatcher")
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		// log.info("hashedCredentialsMatcher()");
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

		hashedCredentialsMatcher.setHashAlgorithmName("MD5");// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(2);// 散列的次数，比如散列两次，相当于
														// md5(md5(""));

		return hashedCredentialsMatcher;
	}

	/**
	 * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
	 * 
	 * @return
	 */
	@Bean
	public ShiroRealm shiroRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
		ShiroRealm shiroRealm = new ShiroRealm();
		shiroRealm.setCredentialsMatcher(matcher);
		return shiroRealm;
	}

	@Bean
	public VerifyCodeRealm verifyCodeRealm() {
		VerifyCodeRealm verifyCodeRealm = new VerifyCodeRealm();
		return verifyCodeRealm;
	}

	/**
	 * Shiro生命周期处理器
	 * 
	 * @return
	 */
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),
	 * 需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
	 * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)
	 * 和AuthorizationAttributeSourceAdvisor)即可实现此功能
	 * 
	 * @return
	 */
	@Bean
	@DependsOn({ "lifecycleBeanPostProcessor" })
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			@Qualifier("securityManager") SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	 @Bean(name = "shiroExpiredFilter")
	 public ShiroExpiredFilter shiroExpiredFilter(){
	 ShiroExpiredFilter shiroExpiredFilter = new ShiroExpiredFilter();
	 return shiroExpiredFilter;
	 }
	 
	 @Bean(name = "shiroPermsFilter")
	 public ShiroPermsFilter shiroPermsFilter(){
	 ShiroPermsFilter shiroPermsFilter = new ShiroPermsFilter();
	 return shiroPermsFilter;
	 }
	 
	 @Bean(name = "shiroRolesFilter")
	 public ShiroRolesFilter shiroRolesFilter(){
		 ShiroRolesFilter shiroRolesFilter = new ShiroRolesFilter();
	 return shiroRolesFilter;
	 }
	 
	 @Bean(name = "shiroLogoutFilter")
	 public ShiroLogoutFilter shiroLogoutFilter(){
		 ShiroLogoutFilter shiroLogoutFilter = new ShiroLogoutFilter();
	 return shiroLogoutFilter;
	 }

	
}
