package br.com.mfet.jmovie.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.mfet.jmovie.databinding.ActivityMovieDetailsBinding
import br.com.mfet.jmovie.model.Movie
import br.com.mfet.jmovie.repository.DatabaseService
import br.com.mfet.jmovie.view.activity.MainActivity.Companion.MOVIE_ID
import br.com.mfet.jmovie.view.adapter.MovieViewAdapter
import br.com.mfet.jmovie.viewmodel.MovieDetailsViewModel
import com.bumptech.glide.Glide

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var movieDetailsAdapter: MovieViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()

        viewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)

        val movieId = intent.getIntExtra(MOVIE_ID, -1)

        observe()
        initViewModel(movieId)
        binding.btnAddFavorites.setOnClickListener {
            val intent = Intent(this, MovieFavoriteActivity::class.java)
            startActivity(intent)
            movieDetailsAdapter = MovieViewAdapter({

            }, { movie, isFavorite ->
                if (isFavorite) {
                    DatabaseService.setMovieFavorite(this, movie)
                    Toast.makeText(this, "Filme adicionado à sua lista de favoritos!",
                        Toast.LENGTH_LONG).show()
                }

            })

        }
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

    private fun onClickAddFavorite() {

        movieDetailsAdapter = MovieViewAdapter({
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_ID, it)
            startActivity(intent)
        }, { movie, isFavorite ->
            if (isFavorite) {
                DatabaseService.setMovieFavorite(this, movie)
                Toast.makeText(this, "Filme adicionado à sua lista de favoritos!",
                    Toast.LENGTH_LONG).show()
            }
            else {
                DatabaseService.deleteMovieFavorite(this, movie)
                Toast.makeText(this, "Filme removido da sua lista de favoritos!",
                    Toast.LENGTH_LONG).show()
            }

        })

//        val intent = Intent(this, MovieFavoriteActivity::class.java)
//        startActivity(intent)
    }
}