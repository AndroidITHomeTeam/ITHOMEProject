package com.example.edgarpetrosian;

import android.os.Environment;

public interface AppConstants {
    //String DOWNLOAD_JAVA_FOLDER_PATH="/storage/emulated/0/Download/Java";
   // String DOWNLOAD_JAVA_FOLDER_PA/*TH="/storage/emulated/0/storage/emulated/0/Download/Java";
    /*
    /storage/emulated/0/
     */
    String FIRST_DIRECTORY_PATH="/storage/emulated/0/";
    String DOWNLOAD_JAVA_FOLDER_PATH= FIRST_DIRECTORY_PATH+Environment.DIRECTORY_DOCUMENTS+"/Java";
    /*
     jnjecir? ha lriv jnjaca                                /storage/emulated/0/storage/emulated/0/Download/Java/ThreadITHome.pdf
     */
    String DOWNLOAD_THREAD_PDF_BASE_URL="http://distance-learning.ga/ithome/pdf/Threads.pdf";
}
