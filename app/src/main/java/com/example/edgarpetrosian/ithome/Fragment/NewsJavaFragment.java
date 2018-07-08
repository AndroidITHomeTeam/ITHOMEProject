package com.example.edgarpetrosian.ithome.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edgarpetrosian.ithome.Activity.ProfileActivity;
import com.example.edgarpetrosian.ithome.Adapter.RecycleViewNewsAdapter;
import com.example.edgarpetrosian.ithome.AppAplication;
import com.example.edgarpetrosian.ithome.R;
import com.example.edgarpetrosian.ithome.db_engine.Engine;
import com.example.edgarpetrosian.ithome.WebService.model.AppResponseNews;
import com.example.edgarpetrosian.ithome.db_engine.local_db.Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsJavaFragment extends Fragment {

    private View view;
    private Context context;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private List<AppResponseNews> models = new ArrayList<>();
    private RecycleViewNewsAdapter adapter;
    private Engine engine;
    private Toolbar toolbar;
    private TextView toolBarText;
    private ImageView toolBarIconBack;


    public NewsJavaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);
        context = getContext();
        init();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolBarText.setOnClickListener(v->{toolbarTextOnClick(); });
        toolBarIconBack.setOnClickListener(v->toolBarIconOnClick());

        AppAplication
                .appAplication
                .getNetworkServiceNews()
                .getNewsDate()
                .enqueue(new Callback<List<AppResponseNews>>() {
                    @Override
                    public void onResponse(Call<List<AppResponseNews>> call, Response<List<AppResponseNews>> response) {
                        if (response.body() != null) {
                            getResponseData(response.body());
                            models = getResponseData(response.body());
                            setDataRecylcerView(context, models);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<AppResponseNews>> call, Throwable t) {
                        t.getMessage();
                    }
                });
        return view;

    }

    public void onResume() {
        super.onResume();

        // Set title bar
        ((ProfileActivity) getActivity())
                .setActionBarTitle("News");

    }

    public void init() {
        recyclerView = view.findViewById(R.id.recycleNewsID);
        toolbar=(Toolbar) view.findViewById(R.id.my_toolbarNews);
        toolBarIconBack=toolbar.findViewById(R.id.toolbarBackID);
        toolBarText=toolbar.findViewById(R.id.toolbar_titleNewsID);
    }

    private void setDataRecylcerView(Context context, List<AppResponseNews> responseNews) {
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecycleViewNewsAdapter(getListFromDB(), responseNews, context);
        recyclerView.setAdapter(adapter);
    }

    private List<AppResponseNews> getResponseData(List<AppResponseNews> reponse) {
        List<AppResponseNews> getResponse = new ArrayList<>();
        Engine.getInstance().setAppResponseNewsModel(reponse);
        getResponse.addAll(Engine.getInstance().getAppResponseNewsModel());
        return getResponse;
    }


    private LinkedList<Model> getListFromDB() {
        engine = Engine.getInstance();
        LinkedList<Model> models = new LinkedList<>();
        if (engine.getServices(context).getAlldata("") != null) {
            models.addAll(engine.getServices(context).getAlldata(""));
        }
        return models;
    }
    private void toolBarIconOnClick() {
        Toast.makeText(context, "Tool Bar Icon OnClick", Toast.LENGTH_SHORT).show();
    }

    private void toolbarTextOnClick() {
        Toast.makeText(context, "ToolBar Text", Toast.LENGTH_SHORT).show();
    }

}
