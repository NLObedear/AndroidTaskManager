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

public class EditTask extends AppCompatActivity {

    Button toolBarBtn, submitbtn, Delete;
    private MyParcelable parcelTime;
    EditText name, Desc, Prio, timezone, Date;
    String taskno;
    int transfertaskno;
    String Name, desc,prio, Time, date;
    TextView Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);


        taskno = getIntent().getStringExtra("TaskNo");
        Name = getIntent().getStringExtra("TaskName");
        desc = getIntent().getStringExtra("TaskDesc");
        prio = getIntent().getStringExtra("TaskPrio");
        Time = getIntent().getStringExtra("TaskTime");
        date = getIntent().getStringExtra("TaskDate");

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolBarBtn = (Button) findViewById(R.id.back);
        submitbtn = (Button)findViewById(R.id.submit);
        Delete = (Button)findViewById(R.id.delete);
        name = (EditText)findViewById(R.id.editText);
        Desc = (EditText)findViewById(R.id.editText2);
        Prio = (EditText)findViewById(R.id.editText3);
        timezone = (EditText)findViewById(R.id.editText4);
        Date = (EditText)findViewById(R.id.editText5);
        Title = (TextView)findViewById(R.id.title);
        Title.setText("Edit Task");

        name.setText(Name);
        Desc.setText(desc);
        Prio.setText(prio);
        timezone.setText(Time);
        Date.setText(date);
       // Delete.setText(taskno);
        transfertaskno = Integer.parseInt(taskno);

        toolBarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTask.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastdelete();
                Intent returnIntent = new Intent(EditTask.this, MainActivity.class);
                returnIntent.putExtra("Deleteposition", transfertaskno + 1);
                startActivity(returnIntent);
                finish();
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
                   name.setHint("Input a Task name");
               } else {
                   toastsave();
                               Intent returnIntent = new Intent(EditTask.this, MainActivity.class);
                               returnIntent.putExtra("save", transfertaskno + 1);
                   returnIntent.putExtra("TaskName", namechecker);
                   returnIntent.putExtra("TaskDesc", latchecker);
                   returnIntent.putExtra("TaskPrio", lonchecker);
                   returnIntent.putExtra("TaskTime", timechecker);
                   returnIntent.putExtra("TaskDate", date);
                               startActivity(returnIntent);


               }
            }
        });
    }
    private void toastsave() {
        Toast.makeText(this, "Task Saved", Toast.LENGTH_LONG).show();
    }
    private void toastdelete() {
        Toast.makeText(this, "Task Deleted", Toast.LENGTH_LONG).show();
    }
}
