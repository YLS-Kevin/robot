package com.yls.projects.dialog.controller;

import com.yls.frame.common.web.JsonResult;
import com.yls.projects.common.controller.BaseController;
import com.yls.projects.dialog.service.DialogTypesService;

import com.yls.projects.dialog.vo.DialogTypesVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Author:Suchy
 * @Description:获取对话库列表
 * @Date: 11:00 2018/5/4
 */
@Controller
public class ListDialogLibraryController extends BaseController{
    private static Logger logger = LoggerFactory.getLogger(ListDialogLibraryController.class);
    @Autowired
    private DialogTypesService dialogTypesService;

    /**
     * @Author:Suchy
     * @Description:分页获取对话库列表
     * @Date: 11:48 2018/5/4
     */
    @RequestMapping("/listDialogLibrary")
    @ResponseBody
    public JsonResult getDialogTypesPage(DialogTypesVo dialogTypesVo) {

        JsonResult result = this.dialogTypesService.dialogTypesList(dialogTypesVo);
        return result;
    }

}
