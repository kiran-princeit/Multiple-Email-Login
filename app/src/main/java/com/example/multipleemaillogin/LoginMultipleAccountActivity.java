package com.example.multipleemaillogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.multipleemaillogin.model.EmailData;

import java.util.HashSet;
import java.util.Set;

public class LoginMultipleAccountActivity extends AppCompatActivity {


    TextView btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_multiple_account);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view -> {
            showDialog();
        });
    }


    public void showDialog() {


        final Dialog dialog = new Dialog(LoginMultipleAccountActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_remove);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Width match parent
        dialog.getWindow().setAttributes(layoutParams);


        TextView btnCancel = (TextView) dialog.findViewById(R.id.btnCancel);
        TextView btnPositive = (TextView) dialog.findViewById(R.id.btnPositive);
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.cancel();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}