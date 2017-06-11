package com.example.hoang.todoapp_prework.taskview;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.hoang.todoapp_prework.ConfirmActionDialog;
import com.example.hoang.todoapp_prework.Database;
import com.example.hoang.todoapp_prework.ManagerActivity;
import com.example.hoang.todoapp_prework.R;
import com.example.hoang.todoapp_prework.Task;

import org.w3c.dom.Text;

import java.util.Locale;

public class DetailActivity extends Activity {

    private Task task;
    public static final  int EDIT_TASK_REQUEST_CODE = 1;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //connect to DB
        database = new Database(this);
        database.open();

        // back button on action bar

        android.app.ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //retrieve task oject

        Bundle taskDetailBundle = this.getIntent().getExtras();
        try {
            this.task = (Task) taskDetailBundle.getSerializable(task.TASK_EXTRA_KEY);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        //pass  data on the view
        this.putDatatoView();
    }

    private void putDatatoView() {

        if(this.task == null){
            this.finish();

        }else {
            TextView taskTitle = (TextView) findViewById(R.id.task_content);
            taskTitle.setText(this.task.getTitle());

            TextView taskDueDay = (TextView) findViewById(R.id.due_day_content);
            java.util.Calendar dueDay = this.task.getDueDate();
            String dueDayString = dueDay.getDisplayName(java.util.Calendar.MONTH, java.util.Calendar.SHORT, Locale.US)
                    + " " + dueDay.get(java.util.Calendar.DATE) + " " + dueDay.get(java.util.Calendar.YEAR);
            taskDueDay.setText(dueDayString);

            TextView taskNote = (TextView) findViewById(R.id.notes_content);
            taskNote.setText(this.task.getNote());

            TextView taskPriority = (TextView) findViewById(R.id.priority_level_content);
            String priorityString = null;

            switch (this.task.getPriorityLevel()){
                case Task.HIGH_PRIORITY:
                    priorityString = this.getString(R.string.priority_level_high);
                    break;
                case Task.MEDIUM_PRIORITY:
                    priorityString = this.getString(R.string.priority_level_medium);
                    break;
                case Task.LOW_PRIORITY:
                    priorityString = this.getString(R.string.priority_level_low);
            }
            taskPriority.setText(priorityString);

            TextView taskCompletion = (TextView) findViewById(R.id.status_content);

            String completionStatus = null;
            switch (this.task.getStatus()){
                case Task.TASK_DONE:
                    completionStatus = this.getString(R.string.status_done);
                    break;
                case Task.TASK_TO_DO:
                    completionStatus = this.getString(R.string.status_to_do);
                    break;
            }

            taskCompletion.setText(completionStatus);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_task_detail,menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EDIT_TASK_REQUEST_CODE){
            this.task = (Task) data.getExtras().getSerializable(Task.TASK_EXTRA_KEY);
            this.putDatatoView();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.actionbar_edit:
                ManagerActivity.editExistTask(this,this.task);

                return true;
            case R.id.actionbar_delete:
                ConfirmActionDialog.showConfirmDeleteDialogForTask(this,this.task,this.database);
                default:
                    return super.onOptionsItemSelected(item);
        }

    }
}
