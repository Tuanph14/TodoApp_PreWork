package com.example.hoang.todoapp_prework.taskview;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.hoang.todoapp_prework.ConfirmActionDialog;
import com.example.hoang.todoapp_prework.Database;
import com.example.hoang.todoapp_prework.R;
import com.example.hoang.todoapp_prework.Task;

import java.util.Calendar;

public class EditItemActivity extends Activity {


    private Task task = null;
    private int userAction;
    private final int USER_ACTION_EDIT = 1;
    private final int USER_ACTION_ADD = 2;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        database = new Database(this);
        database.open();

        Bundle modifyTaskBundle = this.getIntent().getExtras();

        try {
            this.task = (Task) modifyTaskBundle.getSerializable(Task.TASK_EXTRA_KEY);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        if(task != null){
            this.userAction = this.USER_ACTION_EDIT;
            loadTaskDataOnForm(); // load data onto form fields
        }else {
            this.task = new Task();
            this.userAction = this.USER_ACTION_ADD;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_task,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent resultIntent;
        Bundle resultBundle;

        switch (item.getItemId()){
            case R.id.actionbar_cancel:
            case android.R.id.home:
                resultIntent = new Intent();
                resultBundle = new Bundle();
                resultBundle.putSerializable(Task.TASK_EXTRA_KEY,this.task);
                resultIntent.putExtras(resultBundle);
                setResult(DetailActivity.EDIT_TASK_REQUEST_CODE,resultIntent);
                ConfirmActionDialog.showConfirmCancelDialog(this);
                return true;
            case R.id.actionbar_save:
                if(this.userAction == USER_ACTION_ADD){
                    addNewTaskToDB();
                }else {
                    editExistingTask();
                    resultIntent = new Intent();
                    resultBundle = new Bundle();
                    resultBundle.putSerializable(Task.TASK_EXTRA_KEY, this.task);
                    resultIntent.putExtras(resultBundle);
                    setResult(DetailActivity.EDIT_TASK_REQUEST_CODE, resultIntent);
                }
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        Bundle resultBundle = new Bundle();
        resultBundle.putSerializable(Task.TASK_EXTRA_KEY,this.task);
        setResult(DetailActivity.EDIT_TASK_REQUEST_CODE,resultIntent);
    }


    private void loadNewDataOntoTask(){
        String taskTitle = ((EditText)findViewById(R.id.edit_text_task_title)).getText().toString();
        this.task.setTitle(taskTitle);

        DatePicker taskDueDatePicker = (DatePicker) findViewById(R.id.date_picker_due_date);
        this.task.getDueDate().set(Calendar.DATE,taskDueDatePicker.getDayOfMonth());
        this.task.getDueDate().set(Calendar.MONTH,taskDueDatePicker.getMonth());
        this.task.getDueDate().set(Calendar.YEAR,taskDueDatePicker.getYear());

        String taskNote = ((EditText)findViewById(R.id.edit_text_note)).getText().toString();
        this.task.setNote(taskNote);

        int priorityLevel = ((Spinner)findViewById(R.id.spinner_priority_level)).getSelectedItemPosition();
        this.task.setPriorityLevel(priorityLevel);

        int status = ((Spinner)findViewById(R.id.spinner_status)).getSelectedItemPosition();
        this.task.setStatus(status);

    }

    private void editExistingTask() {
        loadNewDataOntoTask();
        database.editExistingTask(this.task);
    }

    private void addNewTaskToDB() {
        loadNewDataOntoTask();
        String taskId = database.getNewTaskId();
        this.task.setId(taskId);
        this.database.insertTaskintoDB(this.task);
    }

    private void loadTaskDataOnForm(){
        if (this.userAction == USER_ACTION_EDIT){
            EditText taskTitleEdit = (EditText) findViewById(R.id.edit_text_task_title);
            taskTitleEdit.setText(this.task.getTitle());

            DatePicker taskDueDatePicker = (DatePicker) findViewById(R.id.date_picker_due_date);
            taskDueDatePicker.updateDate(this.task.getDueDate().get(Calendar.YEAR),
                    this.task.getDueDate().get(Calendar.MONTH),
                    this.task.getDueDate().get(Calendar.DATE));

            EditText taskNoteEditText = (EditText) findViewById(R.id.edit_text_note);
            taskNoteEditText.setText(this.task.getNote());

            Spinner taskPrioritySpinner = (Spinner) findViewById(R.id.spinner_priority_level);
            taskPrioritySpinner.setSelection(this.task.getPriorityLevel());

            Spinner taskStatus = (Spinner) findViewById(R.id.spinner_status);
            taskStatus.setSelection(this.task.getStatus());
        }
    }


    //    EditText etItem;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_item);
//
//        String strItemEdit = getIntent().getStringExtra("editItemText");
//        etItem = (EditText) findViewById(R.id.etEditItem);
//        etItem.setText(strItemEdit);
//        etItem.setSelection(strItemEdit.length());
//    }
//
//    public  void saveEditedItem(View view){
//
//        Intent data = new Intent();
//
//        data.putExtra("strItemEdited",etItem.getText().toString());
//        data.putExtra("code",100);
//        setResult(RESULT_OK,data);
//        finish();
//    }
}
