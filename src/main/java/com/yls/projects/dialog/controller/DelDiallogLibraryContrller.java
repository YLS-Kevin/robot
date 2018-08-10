package com.yls.projects.dialog.controller;

import com.yls.frame.common.web.JsonResult;
import com.yls.projects.common.controller.BaseController;
import com.yls.projects.dialog.service.DialogTypesService;
import com.yls.projects.robot.entity.DialogUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * @Author:Suchy
 * @Description:删除对话库
 * @Date: 11:00 2018/5/4
 */
@Controller
public class DelDiallogLibraryContrller extends BaseController{
    private static Logger logger = LoggerFactory.getLogger(DelDiallogLibraryContrller.class);
    @Autowired
    private DialogTypesService dialogTypesService;

    /**
     * @Author:Suchy
     * @Description:添加对话
     * @Date: 11:48 2018/5/4
     */
    @RequestMapping("/delDiallogLibrary")
    @ResponseBody
    public JsonResult delDiallogLibrary(String id,HttpServletRequest request){
        //DialogUser dialogUser=(DialogUser) request.getSession().getAttribute("dialogUser");
        DialogUser dialogUser = new DialogUser();
    	dialogUser.setUserId("1");
        JsonResult result=this.dialogTypesService.delDialogTypes(id,dialogUser);
        return  result;
    }
}
