// Configuracao geral do projeto (roda antes do build).

pluginManagement {
    // Onde o Gradle procura os PLUGINS (Android, Compose).
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    // Onde o Gradle procura as BIBLIOTECAS (Compose, Material3...).
    repositories {
        google()
        mavenCentral()
    }
}

// Nome do projeto.
rootProject.name = "Fatia"

// Modulos do projeto. Aqui so existe o modulo "app".
include(":app")
