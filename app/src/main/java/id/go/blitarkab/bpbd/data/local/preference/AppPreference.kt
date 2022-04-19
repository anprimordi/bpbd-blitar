package id.go.blitarkab.bpbd.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import id.go.blitarkab.bpbd.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreference @Inject constructor(
    @ApplicationContext context: Context
) {

    companion object {
        private const val PREFERENCE_NAME = BuildConfig.PREF_NAME

        //region Key
        const val KEY_ACCOUNT = "key_json_string_account"
        //endregion Key
    }

    private val sharedPref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun get(): SharedPreferences {
        return sharedPref
    }

    fun editor(): SharedPreferences.Editor {
        return sharedPref.edit()
    }

    fun clear() {
        editor().clear().apply()
    }

}