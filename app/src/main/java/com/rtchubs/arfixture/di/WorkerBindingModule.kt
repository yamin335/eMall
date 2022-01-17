package com.rtchubs.arfixture.di

import com.rtchubs.arfixture.worker.DaggerWorkerFactory
import com.rtchubs.arfixture.worker.TokenRefreshWorker
import com.rtchubs.arfixture.worker.WorkerKey
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
