package exronin.mycinemov.ui.tvshows

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import exronin.mycinemov.R
import exronin.mycinemov.core.data.Resource
import exronin.mycinemov.core.ui.MovAdapter
import exronin.mycinemov.databinding.FragmentTvshowsBinding
import exronin.mycinemov.ui.movie_detail.MovDetailActivity
import exronin.mycinemov.ui.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TvFragment : Fragment() {

    private val movieViewModel: TvViewModel by viewModels()
    private var _binding: FragmentTvshowsBinding? = null
    private val binding get() = _binding as FragmentTvshowsBinding
    private lateinit var searchView: SearchView
    private lateinit var movieAdapter: MovAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvshowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            movieAdapter = MovAdapter()
            movieAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, MovDetailActivity::class.java)
                intent.putExtra(MovDetailActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }

            movieViewModel.movieResult.observe(viewLifecycleOwner) { movie ->
                if (searchView.query.toString() == "") {
                    binding.viewError.root.visibility = View.GONE
                    observerMovie()
                } else {
                    if (movie.isNullOrEmpty()) {
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvMsgError.text = getString(R.string.notification_empty)
                    } else {
                        binding.viewError.root.visibility = View.GONE
                    }
                    movieAdapter.setData(movie)
                }
            }


            with(binding.rvTvShows) {
                layoutManager = GridLayoutManager(activity, 2)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
            observerMovie()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.search_menu).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = resources.getString(R.string.menu_search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(newText: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {

                newText.let {
                    if (newText == "" || newText.isEmpty()) {
                        observerMovie()
                    } else {
                        movieViewModel.setSearchQuery(it)
                    }
                }
                return true
            }
        })

        @Suppress("DEPRECATION")
        super.onCreateOptionsMenu(menu, inflater)

    }

    private fun observerMovie() {
        movieViewModel.movies.observe(viewLifecycleOwner) { movie ->
            if (movie != null) {
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
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_setting) {
            val intent = Intent(activity, SettingActivity::class.java)
            startActivity(intent)
        }
        @Suppress("DEPRECATION")
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvTvShows.adapter = null
        searchView.setOnQueryTextListener(null)
        _binding = null
    }
}