package com.qpay.customer.di

import com.qpay.customer.ui.MainActivity
import com.qpay.customer.ui.MainActivity2
import com.qpay.customer.ui.splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity2(): MainActivity2


}