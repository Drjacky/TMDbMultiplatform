package app.web.drjackycv.omdbmultiplatform.presentation.mapper

import app.web.drjackycv.omdbmultiplatform.presentation.entity.MovieUI
import app.web.drjackycv.omdbmultiplatform.shared.data.base.mapper.Mapper
import app.web.drjackycv.omdbmultiplatform.shared.data.entity.Movie

class MoviesMapper : Mapper<List<Movie>, List<MovieUI>> {

    override fun mapTo(response: List<Movie>): List<MovieUI> {
        return response.map {
            MovieUI(
                title = it.title,
                imdbID = it.imdbID,
                rated = it.rating,
                poster = it.poster
            )
        }
    }
}