package com.magma.aomlati.presentation.main.metal

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magma.aomlati.data.remote.controller.Resource
import com.magma.aomlati.data.remote.controller.ResponseWrapper
import com.magma.aomlati.data.remote.requests.LoginRequest
import com.magma.aomlati.data.remote.responses.LoginResponse
import com.magma.aomlati.data.repository.DataRepository
import com.magma.aomlati.model.Metal
import com.magma.aomlati.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MetalViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    override val coroutineContext: CoroutineContext,
) : ViewModel(), CoroutineScope {

    val metalResponse =
        MutableLiveData<Event<Resource<ResponseWrapper<ArrayList<Metal>>>>>()
    val metalsDb = MutableLiveData<Event<List<Metal>>>()
    val actions = MutableLiveData<Event<MetalActions>>()
    val loginResponse = MutableLiveData<Event<Resource<ResponseWrapper<LoginResponse>>>>()

    fun onShareApp() {
        actions.value = Event(MetalActions.SHARE_APP_CLICKED)
    }

    fun saveToken(loginResponse: LoginResponse) {
        loginResponse.token.let { dataRepository.setApiToken(it) }
    }

    fun doServerGetMetal() {
        launch {
            //val token = dataRepository.getApiToken()
            metalResponse.value = Event(Resource.Loading())
            val response: Resource<ResponseWrapper<ArrayList<Metal>>> =
                dataRepository.doServerGetMetal()
            Log.d("TAG", "doServerGetMetal: $response")
            metalResponse.value = Event(response)
        }
    }

    fun doServerGetMetalRates() {
        launch {
            //val token = dataRepository.getApiToken()
            metalResponse.value = Event(Resource.Loading())
            val response: Resource<ResponseWrapper<ArrayList<Metal>>> =
                dataRepository.doServerGetMetalRates()
            Log.d("TAG", "doServerGetMetalRates: $response")
            metalResponse.value = Event(response)
        }
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

    fun deleteAndSaveMetals(metalResponse: ArrayList<Metal>) {
        // save feed list into database
        launch {
            withContext(Dispatchers.IO)
            {
                val ids = dataRepository.deleteAllMetal()
                saveMetal(metalResponse)
                Log.d("TAG", "saveMetal: $ids")
            }
        }
    }

    private fun saveMetal(metalResponse: ArrayList<Metal>) {
        // save feed list into database
        launch {
            withContext(Dispatchers.IO)
            {
                val ids = dataRepository.insertMetalList(metalResponse)
                loadAllMetals(true)
                Log.d("TAG", "saveMetal: $ids")
            }
        }
    }

    fun loadAllMetals(isTurkish: Boolean) {
        // save feed list into database
        launch {
            val list = withContext(Dispatchers.IO) {
                dataRepository.loadAllMetal(isTurkish)
            }
            Log.d("TAG", "loadAllMetals: isTurkish = $isTurkish")
            metalsDb.value = Event(list)
        }
    }

}