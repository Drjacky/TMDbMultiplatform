package data.repository

import data.base.remote.Api
import data.entity.Movie
import domain.base.repository.Repository

class MoviesRepositoryImpl<R>(
    private val api: Api<R, List<Movie>>
) : Repository<R, List<Movie>> {

    override suspend fun get(request: R?): List<Movie> = api.execute(request)

}