package com.magma.aomlati.presentation.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import dagger.android.support.AndroidSupportInjection
import com.magma.aomlati.R
import com.magma.aomlati.databinding.FragmentOnboarding3Binding
import com.magma.aomlati.presentation.main.MainActivity
import com.magma.aomlati.utils.ViewModelFactory
import javax.inject.Inject

class OnBoarding3Fragment : Fragment() {
    lateinit var binding: FragmentOnboarding3Binding
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
        binding = FragmentOnboarding3Binding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setUp()

        return binding.root
    }

    private fun setUp() {
        /*if (position == 0){
            binding.btnBack.visibility = View.GONE
        }*/
        binding.btnBack.setOnClickListener {
            viewPager?.currentItem = viewPager?.currentItem?.minus(1)!!
        }
        binding.btnContinue.setOnClickListener {
            viewPager?.currentItem = viewPager?.currentItem?.plus(1)!!
        }
    }

    private fun doAnimation() {
        val fromLoc = IntArray(2)
        binding.imgEllipseLeft.getLocationOnScreen(fromLoc)
        val startX = fromLoc[0].toFloat() - binding.imgEllipseLeft.width + 150
        val startY = fromLoc[1].toFloat()

        val toLoc = IntArray(2)
        binding.imgEllipseRight.getLocationOnScreen(toLoc)
        val startX2 = toLoc[0].toFloat()
        val startY2 = toLoc[1].toFloat()

        binding.imgEllipseLeft.animate().translationX(startX2).translationY(0F).duration = 500
        binding.imgEllipseRight.animate().translationX(startX).translationY(0F).duration = 500
    }

    override fun onStart() {
        super.onStart()
        viewPager = requireActivity().findViewById(R.id.viewPager)
    }

    override fun onResume() {
        super.onResume()
        doAnimation()
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
        ): OnBoarding3Fragment {
            val fragment = OnBoarding3Fragment()
            val args = Bundle()
            args.putInt(ARG_PARAM1, position)
            args.putString(ARG_PARAM2, title)
            args.putString(ARG_PARAM3, description)
            args.putInt(ARG_PARAM4, imageResource)
            fragment.arguments = args
            return fragment
        }

        fun newInstance(
        ): OnBoarding3Fragment {
            return OnBoarding3Fragment()
        }
    }
}