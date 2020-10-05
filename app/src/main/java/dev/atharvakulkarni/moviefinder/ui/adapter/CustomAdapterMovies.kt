package dev.atharvakulkarni.moviefinder.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.atharvakulkarni.moviefinder.R
import dev.atharvakulkarni.moviefinder.data.model.SearchResults
import dev.atharvakulkarni.moviefinder.util.loadImage
import dev.atharvakulkarni.moviefinder.util.show


class CustomAdapterMovies : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    private var moviesList = ArrayList<SearchResults.SearchItem?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.list_item_movie, parent, false)
            MovieViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_lazy_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imagePoster: ImageView = view.findViewById(R.id.image_poster)
        private val textTitle: TextView = view.findViewById(R.id.text_title)
        private val textYear: TextView = view.findViewById(R.id.text_year)

        @SuppressLint("SetTextI18n")
        fun bindItems(movie: SearchResults.SearchItem) {
            textTitle.text = movie.title
            textYear.text = movie.year
            imagePoster.context.loadImage(movie.poster, imagePoster)
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder)
            if (moviesList[position] != null)
                holder.bindItems(moviesList[position]!!)
            else if (holder is LoadingViewHolder)
                holder.showLoadingView()
    }

    override fun getItemViewType(position: Int): Int {
        return if (moviesList[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    fun setData(newMoviesList: ArrayList<SearchResults.SearchItem?>?) {
        if (newMoviesList != null) {
            if (moviesList.isNotEmpty())
                moviesList.removeAt(moviesList.size - 1)
            moviesList.clear()
            moviesList.addAll(newMoviesList)
        } else
            moviesList.add(newMoviesList)
        notifyDataSetChanged()
    }

    fun getData() = moviesList

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)

        fun showLoadingView() {
            progressBar.show()
        }
    }
}