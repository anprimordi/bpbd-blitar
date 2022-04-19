object Dependency {

    object Kotlin {
        const val LANG_VERSION: String = "1.5.31"
        const val LANG: String = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$LANG_VERSION"

        private const val COROUTINES_VERSION: String = "1.5.0"
        const val COROUTINES: String =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:$COROUTINES_VERSION"
        const val COROUTINES_ANDROID: String =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:$COROUTINES_VERSION"

        private const val COROUTINES_PLAY_SERVICES_VERSION: String = "1.1.1"
        const val COROUTINES_PLAY_SERVICES: String =
            "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$COROUTINES_PLAY_SERVICES_VERSION"
    }

    object Jetpack {

        object UI {
            private const val APP_COMPAT_VERSION: String = "1.3.1"
            const val APP_COMPAT: String = "androidx.appcompat:appcompat:$APP_COMPAT_VERSION"

            private const val ACTIVITY_VERSION: String = "1.3.1"
            const val ACTIVITY: String = "androidx.activity:activity-ktx:$ACTIVITY_VERSION"

            private const val FRAGMENT_VERSION: String = "1.3.6"
            const val FRAGMENT: String = "androidx.fragment:fragment-ktx:$FRAGMENT_VERSION"

            private const val MATERIAL_VERSION: String = "1.4.0"
            const val MATERIAL: String = "com.google.android.material:material:$MATERIAL_VERSION"

            private const val CARD_VIEW_VERSION: String = "1.0.0"
            const val CARD_VIEW: String = "androidx.cardview:cardview:$CARD_VIEW_VERSION"

            private const val CONSTRAINT_LAYOUT_VERSION: String = "2.1.0"
            const val CONSTRAINT_LAYOUT: String =
                "androidx.constraintlayout:constraintlayout:$CONSTRAINT_LAYOUT_VERSION"

            private const val RECYCLER_VIEW_VERSION: String = "1.2.1"
            const val RECYCLER_VIEW: String =
                "androidx.recyclerview:recyclerview:$RECYCLER_VIEW_VERSION"
        }

        private const val BROWSER_VERSION: String = "1.3.0"
        const val BROWSER: String = "androidx.browser:browser:$BROWSER_VERSION"

        private const val CORE_VERSION: String = "1.6.0"
        const val CORE: String = "androidx.core:core-ktx:$CORE_VERSION"

        const val HILT_VERSION: String = "2.37"
        const val HILT: String = "com.google.dagger:hilt-android:$HILT_VERSION"
        const val HILT_COMPILER: String = "com.google.dagger:hilt-android-compiler:$HILT_VERSION"

        private const val HILT_NAVIGATION_VERSION: String = "1.0.0"
        const val HILT_NAVIGATION: String =
            "androidx.hilt:hilt-navigation-fragment:$HILT_NAVIGATION_VERSION"

        private const val LEGACY_VERSION: String = "1.0.0"
        const val LEGACY: String = "androidx.legacy:legacy-support-v4:$LEGACY_VERSION"

        private const val LIFECYCLE_VERSION: String = "2.2.0"
        const val LIFECYCLE: String = "androidx.lifecycle:lifecycle-extensions:$LIFECYCLE_VERSION"

        private const val LIVE_DATA_VERSION: String = "2.3.1"
        const val LIVE_DATA: String = "androidx.lifecycle:lifecycle-livedata-ktx:$LIVE_DATA_VERSION"

        private const val MULTI_DEX_VERSION: String = "1.0.3"
        const val MULTI_DEX: String = "com.android.support:multidex:$MULTI_DEX_VERSION"

        const val NAVIGATION_VERSION: String = "2.3.5"
        const val NAVIGATION_FRAGMENT: String =
            "androidx.navigation:navigation-fragment-ktx:$NAVIGATION_VERSION"
        const val NAVIGATION_UI: String =
            "androidx.navigation:navigation-ui-ktx:$NAVIGATION_VERSION"

        private const val PAGING_VERSION: String = "3.0.1"
        const val PAGING: String = "androidx.paging:paging-runtime-ktx:$PAGING_VERSION"

        private const val ROOM_VERSION: String = "2.3.0"
        const val ROOM: String = "androidx.room:room-ktx:$ROOM_VERSION"
        const val ROOM_COMPILER: String = "androidx.room:room-compiler:$ROOM_VERSION"
        const val ROOM_RUNTIME: String = "androidx.room:room-runtime:$ROOM_VERSION"

        private const val VIEW_MODEL_VERSION: String = "2.3.1"
        const val VIEW_MODEL: String =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:$VIEW_MODEL_VERSION"
    }

    object GoogleService {
        const val VERSION: String = "4.3.4"

        private const val AUTH_VERSION: String = "19.2.0"
        const val AUTH: String = "com.google.android.gms:play-services-auth:$AUTH_VERSION"

        private const val LOCATION_VERSION: String = "18.0.0"
        const val LOCATION: String =
            "com.google.android.gms:play-services-location:$LOCATION_VERSION"

        private const val MAPS_VERSION: String = "17.0.1"
        const val MAPS: String = "com.google.android.gms:play-services-maps:$MAPS_VERSION"
    }

    object Firebase {
        private const val BOM_VERSION: String = "26.3.0"
        const val BOM: String = "com.google.firebase:firebase-bom:$BOM_VERSION"

        const val AUTH: String = "com.google.firebase:firebase-auth-ktx"
        const val FIRESTORE: String = "com.google.firebase:firebase-firestore-ktx"
        const val MESSAGING: String = "com.google.firebase:firebase-messaging-ktx"
    }

    object Testing {
        private const val ESPRESSO_VERSION: String = "3.4.0"
        const val ESPRESSO: String = "androidx.test.espresso:espresso-core:$ESPRESSO_VERSION"

        private const val JUNIT_VERSION: String = "4.13.2"
        const val JUNIT: String = "junit:junit:$JUNIT_VERSION"

        private const val JUNIT_EXT_VERSION: String = "1.1.3"
        const val JUNIT_EXT: String = "androidx.test.ext:junit:$JUNIT_EXT_VERSION"
    }

    //region Debug-Only Libraries
    private const val CHUCK_VERSION: String = "1.1.0"
    const val CHUCK_DEBUG: String = "com.readystatesoftware.chuck:library:$CHUCK_VERSION"
    const val CHUCK_RELEASE: String = "com.readystatesoftware.chuck:library-no-op:$CHUCK_VERSION"
    //endregion Debug-Only Libraries

    //region 3rd-Party Libraries
    private const val COMPRESSOR_VERSION: String = "3.0.1"
    const val COMPRESSOR: String = "id.zelory:compressor:$COMPRESSOR_VERSION"

    private const val GLIDE_VERSION: String = "4.12.0"
    const val GLIDE: String = "com.github.bumptech.glide:glide:$GLIDE_VERSION"
    const val GLIDE_COMPILER: String = "com.github.bumptech.glide:compiler:$GLIDE_VERSION"

    private const val HTTP_INTERCEPTOR_VERSION: String = "4.9.0"
    const val HTTP_INTERCEPTOR: String =
        "com.squareup.okhttp3:logging-interceptor:$HTTP_INTERCEPTOR_VERSION"

    private const val IMAGE_PICKER_VERSION: String = "3.0.0-beta1"
    const val IMAGE_PICKER: String = "com.github.esafirm:android-image-picker:$IMAGE_PICKER_VERSION"

    private const val IMAGE_VIEWER_VERSION: String = "1.0.1"
    const val IMAGE_VIEWER: String =
        "com.github.stfalcon:stfalcon-imageviewer:$IMAGE_VIEWER_VERSION"

    private const val RETROFIT_VERSION: String = "2.9.0"
    const val RETROFIT: String = "com.squareup.retrofit2:retrofit:$RETROFIT_VERSION"
    const val RETROFIT_CONVERTER: String = "com.squareup.retrofit2:converter-gson:$RETROFIT_VERSION"

    private const val TIMBER_VERSION: String = "4.7.1"
    const val TIMBER: String = "com.jakewharton.timber:timber:$TIMBER_VERSION"

    private const val SHIMMER_VERSION: String = "0.5.0"
    const val SHIMMER: String = "com.facebook.shimmer:shimmer:$SHIMMER_VERSION"
    //endregion 3rd-Party Libraries

}