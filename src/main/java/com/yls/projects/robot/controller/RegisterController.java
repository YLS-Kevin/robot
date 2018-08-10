package com.yls.projects.robot.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
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
 * 用户注册Controller
 * 
 * @author 陈华湛
 * @date 2018年4月28日上午10:04:52
 */
@Controller
public class RegisterController extends BaseController {

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
	 * 注册验证码模板id
	 */
	@Value("${register.templateId}")
	private String templateId;

	/**
	 * 返回注册主页
	 * 
	 * @return
	 */
	@RequestMapping("/registerPage")
	public ModelAndView registerPage() {
		ModelAndView modelAndView = new ModelAndView("/register");
		return modelAndView;
	}

	/**
	 * 发送注册验证码
	 * 
	 * @param telphone
	 * @return
	 */
	@RequestMapping("/sendRegisterCode")
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
			// 返回结果 0-发送成功 ，1-发送失败
			returnMap.put("ret", "1");
			returnMap.put("info", "发送失败");
		}
		return returnMap;
	}

	/**
	 * 保存手机号注册用户信息
	 * 
	 * @return
	 */
	@RequestMapping("/register")
	@ResponseBody
	public Map<String, Object> saveTelPhoneRegister(DialogUser dialogUser) {

		Map<String, Object> map = new HashMap<>(16);

		Map<String, Object> returnData = new HashMap<>(16);

		SMSVerify sMSVerify = new SMSVerify();
		sMSVerify.setTelphone(dialogUser.getTelphone().trim());
		SMSVerify returnSMSVerify = sMSVerifyService.getByTelPhone(sMSVerify);
		String code = returnSMSVerify.getCode();
		String createDate = returnSMSVerify.getCreateDate();
		long createTime = DateUtil.formatString(createDate, "yyyy-MM-dd HH:mm:ss").getTime();
		long nowTime = System.currentTimeMillis();
		long second = nowTime - createTime;

		if (!code.equals(dialogUser.getCode())) {
			// 返回结果：0-注册成功，1-注册失败，2-手机号已经被人注册,3-验证码错误,4-验证码过期
			map.put("ret", "3");
			map.put("info", "验证码错误");
			map.put("returnData", null);
			return map;

		} else if (second > Constant.VERIFY_CODE_OVERTIME) {
			// 返回结果：0-注册成功，1-注册失败，2-手机号已经被人注册,3-验证码错误,4-验证码过期
			map.put("ret", "4");
			map.put("info", "验证码过期");
			map.put("returnData", null);
			return map;
		}

		// 根据手机号码查询用户，如果找到用户，说明该手机号已经被注册了
		DialogUser entity = dialogUserService.getByTelPhone(dialogUser);
		if (entity != null) {
			// 返回结果：0-注册成功，1-注册失败，2-手机号已经被人注册,3-验证码错误,4-验证码过期
			map.put("ret", "2");
			map.put("info", "手机号已经被人注册");
			map.put("returnData", null);
		} else {

			// 加密方式用MD5
			String hashAlgorithmName = "MD5";
			// 明文密码
			Object credentials = dialogUser.getPwd();
			// 加点盐，用账号名作为盐值即可
			Object salt = ByteSource.Util.bytes(dialogUser.getTelphone());
			// 反复加密1024次
			int hashIterations = 2;
			// 加密后的密文
			Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);

			dialogUser.setPwd(result.toString());

			// 用户id uuid
			String userId = IdGen.uuid();
			dialogUser.setUserId(userId);

			// 账号id
			String idAc = IdGen.uuid();
			dialogUser.setIdAc(idAc);
			dialogUser.setNickName(dialogUser.getTelphone());
			// 注册类型1:qq,2:微信,3新浪微博,4:手机短信
			dialogUser.setRegisterType("4");
			// 是否是安全手机. 0:否, 1:是
			dialogUser.setIsSafetyPhone("1");
			// 创建者
			dialogUser.setCreateBy(userId);
			// 更新者
			dialogUser.setUpdateBy(userId);
			// 保存手机号注册用户信息
			Integer integer = dialogUserService.saveTelPhoneRegister(dialogUser);

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

			// 如果注册成功则跳转到登录页面，否则跳转到注册页面
			if (integer > 0) {
				returnData.put("userId", userId);
				// 返回结果：0-注册成功，1-注册失败，2-手机号已经被人注册,3-验证码错误,4-验证码过期
				map.put("ret", "0");
				map.put("info", "注册成功");
				map.put("returnData", returnData);
			} else {
				// 返回结果：0-注册成功，1-注册失败，2-手机号已经被人注册,3-验证码错误,4-验证码过期
				map.put("result", "1");
				map.put("info", "注册失败");
				map.put("returnData", null);
			}
		}

		return map;
	}

	/**
	 * 忘记密码
	 * 
	 * @return
	 */
	@RequestMapping("/fogetPwd")
	@ResponseBody
	public Result fogetPwd(DialogUser dialogUser) {
		SMSVerify sMSVerify = new SMSVerify();
		sMSVerify.setTelphone(dialogUser.getTelphone().trim());
		SMSVerify returnSMSVerify = sMSVerifyService.getByTelPhone(sMSVerify);
		String code = returnSMSVerify.getCode();
		String createDate = returnSMSVerify.getCreateDate();
		long createTime = DateUtil.formatString(createDate, "yyyy-MM-dd HH:mm:ss").getTime();
		long nowTime = System.currentTimeMillis();
		long second = nowTime - createTime;
		// 返回结果：0-修改密码成功，1-修改密码失败，2-手机号已经被人注册,3-验证码错误,4-验证码过期
		if (!code.equals(dialogUser.getCode())) {
			throw new RobotException(ResultEnum.VALID_FAIL);
		} else if (second > Constant.VERIFY_CODE_OVERTIME) {
			throw new RobotException(ResultEnum.VALID_EXPIRED);
		}
		// 根据手机号码查询用户，如果找到用户，说明该手机号已经被注册了
		DialogUser entity = dialogUserService.getByTelPhone(dialogUser);
		if (entity != null) {
			entity.setPwd(dialogUser.getPwd());
			return dialogUserService.updateUserPwd(entity);
		} else {
			throw new RobotException(ResultEnum.USER_NOT_FIND);
		}
	}
}
