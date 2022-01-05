package com.magma.aomlati.presentation.main.converter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magma.aomlati.data.remote.responses.LoginResponse
import com.magma.aomlati.data.repository.DataRepository
import com.magma.aomlati.model.Currency
import com.magma.aomlati.presentation.main.home.HomeActions
import com.magma.aomlati.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ConverterViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    override val coroutineContext: CoroutineContext,
) : ViewModel(), CoroutineScope {

    val actions = MutableLiveData<Event<ConverterActions>>()
    val currenciesDb = MutableLiveData<Event<List<Currency>>>()

    fun onShareApp() {
        actions.value = Event(ConverterActions.SHARE_APP_CLICKED)
    }

    fun saveToken(loginResponse: LoginResponse) {
        loginResponse.token.let { dataRepository.setApiToken(it) }
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

}