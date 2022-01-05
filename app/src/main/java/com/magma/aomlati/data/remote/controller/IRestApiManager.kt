package com.magma.aomlati.data.remote.controller

import com.magma.aomlati.data.remote.requests.LoginRequest
import com.magma.aomlati.data.remote.responses.LoginResponse
import com.magma.aomlati.model.Currency
import com.magma.aomlati.model.Fuel
import com.magma.aomlati.model.Metal

internal interface IRestApiManager {

    suspend fun doServerLogin(loginRequest: LoginRequest?): Resource<ResponseWrapper<LoginResponse>>

    suspend fun doServerGetCurrencies(type: String?): Resource<ResponseWrapper<ArrayList<Currency>>>

    suspend fun doServerGetCurrenciesRates(): Resource<ResponseWrapper<ArrayList<Currency>>>

    suspend fun doServerGetMetal(): Resource<ResponseWrapper<ArrayList<Metal>>>

    suspend fun doServerGetMetalRates(): Resource<ResponseWrapper<ArrayList<Metal>>>

    suspend fun doServerGetFuel(): Resource<ResponseWrapper<ArrayList<Fuel>>>

    suspend fun doServerGetFuelRates(): Resource<ResponseWrapper<ArrayList<Fuel>>>
}