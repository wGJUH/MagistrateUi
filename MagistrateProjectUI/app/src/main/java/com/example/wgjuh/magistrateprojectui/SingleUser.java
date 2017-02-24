package com.example.wgjuh.magistrateprojectui;

/**
 * Created by wGJUH on 24.02.2017.
 */

public class SingleUser {
    private int id;
    private int groupId;
    private String groupName;
    private String name;


    SingleUser(){
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }
}
