pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Online Store"
include(":app")
include(":feature:login")
include(":core")
include(":feature:catalog")
include(":feature:home")
include(":feature:discount")
include(":feature:profile")
include(":feature:basket")
include(":api")
include(":feature:detail")
