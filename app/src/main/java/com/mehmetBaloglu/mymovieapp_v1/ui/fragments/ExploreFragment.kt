package com.mehmetBaloglu.mymovieapp_v1.ui.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.mehmetBaloglu.mymovieapp_v1.R
import com.mehmetBaloglu.mymovieapp_v1.databinding.FragmentDetailBinding
import com.mehmetBaloglu.mymovieapp_v1.databinding.FragmentExploreBinding
import com.mehmetBaloglu.mymovieapp_v1.ui.adapters.AdapterSearchMovies
import com.mehmetBaloglu.mymovieapp_v1.ui.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : Fragment() {
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesViewModel: MoviesViewModel

    private lateinit var adapterSearchMovies: AdapterSearchMovies


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

    //------------------------------------------------------------
        binding.pickerRuntimeMin.minValue = 0
        binding.pickerRuntimeMin.maxValue = 400
        var showInitially_3 = 120
        binding.pickerRuntimeMin.value = showInitially_3
        binding.tvRuntimeMin.text = showInitially_3.toString()
        binding.pickerRuntimeMin.wrapSelectorWheel = false
        binding.pickerRuntimeMin.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))

        binding.pickerRuntimeMin.setOnValueChangedListener { picker, oldVal, newVal ->
            binding.tvRuntimeMin.text = newVal.toString()
        }

    //------------------------------------------------------------

        binding.pickerRuntimeMax.minValue = 0
        binding.pickerRuntimeMax.maxValue = 400
        var showInitially_4 = 200
        binding.pickerRuntimeMax.value = showInitially_4
        binding.tvRuntimeMax.text = showInitially_4.toString()
        binding.pickerRuntimeMax.wrapSelectorWheel = false
        binding.pickerRuntimeMax.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))

        binding.pickerRuntimeMax.setOnValueChangedListener { picker, oldVal, newVal ->
            binding.tvRuntimeMax.text = newVal.toString()

        }

    //------------------------------------------------------------

        val valuesList = ArrayList<Int>()
        var value = 0
        while (value <= 500) {
            valuesList.add(value)
            value += 50
        }
        val valuesArray = valuesList.toIntArray()

        binding.pickerUserVoteMin.minValue = 0
        binding.pickerUserVoteMin.maxValue = valuesArray.size - 1
        var showInitiallyx = 0
        binding.tvMinUser.text = showInitiallyx.toString()
        binding.pickerUserVoteMin.displayedValues = valuesArray.map { it.toString() }.toTypedArray()
        binding.pickerUserVoteMin.wrapSelectorWheel = false
        binding.pickerUserVoteMin.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))

        binding.pickerUserVoteMin.setOnValueChangedListener { picker, oldVal, newVal ->
            val selectedValue = valuesArray[newVal]
            binding.tvMinUser.text = selectedValue.toString()
        }

        //------------------------------------------------------------
        val genres = arrayOf(
            "Action", "Adventure", "Animation", "Comedy", "Crime", "Documentary",
            "Drama", "Family", "Fantasy", "History", "Horror", "Music", "Mystery",
            "Romance", "Science Fiction", "TV Movie", "Thriller", "War", "Western"
        )

        val selectedGenresSet = HashSet<String>()

        binding.pickerGenres.minValue = 0
        binding.pickerGenres.maxValue = genres.size - 1
        binding.pickerGenres.displayedValues = genres
        binding.pickerGenres.wrapSelectorWheel = false
        binding.pickerGenres.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))

        binding.button.setOnClickListener {
            val selectedGenre = genres[binding.pickerGenres.value]
            if (selectedGenresSet.contains(selectedGenre)) {
                Toast.makeText(requireContext(), "$selectedGenre already selected!", Toast.LENGTH_SHORT).show()
            } else {
                selectedGenresSet.add(selectedGenre)
                binding.tvSelectedGenres.text = selectedGenresSet.joinToString(", ")
                checkClearAllButtonVisibility(selectedGenresSet)
            }
        }


        binding.buttonSearch.setOnClickListener{

            var min_year = binding.tvReleaseDateMin.text.toString() + "-01-01"
            var max_year = binding.tvReleaseDateMax.text.toString() + "-12-30"

            var min_rate = binding.tvRateMin.text.toString().toDouble()
            var max_rate = binding.tvRateMax.text.toString().toDouble()

            var min_runtime = binding.tvRuntimeMin.text.toString().toInt()
            var max_runtime = binding.tvRuntimeMax.text.toString().toInt()

            var min_uservote = binding.tvMinUser.text.toString().toInt()

            var genres_list = binding.tvSelectedGenres.text.toString()
            var genres_id_list = createGenresIDs(genres_list)


            Log.d("xxxParams1",min_year+ " " + max_year+ " " + min_rate+ " " + max_rate+ " " + min_runtime+ " " + max_runtime+ " ")
            Log.d("xxxParams2", min_uservote.toString()+ " "+genres_id_list+ " ")

            moviesViewModel.discoverMovies(
                releaseDateGte = min_year,
                releaseDateLte = max_year,
                runtimeLte = max_runtime,
                runtimeGte = min_runtime,
                withGenres = genres_id_list,
                voteCount = min_uservote,
                voteAverageLte = max_rate,
                voteAverageGte = min_rate,
                keyWord = null
            )

            adapterSearchMovies = AdapterSearchMovies(requireContext(),moviesViewModel)
            //binding.recyclerViewDiscoverMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerViewDiscoverMovies.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            binding.recyclerViewDiscoverMovies.adapter = adapterSearchMovies

            moviesViewModel.discoveredMoviesList.observe(viewLifecycleOwner){
                adapterSearchMovies.differ.submitList(it)
                if (it.isNotEmpty()){
                    binding.recyclerViewDiscoverMovies.visibility = View.VISIBLE
                }
            }

        }

        binding.buttonClearAll.setOnClickListener {
            binding.tvSelectedGenres.text = ""
            selectedGenresSet.clear()
            checkClearAllButtonVisibility(selectedGenresSet)
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkClearAllButtonVisibility(selectedGenresSet: HashSet<String>) {
        if (selectedGenresSet.isEmpty()) {
            binding.buttonClearAll.visibility = View.GONE
        } else {
            binding.buttonClearAll.visibility = View.VISIBLE
        }
    }


    private fun createGenresIDs(genresText: String): String {
        val immutableMap = mapOf(
            "Action" to 28,
            "Adventure" to 12,
            "Animation" to 16,
            "Comedy" to 35,
            "Crime" to 80,
            "Documentary" to 99,
            "Drama" to 18,
            "Family" to 10751,
            "Fantasy" to 14,
            "History" to 36,
            "Horror" to 27,
            "Music" to 10402,
            "Mystery" to 9648,
            "Romance" to 10749,
            "Science Fiction" to 878,
            "TV Movie" to 10770,
            "Thriller" to 53,
            "War" to 10752,
            "Western" to 37
        )

        val genresList = genresText.split(",").map { it.trim() }
        val idsList = genresList.mapNotNull { genre -> immutableMap[genre] }

        return idsList.joinToString(", ")
    }


}