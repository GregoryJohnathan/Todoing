package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> items;
    private ArrayList<Boolean> itemsSelection;
    private ListView list;
    private Button button;
    private ArrayAdapter<String> itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);
        button = findViewById(R.id.button);


        // Apparently THIS is how you create a new item with button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                additem(v);
            }
        });
        items = new ArrayList<>();
        itemsSelection = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        list.setAdapter(itemsAdapter);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return remove(position);
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                checkOffItem(position, view);
            }
        });


    }

    private boolean remove(int position){
        Context context = getApplicationContext();
        Toast.makeText(context, "Yo task just got: DELETED", Toast.LENGTH_LONG).show();
        items.remove(position);
        itemsSelection.remove(position);
        itemsAdapter.notifyDataSetChanged();
        return true;
    }

    private void additem(View view){
        EditText input = findViewById(R.id.edit_text);
        String itemText = input.getText().toString();

        if(!(itemText.equals(""))){
            itemsAdapter.add(itemText);
            input.setText("");
            itemsSelection.add(false);
        }

        else{
            Toast.makeText(this, "Bro.... there's no text there....",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void checkOffItem(int position, View view){
        EditText input = findViewById(R.id.edit_text);
        Context context = getApplicationContext();


        if(!itemsSelection.get(position)){
            Toast.makeText(context, "Yo task just got: CHECKED", Toast.LENGTH_SHORT).show();
            view.setBackgroundColor(Color.GRAY);
        } else{
            Toast.makeText(context, "Yo task just got: UNCHECKED", Toast.LENGTH_SHORT).show();
            view.setBackgroundColor(Color.WHITE);
        }
        itemsSelection.set(position, !itemsSelection.get(position));

        //itemsAdapter.getItem(position)
        itemsAdapter.notifyDataSetChanged();

    }

}