package com.example.multipleemaillogin.Utills;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.example.multipleemaillogin.R;
import com.example.multipleemaillogin.model.EmailData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

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

        string = context.getString(R.string.facebook);
        arrayList.add(new EmailData(7, ContextCompat.getColor(context, R.color.facebook), string, "https://www.facebook.com/login/",string));

        string = context.getString(R.string.gmail);
        arrayList.add(new EmailData(1, ContextCompat.getColor(context, R.color.gmail), string, "https://mail.google.com/mail/u/0/#inbox",string));

        string = context.getString(R.string.godaddy);
        arrayList.add(new EmailData(5, ContextCompat.getColor(context, R.color.godaddy), string, "https://account.godaddy.com/products",string));

        string = context.getString(R.string.inbox);
        arrayList.add(new EmailData(8, ContextCompat.getColor(context, R.color.inbox), string, "https://email.inbox.lv/mailbox?mailbox=INBOX",string));

        string = context.getString(R.string.instagram);
        arrayList.add(new EmailData(9, ContextCompat.getColor(context, R.color.instagram), string, "https://www.instagram.com/accounts/login/?hl=en",string));

        string = context.getString(R.string.linkedin);
        arrayList.add(new EmailData(10, ContextCompat.getColor(context, R.color.linkedin), string, "https://www.linkedin.com/login",string));

        string = context.getString(R.string.onemail);
        arrayList.add(new EmailData(11, ContextCompat.getColor(context, R.color.onemail), string, "https://login.one.com/mail",string));

        string = context.getString(R.string.outlook);
        arrayList.add(new EmailData(3, ContextCompat.getColor(context, R.color.outlook), string, "https://outlook.live.com/mail/0/",string));

        string = context.getString(R.string.proton);
        arrayList.add(new EmailData(12, ContextCompat.getColor(context, R.color.proton), string, "https://account.proton.me/login",string));

        string = context.getString(R.string.skype);
        arrayList.add(new EmailData(13, ContextCompat.getColor(context, R.color.skype), string, "https://web.skype.com/?openPstnPage=true",string));

        string = context.getString(R.string.slack);
        arrayList.add(new EmailData(14, ContextCompat.getColor(context, R.color.slack), string, "https://slack.com/signin#/signin",string));

        string = context.getString(R.string.snapchat);
        arrayList.add(new EmailData(15, ContextCompat.getColor(context, R.color.snapchat), string, "https://web.snapchat.com/chat",string));

        string = context.getString(R.string.tOnline);
        arrayList.add(new EmailData(6, ContextCompat.getColor(context, R.color.tOnline), string, "https://email.t-online.de",string));

        string = context.getString(R.string.telegram);
        arrayList.add(new EmailData(16, ContextCompat.getColor(context, R.color.telegram), string, "https://web.telegram.org",string));

        string = context.getString(R.string.twitter);
        arrayList.add(new EmailData(17, ContextCompat.getColor(context, R.color.twitter), string, "https://twitter.com/messages",string));

        string = context.getString(R.string.whatsapp);
        arrayList.add(new EmailData(18, ContextCompat.getColor(context, R.color.whatsapp), string, "https://web.whatsapp.com/",string));

        string = context.getString(R.string.yahoo);
        arrayList.add(new EmailData(2, ContextCompat.getColor(context, R.color.yahoo), string, "https://mail.yahoo.com/d/folders/1?reason=onboarded",string));

        string = context.getString(R.string.yendex);
        arrayList.add(new EmailData(19, ContextCompat.getColor(context, R.color.yendex), string, "https://passport.yandex.com",string));

        string = context.getString(R.string.zoho);
        arrayList.add(new EmailData(20, ContextCompat.getColor(context, R.color.zoho), string, "https://accounts.zoho.com",string));

        string = context.getString(R.string.zoom);
        arrayList.add(new EmailData(21, ContextCompat.getColor(context, R.color.zoom), string, "https://zoom.us/signin",string));

        return arrayList;
    }


//    public static final List b(Context context) {
//        Intrinsics.checkNotNull(context, "<this>");
//        ArrayList arrayList = new ArrayList(ContextKt.c(context).b());
//        ArrayList arrayList2 = new ArrayList(a(context));
//        arrayList2.removeIf(new C0946m1(new C0945l1(arrayList)));
//        ArrayList arrayList3 = new ArrayList(CollectionsKt.p(arrayList, 10));
//        Iterator it = arrayList.iterator();
//        while (it.hasNext()) {
//            String str = (String) it.next();
//            Intrinsics.checkNotNull(str);
//            arrayList3.add(Integer.valueOf(Integer.parseInt(str)));
//        }
//        return CollectionsKt.V(new C0153d4(new C0947n1(arrayList3), 1), arrayList2);
//    }
//    public final /* synthetic */ class C0946m1 implements Predicate {
//
//        /* renamed from: a  reason: collision with root package name */
//        public final /* synthetic */ Function1 f6592a;
//
//        public /* synthetic */ C0946m1(C0945l1 l1Var) {
//            this.f6592a = l1Var;
//        }
//
//        public final boolean test(Object obj) {
//            return ((Boolean) this.f6592a.invoke(obj)).booleanValue();
//        }
//    }


    public final /* synthetic */ class C0945l1 implements Function1 {
        public final /* synthetic */ ArrayList b;

        public /* synthetic */ C0945l1(ArrayList arrayList) {
            this.b = arrayList;
        }

        public final Object invoke(Object obj) {
            return Boolean.valueOf(!this.b.contains(String.valueOf(((EmailData) obj).getName())));
        }
    }

//    public final /* synthetic */ class C0153d4 implements Comparator {
//        public final /* synthetic */ int b;
//        public final /* synthetic */ Object c;
//
//        public /* synthetic */ C0153d4(Object obj, int i) {
//            this.b = i;
//            this.c = obj;
//        }
//
//        public final int compare(Object obj, Object obj2) {
//            int i = this.b;
//            Object obj3 = this.c;
//              Object savedSelectionState;
//            switch (i) {
//                case 0:
//
//
////                    SaverKt saverKt$Saver$1 = SelectionRegistrarImpl.m;
//
////                    SaverKt saverKt$Saver$1 = SelectionRegistrarImpl.class();
//
//                    return ((Number) ((Function2) obj3).invoke(obj, obj2)).intValue();
//                case 1:
//                    return ((Number) ((Function2) obj3).invoke(obj, obj2)).intValue();
//                default:
//                    return Cf.a((Cf) obj3, (Cf) obj, (Cf) obj2);
//            }
//        }
//    }
//
//    public final /* synthetic */ class C0947n1 implements Function2 {
//        public final /* synthetic */ List b;
//
//        public /* synthetic */ C0947n1(ArrayList arrayList) {
//            this.b = arrayList;
//        }
//
//        public final Object invoke(Object obj, Object obj2) {
//            Integer valueOf = Integer.valueOf(((EmailData) obj).a);
//            List list = this.b;
//            return Integer.valueOf(Intrinsics.compare(list.indexOf(valueOf), list.indexOf(Integer.valueOf(((EmailData) obj2).a))));
//        }
//    }

}
