object Config {

    const val SDK_MINIMUM_VERSION: Int = 21
    const val SDK_TARGET_VERSION: Int = 31
    const val SDK_COMPILE_VERSION: Int = 31

    const val BUILD_TOOLS_VERSION: String = "32.0.0"

    const val APP_ID: String = "id.go.blitarkab.bpbd"
    const val APP_VERSION_CODE: Int = 5
    const val APP_VERSION_NAME: String = "0.0.5"

    const val KEYSTORE_PATH: String = "/app/release_keystore.jks"
    const val KEYSTORE_ALIAS: String = "key_bpbd_blitar"
    const val KEYSTORE_PASSWORD: String = "BPBDKabBlitar201021"

    object BuildType {
        const val DEBUG: String = "debug"
        const val RELEASE: String = "release"
    }

}