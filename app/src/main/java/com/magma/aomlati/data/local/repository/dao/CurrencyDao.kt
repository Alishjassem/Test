package com.magma.aomlati.data.local.repository.dao

import androidx.room.*
import com.magma.aomlati.model.Currency

@Dao
interface CurrencyDao {

    //Room auto generate the code for these methods
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currency: Currency)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(currencies: List<Currency>): LongArray

    @Query("SELECT * FROM Currency")
    fun loadAll(): List<Currency>

    @Query("SELECT * FROM Currency WHERE type = :type")
    fun loadAll(type: String): List<Currency>

    @Query("SELECT * FROM Currency WHERE type = :type AND isFavorite = :favorite")
    fun loadAll(type: String, favorite: Boolean): List<Currency>

    @Query("SELECT * FROM Currency WHERE name LIKE :text")
    fun loadByName(text: String): List<Currency>

    @Query("SELECT * FROM Currency WHERE name LIKE :text AND type =:type")
    fun loadByName(text: String, type: String): List<Currency>

    @Query("SELECT * FROM Currency WHERE name LIKE :text AND type =:type AND isFavorite =:favorite")
    fun loadByName(text: String, type: String, favorite: Boolean): List<Currency>

    @Query("SELECT * FROM Currency WHERE _id LIKE :id")
    fun load(id: String): Currency

    @Delete
    fun delete(currency: Currency?)

    @Query("DELETE FROM Currency")
    fun deleteAll()

    @Update
    fun update(currency: Currency?)

    @Update
    fun updateAll(currencies: List<Currency>)

}