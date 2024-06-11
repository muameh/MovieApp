package com.mehmetBaloglu.mymovieapp_v1.ui.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.mehmetBaloglu.mymovieapp_v1.R
import com.mehmetBaloglu.mymovieapp_v1.databinding.FragmentDetailBinding
import com.mehmetBaloglu.mymovieapp_v1.databinding.FragmentExploreBinding
import com.mehmetBaloglu.mymovieapp_v1.ui.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : Fragment() {
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesViewModel: MoviesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: MoviesViewModel by viewModels()
        moviesViewModel = tempViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    //------------------------------------------------------------
        binding.pickerReleaseDateMin.minValue = 1874
        binding.pickerReleaseDateMin.maxValue = 2024
        var showInitially_1 = 1990
        binding.pickerReleaseDateMin.value = showInitially_1
        binding.tvReleaseDateMin.text = showInitially_1.toString()
        binding.pickerReleaseDateMin.wrapSelectorWheel = false
        binding.pickerReleaseDateMin.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))

        binding.pickerReleaseDateMin.setOnValueChangedListener { picker, oldVal, newVal ->
            binding.tvReleaseDateMin.text = newVal.toString()
        }
    //------------------------------------------------------------
        binding.pickerReleaseDateMax.minValue = 1970
        binding.pickerReleaseDateMax.maxValue = 2024
        var showInitially_2 = 2000
        binding.pickerReleaseDateMax.value = showInitially_2
        binding.tvReleaseDateMax.text = showInitially_2.toString()
        binding.pickerReleaseDateMax.wrapSelectorWheel = false
        binding.pickerReleaseDateMax.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))

        binding.pickerReleaseDateMax.setOnValueChangedListener { picker, oldVal, newVal ->
            binding.tvReleaseDateMax.text = newVal.toString()
        }
    //------------------------------------------------------------
        val decimalValues = (1..100).map { String.format("%.1f", it / 10.0) }.toTypedArray()
        binding.pickerRateMin.minValue = 0
        binding.pickerRateMin.maxValue = decimalValues.size - 1
        binding.pickerRateMin.wrapSelectorWheel = false
        binding.pickerRateMin.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))

        val initialIndex = decimalValues.indexOf("5.0")
        binding.pickerRateMin.value = initialIndex
        binding.tvRateMin.text = decimalValues[initialIndex]

        binding.pickerRateMin.displayedValues = decimalValues

        binding.pickerRateMin.setOnValueChangedListener { picker, oldVal, newVal ->
            binding.tvRateMin.text = decimalValues[newVal]
        }

    //------------------------------------------------------------

        val xdecimalValues = (1..100).map { String.format("%.1f", it / 10.0) }.toTypedArray()
        binding.pickerRateMax.minValue = 0
        binding.pickerRateMax.maxValue = xdecimalValues.size - 1
        binding.pickerRateMax.wrapSelectorWheel = false
        binding.pickerRateMax.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))

        val xinitialIndex = xdecimalValues.indexOf("9.0")
        binding.pickerRateMax.value = xinitialIndex
        binding.tvRateMax.text = xdecimalValues[xinitialIndex]

        binding.pickerRateMax.displayedValues = xdecimalValues

        binding.pickerRateMax.setOnValueChangedListener { picker, oldVal, newVal ->
            binding.tvRateMax.text = xdecimalValues[newVal]
        }




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}