plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.bird_farm_shop_android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bird_farm_shop_android"
        minSdk = 23
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
//    implementation(files("C:/Users/Blues/Desktop/PRM_TTTM/jtds-1.3.1.jar"))
    implementation(files("E:\\Semester\\Term7\\PRM392\\BIRD_FARM_SHOP_ANDROID\\jtds-1.3.1.jar"))
    implementation("com.google.firebase:firebase-auth:22.2.0")
    implementation("com.google.firebase:firebase-database:20.3.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}