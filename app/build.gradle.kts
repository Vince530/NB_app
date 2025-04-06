plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.nearbychat"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.nearbychat"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation("com.amap.api:map2d:5.2.0") // 高德2D地图
    implementation("com.amap.api:map3d:6.8.0") // 高德3D地图 SDK
    implementation("com.amap.api:location:6.1.0") // 高德定位
    implementation("androidx.appcompat:appcompat:1.6.1")  // AppCompat
    implementation("androidx.fragment:fragment-ktx:1.6.2") // Fragment 扩展
    implementation("com.google.firebase:firebase-firestore-ktx:24.9.0") // Firebase Firestore
    implementation ("com.google.firebase:firebase-database-ktx")
    implementation ("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-auth-ktx:22.2.0") // Firebase Auth
    implementation("androidx.recyclerview:recyclerview:1.3.2") // RecyclerView
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2") // 处理数据
    implementation("com.google.android.material:material:1.9.0") // MaterialComponents 主题支持
    implementation(platform("com.google.firebase:firebase-bom:33.10.0"))
    // 表情支持库
    implementation ("com.vanniktech:emoji-google:0.8.0")
    implementation ("androidx.emoji2:emoji2:1.3.0")
    implementation ("androidx.emoji2:emoji2-views:1.3.0")
    implementation ("androidx.emoji2:emoji2-views-helper:1.3.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}