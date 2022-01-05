package com.magma.aomlati.data.repository

import com.magma.aomlati.data.remote.controller.Resource
import com.magma.aomlati.data.remote.controller.ResponseWrapper
import com.magma.aomlati.data.remote.requests.LoginRequest
import com.magma.aomlati.data.remote.responses.LoginResponse
import com.magma.aomlati.model.Currency
import com.magma.aomlati.model.Fuel
import com.magma.aomlati.model.Metal

interface DataSource {

    //Api
    suspend fun doServerLogin(loginRequest: LoginRequest?): Resource<ResponseWrapper<LoginResponse>>
    suspend fun doServerGetCurrencies(type: String?): Resource<ResponseWrapper<ArrayList<Currency>>>
    suspend fun doServerGetCurrenciesRates(): Resource<ResponseWrapper<ArrayList<Currency>>>
    suspend fun doServerGetMetal(): Resource<ResponseWrapper<ArrayList<Metal>>>
    suspend fun doServerGetMetalRates(): Resource<ResponseWrapper<ArrayList<Metal>>>
    suspend fun doServerGetFuel(): Resource<ResponseWrapper<ArrayList<Fuel>>>
    suspend fun doServerGetFuelRates(): Resource<ResponseWrapper<ArrayList<Fuel>>>

    //Local
    fun loadAllCurrencies(): List<Currency>
    fun loadAllCurrencies(type: String): List<Currency>
    fun loadCurrenciesByFavorite(type: String, favorite: Boolean): List<Currency>
    fun loadCurrenciesByName(text: String): List<Currency>
    fun loadCurrenciesByName(text: String, type: String): List<Currency>
    fun loadCurrenciesByName(text: String, type: String, favorite: Boolean): List<Currency>
    fun loadCurrency(id: String): Currency
    fun insertCurrencyList(currencies: List<Currency>)
    fun insertCurrency(currency: Currency)
    fun updateCurrency(currency: Currency)
    fun updateCurrencies(currencies: List<Currency>)
    fun deleteCurrency(currency: Currency)
    fun deleteAllCurrencies()
    //Metal
    fun loadAllMetal(): List<Metal>
    fun loadAllMetal(isTurkish: Boolean): List<Metal>
    fun loadMetalByName(text: String): List<Metal>
    fun loadMetalByName(text: String, type: String): List<Metal>
    fun loadMetal(id: String): Metal
    fun insertMetalList(metal: List<Metal>)
    fun insertMetal(metal: Metal)
    fun updateMetal(metal: Metal)
    fun updateMetal(metal: List<Metal>)
    fun deleteMetal(metal: Metal)
    fun deleteAllMetal()
    //Fuel
    fun loadAllFuel(): List<Fuel>
    fun loadFuelByName(text: String): List<Fuel>
    fun loadFuel(id: String): Fuel
    fun insertFuelList(fuels: List<Fuel>)
    fun insertFuel(fuel: Fuel)
    fun updateFuel(fuel: Fuel)
    fun updateFuel(fuels: List<Fuel>)
    fun deleteFuel(fuel: Fuel)
    fun deleteAllFuel()

    //Pref
    fun setApiToken(apiToken: String)
    fun getApiToken(): String?
    fun setLang(lang: String)
    fun getLang(): String?
    fun setIsDarkTheme(isDark: Boolean)
    fun isDarkTheme(): Boolean?
    fun setIsShownOnBoarding(isShown: Boolean)
    fun isShownOnBoarding(): Boolean?
}