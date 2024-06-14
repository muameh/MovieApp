package com.mehmetBaloglu.mymovieapp_v1.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.mehmetBaloglu.mymovieapp_v1.data.models.ForFirebaseResponse
import com.mehmetBaloglu.mymovieapp_v1.data.models.general_returns.FilmItem
import com.mehmetBaloglu.mymovieapp_v1.databinding.CardDesing1Binding
import com.mehmetBaloglu.mymovieapp_v1.databinding.CardDesingForUserBinding
import com.mehmetBaloglu.mymovieapp_v1.ui.fragments.HomeFragmentDirections
import com.mehmetBaloglu.mymovieapp_v1.ui.viewmodels.MoviesViewModel
import com.mehmetBaloglu.mymovieapp_v1.utils.Constants

class WatchListAdapter (var mContext: Context, var viewModel: MoviesViewModel)
: RecyclerView.Adapter<WatchListAdapter.Adapter1ViewHolder>() {

    inner class Adapter1ViewHolder(val itemBinding: CardDesingForUserBinding)
        : RecyclerView.ViewHolder(itemBinding.root)


    private val differCallback = object : DiffUtil.ItemCallback<ForFirebaseResponse>() {

        override fun areItemsTheSame(oldItem: ForFirebaseResponse, newItem: ForFirebaseResponse): Boolean {
            return oldItem.filmID== newItem.filmID
        }

        override fun areContentsTheSame(oldItem: ForFirebaseResponse, newItem: ForFirebaseResponse): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter1ViewHolder {
        val binding = CardDesingForUserBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return Adapter1ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: Adapter1ViewHolder, position: Int) {
        val currentFilm = differ.currentList[position]

        val ImageUrl = currentFilm.filmPosterUrl

        if (ImageUrl!=null)
            Glide.with(mContext).load(ImageUrl)
                .apply(RequestOptions().transform(RoundedCorners(16)))
                .into(holder.itemBinding.posterImage)

        holder.itemBinding.apply {
            textViewFilmName.text = currentFilm.filmName
        }


        holder.itemBinding.imageViewDelete.setOnClickListener {
            viewModel.deleteFromWatchList(currentFilm.filmID.toString())
        }




    }

}