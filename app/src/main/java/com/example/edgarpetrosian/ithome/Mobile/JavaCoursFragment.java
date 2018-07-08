package com.example.edgarpetrosian.ithome.Mobile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.edgarpetrosian.ithome.Activity.ProfileActivity;
import com.example.edgarpetrosian.ithome.Fragment.JavaTestsFragment;
import com.example.edgarpetrosian.ithome.Mobile.MobileLessonsFragments.JavaDocumentsFragment;
import com.example.edgarpetrosian.ithome.Mobile.MobileLessonsFragments.JavaVideoTutorialFragment;
import com.example.edgarpetrosian.ithome.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class JavaCoursFragment extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout linearDocument, linearVideo, linearTests;
    private FragmentTransaction fragmentTransaction;
    private Bundle bundle;
    private AppCompatActivity activity;
    private TextView javaTestText;

    public JavaCoursFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_java_cours, container, false);
        getID();
        linearDocument.setOnClickListener(this);
        linearVideo.setOnClickListener(this);
        linearTests.setOnClickListener(this);
        return view;
    }

    public void onResume() {
        super.onResume();
        // Set title bar
        ((ProfileActivity) getActivity())
                .setActionBarTitle("Java Courses");
    }

    private void getID() {
        linearDocument = view.findViewById(R.id.linearDocumentID);
        linearVideo = view.findViewById(R.id.linearVideoID);
        linearTests = view.findViewById(R.id.linearTestID);
        javaTestText = view.findViewById(R.id.javaTestTextViewID);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearDocumentID:
                fragmentTransaction = getFragmentManager().beginTransaction().addToBackStack("");
                fragmentTransaction.replace(R.id.conteyner, new JavaDocumentsFragment());
                fragmentTransaction.commit();
                break;
            case R.id.linearVideoID:
                fragmentTransaction = getFragmentManager().beginTransaction().addToBackStack("");
                fragmentTransaction.replace(R.id.conteyner, new JavaVideoTutorialFragment());
                fragmentTransaction.commit();
                break;
            case R.id.linearTestID:
                bundle = new Bundle();
                bundle.putString("tests", javaTestText.getText().toString());
                JavaTestsFragment javaTestsFragment = new JavaTestsFragment();
                javaTestsFragment.setArguments(bundle);
                activity = (AppCompatActivity) view.getContext();
                fragmentTransaction = activity.getSupportFragmentManager().beginTransaction().addToBackStack("");
                fragmentTransaction.replace(R.id.conteyner, javaTestsFragment);
                fragmentTransaction.commit();
                break;
        }
    }
}
