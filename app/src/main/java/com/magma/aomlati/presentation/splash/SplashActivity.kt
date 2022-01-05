package com.magma.aomlati.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.magma.aomlati.presentation.main.MainActivity
import com.magma.aomlati.presentation.onboarding.OnBoardingActivity
import com.magma.aomlati.utils.LocalHelper
import dagger.android.AndroidInjection
import com.magma.aomlati.utils.ViewModelFactory
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: SplashViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SplashViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        //lang
        LocalHelper.onCreate(this)

        val isShown = viewModel.isShowOnBoarding()
        val token = viewModel.getToken()
        if (isShown && token == null)
            goToRegisterActivity()
        else if (isShown && token != null)
            goToHomeActivity()
        else
            goToOnBoardingActivity()

    }

    private fun goToOnBoardingActivity() {
        val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToHomeActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToRegisterActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}