plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.info.multiple.email.onplace.login"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.info.multiple.email.onplace.login"
        minSdk = 21
        targetSdk = 34
        versionCode = 8
        versionName = "1.7"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("D:\\Multiple Email Login\\app\\multipleemaillogin.jks")
            storePassword = "Prince@9313"
            keyAlias = "key0"
            keyPassword = "Prince@9313"
        }
    }

    buildTypes {
        getByName("debug") {
            manifestPlaceholders["crashlyticsCollectionEnabled"] = "false"
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            manifestPlaceholders["crashlyticsCollectionEnabled"] = "true"
            ndk {
                debugSymbolLevel = "FULL"
            }
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    ndkVersion = "28.0.12674087 rc2"

}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    implementation("com.intuit.sdp:sdp-android:1.1.1")
    implementation("com.intuit.ssp:ssp-android:1.1.1")
    implementation("com.github.ybq:Android-SpinKit:1.4.0")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("com.github.bumptech.glide:glide:4.9.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.9.0")

//    Ads
    implementation("com.google.android.gms:play-services-ads:23.5.0")
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    // Firebase SDK
    implementation(platform("com.google.firebase:firebase-bom:32.1.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-config")
    implementation("com.google.firebase:firebase-core:21.1.1")
    implementation("com.google.firebase:firebase-crashlytics:19.2.1")
    implementation("com.google.android.ump:user-messaging-platform:3.1.0")
    implementation("android.arch.lifecycle:extensions:1.1.1")

    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.google.android.ads.consent:consent-library:1.0.8")
//facebook ads
    implementation("com.facebook.android:audience-network-sdk:6.12.0")
    implementation("com.google.ads.mediation:facebook:6.12.0.0")

}