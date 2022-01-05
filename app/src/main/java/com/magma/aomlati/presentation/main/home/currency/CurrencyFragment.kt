package com.magma.aomlati.presentation.main.home.currency

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.magma.aomlati.data.remote.controller.ErrorManager
import com.magma.aomlati.data.remote.controller.Resource
import com.magma.aomlati.data.remote.controller.ResponseWrapper
import com.magma.aomlati.data.remote.responses.LoginResponse
import com.magma.aomlati.databinding.FragmentCurrencyBinding
import com.magma.aomlati.model.Currency
import com.magma.aomlati.utils.EventObserver
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import com.magma.aomlati.utils.ViewModelFactory
import com.magma.aomlati.utils.listeners.RecyclerItemListener

import com.magma.aomlati.presentation.main.home.HomeViewModel
import com.magma.aomlati.utils.Const

class CurrencyFragment : Fragment(), RecyclerItemListener<Currency> {

    lateinit var binding: FragmentCurrencyBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var mCurrencyAdapter: CurrencyAdapter

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setUp()
        setupObservers()

        return binding.root
    }

    private fun setUp() {
        mCurrencyAdapter.setListener(this)
        mCurrencyAdapter.submitList(arrayListOf())
        binding.recyclerCurrencies.adapter = mCurrencyAdapter

        //load Db
        viewModel.loadAllCurrencies(Const.TYPE_FIAT)

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
                        viewModel.loadAllCurrencies(Const.TYPE_FIAT)
                    } else {
                        viewModel.loadCurrenciesByName(text, Const.TYPE_FIAT)
                    }
                }
            })
    }

    private fun setupObservers() {
        viewModel.currenciesDb.observe(
            viewLifecycleOwner,
            EventObserver
                (object :
                EventObserver.EventUnhandledContent<List<Currency>> {
                override fun onEventUnhandledContent(t: List<Currency>) {
                    val sorted = t.sortedByDescending { it.isFavorite }
                    mCurrencyAdapter.submitList(sorted)
                    if (t.isNotEmpty()) {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }))

        // listen to api result
        viewModel.currenciesResponse.observe(
            requireActivity(),
            EventObserver
                (object :
                EventObserver.EventUnhandledContent<Resource<ResponseWrapper<ArrayList<Currency>>>> {
                override fun onEventUnhandledContent(t: Resource<ResponseWrapper<ArrayList<Currency>>>) {
                    when (t) {
                        is Resource.Loading -> {
                            // show progress bar and remove no data layout while loading
                            //binding.progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            // response is ok get the data and display it in the list
                            //binding.progressBar.visibility = View.GONE
                            val response = t.response as ResponseWrapper<*>

                            @Suppress("UNCHECKED_CAST")
                            val currenciesResponse = response.successResult as ArrayList<Currency>
                            Log.d(TAG, "currenciesResponse: $currenciesResponse")
                            viewModel.deleteAndSaveCurrencies(currenciesResponse)
                        }
                        is Resource.DataError -> {
                            //binding.progressBar.visibility = View.GONE
                            // usually this happening when there is server error
                            val response = t.response as ErrorManager
                            if (response.status == 401) {
                                //Expired Token
                                viewModel.doServerLogin()
                            }
                            Log.d(TAG, "registerResponse: DataError $response")
                        }
                        is Resource.Exception -> {
                            //binding.progressBar.visibility = View.GONE
                            // usually this happening when there is no internet
                            val response = t.response
                            Log.d(TAG, "onEventUnhandledContent: $response")
                        }
                    }
                }
            })
        )

        viewModel.loginResponse.observe(
            requireActivity(),
            EventObserver
                (object :
                EventObserver.EventUnhandledContent<Resource<ResponseWrapper<LoginResponse>>> {
                override fun onEventUnhandledContent(t: Resource<ResponseWrapper<LoginResponse>>) {
                    when (t) {
                        is Resource.Loading -> {
                            // show progress bar and remove no data layout while loading
                            //binding.progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            // response is ok get the data and display it in the list
                            //binding.progressBar.visibility = View.GONE
                            val response = t.response as ResponseWrapper<*>
                            val loginResponse = response.successResult as LoginResponse
                            Log.d("TAG", "loginResponse: $loginResponse")
                            viewModel.saveToken(loginResponse)
                            viewModel.doServerGetCurrenciesRates()
                        }
                        is Resource.DataError -> {
                            //binding.progressBar.visibility = View.GONE
                            // usually this happening when there is server error
                            val response = t.response as ErrorManager
                            Log.d("TAG", "loginResponse: DataError $response")
                        }
                        is Resource.Exception -> {
                            //binding.progressBar.visibility = View.GONE
                            // usually this happening when there is no internet
                            val response = t.response
                            Log.d("TAG", "onEventUnhandledContent: $response")
                        }
                    }
                }
            })
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)

        viewModel.doServerGetCurrenciesRates()
    }

    companion object {
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        fun newInstance(): CurrencyFragment {
            val fragment = CurrencyFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

        private const val TAG = "CurrencyFragment"
    }

    override fun onItemClicked(item: Currency, index: Int) {

    }
}