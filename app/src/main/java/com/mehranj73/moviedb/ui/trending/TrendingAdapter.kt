package com.mehranj73.moviedb.ui.trending

import android.util.Log
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
import com.mehranj73.moviedb.data.model.TrendingEntity
import com.mehranj73.moviedb.util.originalPosterUrl
import com.mehranj73.moviedb.util.w92PosterUrl
import kotlinx.android.synthetic.main.person_item.view.*

class TrendingAdapter(
    private val requestManager: RequestManager
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class MovieViewHolder(itemView: View, requestManager: RequestManager) :
        RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title_textView)
        private val releaseDate: TextView = itemView.findViewById(R.id.release_date_textView)
        private val voteAverage: TextView = itemView.findViewById(R.id.vote_average_textView)
        private val overview: TextView = itemView.findViewById(R.id.overview_textView)
        private var poster: ImageView = itemView.findViewById(R.id.poster_imageView)
        private var cardView: MaterialCardView = itemView.findViewById(R.id.cardView)

        fun bind(trendingEntity: TrendingEntity) {
            title.text = trendingEntity.title
            releaseDate.text = trendingEntity.release_date
            voteAverage.text = trendingEntity.vote_average.toString()
            overview.text = trendingEntity.overview

            trendingEntity.poster_path?.let {
                requestManager
                    .load(it.originalPosterUrl())
                    .thumbnail(requestManager.load(it.w92PosterUrl()))
                    .into(poster)
            }


        }

    }
    inner class PersonViewHolder(itemView: View, requestManager: RequestManager) :
        RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.name_textView)
        private val gender: TextView = itemView.findViewById(R.id.gender_textView)
        private val knownFor: TextView = itemView.findViewById(R.id.known_for_department_textView)
        private val profile: ImageView = itemView.findViewById(R.id.profile_imageView)


        fun bind(trendingEntity: TrendingEntity) {
            name.text = trendingEntity.name
            knownFor.text = trendingEntity.known_for_department

            if (trendingEntity.gender == 1) {
                gender.text = "female"
            } else {
                gender.text = "male"
            }


            trendingEntity.profile_path?.let {
                requestManager
                    .load(it.originalPosterUrl())
                    .thumbnail(requestManager.load(it.w92PosterUrl()))
                    .into(profile)
            }


        }

    }

    inner class TvViewHolder(itemView: View, requestManager: RequestManager) :
        RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.name_textView)
        private val voteAverage: TextView = itemView.findViewById(R.id.vote_average_textView)
        private val firstAirDate: TextView = itemView.findViewById(R.id.first_air_date_textView)
        private val overView: TextView = itemView.findViewById(R.id.overview_textView)
        private val poster: ImageView = itemView.findViewById(R.id.poster_imageView)


        fun bind(trendingEntity: TrendingEntity) {
            name.text = trendingEntity.name
            voteAverage.text = trendingEntity.vote_average.toString()
            firstAirDate.text = trendingEntity.first_air_date
            overView.text = trendingEntity.overview


            trendingEntity.poster_path?.let {
                requestManager
                    .load(it.originalPosterUrl())
                    .thumbnail(requestManager.load(it.w92PosterUrl()))
                    .into(poster)
            }


        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<TrendingEntity>() {

        override fun areItemsTheSame(oldItem: TrendingEntity, newItem: TrendingEntity): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: TrendingEntity, newItem: TrendingEntity): Boolean {
            return oldItem == newItem
        }


    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TV_VIEW_TYPE -> {
                TvViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.tv_item,
                        parent,
                        false
                    ),
                    requestManager = requestManager
                )
            }

            MOVIE_VIEW_TYPE -> {
                MovieViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.movie_item,
                        parent,
                        false
                    ),
                    requestManager = requestManager
                )
            }

            PERSON_VIEW_TYPE -> {
                PersonViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.person_item,
                        parent,
                        false
                    ),
                    requestManager = requestManager
                )
            }


            else -> {
                Log.d("TrendingAdapter", "onCreateViewHolder: ")
                MovieViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.movie_item,
                        parent,
                        false
                    ),
                    requestManager = requestManager
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (differ.currentList[position].media_type) {
            "tv" -> TV_VIEW_TYPE
            "movie" -> MOVIE_VIEW_TYPE
            "person" -> PERSON_VIEW_TYPE
            else -> -1
        }
    }

    fun preloadGlideImages(
        requestManager: RequestManager,
        list: List<TrendingEntity>
    ) {
        for (movie in list) {
            requestManager
                .load(movie.poster_path?.originalPosterUrl())
                .thumbnail(requestManager.load(movie.poster_path?.w92PosterUrl()))
                .preload()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is MovieViewHolder -> {
                holder.bind(differ.currentList[position])
            }

            is PersonViewHolder -> {
                holder.bind(differ.currentList[position])
            }
            is TvViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }

    }


    companion object {
        const val TV_VIEW_TYPE = 0
        const val MOVIE_VIEW_TYPE = 1
        const val PERSON_VIEW_TYPE = 2

    }

}