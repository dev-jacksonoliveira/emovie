package br.com.mfet.jmovie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.mfet.jmovie.view.adapter.MovieViewAdapter
import br.com.mfet.jmovie.models.Movie
import br.com.mfet.jmovie.view.activity.MovieDetailsActivity
import br.com.mfet.jmovie.viewmodel.MainActivityViewModel
import br.com.mfet.jmovie.viewmodel.MainActivityViewModel.Companion.MOVIE_ID

class MovieListFragment : Fragment() {

    private lateinit var movieAdapter : MovieViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_movie_list, container, false)

        initViewModel(view)
        initViewModel()
        return view
    }

    private fun initViewModel(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_movielistpopular)
        val recyclerViewLatest = view.findViewById<RecyclerView>(R.id.rv_movieupcoming)

        recyclerView.layoutManager = LinearLayoutManager(
            activity, LinearLayoutManager.HORIZONTAL,false
        )

        recyclerViewLatest.layoutManager = LinearLayoutManager(
            activity, LinearLayoutManager.HORIZONTAL, false
        )

        movieAdapter = MovieViewAdapter { id ->
            val intent = Intent(activity, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_ID, id)
            startActivity(intent)
        }
        recyclerView.adapter = movieAdapter
        recyclerViewLatest.adapter = movieAdapter

    }

    private fun initViewModel() {
        val viewModel = ViewModelProvider(this)
            .get(MainActivityViewModel::class.java)
        viewModel.getRecyclerListObserver().observe(this, Observer<Movie> {
            if(it != null) {
                movieAdapter.setUpdateData(it.results)
            } else {
                Toast.makeText(activity, "Erro ao carregar dados", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCall()
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            MovieListFragment()
    }
}