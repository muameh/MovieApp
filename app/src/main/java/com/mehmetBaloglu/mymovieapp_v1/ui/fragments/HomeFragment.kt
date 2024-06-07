package com.mehmetBaloglu.mymovieapp_v1.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.mehmetBaloglu.mymovieapp_v1.data.models.general_returns.FilmItem
import com.mehmetBaloglu.mymovieapp_v1.databinding.FragmentHomeBinding
import com.mehmetBaloglu.mymovieapp_v1.ui.adapters.AdapterForSeries
import com.mehmetBaloglu.mymovieapp_v1.ui.adapters.AdapterPopularMovies
import com.mehmetBaloglu.mymovieapp_v1.ui.adapters.AdapterTabLayout
import com.mehmetBaloglu.mymovieapp_v1.ui.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesViewModel: MoviesViewModel

    private lateinit var popularNewsAdapter: AdapterPopularMovies
    private lateinit var adapterTabLayout: AdapterTabLayout
    private lateinit var adapterForSeries: AdapterForSeries

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: MoviesViewModel by viewModels()
        moviesViewModel = tempViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPopularNewsRecyclerView()
        getTopRatedMovies()
        getPopularSeries()


        binding.tabLayoutFilms.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> getTopRatedMovies()
                    1 -> getMoviesInTheaters()
                    2 -> getUpcomingMovies()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.tabLayoutSeries.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> getPopularSeries()
                    1 -> getTopRatedSeries()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getTopRatedSeries() {
        adapterForSeries = AdapterForSeries(requireContext(), moviesViewModel)
        binding.recyclerViewForSeries.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterForSeries
        }
        activity?.let {
            moviesViewModel.topRatedTVSeriesList.observe(viewLifecycleOwner) {
                adapterForSeries.differ.submitList(it)
            }
        }
    }

    private fun getPopularSeries(){
        adapterForSeries = AdapterForSeries(requireContext(), moviesViewModel)
        binding.recyclerViewForSeries.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterForSeries
        }
        activity?.let {
            moviesViewModel.popularTVSeriesList.observe(viewLifecycleOwner) {
                adapterForSeries.differ.submitList(it)
            }
        }
    }

    private fun getTopRatedMovies() {
        adapterTabLayout = AdapterTabLayout(requireContext(), moviesViewModel)
        binding.recylerViewTabLayout.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterTabLayout
        }
        activity?.let {
            moviesViewModel.topRatedMoviesList.observe(viewLifecycleOwner) {
                adapterTabLayout.differ.submitList(it)
            }
        }
    }

    private fun getMoviesInTheaters() {
        adapterTabLayout = AdapterTabLayout(requireContext(), moviesViewModel)
        binding.recylerViewTabLayout.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterTabLayout
        }
        activity?.let {
            moviesViewModel.moviesInTheatersList.observe(viewLifecycleOwner) {
                adapterTabLayout.differ.submitList(it)
            }
        }
    }

    private fun getUpcomingMovies() {
        adapterTabLayout = AdapterTabLayout(requireContext(), moviesViewModel)
        binding.recylerViewTabLayout.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterTabLayout
        }
        activity?.let {
            moviesViewModel.upcomingMoviesList.observe(viewLifecycleOwner) {
                adapterTabLayout.differ.submitList(it)
            }
        }
    }

    private fun setupPopularNewsRecyclerView(){
        popularNewsAdapter = AdapterPopularMovies(requireContext(),moviesViewModel)
        binding.recyclerViewPopular.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter = popularNewsAdapter
        }
        activity?.let {
            moviesViewModel.popularMoviesList.observe(viewLifecycleOwner){
                popularNewsAdapter.differ.submitList(it)
                //updateUI(it)
            }
        }
    }

}