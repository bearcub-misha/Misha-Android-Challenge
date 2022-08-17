package com.podium.technicalchallenge.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.podium.technicalchallenge.databinding.FragmentMovieDetailsBinding
import com.podium.technicalchallenge.entity.Movie
import com.podium.technicalchallenge.ui.BaseFragment
import com.podium.technicalchallenge.ui.LoadingViewModel
import com.podium.technicalchallenge.ui.common.MoviesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment : BaseFragment() {

    override val viewModel: MovieDetailsViewModel by viewModel()
    private val args: MovieDetailsFragmentArgs by navArgs()
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.stateLD.observe(viewLifecycleOwner) {
            binding.loadingIndicator.isVisible = it == LoadingViewModel.State.LOADING
            binding.detailsContainer.isVisible = it != LoadingViewModel.State.LOADING
        }

        viewModel.movieDetailsLD.observe(viewLifecycleOwner) {
            binding.title.text = "Title: ${it.title}"
            binding.rating.text = "Rating: ${it.rating}"
            binding.genres.text = "Genres: ${it.genres.joinToString(separator = ", ")}"
            binding.description.text = "Description: ${it.description}"
            binding.director.text = "Director: ${it.directorName}"
            binding.cast.text =
                "Cast: ${it.cast.joinToString(separator = ", ") { actor -> actor.name }}"
            Glide.with(this)
                .load(it.imageUrl)
                .into(binding.image)
        }

        viewModel.loadMovieDetails(args.movieId)
    }

}

