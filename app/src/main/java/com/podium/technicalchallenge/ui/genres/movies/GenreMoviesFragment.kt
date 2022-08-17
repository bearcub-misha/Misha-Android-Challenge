package com.podium.technicalchallenge.ui.genres.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.databinding.FragmentAllmoviesBinding
import com.podium.technicalchallenge.databinding.FragmentGenreMoviesBinding
import com.podium.technicalchallenge.entity.Movie
import com.podium.technicalchallenge.ui.BaseFragment
import com.podium.technicalchallenge.ui.LoadingViewModel.State.LOADING
import com.podium.technicalchallenge.ui.MarginItemDecoration
import com.podium.technicalchallenge.ui.common.MoviesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenreMoviesFragment : BaseFragment() {

    override val viewModel: GenreMoviesViewModel by viewModel()
    private val args: GenreMoviesFragmentArgs by navArgs()
    private var _binding: FragmentGenreMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenreMoviesBinding.inflate(inflater)
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

        binding.title.text = args.genre

        viewModel.stateLD.observe(viewLifecycleOwner) {
            binding.loadingIndicator.isVisible = it == LOADING
            binding.movieList.isVisible = it != LOADING
        }

        viewModel.moviesLD.observe(viewLifecycleOwner) {
            binding.movieList.adapter = MoviesAdapter(this, it, object : MoviesAdapter.Listener {
                override fun onMovieSelected(movie: Movie) {
                    viewModel.onMovieSelected(movie)
                }
            })
        }

        viewModel.getMovies(args.genre)
    }

}

