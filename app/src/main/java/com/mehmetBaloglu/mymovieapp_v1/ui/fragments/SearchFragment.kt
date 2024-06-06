package com.mehmetBaloglu.mymovieapp_v1.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.mehmetBaloglu.mymovieapp_v1.R
import com.mehmetBaloglu.mymovieapp_v1.data.models.general_returns.FilmItem
import com.mehmetBaloglu.mymovieapp_v1.data.models.series.SeriesItem
import com.mehmetBaloglu.mymovieapp_v1.databinding.FragmentHomeBinding
import com.mehmetBaloglu.mymovieapp_v1.databinding.FragmentSearchBinding
import com.mehmetBaloglu.mymovieapp_v1.ui.adapters.AdapterForSeries
import com.mehmetBaloglu.mymovieapp_v1.ui.adapters.AdapterSearchMovies
import com.mehmetBaloglu.mymovieapp_v1.ui.adapters.AdapterSearchSeries
import com.mehmetBaloglu.mymovieapp_v1.ui.adapters.AdapterTabLayout
import com.mehmetBaloglu.mymovieapp_v1.ui.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesViewModel: MoviesViewModel

    private lateinit var adapterSearchSeries: AdapterSearchSeries
    private lateinit var adapterSearchMovies: AdapterSearchMovies


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: MoviesViewModel by viewModels()
        moviesViewModel = tempViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var job : Job? = null
        binding.searchText.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(1000)
                editable?.let {
                    if (editable.toString().isNotEmpty()){
                        moviesViewModel.searchMovies(editable.toString())
                        moviesViewModel.searchSeries(editable.toString())
                    }
                }
            }
        }

        getSearchedSeries()
        getSearchedMovies()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getSearchedSeries() {
        adapterSearchSeries = AdapterSearchSeries(requireContext(), moviesViewModel)
        binding.recyclerViewSearchSeries.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterSearchSeries
        }
        activity?.let {
            moviesViewModel.searchListForSeries.observe(viewLifecycleOwner) {
                adapterSearchSeries.differ.submitList(it)
                if (it.isNotEmpty())
                    binding.textViewInTVSeries.visibility = View.VISIBLE
            }
        }
    }

    private fun getSearchedMovies() {
        adapterSearchMovies = AdapterSearchMovies(requireContext(), moviesViewModel)
        binding.recyclerViewSearchFilm.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterSearchMovies
        }
        activity?.let {
            moviesViewModel.searchListForMovies.observe(viewLifecycleOwner) {
                adapterSearchMovies.differ.submitList(it)
                if (it.isNotEmpty())
                    binding.textViewInMovies.visibility = View.VISIBLE
            }
        }
    }




}