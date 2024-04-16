package com.pr.databasepractice.Utils;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pr.databasepractice.Adapter.ToDoAdapter;
import com.pr.databasepractice.List.MyDatabase;
import com.pr.databasepractice.Model.TodoModel;
import com.pr.databasepractice.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.util.Log;


public class MainActivity extends AppCompatActivity implements OnDialogCloseListener {
    private ToDoAdapter adapter;
    private static final String TAG = "MainActivity";
    private MyDatabase myDb;
    private List<TodoModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recycleview = findViewById(R.id.recyclerview);
            myDb = new MyDatabase(MainActivity.this);
        FloatingActionButton bt = findViewById(R.id.fbt);
            list = new ArrayList<>();
            adapter=new ToDoAdapter(myDb,MainActivity.this);

            recycleview.setHasFixedSize(true);


        recycleview.setLayoutManager(new LinearLayoutManager(this));
        recycleview.setAdapter(adapter);

        list = myDb.getAllTask();
        Collections.reverse(list);
        adapter.setTasks(list);

            bt.setOnClickListener(view -> AddNewTask.newinst().show(getSupportFragmentManager(),AddNewTask.TAG));
        logTableContents();
    }

    @Override
    public void OnDialogClose(DialogInterface dialogInterface) {
        list = myDb.getAllTask();
        Collections.reverse(list);
        adapter.setTasks(list);
        adapter.notifyDataSetChanged();
    }

    private void logTableContents() {
        MyDatabase myDb = new MyDatabase(this);
        List<TodoModel> todoList = myDb.getAllTask();

        Log.d(TAG, "Table Contents:");
        for (TodoModel task : todoList) {
            Log.d(TAG, "ID: " + task.getId() + ", Task: " + task.getTask() + ", Status: " + task.getStatus());
        }
    }
}