package app.web.drjackycv.omdbmultiplatform.shared.domain.base.usecase

interface UseCase<in R, T> {

    suspend fun execute(request: R?): T

}