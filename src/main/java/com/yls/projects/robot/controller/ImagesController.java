package com.yls.projects.robot.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yls.frame.common.enums.ResultEnum;
import com.yls.frame.common.utils.ResultUtil;
import com.yls.frame.common.web.Result;
import com.yls.projects.common.controller.BaseController;

/**
 * 图片上传 Controller
 * 
 * @author 陈俊
 * @date 2018年6月5日
 */
@RestController
public class ImagesController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ImagesController.class);

	@Value("${file.imagePath}")
	private String uploadDir;

	@Value("${file.url}")
	private String uploadUrl;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public Result uploadImage(@RequestParam(value = "file") MultipartFile file) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			if (!file.isEmpty()) {
				// 获取文件名
				String fileName = file.getOriginalFilename();
				// 获取文件的后缀名
				String suffixName = fileName.substring(fileName.lastIndexOf("."));
				// 获取当前日期
				Date date = new Date();
				String dateFile = sdf.format(date) + "/";
				// 如果目录未创建, 就创建目录
				File pathFile = new File(uploadDir + dateFile);
				if (!pathFile.exists()) {
					pathFile.mkdirs();
				}
				// 解决中文问题，liunx下中文路径，图片显示问题
				fileName = UUID.randomUUID() + suffixName;
				// 拼接文件路径
				String filePath = uploadDir + dateFile + fileName;
				file.transferTo(new File(filePath));
				// 判断文件是否上传成功
				File existFile = new File(filePath);
				// 检测是否存在目录
				if (existFile.exists()) {
					return ResultUtil.success(uploadUrl + dateFile + fileName);
				} else {
					return ResultUtil.error(ResultEnum.FILE_ERROR.getCode(), ResultEnum.FILE_ERROR.getInfo());
				}
			}
		} catch (IllegalStateException e) {
			return ResultUtil.error(ResultEnum.FILE_ERROR.getCode(), ResultEnum.FILE_ERROR.getInfo());
		}
		return ResultUtil.success();
	}

}
