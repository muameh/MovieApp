package com.mehmetBaloglu.mymovieapp_v1.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.mehmetBaloglu.mymovieapp_v1.data.models.detailseries.DetailSerieResponse
import com.mehmetBaloglu.mymovieapp_v1.data.models.general_returns.detail.DetailFilmResponse
import com.mehmetBaloglu.mymovieapp_v1.databinding.FragmentUserBinding
import com.mehmetBaloglu.mymovieapp_v1.ui.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint




@AndroidEntryPoint
class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesViewModel: MoviesViewModel

    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore

    private var UsersWatchEDListIDs: MutableList<String> = mutableListOf()
    private var UsersWatchListIDs : MutableList<String> = mutableListOf()

    private val WatchListMovie : MutableList<DetailFilmResponse> = mutableListOf()
    private val WatchListSerie : MutableList<DetailSerieResponse> = mutableListOf()

    //private lateinit var WatchEDListMovie : MutableList<DetailFilmResponse>
    //private lateinit var WatchEDListSeries : MutableList<DetailSerieResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: MoviesViewModel by viewModels()
        moviesViewModel = tempViewModel

        auth = Firebase.auth
        db = Firebase.firestore
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
//----------------------------------------------------------------------------


    private fun createUsersWatchListIDsFromFirestore(){
        db.collection("WatchList").get().addOnSuccessListener { result ->
            if (!result.isEmpty){
                val documents = result.documents

                for (document in documents){
                    val ID = document.get("ID") as String
                    UsersWatchListIDs.add(ID)

                }
            }
            Log.e("watchList",UsersWatchListIDs.toString())
        }
    }

    private fun createUsersWatchEDListIDFromFirestore(){
        db.collection("WatchedList").get().addOnSuccessListener { result ->
            if (!result.isEmpty){
                val documents = result.documents

                for (document in documents){
                    val ID = document.get("ID") as String
                    UsersWatchEDListIDs.add(ID)

                }
            }
            Log.e("watchedList",UsersWatchEDListIDs.toString())
        }
    }
/*
private fun createWatchList(){
        val _id_list =  UsersWatchListIDs
        for (_id in _id_list){
            if (_id.startsWith("m")){
                var _movie = moviesViewModel.getMovieDetails(_id.drop(1).toInt())
                moviesViewModel.detailedMovie.observe(viewLifecycleOwner){
                    WatchListMovie.add(it)
                }
            }
            else if (_id.startsWith("s")){
                moviesViewModel.getSerieDetails(_id.drop(1).toInt())
                moviesViewModel.detailedSerie.observe(viewLifecycleOwner){
                    WatchListSerie.add(it)
                }
            }

        }

    }
 */













}