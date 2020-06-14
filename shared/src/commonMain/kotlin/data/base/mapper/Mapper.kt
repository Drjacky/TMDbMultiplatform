package app.web.drjackycv.omdbmultiplatform.shared.data.base.mapper

interface Mapper<in T, out E> {

    fun mapTo(response: T): E

}