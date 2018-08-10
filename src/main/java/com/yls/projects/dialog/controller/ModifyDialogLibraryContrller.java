package com.yls.projects.dialog.controller;

import com.yls.frame.common.utils.ResultUtil;
import com.yls.frame.common.web.JsonResult;
import com.yls.frame.common.web.Result;
import com.yls.projects.common.controller.BaseController;
import com.yls.projects.dialog.entity.DialogTypes;
import com.yls.projects.dialog.service.DialogTypesService;
import com.yls.projects.robot.entity.DialogUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:Suchy
 * @Description:修改对话库
 * @Date: 11:00 2018/5/4
 */
@Controller
public class ModifyDialogLibraryContrller extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ModifyDialogLibraryContrller.class);
    @Autowired
    private DialogTypesService dialogTypesService;

    /**
     * @Author:Suchy
     * @Description:添加对话
     * @Date: 11:48 2018/5/4
     */
    @RequestMapping("/modifyDialogLibrary")
    @ResponseBody
    public JsonResult modifyDialogLibrary(DialogTypes dialogTypes,HttpServletRequest request){
        //DialogUser dialogUser=(DialogUser) request.getSession().getAttribute("dialogUser");
    	DialogUser dialogUser = new DialogUser();
    	dialogUser.setUserId("1");
        JsonResult result=this.dialogTypesService.updateDialogTypes(dialogTypes,dialogUser);
        return  result;
    }
    
    /**
     * @Author:Suchy
     * @Description:添加对话
     * @Date: 11:48 2018/5/4
     */
    @PostMapping("/shareDialogLibrary")
    @ResponseBody
    public Result shareDialogLibrary(DialogTypes dialogTypes){
        return dialogTypesService.shareDialogLibrary(dialogTypes);
    }
}
