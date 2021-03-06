package com.magma.aomlati.di.component

import android.app.Application
import com.magma.aomlati.MAGMA
import com.magma.aomlati.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import com.magma.aomlati.di.module.FragmentModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        FragmentModule::class,
        DataModule::class,
        ViewModelModule::class,
        AdapterModule::class
    ]
)

// Definition of a Dagger component

interface AppComponent {
    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        @BindsInstance
        fun application(application: Application): Builder
    }
    // Classes that can be injected by this Component

    fun inject(app: MAGMA)

}