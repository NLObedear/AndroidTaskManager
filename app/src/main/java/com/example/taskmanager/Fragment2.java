package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment {
    int i = 0;
    String sunrise, sunset, city;
    String CityName, timeZone;
    Double Long, Lat;
    Button back;

    public static Fragment2 newInstance() {
        Bundle bundle = new Bundle();
        Fragment2 fragment = new Fragment2();
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

        View view = inflater.inflate(R.layout.menufrag2, container, false);
       // readBundle(getArguments());
        back = (Button) view.findViewById(R.id.exitmenu);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
               startActivity(intent);
            //   getActivity().getSupportFragmentManager().beginTransaction().remove(Fragment2.this).commit();
              //  getActivity().getSupportFragmentManager().beginTransaction().remove(Fragment1.newInstance()).commit();
              //  getActivity().getFragmentManager().popBackStack();
            //    getActivity().getFragmentManager().popBackStack();




            }
        });
        return view;
    }




}
