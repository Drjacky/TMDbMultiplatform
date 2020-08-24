package app.web.drjackycv.tmdbmultiplatform.presentation.mapper

import app.web.drjackycv.tmdbmultiplatform.presentation.entity.MovieUI
import data.base.mapper.Mapper
import data.entity.Movie

class MoviesUIMapper : Mapper<List<Movie>, List<MovieUI>> {

    override fun mapTo(response: List<Movie>): List<MovieUI> = response.map {
        MovieUI(
            popularity = it.popularity,
            voteCount = it.voteCount,
            video = it.video,
            posterPath = it.posterPath,
            id = it.id,
            adult = it.adult,
            backdropPath = it.backdropPath,
            originalLanguage = it.originalLanguage,
            originalTitle = it.originalTitle,
            genreIds = it.genreIds,
            title = it.title,
            voteAverage = it.voteAverage,
            overview = it.overview,
            releaseDate = it.releaseDate,
            fullPosterPath = it.fullPosterPath
        )
    }

}