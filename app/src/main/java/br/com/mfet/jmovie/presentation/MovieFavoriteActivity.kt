package br.com.mfet.jmovie.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import br.com.mfet.jmovie.databinding.ActivityMovieFavoriteBinding
import br.com.mfet.jmovie.data.repository.DatabaseService
import br.com.mfet.jmovie.presentation.adapter.MovieViewAdapter

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
        }, { movie, isChecked ->
            if (isChecked) DatabaseService.setMovieFavorite(this, movie)

//            if (isChecked) {
//                DatabaseService.deleteMovieFavorite(this, movie)
//                !isChecked
//                Toast.makeText(this, "Filme deletado com sucesso!",
//                    Toast.LENGTH_LONG).show()
//            }
            else DatabaseService.deleteMovieFavorite(this, movie)

        })
        binding.rvMoviefavorite.adapter = movieAdapterFavorite
        binding.rvMoviefavorite.layoutManager =  GridLayoutManager(this,2)

    }

    fun movieListFavDbCall() {
        DatabaseService.getMovieFavorite(this) { list ->
            movieAdapterFavorite.addItems(list)

        }
    }
}