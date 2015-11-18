package com.ebay.android.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private static final String TODO_INSTANCE_REQUEST = "com.ebay.android.simpletodo.EditActivity.todo_instance_request";
    private static final String TODO_POSITION_REQUEST = "com.ebay.android.simpletodo.EditActivity.todo_position_request";
    private static final String TODO_INSTANCE_RESPONSE ="com.ebay.android.simpletodo.EditActivity.todo_instance_response";
    private static final String TODO_POSITION_RESPONSE = "com.ebay.android.simpletodo.EditActivity.todo_position_response";

    EditText mTitleEditText;
    Spinner mPriorityEditSpinner;
    private Todo mTodo;
    private int mPos;
    private String mTempPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        mTitleEditText = (EditText) findViewById(R.id.edit_editText);
        mPriorityEditSpinner = (Spinner) findViewById(R.id.editPriority_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.property_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPriorityEditSpinner.setAdapter(adapter);
        mPriorityEditSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTempPriority = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        mTodo = (Todo) getIntent().getSerializableExtra(TODO_INSTANCE_REQUEST);
        mTempPriority = mTodo.priority;
        mPos = getIntent().getIntExtra(TODO_POSITION_REQUEST,0);
        mTitleEditText.setText(mTodo.title);
        int priorityPosition = 0;
            if(mTodo.priority.equals("Medium")){
                priorityPosition = 1;
            }else{
                if(mTodo.priority.equals("Low")){
                    priorityPosition = 2;
                }
            }
        mPriorityEditSpinner.setSelection(priorityPosition);
    }

    public static Intent newIntent(Context packageContext, Todo todo,int pos) {
        Intent i = new Intent(packageContext, EditActivity.class);
        i.putExtra(TODO_INSTANCE_REQUEST,todo);
        i.putExtra(TODO_POSITION_REQUEST, pos);
        return i;
    }

    public void onSaveItem(View v){
        String mTodoTitle = mTitleEditText.getText().toString();
        String mTodoPriority = mTempPriority;
        setTodo(new Todo(mTodoTitle, mTodoPriority));

        Toast.makeText(EditActivity.this,
                R.string.save_edit_title,Toast.LENGTH_SHORT).show();
    }

    private void setTodo(Todo todo) {
        Intent data = new Intent();
        data.putExtra(TODO_INSTANCE_RESPONSE, todo);
        data.putExtra(TODO_POSITION_RESPONSE,mPos);
        setResult(RESULT_OK, data);
    }

    public static Todo getTodo(Intent result) {
        return (Todo) result.getSerializableExtra(TODO_INSTANCE_RESPONSE);
    }

    public static int getPos(Intent result){
        return result.getIntExtra(TODO_POSITION_RESPONSE,0);
    }

}
