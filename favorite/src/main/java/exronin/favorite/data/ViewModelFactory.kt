package exronin.favorite.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import exronin.mycinemov.core.domain.usecase.MovUseCase
import exronin.favorite.ui.FavoriteViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val movieUseCase: MovUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(movieUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}