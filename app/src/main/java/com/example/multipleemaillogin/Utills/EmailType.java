package com.example.multipleemaillogin.Utills;

import java.util.Arrays;
import java.util.List;

import kotlin.Metadata;

@Metadata
public enum EmailType {
    GMAIL(0),
    YAHOO(1),
    OUTLOOK(2),
    AOL(3),
    GODADDY(4),
    T_ONLINE(5),
    FACEBOOK(6),
    INBOX(7),
    INSTAGRAM(8),
    LINKEDIN(9),
    ONEMAIL(10),
    PROTON(11),
    SKYPE(12),
    SLACK(13),
    SNAPCHAT(14),
    TELEGRAM(15),
    TWITTER(16),
    WHATSAPP(17),
    YANDEX(18),
    ZOHO(19),
    ZOOM(20);

    private final int id;

    // Private constructor for enum
    EmailType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // Optional static fields if needed
    public static final EmailType[] emailTypes = values();  // Array of all email types
    public static final List<EmailType> emailTypeList = Arrays.asList(emailTypes);  // List of all email types
}

