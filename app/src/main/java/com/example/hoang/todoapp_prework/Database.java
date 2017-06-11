package com.example.hoang.todoapp_prework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.UUID;

/**
 * Created by hoang on 29/05/2017.
 */

public class Database  {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    private final Context context;

    public static final String DATABASE_NAME = "Todo.db";
    public static final int DATABASE_VERSION = 1;

    //TASK
    public static final String TASK_TABLE_NAME  = "_Task";
    public static final String TASK_TABLE_ID    = "_id";
    public static final String TASK_TABLE_TITLE = "_title";
    public static final String TASK_TABLE_DUE_DATE = "_due_date";
    public static final String TASK_TABLE_NOTES = "_notes";
    public static final String TASK_TABLE_STATUS= "_status";
    public static final String TASK_TABLE_PRIORITY = "_priority";


    //SQLite query
    public static final String TASK_TABLE_CREATE
            = "create table " + TASK_TABLE_NAME
            + " ( "
            + TASK_TABLE_ID        + " text primary key, "
            + TASK_TABLE_TITLE     + " text not null, "
            + TASK_TABLE_DUE_DATE  + " integer not null, "
            + TASK_TABLE_NOTES     + " text,"
            + TASK_TABLE_STATUS    + " integer not null, "
            + TASK_TABLE_PRIORITY  + " text not null, "
            + "foreign key ( " + TASK_TABLE_ID + " ) references " + TASK_TABLE_NAME + " ( "
            + TASK_TABLE_ID + " ) "
            + " ); ";

    public Database(Context context) {
        this.context = context;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Database.TASK_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop table if exists " + Database.TASK_TABLE_NAME);
            onCreate(db);
        }
    }

    public Database open(){
        databaseHelper = new DatabaseHelper(context,this.DATABASE_NAME,null,this.DATABASE_VERSION);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return  this;
    }

    public  void close (){
        databaseHelper.close();
    }

    public void insertTaskintoDB(Task task){
        ContentValues initialvaValues = new ContentValues();
        initialvaValues.put(TASK_TABLE_ID, task.getId());
        initialvaValues.put(TASK_TABLE_TITLE,task.getTitle());
        initialvaValues.put(TASK_TABLE_DUE_DATE,task.getDueDate().getTimeInMillis());
        initialvaValues.put(TASK_TABLE_NOTES,task.getNote());
        initialvaValues.put(TASK_TABLE_PRIORITY,task.getPriorityLevel());
        initialvaValues.put(TASK_TABLE_STATUS,task.getStatus());

        sqLiteDatabase.insert(TASK_TABLE_NAME,null,initialvaValues);
    }

    public Cursor getAllTask(){

        return  sqLiteDatabase.query(TASK_TABLE_NAME,
                new String[]{TASK_TABLE_ID,TASK_TABLE_TITLE,TASK_TABLE_DUE_DATE,TASK_TABLE_NOTES,TASK_TABLE_PRIORITY,TASK_TABLE_STATUS},
                null,null,null,null,null);
    }

    public  Cursor getTaskById (String taskId){

        return sqLiteDatabase.query(TASK_TABLE_NAME,
                new String[]{TASK_TABLE_ID,TASK_TABLE_TITLE,TASK_TABLE_DUE_DATE,TASK_TABLE_NOTES,TASK_TABLE_PRIORITY,TASK_TABLE_STATUS},
                TASK_TABLE_ID + " = '" + taskId + "'",null, null, null, null);
    }

    public void editExistingTask(Task task){
        ContentValues upContentValues = new ContentValues();
        upContentValues.put(TASK_TABLE_ID, task.getId());
        upContentValues.put(TASK_TABLE_TITLE,task.getTitle());
        upContentValues.put(TASK_TABLE_DUE_DATE,task.getDueDate().getTimeInMillis());
        upContentValues.put(TASK_TABLE_NOTES,task.getNote());
        upContentValues.put(TASK_TABLE_PRIORITY,task.getPriorityLevel());
        upContentValues.put(TASK_TABLE_STATUS,task.getStatus());

        sqLiteDatabase.update(TASK_TABLE_NAME,upContentValues, TASK_TABLE_ID + " = '" + task.getId() + "'",null);

    }

    public void deleteTask(Task task){
        deleteTaskById(task.getId());
    }

    public  void deleteTaskById(String taskId){
        sqLiteDatabase.delete(TASK_TABLE_NAME,TASK_TABLE_ID + " = '" + taskId + "'",null);

    }

    public String getNewTaskId(){
        String uuid = null;
        Cursor cursor = null;
         do{
             uuid = UUID.randomUUID().toString();
             cursor = getTaskById(uuid);
         }while (cursor.getCount() > 0);

        return uuid;
    }
}
