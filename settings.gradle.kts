pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        jcenter()
        maven { url = uri("https://jitpack.io") }
        gradlePluginPortal()

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        mavenLocal()
        maven { url = uri("https://jitpack.io") }

        // OR publish to a remote repository, e.g., JFrog Artifactory, Nexus
        /*maven {
            url = uri("https://github.com/GuptaGrajan/SampleLibrary.git") // Replace with your repository URL
            credentials {
                username = settings.extra["repo.user"].toString()
                password = settings.extra["repo.password"].toString()
            }
        }*/

    }
}

rootProject.name = "Sample Library"
include(":sampleApp")
 