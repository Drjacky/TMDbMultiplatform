package data.mapper

import data.base.mapper.Mapper
import data.entity.Movie
import data.entity.MoviesResponse

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