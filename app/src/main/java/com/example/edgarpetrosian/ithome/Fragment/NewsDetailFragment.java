package com.example.edgarpetrosian.ithome.Fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.edgarpetrosian.ithome.R;
import com.example.edgarpetrosian.ithome.WebService.model.AppResponseNews;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsDetailFragment extends Fragment {

    private View view;
    private Context context;

    private ImageView imageView, back, videoBtn;
    private TextView newsDescription, newsDetailText;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

    private Bundle getAppResponsNewsObject;

    private String videoUrl;

    private FragmentTransaction fragmentTransaction;


    public NewsDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news_detail, container, false);

        context = getContext();
        initID();
        // hide Action Bar
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        // back All News Fragment
        back.setOnClickListener(v -> {
            replaceFragment(new NewsJavaFragment());
        });

        // get Object From Bundle
        getAppResponsNewsObject = getArguments();
        getNewsModel(getAppResponsNewsObject);

        // check video URL
        videoUrl = getNewsModel(getAppResponsNewsObject).getPostVideoUrl();
        isHaveVideoURL(videoBtn, videoUrl);
        if (isHaveVideoURL(videoBtn, videoUrl)) {
            videoBtn.setOnClickListener(v -> {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
                    startActivity(intent);
            });
        }
        return view;
    }


    private AppResponseNews getNewsModel(Bundle bundle) {
        AppResponseNews appResponseNews = null;
        if (bundle != null) {
            appResponseNews = bundle.getParcelable("AppResponseNews");
            if (appResponseNews != null) {
                Glide
                        .with(context)
                        .load(appResponseNews.getPostImageUrl())
                        .into(imageView);
                collapsingToolbarLayout.setTitleEnabled(true);
                collapsingToolbarLayout.setTitle(appResponseNews.getTitle());
                newsDescription.setText(appResponseNews.getDescription());
                newsDetailText.setText(appResponseNews.getDetailText());
            }
        }
        return appResponseNews;
    }

    private void initID() {
        newsDescription = view.findViewById(R.id.newsDescriptionID);
        newsDetailText = view.findViewById(R.id.newsDetailsTextID);
        appBarLayout = view.findViewById(R.id.appBarID);
        imageView = view.findViewById(R.id.newsImageDetailsID);
        collapsingToolbarLayout = view.findViewById(R.id.colapsToolID);
        toolbar = view.findViewById(R.id.toolbarID);
        videoBtn = view.findViewById(R.id.newsVideoBtnID);
        back = view.findViewById(R.id.backID);
    }

    private boolean isHaveVideoURL(View view, String videoURL) {
        boolean b;
        if (!videoURL.equals("")) {
            view.setVisibility(View.VISIBLE);
            b = true;
        } else {
            view.setVisibility(View.GONE);
            b = false;
        }
        return b;
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

}
