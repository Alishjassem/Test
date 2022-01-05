package com.magma.aomlati.presentation.onboarding

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magma.aomlati.data.remote.controller.Resource
import com.magma.aomlati.data.repository.DataRepository
import com.magma.aomlati.model.Currency
import com.magma.aomlati.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.magma.aomlati.data.remote.controller.ResponseWrapper
import com.magma.aomlati.data.remote.requests.LoginRequest
import com.magma.aomlati.data.remote.responses.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class OnBoardingViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    override val coroutineContext: CoroutineContext,
) : ViewModel(), CoroutineScope {

    val currenciesResponse =
        MutableLiveData<Event<Resource<ResponseWrapper<ArrayList<Currency>>>>>()
    val currenciesDb = MutableLiveData<Event<List<Currency>>>()
    val loginResponse = MutableLiveData<Event<Resource<ResponseWrapper<LoginResponse>>>>()
    val actions = MutableLiveData<Event<FavoriteActions>>()

    fun doServerLogin() {
        launch {
            loginResponse.value = Event(Resource.Loading())
            val request = LoginRequest()
            val response: Resource<ResponseWrapper<LoginResponse>> =
                dataRepository.doServerLogin(request)
            loginResponse.value = Event(response)
        }
    }

    fun doServerGetCurrencies(type: String) {
        launch {
            //val token = dataRepository.getApiToken()
            currenciesResponse.value = Event(Resource.Loading())
            val response: Resource<ResponseWrapper<ArrayList<Currency>>> =
                dataRepository.doServerGetCurrencies(type)
            Log.d("TAG", "doServerGetCurrencies: $response")
            currenciesResponse.value = Event(response)
        }
    }

    fun saveCurrencies(currenciesResponse: ArrayList<Currency>) {
        // save feed list into database
        launch {
            withContext(Dispatchers.IO)
            {
                val ids = dataRepository.insertCurrencyList(currenciesResponse)
                Log.d("TAG", "saveCurrencies: $ids")
            }
        }
    }

    fun updateFavoriteCurrencies(currencies: List<Currency>) {
        // save feed list into database
        launch {
            withContext(Dispatchers.IO)
            {
                val ids = dataRepository.updateCurrencies(currencies)
                Log.d("TAG", "updateFavoriteCurrencies: $ids")
            }
        }
    }

    fun loadAllCurrencies() {
        // save feed list into database
        launch {
            val list = withContext(Dispatchers.IO) {
                dataRepository.loadAllCurrencies()
            }
            Log.d("TAG", "loadAllCurrencies: $list")
            currenciesDb.value = Event(list)
        }
    }

    fun loadCurrenciesByName(text: String) {
        // save feed list into database
        launch {
            val list = withContext(Dispatchers.IO) {
                dataRepository.loadCurrenciesByName(text)
            }
            Log.d("TAG", "loadAllCurrencies: $list")
            currenciesDb.value = Event(list)
        }
    }

    fun onContinue() {
        actions.value = Event(FavoriteActions.CONTINUE_CLICKED)
    }

    fun setIsShowingOnboard(isShown : Boolean) {
        dataRepository.setIsShownOnBoarding(isShown)
    }

    fun setToken(token: String?) {
        token?.let { dataRepository.setApiToken(it) }
    }

    fun getToken(): String? {
        return dataRepository.getApiToken()
    }

}