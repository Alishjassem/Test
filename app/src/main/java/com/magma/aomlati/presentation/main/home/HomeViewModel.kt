package com.magma.aomlati.presentation.main.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magma.aomlati.data.remote.controller.Resource
import com.magma.aomlati.data.remote.controller.ResponseWrapper
import com.magma.aomlati.data.remote.requests.LoginRequest
import com.magma.aomlati.data.remote.responses.LoginResponse
import com.magma.aomlati.data.repository.DataRepository
import com.magma.aomlati.model.Currency
import com.magma.aomlati.utils.Const
import com.magma.aomlati.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HomeViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    override val coroutineContext: CoroutineContext,
) : ViewModel(), CoroutineScope {

    val currenciesResponse =
        MutableLiveData<Event<Resource<ResponseWrapper<ArrayList<Currency>>>>>()
    val loginResponse = MutableLiveData<Event<Resource<ResponseWrapper<LoginResponse>>>>()
    val currenciesDb = MutableLiveData<Event<List<Currency>>>()
    val actions = MutableLiveData<Event<HomeActions>>()

    fun saveToken(loginResponse: LoginResponse) {
        loginResponse.token.let { dataRepository.setApiToken(it) }
    }

    fun onShareApp() {
        actions.value = Event(HomeActions.SHARE_APP_CLICKED)
    }

    fun loadAllCurrencies(type: String) {
        // save feed list into database
        launch {
            val list = withContext(Dispatchers.IO) {
                dataRepository.loadAllCurrencies(type)
            }
            Log.d("TAG", "loadAllCurrencies: $list")
            currenciesDb.value = Event(list)
        }
    }

    fun loadCurrenciesByName(text: String, type: String) {
        // save feed list into database
        launch {
            val list = withContext(Dispatchers.IO) {
                dataRepository.loadCurrenciesByName(text, type)
            }
            Log.d("TAG", "loadAllCurrencies: $list")
            currenciesDb.value = Event(list)
        }
    }

    fun deleteAndSaveCurrencies(currenciesResponse: ArrayList<Currency>) {
        // save feed list into database
        launch {
            withContext(Dispatchers.IO)
            {
                val items = dataRepository.loadAllCurrencies()
                deleteAndSaveCurrencies(items, currenciesResponse)
                Log.d("TAG", "saveCurrencies: $items")
            }
        }
    }

    private fun deleteAndSaveCurrencies(items: List<Currency>, currenciesResponse: ArrayList<Currency>) {
        // save feed list into database
        launch {
            withContext(Dispatchers.IO)
            {
                val ids = dataRepository.deleteAllCurrencies()
                saveCurrencies(items, currenciesResponse)
                Log.d("TAG", "saveCurrencies: $ids")
            }
        }
    }

    private fun saveCurrencies(items: List<Currency>, currenciesResponse: ArrayList<Currency>) {
        // save feed list into database
        launch {
            withContext(Dispatchers.IO)
            {
                val map = HashMap<String, Currency>()
                items.forEach { map[it._id] = it }
                currenciesResponse.forEach { map[it._id]?.let { it1-> it.isFavorite = it1.isFavorite } }
                val ids = dataRepository.insertCurrencyList(currenciesResponse)
                loadAllCurrencies(Const.TYPE_FIAT)
                Log.d("TAG", "saveCurrencies: $ids")
            }
        }
    }

    fun doServerGetCurrenciesRates() {
        launch {
            //val token = dataRepository.getApiToken()
            currenciesResponse.value = Event(Resource.Loading())
            val response: Resource<ResponseWrapper<ArrayList<Currency>>> =
                dataRepository.doServerGetCurrenciesRates()
            Log.d("TAG", "doServerGetCurrencies: $response")
            currenciesResponse.value = Event(response)
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

}