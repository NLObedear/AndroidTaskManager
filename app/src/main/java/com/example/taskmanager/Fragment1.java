package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {
    int i = 0;
    String sunrise, sunset, city;
    String CityName, timeZone;
    Double Long, Lat;
    Button addnew,edit,completed,eff;

    public static Fragment1 newInstance() {
        Bundle bundle = new Bundle();
        Fragment1 fragment = new Fragment1();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            CityName = bundle.getString("name");
            Long = bundle.getDouble("Long");
            Lat = bundle.getDouble("Lat");
            timeZone = bundle.getString("tz");

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menufrag, container, false);
       // readBundle(getArguments());
        addnew = (Button) view.findViewById(R.id.addnew2);
        completed = (Button) view.findViewById(R.id.completed);
        eff = (Button) view.findViewById(R.id.efficiency);
        final MyParcelable fp = new MyParcelable("", "", "", "", "");

        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddTask.class);
                intent.putExtra("Parcel", fp);
                startActivityForResult(intent, i);
            }
        });
        completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Completed.class);
                startActivity(intent);
            }
        });
        eff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Efficiency.class);
                startActivity(intent);
            }
        });
        return view;
    }


}
