package com.rtchubs.edokanpat.di

import com.rtchubs.edokanpat.worker.DaggerWorkerFactory
import com.rtchubs.edokanpat.worker.TokenRefreshWorker
import com.rtchubs.edokanpat.worker.WorkerKey
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
