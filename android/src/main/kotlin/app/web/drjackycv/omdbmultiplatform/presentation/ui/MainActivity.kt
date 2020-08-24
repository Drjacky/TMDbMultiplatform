package app.web.drjackycv.omdbmultiplatform.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.web.drjackycv.omdbmultiplatform.R
import app.web.drjackycv.omdbmultiplatform.presentation.adapter.MoviesAdapter
import app.web.drjackycv.omdbmultiplatform.presentation.entity.MovieUI
import app.web.drjackycv.omdbmultiplatform.presentation.mapper.MoviesUIMapper
import com.badoo.reaktive.observable.observeOn
import com.badoo.reaktive.scheduler.mainScheduler
import kotlinx.android.synthetic.main.activity_main.*
import presentation.base.ListViewModel
import presentation.base.ViewModelBinding
import presentation.movies.MoviesListViewModel

private const val KEYWORD = "avengers"

class MainActivity : AppCompatActivity() {

    private lateinit var mMoviesAdapter: MoviesAdapter
    private var mIsRefreshing = false
    private val mBinding = ViewModelBinding()
    private val mViewModel: ListViewModel<String, MovieUI> by lazy {
        val moviesUiMapper = MoviesUIMapper()

        MoviesListViewModel(moviesUiMapper)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
    }

    private fun setupUI() {
        mMoviesAdapter = MoviesAdapter()
        mainList.layoutManager = GridLayoutManager(this, 2)
        mainList.adapter = mMoviesAdapter

        mainList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val manager = mainList.layoutManager as LinearLayoutManager

                val totalItemCount = manager.itemCount
                val lastVisibleItem = manager.findLastVisibleItemPosition()

                if (!mIsRefreshing && totalItemCount <= lastVisibleItem + 2) {
                    loadMore()
                }
            }
        })

        mainSwipeRefresh.setOnRefreshListener {
            mViewModel.inputs.get(KEYWORD)
        }

        mViewModel.inputs.get(KEYWORD)
    }

    override fun onDestroy() {
        mBinding.dispose() //TODO Move it into view model
        super.onDestroy()
    }

    private fun binding() {
        mBinding.subscribe(mViewModel.outputs.loading.observeOn(mainScheduler), onNext = ::loading)
        mBinding.subscribe(mViewModel.outputs.result.observeOn(mainScheduler), onNext = ::result)
    }

    private fun loading(isLoading: Boolean) {
        mIsRefreshing = isLoading

        mainSwipeRefresh.isRefreshing = isLoading
    }

    private fun result(movies: List<MovieUI>) {
        mMoviesAdapter.setList(movies)
    }

    private fun loadMore() = mViewModel.inputs.loadMore(KEYWORD)

}
