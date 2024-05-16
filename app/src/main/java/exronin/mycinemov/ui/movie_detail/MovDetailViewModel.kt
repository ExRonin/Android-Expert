package exronin.mycinemov.ui.movie_detail



import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import exronin.mycinemov.core.domain.model.Mov
import exronin.mycinemov.core.domain.usecase.MovUseCase
import javax.inject.Inject

@HiltViewModel
class MovDetailViewModel @Inject constructor(
    private val movieUseCase: MovUseCase
) : ViewModel() {
    fun setFavoriteMovie(movie: Mov, newStatus: Boolean) =
        movieUseCase.setFavoriteMovie(movie, newStatus)
}
