package com.magma.aomlati.data.remote.services

import com.magma.aomlati.data.remote.controller.ResponseWrapper
import com.magma.aomlati.data.remote.requests.LoginRequest
import com.magma.aomlati.data.remote.responses.LoginResponse
import com.magma.aomlati.model.Currency
import com.magma.aomlati.model.Fuel
import com.magma.aomlati.model.Metal
import com.magma.aomlati.utils.Urls
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IFoodService {

    @POST(Urls.END_POINT_LOGIN)
    suspend fun doServerLogin(
        @Body loginRequest: LoginRequest?
    ): Response<ResponseWrapper<LoginResponse>>

    @GET(Urls.END_POINT_GET_CURRENCIES)
    suspend fun getCurrencies(
        @Query("type") type: String?
    ): Response<ResponseWrapper<ArrayList<Currency>>>

    @GET(Urls.END_POINT_GET_CURRENCIES_RATES)
    suspend fun getCurrenciesRates(): Response<ResponseWrapper<ArrayList<Currency>>>

    @GET(Urls.END_POINT_GET_METAL)
    suspend fun getMetal(): Response<ResponseWrapper<ArrayList<Metal>>>

    @GET(Urls.END_POINT_GET_METAL_RATES)
    suspend fun getMetalRates(): Response<ResponseWrapper<ArrayList<Metal>>>

    @GET(Urls.END_POINT_GET_FUEL)
    suspend fun getFuel(): Response<ResponseWrapper<ArrayList<Fuel>>>

    @GET(Urls.END_POINT_GET_FUEL_RATES)
    suspend fun getFuelRates(): Response<ResponseWrapper<ArrayList<Fuel>>>

}