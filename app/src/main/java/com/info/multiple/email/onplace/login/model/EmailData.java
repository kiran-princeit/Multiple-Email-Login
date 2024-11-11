package com.info.multiple.email.onplace.login.model;

import androidx.annotation.ColorInt;
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
    private String emailType;


    public EmailData(int id, @ColorInt int color, String name, String url, String emailType) {
        this.id = id;
        this.color = color;
        this.name = name;
        this.url = url;
        this.emailType = emailType;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getUrl() { return url; }
    public int getColor() { return color; }
    public boolean isChecked() { return isChecked; }
    public void setChecked(boolean checked) { isChecked = checked; }
    public String getEmailType() { return emailType; }
    public void setName(String name) {
        this.name = name;
    }


//    @Override
//    public String toString() {
//        return "EmailData{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", url='" + url + '\'' +
//                ", color=" + color +
//                ", isChecked=" + isChecked +
//                ", emailType=" + emailType +
//                '}';
//    }
}