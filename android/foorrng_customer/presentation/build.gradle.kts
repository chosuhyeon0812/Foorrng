import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.konan.properties.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kapt)
    alias(libs.plugins.daggerHilt)
//    alias(libs.plugins.googleservice)
}

fun getProperty(propertyKey: String): String = gradleLocalProperties(rootDir).getProperty(propertyKey)

android {
    namespace = "com.tasteguys.foorrng_customer.presentation"
    compileSdk = 33

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(localPropertiesFile.inputStream())
        }

        val nativeAppKey = localProperties.getProperty("kakao_app_key_cus") ?: ""
        // 매니페스트 플레이스홀더 설정
        manifestPlaceholders["NATIVE_APP_KEY"] = nativeAppKey
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

    // Android
    implementation(libs.bundles.androidx)
    implementation(project(mapOf("path" to ":domain")))
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

    // UI
    implementation(libs.lottie) // Lottie

//    // Firebase
//    implementation(platform(libs.firebase.bom))
//    implementation(libs.fcm)

    //Glide
    implementation(libs.glide)

    // Naver Map
    implementation(libs.bundles.naverMap)

    // kakao oauth
    implementation(libs.kakao.oauth)

    //Flexbox
    implementation(libs.flexbox)

    //Circle Image View
    implementation(libs.circleImageView)

    //안드로이드 인스트루먼테이션 테스트를 위한 종속성
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation ("androidx.test:runner:1.4.0")
    androidTestImplementation ("androidx.test:rules:1.4.0")
}