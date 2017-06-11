package com.example.hoang.todoapp_prework.taskview;

import android.app.Activity;
import android.app.ActivityManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.hoang.todoapp_prework.CustomTaskAdapter;
import com.example.hoang.todoapp_prework.Database;
import com.example.hoang.todoapp_prework.ManagerActivity;
import com.example.hoang.todoapp_prework.R;
import com.example.hoang.todoapp_prework.Task;

public class AllTaskActivity extends Activity {

    private ListView allTaskListView;
    private ListView allTaskListView2;
    private Database database;
    private Cursor  allTaskCursor;
    private SimpleCursorAdapter allTaskAdapter;
    private SimpleCursorAdapter allTaskAdapter2;

    public static final int  ADD_TASK_REQUSET_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_task);

        allTaskListView = (ListView) findViewById(R.id.ListView_all_tasks);
        allTaskListView2 = (ListView) findViewById(R.id.ListView2_all_tasks);

        allTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                taskItemClickHandle(parent,view,position);
            }
        });

        allTaskListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                taskItemClickHandle(parent,view,position);
            }
        });

        database = new Database(this);
        database.open();

        loadTaskIntoListView();
    }

    private void loadTaskIntoListView() {
        if(this.database != null){
            allTaskCursor = database.getAllTask();
            startManagingCursor(allTaskCursor);

            //get data from column
            String[] from = new String[]{
                    Database.TASK_TABLE_TITLE
            };

            //put data to which components in layout
            int [] to = new int[]{
                    R.id.task_title
            };

            String[] from2 = new String[]{
                    Database.TASK_TABLE_PRIORITY
            };

            //put data to which components in layout
            int [] to2 = new int[]{
                    R.id.priority_level_display
            };

            allTaskAdapter  = new CustomTaskAdapter(
                    this,R.layout.activity_view_all_task_item,allTaskCursor,from,to);
            allTaskAdapter2 = new CustomTaskAdapter(
                    this,R.layout.activity_view_all_task_item,allTaskCursor,from2,to2);
            allTaskAdapter2.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                    String index = allTaskCursor.getString(columnIndex);
                    String textToDisplay;
                    int color;

                    TextView tv = (TextView) view;

                    if(index.equals("0")) {
                        textToDisplay = "HIGH";
                        color = Color.parseColor("#d96679");
                } else if (index.equals("1")) {
                    textToDisplay = "MEDIUM";
                    color = Color.parseColor("#ffc153");
                } else {
                    textToDisplay = "LOW";
                    color = Color.parseColor("#2bbf98");
                }
                    tv.setText(textToDisplay);
                    tv.setTypeface(null, Typeface.BOLD);
                    tv.setTextColor(color);
                    return true;
                }
            });

            this.allTaskListView.setAdapter(allTaskAdapter);
            this.allTaskListView2.setAdapter(allTaskAdapter2);
        }

    }

    private void taskItemClickHandle(AdapterView<?> parent, View view, int position) {
        Task selectTask = new Task();

        allTaskCursor.moveToFirst();
        allTaskCursor.move(position);

        //selected task

        selectTask.setId(allTaskCursor.getString(allTaskCursor.getColumnIndex(Database.TASK_TABLE_ID)));
        selectTask.setTitle(allTaskCursor.getString(allTaskCursor.getColumnIndex(Database.TASK_TABLE_TITLE)));
        selectTask.getDueDate().setTimeInMillis(allTaskCursor.getLong(allTaskCursor.getColumnIndex(Database.TASK_TABLE_DUE_DATE)));
        selectTask.setPriorityLevel(allTaskCursor.getInt(allTaskCursor.getColumnIndex(Database.TASK_TABLE_PRIORITY)));
        selectTask.setStatus(allTaskCursor.getInt(allTaskCursor.getColumnIndex(Database.TASK_TABLE_STATUS)));

        ManagerActivity.viewTaskDetail(this,selectTask);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_all_task,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionbar_add:
                ManagerActivity.addNewTask(this,this.database);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
