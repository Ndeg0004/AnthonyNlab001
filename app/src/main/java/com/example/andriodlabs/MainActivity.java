package com.example.andriodlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.BaseAdapter;

import java.util.List;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private EditText editTextTodo;
    private ListView todoListView;
    private Button buttonTodo;
    private Switch switchUrgent;
    private List<TodoItem> todoItemList;
    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTodo = findViewById(R.id.editTextTodo);
        switchUrgent = findViewById(R.id.switchUrgent);
        buttonTodo = findViewById(R.id.buttonAddTodo);
        todoListView = findViewById(R.id.todoListView);

        todoItemList = new ArrayList<>();
        todoAdapter = new TodoAdapter(this, todoItemList);
        todoListView.setAdapter(todoAdapter);

        buttonTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todoText = editTextTodo.getText().toString();
                boolean isUrgent = switchUrgent.isChecked();

                TodoItem newTodoItem = new TodoItem(todoText, isUrgent);
                todoItemList.add(newTodoItem);

                editTextTodo.setText("");
                todoAdapter.notifyDataSetChanged();
            }
        });

        todoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Do you want to delete this?");
                builder.setMessage("The selected row is: " + position);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Delete the item at the specified position
                        todoItemList.remove(position);
                        todoAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
                return true;
            }
        });
    }

    // TodoItem class
    public class TodoItem {
        public String todoText;
        public boolean isUrgent;

        public TodoItem(String todoText, boolean isUrgent) {
            this.todoText = todoText;
            this.isUrgent = isUrgent;
        }
    }

    // TodoAdapter class
    public class TodoAdapter extends BaseAdapter {
        private List<TodoItem> todoItems;
        private Context context;

        public TodoAdapter(Context context, List<TodoItem> todoItems) {
            this.context = context;
            this.todoItems = todoItems;
        }

        @Override
        public int getCount() {
            return todoItems.size();
        }

        @Override
        public Object getItem(int position) {
            return todoItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item_todo, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            TodoItem todoItem = todoItems.get(position);
            viewHolder.textViewTodo.setText(todoItem.todoText);

            // Set background color and text color based on urgency
            if (todoItem.isUrgent) {
                convertView.setBackgroundColor(Color.RED);
                viewHolder.textViewTodo.setTextColor(Color.WHITE);
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
                viewHolder.textViewTodo.setTextColor(Color.BLACK);
            }

            return convertView;
        }

        private class ViewHolder {
            TextView textViewTodo;

            ViewHolder(View view) {
                textViewTodo = view.findViewById(R.id.textViewTodo);
            }
        }
    }
}