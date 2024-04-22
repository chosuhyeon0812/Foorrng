@file:Suppress("DSL_SCOPE_VIOLATION")
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kapt)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.googleservice)
}

fun getProperty(propertyKey: String): String = gradleLocalProperties(rootDir).getProperty(propertyKey)

android {
    namespace = "com.tasteguys.foorrng_customer"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.tasteguys.foorrng_customer"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "NAVER_MAP_CLIENT_ID", getProperty("naverMapClientID"))
        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", getProperty("kakao_app_key_cus"))
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures{
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(":presentation"))
    implementation(project(":domain"))
    implementation(project(":data"))

    // Android
    implementation(libs.bundles.androidx)
    implementation(project(mapOf("path" to ":util:retrofit_adapter")))
    testImplementation(libs.bundles.testing)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    // Retrofit
    implementation(libs.bundles.retrofit)

    // Firebase
    implementation(platform(libs.firebase.bom))

    // Naver Map
    implementation(libs.naver.mapsdk)

    // kakao oauth
    implementation(libs.kakao.oauth)

    //안드로이드 인스트루먼테이션 테스트를 위한 종속성
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation ("androidx.test:runner:1.4.0")
    androidTestImplementation ("androidx.test:rules:1.4.0")
}