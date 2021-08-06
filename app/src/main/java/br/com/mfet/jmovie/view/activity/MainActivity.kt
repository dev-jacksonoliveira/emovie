package br.com.mfet.jmovie.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.mfet.jmovie.databinding.ActivityMainBinding
import br.com.mfet.jmovie.view.adapter.MovieViewAdapter
import br.com.mfet.jmovie.viewmodel.MainActivityViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapterPopular: MovieViewAdapter
    private lateinit var movieAdapterUpComing: MovieViewAdapter

    private lateinit var viewModel: MainActivityViewModel

    companion object {
        const val MOVIE_ID = "br.com.mfet.jmovie.view.activity.MainActivity.MOVIE_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this)
            .get(MainActivityViewModel::class.java)

        movieAdapterPopular = MovieViewAdapter {
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_ID, it)
            startActivity(intent)
        }
        binding.rvMovielistpopular.adapter = movieAdapterPopular

        movieAdapterUpComing = MovieViewAdapter {
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_ID, it)
            startActivity(intent)
        }
        binding.rvMovieupcoming.adapter = movieAdapterUpComing


        viewModel.getPopularObserver().observe(this, Observer {
            if(it != null)
            movieAdapterPopular.addItems(it)
        })

        viewModel.getUpComingObserver().observe(this, Observer {
            if(it != null)
            movieAdapterUpComing.addItems(it)
        })

        viewModel.movieListApiCall()
    }
}