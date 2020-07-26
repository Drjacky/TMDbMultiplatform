package domain.usecase

import data.entity.Movie
import domain.base.repository.Repository
import domain.base.usecase.UseCase

class GetMoviesUseCaseImpl<R>(
    private val repository: Repository<R, List<Movie>>
) : UseCase<R, List<Movie>> {

    override suspend fun execute(request: R?): List<Movie> {
        return repository.get(request)
    }

}