package exronin.mycinemov.core.data

import exronin.mycinemov.core.data.source.local.LocalDataSource
import exronin.mycinemov.core.data.source.remote.RemoteDataS
import exronin.mycinemov.core.data.source.remote.apires.ApiResponse
import exronin.mycinemov.core.data.source.remote.response.movie.MovResponse
import exronin.mycinemov.core.data.source.remote.response.tvshows.TvResponse
import exronin.mycinemov.core.domain.model.Mov
import exronin.mycinemov.core.domain.repository.IMovRepository
import exronin.mycinemov.core.utils.AppExecutors
import exronin.mycinemov.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MovRepository @Inject constructor(
    private val remoteDataSource: RemoteDataS,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovRepository {
    override fun getAllMovie(): Flow<Resource<List<Mov>>> {
        return object :
            Network<List<Mov>, List<MovResponse>>() {
            override fun loadFromDB(): Flow<List<Mov>> {
                return localDataSource.getAllMovie().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Mov>?): Boolean = data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovResponse>>> =
                remoteDataSource.getAllMovie()

            override suspend fun saveCallResult(data: List<MovResponse>) {
                val movieList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()
    }

    override fun getFavoriteMovie(): Flow<List<Mov>> {
        return localDataSource.getFavoriteMovie().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteMovie(movie: Mov, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movieEntity, state) }
    }

    override fun searchMovie(value: String): Flow<List<Mov>> {
        return localDataSource.searchMovie(value).map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }


    override fun getAllTvShows(): Flow<Resource<List<Mov>>> {
        return object :
            Network<List<Mov>, List<TvResponse>>() {
            override fun loadFromDB(): Flow<List<Mov>> {
                return localDataSource.getAllTvShows().map {
                    DataMapper.mapEntitiesToDomain(it)
                }

            }

            override fun shouldFetch(data: List<Mov>?): Boolean = data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<TvResponse>>> =
                remoteDataSource.getAllTvShows()

            override suspend fun saveCallResult(data: List<TvResponse>) {
                val movieList = DataMapper.mapResponsesTvShowsToEntities(data)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()
    }

    override fun getFavoriteTvShows(): Flow<List<Mov>> {
        return localDataSource.getFavoriteTvShows().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun searchTvShows(value: String): Flow<List<Mov>> {
        return localDataSource.searchTvShows(value).map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

}