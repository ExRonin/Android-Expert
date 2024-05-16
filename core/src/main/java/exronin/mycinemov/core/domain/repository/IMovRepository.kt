package exronin.mycinemov.core.domain.repository

import exronin.mycinemov.core.data.Resource
import exronin.mycinemov.core.domain.model.Mov
import kotlinx.coroutines.flow.Flow

interface IMovRepository {
    fun getAllMovie(): Flow<Resource<List<Mov>>>

    fun getFavoriteMovie(): Flow<List<Mov>>

    fun setFavoriteMovie(movie: Mov, state: Boolean)

    fun searchMovie(value: String): Flow<List<Mov>>

    fun getAllTvShows(): Flow<Resource<List<Mov>>>

    fun getFavoriteTvShows(): Flow<List<Mov>>

    fun searchTvShows(value: String): Flow<List<Mov>>
}