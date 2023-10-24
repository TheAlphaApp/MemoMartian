
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.gradlePlugin)
    alias(libs.plugins.devtools.ksp)
}

android {
    namespace = "com.dzdexon.memomartian"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dzdexon.memomartian"
        minSdk = 26
        targetSdk = 34
        versionCode = 3
        versionName = "0.0.3"


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    // Default
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.bundles.compose)
//    implementation("androidx.compose.ui:ui-graphics")

    //Nav
    implementation(libs.androidx.navigation.compose)
    //Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    //Gson
    implementation(libs.gson)

    //coil
    implementation(libs.coil)

    ///Test
    testImplementation(libs.junit4)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.bundles.compose)
    debugImplementation(libs.bundles.compose)

}