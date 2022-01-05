package com.magma.aomlati.presentation.main.fuel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magma.aomlati.data.remote.controller.Resource
import com.magma.aomlati.data.remote.controller.ResponseWrapper
import com.magma.aomlati.data.remote.requests.LoginRequest
import com.magma.aomlati.data.remote.responses.LoginResponse
import com.magma.aomlati.data.repository.DataRepository
import com.magma.aomlati.model.Fuel
import com.magma.aomlati.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FuelViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    override val coroutineContext: CoroutineContext,
) : ViewModel(), CoroutineScope {

    val fuelResponse =
        MutableLiveData<Event<Resource<ResponseWrapper<ArrayList<Fuel>>>>>()
    val loginResponse = MutableLiveData<Event<Resource<ResponseWrapper<LoginResponse>>>>()
    val fuelsDb = MutableLiveData<Event<List<Fuel>>>()
    val actions = MutableLiveData<Event<FuelActions>>()

    fun onShareApp() {
        actions.value = Event(FuelActions.SHARE_APP_CLICKED)
    }

    fun saveToken(loginResponse: LoginResponse) {
        loginResponse.token.let { dataRepository.setApiToken(it) }
    }

    fun doServerLogin() {
        launch {
            loginResponse.value = Event(Resource.Loading())
            val request = LoginRequest()
            val response: Resource<ResponseWrapper<LoginResponse>> =
                dataRepository.doServerLogin(request)
            loginResponse.value = Event(response)
        }
    }

    fun doServerGetFuelRates() {
        launch {
            //val token = dataRepository.getApiToken()
            fuelResponse.value = Event(Resource.Loading())
            val response: Resource<ResponseWrapper<ArrayList<Fuel>>> =
                dataRepository.doServerGetFuelRates()
            Log.d("TAG", "doServerGetMetalRates: $response")
            fuelResponse.value = Event(response)
        }
    }

    fun doServerGetFuel() {
        launch {
            //val token = dataRepository.getApiToken()
            fuelResponse.value = Event(Resource.Loading())
            val response: Resource<ResponseWrapper<ArrayList<Fuel>>> =
                dataRepository.doServerGetFuel()
            Log.d("TAG", "doServerGetFuel: $response")
            fuelResponse.value = Event(response)
        }
    }

    fun loadAllFuels() {
        // save feed list into database
        launch {
            val list = withContext(Dispatchers.IO) {
                dataRepository.loadAllFuel()
            }
            Log.d("TAG", "loadAllFuels: $list")
            fuelsDb.value = Event(list)
        }
    }

    fun deleteAndSaveFuels(fuelResponse: ArrayList<Fuel>) {
        // save feed list into database
        launch {
            withContext(Dispatchers.IO)
            {
                val ids = dataRepository.deleteAllFuel()
                saveFuels(fuelResponse)
                Log.d("TAG", "saveFuels: $ids")
            }
        }
    }

    private fun saveFuels(fuelResponse: ArrayList<Fuel>) {
        // save feed list into database
        launch {
            withContext(Dispatchers.IO)
            {
                val ids = dataRepository.insertFuelList(fuelResponse)
                Log.d("TAG", "saveFuels: $ids")
            }
        }
    }

}