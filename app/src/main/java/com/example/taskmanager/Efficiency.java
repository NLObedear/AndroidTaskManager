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

public class Efficiency extends AppCompatActivity {
    int i;
    Button toolBarBtn;
    String Name, desc, prio, Time, date;
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

    Double time1, time2;
    Double efficiency1;
    Double added = 0.0;
    ArrayList<String> Efficiency = new ArrayList<String>();
    TextView eff,taskscomp, Title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.efficiency);
        taskscomp = (TextView) findViewById(R.id.tasks);
        eff = (TextView) findViewById(R.id.efficiency);
        Title = (TextView)findViewById(R.id.title);
        Title.setText("Efficiency");
        load();
        efficiency();
        Double is = time1/time2;
        String effic = "%" + Double.toString(added);
        eff.setText(effic);
        taskscomp.setText(Integer.toString(TaskNo.size()));
        toolBarBtn = (Button) findViewById(R.id.back);

        toolBarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Efficiency.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    private void efficiency() {
        int h =0;
        for (i = 0; i < TaskNo.size() ; i ++) {

            if (EstTime.get(i).equals("")) {} else {
                time1 = Double.parseDouble(EstTime.get(i));
                if (TimeTaken.get(i).equals("None")) {}else {
                    time2 = Double.parseDouble(TimeTaken.get(i));
                    efficiency1 = (time1/time2) * 100;
                    added = added + efficiency1;
                    h++;
                }
            }
        }
        added = added / h;
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
}
