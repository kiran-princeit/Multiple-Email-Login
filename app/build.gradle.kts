plugins {
    id("com.android.application")
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


}