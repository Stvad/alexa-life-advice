rootProject.name = "alexa_life_adviser"

includeBuild("../kask")

pluginManagement {
    repositories {
        maven { url = uri("../kask/build/repository") }
        gradlePluginPortal()
        jcenter()
    }
}