package app.web.drjackycv.omdbmultiplatform.shared.data.base.remote

interface Api<in R, T> {

    suspend fun execute(request: R?): T

}