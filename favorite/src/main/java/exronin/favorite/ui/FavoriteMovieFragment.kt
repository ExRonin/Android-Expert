package exronin.favorite.ui
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.EntryPointAccessors
import exronin.mycinemov.core.ui.MovAdapter
import exronin.favorite.databinding.FragmentMovieFavoriteBinding
import exronin.favorite.data.DaggerFavoriteModule
import exronin.mycinemov.di.FavoriteModuleDependencies
import exronin.mycinemov.ui.movie_detail.MovDetailActivity

import javax.inject.Inject
class FavoriteMovieFragment @Inject constructor(private val isMovie: Boolean) : Fragment() {

    @Inject
    lateinit var factory: exronin.favorite.data.ViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }

    private var _binding: FragmentMovieFavoriteBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("FragmentMovieFavoriteBinding should not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFavoriteModule.builder()
            .context(context)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    context,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieAdapter = MovAdapter()
        movieAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, MovDetailActivity::class.java)
            intent.putExtra(MovDetailActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }

        if (isMovie) {
            favoriteViewModel.moviesFavorite.observe(viewLifecycleOwner) { movie ->
                movieAdapter.setData(movie)
                viewEmpty(movie.isNotEmpty())
            }
        } else {
            favoriteViewModel.tvShowsFavorite.observe(viewLifecycleOwner) { tvShow ->
                movieAdapter.setData(tvShow)
                viewEmpty(tvShow.isNotEmpty())
            }
        }

        with(binding.rvMovFav) {
            layoutManager = GridLayoutManager(activity, 2)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun viewEmpty(notEmpty: Boolean) {
        if (notEmpty) {
            binding.tvMsgError.visibility = View.GONE
        } else {
            binding.tvMsgError.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvMovFav.adapter = null
        _binding = null
    }
}
