package com.podium.technicalchallenge.ui.genres

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.entity.Genre
import com.podium.technicalchallenge.ui.genres.GenreAdapter.ViewHolder

class GenreAdapter(
    private val dataSet: List<Genre>,
    private val listener: Listener
) :
    RecyclerView.Adapter<ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var title: TextView = itemView.findViewById(R.id.title)

        fun bind(genre: Genre) {
            title.text = genre.title
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.genre_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val genre = dataSet[position]
        viewHolder.bind(genre)
        viewHolder.itemView
            .setOnClickListener { listener.onGenreSelected(genre) }
    }

    override fun getItemCount() = dataSet.size

    interface Listener {
        fun onGenreSelected(genre: Genre)
    }
}
