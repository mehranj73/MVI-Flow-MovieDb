package com.mehranj73.moviedb.ui.movie

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
import com.mehranj73.moviedb.util.originalPosterUrl
import com.mehranj73.moviedb.util.w92PosterUrl

class MovieAdapter(
    private val requestManager: RequestManager,
    private val interaction: Interaction? = null
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    inner class MovieViewHolder(
        itemView: View,
        val requestManager: RequestManager
    ): RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title_textView)
        private val releaseDate: TextView = itemView.findViewById(R.id.release_date_textView)
        private val voteAverage: TextView = itemView.findViewById(R.id.vote_average_textView)
        private val overview: TextView = itemView.findViewById(R.id.overview_textView)
        private var poster: ImageView = itemView.findViewById(R.id.poster_imageView)
        private var cardView: MaterialCardView = itemView.findViewById(R.id.cardView)

        fun bind(movieEntity: MovieEntity){
            title.text = movieEntity.title
            releaseDate.text = movieEntity.release_date
            voteAverage.text = movieEntity.vote_average.toString()
            overview.text = movieEntity.overview

            requestManager
                .load(movieEntity.poster_path.originalPosterUrl())
                .thumbnail(requestManager.load(movieEntity.poster_path.w92PosterUrl()))
                .into(poster)


            cardView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, movieEntity)
            }


        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<MovieEntity>(){

        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem == newItem
        }


    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_item,
                parent,
                false
            ),
            requestManager
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.bind(movie)

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