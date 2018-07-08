package com.example.edgarpetrosian.ithome.Mobile;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edgarpetrosian.ithome.Activity.ProfileActivity;
import com.example.edgarpetrosian.ithome.Adapter.RecycleViewAdapter;
import com.example.edgarpetrosian.ithome.Models.ModelCourses;
import com.example.edgarpetrosian.ithome.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MobileTrainingsFragment extends Fragment {
    private View view;
    private TextView coursText;
    private int index, count, imagePath;
    private Button btnCount;
    private ProgressDialog progressDialog;
    private AlertDialog.Builder alertDialog;
    private String coursName;
    private ImageView imageView;
    private Context context;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecycleViewAdapter adapter;
    private List<ModelCourses> models;

    public MobileTrainingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_courses, container, false);
        getID();
        context = getContext();
        recyclerView = view.findViewById(R.id.recycleID);
        models = new ArrayList<>();
        models.add(new ModelCourses(R.drawable.java, "Java"));
        models.add(new ModelCourses(R.drawable.android, "Android"));
        index = getArguments().getInt("position");
        coursName = getArguments().getString("coursesname");
        //coursText.setText(coursName);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecycleViewAdapter(models, context);
        recyclerView.setAdapter(adapter);
        //   imageView.setImageResource(getArguments().getInt("image"));
        return view;
    }

    private void getID() {
        //     imageView = view.findViewById(R.id.imageID);
        coursText = view.findViewById(R.id.mobileCourseTextID);
        //      btnCount = view.findViewById(R.id.btnCountID);
    }

    public void onResume() {
        super.onResume();
        // Set title bar
        ((ProfileActivity) getActivity())
                .setActionBarTitle(coursName);
    }

}
