package com.magma.aomlati.presentation.main

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.magma.aomlati.R
import com.magma.aomlati.databinding.ActivityMainBinding
import com.magma.aomlati.utils.Const
import com.magma.aomlati.utils.LocalHelper
import com.magma.aomlati.utils.ViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        val isDarkTheme = sharedPreferences.getBoolean(Const.PREF_IS_DARK_THEME, false)
        if (isDarkTheme)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        //lang
        LocalHelper.onCreate(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val navController = Navigation.findNavController(this, R.id.nav_host)
        val bottomNavigationView: BottomNavigationView = binding.bottomNavView
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }
}