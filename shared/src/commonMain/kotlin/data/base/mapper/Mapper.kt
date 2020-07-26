package data.base.mapper

interface Mapper<in T, out E> {

    fun mapTo(response: T): E

}