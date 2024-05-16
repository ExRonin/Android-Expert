package exronin.mycinemov.core.data.source.local

import exronin.mycinemov.core.data.source.local.entity.MovEntity
import exronin.mycinemov.core.data.source.local.room.MovDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val movieDao: MovDao) {
    fun getAllMovie(): Flow<List<MovEntity>> = movieDao.getListMovie()

    fun getFavoriteMovie(): Flow<List<MovEntity>> = movieDao.getBookmarkedMovie()

    suspend fun insertMovie(movie: List<MovEntity>) = movieDao.insertMovie(movie)

    fun setFavoriteMovie(movie: MovEntity, newState: Boolean) {
        movie.isFavorite = newState
        movieDao.updateMovie(movie)
    }

    fun searchMovie(value: String): Flow<List<MovEntity>> = movieDao.searchMovies(value)

    fun getAllTvShows(): Flow<List<MovEntity>> = movieDao.getListTvShows()

    fun getFavoriteTvShows(): Flow<List<MovEntity>> = movieDao.getBookmarkedTvShows()

    fun searchTvShows(value: String): Flow<List<MovEntity>> = movieDao.searchTvShows(value)
}