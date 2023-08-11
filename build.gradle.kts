import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    id("maven-publish")
    application
}

group = "cn.mucute"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.alibaba:fastjson:2.0.13.android")
    implementation("com.google.code.gson:gson:2.10")
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "cn.mucute"
            artifactId = "netrut"
            version = "1.0.1"
            from(components["kotlin"])
        }
    }
    repositories {
        maven {
            url = uri("https://maven.mucute.cn/repository/mucute/")
            credentials {
                username = "admin"
                password = "Muc244048880..."
            }
        }
    }
}