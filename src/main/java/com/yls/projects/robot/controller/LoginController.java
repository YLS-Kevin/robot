  package com.yls.projects.robot.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yls.frame.common.Exception.RobotException;
import com.yls.frame.common.enums.ResultEnum;
import com.yls.frame.common.utils.IdGen;
import com.yls.frame.common.utils.ResultUtil;
import com.yls.frame.common.web.Result;
import com.yls.projects.common.constant.Constant;
import com.yls.projects.common.controller.BaseController;
import com.yls.projects.robot.entity.Account;
import com.yls.projects.robot.entity.DialogUser;
import com.yls.projects.robot.entity.SMSVerify;
import com.yls.projects.robot.service.AccountService;
import com.yls.projects.robot.service.DialogUserService;
import com.yls.projects.robot.service.SMSService;
import com.yls.projects.robot.service.SMSVerifyService;
import com.yls.projects.robot.utils.DateUtil;

/**
 * 
 * 用户登录Controller
 * 
 * @author 陈华湛
 * @date 2018年4月28日上午10:04:52
 */
@Controller
public class LoginController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private DialogUserService dialogUserService;

	@Autowired
	private SMSVerifyService sMSVerifyService;

	@Autowired
	private SMSService sMSService;

	@Autowired
	private AccountService accountService;

	/**
	 * SDK AppID是短信应用的唯一标识，调用短信API接口时需要提供该参数。
	 */
	@Value("${appid}")
	private String appid;

	/**
	 * App Key是用来校验短信发送请求合法性的密码，与SDK AppID对应，需要业务方高度保密，切勿把密码存储在客户端。
	 */
	@Value("${appkey}")
	private String appkey;

	/**
	 * 短信签名
	 */
	@Value("${smsSign}")
	private String smsSign;

	/**
	 * 登录验证码模板id
	 */
	@Value("${login.templateId}")
	private String templateId;

	/**
	 * 返回登录主页
	 * 
	 * @return
	 */
	@RequestMapping("/loginPage")
	public ModelAndView loginPage() {
		ModelAndView modelAndView = new ModelAndView("/login");
		return modelAndView;
	}

	/**
	 * 返回验证码登录主页
	 * 
	 * @return
	 */
	@RequestMapping("/verifyLoginPage")
	public ModelAndView verifyLoginPage() {
		ModelAndView modelAndView = new ModelAndView("/verifyLogin");
		return modelAndView;
	}

	/**
	 * 用户登录（账号密码登录）
	 * 
	 * @param dialogUser
	 * @return
	 */
	@RequestMapping("/loginByPhone")
	@ResponseBody
	public Result login(DialogUser dialogUser) {
		
		Map<String, Object> returnMap = new HashMap<>(16);
		Result result = new Result();

		Subject currentUser = SecurityUtils.getSubject();
		// if (!currentUser.isAuthenticated()) {
		// 把用户名和密码封装为 UsernamePasswordToken 对象
		UsernamePasswordToken token = new UsernamePasswordToken(dialogUser.getTelphone().trim(), dialogUser.getPwd().trim());
		// 记住我
		// token.setRememberMe(true);
		try {
			// 执行登录.
			currentUser.login(token);
			// 返回结果：0-登录成功，1-用户名或密码不正确！,2-已经是登录过了
			//returnMap.put("ret", "0");
			//returnMap.put("info", "登录成功");
			DialogUser entity = dialogUserService.getByTelPhone(dialogUser);
			returnMap.put("token", currentUser.getSession().getId());
			returnMap.put("userid", entity.getUserId());
			returnMap.put("nickName", entity.getNickName());
			returnMap.put("idAc", entity.getIdAc());
			SecurityUtils.getSubject().getSession().setAttribute("dialogUser", entity);
			result =  ResultUtil.success(returnMap);

		}
		// ... catch more exceptions here (maybe custom ones specific to your
		// application?
		// 所有认证时异常的父类.
		catch (AuthenticationException ae) {
			ae.printStackTrace();
			// unexpected condition? error?
			// 返回结果：0-登录成功，1-用户名或密码不正确！,2-已经是登录过了
			throw new RobotException(ResultEnum.LOGIN_FAIL);
		}
		/*
		 * }else{ //返回结果：0-登录成功，1-用户名或密码不正确！,2-已经是登录过了 returnMap.put("ret",
		 * "2"); returnMap.put("info", "已经是登录过了"); }
		 */
		return result;
	}

	/**
	 * 用户登录（短信验证码直接登录）
	 * 
	 * @param dialogUser
	 * @return Map
	 */
	@RequestMapping("/loginBySms")
	@ResponseBody
	public Map<String, Object> verifyLogining(DialogUser dialogUser) {

		Map<String, Object> returnMap = new HashMap<>(16);

		Subject currentUser = SecurityUtils.getSubject();
		// if (!currentUser.isAuthenticated()) {
		SMSVerify sMSVerify = new SMSVerify();
		sMSVerify.setTelphone(dialogUser.getTelphone().trim());
		SMSVerify returnSMSVerify = sMSVerifyService.getByTelPhone(sMSVerify);
		// 数据库中保存的验证码
		String code = returnSMSVerify.getCode();
		// 数据库中验证码的生成时间
		String createDate = returnSMSVerify.getCreateDate();
		long createTime = DateUtil.formatString(createDate, "yyyy-MM-dd HH:mm:ss").getTime();
		long nowTime = System.currentTimeMillis();
		long second = nowTime - createTime;

		// 判断前台传过来的验证码是否和数据库中的验证码一致，是否已经过期
		if (!code.equals(dialogUser.getCode())) {
			// 返回结果：0-登录成功,1-账号异常,2-已经是登录过了,3-验证码错误,4-验证码过期
			returnMap.put("ret", "3");
			returnMap.put("info", "验证码错误");
		} else if (second > Constant.VERIFY_CODE_OVERTIME) {
			// 返回结果：0-登录成功,1-账号异常,2-已经是登录过了,3-验证码错误,4-验证码过期
			returnMap.put("ret", "4");
			returnMap.put("info", "验证码过期");
		}

		// 根据手机号码查用户表看该手机号码是否已经注册，如果没注册则默认注册并登录成功，如果用户表中已经有该手机号码的记录则登录成功
		// 根据手机号码查询用户，如果找到用户，说明该手机号已经被注册了
		DialogUser entity = dialogUserService.getByTelPhone(dialogUser);
		if (entity != null) {
			// 已经注册过了

			// 把用户名和密码封装为 UsernamePasswordToken 对象
			UsernamePasswordToken token = new UsernamePasswordToken(dialogUser.getTelphone(), dialogUser.getCode());
			// 记住我
			// token.setRememberMe(true);
			try {
				// 执行登录.
				currentUser.login(token);
				// 返回结果：0-登录成功,1-账号异常,2-已经是登录过了,3-验证码错误,4-验证码过期
				returnMap.put("ret", "0");
				returnMap.put("info", "登录成功");
				SecurityUtils.getSubject().getSession().setAttribute("dialogUser", entity);

			}
			// ... catch more exceptions here (maybe custom ones specific to
			// your application?
			// 所有认证时异常的父类.
			catch (AuthenticationException ae) {
				// unexpected condition? error?
				// System.out.println("登录失败: " + ae.getMessage());
				// 返回结果：0-登录成功,1-账号异常,2-已经是登录过了,3-验证码错误,4-验证码过期
				returnMap.put("ret", "1");
				returnMap.put("info", "账号异常");
			}

		} else {
			// 还没注册

			// ======在用户表生成一个用户==========
			// 用户密码，因直接用户短信验证码登录所以给个随机密码
			dialogUser.setPwd(IdGen.uuid());
			// 用户id uuid
			String userId = IdGen.uuid();
			dialogUser.setUserId(userId);
			// 账号id
			String idAc = IdGen.uuid();
			dialogUser.setIdAc(idAc);
			// 注册类型1:qq,2:微信,3新浪微博,4:手机短信
			dialogUser.setRegisterType("4");
			// 是否是安全手机. 0:否, 1:是
			dialogUser.setIsSafetyPhone("1");
			// 创建者
			dialogUser.setCreateBy(userId);
			// 更新者
			dialogUser.setUpdateBy(userId);
			// 保存用短信验证码登录的用户信息
			dialogUserService.saveVerifyRegister(dialogUser);

			// ========在账号表生成一条记录========
			Account account = new Account();
			// 账户主键id
			String accountId = idAc;
			account.setId(accountId);
			// 超级用户id
			account.setSuperUserId(userId);
			// 会员等级id,默认为普通免费会员
			String vipId = Constant.FREE_VIP_ID;
			account.setVipId(vipId);
			// 会员过期时间，免费会员：永不过期
			String vipExpireTime = Constant.FREE_VIP_EXPIRETIME;
			account.setVipExpireTime(vipExpireTime);
			// 创建者
			account.setCreateBy(userId);
			// 更新者
			account.setUpdateBy(userId);
			// 保存注册账户信息
			accountService.saveAccount(account);

			// 把用户名和密码封装为 UsernamePasswordToken 对象
			UsernamePasswordToken token = new UsernamePasswordToken(dialogUser.getTelphone(), dialogUser.getCode());
			// 记住我
			// token.setRememberMe(true);
			try {
				// 执行登录.
				currentUser.login(token);
				// 返回结果：0-登录成功,1-账号异常,2-已经是登录过了,3-验证码错误,4-验证码过期
				returnMap.put("ret", "0");
				returnMap.put("token", currentUser.getSession().getId());
				returnMap.put("userid", entity.getUserId());
				returnMap.put("nickName", entity.getNickName());
				returnMap.put("idAc", entity.getIdAc());
				returnMap.put("info", "登录成功");
				SecurityUtils.getSubject().getSession().setAttribute("dialogUser", dialogUser);

			}
			// ... catch more exceptions here (maybe custom ones specific to
			// your application?
			// 所有认证时异常的父类.
			catch (AuthenticationException ae) {
				// unexpected condition? error?
				// System.out.println("登录失败: " + ae.getMessage());
				// 返回结果：0-登录成功,1-账号异常,2-已经是登录过了,3-验证码错误,4-验证码过期
				returnMap.put("ret", "1");
				returnMap.put("info", "账号异常");
			}
		}

		/*
		 * }else{ //返回结果：0-登录成功,1-账号异常,2-已经是登录过了,3-验证码错误,4-验证码过期
		 * returnMap.put("ret", "2"); returnMap.put("info", "已经是登录过了"); }
		 */

		return returnMap;
	}

	/**
	 * 发送登录验证码
	 * 
	 * @param telphone
	 * @return
	 */
	@RequestMapping("/sendLoginCode")
	@ResponseBody
	public Map<String, Object> sendRegisterCode(@RequestParam("telphone") String telphone,
			@RequestParam(value = "nationcode", required = false) String nationcode) {

		Map<String, Object> map = new HashMap<>(16);

		Map<String, Object> returnMap = new HashMap<>(16);

		Map<String, Object> resultMap = new HashMap<>(16);

		// 国家码
		map.put("nationcode", nationcode == null ? "86" : nationcode);

		// 短信应用SDK AppID
		map.put("appid", appid);

		// 短信应用SDK AppKey
		map.put("appkey", appkey);

		// 需要发送短信的手机号码
		map.put("telphone", telphone);

		// 短信模板ID，需要在短信应用中申请
		map.put("templateId", templateId);

		// 签名 NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`
		map.put("smsSign", smsSign);

		// 验证码
		int code = (int) ((Math.random() * 9 + 1) * 1000);
		map.put("code", code);

		// 把验证码保存到数据库
		SMSVerify sMSVerify = new SMSVerify();
		sMSVerify.setTelphone(telphone);
		sMSVerify.setCode(code + "");
		Integer integer = sMSVerifyService.save(sMSVerify);

		// 当验证码信息保存成功进数据库后，发送验证码给用户
		if (integer > 0) {
			// 发送验证码
			resultMap = sMSService.sendVerifyCode(map);
		}

		if (resultMap != null
				&& Constant.SMS_JSON_RESULT_VALUE.equals(resultMap.get(Constant.SMS_JSON_RESULT_KEY).toString())) {
			// 返回结果 0-发送成功 ，1-发送失败
			returnMap.put("ret", "0");
			returnMap.put("info", "发送成功");
		} else {
			// 返回结果 0-发送成功，1-发送失败
			returnMap.put("ret", "1");
			returnMap.put("info", "发送失败");
		}
		return returnMap;
	}

}
