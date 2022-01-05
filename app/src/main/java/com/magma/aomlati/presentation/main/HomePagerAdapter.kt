package com.magma.aomlati.presentation.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomePagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {

    private var fragments: List<Fragment> = arrayListOf()

    fun setFragments(fragments: List<Fragment>) {
        this.fragments = fragments
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}