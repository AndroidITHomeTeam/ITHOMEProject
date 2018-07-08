package com.example.edgarpetrosian.ithome.Mobile.MobileLessonsFragments;


import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edgarpetrosian.AppConstants;
import com.example.edgarpetrosian.ithome.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class JavaDocumentsFragment extends Fragment {
    private View view;
    private Button downloadPDF;
    private DownloadManager downloadManager;
    private Permission permision;
    private DownloadManager.Request request;
    private ImageView toolBarIconBack;
    private Context context;
    private Toolbar toolbar;
    private List data_files;
    private Uri uri;
    private TextView toolBarJavaDocTXT;
//***
    public JavaDocumentsFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void connectPermosstion() {
        permision = new Permission(getContext());
        permision.coonectStoragePermittions();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_java_documents, container, false);
        context = getContext();
        initID();
        connectPermosstion();
        getFile();

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolBarIconBack.setOnClickListener(v -> toolBarIconOnClick());
        toolBarJavaDocTXT.setText("Documentation");
        downloadPDF.setOnClickListener(v -> downloadingPdf(AppConstants.DOWNLOAD_THREAD_PDF_BASE_URL));
        return view;
    }


    private void toolBarIconOnClick() {
        Toast.makeText(context, "Tool Bar Icon OnClick", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void downloadingPdf(String baseUrl) {
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "Download Finished", Toast.LENGTH_SHORT).show();
            }
        };

        String path = String.valueOf(AppConstants.DOWNLOAD_JAVA_FOLDER_PATH);
        File file = new File(path+"/ThreadITHome.pdf");
        /*
        axper hima sax java folderner@ u es thread pdfner@ jnji
         */
        if (!file.exists()) {
            Toast.makeText(context, "Downloading", Toast.LENGTH_SHORT).show();
            uri = Uri.parse(baseUrl);
            request = new DownloadManager.Request(uri);
            request.setDestinationInExternalPublicDir(creatFolder().getPath(), "ThreadITHome.pdf");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.allowScanningByMediaScanner();
            downloadManager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
            getActivity().registerReceiver(receiver, new IntentFilter(downloadManager.ACTION_DOWNLOAD_COMPLETE));
        } else {
            Toast.makeText(context, path + "/n exists", Toast.LENGTH_SHORT).show();
        }
    }

    private void getFile() {
        File pdfFile = new File(AppConstants.DOWNLOAD_JAVA_FOLDER_PATH, "");
        data_files = new ArrayList<>();
        if(pdfFile.exists()) {
            Collections.addAll(data_files, pdfFile.listFiles());
        }
        if (data_files != null) {
            Toast.makeText(context, "" + data_files.size(), Toast.LENGTH_SHORT).show();
        }
    }

    private File creatFolder() {
        File file = new File(AppConstants.DOWNLOAD_JAVA_FOLDER_PATH);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
        return file;
    }
    private void initID(){
        toolbar = view.findViewById(R.id.javaDocToolbar);
        toolBarIconBack = view.findViewById(R.id.javaDoctoolbarBackID);
        toolBarJavaDocTXT = view.findViewById(R.id.javaDocTextID);
        downloadPDF = view.findViewById(R.id.downloadBtnID);

    }

}
