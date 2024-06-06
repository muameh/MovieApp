package com.mehmetBaloglu.mymovieapp_v1.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mehmetBaloglu.mymovieapp_v1.data.models.general_returns.FilmItem
import com.mehmetBaloglu.mymovieapp_v1.data.models.series.SeriesItem
import com.mehmetBaloglu.mymovieapp_v1.databinding.CardDesing1Binding
import com.mehmetBaloglu.mymovieapp_v1.ui.fragments.HomeFragmentDirections
import com.mehmetBaloglu.mymovieapp_v1.ui.viewmodels.MoviesViewModel
import com.mehmetBaloglu.mymovieapp_v1.utils.Constants

class AdapterForSeries (var mContext: Context, var viewModel: MoviesViewModel)
    : RecyclerView.Adapter<AdapterForSeries.Adapter1ViewHolder>() {

    inner class Adapter1ViewHolder(val itemBinding: CardDesing1Binding)
        : RecyclerView.ViewHolder(itemBinding.root)


    private val differCallback = object : DiffUtil.ItemCallback<SeriesItem>() {

        override fun areItemsTheSame(oldItem: SeriesItem, newItem: SeriesItem): Boolean {
            return oldItem.id== newItem.id
        }

        override fun areContentsTheSame(oldItem: SeriesItem, newItem: SeriesItem): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter1ViewHolder {
        val binding = CardDesing1Binding.inflate(LayoutInflater.from(parent.context), parent,false)
        return Adapter1ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: Adapter1ViewHolder, position: Int) {
        val currentFilm = differ.currentList[position]

        val ImageUrl = Constants.BASE_IMAGE_URL + currentFilm.posterPath

        if (ImageUrl!=null)
            Glide.with(mContext).load(ImageUrl)
                .apply(RequestOptions().transform(RoundedCorners(16)))
                .into(holder.itemBinding.posterImage)

        holder.itemBinding.apply {
            textViewFilmName.text = currentFilm.originalName


        }

        holder.itemBinding.cardView.setOnClickListener {
            var id = currentFilm.id
            var _id = "s" + id.toString()

            val direction = HomeFragmentDirections.actionHomeFragmentToDetailFragment(_id)
            Navigation.findNavController(it).navigate(direction)
        }


    }

}
