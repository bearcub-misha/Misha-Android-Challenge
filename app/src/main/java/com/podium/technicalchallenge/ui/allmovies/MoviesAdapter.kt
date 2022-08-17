package com.podium.technicalchallenge.ui.allmovies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.entity.MovieEntity
import com.podium.technicalchallenge.ui.allmovies.MoviesAdapter.ViewHolder

class MoviesAdapter(
    private val fragment: Fragment,
    private val dataSet: List<MovieEntity>,
    private val listener: Listener
) :
    RecyclerView.Adapter<ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var title: TextView = itemView.findViewById(R.id.title)
        private var image: ImageView = itemView.findViewById(R.id.image)

        fun bind(movie: MovieEntity, fragment: Fragment) {
            title.text = movie.title
            Glide.with(fragment)
                .load(movie.imageUrl)
                .into(image);
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.movie_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val movie = dataSet[position]
        viewHolder.bind(movie, fragment)
        viewHolder.itemView
            .setOnClickListener { listener.onMovieSelected(movie) }
    }

    override fun getItemCount() = dataSet.size

    interface Listener {
        fun onMovieSelected(movie: MovieEntity)
    }
}
