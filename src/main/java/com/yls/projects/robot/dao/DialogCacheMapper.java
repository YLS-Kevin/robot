/**
 * 
 */
package com.yls.projects.robot.dao;


import java.util.List;

import com.yls.projects.robot.entity.DialogExp;
import com.yls.projects.robot.entity.DialogMoreAndOne2;

/**
 * @author huangsy
 * @date 2018年2月26日上午9:19:43
 */
public interface DialogCacheMapper {

	List<DialogMoreAndOne2> findDialogById(String id);
	
	List<DialogExp> findDialogExpById(String id);
	
	List<DialogMoreAndOne2> findDialogByInterDataId(String id);
	
}
