package com.magma.aomlati.presentation.main.home.crypto

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.magma.aomlati.databinding.FragmentCurrencyBinding
import com.magma.aomlati.model.Currency
import com.magma.aomlati.presentation.main.home.HomeViewModel
import com.magma.aomlati.utils.Const
import com.magma.aomlati.utils.EventObserver
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import com.magma.aomlati.utils.ViewModelFactory
import com.magma.aomlati.utils.listeners.RecyclerItemListener

class CryptoCurrencyFragment : Fragment(), RecyclerItemListener<Currency> {

    lateinit var binding: FragmentCurrencyBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var mCryptoCurrencyAdapter: CryptoCurrencyAdapter

    private val viewModelCrypto: HomeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyBinding.inflate(inflater, container, false)
        binding.viewModel = viewModelCrypto

        setUp()
        setupObservers()

        return binding.root
    }

    private fun setUp() {
        mCryptoCurrencyAdapter.setListener(this)
        mCryptoCurrencyAdapter.submitList(arrayListOf())
        binding.recyclerCurrencies.adapter = mCryptoCurrencyAdapter

        //load Db
        viewModelCrypto.loadAllCurrencies(Const.TYPE_CRYPTO)

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
                        viewModelCrypto.loadAllCurrencies(Const.TYPE_CRYPTO)
                    } else {
                        viewModelCrypto.loadCurrenciesByName(text, Const.TYPE_CRYPTO)
                    }
                }
            })

        /*binding.recyclerCurrencies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    // Scrolling up
                    binding.edtSearch.visibility = View.VISIBLE
                } else {
                    // Scrolling down
                    binding.edtSearch.visibility = View.GONE
                }
            }
        })*/
    }

    private fun setupObservers() {
        viewModelCrypto.currenciesDb.observe(
            viewLifecycleOwner,
            EventObserver
                (object :
                EventObserver.EventUnhandledContent<List<Currency>> {
                override fun onEventUnhandledContent(t: List<Currency>) {
                    mCryptoCurrencyAdapter.submitList(t)
                    if (t.isNotEmpty()) {
                        binding.progressBar.visibility = View.GONE
                    } else {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            })
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    companion object {
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        fun newInstance(): CryptoCurrencyFragment {
            val fragment = CryptoCurrencyFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

        private const val TAG = "CryptoCurrencyFragment"
    }

    override fun onItemClicked(item: Currency, index: Int) {

    }
}