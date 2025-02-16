package com.podium.technicalchallenge.ui.topmovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.databinding.FragmentTopmoviesBinding
import com.podium.technicalchallenge.entity.Movie
import com.podium.technicalchallenge.ui.BaseFragment
import com.podium.technicalchallenge.ui.LoadingViewModel
import com.podium.technicalchallenge.ui.MarginItemDecoration
import com.podium.technicalchallenge.ui.common.MoviesAdapter
import com.podium.technicalchallenge.ui.common.MoviesAdapter.Listener
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopMoviesFragment : BaseFragment() {

    override val viewModel: TopMoviesViewModel by viewModel()
    private var _binding: FragmentTopmoviesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopmoviesBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieList.addItemDecoration(
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.movie_list_margin))
        )

        viewModel.stateLD.observe(viewLifecycleOwner) {
            binding.loadingIndicator.isVisible = it == LoadingViewModel.State.LOADING
            binding.movieList.isVisible = it != LoadingViewModel.State.LOADING
        }

        viewModel.moviesLD.observe(viewLifecycleOwner) {
            binding.movieList.adapter =
                MoviesAdapter(this, it, object : Listener {
                    override fun onMovieSelected(movie: Movie) {
                        viewModel.onMovieSelected(movie)
                    }
                })
        }

        viewModel.getMovies()
    }

}

