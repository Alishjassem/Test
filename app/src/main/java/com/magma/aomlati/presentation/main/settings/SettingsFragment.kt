package com.magma.aomlati.presentation.main.settings

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.magma.aomlati.databinding.FragmentSettingsBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import com.magma.aomlati.utils.BindingUtils.hideKeyboard
import com.magma.aomlati.utils.ViewModelFactory

import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import com.magma.aomlati.R
import com.magma.aomlati.model.Currency
import com.magma.aomlati.presentation.main.MainActivity
import com.magma.aomlati.presentation.onboarding.FavoriteCurrencyAdapter
import com.magma.aomlati.presentation.share.ShareActivity
import com.magma.aomlati.utils.Const
import com.magma.aomlati.utils.EventObserver
import com.magma.aomlati.utils.LocalHelper
import com.magma.aomlati.utils.listeners.RecyclerItemListener
import java.lang.Exception

class SettingsFragment : Fragment(), RecyclerItemListener<Currency> {

    lateinit var binding: FragmentSettingsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var mPinnedCurrencyAdapter: FavoriteCurrencyAdapter

    @Inject
    lateinit var mAllCurrencyAdapter: FavoriteCurrencyAdapter

    private val viewModel: SettingsViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SettingsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setUp()
        setupObservers()

        return binding.root
    }

    private fun setUp() {
        val listLanguage = resources.getStringArray(R.array.languages)
        val listFontSizes = resources.getStringArray(R.array.font_sizes)
        val adapterLanguage =
            ArrayAdapter(requireActivity(), R.layout.item_setting_spinner, listLanguage)
        val adapterFontSizes =
            ArrayAdapter(requireActivity(), R.layout.item_setting_spinner, listFontSizes)
        binding.spnLanguage.adapter = adapterLanguage
        binding.spnFontSize.adapter = adapterFontSizes

        mPinnedCurrencyAdapter.setListener(this)
        mPinnedCurrencyAdapter.setType(Const.TYPE_CURRENCY_SETTING_FAVORITE)
        mPinnedCurrencyAdapter.submitList(arrayListOf())
        binding.recyclerPinnedCurrencies.adapter = mPinnedCurrencyAdapter

        mAllCurrencyAdapter.setListener(this)
        mAllCurrencyAdapter.setType(Const.TYPE_CURRENCY_SETTING_FAVORITE)
        mAllCurrencyAdapter.submitList(arrayListOf())
        binding.recyclerAllCurrencies.adapter = mAllCurrencyAdapter

        //load Db
        viewModel.loadNotPinnedCurrencies(Const.TYPE_FIAT, false)
        viewModel.loadPinnedCurrencies(Const.TYPE_FIAT, true)

        binding.edtSearch.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun afterTextChanged(editable: Editable) {
                    val text = editable.toString()
                    if (text.isEmpty()) {
                        viewModel.loadNotPinnedCurrencies(Const.TYPE_FIAT, false)
                    } else {
                        viewModel.loadCurrenciesByName(text, Const.TYPE_FIAT, false)
                    }
                }
            })

        val isDark = viewModel.isDarkTheme()
        binding.switchTheme.isChecked = isDark == true ||
                (requireActivity().resources.configuration.uiMode
                        and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            val isDarkTheme = viewModel.isDarkTheme()
            isDarkTheme?.let {
                if (isDarkTheme != isChecked) {
                    viewModel.setIsDarkTheme(isChecked)
                    if (isChecked) {
                        Log.d(TAG, "setUp: ")
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    } else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }

        when (viewModel.getLanguage()) {
            "en" -> binding.spnLanguage.setSelection(0)
            "ar" -> binding.spnLanguage.setSelection(1)
            "tr" -> binding.spnLanguage.setSelection(2)
        }
        binding.spnLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                i: Int,
                l: Long
            ) {
                // Your code here
                var lang = "en"
                when (i) {
                    0 -> lang = "en"
                    1 -> lang = "ar"
                    2 -> lang = "tr"
                }

                try {
                    viewModel.getLanguage()?.let {
                        if (!viewModel.getLanguage().equals(lang)) {
                            viewModel.setLanguage(lang)
                            restartApp(lang)
                        }
                    }
                } catch (ignored: Exception) {
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                return
            }
        }
    }

    private fun restartApp(lang: String) {
        LocalHelper.setLocale(binding.root.context, lang)
        val toMainActivity = Intent(context, MainActivity::class.java)
        toMainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(toMainActivity)
    }

    private fun setupObservers() {
        viewModel.actions.observe(
            viewLifecycleOwner, EventObserver(
                object : EventObserver.EventUnhandledContent<SettingsActions> {
                    override fun onEventUnhandledContent(t: SettingsActions) {
                        when (t) {
                            SettingsActions.SHARE_APP_CLICKED -> {
                                goToShareActivity()
                            }
                        }
                    }
                })
        )

        viewModel.pinnedCurrenciesDb.observe(
            viewLifecycleOwner,
            EventObserver
                (object :
                EventObserver.EventUnhandledContent<List<Currency>> {
                override fun onEventUnhandledContent(t: List<Currency>) {
                    mPinnedCurrencyAdapter.submitList(t)
                    if (t.isNotEmpty()) {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            })
        )
        viewModel.currenciesDb.observe(
            viewLifecycleOwner,
            EventObserver
                (object :
                EventObserver.EventUnhandledContent<List<Currency>> {
                override fun onEventUnhandledContent(t: List<Currency>) {
                    mAllCurrencyAdapter.submitList(t)
                    if (t.isNotEmpty()) {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            })
        )
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun goToShareActivity() {
        val intent = Intent(requireActivity(), ShareActivity::class.java)
        startActivity(intent)
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
        ): SettingsFragment {
            val fragment = SettingsFragment()
            val args = Bundle()
            args.putInt(ARG_PARAM1, position)
            args.putString(ARG_PARAM2, title)
            args.putString(ARG_PARAM3, description)
            args.putInt(ARG_PARAM4, imageResource)
            fragment.arguments = args
            return fragment
        }

        private const val TAG = "SettingFragment"
    }

    override fun onItemClicked(item: Currency, index: Int) {

    }
}