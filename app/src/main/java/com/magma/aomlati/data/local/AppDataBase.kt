package com.magma.aomlati.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.magma.aomlati.data.local.repository.dao.CurrencyDao
import com.magma.aomlati.data.local.repository.dao.FuelDao
import com.magma.aomlati.data.local.repository.dao.MetalDao
import com.magma.aomlati.model.Currency
import com.magma.aomlati.model.Fuel
import com.magma.aomlati.model.Metal
import com.magma.aomlati.model.converters.ListValueConverter
import com.magma.aomlati.model.converters.TitleConverter

@Database(
    entities = [Currency::class, Metal::class, Fuel::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(TitleConverter::class, ListValueConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun metalDao(): MetalDao
    abstract fun fuelDao(): FuelDao
}