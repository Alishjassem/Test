package com.magma.aomlati.data.local.repository.dao

import androidx.room.*
import com.magma.aomlati.model.Metal

@Dao
interface MetalDao {

    //Room auto generate the code for these methods
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(metal: Metal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(metal: List<Metal>): LongArray

    @Query("SELECT * FROM Metal")
    fun loadAll(): List<Metal>

    @Query("SELECT * FROM Metal WHERE turkish = :isTurkish")
    fun loadAll(isTurkish: Boolean): List<Metal>

    @Query("SELECT * FROM Metal WHERE name LIKE :text")
    fun loadByName(text: String): List<Metal>

    @Query("SELECT * FROM Metal WHERE name LIKE :text AND type =:type")
    fun loadByName(text: String, type: String): List<Metal>

    @Query("SELECT * FROM Metal WHERE _id LIKE :id")
    fun load(id: String): Metal

    @Delete
    fun delete(metal: Metal?)

    @Query("DELETE FROM Metal")
    fun deleteAll()

    @Update
    fun update(metal: Metal?)

    @Update
    fun updateAll(metal: List<Metal>)

}