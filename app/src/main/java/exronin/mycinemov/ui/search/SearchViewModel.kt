package exronin.mycinemov.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import exronin.mycinemov.core.data.source.remote.network.ApiService
import exronin.mycinemov.core.data.source.remote.response.search.SearchResponse
import exronin.mycinemov.core.domain.model.Mov
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _searchResults = MutableLiveData<List<Mov>?>()
    val searchResults: MutableLiveData<List<Mov>?> get() = _searchResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun searchMovies(query: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = apiService.getSearchedMovie(query)
                val searchResponse = response.results
                val movList =
                    searchResponse?.let { mapResponsesToMovs(it) }
                _searchResults.postValue(movList)
                if (movList != null) {
                    if (movList.isEmpty()) {
                        _errorMessage.value = "No results found"
                    } else {
                        _errorMessage.value = null
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun mapResponsesToMovs(searchResponses: List<SearchResponse>): List<Mov> {
        return searchResponses.map { searchResponse ->
            Mov(
                id = searchResponse.id,
                name = searchResponse.title ?: "",
                overview = searchResponse.overview,
                poster = searchResponse.posterPath,
                isFavorite = false,
                isTvShows = false,
                vote_average = searchResponse.voteAverage,
                release_date = searchResponse.releaseDate,
                vote_count = searchResponse.voteCount,
                original_language = searchResponse.originalLanguage
            )
        }
    }
}
