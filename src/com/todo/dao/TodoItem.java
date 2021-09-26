package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
    private String title;
    private String desc;
    private String category;
    private String current_date;
    private String due_date;


    public TodoItem(String category, String title, String desc, String due_date){
        this.category = category;
    	this.title = title;
        this.desc = desc;
        this.current_date = new String();
        this.due_date = due_date;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String title) {
        this.category = category;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }
    
    public String getDue_date() {
        return due_date;
    }

    public void setCurrent_date(Date current_date) {
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String to = transFormat.format(current_date);
		
        this.current_date = to;
    }
    
    public String toSaveString() {
    	return category + "##" + title + "##" + desc + "##" + due_date + "##" + current_date + "\n";
    }
}