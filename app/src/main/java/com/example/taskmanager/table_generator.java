package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class table_generator extends AppCompatActivity {

    Button toolBarBtn, sharebtn;
    String City;
    String CityName;
    TextView Name;
    int postition;
    double lat, longi;
    String combinedshare;

    String dateword1, dateword2;
    int day1, day2, month1, month2, year1, year2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_generator);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolBarBtn = (Button) findViewById(R.id.back);
        toolBarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(table_generator.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }


}
