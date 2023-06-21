package br.com.mfet.emovie.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.mfet.emovie.R
import br.com.mfet.emovie.databinding.ActivityMainBinding
import br.com.mfet.emovie.data.database.DatabaseService
import br.com.mfet.emovie.data.model.Movie
import br.com.mfet.emovie.presentation.details.MovieDetailsActivity
import br.com.mfet.emovie.utils.FirebaseUtils.firebaseAuth
import br.com.mfet.emovie.presentation.adapter.MovieViewAdapter
import br.com.mfet.emovie.presentation.favorite.MovieFavoriteActivity
import br.com.mfet.emovie.presentation.login.SignInActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var movieAdapterPopular: MovieViewAdapter

    // private var movieAdapterPopular: MovieViewAdapter? = null
    private lateinit var movieAdapterUpComing: MovieViewAdapter
    private lateinit var movieAdapterFavorite: MovieViewAdapter

    private val viewModel: MovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupListeners()
        setupObservers()



//        movieAdapterPopular = MovieViewAdapter({
//            val intent = Intent(this, MovieDetailsActivity::class.java)
//            intent.putExtra(MOVIE_ID, it)
//            startActivity(intent)
//        }, { movie, movieFavorite ->
//            setupMovieFavoritesList(movie, movieFavorite)
//        })
//        binding.rvMovielistpopular.adapter = movieAdapterPopular
//
//        movieAdapterUpComing = MovieViewAdapter({
//            val intent = Intent(this, MovieDetailsActivity::class.java)
//            intent.putExtra(MOVIE_ID, it)
//            startActivity(intent)
//        }, { movie, movieFavorite ->
//            setupMovieFavoritesList(movie, movieFavorite)
//        })
//        binding.rvMovieupcoming.adapter = movieAdapterUpComing


//        viewModel.getPopularObserver().observe(this, Observer {
//            if (it != null)
//                movieAdapterPopular.addItems(it)
//        })

//        viewModel.getUpComingObserver().observe(this, Observer {
//            if (it != null)
//                movieAdapterUpComing.addItems(it)
//        })
//
//        viewModel.movieListApiCall()

//        movieAdapterFavorite = MovieViewAdapter({
//            val intent = Intent(this, MovieDetailsActivity::class.java)
//            intent.putExtra(MOVIE_ID, it)
//            startActivity(intent)
//        }, { movie, isChecked ->
//            TODO()
//        })
    }

    private fun setupViews() {
        TODO()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when(uiState) {
                        is MovieUiState.Success -> showPopularMoviesList(uiState.popularMovies)
                        is MovieUiState.Error -> showError()
                    }
                }
            }
        }
    }

    private fun setupListeners() = with(binding) {
        btnSignOut.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(SignInActivity.getLaunchIntent(this@MainActivity))
            finish()
        }

        btnFavoritos.setOnClickListener {
            startActivity(MovieFavoriteActivity.getLaunchIntent(this@MainActivity))
        }
    }

    private fun showPopularMoviesList(popularMoviesList: List<Movie>) {
        movieAdapterPopular.addItems(popularMoviesList)
    }

    private fun showError() {
        Log.d("TAG", "Erro")
    }

    private fun setupMovieFavoritesList(movie: Movie, movieFavorite: Boolean) {
        if (movieFavorite) {
            DatabaseService.setMovieFavorite(this, movie)
            showToastMessage(R.string.movie_added_to_favorites_list)
        } else {
            DatabaseService.deleteMovieFavorite(this, movie)
            showToastMessage(R.string.movie_removed_from_favorites_list)
        }
    }

    private fun showToastMessage(message: Int) {
        Toast.makeText(
            this,
            getString(message),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun navigateToMovieDetails() {
        startActivity(MovieDetailsActivity.getLaunchIntent(this@MainActivity))
    }

    companion object {
        fun getLaunchIntent(context: Context) = Intent(context, MainActivity::class.java)
        const val MOVIE_ID = "movie_id_key"
    }
}