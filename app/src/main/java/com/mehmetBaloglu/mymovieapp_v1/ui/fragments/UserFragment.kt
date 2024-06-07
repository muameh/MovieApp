package com.mehmetBaloglu.mymovieapp_v1.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
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

    var UsersWatchEDList: MutableList<String> = mutableListOf()
    var UsersWatchList : MutableList<String> = mutableListOf()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createWatchListFromFirestore()

        createWatchEDListFromFirestore()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createWatchListFromFirestore(){
        db.collection("WatchList").get().addOnSuccessListener { result ->
            if (!result.isEmpty){
                val documents = result.documents

                for (document in documents){
                    val ID = document.get("ID") as String
                    UsersWatchList.add(ID)

                }
            }
            Log.e("watchList",UsersWatchList.toString())
        }
    }

    private fun createWatchEDListFromFirestore(){
        db.collection("WatchedList").get().addOnSuccessListener { result ->
            if (!result.isEmpty){
                val documents = result.documents

                for (document in documents){
                    val ID = document.get("ID") as String
                    UsersWatchEDList.add(ID)

                }
            }
            Log.e("watchedList",UsersWatchEDList.toString())
        }

    }







}