package br.com.mfet.emovie.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.mfet.emovie.databinding.ActivityMovieDetailsBinding
import br.com.mfet.emovie.data.model.Movie
import br.com.mfet.emovie.data.database.DatabaseService
import br.com.mfet.emovie.presentation.MainActivity.Companion.MOVIE_ID
import br.com.mfet.emovie.presentation.adapter.MovieViewAdapter
import br.com.mfet.emovie.viewmodel.MovieDetailsViewModel
import com.bumptech.glide.Glide

class MovieDetailsActivity : AppCompatActivity() {
    private val binding: ActivityMovieDetailsBinding by lazy {
        ActivityMovieDetailsBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var movieDetailsAdapter: MovieViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
        val movieId = intent.getIntExtra(MOVIE_ID, -1)

        observe()
        initViewModel(movieId)
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnAddFavorites.setOnClickListener {
            startActivity(MovieFavoriteActivity.getLaunchIntent(this@MovieDetailsActivity))
            movieDetailsAdapter = MovieViewAdapter({

            }, { movie, isFavorite ->
                if (isFavorite) {
                    DatabaseService.setMovieFavorite(this, movie)
                    Toast.makeText(
                        this, "Filme adicionado à sua lista de favoritos!",
                        Toast.LENGTH_LONG
                    ).show()
                }

            })

        }
    }

    private fun initViewModel(id: Int) {
        viewModel.getApiMovieCall(id)
    }

    private fun observe() {
        viewModel.getMovie().observe(this) {
            if (it != null) {
                setDetails(it)
            } else {
                Toast.makeText(this, "Erro ao carregar dados", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setDetails(it: Movie) {
        binding.apply {
            Glide.with(root)
                .load("https://image.tmdb.org/t/p/w500${it.posterPath}")
                .into(ivPoster)

            title.text = it.title
            releaseDate.text = it.releaseDate
            tvOverview.text = it.overview
        }
    }

    private fun onClickAddFavorite() {
        movieDetailsAdapter = MovieViewAdapter({
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_ID, it)
            startActivity(intent)
        }, { movie, isFavorite ->
            if (isFavorite) {
                DatabaseService.setMovieFavorite(this, movie)
                Toast.makeText(
                    this, "Filme adicionado à sua lista de favoritos!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                DatabaseService.deleteMovieFavorite(this, movie)
                Toast.makeText(
                    this, "Filme removido da sua lista de favoritos!",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    companion object {
        fun getLaunchIntent(context: Context) = Intent(context, MovieDetailsActivity::class.java)
    }
}