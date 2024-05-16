package exronin.mycinemov.ui.movie


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import exronin.mycinemov.core.data.Resource
import exronin.mycinemov.core.domain.model.Mov
import exronin.mycinemov.core.domain.usecase.MovUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class MovViewModel @Inject constructor(private val movieUseCase: MovUseCase) : ViewModel() {
    private val _movies = MutableLiveData<Resource<List<Mov>>>()
    val movies: LiveData<Resource<List<Mov>>> get() = _movies

    private val queryState = MutableStateFlow("")

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        viewModelScope.launch {
            movieUseCase.getAllMovie().collect { resource ->
                _movies.postValue(resource)
            }
        }
    }

    fun setSearchQuery(search: String) {
        queryState.value = search
    }

    @OptIn(FlowPreview::class)
    val movieResult = queryState
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest {
            movieUseCase.searchMovie(it)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
        .asLiveData()
}
