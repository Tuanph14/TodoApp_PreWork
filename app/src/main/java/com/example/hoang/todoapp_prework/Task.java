package com.example.hoang.todoapp_prework;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by hoang on 31/05/2017.
 */
public class Task implements Serializable {

    public static final int HIGH_PRIORITY = 0;
    public static final int MEDIUM_PRIORITY = 1;
    public static final int LOW_PRIORITY = 2;

    public static final int TASK_DONE = 0;
    public static final int TASK_TO_DO = 1;

    public static final String TASK_EXTRA_KEY = "task_extra_key";

    private String id;
    private String title;
    private Calendar dueDate;
    private String note;
    private int priorityLevel;
    private int completionStatus;

    public Task() {
        this.id = "";
        this.title = "";
        this.dueDate = Calendar.getInstance();
        this.priorityLevel = HIGH_PRIORITY;
        this.completionStatus = TASK_DONE;
    }

    public Task(String id, String title, Calendar dueDate, String note,
                int priorityLevel, int completionStatus) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.note = note;
        this.priorityLevel = priorityLevel;
        this.completionStatus = completionStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public int getStatus() {
        return completionStatus;
    }

    public void setStatus(int completionStatus) {
        this.completionStatus = completionStatus;
    }
}
