package com.pr.databasepractice.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pr.databasepractice.List.MyDatabase;
import com.pr.databasepractice.Utils.AddNewTask;
import com.pr.databasepractice.Utils.MainActivity;
import com.pr.databasepractice.Model.TodoModel;
import com.pr.databasepractice.R;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {
    private  List<TodoModel> todolist;
    private MainActivity activity;
    private MyDatabase myDb;
    public ToDoAdapter(MyDatabase myDb,MainActivity activity){
        this.myDb = myDb;
        this.activity=activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    final TodoModel item = todolist.get(position);
    holder.check.setText(item.getTask());
    holder.check.setChecked(toBool(item.getStatus()));
    holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b){
                myDb.updateStatus(item.getId(), String.valueOf(1));
            }else {
                myDb.updateStatus(item.getId(), String.valueOf(0));
            }

        }
    });
    }
    public boolean toBool(int num){
        return num!=0;
    }


    @Override
    public int getItemCount() {
        if (todolist != null) {
            return todolist.size();
        } else {
            return 0; // or return another appropriate value
        }
    }


    public Context getContext(){
        return activity;
    }

    public void setTasks(List<TodoModel> todolist){
        this.todolist=todolist;
        notifyDataSetChanged();
    }

    public void deleteTasks(int position){
        TodoModel item = todolist.get(position);
        myDb.deleteTask(item.getId());
        todolist.remove(position);
        notifyItemRemoved(position);

    }

    public void editItem(int position){
        TodoModel item = todolist.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task",item.getTask());
        AddNewTask task =new AddNewTask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager(),task.getTag());
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox check;
        public MyViewHolder(View itemview){
            super(itemview);
            check = itemview.findViewById(R.id.checkbox);
        }
    }
}
