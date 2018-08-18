rootProject.name = "alexa_life_adviser"

includeBuild("../kask")

pluginManagement {
    repositories {
        maven { url = uri("../kask-gradle-plugin/build/repository") }
        gradlePluginPortal()
        jcenter()
    }
}