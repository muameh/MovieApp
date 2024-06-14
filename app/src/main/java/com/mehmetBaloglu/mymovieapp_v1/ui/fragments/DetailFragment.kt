package com.mehmetBaloglu.mymovieapp_v1.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.mehmetBaloglu.mymovieapp_v1.R
import com.mehmetBaloglu.mymovieapp_v1.data.models.ForFirebaseResponse
import com.mehmetBaloglu.mymovieapp_v1.databinding.FragmentDetailBinding
import com.mehmetBaloglu.mymovieapp_v1.ui.viewmodels.MoviesViewModel
import com.mehmetBaloglu.mymovieapp_v1.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class DetailFragment : Fragment(), PopupMenu.OnMenuItemClickListener {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var popup: PopupMenu

    private var filmID : Int? = null
    private var filmName : String? = null
    private var filmPosterURL : String? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore

    val bundle :DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: MoviesViewModel by viewModels()
        moviesViewModel = tempViewModel

        auth = Firebase.auth
        db = Firebase.firestore

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
            getSeriesDetails()
        } else if (bundle.id?.startsWith("m") == true) {
            getMoviesDetais()
        }

        binding.floatingActionButton2.setOnClickListener { myPopUpMenu(it) }

        popup = PopupMenu(requireContext(),binding.floatingActionButton2)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.popup_menu,popup.menu)
        popup.setOnMenuItemClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
//----------------------------------------------------------------------------------------

    fun getMoviesDetais(){
        var __id = bundle.id.toString().drop(1)
        var _id = __id.toInt()

        moviesViewModel.getMovieDetails(_id)

        moviesViewModel.detailedMovie.observe(viewLifecycleOwner){
            binding.textViewMainTitle.text = it.title
            binding.textViewScore.text = it.voteAverage.toString().substring(0,3)
            binding.textViewLanguage.text = it.originalLanguage.uppercase()
            binding.textViewOrginalTitle.text = it.originalTitle
            binding.textViewStatus.text = it.status
            binding.textViewRevenue.text = revenueBuilder(it.revenue.toString())
            binding.textViewSlogan.text = it.tagline
            binding.textViewOverView.text = it.overview
            binding.textViewYearAndGenres.text = yearAndGenresBuilder(it.genres,it.releaseDate)

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

            filmID = it.id
            filmName = it.title
            filmPosterURL = posterURL
        }
    }

    fun getSeriesDetails(){
        var __id = bundle.id.toString().drop(1)
        var _id = __id.toInt()

        moviesViewModel.getSerieDetails(_id)

        Log.e("serieID",_id.toString())

        moviesViewModel.detailedSerie.observe(viewLifecycleOwner){
            binding.textViewMainTitle.text = it.name
            binding.textViewYearAndGenres.text = it.originalName
            binding.textViewScore.text = it.voteAverage.toString().substring(0,3)
            binding.textViewLanguage.text = it.originalLanguage!!.uppercase() //todo
            binding.textViewStatus.text = it.status
            binding.textViewOrgtitleTexti.text = "Number of Seasons"
            binding.textViewOrginalTitle.text = it.numberOfSeasons.toString()
            binding.textViewRevenueTexti.text = "Last Air Date"
            binding.textViewRevenue.text = it.lastAirDate
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

            filmID = it.id
            filmName = it.name
            filmPosterURL = posterURL
        }
    }

    private fun myPopUpMenu(view: View) {
        popup.show()
    }

    fun revenueBuilder(input: String): String {
        // Split the string into chunks of 3 characters
        val chunks = input.chunked(3)

        // Join the chunks with commas
        val formattedString = chunks.joinToString(",")

        // Append ".00" at the end
        return "$$formattedString.00"
    }

    fun yearAndGenresBuilder (xgenreList: List<com.mehmetBaloglu.mymovieapp_v1.data.models.general_returns.detail.Genre>, xyear: String) : String {
        var _genres = StringBuilder()
        for (genre in xgenreList){
            _genres.append(genre.name).append(", ")
        }
        var genres = _genres.dropLast(2)
        var releaseTime = xyear.substring(0,4)
        var yearAndGenres = releaseTime + " " +  genres
        return yearAndGenres
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.IDwatched){

            var newItem = ForFirebaseResponse(
                date = Timestamp.now(),
                filmID = bundle.id.toString(),
                filmPosterUrl = filmPosterURL.toString(),
                filmName = filmName.toString(),
                email = auth.currentUser?.email.toString()
            )

            db.collection("WatchedList").document().set(newItem)
                .addOnSuccessListener { Toast.makeText(requireContext(),"Added to Watched List",Toast.LENGTH_SHORT).show() }
                .addOnFailureListener { Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_SHORT).show() }

        } else if (item?.itemId == R.id.IDtowatch) {

            var newItem = ForFirebaseResponse(
                date = Timestamp.now(),
                filmID = bundle.id.toString(),
                filmPosterUrl = filmPosterURL.toString(),
                filmName = filmName.toString(),
                email = auth.currentUser?.email.toString()
            )

            db.collection("WatchList").document().set(newItem)
                .addOnSuccessListener { Toast.makeText(requireContext(),"Added to Watch List",Toast.LENGTH_SHORT).show() }
                .addOnFailureListener { Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_SHORT).show() }
        }
        return true
    }

}