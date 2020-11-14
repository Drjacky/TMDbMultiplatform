package presentation.movies

import com.badoo.reaktive.observable.*
import com.badoo.reaktive.subject.publish.PublishSubject
import data.base.coroutines.singleFromCoroutine
import data.base.mapper.Mapper
import data.entity.Movie
import di.Di
import domain.base.usecase.UseCase
import org.kodein.di.instance
import presentation.base.ListViewModel
import presentation.base.ListViewModelInput
import presentation.base.ListViewModelOutput

class MoviesListViewModel<E>(
    mapper: Mapper<List<Movie>, List<E>>?
) : ListViewModel<String, E>, ListViewModelInput<String>, ListViewModelOutput<String, E> {
    override val inputs: ListViewModelInput<String> = this
    override val outputs: ListViewModelOutput<String, E> = this
    override val loading: Observable<Boolean>
    override val result: Observable<List<E>>

    val useCase: UseCase<String, List<Movie>> by Di.di.instance("UseCase")
    private val mListProperty = PublishSubject<String>()// publishSubject<String>()
    private val mLoadMoreProperty = PublishSubject<String>()//publishSubject<String>()

    init {
        val loadingProperty = PublishSubject<Boolean>()//publishSubject<Boolean>()

        val items = mutableListOf<E>()

        var clearItems = false

        loading = loadingProperty

        val initialRequest = mListProperty
            .doOnBeforeNext { loadingProperty.onNext(true) }
            .flatMapSingle { request ->
                singleFromCoroutine { useCase.execute(request) }
            }
            .doOnBeforeNext {
                loadingProperty.onNext(false)
                clearItems = true
            }

        val nextRequest = mLoadMoreProperty
            .doOnBeforeNext { loadingProperty.onNext(true) }
            .flatMapSingle { request ->
                singleFromCoroutine { useCase.execute(request) }
            }
            .doOnBeforeNext {
                loadingProperty.onNext(false)
                clearItems = false
            }

        result = merge(initialRequest, nextRequest).map {
            if (clearItems) {
                items.clear()
            }

            @Suppress("UNCHECKED_CAST")
            val list = mapper?.mapTo(it) ?: it as List<E>

            items.addAll(list)

            items
        }

    }

    override fun get(request: String) {
        mListProperty.onNext(request)
    }

    override fun loadMore(request: String) {
        mLoadMoreProperty.onNext(request)
    }

}