package com.yls.projects.dialog.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yls.frame.common.web.JsonResult;
import com.yls.frame.common.web.Result;
import com.yls.projects.common.controller.BaseController;
import com.yls.projects.dialog.entity.DialogMore;
import com.yls.projects.dialog.service.DialogMoreService;
import com.yls.projects.dialog.vo.DialogMoreVo;
import com.yls.projects.robot.entity.DialogUser;

@RestController
public class DialogMoreController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(DialogMoreController.class);
	@Autowired
	private DialogMoreService dialogMoreService;

	/**
	 * 分页获取主题列表
	 * 
	 * @Author:Suchy
	 * @Description:
	 * @Date: 19:06 2018/5/7
	 */
	@RequestMapping("/listTheme")
	@ResponseBody
	public JsonResult getListTheme(DialogMoreVo dialogMoreVo) {

		JsonResult result = this.dialogMoreService.dialogMoreList(dialogMoreVo);

		return result;
	}

	/**
	 * 新增主题
	 * 
	 * @Author:Suchy
	 * @Description:
	 * @Date: 19:09 2018/5/7
	 */
	@RequestMapping("/addTheme")
	@ResponseBody
	public JsonResult addTheme(DialogMore dialogMore, HttpServletRequest request) {
		// DialogUser dialogUser=(DialogUser)
		// request.getSession().getAttribute("dialogUser");
		DialogUser dialogUser = new DialogUser();
		dialogUser.setUserId("1");
		JsonResult result = this.dialogMoreService.addDialogMore(dialogMore, dialogUser);
		return result;
	}

	/**
	 * 删除主题
	 * 
	 * @Author:Suchy
	 * @Description:
	 * @Date: 19:11 2018/5/7
	 */
	@RequestMapping("/delTheme")
	@ResponseBody
	public JsonResult delTheme(String id, HttpServletRequest request) {
		// DialogUser dialogUser=(DialogUser)
		// request.getSession().getAttribute("dialogUser");
		DialogUser dialogUser = new DialogUser();
		dialogUser.setUserId("1");
		JsonResult result = this.dialogMoreService.delDialogMore(id, dialogUser);
		return result;
	}

	/**
	 * 修改主题
	 * 
	 * @Author:Suchy
	 * @Description:
	 * @Date: 19:11 2018/5/7
	 */
	@RequestMapping("/modifyTheme")
	@ResponseBody
	public JsonResult modifyTheme(DialogMore dialogMore, HttpServletRequest request) {
		// DialogUser dialogUser=(DialogUser)
		// request.getSession().getAttribute("dialogUser");
		DialogUser dialogUser = new DialogUser();
		dialogUser.setUserId("1");
		JsonResult result = this.dialogMoreService.updateDialogMore(dialogMore, dialogUser);
		return result;
	}

	/**
	 * 批量启用/禁用
	 * 
	 * @param dialogMoreVo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/modifyMulDialogByState")
	public Result modifyMulDialogByState(DialogMoreVo dialogMoreVo) {
		return dialogMoreService.modifyMulDialogByState(dialogMoreVo);
	}
}
