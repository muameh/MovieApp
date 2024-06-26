package com.mehmetBaloglu.mymovieapp_v1.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.mehmetBaloglu.mymovieapp_v1.R
import com.mehmetBaloglu.mymovieapp_v1.data.models.firebase_response.ForFirebaseResponse
import com.mehmetBaloglu.mymovieapp_v1.databinding.FragmentUserBinding
import com.mehmetBaloglu.mymovieapp_v1.ui.adapters.WatchEDListAdapter
import com.mehmetBaloglu.mymovieapp_v1.ui.adapters.WatchListAdapter
import com.mehmetBaloglu.mymovieapp_v1.ui.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesViewModel: MoviesViewModel

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var watchListAdapter: WatchListAdapter
    private lateinit var watchEDListAdapter: WatchEDListAdapter

    private var UserWatchList: MutableList<ForFirebaseResponse> = mutableListOf()
    private var UserWatchEDList: MutableList<ForFirebaseResponse> = mutableListOf()


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

        binding.textViewUserMail.text = auth.currentUser?.email

        binding.buttonSignOut.setOnClickListener { signOut(it) }

        moviesViewModel.createUsersWatchList()
        moviesViewModel.createUsersWatchEDList()

        createWatchListRecyclerView()
        createWatchEDListRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//----------------------------------------------------------------------------

    fun createWatchListRecyclerView() {
        watchListAdapter = WatchListAdapter(requireContext(), moviesViewModel)
        binding.recyclerViewWatchList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = watchListAdapter
        }
        moviesViewModel.UserWatchList.observe(viewLifecycleOwner) {
            watchListAdapter.differ.submitList(it)
            Log.e("watchListInUserFragment",it.toString())
        }
    }

    fun createWatchEDListRecyclerView() {
        watchEDListAdapter = WatchEDListAdapter(requireContext(), moviesViewModel)

        binding.recyclerViewWatchEDList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = watchEDListAdapter
        }

        moviesViewModel.UserWatchEDList.observe(viewLifecycleOwner) {
            watchEDListAdapter.differ.submitList(it)
        }
    }

    fun signOut(view: View) {
        auth.signOut() //signout
        Navigation.findNavController(view).navigate(R.id.action_userFragment_to_loginFragment)
        Toast.makeText(requireContext(), "You have successfully logged out.", Toast.LENGTH_SHORT).show()
    }

}
















