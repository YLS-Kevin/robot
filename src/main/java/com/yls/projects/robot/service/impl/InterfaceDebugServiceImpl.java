package com.yls.projects.robot.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.yls.frame.common.Exception.RobotException;
import com.yls.frame.common.enums.ResultEnum;
import com.yls.frame.common.utils.ResultUtil;
import com.yls.frame.common.web.Result;
import com.yls.projects.robot.dao.RobotsDao;
import com.yls.projects.robot.entity.Robots;
import com.yls.projects.robot.service.InterfaceDebugService;
import com.yls.projects.robot.utils.HttpPostUtil;
import com.yls.projects.robot.vo.InterfaceDebugVo;
import com.yls.projects.robot.vo.RobotsVo;

@Service("InterfaceDebugService")
@Transactional(value = "robotTransactionManager", readOnly = true, rollbackFor = Exception.class)
public class InterfaceDebugServiceImpl implements InterfaceDebugService{
	
	private static Logger logger = LoggerFactory.getLogger(InterfaceDebugServiceImpl.class);
	
	@Autowired
	private RobotsDao robotsDao;
	
	@Value("${url}")
	private String dialogUrl;
	
	@Value("${accessToken_url}")
	private String accessTokenUrl;
	
	@Value("${baidu_ak}")
	private String baidu_ak;
	

