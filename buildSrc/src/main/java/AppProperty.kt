object AppProperty {

    //region Property Types
    const val TYPE_TEXT: String = "String"
    const val TYPE_NUMBER: String = "int"
    const val TYPE_RES_STRING: String = "string"
    //endregion Property Types

    //region Property Names
    const val APP_NAME: String = "app_name"
    const val PREF_NAME: String = "PREF_NAME"
    const val SERVER_URL: String = "SERVER_URL"
    //endregion Property Names

    object Field {
        const val APP_NAME: String = "siMAK PRB (BPBD Kab Blitar)"
        const val PREF_NAME: String = "\"bpbd_blitar_shared_pref\""

        object Debug {
            const val APP_ID_SUFFIX: String = ".debug"
            const val APP_NAME: String = "${Field.APP_NAME} Debug"
            const val APP_VERSION_NAME_SUFFIX: String = "-debug"
            const val SERVER_URL: String = "\"https://bpbd.blitarkab.go.id/simak-prb\""
        }

        object Release {
            const val APP_NAME: String = Field.APP_NAME
            const val SERVER_URL: String = "\"https://bpbd.blitarkab.go.id/simak-prb\""
        }
    }

}