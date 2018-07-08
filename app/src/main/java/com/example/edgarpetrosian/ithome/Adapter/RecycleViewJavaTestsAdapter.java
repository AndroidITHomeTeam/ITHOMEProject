package com.example.edgarpetrosian.ithome.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.edgarpetrosian.ithome.Mobile.MobileLessonsFragments.StartJavaTestFragment;
import com.example.edgarpetrosian.ithome.Models.ModelCourses;
import com.example.edgarpetrosian.ithome.R;

import java.util.List;

public class RecycleViewJavaTestsAdapter extends RecyclerView.Adapter<RecycleViewJavaTestsAdapter.MyViewHolder> {
    private View view;
    private Context context;
    private List<ModelCourses> modelCourses;

    public RecycleViewJavaTestsAdapter(Context context, List<ModelCourses> modelCourses) {
        this.context = context;
        this.modelCourses = modelCourses;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.java_tests_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.activity = (AppCompatActivity) view.getContext();
        holder.levelName.setText(modelCourses.get(position).getCoursesName());
        holder.levellayout.
                setBackground(holder.activity.getResources()
                        .getDrawable(modelCourses.get(position).getCoursImagePath()));
        holder.levellayout.setOnClickListener(v -> {
            switch (modelCourses.get(position).getCoursesName()){
                case "Level 1":
                    holder.bundle = new Bundle();
                    holder.bundle.putInt("position", position);
                    holder.bundle.putString("coursesname", modelCourses.get(position).getCoursesName());
                    holder.bundle.putInt("image", modelCourses.get(position).getCoursImagePath());
                    // set Fragmentclass Arguments
                    StartJavaTestFragment startJavaTestFragment = new StartJavaTestFragment();
                    startJavaTestFragment.setArguments(holder.bundle);
                    holder.activity = (AppCompatActivity) view.getContext();
                    holder.fragmentTransaction = holder.activity.getSupportFragmentManager().beginTransaction().addToBackStack("");
                    holder.fragmentTransaction.replace(R.id.conteyner, startJavaTestFragment);
                    holder.fragmentTransaction.commit();
                    break;
                case "Level 2":
                    break;
                case "Level 3":
                    break;
                case "Level 4":
                    break;
                case "Level 5":
                    break;
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelCourses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView levelName;
        FragmentTransaction fragmentTransaction;
        Bundle bundle;
        AppCompatActivity activity;
        LinearLayout levellayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            levellayout = itemView.findViewById(R.id.javaTestLinearItemID);
            levelName = itemView.findViewById(R.id.javaTestTextID);
        }
    }
}
