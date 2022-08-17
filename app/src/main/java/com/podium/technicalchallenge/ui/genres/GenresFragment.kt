package com.podium.technicalchallenge.ui.genres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.databinding.FragmentGenresBinding
import com.podium.technicalchallenge.ui.BaseFragment
import com.podium.technicalchallenge.ui.MarginItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenresFragment : BaseFragment() {

    override val viewModel: GenresViewModel by viewModel()
    private var _binding: FragmentGenresBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenresBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.genresList.addItemDecoration(
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.movie_list_margin))
        )

        viewModel.stateLD.observe(viewLifecycleOwner) {
        }

        viewModel.moviesLD.observe(viewLifecycleOwner) {
//            binding.movieList.adapter = MoviesAdapter(this, it, object : MoviesAdapter.Listener {
//                override fun onMovieSelected(movie: MovieEntity) {
//                    viewModel.onMovieSelected(movie)
//                }
//            })
        }

        viewModel.getMovies()
    }

}

