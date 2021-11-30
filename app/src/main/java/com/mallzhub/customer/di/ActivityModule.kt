package com.mallzhub.customer.di

import com.mallzhub.customer.ui.LoginActivity
import com.mallzhub.customer.ui.MainActivity
import com.mallzhub.customer.ui.SplashActivity
import com.mallzhub.customer.ui.barcode_reader.LiveBarcodeScanningActivity
import com.mallzhub.customer.ui.live_chat.LiveChatActivity
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