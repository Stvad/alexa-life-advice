rootProject.name = "alexa_life_adviser"

includeBuild("../kask") {
    dependencySubstitution {
        substitute(module("com.github.Stvad:kask")).with(project(":"))
    }
}

pluginManagement {
    repositories {
        maven { url = uri("../kask-gradle-plugin/build/repository") }
        gradlePluginPortal()
        jcenter()
        maven(url = "https://jitpack.io")
    }
}