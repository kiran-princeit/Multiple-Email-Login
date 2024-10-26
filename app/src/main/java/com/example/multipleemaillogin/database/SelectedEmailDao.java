package com.example.multipleemaillogin.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SelectedEmailDao {
    @Insert
    void insert(SelectedEmail selectedEmail);

    @Query("DELETE FROM selected_emails WHERE emailId = :emailId")
    void deleteByEmailId(int emailId);

    @Query("SELECT * FROM selected_emails")
    List<SelectedEmail> getAllSelectedEmails();
}