package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MyAdapter.OnButtonListener {

    private static final String TAG = "MainActivity";

    private PagerAdapter mSectionsStatePagerAdapter;
    private ViewPager mViewPager ,mViewPager2;
    String City;
    Button toolBarBtn, sharebtn, newbtn;
    EditText search;
    int i, taskno,show;
    String sunset, sunrise, combinedshare;
    ArrayList<String> TaskNo = new ArrayList<String>();
    ArrayList<String> TaskNames = new ArrayList<String>();
    ArrayList<String> Desc = new ArrayList<>();
    ArrayList<String> Priority = new ArrayList<>();
    ArrayList<String> EstTime = new ArrayList<String>();
    ArrayList<String> CompDate = new ArrayList<String>();
    private static final String FILE_NAME = "Tasks.txt";
    private MyParcelable parceltime;

    private ArrayList<String> mButton1 = new ArrayList<>();
    private ArrayList<String> mButton2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started.");

        mSectionsStatePagerAdapter = new PagerAdapter(getSupportFragmentManager());

        CreateEmptyParcel();
       // Data();
       load();
       // delete();
        taskno = 0;
        show = 0;
        taskno = getIntent().getIntExtra("Deleteposition", 0);
        show = getIntent().getIntExtra("save", 0);
        if (taskno != 0) {
            DeleteTask(taskno);
        }
        if (show != 0) {
            ShowTaskPosition(show);
        }
            initRecyclerView();
        /*
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager); */

        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolBarBtn = (Button)findViewById(R.id.table);
        newbtn = (Button)findViewById(R.id.addnew);
       // search = (EditText)findViewById(R.id.search);
        toolBarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager = (ViewPager) findViewById(R.id.container);
                mViewPager2 = (ViewPager) findViewById(R.id.container2);
                setupViewPager(mViewPager);
                setupViewPager2(mViewPager2);
            }
        });
        newbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Intent intent = new Intent(MainActivity.this, table_generator.class);
                //  startActivity(intent);
                final MyParcelable parcel1;
               parcel1 = CreateEmptyParcel();
                ItemClicked(parcel1, 1);
            }
        });
     /*   search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        }); */
    }
    private void setupViewPager(ViewPager viewPager){
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
            Fragment1 fragment1 = Fragment1.newInstance();
            adapter.addFragment(fragment1, "fragment 1");
        viewPager.setAdapter(adapter);
    }
    private void setupViewPager2(ViewPager viewPager){
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        Fragment2 fragment2 = Fragment2.newInstance();
        adapter.addFragment(fragment2, "fragment 2");
        viewPager.setAdapter(adapter);
    }
    public void setupViewPager3(ViewPager viewPager,String number ,String name, String desc, String prio, String time, String date){
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        Fragment3 fragment3 = Fragment3.newInstance(number,name, desc, prio, time, date);
        adapter.addFragment(fragment3, "fragment 3");
        viewPager.setAdapter(adapter);
    }
    public void DeleteTask(int i) {
        int j, h, k;
        h = i - 1 ;
        if (h == 0) {
            TaskNo.remove(h);
            TaskNames.remove(h);
            Desc.remove(h);
            Priority.remove(h);
            EstTime.remove(h);
            CompDate.remove(h);
            delete();
        } else {
            TaskNo.remove(h);
            TaskNames.remove(h);
            Desc.remove(h);
            Priority.remove(h);
            EstTime.remove(h);
            CompDate.remove(h);
            if (!TaskNo.isEmpty()) {
                for (j = 0; j < TaskNo.size(); j++) {
                    TaskNo.set(j, Integer.toString(j));
                }
                save();
            } else {

            }
        }
    }
    public void ShowTaskPosition(int i) {
       int h;
        h = i - 1;
        String taskname,taskdesc,taskprio,tasktime,taskdate;
        taskname = getIntent().getStringExtra("TaskName");
        taskdesc = getIntent().getStringExtra("TaskDesc");
        taskprio = getIntent().getStringExtra("TaskPrio");
        tasktime = getIntent().getStringExtra("TaskTime");
        taskdate = getIntent().getStringExtra("TaskDate");

        TaskNames.set(h,taskname);
        Desc.set(h,taskdesc);
        Priority.set(h,taskprio);
        EstTime.set(h,tasktime);
        CompDate.set(h,taskdate);

        save();
    }

    public MyParcelable CreateEmptyParcel() {
        final MyParcelable parcel1 = new MyParcelable("", "", "", "", "");
        return parcel1;
    }

    public void ItemClicked(MyParcelable fp, int i){
        Intent intent = new Intent(MainActivity.this, AddTask.class);
        intent.putExtra("Parcel", fp);
        startActivityForResult(intent, i);

    }
    public void save() {
        ArrayList<String> text = new ArrayList<>();
        for (i = 0; i < TaskNames.size(); i++) {
            text.add(TaskNo.get(i) + "," + TaskNames.get(i) + "," + Desc.get(i) + "," + Priority.get(i) + "," + EstTime.get(i) + "," + CompDate.get(i));
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

        ArrayList<String> text = new ArrayList<>();
        for (i = 0; i < TaskNames.size(); i++) {
            text.add(TaskNames.get(i) + "," + Desc.get(i) + "," + Priority.get(i) + "," + EstTime.get(i) + "," + CompDate.get(i));
        }
        FileOutputStream fos = null;
        String Oof = "oof";
        String empty = " ";
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(Oof.getBytes());
            fos.write('\n');
            for (i = 0; i < TaskNames.size() ; i++) {
                fos.write(empty.getBytes());
                fos.write('\n');
            }
          //  Toast.makeText(this, "Saved to" + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
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

    @Override
        public void onActivityResult(int requestcode, int resultcode, Intent data) {

        super.onActivityResult(requestcode, resultcode, data);
        if (resultcode == 1) {
            MyParcelable result = data.getParcelableExtra("result");

            int number = TaskNo.size();
            TaskNo.add(Integer.toString(number));
            TaskNames.add(result.TaskName);
            Desc.add(result.Desc);
            Priority.add(result.Priority);
            EstTime.add(result.Time);
            CompDate.add(result.Date);

            save();
            initRecyclerView();
        }
    }

    // LOAD IN FRAGMENTS
   /* private void setupViewPager(ViewPager viewPager){
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        for (i = 0; i < cityNames.size(); i++) {
            Fragment1 fragment1 = Fragment1.newInstance(cityNames.get(i), longitude.get(i), latitude.get(i), timeZones.get(i));
            adapter.addFragment(fragment1, "fragment 1");
        }
      //  adapter.addFragment(new Fragment3(), "Fragment3");
      //  adapter.addFragment(new Fragment4(), "Fragment4");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNo){
        mViewPager.setCurrentItem(fragmentNo);
    } */

/*  GET DATA FROM FRAGMENTS
    public void getData(String sset, String srise) {
        sunset = sset;
        sunrise = srise;
    } */
private void Data() {

    TaskNo.add("0");
    TaskNames.add("1");
    Desc.add("Do my weekly washing");
    Priority.add("1");
    EstTime.add("1");
    CompDate.add("22/10/19");

    TaskNo.add("1");
    TaskNames.add("2");
    Desc.add("Do my weekly washing");
    Priority.add("1");
    EstTime.add("1");
    CompDate.add("22/10/19");

    TaskNo.add("2");
    TaskNames.add("3");
    Desc.add("Do my weekly washing");
    Priority.add("1");
    EstTime.add("1");
    CompDate.add("22/10/19");

    TaskNo.add("3");
    TaskNames.add("4");
    Desc.add("Do my weekly washing");
    Priority.add("1");
    EstTime.add("1");
    CompDate.add("22/10/19");

    TaskNo.add("4");
    TaskNames.add("5");
    Desc.add("Do my weekly washing");
    Priority.add("1");
    EstTime.add("1");
    CompDate.add("22/10/19");

    TaskNo.add("5");
    TaskNames.add("6");
    Desc.add("Do my weekly washing");
    Priority.add("1");
    EstTime.add("1");
    CompDate.add("22/10/19");

    TaskNo.add("6");
    TaskNames.add("7");
    Desc.add("Do my weekly washing");
    Priority.add("1");
    EstTime.add("1");
    CompDate.add("22/10/19");

    TaskNo.add("7");
    TaskNames.add("8");
    Desc.add("Do my weekly washing");
    Priority.add("1");
    EstTime.add("1");
    CompDate.add("22/10/19");


    save();
}
     private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        MyAdapter adapter2 = new MyAdapter(TaskNo ,TaskNames,Desc,Priority,EstTime,CompDate,this, MainActivity.this);
        recyclerView.setAdapter(adapter2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void OnButtonClick(int position) {
        TaskNo.get(position);
    }
}
