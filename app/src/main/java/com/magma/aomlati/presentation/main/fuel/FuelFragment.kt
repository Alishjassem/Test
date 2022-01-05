package com.magma.aomlati.presentation.main.fuel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.magma.aomlati.R
import com.magma.aomlati.data.remote.controller.ErrorManager
import com.magma.aomlati.data.remote.controller.Resource
import com.magma.aomlati.data.remote.controller.ResponseWrapper
import com.magma.aomlati.data.remote.responses.LoginResponse
import com.magma.aomlati.databinding.FragmentFuelBinding
import com.magma.aomlati.model.Fuel
import com.magma.aomlati.presentation.share.ShareActivity
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import com.magma.aomlati.utils.BindingUtils.hideKeyboard
import com.magma.aomlati.utils.EventObserver
import com.magma.aomlati.utils.ViewModelFactory
import com.magma.aomlati.utils.listeners.RecyclerItemListener

class FuelFragment : Fragment(), RecyclerItemListener<Fuel> {

    lateinit var binding: FragmentFuelBinding

    @Inject
    lateinit var mFuelAdapter: FuelAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: FuelViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FuelViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFuelBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setUp()
        setupObservers()

        return binding.root
    }

    private fun setUp() {
        mFuelAdapter.setListener(this)
        mFuelAdapter.submitList(arrayListOf())
        binding.recyclerFuel.adapter = mFuelAdapter

        viewModel.loadAllFuels()
    }

    private fun setupObservers() {

        viewModel.actions.observe(
            viewLifecycleOwner, EventObserver(
                object : EventObserver.EventUnhandledContent<FuelActions> {
                    override fun onEventUnhandledContent(t: FuelActions) {
                        when (t) {
                            FuelActions.SHARE_APP_CLICKED -> {
                                goToShareActivity()
                            }
                        }
                    }
                }))


        viewModel.fuelsDb.observe(
            viewLifecycleOwner,
            EventObserver
                (object :
                EventObserver.EventUnhandledContent<List<Fuel>> {
                override fun onEventUnhandledContent(t: List<Fuel>) {
                    mFuelAdapter.submitList(t)
                    if (t.isNotEmpty()) {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }))

        // listen to api result
        viewModel.fuelResponse.observe(
            requireActivity(),
            EventObserver
                (object :
                EventObserver.EventUnhandledContent<Resource<ResponseWrapper<ArrayList<Fuel>>>> {
                override fun onEventUnhandledContent(t: Resource<ResponseWrapper<ArrayList<Fuel>>>) {
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
                            val fuelResponse = response.successResult as ArrayList<Fuel>
                            Log.d(TAG, "fuelResponse: $fuelResponse")
                            viewModel.deleteAndSaveFuels(fuelResponse)
                            mFuelAdapter.submitList(fuelResponse)
                        }
                        is Resource.DataError -> {
                            binding.progressBar.visibility = View.GONE
                            // usually this happening when there is server error
                            val response = t.response as ErrorManager
                            Toast.makeText(
                                requireActivity(),
                                getString(R.string.expired_token),
                                Toast.LENGTH_SHORT
                            ).show()
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
            }))

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
                            viewModel.saveToken(loginResponse)
                            viewModel.doServerGetFuelRates()
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

        viewModel.doServerGetFuelRates()
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
        ): FuelFragment {
            val fragment = FuelFragment()
            val args = Bundle()
            args.putInt(ARG_PARAM1, position)
            args.putString(ARG_PARAM2, title)
            args.putString(ARG_PARAM3, description)
            args.putInt(ARG_PARAM4, imageResource)
            fragment.arguments = args
            return fragment
        }

        private const val TAG = "LoginFragment"
    }

    override fun onItemClicked(item: Fuel, index: Int) {

    }
}