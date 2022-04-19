package id.go.blitarkab.bpbd.presentation

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
import id.go.blitarkab.bpbd.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class MainApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

}