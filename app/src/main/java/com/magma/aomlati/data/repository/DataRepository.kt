package com.magma.aomlati.data.repository

import com.magma.aomlati.data.local.repository.LocalRepository
import com.magma.aomlati.data.remote.controller.Resource
import com.magma.aomlati.data.remote.controller.ResponseWrapper
import com.magma.aomlati.data.remote.requests.LoginRequest
import com.magma.aomlati.data.remote.responses.LoginResponse
import com.magma.aomlati.model.Currency
import com.magma.aomlati.model.Fuel
import com.magma.aomlati.model.Metal
import javax.inject.Inject

class DataRepository
@Inject
constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : DataSource {

    //Api
    override suspend fun doServerLogin(loginRequest: LoginRequest?): Resource<ResponseWrapper<LoginResponse>> {
        return remoteRepository.doServerLogin(loginRequest)
    }

    override suspend fun doServerGetCurrencies(type: String?): Resource<ResponseWrapper<ArrayList<Currency>>> {
        return remoteRepository.doServerGetCurrencies(type)
    }

    override suspend fun doServerGetCurrenciesRates(): Resource<ResponseWrapper<ArrayList<Currency>>> {
        return remoteRepository.doServerGetCurrenciesRates()
    }

    override suspend fun doServerGetMetal(): Resource<ResponseWrapper<ArrayList<Metal>>> {
        return remoteRepository.doServerGetMetal()
    }

    override suspend fun doServerGetMetalRates(): Resource<ResponseWrapper<ArrayList<Metal>>> {
        return remoteRepository.doServerGetMetalRates()
    }

    override suspend fun doServerGetFuel(): Resource<ResponseWrapper<ArrayList<Fuel>>> {
        return remoteRepository.doServerGetFuel()
    }

    override suspend fun doServerGetFuelRates(): Resource<ResponseWrapper<ArrayList<Fuel>>> {
        return remoteRepository.doServerGetFuelRates()
    }


    //Local DB
    override fun loadAllCurrencies(): List<Currency> {
        return localRepository.loadAllCurrencies()
    }

    override fun loadAllCurrencies(type: String): List<Currency> {
        return localRepository.loadAllCurrencies(type)
    }

    override fun loadCurrenciesByFavorite(type: String, favorite: Boolean): List<Currency> {
        return localRepository.loadCurrenciesByFavorite(type, favorite)
    }

    override fun loadCurrenciesByName(text: String): List<Currency> {
        return localRepository.loadCurrenciesByName("%$text%")
    }

    override fun loadCurrenciesByName(text: String, type: String): List<Currency> {
        return localRepository.loadCurrenciesByName("%$text%", type)
    }

    override fun loadCurrenciesByName(
        text: String,
        type: String,
        favorite: Boolean
    ): List<Currency> {
        return localRepository.loadCurrenciesByName("%$text%", type, favorite)
    }

    override fun loadCurrency(id: String): Currency {
        return localRepository.loadCurrency(id)
    }

    override fun insertCurrencyList(currencies: List<Currency>) {
        localRepository.insertCurrencyList(currencies)
    }

    override fun insertCurrency(currency: Currency) {
        localRepository.insertCurrency(currency)
    }

    override fun updateCurrency(currency: Currency) {
        localRepository.updateCurrency(currency)
    }

    override fun updateCurrencies(currencies: List<Currency>) {
        localRepository.updateCurrencies(currencies)
    }

    override fun deleteCurrency(currency: Currency) {
        localRepository.deleteCurrency(currency)
    }

    override fun deleteAllCurrencies() {
        localRepository.deleteAllCurrencies()
    }

    override fun loadAllMetal(): List<Metal> {
        return localRepository.loadAllMetal()
    }

    override fun loadAllMetal(isTurkish: Boolean): List<Metal> {
        return localRepository.loadAllMetal(isTurkish)
    }

    override fun loadMetalByName(text: String): List<Metal> {
        return localRepository.loadMetalByName("%$text%")
    }

    override fun loadMetalByName(text: String, type: String): List<Metal> {
        return localRepository.loadMetalByName("%$text%", type)
    }

    override fun loadMetal(id: String): Metal {
        return localRepository.loadMetal(id)
    }

    override fun insertMetalList(metal: List<Metal>) {
        localRepository.insertMetalList(metal)
    }

    override fun insertMetal(metal: Metal) {
        localRepository.insertMetal(metal)
    }

    override fun updateMetal(metal: Metal) {
        localRepository.updateMetal(metal)
    }

    override fun updateMetal(metal: List<Metal>) {
        localRepository.updateMetal(metal)
    }

    override fun deleteMetal(metal: Metal) {
        localRepository.deleteMetal(metal)
    }

    override fun deleteAllMetal() {
        localRepository.deleteAllMetal()
    }

    //Fuel
    override fun loadAllFuel(): List<Fuel> {
        return localRepository.loadAllFuel()
    }

    override fun loadFuelByName(text: String): List<Fuel> {
        return localRepository.loadFuelByName("%$text%")
    }

    override fun loadFuel(id: String): Fuel {
        return localRepository.loadFuel(id)
    }

    override fun insertFuelList(fuels: List<Fuel>) {
        localRepository.insertFuelList(fuels)
    }

    override fun insertFuel(fuel: Fuel) {
        localRepository.insertFuel(fuel)
    }

    override fun updateFuel(fuel: Fuel) {
        localRepository.updateFuel(fuel)
    }

    override fun updateFuel(fuels: List<Fuel>) {
        localRepository.updateFuel(fuels)
    }

    override fun deleteFuel(fuel: Fuel) {
        localRepository.deleteFuel(fuel)
    }

    override fun deleteAllFuel() {
        localRepository.deleteAllFuel()
    }


    //Pref
    override fun setApiToken(apiToken: String) {
        localRepository.setApiToken(apiToken)
    }

    override fun getApiToken(): String? {
        return localRepository.getApiToken()
    }

    override fun setLang(lang: String) {
        localRepository.setLang(lang)
    }

    override fun getLang(): String? {
        return localRepository.getLang()
    }

    override fun setIsDarkTheme(isDark: Boolean) {
        localRepository.setIsDarkTheme(isDark)
    }

    override fun isDarkTheme(): Boolean? {
        return localRepository.isDarkTheme()
    }

    override fun setIsShownOnBoarding(isShown: Boolean) {
        return localRepository.setIsShownOnBoarding(isShown)
    }

    override fun isShownOnBoarding(): Boolean {
        return localRepository.isShownOnBoarding()
    }

}

