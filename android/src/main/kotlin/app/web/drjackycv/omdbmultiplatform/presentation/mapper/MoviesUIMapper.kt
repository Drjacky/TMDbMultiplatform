package app.web.drjackycv.omdbmultiplatform.presentation.mapper

import app.web.drjackycv.omdbmultiplatform.presentation.entity.MovieUI
import data.base.mapper.Mapper
import data.entity.Movie

class MoviesUIMapper : Mapper<List<Movie>, List<MovieUI>> {

    override fun mapTo(response: List<Movie>): List<MovieUI> = response.map {
        MovieUI(
            title = it.title,
            year = it.year,
            imdbID = it.imdbID,
            type = it.type,
            poster = it.poster
        )
    }

}