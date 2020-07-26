package app.web.drjackycv.omdbmultiplatform.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.web.drjackycv.omdbmultiplatform.BuildConfig
import app.web.drjackycv.omdbmultiplatform.R
import app.web.drjackycv.omdbmultiplatform.presentation.adapter.MoviesAdapter


private val API_KEY = BuildConfig.API_KEY

class MainActivity : AppCompatActivity() {

    private lateinit var mMoviesAdapter: MoviesAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
    }

    private fun setupUI() {
        //mainMessageTxt.text = HelloWorldMessage()
    }

}
