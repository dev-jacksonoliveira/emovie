package br.com.mfet.jmovie.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.mfet.jmovie.databinding.MovieItemBinding
import br.com.mfet.jmovie.data.model.Movie
import com.bumptech.glide.Glide

class MovieViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)

class MovieViewAdapter(
    val movieClickListener: (Int) -> Unit, val btnCheckCallback: (Movie, Boolean) -> Unit
) : RecyclerView.Adapter<MovieViewHolder>() {

    val movieList : MutableList<Movie> = mutableListOf()

    fun addItems(items: List<Movie>) {
        movieList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = movieList[position]
//        val itemUpComing = moviesUpComing[position]

        holder.let {

            it.binding.title = item.title

            Glide.with(it.binding.root)
                .load("https://image.tmdb.org/t/p/w500${item.posterPath}")
                .into(it.binding.ivPoster)
            it.binding.itemBackground.setOnClickListener {
                movieClickListener(item.id)
            }
            it.binding.iconFavorite.setOnCheckedChangeListener { buttonView, isChecked ->
                btnCheckCallback(item, isChecked)
            }
        }
    }

    override fun getItemCount() = movieList.size

}