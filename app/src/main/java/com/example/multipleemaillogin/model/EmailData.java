package com.example.multipleemaillogin.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "emails")
public class EmailData{
    @PrimaryKey
    private int id;
    private String name;
    private String url;
    private int color;
    private boolean isChecked = false;
    public EmailData(int id, int color, String name, String url) {
        this.id = id;
        this.color = color;
        this.name = name;
        this.url = url;
    }
    public boolean isChecked() {
        return isChecked;
    }
    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getUrl() {
        return url;
    }

    public int getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "EmailData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", color=" + color +
                ", isChecked=" + isChecked +
                '}';
    }


}