/*
 * Copyright (c) 2020 Mustafa Ozhan. All rights reserved.
 */
plugins {
    with(Plugins) {
        id(androidLibrary)
        kotlin(android)
        `maven-publish`
        signing
    }
}

android {
    with(ProjectSettings) {
        compileSdkVersion(projectCompileSdkVersion)

        defaultConfig {
            minSdkVersion(projectMinSdkVersion)
            targetSdkVersion(projectTargetSdkVersion)

            versionCode = getVersionCode(project)
            versionName = getVersionName(project)
        }

        buildFeatures {
            viewBinding = true
            dataBinding = true
        }
    }
}

dependencies {
    with(Dependencies.Android) {
        implementation(navigation)
        implementation(androidMaterial)
    }
}

val androidJavaDocJar by tasks.register("androidJavadocJar", Jar::class) {
    archiveClassifier.set("javadoc")
    from("$buildDir/javadoc")
}

val androidSourcesJar by tasks.register("androidSourcesJar", Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
}

allprojects {

    with(Library) {

        group = libraryGroup
        version = libraryVersion

        repositories {
            google()
            mavenCentral()
            jcenter()
        }

        afterEvaluate {
            extensions.findByType<PublishingExtension>()?.apply {
                repositories {
                    maven {
                        url = uri(if (isReleaseBuild) releaseUrl else snapshotUrl)
                        credentials {
                            username = System.getenv("MAVEN_USERNAME")?.toString()
                            password = System.getenv("MAVEN_PASSWORD")?.toString()
                        }
                    }
                }

                publications.register<MavenPublication>("mavenAndroid") {
                    artifact(androidJavaDocJar)
                    artifact(androidSourcesJar)

                    pom {
                        name.set(libraryName)
                        description.set(libraryDescription)
                        url.set(libraryUrl)

                        licenses {
                            license {
                                name.set(licenseName)
                                url.set(licenseUrl)
                                distribution.set(licenseDistribution)
                            }
                        }
                        developers {
                            developer {
                                id.set(developerId)
                                name.set(developerName)
                                email.set(developerEmail)
                            }
                        }
                        scm { url.set(libraryUrl) }
                    }
                }
            }

            extensions.findByType<SigningExtension>()?.apply {
                val publishing = extensions.findByType<PublishingExtension>() ?: return@apply
                val key = System.getenv("GPG_KEY")?.toString()?.replace("\\n", "\n")
                val password = System.getenv("GPG_PASSWORD")?.toString()

                @Suppress("UnstableApiUsage")
                useInMemoryPgpKeys(key, password)
                sign(publishing.publications)
            }

            tasks.withType<Sign>().configureEach {
                onlyIf { isReleaseBuild }
            }
        }
    }
}

val isReleaseBuild: Boolean
    get() = System.getenv("GPG_KEY") != null

object Library {
    const val libraryGroup = "com.github.submob"
    const val libraryVersion = "2.0.0"

    const val libraryUrl = "https://github.com/SubMob/BaseMob"
    const val libraryName = "BaseMob"
    const val libraryDescription = "Set of base classes for Android"

    const val developerName = "Mustafa Ozhan"
    const val developerId = "mustafaozhan"
    const val developerEmail = "mr.mustafa.ozhan@gmail.com"

    const val licenseName = "The Apache Software License, Version 2.0"
    const val licenseUrl = "http://www.apache.org/licenses/LICENSE-2.0.txt"
    const val licenseDistribution = "repo"

    const val releaseUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2"
    const val snapshotUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots"
}
