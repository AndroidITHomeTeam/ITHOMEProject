package com.example.edgarpetrosian.ithome.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edgarpetrosian.ithome.Activity.ProfileActivity;
import com.example.edgarpetrosian.ithome.Adapter.RecycleViewAdapter;
import com.example.edgarpetrosian.ithome.Adapter.RecycleViewJavaTestsAdapter;
import com.example.edgarpetrosian.ithome.Mobile.JavaCoursFragment;
import com.example.edgarpetrosian.ithome.Models.ModelCourses;
import com.example.edgarpetrosian.ithome.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JavaTestsFragment extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    private RecycleViewJavaTestsAdapter adapter;
    private View view;
    private LinearLayoutManager layoutManager;
    private List<ModelCourses> models;

    private ImageView coverImage, back;

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

    public JavaTestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_java_tests2, container, false);
        context = getContext();
        initID();
        // hide Action Bar
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        // back All News Fragment
        back.setOnClickListener(v -> {
            replaceFragment(new JavaCoursFragment());
        });
        recyclerView = view.findViewById(R.id.javaTestRecycleID);
        models = new ArrayList<>();
        models.add(new ModelCourses(R.drawable.image_one, "Level 1"));
        models.add(new ModelCourses(R.drawable.image_two, "Level 2"));
        models.add(new ModelCourses(R.drawable.image_three, "Level 3"));
        models.add(new ModelCourses(R.drawable.image_four, "Level 4"));
        models.add(new ModelCourses(R.drawable.image_five, "Level 5"));
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        itemAnimation();
        adapter = new RecycleViewJavaTestsAdapter(context, models);
        recyclerView.setAdapter(adapter);
        collapsingToolbarLayout.setTitleEnabled(true);
        collapsingToolbarLayout.setTitle(getArguments().getString("tests"));
        coverImage.setImageResource(R.drawable.news_toolbar);

        return view;
    }

    private void initID() {
        appBarLayout = view.findViewById(R.id.javaTestsAppBarID);
        coverImage = view.findViewById(R.id.javaTestsImageDetailsID);
        collapsingToolbarLayout = view.findViewById(R.id.javaTestsColapsToolID);
        toolbar = view.findViewById(R.id.javaTestsToolbarID);
        back = view.findViewById(R.id.javaTestsBackID);
    }

    private void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getActivity().getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.conteyner, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }

    }

    public void itemAnimation() {
        LayoutAnimationController coursItemAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(coursItemAnimation);
    }

    //    public void onResume() {
//        super.onResume();
//        // Set title bar
//        ((ProfileActivity) getActivity())
//                .setActionBarTitle("Java Tests");
//    }
    private void toolBarIconOnClick() {
        Toast.makeText(context, "Tool Bar Icon OnClick", Toast.LENGTH_SHORT).show();
    }

    private void toolbarTextOnClick() {
        Toast.makeText(context, "ToolBar Text", Toast.LENGTH_SHORT).show();
    }

}
