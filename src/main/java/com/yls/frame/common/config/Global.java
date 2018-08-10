package com.yls.frame.common.config;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;

import com.google.common.collect.Maps;
import com.yls.frame.common.utils.PropertiesLoader;
import com.yls.frame.common.utils.StringUtils;

/**
 * 全局配置类
 */
@Configuration
public class Global {

	/**
	 * 环境。 开发、测试、生产等
	 */
	@Value("${spring.profiles.active}")
	private String sysenv;

	/**
	 * 当前对象实例
	 */
	private static Global global = new Global();

	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();

	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader();

	private static Logger logger = Logger.getLogger(Global.class);

	/**
	 * 获取当前对象实例
	 */
	public static Global getInstance() {
		return global;
	}

	/**
	 * 获取配置
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null) {
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}

	/**
	 * 获取工程路径
	 * 
	 * @return
	 */
	public static String getProjectPath() {
		// 如果配置了工程路径，则直接返回，否则自动获取。
		String projectPath = Global.getConfig("projectPath");
		if (StringUtils.isNotBlank(projectPath)) {
			return projectPath;
		}
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null) {
				while (true) {
					File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
					if (f == null || f.exists()) {
						break;
					}
					if (file.getParentFile() != null) {
						file = file.getParentFile();
					} else {
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {
			logger.error(e, e);
		}
		return projectPath;
	}

	public static String getSysenv() {
		return global.sysenv;
	}

	public static void setSysenv(String sysenv) {
		global.sysenv = sysenv;
		// loader = new PropertiesLoader();
		// log = new LogInitLoader();
	}

	public static void init(String sysenv) {
		setSysenv(sysenv);
		loader = new PropertiesLoader();
		// log = new LogInitLoader();
		logger.info("系统初始化");
	}

}
