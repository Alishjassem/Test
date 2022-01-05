package com.magma.aomlati.di.module

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.gson.Gson
import com.magma.aomlati.data.local.AppDatabase
import com.magma.aomlati.utils.Const
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext


@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        app: Application
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        Const.DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Singleton
    @Provides
    fun provideFuelDao(db: AppDatabase) = db.fuelDao()

    @Singleton
    @Provides
    fun provideCurrencyDao(db: AppDatabase) = db.currencyDao()

    @Singleton
    @Provides
    fun provideMetalDao(db: AppDatabase) = db.metalDao()

    @Singleton
    @Provides
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences(Const.PREF_NAME, AppCompatActivity.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext {
        return Dispatchers.Main
    }

    @Provides
    @Singleton
    fun provideGsonObject(): Gson {
        return Gson()
    }
}