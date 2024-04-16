package com.pr.databasepractice.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pr.databasepractice.Model.TodoModel;

import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TODO_DB";
    private static final String TABLE_NAME = "Task_table";
    private static final String ID = "ID";
    private static final String MY_TASK = "task";
    private static final String STATUS = "status";

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Use uppercase for SQL keywords and table/column names
        String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MY_TASK + " TEXT,"
                + STATUS + " INTEGER)";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing table if it exists and recreate it
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertTask(TodoModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        // Don't insert ID, it's auto-incremented
        cv.put(MY_TASK, model.getTask());
        cv.put(STATUS, model.getStatus());
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public void updateTask(int id, String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MY_TASK, task);
        db.update(TABLE_NAME, cv, ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateStatus(int id, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TABLE_NAME, cv, ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    @SuppressLint("Range")
    public List<TodoModel> getAllTask() {
        List<TodoModel> Todolist = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    TodoModel task = new TodoModel();
                    task.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                    task.setTask(cursor.getString(cursor.getColumnIndex(MY_TASK)));
                    task.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
                    Todolist.add(task);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return Todolist;
    }
}
