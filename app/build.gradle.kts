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
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }



//    buildTypes {
//        debug {
//            isMinifyEnabled =false
//            isShrinkResources =false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//        release {
//            isMinifyEnabled =true
//            isShrinkResources =true
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    implementation ("com.intuit.sdp:sdp-android:1.1.1")
    implementation ("com.intuit.ssp:ssp-android:1.1.1")

    implementation ("android.arch.persistence.room:runtime:1.0.0-alpha1")
    annotationProcessor ("android.arch.persistence.room:compiler:1.0.0-alpha1")
    implementation ("com.airbnb.android:lottie:3.4.0")
    implementation ("com.github.ybq:Android-SpinKit:1.4.0")


    implementation ("androidx.multidex:multidex:2.0.1")

//    Ads

    implementation ("com.google.android.gms:play-services-ads:23.5.0")


    // Firebase SDK

//    implementation("com.google.firebase:firebase-analytics:22.1.2")
//    implementation("com.google.firebase:firebase-crashlytics:19.2.1")
    implementation ("com.facebook.shimmer:shimmer:0.5.0")

    implementation (platform("com.google.firebase:firebase-bom:32.1.1"))
    implementation ("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-config")
    implementation ("com.google.firebase:firebase-core:21.1.1")
    implementation ("com.google.firebase:firebase-crashlytics:19.2.1")

//    implementation ("com.google.code.gson:gson:2.10.1")


    implementation(project(path = ":adsprosimple"))

}