package com.podium.technicalchallenge.ui.allmovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.databinding.FragmentAllmoviesBinding
import com.podium.technicalchallenge.entity.MovieEntity
import com.podium.technicalchallenge.ui.BaseFragment
import com.podium.technicalchallenge.ui.LoadingViewModel
import com.podium.technicalchallenge.ui.LoadingViewModel.State.LOADING
import com.podium.technicalchallenge.ui.MarginItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllMoviesFragment : BaseFragment() {

    override val viewModel: AllMoviesViewModel by viewModel()
    private var _binding: FragmentAllmoviesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllmoviesBinding.inflate(inflater)
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
            binding.loadingIndicator.isVisible = it == LOADING
            binding.movieList.isVisible = it != LOADING
        }

        viewModel.moviesLD.observe(viewLifecycleOwner) {
            binding.movieList.adapter = MoviesAdapter(this, it, object : MoviesAdapter.Listener {
                override fun onMovieSelected(movie: MovieEntity) {
                    viewModel.onMovieSelected(movie)
                }
            })
        }

        viewModel.getMovies()
    }

}

