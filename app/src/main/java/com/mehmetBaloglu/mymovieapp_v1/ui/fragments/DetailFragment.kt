package com.mehmetBaloglu.mymovieapp_v1.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mehmetBaloglu.mymovieapp_v1.R
import com.mehmetBaloglu.mymovieapp_v1.databinding.FragmentDetailBinding
import com.mehmetBaloglu.mymovieapp_v1.databinding.FragmentHomeBinding
import com.mehmetBaloglu.mymovieapp_v1.databinding.FragmentLoginBinding
import com.mehmetBaloglu.mymovieapp_v1.ui.viewmodels.MoviesViewModel
import com.mehmetBaloglu.mymovieapp_v1.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesViewModel: MoviesViewModel

    val bundle :DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: MoviesViewModel by viewModels()
        moviesViewModel = tempViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = bundle.id

        if (bundle.id?.startsWith("s") == true){

            var __id = bundle.id.toString().drop(1)
            var _id = __id.toInt()

            moviesViewModel.getSerieDetails(_id)

            moviesViewModel.detailedSerie.observe(viewLifecycleOwner){
                binding.textViewMainTitle.text = it.title
                binding.textViewScore.text = it.voteAverage.toString().substring(0,3)
                binding.textViewLanguage.text = it.originalLanguage.uppercase()
                binding.textViewStatus.text = it.status
                binding.textViewRevenue.text = formatString(it.revenue.toString())
                binding.textViewSlogan.text = it.tagline
                binding.textViewOverView.text = it.overview


                var backdropURL = Constants.BASE_IMAGE_URL + it.backdropPath
                Glide.with(requireContext()).load(backdropURL)
                    .apply(RequestOptions().transform(RoundedCorners(32)))
                    .into(binding.imageViewBackDrop)
                binding.imageViewBackDrop.visibility = View.VISIBLE

                var posterURL = Constants.BASE_IMAGE_URL + it.posterPath
                Glide.with(requireContext())
                    .load(posterURL)
                    .apply(RequestOptions().transform(RoundedCorners(32)))
                    .into(binding.imageViewPoster)
                binding.imageViewPoster.visibility = View.VISIBLE
            }
        }

        if(bundle.id?.startsWith("m") == true) {

            var __id = bundle.id.toString().drop(1)
            var _id = __id.toInt()

            moviesViewModel.getMovieDetails(_id)

            moviesViewModel.detailedMovie.observe(viewLifecycleOwner){
                binding.textViewMainTitle.text = it.title
                binding.textViewScore.text = it.voteAverage.toString().substring(0,3)
                binding.textViewLanguage.text = it.originalLanguage.uppercase()
                binding.textViewStatus.text = it.status
                binding.textViewRevenue.text = formatString(it.revenue.toString())
                binding.textViewSlogan.text = it.tagline
                binding.textViewOverView.text = it.overview


                var backdropURL = Constants.BASE_IMAGE_URL + it.backdropPath
                Glide.with(requireContext()).load(backdropURL)
                    .apply(RequestOptions().transform(RoundedCorners(3)))
                    .into(binding.imageViewBackDrop)
                binding.imageViewBackDrop.visibility = View.VISIBLE

                var posterURL = Constants.BASE_IMAGE_URL + it.posterPath
                Glide.with(requireContext())
                    .load(posterURL)
                    .apply(RequestOptions().transform(RoundedCorners(32)))
                    .into(binding.imageViewPoster)
                binding.imageViewPoster.visibility = View.VISIBLE



                //todo burası düzenlenecek
                var yearAndGenres = it.releaseDate + it.genres.toString()
                binding.textViewYearAndGenres.text = yearAndGenres

            }
        }



    }
    fun formatString(input: String): String {
        // Split the string into chunks of 3 characters
        val chunks = input.chunked(3)

        // Join the chunks with commas
        val formattedString = chunks.joinToString(",")

        // Append ".00" at the end
        return "$$formattedString.00"
    }

}