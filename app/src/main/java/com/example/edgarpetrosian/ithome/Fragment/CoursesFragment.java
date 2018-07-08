package com.example.edgarpetrosian.ithome.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.edgarpetrosian.ithome.Activity.ProfileActivity;
import com.example.edgarpetrosian.ithome.Adapter.RecycleViewAdapter;
import com.example.edgarpetrosian.ithome.Models.ModelCourses;
import com.example.edgarpetrosian.ithome.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoursesFragment extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private RecycleViewAdapter adapter;
    private View view;
    private List<ModelCourses> models;


    public CoursesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_courses, container, false);
        context = getContext();
        recyclerView = view.findViewById(R.id.recycleID);
        models = new ArrayList<>();
        models.add(new ModelCourses(R.drawable.mobile, "Mobile"));
        models.add(new ModelCourses(R.drawable.web, "WEB"));
        models.add(new ModelCourses(R.drawable.algoritm, "Algorithms"));
        models.add(new ModelCourses(R.drawable.machine_learning, "Machine Learning"));
        layoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(layoutManager);
        itemAnimation();
        adapter = new RecycleViewAdapter(models, context);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void itemAnimation() {
        LayoutAnimationController coursItemAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(coursItemAnimation);
    }

    public void onResume() {
        super.onResume();
        // Set title bar
        ((ProfileActivity) getActivity())
                .setActionBarTitle("Trainings");
    }

}
