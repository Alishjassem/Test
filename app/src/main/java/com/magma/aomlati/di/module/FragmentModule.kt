package com.magma.aomlati.di.module

import com.magma.aomlati.presentation.main.converter.ConverterFragment
import com.magma.aomlati.presentation.main.fuel.FuelFragment
import com.magma.aomlati.presentation.main.home.HomeFragment
import com.magma.aomlati.presentation.main.home.crypto.CryptoCurrencyFragment
import com.magma.aomlati.presentation.main.home.currency.CurrencyFragment
import com.magma.aomlati.presentation.main.metal.MetalFragment
import com.magma.aomlati.presentation.main.settings.SettingsFragment
import com.magma.aomlati.presentation.onboarding.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class FragmentModule {

    /*OnBoarding*/
    @ContributesAndroidInjector
    abstract fun contributeOnBoardingFragment(): OnBoardingFragment

    @ContributesAndroidInjector
    abstract fun contributeOnBoarding2Fragment(): OnBoarding2Fragment

    @ContributesAndroidInjector
    abstract fun contributeOnBoarding3Fragment(): OnBoarding3Fragment

    @ContributesAndroidInjector
    abstract fun contributeOnBoarding4Fragment(): OnBoarding4Fragment

    @ContributesAndroidInjector
    abstract fun contributeOnBoarding5Fragment(): OnBoarding5Fragment

    @ContributesAndroidInjector
    abstract fun contributeWelcomeFavoriteFragment(): WelcomeFavoriteFragment

    /*Main*/
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeMetalFragment(): MetalFragment

    @ContributesAndroidInjector
    abstract fun contributeConverterFragment(): ConverterFragment

    @ContributesAndroidInjector
    abstract fun contributeFuelFragment(): FuelFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun contributeCurrencyFragment(): CurrencyFragment

    @ContributesAndroidInjector
    abstract fun contributeCryptoCurrencyFragment(): CryptoCurrencyFragment

}