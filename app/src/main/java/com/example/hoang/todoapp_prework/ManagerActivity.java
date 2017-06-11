package com.example.hoang.todoapp_prework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.hoang.todoapp_prework.taskview.AllTaskActivity;
import com.example.hoang.todoapp_prework.taskview.DetailActivity;
import com.example.hoang.todoapp_prework.taskview.EditItemActivity;

/**
 * Created by hoang on 31/05/2017.
 */

public class ManagerActivity {

    public static void viewTaskDetail(Activity sourceActivity, Task task){

        Intent viewTaskDetailintent = new Intent(sourceActivity, DetailActivity.class);
        Bundle viewTaskDetailBundle = new Bundle();
        viewTaskDetailBundle.putSerializable(Task.TASK_EXTRA_KEY,task);
        viewTaskDetailintent.putExtras(viewTaskDetailBundle);
        sourceActivity.startActivity(viewTaskDetailintent);

    }

    public static void editExistTask (Activity sourceActivity, Task edittask){
        Intent edtiIntent = new Intent(sourceActivity, EditItemActivity.class);
        Bundle editBundle = new Bundle();
        editBundle.putSerializable(Task.TASK_EXTRA_KEY,edittask);
        edtiIntent.putExtras(editBundle);
        sourceActivity.startActivityForResult(edtiIntent, DetailActivity.EDIT_TASK_REQUEST_CODE);
    }

    public static void addNewTask (Activity sourceActivity, Database database){
        Intent addNewIntent = new Intent(sourceActivity, EditItemActivity.class);
        sourceActivity.startActivityForResult(addNewIntent,AllTaskActivity.ADD_TASK_REQUSET_CODE);
    }
}
