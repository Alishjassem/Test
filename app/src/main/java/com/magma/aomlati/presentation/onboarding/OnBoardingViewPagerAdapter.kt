package com.magma.aomlati.presentation.onboarding

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardingViewPagerAdapter(fragmentActivity: FragmentActivity, private val context: Context) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnBoardingFragment.newInstance()
            1 -> OnBoarding2Fragment.newInstance()
            2 -> OnBoarding3Fragment.newInstance()
            3 -> OnBoarding4Fragment.newInstance()
            4 -> OnBoarding5Fragment.newInstance()
            else -> WelcomeFavoriteFragment.newInstance()
        }
    }

    override fun getItemCount(): Int {
        return 6
    }
}