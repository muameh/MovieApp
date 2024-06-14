package com.mehmetBaloglu.mymovieapp_v1.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.mehmetBaloglu.mymovieapp_v1.data.models.ForFirebaseResponse
import com.mehmetBaloglu.mymovieapp_v1.databinding.CardDesingForUserBinding
import com.mehmetBaloglu.mymovieapp_v1.ui.fragments.UserFragment
import com.mehmetBaloglu.mymovieapp_v1.ui.viewmodels.MoviesViewModel

class WatchEDListAdapter(var mContext: Context, var viewModel: MoviesViewModel) :
    RecyclerView.Adapter<WatchEDListAdapter.Adapter1ViewHolder>() {


    inner class Adapter1ViewHolder(val itemBinding: CardDesingForUserBinding) :
        RecyclerView.ViewHolder(itemBinding.root)


    private val differCallback = object : DiffUtil.ItemCallback<ForFirebaseResponse>() {

        override fun areItemsTheSame(
            oldItem: ForFirebaseResponse,
            newItem: ForFirebaseResponse
        ): Boolean {
            return oldItem.filmID == newItem.filmID
        }

        override fun areContentsTheSame(
            oldItem: ForFirebaseResponse,
            newItem: ForFirebaseResponse
        ): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    lateinit var userFragment : UserFragment


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter1ViewHolder {
        val binding =
            CardDesingForUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Adapter1ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: Adapter1ViewHolder, position: Int) {
        val currentFilm = differ.currentList[position]

        val ImageUrl = currentFilm.filmPosterUrl

        if (ImageUrl != null)
            Glide.with(mContext).load(ImageUrl)
                .apply(RequestOptions().transform(RoundedCorners(16)))
                .into(holder.itemBinding.posterImage)

        holder.itemBinding.apply {
            textViewFilmName.text = currentFilm.filmName
        }

        holder.itemBinding.imageViewDelete.setOnClickListener {
            val firestore = Firebase.firestore

            firestore.collection("WatchedList")
                .whereEqualTo("ID", currentFilm.filmID)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val document = querySnapshot.documents[0]
                        firestore.collection("WatchedList").document(document.id).delete()
                            .addOnSuccessListener {
                                // Başarıyla silindiğinde yapılacak işlemler
                                println("Document successfully deleted!")

                            }
                            .addOnFailureListener { e ->
                                // Hata durumunda yapılacak işlemler
                                println("Error deleting document: $e")
                            }
                    } else {
                        println("No documents found with the given ID")
                    }
                }
        }

    }
}

