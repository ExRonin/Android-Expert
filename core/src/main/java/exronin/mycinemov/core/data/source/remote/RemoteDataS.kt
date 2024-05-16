package exronin.mycinemov.core.data.source.remote

import android.util.Log
import exronin.mycinemov.core.data.source.remote.network.ApiService
import exronin.mycinemov.core.data.source.remote.response.movie.MovResponse
import exronin.mycinemov.core.data.source.remote.response.tvshows.TvResponse
import exronin.mycinemov.core.data.source.remote.apires.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataS @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllMovie(): Flow<ApiResponse<List<MovResponse>>> {
        return flow {
            try {
                val response = apiService.getMovieNowPlaying()
                val dataArray = response.results
                if (dataArray != null) {
                    if (dataArray.isNotEmpty()) {
                        emit(ApiResponse.Success(response.results))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllTvShows(): Flow<ApiResponse<List<TvResponse>>> {
        return flow {
            try {
                val response = apiService.getPopularTvShows()
                val dataArray = response.results
                if (dataArray != null) {
                    if (dataArray.isNotEmpty()) {
                        emit(ApiResponse.Success(response.results))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}