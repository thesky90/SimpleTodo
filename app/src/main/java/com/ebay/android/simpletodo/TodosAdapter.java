package com.ebay.android.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hachong on 11/16/15.
 */
public class TodosAdapter extends ArrayAdapter<Todo> {
    public TodosAdapter(Context context, ArrayList<Todo> TodoList) {
        super(context,0,TodoList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Todo todo = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }
        // Lookup view for data population
        TextView todoTitle = (TextView) convertView.findViewById(R.id.todoTitle);
        TextView todoPriority = (TextView) convertView.findViewById(R.id.todoPriority);
        // Populate the data into the template view using the data object
        todoTitle.setText(todo.title);
        todoPriority.setText(todo.priority);
        // Return the completed view to render on screen
        return convertView;
    }


}
