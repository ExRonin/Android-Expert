package exronin.mycinemov.core.data.source.local.room

import androidx.room.*
import exronin.mycinemov.core.data.source.local.entity.MovEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovDao {
    @Update
    fun updateMovie(movie: MovEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: List<MovEntity>)

    @Query("SELECT * FROM tb_movie where isTvShows = 0 ")
    fun getListMovie(): Flow<List<MovEntity>>

    @Query("SELECT * FROM tb_movie where bookmarked = 1 and isTvShows = 0")
    fun getBookmarkedMovie(): Flow<List<MovEntity>>

    @Query("SELECT * FROM tb_movie WHERE  title LIKE '%' || :search || '%' and isTvShows = 0")
    fun searchMovies(search: String): Flow<List<MovEntity>>


    @Query("SELECT * FROM tb_movie where isTvShows = 1 ")
    fun getListTvShows(): Flow<List<MovEntity>>

    @Query("SELECT * FROM tb_movie where bookmarked = 1 and isTvShows = 1")
    fun getBookmarkedTvShows(): Flow<List<MovEntity>>

    @Query("SELECT * FROM tb_movie WHERE  title LIKE '%' || :search || '%' and isTvShows = 1")
    fun searchTvShows(search: String): Flow<List<MovEntity>>
}