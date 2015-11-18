package com.ebay.android.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //ArrayList<String> items;
    ArrayList<Todo> todoList;
    //ArrayAdapter<String> itemsAdapter;
    String path;
    TodosAdapter itemsAdapter;
    ListView lvItems;
    private static final int REQUEST_EDIT = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems =(ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new TodosAdapter(this,todoList);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    public void onAddItem(View v){
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String todoTitle = etNewItem.getText().toString();
        itemsAdapter.add(new Todo(todoTitle,"Low"));
        etNewItem.setText("");
        writeItems();
    }

    private void setupListViewListener(){

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoList.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = EditActivity.newIntent(MainActivity.this, todoList.get(position),position);
                startActivityForResult(i, REQUEST_EDIT);
            }
        });
    }

    private void readItems(){
        //File filesDir = getFilesDir();
        //File todoFile = new File(filesDir,"todo.txt");
        try{
            if(path == null)
                path= getFilesDir().getPath().toString() + "/"+"todo.txt";
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            todoList = (ArrayList<Todo>) ois.readObject();
            ois.close();

            //todoList = new ArrayList<>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            e.printStackTrace();
            todoList = new ArrayList<>();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            todoList = new ArrayList<>();
        }
    }

    private void writeItems(){
        try {
            if(path == null)
                path= getFilesDir().getPath().toString() + "/"+"todo.txt";
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(todoList);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return; }
        if (requestCode == REQUEST_EDIT) {
            if (data == null) {
                return; }
            Todo todo = EditActivity.getTodo(data);
            int mPos = EditActivity.getPos(data);
            todoList.remove(mPos);
            todoList.add(mPos,todo);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }



}
