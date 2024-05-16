package exronin.mycinemov.ui.movie

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import exronin.mycinemov.R
import exronin.mycinemov.core.data.Resource
import exronin.mycinemov.core.ui.MovAdapter
import exronin.mycinemov.databinding.FragmentMovieBinding
import exronin.mycinemov.ui.movie_detail.MovDetailActivity
import exronin.mycinemov.ui.setting.SettingActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovFragment : Fragment() {

    private val movieViewModel: MovViewModel by viewModels()
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("FragmentMovieBinding should not be null")
    private lateinit var searchView: SearchView
    private lateinit var movieAdapter: MovAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovAdapter()
        movieAdapter.onItemClick = { selectedData ->
            val intent = Intent(requireActivity(), MovDetailActivity::class.java)
            intent.putExtra(MovDetailActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }

        binding.rvMov.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
        binding.searchIv.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }
        observeMovie()
        movieViewModel.fetchMovies()
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        @Suppress("DEPRECATION")
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)

        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as? SearchManager
        searchManager?.let { it ->
            searchView = menu.findItem(R.id.search_menu).actionView as SearchView
            searchView.setSearchableInfo(it.getSearchableInfo(requireActivity().componentName))
            searchView.queryHint = resources.getString(R.string.menu_search)

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(newText: String): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    newText.let {
                        if (newText.isEmpty()) {
                            observeMovie()
                        } else {
                            movieViewModel.setSearchQuery(it)
                        }
                    }
                    return true
                }
            })
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_setting) {
            val intent = Intent(requireActivity(), SettingActivity::class.java)
            startActivity(intent)
            return true
        }
        @Suppress("DEPRECATION")
        return super.onOptionsItemSelected(item)
    }

    private fun observeMovie() {
        movieViewModel.movies.observe(viewLifecycleOwner) { movie ->
            when (movie) {
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    movieAdapter.setData(movie.data)
                    binding.viewError.root.visibility = View.GONE
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.viewError.root.visibility = View.VISIBLE
                    binding.viewError.tvMsgError.text = getString(R.string.notification_error)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
