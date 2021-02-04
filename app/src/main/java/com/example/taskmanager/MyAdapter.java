package com.example.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private static final String TAG = "MyAdapter";


    private ArrayList<String> mTaskNo = new ArrayList<>();
    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> Desc = new ArrayList<>();
    private ArrayList<String> Prio = new ArrayList<>();
    private ArrayList<String> Time = new ArrayList<>();
    private ArrayList<String> CompDate = new ArrayList<>();
    private ArrayList<String> mbutton1 = new ArrayList<>();
    private ArrayList<String> mbutton2 = new ArrayList<>();
    private Context mContext;
    private ViewPager mViewPager;
    private MainActivity main;

    public MyAdapter(ArrayList<String> taskNo, ArrayList<String> imageNames, ArrayList<String> desc, ArrayList<String> prio, ArrayList<String> time, ArrayList<String> date, Context context, MainActivity m) {

       mTaskNo = taskNo;
        mImageNames = imageNames;
        Desc = desc;
        Prio = prio;
        Time = time;
        CompDate = date;
        mContext = context;
        main = m;

    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        /*Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.image);*/
       final int index = position;
        holder.imageName.setText(mImageNames.get(position));

        holder.Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager = (ViewPager) main.findViewById(R.id.container3);
                main.setupViewPager3(mViewPager,mTaskNo.get(index), mImageNames.get(index), Desc.get(index), Prio.get(index), Time.get(index), CompDate.get(index));
            }
        });
        holder.Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditTask.class);
                intent.putExtra("TaskNo", mTaskNo.get(index));
                intent.putExtra("TaskName", mImageNames.get(index));
                intent.putExtra("TaskDesc", Desc.get(index));
                intent.putExtra("TaskPrio", Prio.get(index));
                intent.putExtra("TaskTime", Time.get(index));
                intent.putExtra("TaskDate", CompDate.get(index));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView imageName;
        RelativeLayout parentLayout;
        Button Button1, Button2;

        public ViewHolder(View itemView) {
            super(itemView);
            imageName = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            Button1 = itemView.findViewById(R.id.btn1);
            Button2 = itemView.findViewById(R.id.btn2);
        }
    }
    public interface OnButtonListener{
        void OnButtonClick(int position);
    }
}
