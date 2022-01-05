package com.magma.aomlati.presentation.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import dagger.android.support.AndroidSupportInjection
import com.magma.aomlati.R
import com.magma.aomlati.databinding.FragmentOnboardingBinding
import com.magma.aomlati.presentation.main.MainActivity
import com.magma.aomlati.utils.ViewModelFactory
import javax.inject.Inject

class OnBoardingFragment : Fragment() {
    lateinit var binding: FragmentOnboardingBinding
    private var viewPager: ViewPager2? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: OnBoardingViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[OnBoardingViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setUp()

        return binding.root
    }

    private fun setUp() {
        /*if (position == 0){
            binding.btnBack.visibility = View.GONE
        }*/
        binding.btnContinue.setOnClickListener {
            viewPager?.currentItem = viewPager?.currentItem?.plus(1)!!
        }
    }

    override fun onStart() {
        super.onStart()
        viewPager = requireActivity().findViewById(R.id.viewPager)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    private fun goToRegisterActivity() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    fun hideKeyboard() {
        val view = binding.root
        val imm =
            requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
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
        ): OnBoardingFragment {
            val fragment = OnBoardingFragment()
            val args = Bundle()
            args.putInt(ARG_PARAM1, position)
            args.putString(ARG_PARAM2, title)
            args.putString(ARG_PARAM3, description)
            args.putInt(ARG_PARAM4, imageResource)
            fragment.arguments = args
            return fragment
        }

        fun newInstance(
        ): OnBoardingFragment {
            return OnBoardingFragment()
        }
    }
}