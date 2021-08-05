package br.com.mfet.jmovie.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.mfet.jmovie.databinding.ActivityMovieDetailsBinding
import br.com.mfet.jmovie.models.Movie
import br.com.mfet.jmovie.view.adapter.MovieViewAdapter
import br.com.mfet.jmovie.viewmodel.MainActivityViewModel.Companion.MOVIE_ID
import br.com.mfet.jmovie.viewmodel.MovieDetailsViewModel

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var movieAdapter : MovieViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()

        val movieId = intent.getIntExtra(MOVIE_ID, -1)
        println("VALOR ID: $movieId")
        initViewModel()

    }

    fun setupBinding() {
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun initViewModel() {
        val viewModel = ViewModelProvider(this)
            .get(MovieDetailsViewModel::class.java)
        viewModel.addObserver().observe(this, Observer <Movie>{
            if(it != null) {
                movieAdapter.setUpdateData(it.results)
            } else {
                Toast.makeText(this, "Erro ao carregar dados", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.getApiMovieCall()

    }



}