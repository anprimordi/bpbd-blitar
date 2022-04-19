object Plugin {

    private const val GRADLE_ANDROID_VERSION: String = "7.0.4"
    const val GRADLE_ANDROID: String = "com.android.tools.build:gradle:$GRADLE_ANDROID_VERSION"

    const val GRADLE_KOTLIN: String =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Dependency.Kotlin.LANG_VERSION}"

    const val ANDROID: String = "com.android.application"

    const val KOTLIN_ANDROID: String = "kotlin-android"
    const val KOTLIN_PARCELIZE: String = "kotlin-parcelize"
    const val KOTLIN_KAPT: String = "kotlin-kapt"

    const val GOOGLE_SERVICES: String = "com.google.gms.google-services"
    const val GOOGLE_SERVICES_CLASSPATH =
        "com.google.gms:google-services:${Dependency.GoogleService.VERSION}"

    const val JETPACK_HILT: String = "dagger.hilt.android.plugin"
    const val JETPACK_HILT_CLASSPATH =
        "com.google.dagger:hilt-android-gradle-plugin:${Dependency.Jetpack.HILT_VERSION}"

    const val JETPACK_NAVIGATION: String = "androidx.navigation.safeargs.kotlin"
    const val JETPACK_NAVIGATION_CLASSPATH =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Dependency.Jetpack.NAVIGATION_VERSION}"

}