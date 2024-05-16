package exronin.mycinemov.ui.tvshows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import exronin.mycinemov.core.domain.usecase.MovUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@FlowPreview
@ExperimentalCoroutinesApi
class TvViewModel @Inject constructor(movieUseCase: MovUseCase) : ViewModel() {
    val movies = movieUseCase.getAllTvShows().asLiveData()

    private val queryState = MutableStateFlow("")

    fun setSearchQuery(search: String) {
        queryState.value = search
    }

    val movieResult = queryState
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest {
            movieUseCase.searchTvShows(it)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
        .asLiveData()
}
