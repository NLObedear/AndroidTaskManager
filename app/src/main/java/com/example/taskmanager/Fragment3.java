package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Fragment3 extends Fragment {
    int i = 0;
    String sunrise, sunset, city;
    String taskno, taskname, taskdesc,taskprio,tasktime,taskdate;
    Double Long, Lat;
    Button complete, back;
    EditText timetaken,datecompleted;

    ArrayList<String> TaskNo = new ArrayList<String>();
    ArrayList<String> TaskNames = new ArrayList<String>();
    ArrayList<String> Desc = new ArrayList<>();
    ArrayList<String> Priority = new ArrayList<>();
    ArrayList<String> EstTime = new ArrayList<String>();
    ArrayList<String> CompDate = new ArrayList<String>();
    ArrayList<String> TimeTaken = new ArrayList<String>();
    ArrayList<String> ActDate = new ArrayList<String>();
    private static final String FILE_NAME = "CompletedTasks.txt";

    public static Fragment3 newInstance(String tasknumber,String name, String desc,String Prio, String time,String date) {
        Bundle bundle = new Bundle();

       bundle.putString("number", tasknumber);
        bundle.putString("name", name);
        bundle.putString("desc", desc);
        bundle.putString("prio", Prio);
        bundle.putString("time", time);
        bundle.putString("date", date);

        Fragment3 fragment = new Fragment3();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            taskno = bundle.getString("number");
            taskname = bundle.getString("name");
            taskdesc = bundle.getString("desc");
            taskprio = bundle.getString("prio");
            tasktime = bundle.getString("time");
            taskdate = bundle.getString("date");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.completefrag, container, false);
        Bundle b3 =getArguments();
        readBundle(b3);
       // readBundle(getArguments());
        back = (Button) view.findViewById(R.id.back);
        complete = (Button) view.findViewById(R.id.complete);
        timetaken = (EditText) view.findViewById(R.id.timetaken);
        datecompleted = (EditText) view.findViewById(R.id.date);
        load();
        //test.setText(taskname);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // FRAGMENT DOESNT GET DELETED FOR SOME REASON SO RE LAUNCH ACTIVITY IS DONE
                // getActivity().getSupportFragmentManager().beginTransaction().remove(Fragment3.this).commit();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = TaskNo.size();
                TaskNo.add(Integer.toString(number));
                TaskNames.add(taskname);
                Desc.add(taskdesc);
                Priority.add(taskprio);
                EstTime.add(tasktime);
                CompDate.add(taskdate);
                if (timetaken.getText().toString().equals("")) {
                    TimeTaken.add("None");
                } else {
                    TimeTaken.add(timetaken.getText().toString());
                }
                if (datecompleted.getText().toString().equals("")) {
                    ActDate.add("No date added");
                } else {
                    ActDate.add(datecompleted.getText().toString());
                }
                save();
                int n;
                n = Integer.parseInt(taskno) + 1;
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("Deleteposition", n);
                startActivity(intent);
            }
        });
        return view;
    }

    public void save() {
        ArrayList<String> text = new ArrayList<>();
        for (i = 0; i < TaskNames.size(); i++) {
            text.add(TaskNo.get(i) + "," + TaskNames.get(i) + "," + Desc.get(i) + "," + Priority.get(i) + "," + EstTime.get(i) + "," + CompDate.get(i) + "," + TimeTaken.get(i) + "," + ActDate.get(i));
        }
        FileOutputStream fos = null;
        String Oof = "oof";
        try {
            fos = getActivity().openFileOutput(FILE_NAME, MODE_PRIVATE);
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
    public void load() {
        FileInputStream fis = null;

        try {
            fis = getActivity().openFileInput(FILE_NAME);
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
