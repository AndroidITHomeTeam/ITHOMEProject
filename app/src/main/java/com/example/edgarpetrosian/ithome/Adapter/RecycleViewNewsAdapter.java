package com.example.edgarpetrosian.ithome.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.edgarpetrosian.ithome.Fragment.NewsDetailFragment;
import com.example.edgarpetrosian.ithome.R;
import com.example.edgarpetrosian.ithome.WebService.model.AppResponseNews;
import com.example.edgarpetrosian.ithome.db_engine.Engine;
import com.example.edgarpetrosian.ithome.db_engine.local_db.Model;

import java.util.LinkedList;
import java.util.List;

public class RecycleViewNewsAdapter extends RecyclerView.Adapter<RecycleViewNewsAdapter.MyViewHolder> {
    private List<AppResponseNews> modelAppResponseNews;
    private Context context;
    private View view;
    private LinkedList<Model> modelListDB;
    private Engine engine;

    public RecycleViewNewsAdapter(LinkedList<Model> modelListDB, List<AppResponseNews> modelAppResponseNews, Context context) {
        this.modelAppResponseNews = modelAppResponseNews;
        this.modelListDB = modelListDB;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new RecycleViewNewsAdapter.MyViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        engine = Engine.getInstance();
        if (modelListDB.size() > 0) {
            for (int i = 0; i < modelListDB.size(); i++) {
                if (modelListDB.get(i).getRecyclerViewPosition() == getItemViewType(position)) {
                    holder.title.setTextColor(Color.RED);
                }
            }
        }
        Glide
                .with(context)
                .load(modelAppResponseNews.get(position).getPostImageUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.imageView);
        holder.title.setText(modelAppResponseNews.get(position).getTitle());
        holder.layout.setOnClickListener(v -> {
            engine = Engine.getInstance();
            long myPosition = position;
            Model model = new Model(myPosition);
            //save color
            engine.getServices(context).save(model);
            holder.bundle = new Bundle();
            holder.bundle.putParcelable("AppResponseNews", modelAppResponseNews.get(position));
            // set Fragmentclass Arguments
            NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
            newsDetailFragment.setArguments(holder.bundle);
            holder.activity = (AppCompatActivity) view.getContext();
            holder.fragmentTransaction = holder.activity.getSupportFragmentManager().beginTransaction().addToBackStack("");
            holder.fragmentTransaction.replace(R.id.conteyner, newsDetailFragment);
            holder.fragmentTransaction.commit();
        });
    }

    @Override
    public int getItemCount() {
        return modelAppResponseNews.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        ImageView imageView;
        TextView title;
        FragmentTransaction fragmentTransaction;
        Bundle bundle;
        AppCompatActivity activity;
        LinearLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBarID);
            imageView = itemView.findViewById(R.id.imageNewsID);
            title = itemView.findViewById(R.id.titleNewsID);
            layout = itemView.findViewById(R.id.linearNewsItemID);
        }
    }
}
