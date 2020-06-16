package app.web.drjackycv.omdbmultiplatform.shared.data.mapper

import app.web.drjackycv.omdbmultiplatform.shared.data.base.mapper.Mapper
import app.web.drjackycv.omdbmultiplatform.shared.data.entity.Movie
import app.web.drjackycv.omdbmultiplatform.shared.data.entity.MoviesResponse

class MoviesMapper : Mapper<MoviesResponse, List<Movie>> {

    override fun mapTo(response: MoviesResponse): List<Movie> = response.items.map {
        Movie(
            title = it.title,
            year = it.year,
            imdbID = it.imdbID,
            type = it.type,
            poster = it.poster
        )
    }

}