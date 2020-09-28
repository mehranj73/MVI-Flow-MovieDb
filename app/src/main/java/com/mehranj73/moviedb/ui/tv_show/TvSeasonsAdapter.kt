package com.mehranj73.moviedb.ui.tv_show

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.google.android.material.card.MaterialCardView
import com.mehranj73.moviedb.R
import com.mehranj73.moviedb.data.model.Season
import com.mehranj73.moviedb.data.model.TvEntity
import com.mehranj73.moviedb.util.originalPosterUrl
import com.mehranj73.moviedb.util.w92PosterUrl

class TvSeasonsAdapter(
    private val requestManager: RequestManager,
    private val interaction: Interaction? = null
) : RecyclerView.Adapter<TvSeasonsAdapter.SeasonsViewHolder>() {


    inner class SeasonsViewHolder(itemView: View, var requestManager: RequestManager) :
        RecyclerView.ViewHolder(itemView) {
        private val seasonName: TextView = itemView.findViewById(R.id.name_textView)
        private val firstAirDate: TextView = itemView.findViewById(R.id.air_date_textView)
        private val poster: ImageView = itemView.findViewById(R.id.poster_imageView)
        private val cardView: MaterialCardView = itemView.findViewById(R.id.seasonCardView)


        fun bind(season: Season) {
            seasonName.text = season.name
            firstAirDate.text = season.air_date

            if (season.poster_path != null){
                requestManager
                    .load(season.poster_path.originalPosterUrl())
                    .thumbnail(requestManager.load(season.poster_path.w92PosterUrl()))
                    .into(poster)
            } else {
                requestManager
                    .load(R.drawable.poster_placeholder)
                    .into(poster)
            }



            cardView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, season)
            }


        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<Season>() {

        override fun areItemsTheSame(oldItem: Season, newItem: Season): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Season, newItem: Season): Boolean {
            return oldItem == newItem
        }


    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonsViewHolder {
        return SeasonsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.season_item,
                parent,
                false
            ),
            requestManager
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SeasonsViewHolder, position: Int) {
        val tv = differ.currentList[position]
        holder.bind(tv)

    }


    fun preloadGlideImages(
        requestManager: RequestManager,
        list: List<Season>
    ) {
        for (season in list) {
            requestManager
                .load(season.poster_path?.originalPosterUrl())
                .thumbnail(requestManager.load(season.poster_path?.w92PosterUrl()))
                .preload()
        }
    }


    interface Interaction {

        fun onItemSelected(position: Int, item: Season)


    }

}