	@Override
	public Result InterfaceDebug(HttpServletRequest request, InterfaceDebugVo interfaceDebugVo) {
		
		String osAndBrowserInfo = getOsAndBrowserInfo(request);
		
		String[] osAndBrowserInfoArr = osAndBrowserInfo.split("---");
		//操作系统
		String os = osAndBrowserInfoArr[0];
		//浏览器
		String browser = osAndBrowserInfoArr[1];
		
		interfaceDebugVo.setOs(os);
		
		
		//获取用户ip地址
		String userIp = request.getRemoteAddr();
		//根据ip地址解析地理位置
//		String url = "http://api.map.baidu.com/location/ip?ip="+userIp+"&ak=65UHGwvfRgecD6uBlkfYrMsEQNVhgSgy&coor=bd09ll";
		String url = "http://api.map.baidu.com/location/ip?ip="+userIp+"&ak="+baidu_ak+"&coor=bd09ll";
		String result = "";
		try {
			
			result = HttpPostUtil.doPost(request,url,new HashMap<>());
			//logger.info(result);
			Gson gson = new Gson();
			
			//{address=CN|广东|深圳|None|CHINANET|0|0, content={address=广东省深圳市, address_detail={city=深圳市, city_code=340.0, district=, province=广东省, street=, street_number=}, point={x=114.02597366, y=22.54605355}}, status=0.0}
			Map<String, Object> gsonMap = JSON.parseObject(result, Map.class);
			//System.out.println(gsonMap.get("status").toString());
			if("0".equals(gsonMap.get("status").toString())){
				
				Map<String, Object> content = JSON.parseObject(gsonMap.get("content").toString(), Map.class);
				Map<String, Object> addressDetail = JSON.parseObject(content.get("address_detail").toString(), Map.class);
				Map<String, Object> point = JSON.parseObject(content.get("point").toString(), Map.class);
				
				interfaceDebugVo.setScity(addressDetail.get("city").toString());
				interfaceDebugVo.setRegion(addressDetail.get("city").toString());
				interfaceDebugVo.setSaddr(content.get("address").toString());
				interfaceDebugVo.setLon(point.get("x").toString());
				interfaceDebugVo.setLat(point.get("y").toString());
				
				//logger.info(gsonMap.get("content").toString());
				//logger.info("address_detail :" + content.get("address_detail").toString());
//				logger.info(addressDetail.get("city").toString());
//				logger.info(content.get("address").toString());
//				logger.info(point.get("x").toString());
//				logger.info(point.get("y").toString());
				
			}else{
				interfaceDebugVo.setScity("深圳市");
				interfaceDebugVo.setRegion("深圳市");
				interfaceDebugVo.setSaddr("广东省深圳市");
				interfaceDebugVo.setLon("113.9629412");
				interfaceDebugVo.setLat("22.4627142");
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		
		
		
		
		//获取token
		String accessToken = this.getAccessToken(request,interfaceDebugVo);
		//System.out.println("accessToken"+accessToken);
		
	    Map<String, Object> map = new HashMap<>();
	    map.put("token", accessToken);
	    //map.put("sbid", interfaceDebugVo.getSbid());
	    map.put("scity", interfaceDebugVo.getScity());
	    map.put("saddr", interfaceDebugVo.getSaddr());
	    map.put("lon", interfaceDebugVo.getLon());
	    map.put("lat", interfaceDebugVo.getLat());
	    map.put("os", interfaceDebugVo.getOs());
	    map.put("info", interfaceDebugVo.getInfo());
	    map.put("acid", interfaceDebugVo.getAcid());
	    map.put("region", interfaceDebugVo.getRegion());
	    map.put("cid_m", interfaceDebugVo.getCid_m());
	    map.put("cid", interfaceDebugVo.getCid());
	      
		String content;
		try {
			content = HttpPostUtil.doPost(request,dialogUrl, map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RobotException(ResultEnum.FAIL);
		}
		
		//System.out.println("content"+content);
		
		/*
		 * 如果返回 ret=10 且code=5000则做特殊处理
		{"ret":"10","returnData":{"result":"小象机器人还找不到该问题的答案,小象机器人正在学习中...","code":"5000","script":{"action":"normal"}},"info":"接口调用成功"}
		 */
		Gson gson = new Gson();
		Map<String, Object> map2 = gson.fromJson(content, Map.class);
		String ret = map2.get("ret").toString();
		Map<String, Object> returnDataMap = (Map<String, Object>) map2.get("returnData");
		
		String codeStr = returnDataMap.get("code")==null?"":returnDataMap.get("code").toString();
		if("10".equals(ret) && "5000".equals(codeStr)){
			returnDataMap.put("result", "小缘机器人还找不到该问题的答案,小缘机器人正在学习中...");
			map2.put("returnData", returnDataMap);
			content = gson.toJson(map2);
		}
		
		if ("2".equals(ret) || "3".equals(ret)) {
			try {
				content = HttpPostUtil.doPost(request,dialogUrl, map);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RobotException(ResultEnum.FAIL);
			}
		}
		
		return ResultUtil.success(content);
		
	}
	
	/**
	 * 获取token
	 * 
	 * @param request
	 * @return
	 */
	public String getAccessToken(HttpServletRequest request,InterfaceDebugVo interfaceDebugVo) {
		
		//根据cid获取密钥
		RobotsVo robotsVo = new RobotsVo();
		robotsVo.setCid(interfaceDebugVo.getCid());
		Robots entityById = robotsDao.getEntityById(robotsVo);

		Map<String, Object> map = new HashMap<>();
		map.put("grant_type", "client_credentials");
		map.put("client_id", interfaceDebugVo.getCid());
		map.put("client_secret", entityById.getSecret());
		map.put("scope", "read write");

		String content = "";
		try {
			content = HttpPostUtil.doPost(request,accessTokenUrl, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Gson gson = new Gson();
		Map<String, Object> mapJson = gson.fromJson(content, Map.class);
		String token = mapJson.get("access_token").toString();
		return token;

	}
	
	/** 
     * 获取操作系统,浏览器及浏览器版本信息 
     * @param request 
     * @return 
     */  
    public static String getOsAndBrowserInfo(HttpServletRequest request){  
        String  browserDetails  =   request.getHeader("User-Agent");  
        String  userAgent       =   browserDetails;  
        String  user            =   userAgent.toLowerCase();  
  
        String os = "";  
        String browser = "";  
  
        //=================OS Info=======================  
        if (userAgent.toLowerCase().indexOf("windows") >= 0 )  
        {  
            os = "Windows";  
        } else if(userAgent.toLowerCase().indexOf("mac") >= 0)  
        {  
            os = "Mac";  
        } else if(userAgent.toLowerCase().indexOf("x11") >= 0)  
        {  
            os = "Unix";  
        } else if(userAgent.toLowerCase().indexOf("android") >= 0)  
        {  
            os = "Android";  
        } else if(userAgent.toLowerCase().indexOf("iphone") >= 0)  
        {  
            os = "IPhone";  
        }else{  
            os = "UnKnown, More-Info: "+userAgent;  
        }  
        //===============Browser===========================  
        if (user.contains("edge"))  
        {  
            browser=(userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-");  
        } else if (user.contains("msie"))  
        {  
            String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];  
            browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];  
        } else if (user.contains("safari") && user.contains("version"))  
        {  
            browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]  
                    + "-" +(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];  
        } else if ( user.contains("opr") || user.contains("opera"))  
        {  
            if(user.contains("opera")){  
                browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]  
                        +"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];  
            }else if(user.contains("opr")){  
                browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))  
                        .replace("OPR", "Opera");  
            }  
  
        } else if (user.contains("chrome"))  
        {  
            browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");  
        } else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)  ||  
                (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) ||  
                (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1) )  
        {  
            browser = "Netscape-?";  
  
        } else if (user.contains("firefox"))  
        {  
            browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");  
        } else if(user.contains("rv"))  
        {  
            String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");  
            browser="IE" + IEVersion.substring(0,IEVersion.length() - 1);  
        } else  
        {  
            browser = "UnKnown, More-Info: "+userAgent;  
        }  
  
        return os +"---"+ browser ;  
    }  

	
}
