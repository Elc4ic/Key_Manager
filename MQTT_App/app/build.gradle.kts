import com.google.protobuf.gradle.id

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.protobuf") version "0.9.1"
}

android {
    namespace = "com.example.mqtt_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mqtt_app"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        manifestPlaceholders["YANDEX_CLIENT_ID"] = "4b0a18de1c5f461e8f4fc61ac13b9ff5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.7")
    implementation("androidx.navigation:navigation-compose:2.7.7")

    implementation ("com.yandex.android:authsdk:3.1.0")

    implementation ("io.grpc:grpc-stub:1.58.0")
    implementation ("io.grpc:grpc-protobuf:1.58.0")
    implementation ("io.grpc:grpc-okhttp:1.52.1")
    implementation ("io.grpc:protoc-gen-grpc-kotlin:1.3.0")
    implementation ("io.grpc:grpc-kotlin-stub:1.3.0")
    implementation ("com.google.protobuf:protobuf-kotlin:3.22.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.datastore:datastore-preferences-rxjava2:1.0.0")
    implementation("androidx.datastore:datastore-preferences-rxjava3:1.0.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

}

protobuf{
    protoc{
        artifact = "com.google.protobuf:protoc:3.21.12"
    }
    plugins{
        id("grpc"){
            artifact = "io.grpc:protoc-gen-grpc-java:1.52.1"
        }
        id("grpckt"){
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.3.0:jdk8@jar"
        }
    }
    generateProtoTasks{
        all().forEach{
            it.plugins{
                id("grpc")
                id("grpckt")
            }
            it.builtins {
                id("kotlin")
                id("java")
            }
        }
    }
}
