package com.example.multipleemaillogin.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "selected_emails")
public class SelectedEmail {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int emailId;

    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private int color;

    public SelectedEmail(int emailId) {
        this.emailId = emailId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public int getEmailId() {
        return emailId;
    }

    public void setId(int id) {
        this.id = id;
    }
}
