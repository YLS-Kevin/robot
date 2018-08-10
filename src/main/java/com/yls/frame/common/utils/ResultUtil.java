package com.yls.frame.common.utils;

import com.yls.frame.common.web.Result;

/**
 * 返回结果工具类
 * 
 * @author Alex Lee 李璐
 * @date 2018年5月3日下午5:23:00
 */
public class ResultUtil {

	public static Result success(Object object) {
        Result result = new Result();
        result.setRet("0");
        result.setInfo("成功");
        result.setReturnData(object);
        return result;
    }
	
	public static Result success() {
        return success(null);
    }

    public static Result error(String code, String info) {
        Result result = new Result();
        result.setRet(code);
        result.setInfo(info);
        return result;
    }
}
