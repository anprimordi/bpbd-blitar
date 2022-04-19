buildscript {

    repositories {
        google()
        mavenCentral()
        jcenter()
    }

    dependencies {
        classpath(Plugin.GRADLE_ANDROID)
        classpath(Plugin.GRADLE_KOTLIN)
        classpath(Plugin.GOOGLE_SERVICES_CLASSPATH)
        classpath(Plugin.JETPACK_HILT_CLASSPATH)
        classpath(Plugin.JETPACK_NAVIGATION_CLASSPATH)
    }

}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven { url = uri("https://jitpack.io") }
    }
}

tasks.register(name = "clean", type = Delete::class) {
    delete(rootProject.buildDir)
}