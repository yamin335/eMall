package com.rtchubs.arfixture

import androidx.databinding.DataBindingUtil
import androidx.work.Configuration
import androidx.work.WorkManager
import com.rtchubs.arfixture.binding.FragmentDataBindingComponent
import com.rtchubs.arfixture.di.DaggerAppComponent
import com.rtchubs.arfixture.util.AppConstants.arModelsFolder
import com.rtchubs.arfixture.util.AppConstants.downloadFolder
import com.rtchubs.arfixture.util.FileUtils
import com.rtchubs.arfixture.worker.DaggerWorkerFactory
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerApplication
import javax.inject.Inject

class App : DaggerApplication() {

    private val applicationInjector = DaggerAppComponent.builder()
        .application(this)
        .build()

    @Inject
    lateinit var workerFactory: DaggerWorkerFactory

    @Inject
    lateinit var picasso: Picasso

    override fun applicationInjector() = applicationInjector

    override fun onCreate() {
        super.onCreate()
        FileUtils.makeEmptyFolderIntoExternalStorageWithTitle(this, downloadFolder)
        FileUtils.makeEmptyFolderIntoExternalStorageWithTitle(this, arModelsFolder)
        // Inject this class's @Inject-annotated members.
        applicationInjector.inject(this)
        /*if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }*/

        // Fabric.with(this, Crashlytics())


        //set picasso to support http protocol
        Picasso.setSingletonInstance(picasso)

        DataBindingUtil.setDefaultComponent(FragmentDataBindingComponent())

        WorkManager.initialize(
            this,
            Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
        )
    }

    companion object {
        private const val TAG = "App"
    }
}