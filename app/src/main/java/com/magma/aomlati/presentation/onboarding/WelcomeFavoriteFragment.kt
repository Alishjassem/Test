package com.magma.aomlati.presentation.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import dagger.android.support.AndroidSupportInjection
import com.magma.aomlati.R
import com.magma.aomlati.data.remote.controller.ErrorManager
import com.magma.aomlati.data.remote.controller.Resource
import com.magma.aomlati.databinding.FragmentWelcomeFavoriteBinding
import com.magma.aomlati.model.Currency
import com.magma.aomlati.presentation.main.MainActivity
import com.magma.aomlati.utils.EventObserver
import com.magma.aomlati.utils.ViewModelFactory
import com.magma.aomlati.utils.listeners.RecyclerItemListener
import com.magma.aomlati.data.remote.controller.ResponseWrapper
import com.magma.aomlati.data.remote.responses.LoginResponse
import com.magma.aomlati.utils.Const
import javax.inject.Inject

class WelcomeFavoriteFragment : Fragment(), RecyclerItemListener<Currency> {
    lateinit var binding: FragmentWelcomeFavoriteBinding
    private var viewPager: ViewPager2? = null
    private val TAG = javaClass.name

    @Inject
    lateinit var mFavoriteCurrencyAdapter: FavoriteCurrencyAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: OnBoardingViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[OnBoardingViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeFavoriteBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setUp()
        setObservers()

        return binding.root
    }

    private fun setUp() {
        mFavoriteCurrencyAdapter.setListener(this)
        mFavoriteCurrencyAdapter.submitList(arrayListOf())
        binding.recyclerCurrencies.adapter = mFavoriteCurrencyAdapter

        if (viewModel.getToken() != null) {
            //load Db
            //viewModel.loadAllCurrencies()
            //fetch currencies api
            viewModel.doServerGetCurrencies(Const.TYPE_FIAT)
        } else {
            viewModel.doServerLogin()
        }

        binding.btnContinue.setOnClickListener {
            viewPager?.currentItem = viewPager?.currentItem?.plus(1)!!
        }

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
                        viewModel.loadAllCurrencies()
                    } else {
                        viewModel.loadCurrenciesByName(text)
                    }
                }
            })
    }

    private fun setObservers() {

        viewModel.actions.observe(
            viewLifecycleOwner, EventObserver(
                object : EventObserver.EventUnhandledContent<FavoriteActions> {
                    override fun onEventUnhandledContent(t: FavoriteActions) {
                        when (t) {
                            FavoriteActions.CONTINUE_CLICKED -> {
                                val currenciesList = mFavoriteCurrencyAdapter.currentList
                                viewModel.updateFavoriteCurrencies(currenciesList)
                                goToHomeActivity()
                            }
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
                    mFavoriteCurrencyAdapter.submitList(t)
                    if (t.isNotEmpty()) {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            })
        )

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
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            // response is ok get the data and display it in the list
                            binding.progressBar.visibility = View.GONE
                            val response = t.response as ResponseWrapper<*>

                            @Suppress("UNCHECKED_CAST")
                            val currenciesResponse = response.successResult as ArrayList<Currency>
                            Log.d(TAG, "currenciesResponse: $currenciesResponse")
                            viewModel.saveCurrencies(currenciesResponse)
                            mFavoriteCurrencyAdapter.submitList(currenciesResponse)
                        }
                        is Resource.DataError -> {
                            binding.progressBar.visibility = View.GONE
                            // usually this happening when there is server error
                            val response = t.response as ErrorManager
                            if (response.status == 401) {
                                //Expired Token
                                viewModel.doServerLogin()
                            }
                            Log.d(TAG, "registerResponse: DataError $response")
                        }
                        is Resource.Exception -> {
                            binding.progressBar.visibility = View.GONE
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
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            // response is ok get the data and display it in the list
                            binding.progressBar.visibility = View.GONE
                            val response = t.response as ResponseWrapper<*>
                            val loginResponse = response.successResult as LoginResponse
                            Log.d("TAG", "loginResponse: $loginResponse")
                            viewModel.setToken(loginResponse.token)
                            viewModel.doServerGetCurrencies(Const.TYPE_FIAT)
                        }
                        is Resource.DataError -> {
                            binding.progressBar.visibility = View.GONE
                            // usually this happening when there is server error
                            val response = t.response as ErrorManager
                            Log.d("TAG", "loginResponse: DataError $response")
                        }
                        is Resource.Exception -> {
                            binding.progressBar.visibility = View.GONE
                            // usually this happening when there is no internet
                            val response = t.response
                            Log.d("TAG", "onEventUnhandledContent: $response")
                        }
                    }
                }
            })
        )
    }

    override fun onStart() {
        super.onStart()
        viewPager = requireActivity().findViewById(R.id.viewPager)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    private fun goToHomeActivity() {
        viewModel.setIsShowingOnboard(true)
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
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
        ): WelcomeFavoriteFragment {
            val fragment = WelcomeFavoriteFragment()
            val args = Bundle()
            args.putInt(ARG_PARAM1, position)
            args.putString(ARG_PARAM2, title)
            args.putString(ARG_PARAM3, description)
            args.putInt(ARG_PARAM4, imageResource)
            fragment.arguments = args
            return fragment
        }

        fun newInstance(
        ): WelcomeFavoriteFragment {
            return WelcomeFavoriteFragment()
        }
    }

    override fun onItemClicked(item: Currency, index: Int) {

    }
}