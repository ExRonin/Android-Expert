package exronin.mycinemov.core.utils

import exronin.mycinemov.core.data.source.local.entity.MovEntity
import exronin.mycinemov.core.data.source.remote.response.movie.MovResponse
import exronin.mycinemov.core.data.source.remote.response.tvshows.TvResponse
import exronin.mycinemov.core.domain.model.Mov

object DataMapper {
    fun mapResponsesToEntities(input: List<MovResponse>): List<MovEntity> {
        val movieList = ArrayList<MovEntity>()
        input.map { movieResponse ->

            val movie = MovEntity(
                movie_id = movieResponse.id,
                name = movieResponse.originalTitle,
                overview = movieResponse.overview,
                poster_path = movieResponse.posterPath,
                release_date = movieResponse.releaseDate,
                vote_average = movieResponse.voteAverage,
                vote_count = movieResponse.voteCount,
                isFavorite = false,
                original_language = movieResponse.originalLanguage,
                isTvShows = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<MovEntity>): List<Mov> =
        input.map {
            Mov(
                id = it.movie_id,
                name = it.name.toString(),
                overview = it.overview,
                poster = it.poster_path,
                isFavorite = it.isFavorite,
                vote_average = it.vote_average,
                release_date = it.release_date,
                vote_count = it.vote_count,
                original_language = it.original_language,
                isTvShows = it.isTvShows
            )
        }

    fun mapDomainToEntity(input: Mov) =
        MovEntity(
            movie_id = input.id,
            overview = input.overview,
            name = input.name,
            poster_path = input.poster,
            vote_average = input.vote_average,
            isFavorite = input.isFavorite,
            release_date = input.release_date,
            vote_count = input.vote_count,
            original_language = input.original_language,
            isTvShows = input.isTvShows
        )

    fun mapResponsesTvShowsToEntities(input: List<TvResponse>): List<MovEntity> {
        val movieList = ArrayList<MovEntity>()
        input.map { movieResponse ->

            val movie = MovEntity(
                movie_id = movieResponse.id,
                name = movieResponse.name,
                overview = movieResponse.overview,
                poster_path = movieResponse.posterPath,
                release_date = movieResponse.firstAirDate,
                vote_average = movieResponse.voteAverage,
                vote_count = movieResponse.voteCount,
                isFavorite = false,
                original_language = movieResponse.originalLanguage,
                isTvShows = true
            )
            movieList.add(movie)
        }
        return movieList
    }


}