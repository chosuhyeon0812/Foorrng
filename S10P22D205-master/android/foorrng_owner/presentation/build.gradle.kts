import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.konan.properties.Properties


@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kapt)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.googleservice)
}

fun getProperty(propertyKey: String): String = gradleLocalProperties(rootDir).getProperty(propertyKey)

android {
    namespace = "com.tasteguys.foorrng_owner.presentation"
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

        val nativeAppKey = localProperties.getProperty("kakao_app_key") ?: ""
        // 매니페스트 플레이스홀더 설정
        manifestPlaceholders["NATIVE_APP_KEY"] = nativeAppKey
        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", getProperty("kakao_app_key"))
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
    implementation(project(":domain"))
    implementation(project(":util:retrofit_adapter"))

    // Android
    implementation(libs.bundles.androidx)
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

    //FlexBoxLayoutManager
    implementation(libs.flexbox)


}


