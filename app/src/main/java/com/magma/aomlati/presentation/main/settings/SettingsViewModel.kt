package com.magma.aomlati.presentation.main.settings

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magma.aomlati.data.remote.responses.LoginResponse
import com.magma.aomlati.data.repository.DataRepository
import com.magma.aomlati.model.Currency
import com.magma.aomlati.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SettingsViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    override val coroutineContext: CoroutineContext,
) : ViewModel(), CoroutineScope {

    val currenciesDb = MutableLiveData<Event<List<Currency>>>()
    val pinnedCurrenciesDb = MutableLiveData<Event<List<Currency>>>()
    val actions = MutableLiveData<Event<SettingsActions>>()

    fun saveToken(loginResponse: LoginResponse) {
        loginResponse.token.let { dataRepository.setApiToken(it) }
    }

    fun isDarkTheme(): Boolean? {
        return dataRepository.isDarkTheme()
    }

    fun setIsDarkTheme(isDarkTheme: Boolean) {
        return dataRepository.setIsDarkTheme(isDarkTheme)
    }

    fun setLanguage(lang: String) {
        dataRepository.setLang(lang)
    }

    fun getLanguage(): String? {
        return dataRepository.getLang()
    }

    fun onShareApp() {
        actions.value = Event(SettingsActions.SHARE_APP_CLICKED)
    }

    fun loadNotPinnedCurrencies(type: String, favorite: Boolean) {
        // save feed list into database
        launch {
            val list = withContext(Dispatchers.IO) {
                dataRepository.loadCurrenciesByFavorite(type, favorite)
            }
            Log.d("TAG", "loadAllCurrencies: $list")
            currenciesDb.value = Event(list)
        }
    }

    fun loadPinnedCurrencies(type: String, favorite: Boolean) {
        // save feed list into database
        launch {
            val list = withContext(Dispatchers.IO) {
                dataRepository.loadCurrenciesByFavorite(type, favorite)
            }
            Log.d("TAG", "loadPinnedCurrencies: $list")
            pinnedCurrenciesDb.value = Event(list)
        }
    }

    fun loadCurrenciesByName(text: String, type: String, favorite: Boolean) {
        // save feed list into database
        launch {
            val list = withContext(Dispatchers.IO) {
                dataRepository.loadCurrenciesByName(text, type, favorite)
            }
            Log.d("TAG", "loadAllCurrencies: $list")
            currenciesDb.value = Event(list)
        }
    }

}