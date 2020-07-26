package domain.base.repository

interface Repository<in R, T> {

    suspend fun get(request: R?): T

}