package com.magma.aomlati.data.repository

import android.util.Log
import com.google.gson.Gson
import com.magma.aomlati.data.remote.controller.*
import com.magma.aomlati.data.remote.controller.IRestApiManager
import com.magma.aomlati.data.remote.requests.LoginRequest
import com.magma.aomlati.data.remote.responses.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.magma.aomlati.data.remote.services.IFoodService
import com.magma.aomlati.model.Currency
import com.magma.aomlati.model.Fuel
import com.magma.aomlati.model.Metal
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class RemoteRepository
@Inject constructor(private val serviceGenerator: ServiceGenerator, private val gson: Gson) :
    IRestApiManager {

    override suspend fun doServerLogin(loginRequest: LoginRequest?): Resource<ResponseWrapper<LoginResponse>> {
        val authService = serviceGenerator.createService(IFoodService::class.java)
        try {
            val response = authService.doServerLogin(loginRequest)

            return if (response.isSuccessful) {
                //Do something with response e.g show to the UI.
                val loginResponse = response.body() as ResponseWrapper<LoginResponse>
                Log.d(TAG, "doServerLogin: isSuccessful " + response.code())
                Log.d(TAG, "doServerLogin: isSuccessful $loginResponse")
                Resource.Success(loginResponse)
            } else {
                Log.d(TAG, "doServerLogin: isSuccessful no " + response.code())
                Log.d(TAG, "doServerLogin: isSuccessful no " + response.message())
                val errorBody = gson.fromJson(
                    response.errorBody()?.stringSuspending(),
                    ErrorManager::class.java
                )
                Resource.DataError(errorBody)
            }
        } catch (e: HttpException) {
            return Resource.Exception(e.message() as String)
        } catch (e: Throwable) {
            return Resource.Exception(errorMessage = e.message as String)
        } catch (e: SocketTimeoutException) {
            return Resource.Exception(errorMessage = e.message as String)
        } catch (e: IOException) {
            return Resource.Exception(errorMessage = e.message as String)
        }
    }

    override suspend fun doServerGetCurrencies(type: String?): Resource<ResponseWrapper<ArrayList<Currency>>> {
        val authService = serviceGenerator.createService(IFoodService::class.java)
        try {
            val response = authService.getCurrencies(type)

            return if (response.isSuccessful) {
                //Do something with response e.g show to the UI.
                val currenciesResponse = response.body() as ResponseWrapper<ArrayList<Currency>>
                Log.d(TAG, "doServerGetCurrencies: isSuccessful " + response.code())
                Log.d(TAG, "doServerGetCurrencies: isSuccessful $currenciesResponse")
                Resource.Success(currenciesResponse)
            } else {
                Log.d(TAG, "doServerGetCurrencies: isSuccessful no " + response.code())
                Log.d(TAG, "doServerGetCurrencies: isSuccessful no " + response.message())
                val errorBody = gson.fromJson(
                    response.errorBody()?.stringSuspending(),
                    ErrorManager::class.java
                )
                Resource.DataError(errorBody)
            }
        } catch (e: HttpException) {
            return Resource.Exception(e.message() as String)
        } catch (e: Throwable) {
            return Resource.Exception(errorMessage = e.message as String)
        } catch (e: SocketTimeoutException) {
            return Resource.Exception(errorMessage = e.message as String)
        } catch (e: IOException) {
            return Resource.Exception(errorMessage = e.message as String)
        }
    }

    override suspend fun doServerGetCurrenciesRates(): Resource<ResponseWrapper<ArrayList<Currency>>> {
        val authService = serviceGenerator.createService(IFoodService::class.java)
        try {
            val response = authService.getCurrenciesRates()

            return if (response.isSuccessful) {
                //Do something with response e.g show to the UI.
                val currenciesResponse = response.body() as ResponseWrapper<ArrayList<Currency>>
                Log.d(TAG, "doServerGetCurrenciesRates: isSuccessful " + response.code())
                Log.d(TAG, "doServerGetCurrenciesRates: isSuccessful $currenciesResponse")
                Resource.Success(currenciesResponse)
            } else {
                Log.d(TAG, "doServerGetCurrenciesRates: isSuccessful no " + response.code())
                Log.d(TAG, "doServerGetCurrenciesRates: isSuccessful no " + response.message())
                val errorBody = gson.fromJson(
                    response.errorBody()?.stringSuspending(),
                    ErrorManager::class.java
                )
                Resource.DataError(errorBody)
            }
        } catch (e: HttpException) {
            return Resource.Exception(e.message() as String)
        } catch (e: Throwable) {
            return Resource.Exception(errorMessage = e.message as String)
        } catch (e: SocketTimeoutException) {
            return Resource.Exception(errorMessage = e.message as String)
        } catch (e: IOException) {
            return Resource.Exception(errorMessage = e.message as String)
        }
    }

    override suspend fun doServerGetMetal(): Resource<ResponseWrapper<ArrayList<Metal>>> {
        val authService = serviceGenerator.createService(IFoodService::class.java)
        try {
            val response = authService.getMetal()

            return if (response.isSuccessful) {
                //Do something with response e.g show to the UI.
                val metalResponse = response.body() as ResponseWrapper<ArrayList<Metal>>
                Log.d(TAG, "doServerGetMetal: isSuccessful " + response.code())
                Log.d(TAG, "doServerGetMetal: isSuccessful $metalResponse")
                Resource.Success(metalResponse)
            } else {
                Log.d(TAG, "doServerGetMetal: isSuccessful no " + response.code())
                Log.d(TAG, "doServerGetMetal: isSuccessful no " + response.message())
                val errorBody = gson.fromJson(
                    response.errorBody()?.stringSuspending(),
                    ErrorManager::class.java
                )
                Resource.DataError(errorBody)
            }
        } catch (e: HttpException) {
            return Resource.Exception(e.message() as String)
        } catch (e: Throwable) {
            return Resource.Exception(errorMessage = e.message as String)
        } catch (e: SocketTimeoutException) {
            return Resource.Exception(errorMessage = e.message as String)
        } catch (e: IOException) {
            return Resource.Exception(errorMessage = e.message as String)
        }
    }

    override suspend fun doServerGetMetalRates(): Resource<ResponseWrapper<ArrayList<Metal>>> {
        val authService = serviceGenerator.createService(IFoodService::class.java)
        try {
            val response = authService.getMetalRates()

            return if (response.isSuccessful) {
                //Do something with response e.g show to the UI.
                val metalResponse = response.body() as ResponseWrapper<ArrayList<Metal>>
                Log.d(TAG, "doServerGetMetal: isSuccessful " + response.code())
                Log.d(TAG, "doServerGetMetal: isSuccessful $metalResponse")
                Resource.Success(metalResponse)
            } else {
                Log.d(TAG, "doServerGetMetal: isSuccessful no " + response.code())
                Log.d(TAG, "doServerGetMetal: isSuccessful no " + response.message())
                val errorBody = gson.fromJson(
                    response.errorBody()?.stringSuspending(),
                    ErrorManager::class.java
                )
                Resource.DataError(errorBody)
            }
        } catch (e: HttpException) {
            return Resource.Exception(e.message() as String)
        } catch (e: Throwable) {
            return Resource.Exception(errorMessage = e.message as String)
        } catch (e: SocketTimeoutException) {
            return Resource.Exception(errorMessage = e.message as String)
        } catch (e: IOException) {
            return Resource.Exception(errorMessage = e.message as String)
        }
    }

    override suspend fun doServerGetFuel(): Resource<ResponseWrapper<ArrayList<Fuel>>> {
        val authService = serviceGenerator.createService(IFoodService::class.java)
        try {
            val response = authService.getFuel()

            return if (response.isSuccessful) {
                //Do something with response e.g show to the UI.
                val fuelResponse = response.body() as ResponseWrapper<ArrayList<Fuel>>
                Log.d(TAG, "doServerGetFuel: isSuccessful " + response.code())
                Log.d(TAG, "doServerGetFuel: isSuccessful $fuelResponse")
                Resource.Success(fuelResponse)
            } else {
                Log.d(TAG, "doServerGetFuel: isSuccessful no " + response.code())
                Log.d(TAG, "doServerGetFuel: isSuccessful no " + response.message())
                val errorBody = gson.fromJson(
                    response.errorBody()?.stringSuspending(),
                    ErrorManager::class.java
                )
                Resource.DataError(errorBody)
            }
        } catch (e: HttpException) {
            return Resource.Exception(e.message() as String)
        } catch (e: Throwable) {
            return Resource.Exception(errorMessage = e.message as String)
        } catch (e: SocketTimeoutException) {
            return Resource.Exception(errorMessage = e.message as String)
        } catch (e: IOException) {
            return Resource.Exception(errorMessage = e.message as String)
        }
    }

    override suspend fun doServerGetFuelRates(): Resource<ResponseWrapper<ArrayList<Fuel>>> {
        val authService = serviceGenerator.createService(IFoodService::class.java)
        try {
            val response = authService.getFuelRates()

            return if (response.isSuccessful) {
                //Do something with response e.g show to the UI.
                val fuelResponse = response.body() as ResponseWrapper<ArrayList<Fuel>>
                Log.d(TAG, "doServerGetFuelRates: isSuccessful " + response.code())
                Log.d(TAG, "doServerGetFuelRates: isSuccessful $fuelResponse")
                Resource.Success(fuelResponse)
            } else {
                Log.d(TAG, "doServerGetFuelRates: isSuccessful no " + response.code())
                Log.d(TAG, "doServerGetFuelRates: isSuccessful no " + response.message())
                val errorBody = gson.fromJson(
                    response.errorBody()?.stringSuspending(),
                    ErrorManager::class.java
                )
                Resource.DataError(errorBody)
            }
        } catch (e: HttpException) {
            return Resource.Exception(e.message() as String)
        } catch (e: Throwable) {
            return Resource.Exception(errorMessage = e.message as String)
        } catch (e: SocketTimeoutException) {
            return Resource.Exception(errorMessage = e.message as String)
        } catch (e: IOException) {
            return Resource.Exception(errorMessage = e.message as String)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun ResponseBody.stringSuspending() =
        withContext(Dispatchers.IO) { string() }

    companion object {
        private const val TAG = "RemoteRepository"
    }

}