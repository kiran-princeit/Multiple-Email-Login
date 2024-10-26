package com.example.multipleemaillogin.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.multipleemaillogin.model.EmailData;

import java.util.List;

@Dao
public interface EmailDao {
    @Insert
    void insert(EmailData emailData);

    @Query("SELECT * FROM emails")
    List<EmailData> getAllEmails();



    @Query("SELECT * FROM emails WHERE isChecked = 1")
    List<EmailData> getAllSelectedEmails();

    @Update
    void updateEmail(EmailData email);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<EmailData> emails);

    @Query("UPDATE emails SET isChecked = :isSelected WHERE id = :id")
    void updateEmailSelection(int id, boolean isSelected);


}