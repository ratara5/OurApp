package com.misiontic.habit_tracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ToolsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToolsFragment extends Fragment {

    Button btnPushUps;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ToolsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ToolsFragment newInstance(String param1, String param2) {
        ToolsFragment fragment = new ToolsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_tools, container, false);
        View view = inflater.inflate(R.layout.fragment_tools,container,false);

        btnPushUps = (Button) view.findViewById(R.id.btnPushUps);
        btnPushUps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HabitsActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}