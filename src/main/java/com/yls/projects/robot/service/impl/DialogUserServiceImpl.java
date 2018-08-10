package com.yls.projects.robot.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yls.frame.common.Exception.RobotException;
import com.yls.frame.common.enums.ResultEnum;
import com.yls.frame.common.utils.IdGen;
import com.yls.frame.common.utils.ResultUtil;
import com.yls.frame.common.utils.StringUtils;
import com.yls.frame.common.web.Result;
import com.yls.projects.common.Page.PageBaen;
import com.yls.projects.robot.dao.AccountDao;
import com.yls.projects.robot.dao.DialogUserDao;
import com.yls.projects.robot.entity.Account;
import com.yls.projects.robot.entity.DialogUser;
import com.yls.projects.robot.service.DialogUserService;
import com.yls.projects.robot.vo.DialogUserVo;

/**
 * 
 * 用户service实现类
 * 
 * @author 陈华湛
 * @date 2018年4月28日上午10:21:20
 */
@Service("dialogUserService")
@Transactional(readOnly = true)
public class DialogUserServiceImpl implements DialogUserService {

	@Autowired
	private DialogUserDao dialogUserDao;

	@Autowired
	private AccountDao accountDao;

	/**
	 * 根据id查询用户
	 */
	@Override
	public DialogUser getById(String id) {
		return dialogUserDao.getById(id);
	}

	/**
	 * 根据手机号码查询用户，如果找到用户，说明该手机号已经被注册了
	 */
	// @Cacheable(value = "user", key = "#root.targetClass.name")
	@Transactional(value = "robotTransactionManager")
	@Override
	public DialogUser getByTelPhone(DialogUser dialogUser) {
		return dialogUserDao.getByTelPhone(dialogUser);
	}

	/**
	 * 保存手机号注册用户信息
	 */
	@Override
	@Transactional(value = "robotTransactionManager", readOnly = false)
	public Integer saveTelPhoneRegister(DialogUser dialogUser) {
		return dialogUserDao.saveTelPhoneRegister(dialogUser);
	}

	/**
	 * 保存用短信验证码登录的用户信息
	 */
	@Override
	@Transactional(value = "robotTransactionManager", readOnly = false)
	public Integer saveVerifyRegister(DialogUser dialogUser) {

		return dialogUserDao.saveVerifyRegister(dialogUser);
	}

