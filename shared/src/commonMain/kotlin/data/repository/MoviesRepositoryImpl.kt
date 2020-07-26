package app.web.drjackycv.omdbmultiplatform.shared.data.repository

import app.web.drjackycv.omdbmultiplatform.shared.data.base.remote.Api
import app.web.drjackycv.omdbmultiplatform.shared.data.base.repository.Repository
import app.web.drjackycv.omdbmultiplatform.shared.data.entity.Movie

class MoviesRepositoryImpl<R>(
    private val api: Api<R, List<Movie>>
) : Repository<R, List<Movie>> {

    override suspend fun get(request: R?): List<Movie> = api.execute(request)

}