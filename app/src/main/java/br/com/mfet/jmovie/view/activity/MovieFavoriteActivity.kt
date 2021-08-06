package br.com.mfet.jmovie.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.mfet.jmovie.databinding.ActivityMovieFavoriteBinding
import br.com.mfet.jmovie.repository.DatabaseService
import br.com.mfet.jmovie.view.adapter.MovieViewAdapter

class MovieFavoriteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMovieFavoriteBinding
    private lateinit var movieAdapterFavorite: MovieViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieFavoriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        movieListFavDbCall()

        movieAdapterFavorite =  MovieViewAdapter({
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra(MainActivity.MOVIE_ID, it)
            startActivity(intent)
        }, { movie, isFavorite ->
            if (isFavorite) DatabaseService.setMovieFavorite(this, movie)
            else DatabaseService.deleteMovieFavorite(this, movie)

        })
        binding.rvMoviefavorite.adapter = movieAdapterFavorite
    }

    fun movieListFavDbCall() {
        DatabaseService.getMovieFavorite(this) { list ->
            movieAdapterFavorite.addItems(list)
        }

    }

}