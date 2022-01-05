package com.magma.aomlati.di.module

import androidx.lifecycle.ViewModel
import com.magma.aomlati.presentation.main.converter.ConverterViewModel
import com.magma.aomlati.presentation.main.fuel.FuelViewModel
import com.magma.aomlati.presentation.main.home.HomeViewModel
import com.magma.aomlati.presentation.main.metal.MetalViewModel
import com.magma.aomlati.presentation.main.settings.SettingsViewModel
import com.magma.aomlati.presentation.onboarding.OnBoardingViewModel
import com.magma.aomlati.utils.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import com.magma.aomlati.presentation.splash.SplashViewModel

// Because of @Binds, ViewModelModule needs to be an abstract class

@Module
abstract class ViewModelModule {

// Use @Binds to tell Dagger which implementation it needs to use when providing an interface.

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OnBoardingViewModel::class)
    abstract fun bindOnBoardingViewModel(viewModel: OnBoardingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ConverterViewModel::class)
    abstract fun bindConverterViewModel(viewModel: ConverterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FuelViewModel::class)
    abstract fun bindFuelViewModel(viewModel: FuelViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MetalViewModel::class)
    abstract fun bindMetalViewModel(viewModel: MetalViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel
    

}