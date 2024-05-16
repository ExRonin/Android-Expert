package exronin.mycinemov.core.domain.usecase

import exronin.mycinemov.core.data.Resource
import exronin.mycinemov.core.domain.model.Mov
import exronin.mycinemov.core.domain.repository.IMovRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovInteractor @Inject constructor(private val movieRepository: IMovRepository) :
    MovUseCase {
    override fun getAllMovie(): Flow<Resource<List<Mov>>> = movieRepository.getAllMovie()
    override fun getFavoriteMovie(): Flow<List<Mov>> = movieRepository.getFavoriteMovie()
    override fun setFavoriteMovie(movie: Mov, state: Boolean) =
        movieRepository.setFavoriteMovie(movie, state)

    override fun searchMovie(value: String): Flow<List<Mov>> = movieRepository.searchMovie(value)
    override fun getAllTvShows(): Flow<Resource<List<Mov>>> = movieRepository.getAllTvShows()

    override fun getFavoriteTvShows(): Flow<List<Mov>> = movieRepository.getFavoriteTvShows()

    override fun searchTvShows(value: String): Flow<List<Mov>> =
        movieRepository.searchTvShows(value)

}