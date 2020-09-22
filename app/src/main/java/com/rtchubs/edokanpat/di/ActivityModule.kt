package com.rtchubs.edokanpat.di

import com.rtchubs.edokanpat.ui.LoginActivity
import com.rtchubs.edokanpat.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeLoginActivity(): LoginActivity
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity


}