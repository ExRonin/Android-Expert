package exronin.mycinemov.core.data.source.remote.network

import exronin.mycinemov.core.BuildConfig
import exronin.mycinemov.core.data.source.remote.response.ListMovResponse
import exronin.mycinemov.core.data.source.remote.response.movie.MovResponse
import exronin.mycinemov.core.data.source.remote.response.search.SearchResponse
import exronin.mycinemov.core.data.source.remote.response.tvshows.TvResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("movie/now_playing")
    suspend fun getMovieNowPlaying(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): ListMovResponse<MovResponse>

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): ListMovResponse<TvResponse>


    @GET("search/movie")
    suspend fun getSearchedMovie(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): ListMovResponse<SearchResponse>
}
