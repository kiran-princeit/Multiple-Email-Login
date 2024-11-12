-flattenpackagehierarchy 'myobfuscatedconsumer'

-keep class org.jetbrains.annotations.** { *; }
-dontwarn org.jetbrains.annotations.**
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keep class com.google.gson.stream.** { *; }
-keepclassmembers public class * extends android.view.View {
    void set*(***);
    *** get*();
}


-keep class com.scottyab.rootbeer.** { *; }
-dontwarn com.scottyab.rootbeer.**


-keep class com.google.gson.annotations.SerializedName{*;}

-keepattributes InnerClasses

-keep class **.R
-keep class **.R$* {
    <fields>;
}


-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

-keep class jorg.joda.time.**{*;}


-mergeinterfacesaggressively
-overloadaggressively
#-repackageclasses 'com.facebook.shimmer'

-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }


# Firebase
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**

# Google Ads
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**
-keep class com.facebook.** { *; }


# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-keep class org.jetbrains.annotations.** { *; }
-keep class org.jsoup.** { *; }
-dontwarn org.jetbrains.annotations.**

-keepattributes Signature
-keepattributes Exceptions

-keep class com.google.gson.stream.** { *; }

-keepattributes InnerClasses, Signature, *Annotation*

-renamesourcefileattribute SourceFile

# Butterknife
#-dontwarn butterknife.internal.**
# Retain generated class which implement Unbinder.
#-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }

# Prevent obfuscation of types which use ButterKnife annotations since the simple name
# is used to reflectively look up the generated ViewBinding.
#-keep class butterknife.*
#-keepclasseswithmembernames class * { @butterknife.* <methods>; }
#-keepclasseswithmembernames class * { @butterknife.* <fields>; }

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer


-keeppackagenames org.jsoup.nodes

-dontwarn okio.**

-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*


-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**
-keep class com.facebook.** { *; }

# Keep Parcelable classes
-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}



# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-keep class org.jetbrains.annotations.** { *; }
-keep class org.jsoup.** { *; }
-dontwarn org.jetbrains.annotations.**


-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keep class com.google.gson.stream.** { *; }

-keepclassmembers class videodownloader.fastvideo.videoplayer.storysaver.photolock** { <fields>; }
-keepclassmembers public class * extends android.view.View {
    void set*(***);
    *** get*();
}

-keep class android.support.v7.widget.** { *; }

-keep public class io.github.aveuiller.** { public *; }

# Mozila Rhino
-keep class javax.script.** { *; }
-keep class com.sun.script.javascript.** { *; }
-keep class org.mozilla.javascript.** { *; }

-printconfiguration /tmp/rhinosampleapp-r8-config.txt

-keepattributes InnerClasses, Signature, *Annotation*

-keep class com.karumi.dexter.** { *; }
-keep interface com.karumi.dexter.** { *; }
-keepclasseswithmembernames class com.karumi.dexter.** { *; }
-keepclasseswithmembernames interface com.karumi.dexter.** { *; }

-renamesourcefileattribute SourceFile

# Butterknife
#-dontwarn butterknife.internal.**
# Retain generated class which implement Unbinder.
#-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }

# Prevent obfuscation of types which use ButterKnife annotations since the simple name
# is used to reflectively look up the generated ViewBinding.
#-keep class butterknife.*
#-keepclasseswithmembernames class * { @butterknife.* <methods>; }
#-keepclasseswithmembernames class * { @butterknife.* <fields>; }

-keeppackagenames org.jsoup.nodes

-dontwarn okio.**
-dontwarn com.airbnb.lottie.**
-keep class com.airbnb.lottie.** {*;}

-keep class uk.co.chrisjenx.calligraphy.* { *; }
-keep class uk.co.chrisjenx.calligraphy.*$* { *; }

-dontwarn okio.**

-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { <fields>; }

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# Retain generic signatures of TypeToken and its subclasses with R8 version 3.0 and higher.
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

# Uncomment for DexGuard only
### OKHTTP

-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }