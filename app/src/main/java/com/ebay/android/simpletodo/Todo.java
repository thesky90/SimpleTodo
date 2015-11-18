package com.ebay.android.simpletodo;

import java.io.Serializable;

/**
 * Created by hachong on 11/16/15.
 */
public class Todo implements Serializable {
    public String title;
    public String priority;

    public Todo(String title,String priority){
        this.title = title;
        this.priority = priority;
    }
}
