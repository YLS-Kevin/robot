package com.yls.projects.robot.entity;

public class DialogCacheEventWithBLOBs extends DialogCacheEvent {
    /** 所操作的数据,更新前Json格式 */
    private String updateBeforeData;

    /** 所要操作的数据JSON格式 */
    private String updateData;

    public String getUpdateBeforeData() {
        return updateBeforeData;
    }

    public void setUpdateBeforeData(String updateBeforeData) {
        this.updateBeforeData = updateBeforeData == null ? null : updateBeforeData.trim();
    }

    public String getUpdateData() {
        return updateData;
    }

    public void setUpdateData(String updateData) {
        this.updateData = updateData == null ? null : updateData.trim();
    }
}