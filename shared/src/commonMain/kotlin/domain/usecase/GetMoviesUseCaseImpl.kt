package app.web.drjackycv.omdbmultiplatform.shared.domain.usecase

import app.web.drjackycv.omdbmultiplatform.shared.data.entity.Movie
import app.web.drjackycv.omdbmultiplatform.shared.domain.base.repository.Repository
import app.web.drjackycv.omdbmultiplatform.shared.domain.base.usecase.UseCase

class GetMoviesUseCaseImpl<R>(
    private val repository: Repository<R, List<Movie>>
) : UseCase<R, List<Movie>> {

    override suspend fun execute(request: R?): List<Movie> {
        return repository.get(request)
    }

}