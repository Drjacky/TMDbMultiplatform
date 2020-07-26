package app.web.drjackycv.omdbmultiplatform.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.web.drjackycv.omdbmultiplatform.BuildConfig
import app.web.drjackycv.omdbmultiplatform.R
import app.web.drjackycv.omdbmultiplatform.presentation.adapter.MoviesAdapter
import app.web.drjackycv.omdbmultiplatform.presentation.entity.MovieUI
import app.web.drjackycv.omdbmultiplatform.presentation.mapper.MoviesUIMapper
import data.mapper.MoviesMapper
import data.remote.MoviesApiImpl
import data.repository.MoviesRepositoryImpl
import domain.usecase.GetMoviesUseCaseImpl
import presentation.base.ListViewModel
import presentation.base.ViewModelBinding
import presentation.movies.MoviesListViewModel

private const val API_KEY = BuildConfig.API_KEY

class MainActivity : AppCompatActivity() {

    private lateinit var mMoviesAdapter: MoviesAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRefreshLayout: SwipeRefreshLayout
    private var mIsRefreshing = false
    private val mBinding = ViewModelBinding()
    private val mViewModel: ListViewModel<String, MovieUI> by lazy {
        val moviesMapper = MoviesMapper()
        val api = MoviesApiImpl("b445ca0b", "https://www.omdbapi.com/", moviesMapper)
        val repository = MoviesRepositoryImpl(api)
        val useCase = GetMoviesUseCaseImpl(repository)
        val moviesUiMapper = MoviesUIMapper()

        MoviesListViewModel(useCase, moviesUiMapper)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
    }

    private fun setupUI() {
        //mainMessageTxt.text = HelloWorldMessage()
    }

}
