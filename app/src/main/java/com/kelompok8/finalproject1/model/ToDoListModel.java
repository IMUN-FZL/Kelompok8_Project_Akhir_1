package com.kelompok8.finalproject1.model;

public class ToDoListModel {
    private String task;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ToDoListModel(String task){
        this.task = task;
    }
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
