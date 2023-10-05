
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "popular-movie-list2"
include (":app")
include (":feature:movies")
include (":lib:lib-ui")
include (":lib:lib-usecase")
include (":lib:lib-network")
include (":lib:lib-navigation")
