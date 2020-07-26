package presentation.base

import com.badoo.reaktive.observable.Observable

interface ListViewModelInput<in R> {

    fun get(request: R)
    fun loadMore(request: R)

}

interface ListViewModelOutput<R, T> {

    val loading: Observable<Boolean>
    val result: Observable<List<T>>

}

interface ListViewModel<R, T> {

    val inputs: ListViewModelInput<R>
    val outputs: ListViewModelOutput<R, T>

}