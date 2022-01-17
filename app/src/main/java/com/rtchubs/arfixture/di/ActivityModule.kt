package com.rtchubs.arfixture.di

import com.rtchubs.arfixture.ui.LoginActivity
import com.rtchubs.arfixture.ui.MainActivity
import com.rtchubs.arfixture.ui.SplashActivity
import com.rtchubs.arfixture.ui.barcode_reader.LiveBarcodeScanningActivity
import com.rtchubs.arfixture.ui.live_chat.LiveChatActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeLiveChatActivity(): LiveChatActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeLiveBarcodeScanningActivity(): LiveBarcodeScanningActivity
}