package com.info.multiple.email.onplace.login.Utills;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.info.multiple.email.onplace.login.R;
import com.info.multiple.email.onplace.login.model.EmailData;

import java.util.ArrayList;

import kotlin.Metadata;

@Metadata

public final class EmailManager {
    public static  String PREFS_NAME = "EmailPrefs";
    public static  String SELECTED_EMAILS_KEY = "selectedEmails";
    public static ArrayList<EmailData> getEmailData(Context context) {

        if (context == null) {
            throw new NullPointerException("Context cannot be null");
        }

        ArrayList<EmailData> arrayList = new ArrayList<>();

        String string;
        string = context.getString(R.string.aol);
        arrayList.add(new EmailData(4, ContextCompat.getColor(context, R.color.gmail), string, "https://mail.aol.com/",string));

        String string2 = context.getString(R.string.facebook);
        arrayList.add(new EmailData(7, ContextCompat.getColor(context, R.color.facebook), string2, "https://www.facebook.com/login/",string2));

        String string3 = context.getString(R.string.gmail);
        arrayList.add(new EmailData(1, ContextCompat.getColor(context, R.color.gmail), string3, "https://mail.google.com/mail/u/0/#inbox",string3));

        String string4 = context.getString(R.string.godaddy);
        arrayList.add(new EmailData(5, ContextCompat.getColor(context, R.color.godaddy), string4, "https://account.godaddy.com/products",string4));

        String string5 = context.getString(R.string.inbox);
        arrayList.add(new EmailData(8, ContextCompat.getColor(context, R.color.inbox), string5, "https://email.inbox.lv/mailbox?mailbox=INBOX",string5));

        String string6 = context.getString(R.string.instagram);
        arrayList.add(new EmailData(9, ContextCompat.getColor(context, R.color.instagram), string6, "https://www.instagram.com/accounts/login/?hl=en",string6));

        String string7 = context.getString(R.string.linkedin);
        arrayList.add(new EmailData(10, ContextCompat.getColor(context, R.color.linkedin), string7, "https://www.linkedin.com/login",string7));

        String  string8 = context.getString(R.string.onemail);
        arrayList.add(new EmailData(11, ContextCompat.getColor(context, R.color.onemail), string8, "https://login.one.com/mail",string8));

        String string9 = context.getString(R.string.outlook);
        arrayList.add(new EmailData(3, ContextCompat.getColor(context, R.color.outlook), string9, "https://outlook.live.com/mail/0/",string9));

        String string10 = context.getString(R.string.proton);
        arrayList.add(new EmailData(12, ContextCompat.getColor(context, R.color.proton), string10, "https://account.proton.me/login",string10));

        String string11 = context.getString(R.string.skype);
        arrayList.add(new EmailData(13, ContextCompat.getColor(context, R.color.skype), string11, "https://web.skype.com/?openPstnPage=true",string11));

        String string12 = context.getString(R.string.slack);
        arrayList.add(new EmailData(14, ContextCompat.getColor(context, R.color.slack), string12, "https://slack.com/signin#/signin",string12));

        String string13 = context.getString(R.string.snapchat);
        arrayList.add(new EmailData(15, ContextCompat.getColor(context, R.color.snapchat), string13, "https://web.snapchat.com/chat",string13));

        String string14 = context.getString(R.string.tOnline);
        arrayList.add(new EmailData(6, ContextCompat.getColor(context, R.color.tOnline), string14, "https://email.t-online.de",string14));

        String string15 = context.getString(R.string.telegram);
        arrayList.add(new EmailData(16, ContextCompat.getColor(context, R.color.telegram), string15, "https://web.telegram.org",string15));

        String string16 = context.getString(R.string.twitter);
        arrayList.add(new EmailData(17, ContextCompat.getColor(context, R.color.twitter), string16, "https://twitter.com/messages",string16));

        String string17 = context.getString(R.string.whatsapp);
//        arrayList.add(new EmailData(18, ContextCompat.getColor(context, R.color.whatsapp), string, "https://web.whatsapp.com/",string));
//        arrayList.add(new EmailData(18, ContextCompat.getColor(context, R.color.whatsapp), string17, "https://web.whatsapp.com/🌐/en",string17));
            arrayList.add(new EmailData(18, ContextCompat.getColor(context, R.color.whatsapp), string17, "https://web.whatsapp.com/🌐/en",string17));

        String string18 = context.getString(R.string.yahoo);
        arrayList.add(new EmailData(2, ContextCompat.getColor(context, R.color.yahoo), string18, "https://mail.yahoo.com/d/folders/1?reason=onboarded",string18));

        String  string19 = context.getString(R.string.yendex);
        arrayList.add(new EmailData(19, ContextCompat.getColor(context, R.color.yendex), string19, "https://passport.yandex.com",string19));

        String string20 = context.getString(R.string.zoho);
        arrayList.add(new EmailData(20, ContextCompat.getColor(context, R.color.zoho), string20, "https://accounts.zoho.com",string20));

        String string21 = context.getString(R.string.zoom);
        arrayList.add(new EmailData(21, ContextCompat.getColor(context, R.color.zoom), string21, "https://zoom.us/signin",string21));

        return arrayList;
    }
}
