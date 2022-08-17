package com.podium.technicalchallenge.ui.genres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.databinding.FragmentGenresBinding
import com.podium.technicalchallenge.entity.Genre
import com.podium.technicalchallenge.ui.BaseFragment
import com.podium.technicalchallenge.ui.LoadingViewModel
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
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.genre_list_margin))
        )

        viewModel.stateLD.observe(viewLifecycleOwner) {
            binding.loadingIndicator.isVisible = it == LoadingViewModel.State.LOADING
            binding.genresList.isVisible = it != LoadingViewModel.State.LOADING
        }

        viewModel.genresLD.observe(viewLifecycleOwner) {
            binding.genresList.adapter = GenreAdapter(it, object : GenreAdapter.Listener {
                override fun onGenreSelected(genre: Genre) {
                    viewModel.onGenreSelected(genre)
                }
            })
        }

        viewModel.getGenres()
    }

}

