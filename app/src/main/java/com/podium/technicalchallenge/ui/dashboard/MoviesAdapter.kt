package com.podium.technicalchallenge.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.entity.MovieEntity
import com.podium.technicalchallenge.ui.dashboard.MoviesAdapter.ViewHolder

class MoviesAdapter(
    private val dataSet: List<MovieEntity>,
    private val listener: Listener
) :
    RecyclerView.Adapter<ViewHolder>() {

    class ViewHolder(view: View, private val listener: Listener) : RecyclerView.ViewHolder(view) {
        private var title: TextView = itemView.findViewById(R.id.title)

        fun bind(movie: MovieEntity) {
            title.text = movie.title
            itemView.findViewById<View>(R.id.container)
                .setOnClickListener { listener.onMovieSelected(movie) }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.movie_item, viewGroup, false)
        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val movie = dataSet[position]
        viewHolder.bind(movie)
    }

    override fun getItemCount() = dataSet.size

    interface Listener {
        fun onMovieSelected(movie: MovieEntity)
    }
}
