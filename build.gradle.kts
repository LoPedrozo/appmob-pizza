// Arquivo de build da RAIZ do projeto.
// Aqui so declaramos quais plugins existem; "apply false" significa
// que eles nao sao aplicados aqui, e sim dentro do modulo app.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
}
