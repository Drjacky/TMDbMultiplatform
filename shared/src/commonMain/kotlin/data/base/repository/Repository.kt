package app.web.drjackycv.omdbmultiplatform.shared.data.base.repository

interface Repository<in R, T> {

    suspend fun get(request: R?): T

}