package com.magma.aomlati.data.local.repository

import android.content.SharedPreferences
import com.magma.aomlati.data.local.repository.dao.CurrencyDao
import com.magma.aomlati.data.local.repository.dao.FuelDao
import com.magma.aomlati.data.local.repository.dao.MetalDao
import com.magma.aomlati.model.Currency
import com.magma.aomlati.model.Fuel
import com.magma.aomlati.model.Metal
import com.magma.aomlati.utils.Const
import java.util.*
import javax.inject.Inject

class LocalRepository
@Inject constructor(
    private val currencyDao: CurrencyDao,
    private val metalDao: MetalDao,
    private val fuelDao: FuelDao,
    private val preferences: SharedPreferences
) {

    //Preferences
    fun setApiToken(apiToken: String) {
        preferences.edit().putString(Const.PREF_API_TOKEN, apiToken).apply()
    }

    fun getApiToken(): String? {
        return preferences.getString(Const.PREF_API_TOKEN, null)
    }

    fun setLang(lang: String) {
        preferences.edit().putString(Const.PREF_LANG, lang).apply()
    }

    fun getLang(): String? {
        return preferences.getString(Const.PREF_LANG, Locale.getDefault().displayLanguage)
    }

    fun setIsDarkTheme(isDarkTheme: Boolean) {
        preferences.edit().putBoolean(Const.PREF_IS_DARK_THEME, isDarkTheme).apply()
    }

    fun isDarkTheme(): Boolean {
        return preferences.getBoolean(Const.PREF_IS_DARK_THEME, false)
    }

    fun setIsShownOnBoarding(isShown: Boolean) {
        preferences.edit().putBoolean(Const.PREF_IS_SHOWN_ONBOARDING, isShown).apply()
    }

    fun isShownOnBoarding(): Boolean {
        return preferences.getBoolean(Const.PREF_IS_SHOWN_ONBOARDING, false)
    }
    //End pref


    //Local DB
    fun loadAllCurrencies(): List<Currency> {
        return currencyDao.loadAll()
    }

    fun loadAllCurrencies(type: String): List<Currency> {
        return currencyDao.loadAll(type)
    }

    fun loadCurrenciesByFavorite(type: String, favorite: Boolean): List<Currency> {
        return currencyDao.loadAll(type, favorite)
    }

    fun loadCurrenciesByName(text: String): List<Currency> {
        return currencyDao.loadByName(text)
    }

    fun loadCurrenciesByName(text: String, type: String): List<Currency> {
        return currencyDao.loadByName(text, type)
    }

    fun loadCurrenciesByName(text: String, type: String, favorite:Boolean): List<Currency> {
        return currencyDao.loadByName(text, type, favorite)
    }

    fun loadCurrency(id: String): Currency {
        return currencyDao.load(id)
    }

    fun insertCurrencyList(currencies: List<Currency>): LongArray {
        return currencyDao.insertAll(currencies)
    }

    fun insertCurrency(currency: Currency) {
        return currencyDao.insert(currency)
    }

    fun updateCurrency(currency: Currency) {
        return currencyDao.update(currency)
    }

    fun updateCurrencies(currencies: List<Currency>) {
        return currencyDao.updateAll(currencies)
    }

    fun deleteCurrency(currency: Currency) {
        currencyDao.delete(currency)
    }

    fun deleteAllCurrencies() {
        currencyDao.deleteAll()
    }

    //Local DB : Metal
    fun loadAllMetal(): List<Metal> {
        return metalDao.loadAll()
    }

    fun loadAllMetal(isTurkish: Boolean): List<Metal> {
        return metalDao.loadAll(isTurkish)
    }

    fun loadMetalByName(text: String): List<Metal> {
        return metalDao.loadByName(text)
    }

    fun loadMetalByName(text: String, type: String): List<Metal> {
        return metalDao.loadByName(text, type)
    }

    fun loadMetal(id: String): Metal {
        return metalDao.load(id)
    }

    fun insertMetalList(metal: List<Metal>): LongArray {
        return metalDao.insertAll(metal)
    }

    fun insertMetal(metal: Metal) {
        return metalDao.insert(metal)
    }

    fun updateMetal(metal: Metal) {
        return metalDao.update(metal)
    }

    fun updateMetal(metal: List<Metal>) {
        return metalDao.updateAll(metal)
    }

    fun deleteMetal(metal: Metal) {
        metalDao.delete(metal)
    }

    fun deleteAllMetal() {
        metalDao.deleteAll()
    }

    //Local DB : Fuel
    fun loadAllFuel(): List<Fuel> {
        return fuelDao.loadAll()
    }

    fun loadFuelByName(text: String): List<Fuel> {
        return fuelDao.loadByName(text)
    }

    fun loadFuel(id: String): Fuel {
        return fuelDao.load(id)
    }

    fun insertFuelList(fuels: List<Fuel>): LongArray {
        return fuelDao.insertAll(fuels)
    }

    fun insertFuel(fuel: Fuel) {
        return fuelDao.insert(fuel)
    }

    fun updateFuel(fuel: Fuel) {
        return fuelDao.update(fuel)
    }

    fun updateFuel(fuels: List<Fuel>) {
        return fuelDao.updateAll(fuels)
    }

    fun deleteFuel(fuel: Fuel) {
        fuelDao.delete(fuel)
    }

    fun deleteAllFuel() {
        fuelDao.deleteAll()
    }

}