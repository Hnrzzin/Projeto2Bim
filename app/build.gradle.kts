plugins {
    alias(libs.plugins.android.application)
    // Este plugin é necessário para que o google-services.json funcione
    id("com.google.gms.google-services")
}

android {
    namespace = "com.hnrzzin.projeto2b"
    compileSdk = 36 // Correção aqui

    defaultConfig {
        applicationId = "com.hnrzzin.projeto2b"
        minSdk = 24
        targetSdk = 36
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
}

dependencies {
    // Firebase BoM (Gerencia as versões de todos os Firebase abaixo)
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))

    // Autenticação e Database (Versões controladas pelo BoM)
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-database")

    // Google Auth para login federado
    implementation("com.google.android.gms:play-services-auth:21.2.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // Bibliotecas padrão do projeto
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)

    // Testes
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}