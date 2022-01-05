package com.magma.aomlati.presentation.main.converter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.magma.aomlati.R
import com.magma.aomlati.databinding.FragmentConverterBinding
import com.magma.aomlati.model.Currency
import com.magma.aomlati.presentation.share.ShareActivity
import com.magma.aomlati.utils.BindingUtils
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import com.magma.aomlati.utils.BindingUtils.hideKeyboard
import com.magma.aomlati.utils.Const
import com.magma.aomlati.utils.EventObserver
import com.magma.aomlati.utils.ViewModelFactory

class ConverterFragment : Fragment() {

    lateinit var binding: FragmentConverterBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ConverterViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ConverterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConverterBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setUp()
        setupObservers()

        return binding.root
    }

    private fun setUp() {
        /*val listLanguage = resources.getStringArray(R.array.languages)
        val listFontSizes = resources.getStringArray(R.array.font_sizes)
        val adapterFrom =
            ArrayAdapter(requireActivity(), R.layout.item_converter_spinner, listLanguage)
        val adapterTo =
            ArrayAdapter(requireActivity(), R.layout.item_converter_spinner, listFontSizes)
        binding.spnFrom.adapter = adapterFrom
        binding.spnTo.adapter = adapterTo*/

        //load Db
        viewModel.loadAllCurrencies(Const.TYPE_FIAT)
    }

    private fun setupObservers() {
        viewModel.actions.observe(
            viewLifecycleOwner, EventObserver(
                object : EventObserver.EventUnhandledContent<ConverterActions> {
                    override fun onEventUnhandledContent(t: ConverterActions) {
                        when (t) {
                            ConverterActions.SHARE_APP_CLICKED -> {
                                goToShareActivity()
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
                    setSpinners(t)
                }
            })
        )
    }

    private fun setSpinners(currenciesResponse: List<Currency>) {
        val sorted = currenciesResponse.sortedBy { it.isFavorite }
        val names = arrayListOf<String>()
        sorted.forEach {
            it.symbol?.let { it1 -> names.add(it1) }
        }
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_converter_spinner,
            names.toMutableList()
        )
        binding.spnFrom.adapter = adapter
        binding.spnFrom.onItemSelectedListener =
            (object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val selectedCurrency = sorted[p2]
                    BindingUtils.setTextTime(
                        binding.txtLastUpdateFrom,
                        selectedCurrency.lastPriceUpdate
                    )
                    binding.txtBaseFrom.text = selectedCurrency.lastPrice.toString()
                    binding.txtValueFrom.text = selectedCurrency.lastPrice.toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            })
        val adapterTo = ArrayAdapter(
            requireContext(),
            R.layout.item_converter_spinner,
            names.toMutableList()
        )
        binding.spnTo.adapter = adapterTo
        binding.spnTo.onItemSelectedListener =
            (object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val selectedCurrency = sorted[p2]
                    BindingUtils.setTextTime(
                        binding.txtLastUpdateTo,
                        selectedCurrency.lastPriceUpdate
                    )
                    binding.txtBaseTo.text = selectedCurrency.lastPrice.toString()
                    binding.txtValueTo.text = selectedCurrency.lastPrice.toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            })
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
        ): ConverterFragment {
            val fragment = ConverterFragment()
            val args = Bundle()
            args.putInt(ARG_PARAM1, position)
            args.putString(ARG_PARAM2, title)
            args.putString(ARG_PARAM3, description)
            args.putInt(ARG_PARAM4, imageResource)
            fragment.arguments = args
            return fragment
        }

        //private const val TAG = "LoginFragment"
    }
}