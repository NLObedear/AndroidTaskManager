package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Completed extends AppCompatActivity {

    Button toolBarBtn, submitbtn, Delete, clear;
    private MyParcelable parcelTime;
    String taskno;
    int transfertaskno, i;
    String Name, desc,prio, Time, date;
    ArrayList<String> TaskNo = new ArrayList<String>();
    ArrayList<String> TaskNames = new ArrayList<String>();
    ArrayList<String> Desc = new ArrayList<>();
    ArrayList<String> Priority = new ArrayList<>();
    ArrayList<String> EstTime = new ArrayList<String>();
    ArrayList<String> CompDate = new ArrayList<String>();
    ArrayList<String> TimeTaken = new ArrayList<String>();
    ArrayList<String> ActDate = new ArrayList<String>();
    private static final String FILE_NAME = "CompletedTasks.txt";
    private static final String TAG = "Completed";
    TextView Title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed);
        toolBarBtn = (Button) findViewById(R.id.back);
        Title = (TextView)findViewById(R.id.title);
        Title.setText("Completed");
        clear = (Button) findViewById(R.id.delete);

        load();
       // delete();
        initRecyclerView();
        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TaskNo.clear();
                TaskNames.clear();
                Desc.clear();
                Priority.clear();
                EstTime.clear();
                CompDate.clear();
                TimeTaken.clear();
                ActDate.clear();
                Clear();
                save();
                initRecyclerView();
            }
        });
        toolBarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Completed.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    public void Clear() {
        Toast.makeText(this, "Cleared", Toast.LENGTH_LONG).show();
    }
    public void load() {
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            List<String[]> rows = new ArrayList<>();
            String line;
            String csvSplitBy = ",";

            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] row = line.split(csvSplitBy);
                rows.add(row);
            }
            for (int i = 0; i < rows.size(); i++) {
                //  Log.d(Constants.TAG, String.format("row %s: %s, %s", i, rows.get(i)[0], rows.get(i)[1]));
                TaskNo.add(rows.get(i)[0]);
                TaskNames.add(rows.get(i)[1]);
                Desc.add(rows.get(i)[2]);
                Priority.add(rows.get(i)[3]);
                EstTime.add(rows.get(i)[4]);
                CompDate.add(rows.get(i)[5]);
                TimeTaken.add(rows.get(i)[6]);
                ActDate.add(rows.get(i)[7]);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void save() {
        ArrayList<String> text = new ArrayList<>();
        for (i = 0; i < TaskNames.size(); i++) {
            text.add(TaskNo.get(i) + "," + TaskNames.get(i) + "," + Desc.get(i) + "," + Priority.get(i) + "," + EstTime.get(i) + "," + CompDate.get(i) + "," + TimeTaken.get(i) + "," + ActDate.get(i));
        }
        FileOutputStream fos = null;
        String Oof = "oof";
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(Oof.getBytes());
            fos.write('\n');
            for (i = 0; i < TaskNames.size(); i++) {
                fos.write(text.get(i).getBytes());
                fos.write('\n');
            }
            //    Toast.makeText(this, "Saved to" + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void delete() {
        FileOutputStream fos = null;
        String Oof = "oof";
        String empty = "";
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(Oof.getBytes());
            fos.write('\n');
            for (i = 0; i < TaskNames.size() ; i++) {
                fos.write(empty.getBytes());
                fos.write('\n');
            }
            //Toast.makeText(this, "Saved to" + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
            TaskNo.clear();
            TaskNames.clear();
            Desc.clear();
            Priority.clear();
            EstTime.clear();
            CompDate.clear();
            TimeTaken.clear();
            ActDate.clear();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        MyAdapter2 adapter2 = new MyAdapter2(TaskNo ,TaskNames,Desc,Priority,EstTime,CompDate,TimeTaken,ActDate,this);
        recyclerView.setAdapter(adapter2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
