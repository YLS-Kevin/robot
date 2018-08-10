package com.yls.projects.dialog.controller;

import com.yls.frame.common.web.JsonResult;
import com.yls.projects.common.controller.BaseController;
import com.yls.projects.dialog.entity.DialogTypes;
import com.yls.projects.dialog.service.DialogTypesService;
import com.yls.projects.robot.entity.DialogUser;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:Suchy
 * @Description:添加对话库
 * @Date: 10:59 2018/5/4
 */
@Controller
public class AddDialogLibraiyContrller extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(AddDialogLibraiyContrller.class);
    @Autowired
    private DialogTypesService dialogTypesService;

    /**
     * @Author:Suchy
     * @Description:添加对话
     * @Date: 11:48 2018/5/4
     */
    //@RequiresPermissions("user:addDianlogLibrary")
    @RequestMapping("/addDialogLibraiy")
    @ResponseBody
    public JsonResult addDialogLibraiy(DialogTypes dialogTypes,HttpServletRequest request){
        //DialogUser dialogUser2=(DialogUser) request.getSession().getAttribute("dialogUser");
    	Object o = SecurityUtils.getSubject().getPrincipal();
    	//DialogUser dialogUser1 = (DialogUser) SecurityUtils.getSubject().getPrincipal();
    	DialogUser dialogUser = new DialogUser();
    	dialogUser.setUserId("1");
        JsonResult result=this.dialogTypesService.addDialogTypes(dialogTypes,dialogUser);
        return  result;
    }
}
