package com.yls.projects.robot.entity;

public class RobotVocation {
    /** 主键id */
    private String id;

    /** 名称 */
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}