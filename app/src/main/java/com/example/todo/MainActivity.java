package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> items;
    private ListView list;
    private Button button;
    private ArrayAdapter<String> itemsAdapter;
    private Handler handler = new Handler();
    private ProgressBar progressBar;
    private TextView loadingText;
    private int progressStatus;
    private int counter = 0;
    private int progressBoost;
    Context mContext = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);
        button = findViewById(R.id.button);
        Button complete = (Button) findViewById(R.id.Complete);

        new Thread(new Runnable() {
            @Override
            public void run() {
                int delay=50;
                do {
                    Log.d("Agh", "we worky");

                    ActivityManager am = null;

                    am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);

                    List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);

                    String currentRunningActivityName = taskInfo.get(0).topActivity.getClassName();

                    if(currentRunningActivityName.equals("com.google.android.apps.nexuslauncher.NexusLauncherActivity") &&
                    items.size() > 0){

                        Log.d("ALERT:", "PROHIBITED APP DETECTED");

                        sendUserToApp();

                    }


                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }


                }while (true);
            }
        }).start();


        // Apparently THIS is how you create a new item with button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                additem(v);
            }
        });
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        list.setAdapter(itemsAdapter);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return remove(position);
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAll();
            }
        });

    }

    private boolean removeAll() {
        for (int i = items.size() - 1; i >= 0; i--) {
            remove(i);
        }
        return true;
    }

    private boolean remove(int position) {
        Context contex = getApplicationContext();
        Toast.makeText(contex, "Yo task just got: DELETED", Toast.LENGTH_LONG).show();
        items.remove(position);
        itemsAdapter.notifyDataSetChanged();
        return true;
    }

    private void additem(View view) {
        EditText input = findViewById(R.id.edit_text);
        String itemText = input.getText().toString();

        if (!(itemText.equals(""))) {
            itemsAdapter.add(itemText);
            input.setText("");
        } else {
            Toast.makeText(this, "Bro.... there's no text there....",
                    Toast.LENGTH_LONG).show();
        }



    }
    public void sendUserToApp(){

        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mContext.startActivity(startMain);

        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);



        //generateNotification(mContext, "PROHIBITED APP DETECTED");
    }


}