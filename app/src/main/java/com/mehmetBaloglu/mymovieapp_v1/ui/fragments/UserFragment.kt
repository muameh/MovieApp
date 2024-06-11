package com.mehmetBaloglu.mymovieapp_v1.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.mehmetBaloglu.mymovieapp_v1.data.models.ForFirebaseResponse
import com.mehmetBaloglu.mymovieapp_v1.databinding.FragmentUserBinding
import com.mehmetBaloglu.mymovieapp_v1.ui.adapters.AdapterSearchMovies
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
    private lateinit var db : FirebaseFirestore

    private lateinit var watchListAdapter: WatchListAdapter
    private lateinit var watchEDListAdapter: WatchEDListAdapter

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
        xxcreateUsersWatchEDList()

        createWatchListRecyclerView()
        createWatchEDListRecyclerView()

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

    private fun createWatchEDListRecyclerView() {
        watchEDListAdapter = WatchEDListAdapter(requireContext(), moviesViewModel)
        binding.recyclerViewWatchEDList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = watchEDListAdapter
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

    private fun xxcreateUsersWatchEDList(){
        val currentUsersEmail = Firebase.auth.currentUser?.email.toString()

        db.collection("WatchedList")
            .whereEqualTo("email",currentUsersEmail)
            .addSnapshotListener { value, error ->
            if(error != null){
                //hata msjı
            } else{
                if (value!=null){
                    val documents = value.documents

                    for (document in documents){
                        val ID = document.get("ID") as String
                        val name : String = document.get("filmName") as String
                        var posterUrl : String = document.get("filmPosterUrl") as String
                        var _item = ForFirebaseResponse(ID, name, posterUrl)

                        UserWatchEDList.add(_item)
                    }
                    watchEDListAdapter.differ.submitList(UserWatchEDList)
                }
            }
        }
    }



    private fun createUsersWatchEDList(){
        val currentUsersEmail = Firebase.auth.currentUser?.email.toString()

        Log.e("email",currentUsersEmail)

        db.collection("WatchedList")
            .whereEqualTo("email",currentUsersEmail)
            .orderBy("date",Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
            if (!result.isEmpty){
                val documents = result.documents

                for (document in documents){
                    val ID = document.get("ID") as String
                    val name : String = document.get("filmName") as String
                    var posterUrl : String = document.get("filmPosterUrl") as String
                    var _item = ForFirebaseResponse(ID, name, posterUrl)

                    UserWatchEDList.add(_item)
                }

                watchEDListAdapter.differ.submitList(UserWatchEDList)
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_LONG).show()
                Log.e("failure_message",it.localizedMessage.toString())
            }
    }
}
















