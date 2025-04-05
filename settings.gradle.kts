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
        maven("https://jitpack.io")
        maven("https://devrepo.kakao.com/nexus/content/groups/public/")
    }
}

rootProject.name = "FindingLinkModules"

include(":app")
include(":finding")
project(":finding").projectDir = File("./Finding/library")
include(":map")
project(":map").projectDir = File("./Map/library")
include(":cardInfo")
project(":cardInfo").projectDir = File("./CardInfo/library")
include(":filter")
project(":filter").projectDir = File("./Filter/library")
include(":repository")
project(":repository").projectDir = File("./TorangRepository/library")