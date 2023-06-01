package br.com.mfet.emovie.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.mfet.emovie.databinding.ActivityMainBinding
import br.com.mfet.emovie.utils.extensions.Extensions.toast
import br.com.mfet.emovie.data.database.DatabaseService
import br.com.mfet.emovie.utils.FirebaseUtils.firebaseAuth
import br.com.mfet.emovie.presentation.adapter.MovieViewAdapter
import br.com.mfet.emovie.viewmodel.MainActivityViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapterPopular: MovieViewAdapter
    private lateinit var movieAdapterUpComing: MovieViewAdapter
    private lateinit var movieAdapterFavorite: MovieViewAdapter

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this)
            .get(MainActivityViewModel::class.java)

        movieAdapterPopular = MovieViewAdapter({
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
        binding.rvMovielistpopular.adapter = movieAdapterPopular

        movieAdapterUpComing = MovieViewAdapter({
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_ID, it)
            startActivity(intent)
        }, { movie, isChecked ->
            if (isChecked) {
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
        binding.rvMovieupcoming.adapter = movieAdapterUpComing


        viewModel.getPopularObserver().observe(this, Observer {
            if (it != null)
                movieAdapterPopular.addItems(it)
        })

        viewModel.getUpComingObserver().observe(this, Observer {
            if (it != null)
                movieAdapterUpComing.addItems(it)
        })

        viewModel.movieListApiCall()

        movieAdapterFavorite = MovieViewAdapter({
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_ID, it)
            startActivity(intent)
        }, { movie, isChecked ->
            TODO()
        })

        intentFavorites()

        binding.btnSignOut.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, SignInActivity::class.java))
            toast("Conexão encerrada com sucesso")
            finish()
        }

    }

    fun intentFavorites() {
       binding.btnFavoritos.setOnClickListener {
           val intent = Intent(this, MovieFavoriteActivity::class.java)
           startActivity(intent)
       }
    }

    companion object {
        fun getLaunchIntent(context: Context) = Intent(context, MainActivity::class.java)
        const val MOVIE_ID = "br.com.mfet.emovie.view.activity.MainActivity.MOVIE_ID"
    }
}