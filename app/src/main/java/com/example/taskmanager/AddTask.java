package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddTask extends AppCompatActivity {

    Button toolBarBtn, submitbtn;
    private MyParcelable parcelTime;
    EditText name, Desc, Prio, timezone, Date;
    TextView Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_location);

        parcelTime = getIntent().getParcelableExtra("Parcel");
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolBarBtn = (Button) findViewById(R.id.back);
        submitbtn = (Button)findViewById(R.id.submit);
        name = (EditText)findViewById(R.id.editText);
        Desc = (EditText)findViewById(R.id.editText2);
        Prio = (EditText)findViewById(R.id.editText3);
        timezone = (EditText)findViewById(R.id.editText4);
        Date = (EditText)findViewById(R.id.editText5);
        Title = (TextView)findViewById(R.id.title);
        Title.setText("Add Task");

        toolBarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTask.this, MainActivity.class);
                startActivity(intent);
            }
        });
       submitbtn.setOnClickListener(new View.OnClickListener() {

           @Override
                   public void onClick(View view) {
               String namechecker = name.getText().toString();
               String latchecker = Desc.getText().toString();
               String lonchecker = Prio.getText().toString();
               String timechecker = timezone.getText().toString();
               String date = Date.getText().toString();
               if (namechecker.equals("")) {
                   name.setHint("Input a city name");
               } else {

                               parcelTime.TaskName = namechecker;
                               parcelTime.Desc = latchecker;
                               parcelTime.Priority = lonchecker;
                               parcelTime.Time = timechecker;
                               parcelTime.Date = date;
                               toastsave();
                               Intent returnIntent = new Intent();
                               returnIntent.putExtra("result", parcelTime);
                               setResult(1, returnIntent);
                               finish();

               }
            }
        });

    }
    private void toastsave() {
        Toast.makeText(this, "Task Added", Toast.LENGTH_LONG).show();
    }

}
