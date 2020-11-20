package com.example.groupproject;

public class Task {
    public String user_id;
    public String task_id;
    public String task_name;
    public String task_no;
    public String status;

    public Task(String user_id, String task_id, String task_name, String task_no, String status) {
        this.user_id=user_id;
        this.task_id=task_id;
        this.task_name=task_name;
        this.task_no=task_no;
        this.status=status;
    }
}