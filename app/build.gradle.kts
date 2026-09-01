// Arquivo de build do modulo "app" (o aplicativo em si).

plugins {
    // Plugin que transforma este modulo em um app Android.
    alias(libs.plugins.android.application)
    // Plugin necessario para o Jetpack Compose funcionar.
    alias(libs.plugins.kotlin.compose)
}

android {
    // Pacote base usado pelo Android para gerar codigo (classe R, etc).
    namespace = "com.example.fatia"
    // Versao do Android usada para COMPILAR o app.
    compileSdk = 37

    defaultConfig {
        // Identificador unico do app no celular.
        applicationId = "com.example.fatia"
        // Versao minima do Android que roda o app (Android 7.0).
        minSdk = 24
        // Versao do Android para a qual o app foi testado.
        targetSdk = 37
        // Numero e nome da versao do app.
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            // Nao encolhe/ofusca o codigo (mais simples para o trabalho).
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        // Versao do Java usada na compilacao.
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        // Liga o Jetpack Compose neste modulo.
        compose = true
    }
}

// Bibliotecas usadas pelo app (as mesmas do template padrao do Android Studio).
dependencies {
    // Funcoes basicas do Android.
    implementation(libs.androidx.core.ktx)
    // Permite usar setContent { } dentro da Activity.
    implementation(libs.androidx.activity.compose)
    // BOM: escolhe automaticamente versoes compativeis do Compose.
    implementation(platform(libs.androidx.compose.bom))
    // Nucleo do Compose (Modifier, Column, Row, Box...).
    implementation(libs.androidx.ui)
    // Tipos graficos do Compose (por exemplo a classe Color).
    implementation(libs.androidx.ui.graphics)
    // Permite usar a anotacao @Preview.
    implementation(libs.androidx.ui.tooling.preview)
    // Componentes visuais do Material Design 3 (Text, Surface...).
    implementation(libs.androidx.material3)
    // Ferramenta que desenha o @Preview no Android Studio (so no modo debug).
    debugImplementation(libs.androidx.ui.tooling)
}
