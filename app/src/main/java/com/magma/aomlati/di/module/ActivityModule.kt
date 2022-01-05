package com.magma.aomlati.di.module

import com.magma.aomlati.presentation.main.MainActivity
import com.magma.aomlati.presentation.onboarding.OnBoardingActivity
import com.magma.aomlati.presentation.share.ShareActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.magma.aomlati.presentation.splash.SplashActivity

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun contributeOnBoardingActivity(): OnBoardingActivity

    @ContributesAndroidInjector
    abstract fun contributeHomeActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeShareActivity(): ShareActivity

}