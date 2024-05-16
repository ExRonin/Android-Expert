package exronin.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import exronin.mycinemov.core.domain.usecase.MovUseCase

class FavoriteViewModel(movieUseCase: MovUseCase) : ViewModel() {
    val moviesFavorite = movieUseCase.getFavoriteMovie().asLiveData()
    val tvShowsFavorite = movieUseCase.getFavoriteTvShows().asLiveData()
}