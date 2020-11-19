package com.example.groupproject;

public class Group {
    public String user_id;
    public String group_id;
    public String group_name;
    public String group_description;
    public String group_owner;
    public String finished;
    public String total;

    public Group(String user_id, String group_id, String group_name, String group_description, String group_owner, String finished, String total) {
        this.user_id=user_id;
        this.group_id=group_id;
        this.group_name=group_name;
        this.group_description=group_description;
        this.group_owner=group_owner;
        this.finished=finished;
        this.total=total;
    }
}