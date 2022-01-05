package com.magma.aomlati.di.module

import com.magma.aomlati.data.repository.DataRepository
import com.magma.aomlati.data.repository.DataSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideDataRepository(dataRepository: DataRepository): DataSource
}