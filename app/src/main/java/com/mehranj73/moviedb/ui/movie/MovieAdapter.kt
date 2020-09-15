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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.mehranj73.moviedb.R
import com.mehranj73.moviedb.data.model.Movie
import com.mehranj73.moviedb.util.originalPosterUrl
import com.mehranj73.moviedb.util.w154PosterUrl
import com.mehranj73.moviedb.util.w500PosterUrl
import com.mehranj73.moviedb.util.w92PosterUrl

class MovieAdapter(
    private val requestManager: RequestManager
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    inner class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title_textView)
        private val releaseDate: TextView = itemView.findViewById(R.id.release_date_textView)
        private val voteAverage: TextView = itemView.findViewById(R.id.vote_average_textView)
        private val overview: TextView = itemView.findViewById(R.id.overview_textView)
        private var poster: ImageView = itemView.findViewById(R.id.poster_imageView)

        fun bind(movie: Movie){
            title.text = movie.title
            releaseDate.text = movie.release_date
            voteAverage.text = movie.vote_average.toString()
            overview.text = movie.overview

            requestManager
                .load(movie.poster_path.originalPosterUrl())
                .thumbnail(requestManager.load(movie.poster_path.w92PosterUrl()))
                .into(poster)


            itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(movie)
                }
            }


        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<Movie>(){

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
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
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.bind(movie)

    }

    private var onItemClickListener: ((Movie) -> Unit)? = null


    fun setOnItemClickListener(listener: (Movie) -> Unit) {
        onItemClickListener = listener
    }


    fun preloadGlideImages(
        requestManager: RequestManager,
        list: List<Movie>
    ){
        for(movie in list){
            requestManager
                .load(movie.poster_path.originalPosterUrl())
                .thumbnail(requestManager.load(movie.poster_path.w92PosterUrl()))
                .preload()
        }
    }

}