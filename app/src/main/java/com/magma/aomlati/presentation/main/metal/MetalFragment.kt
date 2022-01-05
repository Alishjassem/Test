package com.magma.aomlati.presentation.main.metal

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
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
import com.magma.aomlati.databinding.FragmentMetalBinding
import com.magma.aomlati.model.Metal
import com.magma.aomlati.presentation.share.ShareActivity
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import com.magma.aomlati.utils.BindingUtils.hideKeyboard
import com.magma.aomlati.utils.EventObserver
import com.magma.aomlati.utils.ViewModelFactory
import com.magma.aomlati.utils.listeners.RecyclerItemListener

class MetalFragment : Fragment(), RecyclerItemListener<Metal> {

    lateinit var binding: FragmentMetalBinding

    @Inject
    lateinit var mMetalAdapter: MetalAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MetalViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MetalViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMetalBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setViews()
        setUp()
        setupObservers()

        return binding.root
    }

    private fun setUp() {
        mMetalAdapter.setListener(this)
        mMetalAdapter.submitList(arrayListOf())
        binding.recyclerMetal.adapter = mMetalAdapter

        //load Db
        viewModel.loadAllMetals(true)
    }

    private fun setViews() {
        val tabTurkish = binding.tabTurkish
        val tabNotTurkish = binding.tabNotTurkish
        val tabSelected = binding.select

        tabTurkish.setOnClickListener {
            val config: Configuration = resources.configuration
            if (config.layoutDirection == View.LAYOUT_DIRECTION_RTL)
                tabSelected.animate().x(tabTurkish.width.toFloat()).duration = 100
            else tabSelected.animate().x(0F).duration = 100
            viewModel.loadAllMetals(true)
        }
        tabNotTurkish.setOnClickListener {
            val config: Configuration = resources.configuration
            if (config.layoutDirection == View.LAYOUT_DIRECTION_RTL)
                tabSelected.animate().x(0F).duration = 100
            else tabSelected.animate().x(tabNotTurkish.width.toFloat()).duration = 100
            viewModel.loadAllMetals(false)
        }
    }

    private fun setupObservers() {
        viewModel.actions.observe(
            viewLifecycleOwner, EventObserver(
                object : EventObserver.EventUnhandledContent<MetalActions> {
                    override fun onEventUnhandledContent(t: MetalActions) {
                        when (t) {
                            MetalActions.SHARE_APP_CLICKED -> {
                                goToShareActivity()
                            }
                        }
                    }
                }))

        viewModel.metalsDb.observe(
            viewLifecycleOwner,
            EventObserver
                (object :
                EventObserver.EventUnhandledContent<List<Metal>> {
                override fun onEventUnhandledContent(t: List<Metal>) {
                    mMetalAdapter.submitList(t)
                    if (t.isNotEmpty()) {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }))

        // listen to api result
        viewModel.metalResponse.observe(
            requireActivity(),
            EventObserver
                (object :
                EventObserver.EventUnhandledContent<Resource<ResponseWrapper<ArrayList<Metal>>>> {
                override fun onEventUnhandledContent(t: Resource<ResponseWrapper<ArrayList<Metal>>>) {
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
                            val metalResponse = response.successResult as ArrayList<Metal>
                            Log.d(TAG, "metalResponse: $metalResponse")
                            viewModel.deleteAndSaveMetals(metalResponse)
                            //binding.tabTurkish.performClick()
                            //mMetalAdapter.submitList(metalResponse)
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
                            Log.d(TAG, "metalResponse: DataError $response")
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
                            viewModel.saveToken(loginResponse)
                            viewModel.doServerGetMetalRates()
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

        viewModel.doServerGetMetalRates()
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
        ): MetalFragment {
            val fragment = MetalFragment()
            val args = Bundle()
            args.putInt(ARG_PARAM1, position)
            args.putString(ARG_PARAM2, title)
            args.putString(ARG_PARAM3, description)
            args.putInt(ARG_PARAM4, imageResource)
            fragment.arguments = args
            return fragment
        }

        private const val TAG = "MetalFragment"
    }

    override fun onItemClicked(item: Metal, index: Int) {

    }
}