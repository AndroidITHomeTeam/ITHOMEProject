package com.example.edgarpetrosian.ithome.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.edgarpetrosian.ithome.Activity.ProfileActivity;
import com.example.edgarpetrosian.ithome.Algorithm.AlgorithmsTrainingsFragment;
import com.example.edgarpetrosian.ithome.Fragment.ItemInfoFragment;
import com.example.edgarpetrosian.ithome.MachineLearning.MachineLearningTrainingsFragment;
import com.example.edgarpetrosian.ithome.Mobile.AndroidTrainingsFragment;
import com.example.edgarpetrosian.ithome.Mobile.JavaCoursFragment;
import com.example.edgarpetrosian.ithome.Mobile.MobileTrainingsFragment;
import com.example.edgarpetrosian.ithome.Models.ModelCourses;
import com.example.edgarpetrosian.ithome.R;
import com.example.edgarpetrosian.ithome.Web.WebTrainingsFragment;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    private List<ModelCourses> modelCourses;
    private Context context;
    private View view;

    public RecycleViewAdapter(List<ModelCourses> modelCourses, Context context) {
        this.modelCourses = modelCourses;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_view, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int quiz = ProfileActivity.quiz;
        holder.imageView.setImageResource(modelCourses.get(position).getCoursImagePath());
        holder.courseName.setText(modelCourses.get(position).getCoursesName());
        holder.layout.setOnClickListener(v -> {
            switch (modelCourses.get(position).getCoursesName()) {
                case "Android":
                    AndroidTrainingsFragment androidTrainingsFragment = new AndroidTrainingsFragment();
                    holder.bundle = new Bundle();
                    holder.bundle.putInt("position", position);
                    holder.bundle.putString("coursesname", modelCourses.get(position).getCoursesName());
                    androidTrainingsFragment.setArguments(holder.bundle);
                    holder.activity = (AppCompatActivity) view.getContext();
                    holder.fragmentTransaction = holder.activity.getSupportFragmentManager().beginTransaction().addToBackStack("");
                    holder.fragmentTransaction.replace(R.id.conteyner, androidTrainingsFragment);
                    holder.fragmentTransaction.commit();
                    break;
                case "Java":
                    JavaCoursFragment javaCoursFragment = new JavaCoursFragment();
                    holder.bundle = new Bundle();
                    holder.bundle.putInt("position", position);
                    holder.bundle.putString("coursesname", modelCourses.get(position).getCoursesName());
                    javaCoursFragment.setArguments(holder.bundle);
                    holder.activity = (AppCompatActivity) view.getContext();
                    holder.fragmentTransaction = holder.activity.getSupportFragmentManager().beginTransaction().addToBackStack("");
                    holder.fragmentTransaction.replace(R.id.conteyner, javaCoursFragment);
                    holder.fragmentTransaction.commit();
                    break;
                case "Mobile":
                    holder.bundle = new Bundle();
                    holder.bundle.putInt("position", position);
                    holder.bundle.putString("coursesname", modelCourses.get(position).getCoursesName());
                    holder.bundle.putInt("image", modelCourses.get(position).getCoursImagePath());
                    // set Fragmentclass Arguments
                    MobileTrainingsFragment mobileTrainingsFragment = new MobileTrainingsFragment();
                    mobileTrainingsFragment.setArguments(holder.bundle);
                    holder.activity = (AppCompatActivity) view.getContext();
                    holder.fragmentTransaction = holder.activity.getSupportFragmentManager().beginTransaction().addToBackStack("");
                    holder.fragmentTransaction.replace(R.id.conteyner, mobileTrainingsFragment);
                    holder.fragmentTransaction.commit();
                    break;
                case "WEB":
                    holder.bundle = new Bundle();
                    holder.bundle.putInt("position", position);
                    holder.bundle.putString("coursesname", modelCourses.get(position).getCoursesName());
                    holder.bundle.putInt("image", modelCourses.get(position).getCoursImagePath());
                    // set Fragmentclass Arguments
                    WebTrainingsFragment webTrainingsFragment = new WebTrainingsFragment();
                    webTrainingsFragment.setArguments(holder.bundle);
                    holder.activity = (AppCompatActivity) view.getContext();
                    holder.fragmentTransaction = holder.activity.getSupportFragmentManager().beginTransaction().addToBackStack("");
                    holder.fragmentTransaction.replace(R.id.conteyner, webTrainingsFragment);
                    holder.fragmentTransaction.commit();
                    break;
                case "Algorithms":
                    holder.bundle = new Bundle();
                    holder.bundle.putInt("position", position);
                    holder.bundle.putString("coursesname", modelCourses.get(position).getCoursesName());
                    holder.bundle.putInt("image", modelCourses.get(position).getCoursImagePath());
                    // set Fragmentclass Arguments
                    AlgorithmsTrainingsFragment algorithmsTrainingsFragment = new AlgorithmsTrainingsFragment();
                    algorithmsTrainingsFragment.setArguments(holder.bundle);
                    holder.activity = (AppCompatActivity) view.getContext();
                    holder.fragmentTransaction = holder.activity.getSupportFragmentManager().beginTransaction().addToBackStack("");
                    holder.fragmentTransaction.replace(R.id.conteyner, algorithmsTrainingsFragment);
                    holder.fragmentTransaction.commit();
                    break;
                case "Machine Learning":
                    holder.bundle = new Bundle();
                    holder.bundle.putInt("position", position);
                    holder.bundle.putString("coursesname", modelCourses.get(position).getCoursesName());
                    holder.bundle.putInt("image", modelCourses.get(position).getCoursImagePath());
                    // set Fragmentclass Arguments
                    MachineLearningTrainingsFragment machineLearningTrainingsFragment = new MachineLearningTrainingsFragment();
                    machineLearningTrainingsFragment.setArguments(holder.bundle);
                    holder.activity = (AppCompatActivity) view.getContext();
                    holder.fragmentTransaction = holder.activity.getSupportFragmentManager().beginTransaction().addToBackStack("");
                    holder.fragmentTransaction.replace(R.id.conteyner, machineLearningTrainingsFragment);
                    holder.fragmentTransaction.commit();
                    break;
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelCourses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView courseName;
        FragmentTransaction fragmentTransaction;
        Bundle bundle;
        AppCompatActivity activity;
        LinearLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewID);
            courseName = itemView.findViewById(R.id.courseNameID);
            layout = itemView.findViewById(R.id.linearItemID);
        }
    }
}