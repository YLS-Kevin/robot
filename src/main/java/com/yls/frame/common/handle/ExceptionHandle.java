package com.yls.frame.common.handle;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yls.frame.common.Exception.RobotException;
import com.yls.frame.common.utils.ResultUtil;
import com.yls.frame.common.web.Result;

/**
 * 异常处理类
 * @author Alex Lee 李璐
 * @date 2018年5月3日下午4:44:09
 */
@ControllerAdvice
public class ExceptionHandle {

	private static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
	
	public static final String DEFAULT_ERROR_VIEW = "error";
	
	@ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(HttpServletRequest req,Exception e) {
        if (e instanceof RobotException) {
        	RobotException baseException = (RobotException) e;
        	logger.error("【URL】{}",req.getRequestURL());
            return ResultUtil.error(baseException.getCode(), baseException.getMessage());
        }else {
        	logger.error("【URL】{}",req.getRequestURL());
            logger.error("【系统异常】{}", e);
            return ResultUtil.error("-1", "未知错误");
        }
    }
	
	/*@ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }*/
}
