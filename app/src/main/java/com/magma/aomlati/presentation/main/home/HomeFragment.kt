package com.magma.aomlati.presentation.main.home

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import com.magma.aomlati.databinding.FragmentHomeBinding
import com.magma.aomlati.presentation.main.HomePagerAdapter
import com.magma.aomlati.presentation.main.home.crypto.CryptoCurrencyFragment
import com.magma.aomlati.presentation.main.home.currency.CurrencyFragment
import com.magma.aomlati.presentation.share.ShareActivity
import com.magma.aomlati.utils.BindingUtils.hideKeyboard
import com.magma.aomlati.utils.EventObserver
import com.magma.aomlati.utils.ViewModelFactory

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setViews()
        setupObservers()

        return binding.root
    }

    private fun setViews() {
        val fragmentList: ArrayList<Fragment> = arrayListOf()
        fragmentList.add(CurrencyFragment.newInstance())
        fragmentList.add(CryptoCurrencyFragment.newInstance())
        val adapter = HomePagerAdapter(this)
        adapter.setFragments(fragmentList)
        binding.homePager.adapter = adapter
        binding.homePager.isUserInputEnabled = false

        val tabCurrency = binding.tabCurrency
        val tabCryptocurrency = binding.tabCryptocurrency
        val tabSelected = binding.select

        tabCurrency.setOnClickListener {
            val config: Configuration = resources.configuration
            if (config.layoutDirection == View.LAYOUT_DIRECTION_RTL)
                tabSelected.animate().x(tabCryptocurrency.width.toFloat()).duration = 100
            else tabSelected.animate().x(0F).duration = 100
            binding.homePager.currentItem = 0
        }
        tabCryptocurrency.setOnClickListener {
            val config: Configuration = resources.configuration
            if (config.layoutDirection == View.LAYOUT_DIRECTION_RTL)
                tabSelected.animate().x(0F).duration = 100
            else tabSelected.animate().x(tabCryptocurrency.width.toFloat()).duration = 100
            binding.homePager.currentItem = 1
        }

    }

    private fun setupObservers() {
        viewModel.actions.observe(
            viewLifecycleOwner, EventObserver(
                object : EventObserver.EventUnhandledContent<HomeActions> {
                    override fun onEventUnhandledContent(t: HomeActions) {
                        when (t) {
                            HomeActions.SHARE_APP_CLICKED -> {
                                goToShareActivity()
                            }
                        }
                    }
                })
        )
    }

    private fun goToShareActivity() {
        val intent = Intent(requireActivity(), ShareActivity::class.java)
        startActivity(intent)
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    companion object {
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val ARG_PARAM3 = "param3"
        private const val ARG_PARAM4 = "param4"
        fun newInstance(
            position: Int,
            title: String?,
            description: String?,
            imageResource: Int
        ): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putInt(ARG_PARAM1, position)
            args.putString(ARG_PARAM2, title)
            args.putString(ARG_PARAM3, description)
            args.putInt(ARG_PARAM4, imageResource)
            fragment.arguments = args
            return fragment
        }

        private const val TAG = "HomeFragment"
    }
}