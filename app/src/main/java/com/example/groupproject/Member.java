package com.example.groupproject;

public class Member {
    public String group_id;
    public String user_id;
    public String username;
    public String total;
    public String finished;

    public Member(String group_id, String user_id, String username, String total, String finished) {
        this.user_id=user_id;
        this.group_id=group_id;
        this.username=username;
        this.finished=finished;
        this.total=total;
    }
}