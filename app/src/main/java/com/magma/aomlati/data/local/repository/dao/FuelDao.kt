package com.magma.aomlati.data.local.repository.dao

import androidx.room.*
import com.magma.aomlati.model.Fuel

@Dao
interface FuelDao {

    //Room auto generate the code for these methods
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(fuel: Fuel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(fuel: List<Fuel>): LongArray

    @Query("SELECT * FROM Fuel")
    fun loadAll(): List<Fuel>

    @Query("SELECT * FROM Fuel WHERE name LIKE :text")
    fun loadByName(text: String): List<Fuel>

    @Query("SELECT * FROM Fuel WHERE _id LIKE :id")
    fun load(id: String): Fuel

    @Delete
    fun delete(fuel: Fuel?)

    @Query("DELETE FROM Fuel")
    fun deleteAll()

    @Update
    fun update(fuel: Fuel?)

    @Update
    fun updateAll(fuel: List<Fuel>)

}