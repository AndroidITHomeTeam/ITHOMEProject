package com.example.edgarpetrosian.ithome.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.edgarpetrosian.ithome.Activity.ProfileActivity;
import com.example.edgarpetrosian.ithome.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForumFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Context context;
    private FloatingActionButton allPostFab, addPostFab;
    private FragmentTransaction fragmentTransaction;
    private Animation fabOpen, fabClose, fabClockWise, fabAntiClockwise;
    private boolean isOpen = false;

    public ForumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_forum, container, false);
        getID();
        context = getContext();
        fabOpen = AnimationUtils.loadAnimation(context, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(context, R.anim.fab_close);
        fabClockWise = AnimationUtils.loadAnimation(context, R.anim.rotate_clockwise);
        fabAntiClockwise = AnimationUtils.loadAnimation(context, R.anim.rotate_anticlocwise);

        addPostFab.setOnClickListener(this);
        allPostFab.setOnClickListener(this);
        return view;
    }

    public void onResume() {
        super.onResume();

        // Set title bar
        ((ProfileActivity) getActivity())
                .setActionBarTitle("Forum");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addPostFabID:
                if (isOpen) {
                    allPostFab.startAnimation(fabClose);
                    addPostFab.startAnimation(fabAntiClockwise);
                    allPostFab.setClickable(false);
                    isOpen = false;
                    goToAddPostActivity();
                } else {
                    allPostFab.startAnimation(fabOpen);
                    addPostFab.startAnimation(fabClockWise);
                    allPostFab.setClickable(true);
                    isOpen = true;
                }
                break;
            case R.id.allPostFabID:
                goToAllPostActivity();
                break;
        }
    }

    private void goToAddPostActivity() {
        fragmentTransaction = getFragmentManager().beginTransaction().addToBackStack("");
        fragmentTransaction.replace(R.id.forumConteyner, new AddPostActivity());
        fragmentTransaction.commit();
    }

    private void goToAllPostActivity() {
        fragmentTransaction = getFragmentManager().beginTransaction().addToBackStack("");
        fragmentTransaction.replace(R.id.forumConteyner, new AllPostActivity());
        fragmentTransaction.commit();
    }

    private void getID() {
        addPostFab = view.findViewById(R.id.addPostFabID);
        allPostFab = view.findViewById(R.id.allPostFabID);
    }
}
