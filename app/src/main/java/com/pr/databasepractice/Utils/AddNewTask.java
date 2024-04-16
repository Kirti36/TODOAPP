package com.pr.databasepractice.Utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.core.content.ContextCompat;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pr.databasepractice.List.MyDatabase;
import com.pr.databasepractice.Model.TodoModel;
import com.pr.databasepractice.R;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG = "AddNewTask";

    //using widgets
    private EditText Ed;
    private Button savebtn;
    private MyDatabase myDb;

    public static AddNewTask newinst() {
     return new AddNewTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_newtask,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Ed = view.findViewById(R.id.edittext);
        savebtn = view.findViewById(R.id.btn);
        myDb=new MyDatabase(getActivity());

        boolean isUpdate = false;
        Bundle bundle = getArguments();
        if(bundle!=null){
            isUpdate=true;
            String Task = bundle.getString("task");
            Ed.setText(Task);

            assert Task != null;
            if(Task.length()>0){
                savebtn.setEnabled(false);
            }
        }
    Ed.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int i, int i1, int i2) {
            if(cs.toString().equals("")){
                savebtn.setEnabled(false);
                savebtn.setBackgroundColor(Color.LTGRAY);
            }else{
                savebtn.setEnabled(true);
                savebtn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.b));



            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    });
        final boolean finalIsUp = isUpdate;
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = Ed.getText().toString();
                if(finalIsUp){
                    myDb.updateTask(bundle.getInt("id"),text);
                }else {
                    TodoModel item = new TodoModel();
                    item.setTask(text);
                    item.setStatus(0);
                    myDb.insertTask(item);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if(activity instanceof OnDialogCloseListener){
            ((OnDialogCloseListener)activity).OnDialogClose(dialog);
        }
    }
}
