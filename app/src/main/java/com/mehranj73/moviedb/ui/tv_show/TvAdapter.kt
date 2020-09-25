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
import com.mehranj73.moviedb.data.model.MovieEntity
import com.mehranj73.moviedb.data.model.TrendingEntity
import com.mehranj73.moviedb.data.model.TvEntity
import com.mehranj73.moviedb.util.originalPosterUrl
import com.mehranj73.moviedb.util.w92PosterUrl

class TvAdapter(
    private val requestManager: RequestManager,
    private val interaction: Interaction? = null
) : RecyclerView.Adapter<TvAdapter.TvViewHolder>() {


    inner class TvViewHolder(itemView: View, requestManager: RequestManager) :
        RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.name_textView)
        private val voteAverage: TextView = itemView.findViewById(R.id.vote_average_textView)
        private val firstAirDate: TextView = itemView.findViewById(R.id.first_air_date_textView)
        private val overView: TextView = itemView.findViewById(R.id.overview_textView)
        private val poster: ImageView = itemView.findViewById(R.id.poster_imageView)
        private val cardView: MaterialCardView = itemView.findViewById(R.id.cardView)


        fun bind(tvEntity: TvEntity) {
            name.text = tvEntity.name
            voteAverage.text = tvEntity.vote_average.toString()
            firstAirDate.text = tvEntity.first_air_date
            overView.text = tvEntity.overview


            tvEntity.poster_path.let {
                requestManager
                    .load(it.originalPosterUrl())
                    .thumbnail(requestManager.load(it.w92PosterUrl()))
                    .into(poster)
            }

            cardView.setOnClickListener {

            }


        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<TvEntity>(){

        override fun areItemsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
            return oldItem == newItem
        }


    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        return TvViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.tv_item,
                parent,
                false
            ),
            requestManager
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val tv = differ.currentList[position]
        holder.bind(tv)

    }




    interface Interaction {

        fun onItemSelected(position: Int, item: MovieEntity)


    }


    fun preloadGlideImages(
        requestManager: RequestManager,
        list: List<MovieEntity>
    ){
        for(movie in list){
            requestManager
                .load(movie.poster_path.originalPosterUrl())
                .thumbnail(requestManager.load(movie.poster_path.w92PosterUrl()))
                .preload()
        }
    }

}