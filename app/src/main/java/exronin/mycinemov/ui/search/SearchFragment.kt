package exronin.mycinemov.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import exronin.mycinemov.databinding.FragmentSearchBinding
import exronin.mycinemov.ui.movie_detail.MovDetailActivity
import exronin.mycinemov.ui.search.adapter.SearchAdapter
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("FragmentSearchBinding should not be null")
    private lateinit var navController: NavController
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        searchAdapter = SearchAdapter { selectedData ->
            val intent = Intent(requireActivity(), MovDetailActivity::class.java)
            intent.putExtra(MovDetailActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }

        binding.searchlist.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = searchAdapter
        }

        binding.searchEt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val query = binding.searchEt.text.toString()
                if (query.isNotEmpty()) {
                    searchViewModel.searchMovies(query)
                }
                true
            } else {
                false
            }
        }

        searchViewModel.searchResults.observe(viewLifecycleOwner) { results ->
            searchAdapter.submitList(results)
        }

        searchViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBarSearch.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        searchViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                binding.alertTv.text = errorMessage
                binding.alertTv.visibility = View.VISIBLE
            } else {
                binding.alertTv.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
