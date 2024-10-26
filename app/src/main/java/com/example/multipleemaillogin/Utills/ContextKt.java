package com.example.multipleemaillogin.Utills;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.example.multipleemaillogin.R;


import java.util.Locale;

public final class ContextKt {

    public static final boolean a(Context context) {
        if (context == null) throw new NullPointerException("context cannot be null");

        if (Build.VERSION.SDK_INT >= 26) {
            int checkSelfPermission1 = ContextCompat.checkSelfPermission(context, "android.permission.ANSWER_PHONE_CALLS");
            int checkSelfPermission2 = ContextCompat.checkSelfPermission(context, "android.permission.CALL_PHONE");
            int checkSelfPermission3 = ContextCompat.checkSelfPermission(context, "android.permission.READ_PHONE_STATE");

            return checkSelfPermission1 == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission2 == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission3 == PackageManager.PERMISSION_GRANTED;
        } else {
            int checkSelfPermission4 = ContextCompat.checkSelfPermission(context, "android.permission.CALL_PHONE");
            int checkSelfPermission5 = ContextCompat.checkSelfPermission(context, "android.permission.READ_PHONE_STATE");

            return checkSelfPermission4 == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission5 == PackageManager.PERMISSION_GRANTED;
        }
    }

    public static final void b(Context context, float rating) {
        if (context == null) throw new NullPointerException("context cannot be null");

        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (packageInfo == null) return;

        String versionName = packageInfo.versionName;
        String deviceName = Build.MANUFACTURER + " " + Build.MODEL;
        String androidVersion = Build.VERSION.RELEASE;
        int sdkVersion = Build.VERSION.SDK_INT;
        String country = "";

        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            country = new Locale("", telephonyManager.getNetworkCountryIso()).getDisplayCountry();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String feedback = "Rating :" + (rating != -1.0f ? rating : "") +
                "\nDevice Information - " + context.getResources().getString(R.string.app_name) +
                "\nVersion : " + versionName +
                "\nDevice Name : " + deviceName +
                "\nAndroid API : " + sdkVersion +
                "\nAndroid Version : " + androidVersion +
                "\nCountry : " + country;

        String subject = "Feedback/Suggestion " + context.getResources().getString(R.string.app_name);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:xphotoframeeditor@gmail.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, feedback);

        try {
            context.startActivity(Intent.createChooser(emailIntent, context.getString(Integer.parseInt("R.string.email_choose_from_client"))));
        } catch (ActivityNotFoundException ignored) {
        }
    }

    public static final AppPreferences c(Context context) {
        if (context == null) throw new NullPointerException("context cannot be null");
        return new AppPreferences(context);
    }

//    public static final EmailDao d(Context context) {
//        if (context == null) throw new NullPointerException("context cannot be null");
//        EmailDatabase emailDatabase = EmailDatabase.f4788a;
//
//        if (emailDatabase == null) {
//            synchronized (EmailDatabase.class) {
//                if (EmailDatabase.f4788a == null) {
//                    EmailDatabase.f4788a = Room.databaseBuilder(
//                            context.getApplicationContext(),
//                            EmailDatabase.class,
//                            "EmailProcess.db"
//                    ).build();
//                }
//            }
//        }
//
//        return EmailDatabase.f4788a.a();
//    }

    public static final boolean e(Context context) {
        if (context == null) throw new NullPointerException("context cannot be null");

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());

        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
    }

    public static final void f(Context context) {
        if (context == null) throw new NullPointerException("context cannot be null");

        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        context.startActivity(intent);
    }

//    public static final void g(BaseActivity baseActivity) {
//        if (baseActivity == null) throw new NullPointerException("baseActivity cannot be null");
//
//        try {
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.multiple.email.login"));
//            baseActivity.startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public static final void h(Context context, int i, String str) {
//        if (context == null) throw new NullPointerException("context cannot be null");
//
//        if (Looper.myLooper() == Looper.getMainLooper()) {
//            MyConstantsKt.showMessage(context, i, str);
//        } else {
//            new Handler(Looper.getMainLooper()).post(() -> MyConstantsKt.showMessage(context, i, str));
//        }
//    }

//    public static void i(BaseActivity baseActivity, int i) {
//        if (baseActivity == null) throw new NullPointerException("baseActivity cannot be null");
//
//        String message = baseActivity.getString(i);
//        h(baseActivity, 0, message);
//    }
}

