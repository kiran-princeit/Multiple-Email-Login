package com.hkappstech.adsprosimple;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.google.firebase.FirebaseApp;

public class MainApplication extends Application {
    private static MainApplication mInstance;

    public static synchronized MainApplication getInstance() {
        MainApplication mainApplication;
        synchronized (MainApplication.class) {
            mainApplication = mInstance;
        }
        return mainApplication;
    }

    public void onCreate() {
        super.onCreate();

        mInstance = this;

        FirebaseApp.initializeApp(mInstance);


    }

    public static boolean isNetworkConnected(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


}
