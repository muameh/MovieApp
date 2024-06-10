package com.mehmetBaloglu.mymovieapp_v1.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.mehmetBaloglu.mymovieapp_v1.data.models.ForFirebaseResponse
import com.mehmetBaloglu.mymovieapp_v1.databinding.FragmentUserBinding
import com.mehmetBaloglu.mymovieapp_v1.ui.adapters.AdapterSearchMovies
import com.mehmetBaloglu.mymovieapp_v1.ui.adapters.WatchListAdapter
import com.mehmetBaloglu.mymovieapp_v1.ui.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint




@AndroidEntryPoint
class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesViewModel: MoviesViewModel

    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore

    private lateinit var watchListAdapter: WatchListAdapter

    private var UserWatchList : MutableList<ForFirebaseResponse> = mutableListOf()
    private var UserWatchEDList : MutableList<ForFirebaseResponse> = mutableListOf()


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

        createUserWatchList()

        Log.e("ccc",UserWatchList.toString())

        createUsersWatchEDList()

        createWatchListRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//----------------------------------------------------------------------------

    private fun createWatchListRecyclerView() {
        watchListAdapter = WatchListAdapter(requireContext(), moviesViewModel)
        binding.recyclerViewWatchList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = watchListAdapter
        }
    }

    private fun createUserWatchList(){
        db.collection("WatchList").get().addOnSuccessListener { result ->
            if (!result.isEmpty){
                val documents = result.documents



                for (document in documents){
                    val ID = document.get("ID") as String
                    val name : String = document.get("filmName") as String
                    var posterUrl : String = document.get("filmPosterUrl") as String
                    var _item = ForFirebaseResponse(ID, name, posterUrl)

                    UserWatchList.add(_item)
                }
                //burası önemli !!! listeyi tüm sorgu bitipte liste oluşturulduktan sonra verdik
                //öteki türlü asenkron yapıdan dolayı patlıyor
                watchListAdapter.differ.submitList(UserWatchList)
            }
        }
    }

    private fun createUsersWatchEDList(){
        db.collection("WatchedList").get().addOnSuccessListener { result ->
            if (!result.isEmpty){
                val documents = result.documents

                for (document in documents){
                    val ID = document.get("ID") as String
                    val name : String = document.get("filmName") as String
                    var posterUrl : String = document.get("filmPosterUrl") as String
                    var _item = ForFirebaseResponse(ID, name, posterUrl)

                    UserWatchEDList.add(_item)
                }
            }
        }
    }
}
















