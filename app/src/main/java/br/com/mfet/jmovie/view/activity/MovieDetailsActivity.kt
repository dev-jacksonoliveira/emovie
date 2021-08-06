package br.com.mfet.jmovie.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.mfet.jmovie.databinding.ActivityMovieDetailsBinding
import br.com.mfet.jmovie.models.Movie
import br.com.mfet.jmovie.view.activity.MainActivity.Companion.MOVIE_ID
import br.com.mfet.jmovie.viewmodel.MovieDetailsViewModel
import com.bumptech.glide.Glide

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding

    private lateinit var viewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()

        viewModel = ViewModelProvider(this)
            .get(MovieDetailsViewModel::class.java)

        val movieId = intent.getIntExtra(MOVIE_ID, -1)

        observe()
        initViewModel(movieId)

    }

    fun setupBinding() {
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun initViewModel(id: Int) {

        viewModel.getApiMovieCall(id)
    }

    fun observe() {
        viewModel.getMovie().observe(this, {
            if (it != null) {
                setDetails(it)
            } else {
                Toast.makeText(this, "Erro ao carregar dados", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun setDetails(it: Movie) {
        binding.apply {
            Glide.with(root)
                .load("https://image.tmdb.org/t/p/w500${it.poster_path}")
                .into(ivPoster)

            title.text = it.title
            releaseDate.text = it.release_date
            tvOverview.text = it.overview
        }

    }
}