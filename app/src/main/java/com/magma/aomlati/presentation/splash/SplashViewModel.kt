package com.magma.aomlati.presentation.splash

import androidx.lifecycle.ViewModel
import com.magma.aomlati.data.repository.DataRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SplashViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    override val coroutineContext: CoroutineContext,
) : ViewModel(), CoroutineScope {

    fun isShowOnBoarding(): Boolean {
        return dataRepository.isShownOnBoarding()
    }

    fun getToken(): String? {
        return dataRepository.getApiToken()
    }

}