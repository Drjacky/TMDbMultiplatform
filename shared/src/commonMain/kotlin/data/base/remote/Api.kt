package data.base.remote

interface Api<in R, T> {

    suspend fun execute(request: R?): T

}