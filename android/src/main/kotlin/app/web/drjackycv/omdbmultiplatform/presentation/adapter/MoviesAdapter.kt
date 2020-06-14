package app.web.drjackycv.omdbmultiplatform.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.web.drjackycv.omdbmultiplatform.R
import app.web.drjackycv.omdbmultiplatform.presentation.entity.MovieUI
import app.web.drjackycv.omdbmultiplatform.presentation.extension.load

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private var mItems = mutableListOf<MovieUI>()

    fun setList(list: List<MovieUI>) {
        mItems.clear()
        mItems.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun getItemCount() = mItems.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = mItems[position]

        holder.itemMovieImv.load(movie.poster)
        holder.itemMovieTitleTxv.text = movie.title
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemMovieImv: ImageView = itemView.findViewById(R.id.itemMovieImv)
        val itemMovieTitleTxv: TextView = itemView.findViewById(R.id.itemMovieTitleTxv)
    }

}