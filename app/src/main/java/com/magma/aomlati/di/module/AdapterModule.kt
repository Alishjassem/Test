package com.magma.aomlati.di.module

import com.magma.aomlati.presentation.main.fuel.FuelAdapter
import com.magma.aomlati.presentation.main.home.crypto.CryptoCurrencyAdapter
import com.magma.aomlati.presentation.main.home.currency.CurrencyAdapter
import com.magma.aomlati.presentation.main.metal.MetalAdapter
import com.magma.aomlati.presentation.onboarding.FavoriteCurrencyAdapter
import dagger.Module
import dagger.Provides

@Module
class AdapterModule {

    @Provides
    fun provideFavoriteCurrencyAdapter(): FavoriteCurrencyAdapter {
        return FavoriteCurrencyAdapter()
    }

    @Provides
    fun provideCurrencyAdapter(): CurrencyAdapter {
        return CurrencyAdapter()
    }

    @Provides
    fun provideCryptoCurrencyAdapter(): CryptoCurrencyAdapter {
        return CryptoCurrencyAdapter()
    }

    @Provides
    fun provideMetalAdapter(): MetalAdapter {
        return MetalAdapter()
    }

    @Provides
    fun provideFuelAdapter(): FuelAdapter {
        return FuelAdapter()
    }
}