	/**
	 * 用户主页显示接口
	 */
	@Override
	@SuppressWarnings("rawtypes")
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	public Result getUserHomeById(DialogUser dialogUser) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(dialogUser.getUserId())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}

		DialogUserVo entity = dialogUserDao.getByUserId(dialogUser);
		return ResultUtil.success(entity);
	}

	/**
	 * 修改用户信息
	 */
	@Override
	@SuppressWarnings("rawtypes")
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class, readOnly = false)
	public Result updateUserById(DialogUser dialogUser) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(dialogUser.getUserId())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		// 查询用户信息
		DialogUserVo entity = dialogUserDao.getByUserId(dialogUser);
		Account account = new Account();
		account.setId(entity.getIdAc());
		account.setAccountName(dialogUser.getAccountName());
		// 更新数据
		try {
			accountDao.update(account);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}

		DialogUserVo entity2 = dialogUserDao.getByUserId(dialogUser);
		return ResultUtil.success(entity2);
	}

	/**
	 * 修改密码
	 */
	@Override
	@SuppressWarnings("rawtypes")
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class, readOnly = false)
	public Result updateUserPwd(DialogUser dialogUser) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(dialogUser.getUserId()) || StringUtils.isBlank(dialogUser.getPwd())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		// 查询信息
		DialogUserVo entity = dialogUserDao.getByUserId(dialogUser);
		// 加密方式用MD5
		String hashAlgorithmName = "MD5";
		// 明文密码
		Object credentials = dialogUser.getPwd();
		// 加点盐，用账号名作为盐值即可
		Object salt = ByteSource.Util.bytes(entity.getTelphone());
		// 反复加密1024次
		int hashIterations = 2;
		// 加密后的密文
		Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		// 修改的实体
		DialogUser dialogUser2 = new DialogUser();
		dialogUser2.setUserId(dialogUser.getUserId());
		dialogUser2.setPwd(result.toString());
		// 更新数据
		try {
			dialogUserDao.update(dialogUser2);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		return ResultUtil.success(null);
	}

	/**
	 * 对比旧密码
	 */
	@Override
	@SuppressWarnings("rawtypes")
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	public Result getUserPwd(DialogUser dialogUser) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(dialogUser.getUserId()) || StringUtils.isBlank(dialogUser.getPwd())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		// 查询信息
		DialogUserVo entity = dialogUserDao.getByUserId(dialogUser);
		// 加密方式用MD5
		String hashAlgorithmName = "MD5";
		// 明文密码
		Object credentials = dialogUser.getPwd();
		// 加点盐，用账号名作为盐值即可
		Object salt = ByteSource.Util.bytes(entity.getTelphone());
		// 反复加密1024次
		int hashIterations = 2;
		// 加密后的密文
		Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		// 对比
		Map<String, Object> map = new HashMap<>(16);
		// 1.密码相同 2.密码不同
		String isSame = "";
		if (entity.getPwd().equals(result.toString())) {
			// 如果密码相同
			isSame = "1";
		} else {
			isSame = "2";
		}
		map.put("isSame", isSame);
		return ResultUtil.success(map);
	}

	/**
	 * 获取用户列表
	 */
	@Override
	@SuppressWarnings("rawtypes")
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class)
	public Result getUserList(DialogUserVo dialogUserVo) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(dialogUserVo.getUserId()) || dialogUserVo.getSize() == null
				|| dialogUserVo.getPage() == null) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		// 分页参数必须大于等于0
		if (dialogUserVo.getSize() <= 0 || dialogUserVo.getPage() <= 0) {
			throw new RobotException(ResultEnum.PAGING_SIZE_IS_LESSTHAN0_ERROR);
		}

		PageBaen pageBaen = new PageBaen();
		Account account = accountDao.getBySuperUserId(dialogUserVo.getUserId());
		if (account != null) {
			// 获取起始条数
			Integer startIndex = 0;
			if (dialogUserVo.getPage() > 1) {
				startIndex = dialogUserVo.getSize() * (dialogUserVo.getPage() - 1);
			}
			dialogUserVo.setStartIndex(startIndex);
			dialogUserVo.setIdAc(account.getId());
			List<Map<String, Object>> dataList = dialogUserDao.getListByIdAc(dialogUserVo);
			Integer total = dialogUserDao.getCount(dialogUserVo);
			pageBaen.setSize(dialogUserVo.getSize());
			pageBaen.setPage(dialogUserVo.getPage());
			pageBaen.setTotal(total);
			pageBaen.setList(dataList);
		}
		return ResultUtil.success(pageBaen);
	}

	/**
	 * 新增用户
	 */
	@Override
	@SuppressWarnings("rawtypes")
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class, readOnly = false)
	public Result addUser(DialogUser dialogUser) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(dialogUser.getTelphone()) || StringUtils.isBlank(dialogUser.getPwd())
				|| StringUtils.isBlank(dialogUser.getNickName())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}

		DialogUser entity = dialogUserDao.getByTelPhone(dialogUser);
		if (entity != null) {
			throw new RobotException(ResultEnum.USER_TELPHONE_ERROR);
		} else {
			// 当前登录的用户
			// DialogUser dialogUser = (DialogUser)
			// SecurityUtils.getSubject().getSession().getAttribute("dialogUser");
			// String dialogUserId = dialogUser.getUserId();
			String dialogUserId = "541e9fcce9a34ee8ab1abb1207ec963b";

			// 查询账户表
			Account account = accountDao.getBySuperUserId(dialogUserId);
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

			entity = new DialogUser();
			// 用户id
			entity.setUserId(IdGen.uuid());
			// 账号id
			entity.setIdAc(account.getId());
			// 用户昵称
			entity.setNickName(dialogUser.getNickName());
			// 手机
			entity.setTelphone(dialogUser.getTelphone());
			// 密码
			entity.setPwd(result.toString());
			// 注册类型1:qq,2:微信,3新浪微博,4:手机短信
			entity.setRegisterType(DialogUser.REGISTER_TYPE_4);
			// 是否是安全手机. 0:否, 1:是
			entity.setIsSafetyPhone(DialogUser.IS_SAFETY_EMAIL_1);
			// 创建者
			entity.setCreateBy(dialogUserId);
			// 更新者
			entity.setUpdateBy(dialogUserId);
			// 插入数据
			try {
				dialogUserDao.saveTelPhoneRegister(entity);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RobotException(ResultEnum.FAIL);
			}
		}
		return ResultUtil.success();
	}

	/**
	 * 删除用户
	 * 
	 * @param dialogUser
	 * @return
	 */
	@Override
	@SuppressWarnings("rawtypes")
	@Transactional(value = "robotTransactionManager", rollbackFor = Exception.class, readOnly = false)
	public Result delUser(DialogUser dialogUser) {
		// 如果参数为空，则抛出参数不完整异常
		if (StringUtils.isBlank(dialogUser.getUserId())) {
			throw new RobotException(ResultEnum.PARAMETER_INCOMPLETE_ERROR);
		}
		// 如果该用户是超级用户，则不能删除
		Account account = accountDao.getBySuperUserId(dialogUser.getUserId());
		if (account != null) {
			throw new RobotException(ResultEnum.USER_FAIL);
		} else {
			try {
				dialogUserDao.delete(dialogUser);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RobotException(ResultEnum.FAIL);
			}
		}
		return ResultUtil.success();
	}

}
