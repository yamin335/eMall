package com.rtchubs.engineerbooks.di

import com.rtchubs.engineerbooks.worker.DaggerWorkerFactory
import com.rtchubs.engineerbooks.worker.TokenRefreshWorker
import com.rtchubs.engineerbooks.worker.WorkerKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class WorkerBindingModule {

    /*injector for DaggerWorkerFactory*/
    @Binds
    @IntoMap
    @WorkerKey(TokenRefreshWorker::class)
    abstract fun bindTokenRefreshWorker(factory: TokenRefreshWorker.Factory):
            DaggerWorkerFactory.ChildWorkerFactory


}
